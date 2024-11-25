package com.ongc.liferay.bandhan.service.Impl;

import com.ongc.liferay.bandhan.dao.FeedbackCommentDao;
import com.ongc.liferay.bandhan.dao.Impl.FeedbackCommentDaoImpl;
import com.ongc.liferay.bandhan.model.FeedbackComment;
import com.ongc.liferay.bandhan.model.FeedbackReason;
import com.ongc.liferay.bandhan.service.FeedbackCommentService;

import java.sql.Connection;
import java.util.List;

public class FeedbackCommentServiceImpl implements FeedbackCommentService{

private FeedbackCommentDao feedbackCommentDao=new FeedbackCommentDaoImpl();;
	
	@Override
	public boolean saveFeedbackComment(FeedbackComment comment) {
		// TODO Auto-generated method stub
		return feedbackCommentDao.saveFeedbackComment(comment);
	}

	public boolean archiveFeedbackComment(String status, int postId) {
		// TODO Auto-generated method stub
		return feedbackCommentDao.archiveFeedbackComment(status, postId);
	}

	public boolean deleteFeedbackComment(int postId) {
		// TODO Auto-generated method stub
		return feedbackCommentDao.deleteFeedbackComment(postId);
	}

	@Override
	public List<FeedbackComment> getCommentListByPostId(int postId) {
		
		return feedbackCommentDao.getCommentListByPostId(postId);
	}

	@Override
	public List<FeedbackComment> getAllCommentListByPostId(int postId) {
		
		return feedbackCommentDao.getAllCommentListByPostId(postId);
	}

	public List<FeedbackComment> getCommentListByStatus() {
		// TODO Auto-generated method stub
		return feedbackCommentDao.getCommentListByStatus();
	}

	@Override
	public List<FeedbackReason> getReasonsListByPostId(int postId) {
		
		return feedbackCommentDao.getReasonsListByPostId(postId);
	}

	public int getMaxIdFromTable(String tableName, String colName, Connection conn) {
		// TODO Auto-generated method stub
		return feedbackCommentDao.getMaxIdFromTable(tableName, colName, conn);
	}

}
