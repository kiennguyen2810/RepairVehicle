package com.nuce.repairvehiclemap.controller.admincontroller;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nuce.repairvehiclemap.model.Account;
import com.nuce.repairvehiclemap.service.AccountServ;

@Controller
@RequestMapping("/admin")
public class AccountAdminController {

	@Autowired
	AccountServ accountServ;
	
	@Autowired
	HttpSession httpSession;

	@RequestMapping("/login")
	public String loginform() {
		return "admin/login";
	}
	
	@RequestMapping(value = {"/", "/index"})
	public String index(Model model){
		if(httpSession.getAttribute("role")!=null){
			if(httpSession.getAttribute("role").equals("ROLE_ADMIN")){
				model.addAttribute("username", httpSession.getAttribute("username"));
				return "admin/home";
			} else{
				return "redirect:/admin/login";
			}
		} else {
			return "redirect:/admin/login";
		}
	}
	
	@RequestMapping("/account")
	public String account(Model model){
		if(httpSession.getAttribute("role").equals("ROLE_ADMIN")){
			model.addAttribute("username", httpSession.getAttribute("username"));
			return "admin/account";
		} else{
			return "redirect:/admin/login";
		}
	}
	
	@PostMapping("/checklogin")
	public String login(@RequestParam(required = false) String username,
			@RequestParam(required = false) String password) {
		Account account = new Account();
		account.setUsername(username);
		account.setPassword(password);
		if (accountServ.checkLogin(account)) {
			if (accountServ.getAccountByUserName(username).getRole()
					.equals("ROLE_ADMIN")) {
				httpSession.setAttribute("username", account.getUsername());
				httpSession.setAttribute("role", "ROLE_ADMIN");
				return "redirect:/admin/index";
			} else
				return "redirect:/admin/login";

		} else
			return "redirect:/admin/login";
	}
	
	
	@PostMapping("/logout")
	public String logout(){
		httpSession.setAttribute("role", null);
		return "redirect:/admin/login";
	}
	

	@PostMapping("/register")
	@ResponseBody
	public ResponseEntity<Account> register(@RequestBody Account account)
			throws Exception {
		Boolean result = accountServ.checkAccountExist(account);
		HttpHeaders headers = new HttpHeaders();
		if (!result) {
			// create acc
			accountServ.createAccountAdmin(account);
			headers.add("Result", "Success");
		} else {
			// acc exist
			headers.add("Result", "Account existed");
		}

		return ResponseEntity.accepted().headers(headers).build();
	}
	
	@GetMapping("/getaccountuser")
	@ResponseBody
	public ResponseEntity<Set<Account>> getAccountUser(){
		if(httpSession.getAttribute("role").equals("ROLE_ADMIN")){
			Set<Account> accounts = new HashSet<Account>();
			accounts = accountServ.getAccountUser();
			for (Account account : accounts) {
				account.setHistoryRepairs(null);
			}
			return ResponseEntity.accepted().body(accounts);
		} else{
			return null;
		}
	}
	
	@GetMapping("/searchaccountuser")
	@ResponseBody
	public ResponseEntity<List<Account>> searchAccountUser(@RequestParam("username") String username, @RequestParam("name") String name, @RequestParam("email") String email ){
		if(httpSession.getAttribute("role").equals("ROLE_ADMIN")){
			List<Account> accounts =accountServ.searchAccountUser(username, name, email);
			return ResponseEntity.accepted().body(accounts);
		} else{
			return null;
		}
	}
	
	@DeleteMapping("/deleteaccount/{id}")
	public ResponseEntity<Set<Account>> deleteAccountUser(@PathVariable Integer id){
		if(httpSession.getAttribute("role").equals("ROLE_ADMIN")){
			accountServ.deleteAccountUser(id);
			return ResponseEntity.accepted().build();
		} else{
			return null;
		}
	}
	
}
