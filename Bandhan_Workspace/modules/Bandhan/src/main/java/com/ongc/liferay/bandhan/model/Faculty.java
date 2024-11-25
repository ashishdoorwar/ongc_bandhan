package com.ongc.liferay.bandhan.model;

import java.util.Date;
import java.util.List;

public class Faculty {
	
	private int facid;
	private String cpfno;
	private String username;
	private String designation;
	private String dos;	
	private String mobileNo;
	private String emailId;
	private String faxno;
	private String address;
	private String city;
	private String specialisation;
	private String academics;
	private String superannuation;
	private String title;
	private String code;
	private String description;
	private Date posteddt;
	private String status;
		
	
	
	public int getFacid() {
		return facid;
	}
	public void setFacid(int facid) {
		this.facid = facid;
	}
	public String getCpfno() {
		return cpfno;
	}
	public void setCpfno(String cpfno) {
		this.cpfno = cpfno;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDos() {
		return dos;
	}
	public void setDos(String dos) {
		this.dos = dos;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getFaxno() {
		return faxno;
	}
	public void setFaxno(String faxno) {
		this.faxno = faxno;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSpecialisation() {
		return specialisation;
	}
	public void setSpecialisation(String specialisation) {
		this.specialisation = specialisation;
	}
	public String getAcademics() {
		return academics;
	}
	public void setAcademics(String academics) {
		this.academics = academics;
	}
	public String getSuperannuation() {
		return superannuation;
	}
	public void setSuperannuation(String superannuation) {
		this.superannuation = superannuation;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getPosteddt() {
		return posteddt;
	}
	public void setPosteddt(Date posteddt) {
		this.posteddt = posteddt;
	}
	@Override
	public String toString() {
		return "Faculty [facid=" + facid + ", cpfno=" + cpfno + ", username=" + username + ", designation="
				+ designation + ", dos=" + dos + ", mobileNo=" + mobileNo + ", emailId=" + emailId + ", faxno=" + faxno
				+ ", address=" + address + ", city=" + city + ", specialisation=" + specialisation + ", academics="
				+ academics + ", superannuation=" + superannuation + ", title=" + title + ", code=" + code
				+ ", description=" + description + ", posteddt=" + posteddt + ", status=" + status + "]";
	}
	
	
	
	
	
	
	
}
