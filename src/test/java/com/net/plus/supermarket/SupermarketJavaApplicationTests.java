package com.net.plus.supermarket;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.net.plus.supermarket.model.ItemsToBeBilled;
import com.net.plus.supermarket.model.SuperMarket;
import com.net.plus.supermarket.repository.SuperMarketRepository;
import com.net.plus.supermarket.service.SuperMarketService;

@RunWith(SpringRunner.class)
@SpringBootTest
class SupermarketJavaApplicationTests {

	@Autowired
	private SuperMarketService service;

	@MockBean
	private SuperMarketRepository repo;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	//@Test
	public void findingTotalBill_SingleProductSplPrice() {

		List<SuperMarket> smValue = new ArrayList<>();
		smValue.add(new SuperMarket(1L, "Soap", 50.0, 3, 130.0, null, 0, 0.0));

		SuperMarket sm = SuperMarket.builder().itemNumber(2L).itemName("Soap").unitPrice(30.0).quantity(2)
				.splPrice(45.0).build();

		Mockito.when(repo.findByItemNameAndOtherItemIsNull(sm.getItemName())).thenReturn(smValue);

		List<ItemsToBeBilled> list = new ArrayList<>();
		list.add(new ItemsToBeBilled(1, "Soap", 3));

		assertThat(service.getItemDetails(list)).isEqualTo(130.0);
	}

	//@Test
	public void findingTotalBill_MultipleProductsSplPrice_Offer1() {

		List<SuperMarket> smValue = new ArrayList<>(), smValue1 = new ArrayList<>(), smValue2 = new ArrayList<>();
		smValue.add(new SuperMarket(1L, "Soap", 50.0, 3, 130.0, null, 0, 0.0));
		SuperMarket sm = SuperMarket.builder().itemNumber(2L).itemName("Soap").build();
		Mockito.when(repo.findByItemNameAndOtherItemIsNull(sm.getItemName())).thenReturn(smValue);

		smValue1.add(new SuperMarket(2L, "Brush", 30.0, 2, 45.0, null, 0, 0.0));
		SuperMarket sm1 = SuperMarket.builder().itemNumber(2L).itemName("Brush").build();
		Mockito.when(repo.findByItemNameAndOtherItemIsNull(sm1.getItemName())).thenReturn(smValue1);

		smValue2.add(new SuperMarket(3L, "Paste", 20.0, 2, 38.0, null, 0, 0.0));
		smValue2.add(new SuperMarket(4L, "Paste", 20.0, 3, 50.0, null, 0, 0.0));
		SuperMarket sm2 = SuperMarket.builder().itemNumber(2L).itemName("Paste").build();
		Mockito.when(repo.findByItemNameAndOtherItemIsNull(sm2.getItemName())).thenReturn(smValue2);

		List<ItemsToBeBilled> list = new ArrayList<>();
		list.add(new ItemsToBeBilled(1, "Soap", 3));
		list.add(new ItemsToBeBilled(2, "Brush", 2));
		list.add(new ItemsToBeBilled(3, "Paste", 5));

		assertThat(service.getItemDetails(list)).isEqualTo(263.0);
	}

	@Test
	public void findingTotalBill_MultipleProductsSplPrice_Offer2() {

		List<SuperMarket> smValue = new ArrayList<>(), smValue1 = new ArrayList<>(), smValue2 = new ArrayList<>();
		smValue.add(new SuperMarket(1L, "Soap", 50.0, 3, 130.0, null, 0, 0.0));
		SuperMarket sm = SuperMarket.builder().itemNumber(2L).itemName("Soap").build();
		Mockito.when(repo.findByItemNameAndOtherItemIsNull(sm.getItemName())).thenReturn(smValue);

		smValue1.add(new SuperMarket(2L, "Brush", 30.0, 2, 45.0, null, 0, 0.0));
		SuperMarket sm1 = SuperMarket.builder().itemNumber(2L).itemName("Brush").build();
		Mockito.when(repo.findByItemNameAndOtherItemIsNull(sm1.getItemName())).thenReturn(smValue1);

		smValue2.add(new SuperMarket(3L, "Paste", 15.0, 1, 15.0, "Soap", 2, 5.0));
		SuperMarket sm2 = SuperMarket.builder().itemNumber(2L).itemName("Paste").build();
		Mockito.when(repo.findByItemNameAndOtherItemIsNotNull(sm2.getItemName())).thenReturn(smValue2);

		List<ItemsToBeBilled> list = new ArrayList<>();
		list.add(new ItemsToBeBilled(1, "Soap", 6));
		list.add(new ItemsToBeBilled(3, "Paste", 10));

		assertThat(service.getItemDetails(list)).isEqualTo(350.0);
	}

}
