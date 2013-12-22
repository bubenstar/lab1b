package com.payment.application;

import java.util.List;

import com.payment.domain.Payment;

public interface ApplicationService {

	void changeEntityName() throws Exception;

	List<Payment> findEntitiesWithRepeatableNames() throws Exception;

}
