package com.nuce.repairvehiclemap.service;

import java.util.List;
import java.util.Set;

import com.nuce.repairvehiclemap.model.Account;

public interface AccountServ {
	Boolean checkAccountExist(Account account);
	void createAccount(Account account);
	boolean checkLogin(Account account);
	Account getAccountByUserName(String username);
	void createAccountAdmin(Account account);
	Set<Account> getAccountUser();
	void deleteAccountUser(Integer id);
	List<Account> searchAccountUser(String username, String name, String email);
	void saveAccount(Account account);
	void changePassword(String username, String newpassword);

}
