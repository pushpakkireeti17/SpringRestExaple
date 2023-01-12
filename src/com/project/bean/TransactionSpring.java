package com.project.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TransactionSpring {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int trans_id;
	private String user_id;
	private String transtype;
	private double creditamount;
	private double debitamount;
	private String balance;
	private String time;
	
	public int getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(int trans_id) {
		this.trans_id = trans_id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTranstype() {
		return transtype;
	}
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}
	public double getCreditamount() {
		return creditamount;
	}
	public void setCreditamount(double creditamount) {
		this.creditamount = creditamount;
	}
	public double getDebitamount() {
		return debitamount;
	}
	public void setDebitamount(double debitamount) {
		this.debitamount = debitamount;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public TransactionSpring() {
		super();
	}
	public TransactionSpring(String username, String transtype, double creditamount, double debitamount, String balance,String time) {
		super();
		this.user_id = username;
		this.transtype = transtype;
		this.creditamount = creditamount;
		this.debitamount = debitamount;
		this.balance = balance;
		this.time=time;
	}
	
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String toString()
	{
		return user_id+"\t\t"+transtype+"\t"+creditamount+"\t"+debitamount+"\t"+balance+"\t"+time+"\n";
	}

}
