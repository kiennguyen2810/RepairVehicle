package com.nuce.repairvehiclemap.dao;

import java.util.List;

import com.nuce.repairvehiclemap.model.Account;
import com.nuce.repairvehiclemap.model.Service;
import com.nuce.repairvehiclemap.model.Shop;

public interface HistoryRepairDao {

	void saveHistoryRepair(Account account, Shop shop, List<Service> services);

}
