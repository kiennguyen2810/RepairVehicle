package com.nuce.repairvehiclemap.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nuce.repairvehiclemap.dao.AccountDao;
import com.nuce.repairvehiclemap.model.Account;
import com.nuce.repairvehiclemap.model.HistoryRepair;
import com.nuce.repairvehiclemap.model.Shop;

@Repository
@Transactional
public class AccountDaoImpl implements AccountDao {

	@PersistenceContext
	EntityManager em;

	@Override
	public Boolean checkAccountExist(Account account) {
		String sql = "SELECT COUNT(username) FROM Account WHERE username = :username";
		Query query = em.createQuery(sql);
		query.setParameter("username", account.getUsername());
		Long result =  (Long) query.getSingleResult();
		//System.out.println(result);
		
		if(result == 0){
			return false;
		} else {
			return true;
		}	
	}

	@Override
	public void createAccount(Account account) {
		account.setRole("ROLE_USER");
		em.persist(account);
	}
	
	@Override
	public void createAccountAdmin(Account account) {
		account.setRole("ROLE_ADMIN");
		em.persist(account);
	}
	

	@Override
	public Account getAccountByUserName(String username) {
		String sql = "FROM Account WHERE username = :username";
		Query query = em.createQuery(sql);
		query.setParameter("username", username);
		Account account = (Account) query.getSingleResult();
		return account;
	}
	
	@Override
	public Set<Account> getAllAccount(){
		Set<Account> accounts = new HashSet<>();
		String sql = "FROM Account";
		Query query = em.createQuery(sql);
		List<Account> listaccounts = query.getResultList();
		accounts.addAll(listaccounts);
		return accounts;
	}

	@Override
	public Set<Account> getAccountUser() {
		Set<Account> accounts = new HashSet<>();
		String sql = "FROM Account WHERE role = :role";
		Query query = em.createQuery(sql);
		query.setParameter("role", "ROLE_USER");
		List<Account> listaccounts = query.getResultList();
		accounts.addAll(listaccounts);
		return accounts;
	}

	@Override
	public void deleteAccountUser(Integer id) {
		em.remove( em.find(Account.class, id));
	}

	@Override
	public List<Account> searchAccountUser(String username, String name, String email) {
		StringBuilder sql = new StringBuilder();
		sql.append("FROM Account WHERE role = :role ");
		
		if(!username.equals(null) && !username.equals("")) {
			sql.append(" AND username LIKE :username ");
		}
		
		if(!name.equals(null) && !name.equals("")) {
			sql.append(" AND name LIKE :name ");
		}
		
		if(!email.equals(null) && !email.equals("")) {
			sql.append(" AND email LIKE :email  ");
		}
		Query query = em.createQuery(sql.toString());
		query.setParameter("role", "ROLE_USER");
		if(!username.equals(null) && !username.equals("")) {
			query.setParameter("username", "%" + username + "%" );
		}
		
		if(!name.equals(null) && !name.equals("")) {
			query.setParameter("name", "%" + name + "%" );
		}
		
		if(!email.equals(null) && !email.equals("")) {
			query.setParameter("email", "%" + email + "%" );
		}
		
		List<Account> accounts = query.getResultList();
		return accounts;
	}

	@Override
	public void saveAccount(Account account2) {
		em.merge(account2);
	}

	
	
}
