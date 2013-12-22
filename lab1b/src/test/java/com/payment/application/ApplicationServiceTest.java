package com.payment.application;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.payment.application.ApplicationServiceImpl;
import com.payment.domain.Payment;
import com.payment.domain.PaymentRepository;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceTest extends TestCase {

	@Mock
	PaymentRepository repo;

	@InjectMocks
	ApplicationServiceImpl service;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		initDataSource();
	}

	@Test
	public void testChangeEntityName() throws Exception {
		List<Payment> list = new ArrayList<Payment>();
		list.add(createPayment("EBookPayment_3", "Book", "Vasya",
				new BigDecimal(2500)));
		when(repo.findAllStartsWithE()).thenReturn(list);
		String expected = "EBookPayment_3";
		service.changeEntityName();
		String actual = repo.findAll().get(0).getName();
		assertEquals(expected, actual);
		verify(repo, times(1)).findAllStartsWithE();
	}

	@Test(expected = Exception.class)
	public void testChangeEntityNameWhenNoEntitiesWithNameStartsWithE()
			throws Exception {
		List<Payment> list = new ArrayList<Payment>();
		when(repo.findAllStartsWithE()).thenReturn(list);
		service.changeEntityName();
		verify(repo, times(1)).findAllStartsWithE();
	}

	@Test
	public void testChangeEntityNameNoChanges() throws Exception {
		List<Payment> list = new ArrayList<Payment>();
		list.add(createPayment("CoffeePayment", "Coffee", "Petya",
				new BigDecimal(300)));
		when(repo.findAllStartsWithE()).thenReturn(list);
		String expected = "CoffeePayment";
		service.changeEntityName();
		String actual = repo.findAll().get(1).getName();
		assertEquals(expected, actual);
		verify(repo, times(1)).findAllStartsWithE();
	}

	@Test
	public void testGetPaymentWithRepeatableNames() throws Exception {
		List<Payment> expected = new ArrayList<Payment>();
		expected.add(createPayment("CoffeePayment", "Coffee", "Petya",
				new BigDecimal(300)));
		expected.add(createPayment("CoffeePayment", "Coffee", "Petya",
				new BigDecimal(300)));
		when(repo.findRepeatableEntities()).thenReturn(expected);
		List<Payment> actual = service.findEntitiesWithRepeatableNames();
		assertEquals(expected, actual);
		verify(repo, times(1)).findRepeatableEntities();
	}

	@Test
	public void testGetPaymentWithRepeatableNamesWhenNamesEqualsDifferentOtherFields()
			throws Exception {
		List<Payment> expected = new ArrayList<Payment>();
		expected.add(createPayment("CoffeePayment", "Egg", "Vasya",
				new BigDecimal(400)));
		expected.add(createPayment("CoffeePayment", "Coffee", "Petya",
				new BigDecimal(300)));
		when(repo.findRepeatableEntities()).thenReturn(expected);
		List<Payment> actual = service.findEntitiesWithRepeatableNames();
		assertEquals(expected, actual);
		verify(repo, times(1)).findRepeatableEntities();
	}

	@Test(expected = Exception.class)
	public void testGetPaymentWithRepeatableNamesNoRecords() throws Exception {
		List<Payment> list = new ArrayList<Payment>();
		when(repo.findRepeatableEntities()).thenReturn(list);
		service.findEntitiesWithRepeatableNames();
		verify(repo, times(1)).findAllStartsWithE();
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

		when(repo.findAll()).thenReturn(list);

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
