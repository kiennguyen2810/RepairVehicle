package com.nuce.repairvehiclemap.dao;

import java.util.List;
import java.util.Set;

import com.nuce.repairvehiclemap.model.Account;

public interface AccountDao {
	Boolean checkAccountExist(Account account);
	void createAccount(Account account);
	Account getAccountByUserName(String username);
	Set<Account> getAllAccount();
	void createAccountAdmin(Account account);
	Set<Account> getAccountUser();
	void deleteAccountUser(Integer id);
	List<Account> searchAccountUser(String username, String name, String email);
	void saveAccount(Account account2);
}
