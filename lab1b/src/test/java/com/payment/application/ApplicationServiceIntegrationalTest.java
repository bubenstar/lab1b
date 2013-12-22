package com.payment.application;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.payment.domain.Payment;
import com.payment.domain.PaymentRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ApplicationServiceIntegrationalTest extends TestCase {

	@Autowired
	PaymentRepository repo;

	@Autowired
	ApplicationServiceImpl service;
	
	@Before
	public void before(){
		initDataSource();
	}
	
	@After
	public void after(){
		refreshDataSource();
	}
			
	@Test
	public void testChangeEntityName() throws Exception {
		String expected = "EBookPayment_3";
		service.changeEntityName();
		String actual = repo.findAll().get(0).getName();
		assertEquals(expected, actual);
	}

	@Test(expected = Exception.class)
	public void testChangeEntityNameWhenNoEntitiesWithNameStartsWithE()
			throws Exception {		
		refreshDataSource();
		repo.addEntityList(new ArrayList<Payment>());	
		service.changeEntityName();
	}

	@Test
	public void testChangeEntityNameNoChanges() throws Exception {
		String expected = "CoffeePayment";
		service.changeEntityName();
		String actual = repo.findAll().get(1).getName();
		assertEquals(expected, actual);
	}

	@Test
	public void testGetPaymentWithRepeatableNames() throws Exception {
		String expectedName = "CoffeePayment";
		List<Payment> repeatableList = service.findEntitiesWithRepeatableNames();
		String actual = repeatableList.get(0).getName();
		assertEquals(3, repeatableList.size());
		assertEquals(expectedName, actual);
	}

	@Test
	public void testGetPaymentWithRepeatableNamesWhenNamesEqualsDifferentOtherFields()
			throws Exception {
		String expectedName = "CoffeePayment";
		List<Payment> repeatableList = service.findEntitiesWithRepeatableNames();
		String actualName1 = repeatableList.get(0).getName();
		String actualName2 = repeatableList.get(2).getName();
		String actualCustomer1 = repeatableList.get(0).getCustomer();
		String actualCustomer2 = repeatableList.get(2).getCustomer();
		assertEquals(3, repeatableList.size());
		assertEquals(expectedName, actualName1);
		assertEquals(expectedName, actualName2);
		assertNotSame(actualCustomer1,actualCustomer2);
	}

	@Test(expected = Exception.class)
	public void testGetPaymentWithRepeatableNamesNoRecords() throws Exception {
		refreshDataSource();	
		service.findEntitiesWithRepeatableNames();
	}

	// -------------------------------------------------------------------
	private void initDataSource() {

		List<Payment> list = new ArrayList<Payment>();

		list.add(createPayment("EBookPayment_3", "Book", "Vasya",
				new BigDecimal(2500)));
		list.add(createPayment("CoffeePayment", "Coffee", "Petya",
				new BigDecimal(300)));
		list.add(createPayment("CoffeePayment", "Coffee", "Petya",
				new BigDecimal(300)));
		list.add(createPayment("EggPayment_3", "Egg", "Sonya", new BigDecimal(
				500)));
		list.add(createPayment("CoffeePayment", "Egg", "Vasya", new BigDecimal(
				400)));

		repo.addEntityList(list);
	}
	
	private void refreshDataSource() {
		repo.clearPayments();
	}

	private Payment createPayment(String name, String good, String customer,
			BigDecimal paymentPrice) {
		Payment result = new Payment();
		result.setCustomer(customer);
		result.setGood(good);
		result.setName(name);
		result.setPrice(paymentPrice);
		return result;
	}
}
