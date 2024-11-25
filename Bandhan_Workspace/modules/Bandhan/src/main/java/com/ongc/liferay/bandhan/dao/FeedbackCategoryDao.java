package com.ongc.liferay.bandhan.dao;

import com.ongc.liferay.bandhan.model.FeedbackCategory;

import java.util.List;

public interface FeedbackCategoryDao {

	List<FeedbackCategory> getCategoryListByFeedbakId(int feedbackId);

	boolean roleCheck(String cpfNo);

}
