package com.payment.application;

import java.util.List;

import com.payment.domain.Payment;
import com.payment.domain.PaymentRepository;

public class ApplicationServiceImpl implements ApplicationService {

	private PaymentRepository repo;

	public PaymentRepository getRepo() {
		return repo;
	}

	public void setRepo(PaymentRepository repo) {
		this.repo = repo;
	}

	public void changeEntityName() throws Exception {
		List<Payment> payments = repo.findAllStartsWithE();
		if (payments.size() == 0) {
			throw new Exception("Not found appropriate records");
		}
		for (Payment payment : payments) {
			payment.setName(payment.getName() + "_3");
			repo.updateEntity(payment);
		}
	}

	public List<Payment> findEntitiesWithRepeatableNames() throws Exception {
		List<Payment> repeatable = repo.findRepeatableEntities();
		if (!repeatable.isEmpty()) {
			return repeatable;
		}
		throw new Exception("Not found appropriate records");
	}

}
