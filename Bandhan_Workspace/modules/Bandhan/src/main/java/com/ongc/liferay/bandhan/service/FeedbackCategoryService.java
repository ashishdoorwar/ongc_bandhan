package com.ongc.liferay.bandhan.service;

import com.ongc.liferay.bandhan.model.FeedbackCategory;

import java.util.List;

public interface FeedbackCategoryService {

	List<FeedbackCategory> getCategoryListByFeedbakId(int feedbackId);

	boolean roleCheck(String cpfNo);

}
