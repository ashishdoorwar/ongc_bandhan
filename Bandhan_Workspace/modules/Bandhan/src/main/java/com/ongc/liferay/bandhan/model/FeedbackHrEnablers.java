package com.ongc.liferay.bandhan.model;

public class FeedbackHrEnablers {
	private String cpfNo;
	private String role;
	private int hrCatId;
	private String hrLocation;
	private String subLocation;
	private String emailId;
	private String mobileNo;
	
	public String getSubLocation() {
		return subLocation;
	}
	public void setSubLocation(String subLocation) {
		this.subLocation = subLocation;
	}
	public String getCpfNo() {
		return cpfNo;
	}
	public void setCpfNo(String cpfNo) {
		this.cpfNo = cpfNo;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getHrCatId() {
		return hrCatId;
	}
	public void setHrCatId(int hrCatId) {
		this.hrCatId = hrCatId;
	}
	public String getHrLocation() {
		return hrLocation;
	}
	public void setHrLocation(String hrLocation) {
		this.hrLocation = hrLocation;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	
}
