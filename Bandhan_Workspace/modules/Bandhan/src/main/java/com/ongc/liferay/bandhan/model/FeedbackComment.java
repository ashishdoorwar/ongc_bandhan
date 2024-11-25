package com.ongc.liferay.bandhan.model;

import com.ongc.liferay.bandhan.model.User;

import java.util.Date;


public class FeedbackComment {

	private int commentId;
	private int postId;
	private FeedbackPost post;
	private User user;
	private String commentText;
	private Date postDate;
	private String captchaVal;
	private String postTextDate;
	private String status;
	private boolean authResp;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isAuthResp() {
		return authResp;
	}
	public void setAuthResp(boolean authResp) {
		this.authResp = authResp;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public FeedbackPost getPost() {
		return post;
	}
	public void setPost(FeedbackPost post) {
		this.post = post;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public String getCaptchaVal() {
		return captchaVal;
	}
	public void setCaptchaVal(String captchaVal) {
		this.captchaVal = captchaVal;
	}
	public String getPostTextDate() {
		return postTextDate;
	}
	public void setPostTextDate(String postTextDate) {
		this.postTextDate = postTextDate;
	}
	@Override
	public String toString() {
		return "FeedbackComment [commentId=" + commentId + ", postId=" + postId + ", post=" + post + ", user=" + user
				+ ", commentText=" + commentText + ", postDate=" + postDate + ", captchaVal=" + captchaVal
				+ ", postTextDate=" + postTextDate + ", status=" + status + ", authResp=" + authResp + "]";
	}
	
	
}
