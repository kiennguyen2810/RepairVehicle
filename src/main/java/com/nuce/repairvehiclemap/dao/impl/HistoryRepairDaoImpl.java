package com.nuce.repairvehiclemap.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nuce.repairvehiclemap.dao.HistoryRepairDao;
import com.nuce.repairvehiclemap.model.Account;
import com.nuce.repairvehiclemap.model.HistoryRepair;
import com.nuce.repairvehiclemap.model.Service;
import com.nuce.repairvehiclemap.model.Shop;

@Repository
@Transactional
public class HistoryRepairDaoImpl implements HistoryRepairDao {
	@PersistenceContext
	EntityManager em;

	@Override
	public void saveHistoryRepair(Account account, Shop shop, List<Service> services) {
		
		for (Service service : services) {
			HistoryRepair historyRepair = new HistoryRepair();
			historyRepair.setAccount(account);
			historyRepair.setShop(shop);
			historyRepair.setService(service);
			historyRepair.setTime(new Date());
//			System.out.println("Aaaaaaaa");
//			System.out.println(historyRepair.getTime());
//			System.out.println(historyRepair.getAccount().getUsername());
			em.persist(historyRepair);
		}
	}
	
}
