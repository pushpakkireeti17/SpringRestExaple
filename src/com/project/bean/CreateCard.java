package com.project.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Createcard")
public class CreateCard {

	@Id
	private int cardnumber;
	private String cardname;
	private int expmonth;
	private int expyear;
	private int cvv;
	private String cust_id;

	public CreateCard() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CreateCard(int cardnumber, String cardname, int expmonth, int expyear, int cvv, String cust_id) {
		super();
		this.cardnumber = cardnumber;
		this.cardname = cardname;
		this.expmonth = expmonth;
		this.expyear = expyear;
		this.cvv = cvv;
		this.cust_id = cust_id;
	}

	public int getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(int cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getCardname() {
		return cardname;
	}

	public void setCardname(String cardname) {
		this.cardname = cardname;
	}

	public int getExpmonth() {
		return expmonth;
	}

	public void setExpmonth(int expmonth) {
		this.expmonth = expmonth;
	}

	public int getExpyear() {
		return expyear;
	}

	public void setExpyear(int expyear) {
		this.expyear = expyear;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

}
