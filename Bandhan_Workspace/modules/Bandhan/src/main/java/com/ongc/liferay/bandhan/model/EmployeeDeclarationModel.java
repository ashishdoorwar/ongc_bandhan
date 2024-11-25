package com.ongc.liferay.bandhan.model;

import java.io.File;
import java.util.Date;

public class EmployeeDeclarationModel {
	private int id;
	private String cpfNo;
	private String year;
	private String remark;
	private String employementStatus;
	private String createdDate;
	private String currentPlace;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCpfNo() {
		return cpfNo;
	}
	public void setCpfNo(String cpfNo) {
		this.cpfNo = cpfNo;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getEmployementStatus() {
		return employementStatus;
	}
	public void setEmployementStatus(String employementStatus) {
		this.employementStatus = employementStatus;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCurrentPlace() {
		return currentPlace;
	}
	public void setCurrentPlace(String currentPlace) {
		this.currentPlace = currentPlace;
	}

}
