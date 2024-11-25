package com.ongc.liferay.bandhan.dao;

import com.ongc.liferay.bandhan.model.FeedbackComment;
import com.ongc.liferay.bandhan.model.FeedbackReason;

import java.sql.Connection;
import java.util.List;

public interface FeedbackCommentDao {
	public boolean saveFeedbackComment(FeedbackComment comment);
	public boolean archiveFeedbackComment(String status , int postId);
	public boolean deleteFeedbackComment(int postId);
	public List<FeedbackComment> getCommentListByPostId(int postId);
	public List<FeedbackComment> getAllCommentListByPostId(int postId);
	public List<FeedbackComment> getCommentListByStatus();
	public List<FeedbackReason> getReasonsListByPostId(int postId);
	public int getMaxIdFromTable(String tableName, String colName, Connection conn) ;
}
