package com.ongc.liferay.bandhan.dao;

import com.ongc.liferay.bandhan.model.User;

import java.util.List;

public interface SearchDao {

	public List<User> searchUser(User user ,boolean active);
	public List<User> searchUserMaster(User user );
}
