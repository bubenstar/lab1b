package com.payment.domain;

import java.util.List;

public interface PaymentRepository {

	List<Payment> findAll();

	void updateEntity(Payment updatedEntity);

	List<Payment> findAllStartsWithE();
	
	void addEntity(Payment entity);
	
	List<Payment> findRepeatableEntities();
	
	void addEntityList(List<Payment> list);
	
	void clearPayments();

}
