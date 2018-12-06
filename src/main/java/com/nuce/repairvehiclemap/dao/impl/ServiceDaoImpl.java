package com.nuce.repairvehiclemap.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nuce.repairvehiclemap.dao.ServiceDao;
import com.nuce.repairvehiclemap.model.Service;

@Repository
@Transactional(readOnly=false)
public class ServiceDaoImpl implements ServiceDao {
	
	@PersistenceContext
	EntityManager em;

	@Override
	public List<Service> getService() {
		String sql = "from Service" ;
		Query query = em.createQuery(sql);
		List<Service> services = query.getResultList();
		return services;
	}

	@Override
	public void addService(Service service) {
		em.persist(service);
	}

	@Override
	public void updateService(Integer id, Service service) {
		Service service2 = em.find(Service.class, id);
		service2.setName(service.getName());
		service2.setPrice(service.getPrice());
	}

	@Override
	public void deleteService(Integer id) {
		em.remove(em.find(Service.class, id));
	}

	@Override
	public List<Service> getServiceByName(String serviceName) {
		String sql = "FROM Service WHERE name LIKE :serviceName";
		Query query = em.createQuery(sql);
		query.setParameter("serviceName", "%" + serviceName + "%" );
		List<Service> services = query.getResultList();
		return services;
	}

	@Override
	public Service getServiceById(Integer id) {
		Service service = em.find(Service.class, id);
		return service;
	}

}
