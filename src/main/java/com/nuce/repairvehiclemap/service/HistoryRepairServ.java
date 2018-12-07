package com.nuce.repairvehiclemap.service;

import java.util.List;

import com.nuce.repairvehiclemap.model.HistoryRepair;

public interface HistoryRepairServ {
	List<HistoryRepair> getHistoryRepair(String userName);

	void saveHistoryRepair(String username, Integer idShop, List<Integer> idServices);

	List<HistoryRepair> searchHistoryRepair(String userName, String textSearch, String date);

}
