package com.ongc.liferay.bandhan.service;

import com.ongc.liferay.bandhan.model.FeedbackComment;
import com.ongc.liferay.bandhan.model.FeedbackReason;

import java.util.List;

public interface FeedbackCommentService {

	List<FeedbackComment> getAllCommentListByPostId(int postId);

	List<FeedbackComment> getCommentListByPostId(int postId);

	List<FeedbackReason> getReasonsListByPostId(int postId);

	boolean saveFeedbackComment(FeedbackComment comment);

}
