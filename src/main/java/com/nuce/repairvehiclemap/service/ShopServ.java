package com.nuce.repairvehiclemap.service;

import java.util.List;

import com.nuce.repairvehiclemap.model.Shop;

public interface ShopServ {
	List<Shop> getShopAround(List<Integer> idServices,Double latitude, Double longitude);
	void addShop(Shop shop);
	void updateShop(Integer id, Shop shop);
	void deleteShop(Integer id);
	List<Shop> getAllShop();
	Shop getShopById(Integer id);
	List<Shop> searchShop(String shopName, String address, String phone);
}
