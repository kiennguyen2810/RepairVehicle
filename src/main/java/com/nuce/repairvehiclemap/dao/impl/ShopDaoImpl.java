package com.nuce.repairvehiclemap.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nuce.repairvehiclemap.dao.ShopDao;
import com.nuce.repairvehiclemap.model.Service;
import com.nuce.repairvehiclemap.model.Shop;

@Repository
@Transactional
public class ShopDaoImpl implements ShopDao {

	@PersistenceContext
	EntityManager em;

	@Override
	public List<Shop> getShopAround(List<Integer> idServices,
			Double latitude1, Double latitude2, Double longitude1,
			Double longitude2) {
		String sql = "FROM Shop WHERE (latitude BETWEEN :latitude1 AND :latitude2) AND (longitude BETWEEN :longitude1 AND :longitude2) ";
		Query query = em.createQuery(sql);
		query.setParameter("latitude1", latitude1);
		query.setParameter("latitude2", latitude2);
		query.setParameter("longitude1", longitude1);
		query.setParameter("longitude2", longitude2);
		List<Shop> shops = query.getResultList(); // get list shop around
		List<Service> services = new ArrayList<Service>();
		for (Integer id : idServices) {
			Service service = em.find(Service.class, id); // get Service by
															// idService
			services.add(service);
		}

		List<Shop> shops2 = new ArrayList<Shop>();
		if (!services.isEmpty()) {
			for (Shop shop : shops) {
				if (shop.getServices().containsAll(services)) {
					shops2.add(shop);
				}
			}

		}

		return shops2;
	}

	@Override
	public void addShop(Shop shop) {
		Set<Service> services = new HashSet<Service>();
		for (Service service : shop.getServices()) {
			services.add(em.find(Service.class, service.getId()));
		}
		shop.setServices(services);
		em.merge(shop);
	}

	@Override
	public void updateShop(Integer id, Shop shop) {
		Shop shop2 = em.find(Shop.class, id);
		shop2.setAddress(shop.getAddress());
		shop2.setImage(shop.getImage());
		shop2.setOpenTime(shop.getOpenTime());
		shop2.setLatitude(shop.getLatitude());
		shop2.setLongitude(shop.getLongitude());
		shop2.setName(shop.getName());
		shop2.setPhone(shop.getPhone());
		shop2.setServices(shop.getServices());
	}

	@Override
	public void deleteShop(Integer id) {
		em.remove(em.find(Shop.class, id));
	}

	@Override
	public Shop getShopById(Integer id) {
		Shop shop = em.find(Shop.class, id);
		return shop;
	}

	@Override
	public List<Shop> getAllShop() {
		String sql = "from Shop";
		Query query = em.createQuery(sql);
		List<Shop> shops = query.getResultList();
		return shops;
	}

	@Override
	public List<Shop> searchShop(String shopName, String address, String phone) {
		StringBuilder sql = new StringBuilder();
		sql.append("FROM Shop WHERE 1=1 ");
		
		if(!shopName.equals(null) && !shopName.equals("")) {
			sql.append(" AND name LIKE :shopName ");
		}
		
		if(!address.equals(null) && !address.equals("")) {
			sql.append(" AND address LIKE :address ");
		}
		
		if(!phone.equals(null) && !phone.equals("")) {
			sql.append(" AND phone LIKE :phone  ");
		}
		Query query = em.createQuery(sql.toString());
		if(!shopName.equals(null) && !shopName.equals("")) {
			query.setParameter("shopName", "%" + shopName + "%" );
		}
		
		if(!address.equals(null) && !address.equals("")) {
			query.setParameter("address", "%" + address + "%" );
		}
		
		if(!phone.equals(null) && !phone.equals("")) {
			query.setParameter("phone", "%" + phone + "%" );
		}
		
		List<Shop> shops = query.getResultList();
		return shops;
	}

}
