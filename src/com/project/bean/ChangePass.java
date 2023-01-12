package com.project.bean;

public class ChangePass {
	private String username;
	private String oldpass;
	private String newpass;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOldpass() {
		return oldpass;
	}
	public void setOldpass(String oldpass) {
		this.oldpass = oldpass;
	}
	public String getNewpass() {
		return newpass;
	}
	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}
	public ChangePass(String username, String oldpass, String newpass) {
		super();
		this.username = username;
		this.oldpass = oldpass;
		this.newpass = newpass;
	}
	public ChangePass() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
