package com.ongc.liferay.bandhan.service;

import com.ongc.liferay.bandhan.model.FeedbackCategory;
import com.ongc.liferay.bandhan.model.FeedbackHrCategory;
import com.ongc.liferay.bandhan.model.FeedbackHrEnablers;
import com.ongc.liferay.bandhan.model.FeedbackPost;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public interface FeedbackPostService {

	FeedbackPost getFeedbackPost(FeedbackPost post);

	List<FeedbackHrCategory> getHRCategoryList();

	List<FeedbackHrEnablers> getHREnablersList();

	List<FeedbackPost> searchPostList(FeedbackCategory category);

	int getNoOfFeedbacks(FeedbackCategory category, String string);

	List<FeedbackPost> getCommentListByStatus(int feedbackId, int stratFrom, int showRow, FeedbackCategory category);

	List<FeedbackPost> getPostListByFeedbackId(int feedbackId, int stratFrom, int showRow, FeedbackCategory category);

	List<FeedbackPost> getCommentListByStatus();

	String getCatDesc(String catId);

	FeedbackPost saveFeedbackPost(FeedbackPost post);

	boolean check_cpf(String cpfNo);

	boolean saveUser(String cpfNo, String string);

	boolean updateUser(String cpfNo, String string);

	String getChiefCpf();

	Date getPostForwardDate(int postId);

	boolean updateNoOfComment(int postId, boolean b);

	List<FeedbackPost> getMyPosts(String cpf);

	List<FeedbackPost> getPostListByCategoryId(int categoryId);

	boolean saveFeedbackLike(String postid, String cpfnum, String status);

	boolean getFeedBackLikeByCpfNo(String cpf, String postid);

	int getFeedbackLikeCount(String postid);

	int getFeedbackDisLikeCount(String postid);

	boolean getFeedbackLikeByCPf(String postid, String cpfnum);

	boolean saveFeedbackHits(String postid, String cpfnum);

	boolean getFeedBackHitByCpfNo(String postid, String cpfnum);

	int getFeedbackHitCount(String postid);

	boolean archiveFeedbackPost(String status, int postId);

	List<FeedbackPost> getRecentPost();

	List<FeedbackPost> getRecentEnablerComm();

	List<FeedbackPost> getRecentHREnablerComm();

	List<FeedbackPost> getRecentComm();

	List<FeedbackPost> getTagListByFeedbackId();

	boolean closeComment(FeedbackPost post);

	boolean openComment(FeedbackPost post);

	boolean escalateCorporate(FeedbackPost post);

	boolean revertCorporate(FeedbackPost post);

	boolean sendPostToChief(int postId);

	String getCorporateCpf(FeedbackPost post);

	List<FeedbackPost> getfeedbackListbyLocation(String location);

	List<FeedbackPost> getfeedbackList(String subLocation, int start, int end);

	List<FeedbackPost> getfeedbackListCorp(String username, int start, int end);

	int getNoOfLocalFeedbacks(String sublocation);

	int getNoOfCorpFeedbacks(String username);

	int getFeedbackCount(String cpf, String chk);

	int calculateWorkDays(int pId);

	String getReceiverMobNo(String toCpf);

	Date getSrt_dateByPostId(int postId);

	String getAuth_statusByPostId(int postId);

	String getLastUpdNameByPostId(int postId);

	int getMaxIdFromTable(String tableName, String colName, Connection conn);

}
