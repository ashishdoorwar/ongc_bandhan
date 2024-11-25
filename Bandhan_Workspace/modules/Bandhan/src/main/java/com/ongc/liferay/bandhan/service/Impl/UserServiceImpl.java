package com.ongc.liferay.bandhan.service.Impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.ongc.liferay.bandhan.dao.UserDao;
import com.ongc.liferay.bandhan.dao.Impl.UserDaoImpl;
import com.ongc.liferay.bandhan.model.Faculty;
import com.ongc.liferay.bandhan.model.Talent;
import com.ongc.liferay.bandhan.model.User;
import com.ongc.liferay.bandhan.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService{

	private UserDao userDao=new UserDaoImpl();
	@Override
	public ArrayList<Faculty> getFacultyData(String cpfno) {
		// TODO Auto-generated method stub
		return userDao.getFacultyData(cpfno);
	}

	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		userDao=new UserDaoImpl();
		ServiceContext serviceContext=ServiceContextThreadLocal.getServiceContext();
		long userId = serviceContext.getUserId();
		
		com.liferay.portal.kernel.model.User user=null;
		try {
			user = UserLocalServiceUtil.getUser(userId);
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return userDao.getUserByEmailId(user.getEmailAddress());
		return userDao.getUserByCpfNo(user.getScreenName());
	}

	@Override
	public User getUserByCpfNo(String cpfNo) {
		// TODO Auto-generated method stub
		return userDao.getUserByCpfNo(cpfNo);
	}
	
	@Override
	public User getReportUserByCpfNo(String cpfNo) {
		// TODO Auto-generated method stub
		return userDao.getReportUserByCpfNo(cpfNo);
	}

	@Override
	public Faculty getDataById(String facid) {
		// TODO Auto-generated method stub
		return userDao.getDataById(facid);
	}

	@Override
	public boolean insertFacProfile(User user, Faculty fac) {
		// TODO Auto-generated method stub
		return userDao.insertFacProfile(user, fac);
	}

	@Override
	public ArrayList<String> getProgramCodeList() {
		// TODO Auto-generated method stub
		return userDao.getProgramCodeList();
	}

	@Override
	public List<Talent> getTalentData(String cpfNo) {
		// TODO Auto-generated method stub
		return userDao.getTalentData(cpfNo);
	}

	@Override
	public Talent getTalentDataById(String id) {
		// TODO Auto-generated method stub
		return userDao.getTalentDataById(id);
	}

	@Override
	public boolean insertTalentData(User user, Talent talent) {
		// TODO Auto-generated method stub
		return userDao.insertTalentData(user,talent);
	}

	@Override
	public boolean updateProfile(User user) {
		// TODO Auto-generated method stub
		return userDao.updateProfile(user);
	}

	@Override
	public User getUserByCPFNumber(String cpfNo) {
		// TODO Auto-generated method stub
		return userDao.getUserByCpfNo(cpfNo);
	}
}
