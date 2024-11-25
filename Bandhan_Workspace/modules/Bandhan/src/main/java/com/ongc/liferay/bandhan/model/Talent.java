package com.ongc.liferay.bandhan.model;

import java.util.Date;

public class Talent {
	private int    tid;
	private String cpfno;
	private String username;
	private String dob;
	private String qualification;	
	private String dos;
	private String designation;
	private String lastTenFrm;
	private String lastTenTo;
	private String department;
	private String skillSet;
	private String mobileNo;
	private String emailId;
	private String address;		
	private String willing;
	private String domain;	
	private String willingarea;
	private Date posteddt;
	private String status;
	
	
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
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
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getDos() {
		return dos;
	}
	public void setDos(String dos) {
		this.dos = dos;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getLastTenFrm() {
		return lastTenFrm;
	}
	public void setLastTenFrm(String lastTenFrm) {
		this.lastTenFrm = lastTenFrm;
	}
	public String getLastTenTo() {
		return lastTenTo;
	}
	public void setLastTenTo(String lastTenTo) {
		this.lastTenTo = lastTenTo;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getSkillSet() {
		return skillSet;
	}
	public void setSkillSet(String skillSet) {
		this.skillSet = skillSet;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWilling() {
		return willing;
	}
	public void setWilling(String willing) {
		this.willing = willing;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	
	
	
	public Date getPosteddt() {
		return posteddt;
	}
	public void setPosteddt(Date posteddt) {
		this.posteddt = posteddt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWillingarea() {
		return willingarea;
	}
	public void setWillingarea(String willingarea) {
		this.willingarea = willingarea;
	}
	@Override
	public String toString() {
		return "Talent [tid=" + tid + ", cpfno=" + cpfno + ", username=" + username + ", dob=" + dob
				+ ", qualification=" + qualification + ", dos=" + dos + ", designation=" + designation + ", lastTenFrm="
				+ lastTenFrm + ", lastTenTo=" + lastTenTo + ", department=" + department + ", skillSet=" + skillSet
				+ ", mobileNo=" + mobileNo + ", emailId=" + emailId + ", address=" + address + ", willing=" + willing
				+ ", domain=" + domain + ", willingarea=" + willingarea + ", posteddt=" + posteddt + ", status="
				+ status + "]";
	}
	
	
	
	
	
	
	
	
	
}
