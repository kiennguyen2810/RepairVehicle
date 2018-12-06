package com.nuce.repairvehiclemap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nuce.repairvehiclemap.dao.ServiceDao;
import com.nuce.repairvehiclemap.model.Service;
import com.nuce.repairvehiclemap.service.ServiceServ;

@org.springframework.stereotype.Service
public class ServiceServImpl implements ServiceServ{
	
	@Autowired
	ServiceDao serviceDao;

	@Override
	public List<Service> getService() {
		return serviceDao.getService();
	}

	@Override
	public void addService(Service service) {
		serviceDao.addService(service);
	}

	@Override
	public void updateService(Integer id, Service service) {
		serviceDao.updateService(id, service);
	}

	@Override
	public void deleteService(Integer id) {
		serviceDao.deleteService(id);
	}

	@Override
	public List<Service> getServiceByName(String serviceName) {
		return serviceDao.getServiceByName(serviceName);
	}
	
}
