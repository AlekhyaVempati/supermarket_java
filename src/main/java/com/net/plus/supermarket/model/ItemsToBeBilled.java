package com.net.plus.supermarket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * ItemsToBeBilled model class to accept such items
 * @author Alekhya
 *
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ItemsToBeBilled {

	private Integer itemNumber;
	private String itemName;
	private Integer quantity;

}
