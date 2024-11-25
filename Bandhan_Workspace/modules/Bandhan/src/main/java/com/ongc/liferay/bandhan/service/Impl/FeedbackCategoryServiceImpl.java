package com.ongc.liferay.bandhan.service.Impl;

import com.ongc.liferay.bandhan.dao.FeedbackCategoryDao;
import com.ongc.liferay.bandhan.dao.Impl.FeedbackCategoryDaoImpl;
import com.ongc.liferay.bandhan.model.FeedbackCategory;
import com.ongc.liferay.bandhan.service.FeedbackCategoryService;

import java.util.List;

public class FeedbackCategoryServiceImpl implements FeedbackCategoryService {

	private FeedbackCategoryDao feedbackCategoryDao=new FeedbackCategoryDaoImpl(); 
	@Override
	public List<FeedbackCategory> getCategoryListByFeedbakId(int feedbackId) {
		// TODO Auto-generated method stub
		return feedbackCategoryDao.getCategoryListByFeedbakId(feedbackId);
	}

	@Override
	public boolean roleCheck(String cpfNo) {
		// TODO Auto-generated method stub
		return feedbackCategoryDao.roleCheck(cpfNo);
	}

}
