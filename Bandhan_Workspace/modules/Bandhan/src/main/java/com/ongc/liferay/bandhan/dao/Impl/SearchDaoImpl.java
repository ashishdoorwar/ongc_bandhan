package com.ongc.liferay.bandhan.dao.Impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.ongc.liferay.bandhan.connection.DatasourceConnection;
import com.ongc.liferay.bandhan.connection.EdbConnection;
import com.ongc.liferay.bandhan.dao.SearchDao;
import com.ongc.liferay.bandhan.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SearchDaoImpl implements SearchDao{

	
	
	Connection conn =null;
	PreparedStatement pstmt=null;
	ResultSet set=null;
	private static final Log LOGGER = LogFactoryUtil.getLog(SearchDaoImpl.class);
	private String createSearchQuery(User user){
		String query="SELECT * FROM ONGC_RT_USER ";
		String order=" ORDER BY EMP_NAME ";
		String where=null;
		if(user!=null){

			if(user.getUserName()!=null && user.getUserName().trim().length()>0){
				where="WHERE UPPER(EMP_NAME) LIKE '%"+user.getUserName().toUpperCase()+"%' ";
			}
			if(user.getCpfNo()!=null && user.getCpfNo().trim().length()>0){
				if(where ==null)
					//where="WHERE CPF_NUMBER LIKE '%"+user.getCpfNo()+"%' ";
					where="WHERE CPF_NUMBER = '"+user.getCpfNo()+"'";
				else 
					//where= where + " AND CPF_NUMBER LIKE '%"+user.getCpfNo()+"%' ";
					where= where + " AND CPF_NUMBER = '"+user.getCpfNo()+"'";
			}
			if(user.getLocation()!=null && user.getLocation().trim().length()>0){
				if(where == null)
					where=" WHERE UPPER(LOCATION) LIKE '%"+user.getLocation().toUpperCase()+"%' ";
				else 
					where=where + " AND UPPER(LOCATION) LIKE '%"+user.getLocation().toUpperCase()+"%' ";
			}
			if(user.getMobileNo()!=null && user.getMobileNo().trim().length()>0){
				if(where==null)
					where=" WHERE CONTACTNO LIKE '%"+user.getMobileNo()+"%' ";
				else
					where=where+" AND CONTACTNO LIKE '%"+user.getMobileNo()+"%' ";	
			}
			if(user.getDesignation()!=null && user.getDesignation().trim().length()>0){
				if(where==null)
					where=" WHERE UPPER(PAST_DESIGNATION) LIKE '"+user.getDesignation().toUpperCase()+"%' ";
				else 
					where=where+ " AND UPPER(PAST_DESIGNATION) LIKE '"+user.getDesignation().toUpperCase()+"%' ";	
			}
			if(where !=null)
				query=query+where;
		}

		return query+order;
	}

	public List<User> searchUser(User user ,boolean active){
	

		List<User> list=new ArrayList<User>();
		User u=null;
		String query=createSearchQuery(user);
		try {
			if(active) {
				conn=EdbConnection.getConnection();
			}else {
				conn=DatasourceConnection.getConnection();
			}
			pstmt=conn.prepareStatement(query);
			LOGGER.info(pstmt);
			set=pstmt.executeQuery();
			while(set.next()){
				u=new User();
				u.setCpfNo(set.getString("CPF_NUMBER"));
				u.setPassword(set.getString("PASSWORD"));
				u.setTittle(set.getString("TITLE"));
				u.setUserName(set.getString("EMP_NAME"));
				u.setEmailId(set.getString("EMAILID"));
				u.setMobileNo(set.getString("CONTACTNO"));
				u.setLandLineNo(set.getString("LAND_LINE_NUMBER"));
				u.setLocation(set.getString("LOCATION"));
				u.setAddress(set.getString("ADDRESS"));
				u.setCommunicatedAddress(set.getString("COMM_ADDRESS"));
				u.setAboutMe(set.getString("ABOUT_ME"));
				u.setDesignation(set.getString("PAST_DESIGNATION"));
				u.setPanNumber(set.getString("PAN_NUMBER"));
				u.setDateOfBirth(set.getDate("DT_OF_BIRTH"));
				u.setDateOfJoining(set.getDate("DT_OF_JOINING"));
				u.setDateOfSep(set.getDate("DT_OF_SEP"));
				u.setCreatedBy(set.getString("CREATED_BY"));
				u.setCreationDate(set.getDate("CREATION_DATE"));
				u.setActive(set.getInt("ACTIVE"));
				u.setProfilePic(set.getBytes("PROFILE_PIC"));

				list.add(u);
				

			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			EdbConnection.closeConnection(conn, pstmt, set);

		}
		return list;
	}
	
	private String createSearchUserQuery(User user){
		String query="SELECT * FROM ONGC_USER_MASTER ";
		String order=" ORDER BY EMP_NAME ";
		String where=null;
		if(user != null){
	   if(user.getUserName()!=null && user.getUserName().trim().length()>0){
			where="WHERE UPPER(EMP_NAME) LIKE '%"+user.getUserName().toUpperCase()+"%' ";
		}
		if(user.getCpfNo()!=null && user.getCpfNo().trim().length()>0){
			if(where ==null)
			//where="WHERE CPF_NUMBER LIKE '%"+user.getCpfNo()+"%' ";
				where="WHERE CPF_NUMBER = '"+user.getCpfNo()+"'";
			else 
				//where= where + " AND CPF_NUMBER LIKE '%"+user.getCpfNo()+"%' ";
				where= where + " AND CPF_NUMBER = '"+user.getCpfNo()+"'";
		}
		if(user.getLocation()!=null && user.getLocation().trim().length()>0){
			if(where == null)
				where=" WHERE UPPER(PLACE_POSTING) LIKE '%"+user.getLocation().toUpperCase()+"%' ";
			else 
				where=where + " AND UPPER(PLACE_POSTING) LIKE '%"+user.getLocation().toUpperCase()+"%' ";
		}
		if(user.getMobileNo()!=null && user.getMobileNo().trim().length()>0){
			if(where==null)
				where=" WHERE MOBILE_NUMBER LIKE '%"+user.getMobileNo()+"%' ";
			else
				where=where+" AND MOBILE_NUMBER LIKE '%"+user.getMobileNo()+"%' ";	
		}
		if(user.getDesignation()!=null && user.getDesignation().trim().length()>0){
			if(where==null)
				where=" WHERE UPPER(DESIGNATION) LIKE '"+user.getDesignation().toUpperCase()+"%' ";
			else 
				where=where+ " AND UPPER(DESIGNATION) LIKE '"+user.getDesignation().toUpperCase()+"%' ";	
		}
		}
		if(where !=null) {
			query=query+where+order;
		}else {
			query= null;
		}
		
		return query;
	}

	public List<User> searchUserMaster(User user){
		LOGGER.info("*************searchUserMater()******************");
		List<User> list=null;
		User u=null;
		String query=createSearchUserQuery(user);
		LOGGER.info("======");
		if(query!=null) {
		try {
			conn=EdbConnection.getConnection();
			pstmt=conn.prepareStatement(query);
			set=pstmt.executeQuery();
			while(set.next()){
				if(list==null)
				list=new ArrayList<User>();
				u=new User();
				u.setCpfNo(set.getString("CPF_NUMBER"));
				u.setUserName(set.getString("EMP_NAME"));
				u.setEmailId(set.getString("E_MAILID_OFF"));
				u.setMobileNo(set.getString("MOBILE_NUMBER"));
				u.setLocation(set.getString("PLACE_POSTING"));
				u.setLocation(set.getString("LOCAL_ADDRESS"));
				u.setAboutMe(set.getString("ABOUT_ME"));
				u.setDesignation(set.getString("DESIGNATION"));
				
				list.add(u);
				
			}
		} catch (Exception e) {
			LOGGER.info("Exception in searchUser method "+e);
		}
		finally{
			EdbConnection.closeConnection(conn, pstmt, set);
		}
		}
		return list;
	}
}
