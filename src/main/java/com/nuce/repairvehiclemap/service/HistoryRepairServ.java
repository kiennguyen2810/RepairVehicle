package com.nuce.repairvehiclemap.service;

import java.util.List;
import java.util.Set;

import com.nuce.repairvehiclemap.model.HistoryRepair;
import com.nuce.repairvehiclemap.model.Service;

public interface HistoryRepairServ {
	Set<HistoryRepair> getHistoryRepair(String userName);

	void saveHistoryRepair(String username, Integer idShop, List<Integer> idServices);

}
