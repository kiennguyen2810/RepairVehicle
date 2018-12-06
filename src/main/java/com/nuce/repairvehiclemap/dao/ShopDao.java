package com.nuce.repairvehiclemap.dao;

import java.util.List;

import com.nuce.repairvehiclemap.model.Shop;

public interface ShopDao {
	List<Shop> getShopAround(List<Integer> idService, Double latitude1, Double latitude2, Double longitude1,
			Double longitude2);
	void addShop(Shop shop);
	void updateShop(Integer id, Shop shop);
	void deleteShop(Integer id);
	Shop getShopById(Integer id);
	List<Shop> getAllShop();
	List<Shop> searchShop(String shopName, String address, String phone);
}
