package com.nuce.repairvehiclemap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuce.repairvehiclemap.dao.ShopDao;
import com.nuce.repairvehiclemap.model.Shop;
import com.nuce.repairvehiclemap.service.ShopServ;

@Service
public class ShopServImpl implements ShopServ {
	@Autowired
	ShopDao shopDao;

	@Override
	public List<Shop> getShopAround(List<Integer> idServices, Double latitude, Double longitude) {
		List<Shop> shops;
		//Ban kinh 5.55km
		Double latitude1 = latitude - 0.025;
		Double latitude2 = latitude + 0.025;
		Double longitude1 = longitude - 0.025;
		Double longitude2 = longitude + 0.025;
		
		shops = shopDao.getShopAround(idServices, latitude1, latitude2, longitude1, longitude2);
		return shops;
	}

	@Override
	public void addShop(Shop shop) {
		shopDao.addShop(shop);
	}

	@Override
	public void updateShop(Integer id, Shop shop) {
		shopDao.updateShop(id, shop);
	}

	@Override
	public void deleteShop(Integer id) {
		shopDao.deleteShop(id);
	}

	@Override
	public List<Shop> getAllShop() {
		return shopDao.getAllShop();
	}

	@Override
	public Shop getShopById(Integer id) {
		// TODO Auto-generated method stub
		return shopDao.getShopById(id);
	}

	@Override
	public List<Shop> searchShop(String shopName, String address, String phone) {
		
		return shopDao.searchShop(shopName,address, phone);
	}

}
