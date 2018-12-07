package com.nuce.repairvehiclemap.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nuce.repairvehiclemap.model.HistoryRepair;
import com.nuce.repairvehiclemap.service.HistoryRepairServ;


@Controller
@RequestMapping("/historyrepair")
public class HistoryRepairController {
	@Autowired
	HistoryRepairServ historyRepairServ;
	
	@Autowired
	HttpSession httpSession;
	
	@RequestMapping
	public String historyRepair(Model model) {
		if(httpSession.getAttribute("role").equals("ROLE_USER")){
			model.addAttribute("username", httpSession.getAttribute("username"));
			return "user/historyrepair";
		} else{
			return "redirect:/login";
		}
	}
	
	@PostMapping("/savehistoryrepair/{username}/{idshop}")
	public ResponseEntity<?> saveHistoryRepair(@PathVariable("username") String username, @PathVariable("idshop") Integer idShop, @RequestBody List<Integer> idServices){
		historyRepairServ.saveHistoryRepair(username, idShop, idServices);		
		return ResponseEntity.accepted().build();
	}
	
	@GetMapping("/gethistoryrepair/{username}")
	public ResponseEntity<?> gethistoryrepair(@PathVariable("username") String userName){
		List<HistoryRepair> historyRepairs = historyRepairServ.getHistoryRepair(userName);
		Collections.sort(historyRepairs, Comparator.comparingInt(HistoryRepair::getId));
		Collections.reverse(historyRepairs);
		return ResponseEntity.accepted().body(historyRepairs);
	}
	
	@GetMapping("/gethistoryrepair")
	public ResponseEntity<?> searchHistoryrepair(@RequestParam("username") String userName, @RequestParam("textsearch") String textSearch, @RequestParam("date") String date){
		List<HistoryRepair> historyRepairs = historyRepairServ.searchHistoryRepair(userName, textSearch, date);
		Collections.sort(historyRepairs, Comparator.comparingInt(HistoryRepair::getId));
		Collections.reverse(historyRepairs);
		return ResponseEntity.accepted().body(historyRepairs);
	}
}
