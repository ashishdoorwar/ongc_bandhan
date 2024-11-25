package com.ongc.liferay.bandhan.model;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

public class User {
	private String cpfNo;
	private String userName;
    private String tittle;
	private String password;
	private String location;
	private String address;
	private String communicatedAddress;
	private String designation;
	private String mobileNo;
	private String emailId;
	private String panNumber;
	private String aboutMe;
	private Date dateOfJoining;
	private Date dateOfBirth;
	private Date dateOfSep;
	private String createdBy;
	private byte[] profilePic;
	private Date creationDate;
	private int active;
	private File userPic;
	private String landLineNo;
	
	
	
	
	
	
	public String getCommunicatedAddress() {
		return communicatedAddress;
	}
	public void setCommunicatedAddress(String communicatedAddress) {
		this.communicatedAddress = communicatedAddress;
	}
	public String getLandLineNo() {
		return landLineNo;
	}
	public void setLandLineNo(String landLineNo) {
		this.landLineNo = landLineNo;
	}
	public File getUserPic() {
		return userPic;
	}
	public void setUserPic(File userPic) {
		this.userPic = userPic;
	}
	public byte[] getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getTittle() {
		return tittle;
	}
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCpfNo() {
		return cpfNo;
	}
	public void setCpfNo(String cpfNo) {
		this.cpfNo = cpfNo;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
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
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public String getAboutMe() {
		return aboutMe;
	}
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	public Date getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Date getDateOfSep() {
		return dateOfSep;
	}
	public void setDateOfSep(Date dateOfSep) {
		this.dateOfSep = dateOfSep;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Override
	public String toString() {
		return "User [cpfNo=" + cpfNo + ", userName=" + userName + ", tittle=" + tittle + ", password=" + password
				+ ", location=" + location + ", address=" + address + ", communicatedAddress=" + communicatedAddress
				+ ", designation=" + designation + ", mobileNo=" + mobileNo + ", emailId=" + emailId + ", panNumber="
				+ panNumber + ", aboutMe=" + aboutMe + ", dateOfJoining=" + dateOfJoining + ", dateOfBirth="
				+ dateOfBirth + ", dateOfSep=" + dateOfSep + ", createdBy=" + createdBy + ", profilePic="
				+ Arrays.toString(profilePic) + ", creationDate=" + creationDate + ", active=" + active + ", userPic="
				+ userPic + ", landLineNo=" + landLineNo + "]";
	}
	public String getEmpName() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getCurrentPlace() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
