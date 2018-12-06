package com.nuce.repairvehiclemap.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuce.repairvehiclemap.dao.AccountDao;
import com.nuce.repairvehiclemap.model.Account;
import com.nuce.repairvehiclemap.service.AccountServ;


@Service
public class AccountServImpl implements AccountServ {

	@Autowired
	AccountDao accountDao;

	@Override
	public Boolean checkAccountExist(Account account) {
		return accountDao.checkAccountExist(account);
	}

	@Override
	public void createAccount(Account account) {
		accountDao.createAccount(account);
	}
	
	@Override
	public boolean checkLogin(Account account) {
		Set<Account> accounts = accountDao.getAllAccount();
		for (Account accountExist : accounts) {
			if (StringUtils.equals(account.getUsername(), accountExist.getUsername())
					&& StringUtils.equals(account.getPassword(), accountExist.getPassword())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Account getAccountByUserName(String username) {
		return accountDao.getAccountByUserName(username);
	}

	@Override
	public void createAccountAdmin(Account account) {
		accountDao.createAccountAdmin(account);
		
	}

	@Override
	public Set<Account> getAccountUser() {
		return accountDao.getAccountUser();
	}

	@Override
	public void deleteAccountUser(Integer id) {
		accountDao.deleteAccountUser(id);
	}

	@Override
	public List<Account> searchAccountUser(String username, String name, String email) {
		return accountDao.searchAccountUser(username, name, email);
	}

	@Override
	public void saveAccount(Account account) {
		Account account2 = accountDao.getAccountByUserName(account.getUsername());
		account2.setName(account.getName());
		account2.setEmail(account.getEmail());
		account2.setPhone(account.getPhone());
		accountDao.saveAccount(account2);
	}


	@Override
	public void changePassword(String username, String newpassword) {
		Account account = accountDao.getAccountByUserName(username);
		account.setPassword(newpassword);
		accountDao.saveAccount(account);
	}
	
}
