package com.nuce.repairvehiclemap.controller.admincontroller;

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

import com.nuce.repairvehiclemap.model.Service;
import com.nuce.repairvehiclemap.service.ServiceServ;

@Controller
@RequestMapping("/admin")
public class ServiceAdminController {

	@Autowired
	ServiceServ serviceServ;

	@Autowired
	HttpSession httpSession;

	@RequestMapping("/service")
	public String account(Model model) {
		if (httpSession.getAttribute("role").equals("ROLE_ADMIN")) {
			model.addAttribute("username", httpSession.getAttribute("username"));
			return "admin/service";
		} else {
			return "redirect:/admin/login";
		}
	}
	
	@GetMapping("/getservice/{serviceName}")
	public ResponseEntity<List<Service>> getServiceByName(@PathVariable("serviceName") String serviceName) throws Exception {
		List<Service> services;
		services = serviceServ.getServiceByName(serviceName);
		return ResponseEntity.accepted().body(services);
	}
	
	
	@GetMapping("/getservice")
	public ResponseEntity<List<Service>> getService() throws Exception {
		List<Service> services;
		services = serviceServ.getService();
		return ResponseEntity.accepted().body(services);
	}

	// admin
	@PostMapping("/addservice")
	public ResponseEntity<?> addService(@RequestBody Service service) {
		if (httpSession.getAttribute("account") != null) {
			serviceServ.addService(service);
			return ResponseEntity.accepted().build();
		} else {
			return null;
		}

	}

	// admin
	@PutMapping("/updateservice/{id}")
	public ResponseEntity<?> updateService(@PathVariable("id") Integer id,
			@RequestBody Service service) {
		if (httpSession.getAttribute("account") != null) {
			System.out.println(service.getName());
			System.out.println(service.getPrice());
			serviceServ.updateService(id, service);
			return ResponseEntity.accepted().build();
		} else {
			return null;
		}
	}

	// admin
	@DeleteMapping("/deleteservice/{id}")
	public ResponseEntity<?> deleteService(@PathVariable("id") Integer id) {
		if (httpSession.getAttribute("account") != null) {
			serviceServ.deleteService(id);
			return ResponseEntity.accepted().build();
		} else {
			return null;
		}
		
		
		
	}

}
