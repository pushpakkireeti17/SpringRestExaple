package com.project.bean;

public class ValidationCardDetails {

	private int cardnumber;
	private String cardname;
	private int expmonth;
	private int expyear;
	private int cvv;
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
	public ValidationCardDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ValidationCardDetails(int cardnumber, String cardname, int expmonth, int expyear, int cvv) {
		super();
		this.cardnumber = cardnumber;
		this.cardname = cardname;
		this.expmonth = expmonth;
		this.expyear = expyear;
		this.cvv = cvv;
	}
	
}
