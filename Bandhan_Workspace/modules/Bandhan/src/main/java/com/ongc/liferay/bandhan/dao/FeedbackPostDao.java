package com.ongc.liferay.bandhan.dao;

import com.ongc.liferay.bandhan.model.FeedbackCategory;
import com.ongc.liferay.bandhan.model.FeedbackHrCategory;
import com.ongc.liferay.bandhan.model.FeedbackHrEnablers;
import com.ongc.liferay.bandhan.model.FeedbackPost;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public interface FeedbackPostDao {

	public FeedbackPost saveFeedbackPost(FeedbackPost post);
	public List<FeedbackPost> getPostListByCategoryId(int categoryId);
	public List<FeedbackPost> getPostListByFeedbackId(int feedbackId,int startFrom, int showRows, FeedbackCategory category);
	public int getNoOfFeedbacks(FeedbackCategory category, String stat);
	public String getCatDesc(String catId);
	public List<FeedbackPost> searchPostList(FeedbackCategory category);
	//public void createQuery(FeedbackCategory category,PreparedStatement pstmt);
	public boolean updateNoOfComment(int postId, boolean type);
	public FeedbackPost getFeedbackPost(FeedbackPost post);
	public boolean saveFeedbackLike(String postid, String cpfnum, String status);
	public boolean getFeedBackLikeByCpfNo(String cpf, String postid);
	public int getFeedbackLikeCount(String postid);
	public int getFeedbackDisLikeCount(String postid) ;
	public boolean getFeedbackLikeByCPf(String postid, String cpfnum);
	public boolean saveFeedbackHits(String postid, String cpfnum);
	public boolean getFeedBackHitByCpfNo(String postid, String cpfnum);
	public int getFeedbackHitCount(String postid);
	public boolean archiveFeedbackPost(String status, int postId);
	public List<FeedbackPost> getCommentListByStatus(int feedbackId,int startFrom, int showRows, FeedbackCategory category);
	public Date getSrt_dateByPostId(int postId);
	public String getAuth_statusByPostId(int postId);
	public String getLastUpdNameByPostId(int postId);
	public List<FeedbackPost> getRecentPost();
	public List<FeedbackPost> getRecentEnablerComm();
	public List<FeedbackPost> getRecentHREnablerComm();
	public List<FeedbackPost> getRecentComm();
	public List<FeedbackPost> getCommentListByStatus();
	public List<FeedbackPost> getTagListByFeedbackId();
	public List<FeedbackHrEnablers> getHREnablersList();
	public boolean closeComment(FeedbackPost post);
	public boolean openComment(FeedbackPost post);
	public boolean escalateCorporate(FeedbackPost post);
	public boolean revertCorporate(FeedbackPost post);
	public List<FeedbackHrCategory> getHRCategoryList();
	public boolean sendPostToChief(int postId);
	public String getCorporateCpf(FeedbackPost post);
	public String getChiefCpf();
	public Date getPostForwardDate(int postId);
	public List<FeedbackPost> getfeedbackListbyLocation(String location);
	public List<FeedbackPost> getfeedbackList(String subLocation, int start,int end);
	public List<FeedbackPost> getfeedbackListCorp(String username, int start,
			int end);
	public int getNoOfLocalFeedbacks(String sublocation);
	public int getNoOfCorpFeedbacks(String username);
	public boolean check_cpf(String cpf);
	public boolean saveUser(String cpf, String chk);
	public boolean updateUser(String cpf, String chk);
	public int getFeedbackCount(String cpf, String chk);
	public List<FeedbackPost> getMyPosts(String cpfno);
	public int calculateWorkDays(int pId);
	public int getMaxIdFromTable(String tableName, String colName,Connection conn);
	public String getReceiverMobNo(String toCpf);
}
