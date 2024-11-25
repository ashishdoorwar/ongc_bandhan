package com.ongc.liferay.bandhan.service.Impl;

import com.ongc.liferay.bandhan.dao.SearchDao;
import com.ongc.liferay.bandhan.dao.Impl.SearchDaoImpl;
import com.ongc.liferay.bandhan.model.User;
import com.ongc.liferay.bandhan.service.SearchService;

import java.util.List;

public class SearchServiceImpl implements SearchService{

	private SearchDao searchDao=new SearchDaoImpl();
	@Override
	public List<User> searchUser(User user ,boolean active){
		// TODO Auto-generated method stub
		return searchDao.searchUser(user,active);
	}
	@Override
	public List<User> searchUserMaster(User user) {
		// TODO Auto-generated method stub
		return searchDao.searchUserMaster(user);
	}

}
