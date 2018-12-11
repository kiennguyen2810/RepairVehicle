package com.nuce.repairvehiclemap.controller.admincontroller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nuce.repairvehiclemap.model.Account;
import com.nuce.repairvehiclemap.service.AccountServ;

@RestController
public class SimpleMailController {
	@Autowired
	JavaMailSender sender;
	
	@Autowired
	AccountServ accountServ;

	@PostMapping("/resetpass")
	public ResponseEntity<?> sendMail(@RequestBody Account account) {
		HttpHeaders headers = new HttpHeaders();
		if(!accountServ.checkAccountExist(account)){
			headers.add("Result", "Account not registered!");
			return ResponseEntity.accepted().headers(headers).build();
		}
		
		Account account2 = accountServ.getAccountByUserName(account.getUsername());
		if (account2.getRole().equals("ROLE_ADMIN")) {
			headers.add("Result", "Account of the administrator!");
			return ResponseEntity.accepted().headers(headers).build();
		}
		
		if(account.getEmail().equals(account2.getEmail())){
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			String newPass = randomString(8);
			try {
				helper.setTo(account.getEmail());
				helper.setSubject("Change Password");
				helper.setText("New password: " + newPass);
			} catch (MessagingException e) {
				e.printStackTrace();
				headers.add("Result", "Error while sending mail .. Please come back later!");
				return ResponseEntity.accepted().headers(headers).build();
			}
			try {
				sender.send(message);
			} catch (Exception e) {
				headers.add("Result", "Error while sending mail .. Please come back later!");
				return ResponseEntity.accepted().headers(headers).build();
			}
			account2.setPassword(newPass);
			accountServ.saveAccount(account2);
			headers.add("Result", "Please check email and get new password!");
			return ResponseEntity.accepted().headers(headers).build();
		} else {
			headers.add("Result", "Email addresses don't match!");
			return ResponseEntity.accepted().headers(headers).build();
		}
	}

	public String randomString(int count){
		final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
		int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
		builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

}
