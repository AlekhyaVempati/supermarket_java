package com.net.plus.supermarket.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * SuperMarket Entity
 * 
 * @author Alekhya
 *
 */
@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SuperMarket {

	@Id
	private Long itemNumber;
	@NotNull
	private String itemName;
	@NotNull
	private double unitPrice;
	@Column(nullable = true)
	private int quantity;
	@Column(nullable = true)
	private double splPrice;
	@Column(nullable = true)
	private String otherItem;
	@Column(nullable = true)
	private int otherQuantity;
	@Column(nullable = true)
	private double splPriceBasedOnOtherItem;

}
