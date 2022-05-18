package com.net.plus.supermarket.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.net.plus.supermarket.model.ItemsToBeBilled;
import com.net.plus.supermarket.model.SuperMarket;
import com.net.plus.supermarket.repository.SuperMarketRepository;

/**
 * @author Alekhya
 *
 */
@Service
public class SuperMarketServiceImpl implements SuperMarketService {

	@Autowired
	private SuperMarketRepository repo;

	@Override
	public Double getItemDetails(List<ItemsToBeBilled> items) {

		double totalBill = 0.0;

		Map<String, Integer> itemsMap = new HashMap<>();
		for (ItemsToBeBilled item : items) {
			itemsMap.merge(item.getItemName(), item.getQuantity(), Integer::sum);
		}

		for (Map.Entry<String, Integer> item : itemsMap.entrySet()) {
			List<SuperMarket> smDetailsSplOffer = repo.findByItemNameAndOtherItemIsNotNull(item.getKey());

			if (smDetailsSplOffer.size() == 1) {
				for (SuperMarket sm : smDetailsSplOffer) {

					if (itemsMap.containsKey(sm.getOtherItem())
							&& itemsMap.get(sm.getOtherItem()) >= sm.getOtherQuantity()) {

						int billedOtherItemQty = itemsMap.get(sm.getOtherItem());
						int otherItemQty = sm.getOtherQuantity();
						int remQty = 0;
						if (itemsMap.get(sm.getItemName()) > billedOtherItemQty)
							if (billedOtherItemQty % otherItemQty == 0) {
								remQty = Math.abs(itemsMap.get(sm.getItemName()) - billedOtherItemQty);
							} else
								remQty = (itemsMap.get(sm.getItemName()) - billedOtherItemQty)
										+ (billedOtherItemQty % otherItemQty);

						totalBill += (sm.getUnitPrice() * remQty) + (sm.getSplPriceBasedOnOtherItem())
								* (billedOtherItemQty - (billedOtherItemQty % otherItemQty));
						System.out.println(totalBill);
					} else if (item.getValue() < sm.getQuantity()) {
						totalBill += sm.getUnitPrice() * item.getValue();
					} else if (item.getValue() == sm.getQuantity()) {
						totalBill += sm.getSplPrice();
					}
				}

			}

			List<SuperMarket> smDetails = repo.findByItemNameAndOtherItemIsNull(item.getKey());

			if (smDetails.size() == 1) {

				for (SuperMarket sm : smDetails) {
					if (item.getValue() < sm.getQuantity()) {
						totalBill += sm.getUnitPrice() * item.getValue();
					} else if (item.getValue() == sm.getQuantity()) {
						totalBill += sm.getSplPrice();
					} else {
						int quo = item.getValue() / sm.getQuantity();
						int rem = item.getValue() % sm.getQuantity();
						totalBill += (sm.getUnitPrice() * rem) + (sm.getSplPrice() * quo);
					}

				}
			} else if (smDetailsSplOffer.size() == 0) {
				totalBill += findRelaventInstance(smDetails, item);
			}

		}

		return totalBill;

	}

	/**
	 * This method executes if more than one Super market objects are found.
	 * 
	 * @param smDetails List of actual SP details
	 * @param item      item to be billed
	 * @return total price
	 */
	private Double findRelaventInstance(List<SuperMarket> smDetails, Entry<String, Integer> item) {
		TreeMap<Integer, Double> itemsMap = new TreeMap<>();
		for (SuperMarket sm : smDetails) {
			itemsMap.put(1, sm.getUnitPrice());
			itemsMap.put(sm.getQuantity(), sm.getSplPrice());
		}

		Map.Entry<Integer, Double> low = itemsMap.floorEntry(item.getValue());
		Map.Entry<Integer, Double> high = itemsMap.ceilingEntry(item.getValue());
		Double res = null;
		if (low != null && high != null) {
			res = Math.abs(item.getValue() - low.getKey()) < Math.abs(item.getValue() - high.getKey()) ? low.getValue()
					: high.getValue();
		} else if (low != null || high != null) {
			res = low != null ? low.getValue() : high.getValue();
		}
		int qty = 0;
		if (low != null && low.getValue() == res) {
			qty = low.getKey();
		} else if (high != null && high.getValue() == res)
			qty = high.getKey();

		if (qty == item.getValue()) {
			return res;
		} else if (qty > item.getValue()) {
			res = itemsMap.get(1) * qty;
		} else if (qty < item.getValue()) {
			int remQty = item.getValue() - qty;
			int quo = item.getValue() / qty;
			int rem = item.getValue() % qty;
			if (itemsMap.containsKey(remQty) && remQty != qty) {
				res = (itemsMap.get(remQty)) + (itemsMap.get(qty) * quo);
			} else
				res = (itemsMap.get(1) * rem) + (itemsMap.get(qty) * quo);
		}
		System.out.println(res);
		return res;
	}

}
