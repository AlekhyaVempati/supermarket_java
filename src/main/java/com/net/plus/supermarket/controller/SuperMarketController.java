package com.net.plus.supermarket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.net.plus.supermarket.model.ItemsToBeBilled;
import com.net.plus.supermarket.service.SuperMarketService;

@RestController
public class SuperMarketController {

	@Autowired
	private SuperMarketService superMarketService;

	/**
	 * Post method finds the total bill amount
	 * 
	 * @param items List of items to be billed
	 * @return total amount
	 */
	@PostMapping(value = "/findTotalBill")
	public Double findTotalBillBasedOnItems(@RequestBody List<ItemsToBeBilled> items) {

		return superMarketService.getItemDetails(items);
	}

}
