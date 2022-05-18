package com.net.plus.supermarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.net.plus.supermarket.model.SuperMarket;

/**
 * SuperMarketRepository to fetch/save data from/to database
 * @author Alekhya
 *
 */
@Repository
public interface SuperMarketRepository extends JpaRepository<SuperMarket, Long>{

	/**
	 * findByItemName finds all items based on name
	 * @param itemName
	 * @return
	 */
	List<SuperMarket> findByItemName(String itemName);

	List<SuperMarket> findByItemNameAndOtherItemIsNotNull(String itemName);
	List<SuperMarket> findByItemNameAndOtherItemIsNull(String itemName);

	List<SuperMarket> findByItemNameAndQuantity(String otherItem, int otherQuantity);

}
