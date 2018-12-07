package com.nuce.repairvehiclemap.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "account")
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = { "role", "authorities" })
//@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank
	@NotNull
	@Size(max = 15)
	@Column(unique = true)
	private String username;

	@NotBlank
	@NotNull
	@Size(max = 45)
	private String password;

	
	@Size(max = 45)
	private String role;;


	@Size(max = 45)
	private String name;


	@Size(max = 15)
	private String phone;


	@Size(max = 45)
	private String email;

	@OneToMany(mappedBy = "account",fetch = FetchType.EAGER,  cascade = CascadeType.PERSIST)
	@JsonIgnoreProperties("account")
	private List<HistoryRepair> historyRepairs;

//	@Column(nullable = false, updatable = false)
//	@Temporal(TemporalType.TIMESTAMP)
//	@CreatedDate
//	private Date createdAt;
//
//	@Column(nullable = false)
//	@Temporal(TemporalType.TIMESTAMP)
//	@LastModifiedDate
//	private Date updatedAt;

//	@Column(name = "created_at")
//	@CreationTimestamp
//	private Date createdAt;
//
//	@Column(name = "updated_at")
//	@UpdateTimestamp
//	private Date updatedAt;
	
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<HistoryRepair> getHistoryRepairs() {
		return historyRepairs;
	}

	public void setHistoryRepairs(List<HistoryRepair> historyRepairs) {
		this.historyRepairs = historyRepairs;
	}

}
