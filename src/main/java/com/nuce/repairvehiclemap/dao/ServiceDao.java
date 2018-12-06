package com.nuce.repairvehiclemap.dao;

import java.util.List;


import com.nuce.repairvehiclemap.model.Service;

public interface ServiceDao {
	Service getServiceById(Integer id);
	
	List<Service> getService();

	void addService(Service service);

	void updateService(Integer id, Service service);

	void deleteService(Integer id);

	List<Service> getServiceByName(String serviceName);
}
