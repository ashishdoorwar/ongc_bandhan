package com.ongc.liferay.bandhan.service;

import com.ongc.liferay.bandhan.model.User;

import java.util.List;

public interface SearchService {

	public List<User> searchUser(User user ,boolean active);
	public List<User> searchUserMaster(User user);
}
