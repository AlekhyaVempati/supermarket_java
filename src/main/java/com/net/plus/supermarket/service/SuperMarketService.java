package com.net.plus.supermarket.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.net.plus.supermarket.model.ItemsToBeBilled;

/**
 * SuperMarketService
 * @author Alekhya
 *
 */
@Service
public interface SuperMarketService {

	/**
	 * getItemDetails
	 * @param items
	 * @return
	 */
	Double getItemDetails(List<ItemsToBeBilled> items);

}
