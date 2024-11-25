package com.ongc.liferay.bandhan.model;

import com.ongc.liferay.bandhan.model.User;

import java.util.Date;


public class FeedbackPost {

	private int postId;
	private int categoryId;
	private int categId;
	private FeedbackCategory category;
	private User user;
	private String subject;
	private String message;
	private Date postDate;
	private int noOfReplies;
	private String captchaVal;
    private String status;
	private String postDateText;
	private Date srt_date;
	private String lastUpdName;
	private String auth_status;	
	private String startDate;
	private String endDate;
	private String opStatus;
	private String rvStatus;
	private String revertReason;
	private int hrCatId;
	private String chiefEr;	
	private int pageId;
	private int workdays;
	private String color;
	private String pstdate;
	private String cmmntdate;
	private String cellNumber;
	
	public String getCellNumber() {
		return cellNumber;
	}
	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}
	public int getPageId() {
		return pageId;
	}
	public void setPageId(int pageId) {
		this.pageId = pageId;
	}
	public String getPostDateText() {
		return postDateText;
	}
	public String getOpStatus() {
		return opStatus;
	}
	public void setOpStatus(String opStatus) {
		this.opStatus = opStatus;
	}
	public String getRvStatus() {
		return rvStatus;
	}
	public void setRvStatus(String rvStatus) {
		this.rvStatus = rvStatus;
	}
	public void setPostDateText(String postDateText) {
		this.postDateText = postDateText;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getSrt_date() {
		return srt_date;
	}
	public void setSrt_date(Date srt_date) {
		this.srt_date = srt_date;
	}
	
	public String getCaptchaVal() {
		return captchaVal;
	}
	public void setCaptchaVal(String capthchaVal) {
		this.captchaVal = capthchaVal;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	
	public int getCategId() {
		return categId;
	}
	public void setCategId(int categId) {
		this.categId = categId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public FeedbackCategory getCategory() {
		return category;
	}
	public void setCategory(FeedbackCategory category) {
		this.category = category;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public int getNoOfReplies() {
		return noOfReplies;
	}
	public void setNoOfReplies(int noOfReplies) {
		this.noOfReplies = noOfReplies;
	}
	public void setLastUpdName(String lastUpdName) {
		this.lastUpdName=lastUpdName;
		
	}
	public String getLastUpdName() {
		return lastUpdName;
	}
	public void setAuth_status(String auth_status) {
		this.auth_status=auth_status;
		
	}
	public String getAuth_status() {
		return auth_status;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getRevertReason() {
		return revertReason;
	}
	public void setRevertReason(String revertReason) {
		this.revertReason = revertReason;
	}
	public int getHrCatId() {
		return hrCatId;
	}
	public void setHrCatId(int hrCatId) {
		this.hrCatId = hrCatId;
	}
	public String getChiefEr() {
		return chiefEr;
	}
	public void setChiefEr(String chiefEr) {
		this.chiefEr = chiefEr;
	}
	public int getWorkdays() {
		return workdays;
	}
	public void setWorkdays(int workdays) {
		this.workdays = workdays;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getPstdate() {
		return pstdate;
	}
	public void setPstdate(String pstdate) {
		this.pstdate = pstdate;
	}
	public String getCmmntdate() {
		return cmmntdate;
	}
	public void setCmmntdate(String cmmntdate) {
		this.cmmntdate = cmmntdate;
	}
	@Override
	public String toString() {
		return "FeedbackPost [postId=" + postId + ", categoryId=" + categoryId + ", categId=" + categId + ", category="
				+ category + ", user=" + user + ", subject=" + subject + ", message=" + message + ", postDate="
				+ postDate + ", noOfReplies=" + noOfReplies + ", captchaVal=" + captchaVal + ", status=" + status
				+ ", postDateText=" + postDateText + ", srt_date=" + srt_date + ", lastUpdName=" + lastUpdName
				+ ", auth_status=" + auth_status + ", startDate=" + startDate + ", endDate=" + endDate + ", opStatus="
				+ opStatus + ", rvStatus=" + rvStatus + ", revertReason=" + revertReason + ", hrCatId=" + hrCatId
				+ ", chiefEr=" + chiefEr + ", pageId=" + pageId + ", workdays=" + workdays + ", color=" + color
				+ ", pstdate=" + pstdate + ", cmmntdate=" + cmmntdate + ", cellNumber=" + cellNumber + "]";
	}
	
	
	
}
