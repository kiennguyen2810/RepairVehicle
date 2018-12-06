package com.nuce.repairvehiclemap.controller.admincontroller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nuce.repairvehiclemap.model.Service;
import com.nuce.repairvehiclemap.model.Shop;
import com.nuce.repairvehiclemap.service.ShopServ;

@Controller
@RequestMapping("/admin")
public class ShopAdminController {
	@Autowired
	ShopServ shopServ;
	
	@Autowired
	HttpSession httpSession;
	
	@RequestMapping("/shop")
	public String shop(Model model){
		if(httpSession.getAttribute("role").equals("ROLE_ADMIN")){
			model.addAttribute("username", httpSession.getAttribute("username"));
			return "admin/shop";
		} else{
			return "redirect:/admin/login";
		}
	}
	
	@RequestMapping("/shopdetail/{id}")
	public String shopdetailedit(@PathVariable("id") Integer id, Model model){
		if(httpSession.getAttribute("role").equals("ROLE_ADMIN")){
			model.addAttribute("username", httpSession.getAttribute("username"));
			Shop shop = shopServ.getShopById(id);
			model.addAttribute("shop", shop);
			List<Integer> idServices = new ArrayList<Integer>();
			for (Service service : shop.getServices()) {
				idServices.add(service.getId());
			}
			model.addAttribute("idservices", idServices);
			return "admin/shopdetail";
		} else{
			return "redirect:/admin/login";
		}
	}
	
	@RequestMapping("/shopdetail/")
	public String shopdetail(Model model){
		if(httpSession.getAttribute("role").equals("ROLE_ADMIN")){
			model.addAttribute("username", httpSession.getAttribute("username"));
			Shop shop = new Shop();
			model.addAttribute("shop", shop);
			return "admin/shopdetail";
		} else{
			return "redirect:/admin/login";
		}
	}
	
	
	//admin
	@PostMapping("/addshop")
	public ResponseEntity<?> addShop(@RequestBody Shop shop){
		shopServ.addShop(shop);
		return ResponseEntity.accepted().build();
	}
	
	//admin
	@PutMapping("/updateshop/{id}")
	public ResponseEntity<?> updateShop(@PathVariable("id") Integer id, @RequestBody Shop shop){
		shopServ.updateShop(id, shop);
		return ResponseEntity.accepted().build();
	}
	
	//admin
	@DeleteMapping("/deleteshop/{id}")
	public ResponseEntity<?> deleteShop(@PathVariable("id") Integer id){
		shopServ.deleteShop(id);
		return ResponseEntity.accepted().build();
	}
	
	//admin
	@GetMapping("/getAllShop")
		public ResponseEntity<?> getAllShop(){
			List<Shop> shops = shopServ.getAllShop();
			return ResponseEntity.accepted().body(shops);
	}
	
	//admin
	@GetMapping("/searchShop")
		public ResponseEntity<?> searchShop(@RequestParam("shopname") String shopName, @RequestParam("address") String address, @RequestParam("phone") String phone ){
			List<Shop> shops = shopServ.searchShop(shopName, address, phone);
			return ResponseEntity.accepted().body(shops);
	}
}
