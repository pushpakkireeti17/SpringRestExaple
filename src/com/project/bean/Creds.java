package com.project.bean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.UniqueConstraint;

@Entity
public class Creds {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int Num;
	/*
	@OneToOne(targetEntity=WalletSpring.class)
	@JoinColumn(name="cust_id", referencedColumnName="user_name")*/
	//private String userId;
	private String userName;
	private String pass;
	private String custName;
	private long phone;
	
	
	public int getNum() {
		return Num;
	}
	public void setNum(int id) {
		Num = id;
	}
	/*public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}*/
	public String getUserName() {
		return userName;
	}
	public void setUserName(String user_name) {
		this.userName = user_name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String password) {
		this.pass = password;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long l) {
		this.phone = l;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String name) {
		this.custName = name;
	}
	
	
	
	
	public Creds() {
		super();
	}
	public Creds(int id, /*String userId,*/ String cust_name, long phone, String userName, String pass)
	{
		super();
		this.Num = id;
		//this.userId = userId;
		this.userName = userName;
		this.phone = phone;
		this.custName = cust_name;
		this.pass = pass;
	}
	public Creds (String user_name, String password)
	{
		super();
		this.userName = user_name;
		this.pass = password;
	}
	
}