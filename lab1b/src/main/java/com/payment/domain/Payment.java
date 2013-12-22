package com.payment.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Payment {

	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String customer;
	private String good;
	private BigDecimal price;

	public Payment() {
	};

	public String getGood() {
		return good;
	}

	public void setGood(String good) {
		this.good = good;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal paymentPrice) {
		this.price = paymentPrice;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", name=" + name + ", customer="
				+ customer + ", good=" + good + ", price=" + price + "]";
	}

}
