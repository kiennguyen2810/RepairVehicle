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
		
		
		Account account2 = accountServ.getAccountByUserName(account.getUsername());
		if(account2 == null) {
			headers.add("Result", "Account not registered!");
			return ResponseEntity.accepted().headers(headers).build();
		} else if (account2.getRole().equals("ROLE_ADMIN")) {
			headers.add("Result", "Account of the administrator!");
			return ResponseEntity.accepted().headers(headers).build();
		}
		
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setTo("kiennguyen2810.nuce@gmail.com");
			helper.setText("Change pass :)");
			helper.setSubject("Doi mat khau sua xe lan 2");
		} catch (MessagingException e) {
			e.printStackTrace();
			headers.add("Result", "Error while sending mail .. Please come back later!");
			return ResponseEntity.accepted().headers(headers).build();
		}
		sender.send(message);
		headers.add("Result", "Mail Sent Success! Check your mail and get password!");
		return ResponseEntity.accepted().headers(headers).build();
	}



}
