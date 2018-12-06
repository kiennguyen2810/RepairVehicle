package com.nuce.repairvehiclemap.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.nuce.repairvehiclemap.dao.AccountDao;
import com.nuce.repairvehiclemap.dao.HistoryRepairDao;
import com.nuce.repairvehiclemap.dao.ServiceDao;
import com.nuce.repairvehiclemap.dao.ShopDao;
import com.nuce.repairvehiclemap.model.Account;
import com.nuce.repairvehiclemap.model.HistoryRepair;
import com.nuce.repairvehiclemap.model.Service;
import com.nuce.repairvehiclemap.model.Shop;
import com.nuce.repairvehiclemap.service.HistoryRepairServ;

@org.springframework.stereotype.Service
public class HistoryRepairServImpl implements HistoryRepairServ {
	
	@Autowired
	HistoryRepairDao historyRepairDao;
	@Autowired
	AccountDao accountDao;
	@Autowired
	ShopDao shopDao;
	@Autowired
	ServiceDao serviceDao;
	
	
	@Override
	public void saveHistoryRepair(String username, Integer idShop, List<Integer> idServices) {
		Account account = accountDao.getAccountByUserName(username);
		Shop shop = shopDao.getShopById(idShop);
		List<Service> services = new ArrayList<Service>();
		for (Integer id : idServices) {
			Service service = serviceDao.getServiceById(id);
			services.add(service);
		}
		
		historyRepairDao.saveHistoryRepair(account, shop, services);
	}



	@Override
	public Set<HistoryRepair> getHistoryRepair(String username) {
		Account account = accountDao.getAccountByUserName(username);
		Set<HistoryRepair> historyRepairs = account.getHistoryRepairs();
		return historyRepairs;
	}

}
