package com.project.bean;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ClientDetails {

	@Id
	private String ipAdd;
	private String clientName;
	private String accessValid;
	
	public String getIp() {
		return ipAdd;
	}
	public void setIp(String ip) {
		this.ipAdd = ip;
	}
	public String getName() {
		return clientName;
	}
	public void setName(String name) {
		this.clientName = name;
	}
	public String getAccess() {
		return accessValid;
	}
	public void setAccess(String access) {
		this.accessValid = access;
	}
	public ClientDetails(String ip, String name, String access) {
		super();
		this.ipAdd = ip;
		this.clientName = name;
		this.accessValid = access;
	}
	public ClientDetails()
	{
		super();
	}
	
	
}
