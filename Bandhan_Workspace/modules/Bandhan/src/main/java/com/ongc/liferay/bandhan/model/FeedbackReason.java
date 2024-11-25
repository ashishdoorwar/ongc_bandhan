package com.ongc.liferay.bandhan.model;


public class FeedbackReason {
	private int postId;
	private int reasonId;
	private String reason;
	private String status;
	private String reasonOn;
	private User user;
	private int hrCatId;
	
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public int getReasonId() {
		return reasonId;
	}
	public void setReasonId(int reasonId) {
		this.reasonId = reasonId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReasonOn() {
		return reasonOn;
	}
	public void setReasonOn(String reasonOn) {
		this.reasonOn = reasonOn;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getHrCatId() {
		return hrCatId;
	}
	public void setHrCatId(int hrCatId) {
		this.hrCatId = hrCatId;
	}
	
	
}
