package com.ongc.liferay.bandhan.service.Impl;

import com.ongc.liferay.bandhan.dao.FeedbackPostDao;
import com.ongc.liferay.bandhan.dao.Impl.FeedbackPostDaoImpl;
import com.ongc.liferay.bandhan.model.FeedbackCategory;
import com.ongc.liferay.bandhan.model.FeedbackHrCategory;
import com.ongc.liferay.bandhan.model.FeedbackHrEnablers;
import com.ongc.liferay.bandhan.model.FeedbackPost;
import com.ongc.liferay.bandhan.service.FeedbackPostService;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class FeedbackPostServiceImpl implements FeedbackPostService {


	private FeedbackPostDao feedbackPostDao=new FeedbackPostDaoImpl();
	
	@Override
	public FeedbackPost saveFeedbackPost(FeedbackPost post) {
		
		return feedbackPostDao.saveFeedbackPost(post);
	}

	@Override
	public List<FeedbackPost> getPostListByCategoryId(int categoryId) {
		
		return feedbackPostDao.getPostListByCategoryId(categoryId);
	}

	@Override
	public List<FeedbackPost> getPostListByFeedbackId(int feedbackId, int startFrom, int showRows,
			FeedbackCategory category) {
		
		return feedbackPostDao.getPostListByFeedbackId(feedbackId, startFrom, showRows, category);
	}

	@Override
	public int getNoOfFeedbacks(FeedbackCategory category, String stat) {
		
		return feedbackPostDao.getNoOfFeedbacks(category, stat);
	}

	@Override
	public String getCatDesc(String catId) {
		
		return feedbackPostDao.getCatDesc(catId);
	}

	@Override
	public List<FeedbackPost> searchPostList(FeedbackCategory category) {
		
		return feedbackPostDao.searchPostList(category);
	}

	@Override
	public boolean updateNoOfComment(int postId, boolean type) {
		
		return feedbackPostDao.updateNoOfComment(postId, type);
	}

	@Override
	public FeedbackPost getFeedbackPost(FeedbackPost post) {
		
		return feedbackPostDao.getFeedbackPost(post);
	}

	@Override
	public boolean saveFeedbackLike(String postid, String cpfnum, String status) {
		
		return feedbackPostDao.saveFeedbackLike(postid, cpfnum, status);
	}

	@Override
	public boolean getFeedBackLikeByCpfNo(String cpf, String postid) {
		
		return feedbackPostDao.getFeedBackLikeByCpfNo(cpf, postid);
	}

	@Override
	public int getFeedbackLikeCount(String postid) {
		
		return feedbackPostDao.getFeedbackLikeCount(postid);
	}

	@Override
	public int getFeedbackDisLikeCount(String postid) {
		
		return feedbackPostDao.getFeedbackDisLikeCount(postid);
	}

	@Override
	public boolean getFeedbackLikeByCPf(String postid, String cpfnum) {
		
		return feedbackPostDao.getFeedbackLikeByCPf(postid, cpfnum);
	}

	@Override
	public boolean saveFeedbackHits(String postid, String cpfnum) {
		
		return feedbackPostDao.saveFeedbackHits(postid, cpfnum);
	}

	@Override
	public boolean getFeedBackHitByCpfNo(String postid, String cpfnum) {
		
		return feedbackPostDao.getFeedBackHitByCpfNo(postid, cpfnum);
	}

	@Override
	public int getFeedbackHitCount(String postid) {
		
		return feedbackPostDao.getFeedbackHitCount(postid);
	}

	@Override
	public boolean archiveFeedbackPost(String status, int postId) {
		
		return feedbackPostDao.archiveFeedbackPost(status, postId);
	}

	@Override
	public List<FeedbackPost> getCommentListByStatus(int feedbackId, int startFrom, int showRows,
			FeedbackCategory category) {
		
		return feedbackPostDao.getCommentListByStatus(feedbackId, startFrom, showRows, category);
	}

	@Override
	public List<FeedbackPost> getRecentPost() {
		
		return feedbackPostDao.getRecentPost();
	}

	@Override
	public List<FeedbackPost> getRecentEnablerComm() {
		
		return feedbackPostDao.getRecentEnablerComm();
	}

	@Override
	public List<FeedbackPost> getRecentHREnablerComm() {
		
		return feedbackPostDao.getRecentHREnablerComm();
	}

	@Override
	public List<FeedbackPost> getRecentComm() {
		
		return feedbackPostDao.getRecentComm();
	}

	@Override
	public List<FeedbackPost> getCommentListByStatus() {
		
		return feedbackPostDao.getCommentListByStatus();
	}

	@Override
	public List<FeedbackPost> getTagListByFeedbackId() {
		
		return feedbackPostDao.getTagListByFeedbackId();
	}

	@Override
	public List<FeedbackHrEnablers> getHREnablersList() {
		
		return feedbackPostDao.getHREnablersList();
	}

	@Override
	public boolean closeComment(FeedbackPost post) {
		
		return feedbackPostDao.closeComment(post);
	}

	@Override
	public boolean openComment(FeedbackPost post) {
		
		return feedbackPostDao.openComment(post);
	}

	@Override
	public boolean escalateCorporate(FeedbackPost post) {
		
		return feedbackPostDao.escalateCorporate(post);
	}

	@Override
	public boolean revertCorporate(FeedbackPost post) {
		
		return feedbackPostDao.revertCorporate(post);
	}

	@Override
	public List<FeedbackHrCategory> getHRCategoryList() {
		
		return feedbackPostDao.getHRCategoryList();
	}

	@Override
	public boolean sendPostToChief(int postId) {
		
		return feedbackPostDao.sendPostToChief(postId);
	}

	@Override
	public String getCorporateCpf(FeedbackPost post) {
		
		return feedbackPostDao.getCorporateCpf(post);
	}

	@Override
	public String getChiefCpf() {
		
		return feedbackPostDao.getChiefCpf();
	}

	@Override
	public List<FeedbackPost> getfeedbackListbyLocation(String location) {
		
		return feedbackPostDao.getfeedbackListbyLocation(location);
	}

	@Override
	public List<FeedbackPost> getfeedbackList(String subLocation, int start, int end) {
		
		return feedbackPostDao.getfeedbackList(subLocation, start, end);
	}

	@Override
	public List<FeedbackPost> getfeedbackListCorp(String username, int start, int end) {
		
		return feedbackPostDao.getfeedbackListCorp(username, start, end);
	}

	@Override
	public int getNoOfLocalFeedbacks(String sublocation) {
		
		return feedbackPostDao.getNoOfLocalFeedbacks(sublocation);
	}

	@Override
	public int getNoOfCorpFeedbacks(String username) {
		
		return feedbackPostDao.getNoOfCorpFeedbacks(username);
	}

	@Override
	public boolean check_cpf(String cpf) {
		
		return feedbackPostDao.check_cpf(cpf);
	}

	@Override
	public boolean saveUser(String cpf, String chk) {
		
		return feedbackPostDao.saveUser(cpf, chk);
	}

	@Override
	public boolean updateUser(String cpf, String chk) {
		
		return feedbackPostDao.updateUser(cpf, chk);
	}

	@Override
	public int getFeedbackCount(String cpf, String chk) {
		
		return feedbackPostDao.getFeedbackCount(cpf, chk);
	}

	@Override
	public List<FeedbackPost> getMyPosts(String cpfno) {
		
		return feedbackPostDao.getMyPosts(cpfno);
	}

	@Override
	public int calculateWorkDays(int pId) {
		
		return feedbackPostDao.calculateWorkDays(pId);
	}

	@Override
	public int getMaxIdFromTable(String tableName, String colName, Connection conn) {
		
		return feedbackPostDao.getMaxIdFromTable(tableName, colName, conn);
	}

	@Override
	public String getReceiverMobNo(String toCpf) {
		
		return feedbackPostDao.getReceiverMobNo(toCpf);
	}

//	@Override
//	public void createQuery(FeedbackCategory category) {
//		// TODO Auto-generated method stub
//		feedbackPostDao.createQuery(category);
//	}

	@Override
	public Date getSrt_dateByPostId(int postId) {
		// TODO Auto-generated method stub
		return feedbackPostDao.getSrt_dateByPostId(postId);
	}

	@Override
	public String getAuth_statusByPostId(int postId) {
		// TODO Auto-generated method stub
		return feedbackPostDao.getAuth_statusByPostId(postId);
	}

	@Override
	public String getLastUpdNameByPostId(int postId) {
		// TODO Auto-generated method stub
		return feedbackPostDao.getLastUpdNameByPostId(postId);
	}

	@Override
	public Date getPostForwardDate(int postId) {
		// TODO Auto-generated method stub
		return feedbackPostDao.getPostForwardDate(postId);
	}

}
