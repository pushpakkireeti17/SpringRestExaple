package com.project.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class WalletSpring {
	@NotEmpty
	private String name;
	private long mobile_number;
	private String email;
	private double amount;
	@Id
	private String user_name;
	//private String password;
	private long Account_number;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getMobile_number() {
		return mobile_number;
	}
	public void setMobile_number(long mobile_number) {
		this.mobile_number = mobile_number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	/*public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}*/
	public long getAccount_number() {
		return Account_number;
	}
	public void setAccount_number(long account_number) {
		this.Account_number = account_number;
	}
	public WalletSpring() {
		super();
	}
	public WalletSpring(String name, long mobile_number, String email, double amount,long account_number, String user_name/*, String password*/
			) {
		super();
		this.name = name;
		this.mobile_number = mobile_number;
		this.email = email;
		this.amount = amount;
		this.user_name = user_name;
		//this.password = password;
		this.Account_number = account_number;
	}
	
	
}


