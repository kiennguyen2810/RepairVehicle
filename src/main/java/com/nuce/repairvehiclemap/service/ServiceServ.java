package com.nuce.repairvehiclemap.service;

import java.util.List;

import com.nuce.repairvehiclemap.model.Service;



public interface ServiceServ {
	List<Service> getService();

	void addService(Service service);

	void updateService(Integer id, Service service);

	void deleteService(Integer id);

	List<Service> getServiceByName(String serviceName);
}
