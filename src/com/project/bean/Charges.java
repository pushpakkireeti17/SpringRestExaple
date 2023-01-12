package com.project.bean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
public class Charges {
	@Id
	private String transtype;
	private double transcharge;
	private String lastdate;
	private int freq;
	
	
	public String getTranstype() {
		return transtype;
	}
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}
	public double getTranscharge() {
		return transcharge;
	}
	public void setTranscharge(double transcharge) {
		this.transcharge = transcharge;
	}
	public String getLastdate() {
		return lastdate;
	}
	public void setLastdate(String lastdate) {
		this.lastdate = lastdate;
	}
	public Charges() {
		super();
	}
	public Charges(String transtype, double transcharge, String lastdate, int freq) {
		super();
		this.transtype = transtype;
		this.transcharge = transcharge;
		this.lastdate = lastdate;
		this.freq = freq;
	}
	public int getFreq() {
		return freq;
	}
	public void setFreq(int freq) {
		this.freq = freq;
	}
	

}
