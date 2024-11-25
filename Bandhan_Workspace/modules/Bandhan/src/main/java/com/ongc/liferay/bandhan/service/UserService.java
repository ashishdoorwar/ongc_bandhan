package com.ongc.liferay.bandhan.service;

import com.ongc.liferay.bandhan.model.Faculty;
import com.ongc.liferay.bandhan.model.Talent;
import com.ongc.liferay.bandhan.model.User;

import java.util.ArrayList;
import java.util.List;

public interface UserService {

	public ArrayList<Faculty> getFacultyData(String cpfno);
	public User getUser() ;
	public User getUserByCpfNo(String cpfNo);
	public User getReportUserByCpfNo(String cpfNo);
	
	public Faculty getDataById(String facid);
	public boolean insertFacProfile(User user, Faculty fac);
	public ArrayList<String> getProgramCodeList();
	public List<Talent> getTalentData(String cpfNo);
	public Talent getTalentDataById(String id);
	public boolean insertTalentData(User user, Talent talent);
	public boolean updateProfile(User user);
	public User getUserByCPFNumber(String cpfNo);
}
