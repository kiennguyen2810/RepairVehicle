package com.nuce.repairvehiclemap.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
	public List<HistoryRepair> getHistoryRepair(String userName) {
		Account account = accountDao.getAccountByUserName(userName);
		List<HistoryRepair> historyRepairs = account.getHistoryRepairs();;
		List<HistoryRepair> historyRepairs2 = new ArrayList<HistoryRepair>();
		for (HistoryRepair historyRepair : historyRepairs) {
			historyRepair.setAccount(null);
			historyRepair.getShop().setServices(null);
			historyRepairs2.add(historyRepair);
		}
		return historyRepairs2;
	}



	@Override
	public List<HistoryRepair> searchHistoryRepair(String userName,
			String textSearch, String dateStr) {
//		System.out.println(userName);
//		System.out.println(textSearch);
//		System.out.println("date" + date); 
	    
		Account account = accountDao.getAccountByUserName(userName);
		List<HistoryRepair> historyRepairs = account.getHistoryRepairs();;
		List<HistoryRepair> historyRepairs2 = new ArrayList<HistoryRepair>();
		
		for (HistoryRepair historyRepair : historyRepairs) {
			if(historyRepair.getShop().getName().contains(textSearch)||historyRepair.getService().getName().contains(textSearch)){
				
				Date date = historyRepair.getTime();  
		        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
		        String strDate = dateFormat.format(date);  
		        System.out.println("Converted String: " + strDate);
		        System.out.println(dateStr);
				if(dateStr.equals(strDate)){
					historyRepair.setAccount(null);
					historyRepair.getShop().setServices(null);
					historyRepairs2.add(historyRepair);
				}

			}
		}
		return historyRepairs2;
	}

}
