package com.nuce.repairvehiclemap.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nuce.repairvehiclemap.model.Account;
import com.nuce.repairvehiclemap.service.AccountServ;



@Controller
@RequestMapping
public class AccountController {
	@Autowired
	AccountServ accountServ;
	
	@Autowired
	HttpSession httpSession;
	
//	@Autowired
//	private JwtService jwtService;
	
	@RequestMapping("/userprofile")
	public String userProfile(Model model) {
		if(httpSession.getAttribute("role").equals("ROLE_USER")){
			model.addAttribute("username", httpSession.getAttribute("username"));
			return "user/userprofile";
		} else{
			return "redirect:/login";
		}
	}
	
	@RequestMapping("/changepassword")
	public String changePassword(Model model) {
		if(httpSession.getAttribute("role").equals("ROLE_USER")){
			model.addAttribute("username", httpSession.getAttribute("username"));
			return "user/changepassword";
		} else{
			return "redirect:/login";
		}
	}
	
	@RequestMapping("/update")
	public String update(Model model) {
		if(httpSession.getAttribute("role").equals("ROLE_USER")){
			model.addAttribute("username", httpSession.getAttribute("username"));
			return "user/updateprofile";
		} else{
			return "redirect:/login";
		}
	}
	
	@PutMapping("/saveprofile")
	public ResponseEntity<String> saveProfile(@RequestBody Account account){
		accountServ.saveAccount(account);
		return ResponseEntity.accepted().body("Update Successfully");
	}
	
	@PutMapping("/savepassword/{username}/{currentpassword}/{newpassword}")
	public ResponseEntity<String> savePassword(@PathVariable String username, @PathVariable String currentpassword, @PathVariable String newpassword){
		Account account = new Account();
		account.setUsername(username);
		account.setPassword(currentpassword);
		if(accountServ.checkLogin(account)) {
			accountServ.changePassword(username, newpassword);
			httpSession.setAttribute("role", null);
			return ResponseEntity.accepted().body("Change Password Successfully");
		} else {
			return ResponseEntity.accepted().body("Current Password Wrong!");
		}
	}
	
	
	@GetMapping("/getaccount/{username}")
	public ResponseEntity<Account> getProfile(@PathVariable String username){
		Account account = accountServ.getAccountByUserName(username);
		Account account2 = new Account();
		account2.setName(account.getName());
		account2.setEmail(account.getEmail());
		account2.setPhone(account.getPhone());
		return ResponseEntity.accepted().body(account2);
	}
	
	
	@RequestMapping("/login")
	public String loginform(Model model) {
		model.addAttribute("message", httpSession.getAttribute("message"));
		httpSession.setAttribute("message", null);
		return "user/login";
	}
	
	@RequestMapping("/registerform")
	public String registerform() {
		return "user/register";
	}
	
	
	@RequestMapping(value = {"/", "/home"})
	public String home(Model model) {
		if(httpSession.getAttribute("role")!=null){
			if(httpSession.getAttribute("role").equals("ROLE_USER")){
				model.addAttribute("username", httpSession.getAttribute("username"));
				return "user/trangchu";
			} else{
				return "redirect:/login";
			}
		} else{
			return "redirect:/login";
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
					.equals("ROLE_USER")) {
				account = accountServ.getAccountByUserName(username);
				httpSession.setAttribute("username", account.getUsername());
				httpSession.setAttribute("role", "ROLE_USER");
				return "redirect:/home";
			} else
				httpSession.setAttribute("message", "Account or Password wrong!");
				return "redirect:/login";
		} else
			httpSession.setAttribute("message", "Account or Password wrong!");
			return "redirect:/login";
	}
	
	@PostMapping("/logout")
	public String logout(){
		httpSession.setAttribute("role", null);
		return "redirect:/login";
	}
	
	
	
	
	@PostMapping("/register")
	public ResponseEntity<Account> register(@RequestBody Account account) throws Exception{
		Boolean result = accountServ.checkAccountExist(account);
		HttpHeaders headers = new HttpHeaders();
		if(!result){
			//create acc
			accountServ.createAccount(account);
			headers.add("Result", "Register Successfully");
		} else {
			// acc exist
			headers.add("Result", "Account existed");
		}
		
		return ResponseEntity.accepted().headers(headers).build();
	}
	
	
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public ResponseEntity<String> login(HttpServletRequest request, @RequestBody Account account) {
//		String result = "";
//		HttpStatus httpStatus = null;
//		try {
//			if (accountServ.checkLogin(account)) {
//				result = jwtService.generateTokenLogin(account.getUsername());
//				httpStatus = HttpStatus.OK;
//			} else {
//				result = "Wrong userId and password";
//				httpStatus = HttpStatus.BAD_REQUEST;
//			}
//		} catch (Exception ex) {
//			result = "Server Error";
//			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//		return new ResponseEntity<String>(result, httpStatus);
//	}
	
}
