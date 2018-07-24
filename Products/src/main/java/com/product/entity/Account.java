package com.product.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Accounts")
public class Account implements Serializable {
	
private static final long serialVersionUID = -2054386655979281969L;
	
	public static final String ROLE_MANAGER= "MANAGER";
	public static final String ROLE_EMPLOYEE= "EMPLOYEE";
	
	@Id
	@Column(name="User_Name" , nullable=false )
	private String userName;
	
	@Column(name="Password" , nullable=false )
	private String password;
	
	@Column(name="User_Role" , nullable=false )
	private String userRole;
	
	@Column(name="Active" , nullable=false )
	private boolean active;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Account [userName=" + userName + ", password=" + password + ", userRole=" + userRole + ", active="
				+ active + "]";
	}
	
	
	

	
	
	
	
	
	
}
