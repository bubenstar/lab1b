package com.payment.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class PaymentRepositoryImpl implements PaymentRepository {

	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<Payment> findAll() {
		String query = "select p from Payment p";
		TypedQuery<Payment> typedQuery = entityManager.createQuery(query,
				Payment.class);
		return typedQuery.getResultList();
	}

	public void updateEntity(Payment updatedEntity) {
		entityManager.getTransaction().begin();
		entityManager.refresh(updatedEntity);
		entityManager.getTransaction().commit();
	}

	public List<Payment> findAllStartsWithE() {
		String query = "select p from Payment p where p.name like 'E%'";
		TypedQuery<Payment> typedQuery = entityManager.createQuery(query,
				Payment.class);
		return typedQuery.getResultList();
	}

	public void addEntity(Payment entity) {
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
	}

	public List<Payment> findRepeatableEntities() {
		String query = "SELECT p "
				+ "			FROM Payment p " + "			WHERE p.name " + "			IN ("
				+ "					SELECT p.name " + "					FROM Payment p "
				+ "					GROUP BY p.name " + "					HAVING COUNT(p.name) > 1)";
			TypedQuery<Payment> typedQuery = entityManager.createQuery(query, Payment.class);
			return typedQuery.getResultList();
	}

	public void addEntityList(List<Payment> list) {
		for (Payment entity : list) {
			entityManager.getTransaction().begin();
			entityManager.persist(entity);
			entityManager.getTransaction().commit();
		}
	}

	public void clearPayments() {
		for (Payment entity : findAll()) {
			entityManager.getTransaction().begin();
			entityManager.remove(entity);
			entityManager.getTransaction().commit();
		}
	}

}
