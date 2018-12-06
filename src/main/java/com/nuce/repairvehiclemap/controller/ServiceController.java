package com.nuce.repairvehiclemap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nuce.repairvehiclemap.model.Service;
import com.nuce.repairvehiclemap.service.ServiceServ;

@RestController
@RequestMapping("/service")
public class ServiceController {
	@Autowired
	ServiceServ serviceServ;
	
	
	@GetMapping("/getservice")
	public ResponseEntity<List<Service>> getService() throws Exception {
		List<Service> services;
		services = serviceServ.getService();
		return ResponseEntity.accepted().body(services);
	}
	
	
	
}
