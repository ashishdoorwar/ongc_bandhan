package com.ongc.liferay.bandhan.dao.Impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.ongc.liferay.bandhan.connection.DatasourceConnection;
import com.ongc.liferay.bandhan.connection.EdbConnection;
import com.ongc.liferay.bandhan.dao.UserDao;
import com.ongc.liferay.bandhan.model.Faculty;
import com.ongc.liferay.bandhan.model.Talent;
import com.ongc.liferay.bandhan.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserDaoImpl implements UserDao{

	private static final Log LOGGER = LogFactoryUtil.getLog(UserDaoImpl.class);

	public User getUserByCpfNo(String cpfNo) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		User user = null;
		try {
			String query = "SELECT * FROM ONGC_RT_USER WHERE CPF_NUMBER=?";
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cpfNo);

			set = pstmt.executeQuery();
			if (set.next()) {
				user = new User();
				user.setCpfNo(set.getString("CPF_NUMBER"));
				user.setPassword(set.getString("PASSWORD"));
				user.setTittle(set.getString("TITLE"));
				user.setUserName(set.getString("EMP_NAME"));
				user.setEmailId(set.getString("EMAILID"));
				user.setMobileNo(set.getString("CONTACTNO"));
				user.setLandLineNo(set.getString("LAND_LINE_NUMBER"));
				user.setLocation(set.getString("LOCATION"));
				user.setAddress(set.getString("ADDRESS"));
				user.setCommunicatedAddress(set.getString("COMM_ADDRESS"));
				user.setAboutMe(set.getString("ABOUT_ME"));
				user.setDesignation(set.getString("PAST_DESIGNATION"));
				user.setPanNumber(set.getString("PAN_NUMBER"));
				user.setDateOfBirth(set.getDate("DT_OF_BIRTH"));
				user.setDateOfJoining(set.getDate("DT_OF_JOINING"));
				user.setDateOfSep(set.getDate("DT_OF_SEP"));
				user.setCreatedBy(set.getString("CREATED_BY"));
				user.setCreationDate(set.getDate("CREATION_DATE"));
				user.setActive(set.getInt("ACTIVE"));
				//user.setProfilePic(set.getBytes("PROFILE_PIC"));

			}
		} catch (Exception e) {
			user = null;
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt, set);
		}
		return user;

	}

	public ArrayList<Faculty> getFacultyData(String cpfno) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		ArrayList<Faculty> alist = new ArrayList<Faculty>();
		String query = "";
		try {

			query = "select b.facid,b.cpfno,b.name,b.designation,b.mob,b.email,b.fax,b.address,b.city,b.specialisation,b.academics,b.superannuation,b.program_code,b.description,b.posted_date from rt_faculty_details b where b.status='Y' and cpfno =? order by b.facid desc ";
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cpfno);

			set = pstmt.executeQuery();
			while (set.next()) {
				Faculty fbean = new Faculty();

				fbean.setFacid(set.getInt("facid"));
				fbean.setCpfno(set.getString("cpfno"));
				fbean.setUsername(set.getString("name"));
				fbean.setDesignation(set.getString("designation"));
				fbean.setMobileNo(set.getString("mob"));

				fbean.setEmailId(set.getString("email"));
				fbean.setFaxno(set.getString("fax"));
				fbean.setAddress(set.getString("address"));

				fbean.setCity(set.getString("city"));
				fbean.setSpecialisation(set.getString("specialisation"));
				fbean.setAcademics(set.getString("academics"));

				SimpleDateFormat stm = new SimpleDateFormat("MM/dd/yyyy");
				String dos = "";
				if (set.getDate("superannuation") != null) {
					dos = stm.format(set.getDate("superannuation"));
				}
				fbean.setDos(dos);
				// fbean.setDos(res.getString("superannuation"));

				// fbean.setTitle(set.getString("program_title"));

				fbean.setCode(set.getString("program_code"));
				fbean.setDescription(set.getString("description"));

				// String posdate = stm.format(set.getDate("posted_date"));
				// Date posdate1= stm.parse(posdate);
				fbean.setPosteddt(set.getDate("posted_date"));

				alist.add(fbean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt, set);
		}
		return alist;
	}

	public ArrayList<String> getProgramCodeList() {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		ArrayList<String> codeList = new ArrayList<String>();
		String query = "";
		try {

			query = "select (pcode || ' :: ' || ptitle) as code_title from program_codes where status='Y' order by pid asc ";
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);

			set = pstmt.executeQuery();
			while (set.next()) {

				codeList.add(set.getString("code_title"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt, set);
		}
		return codeList;
	}

	public boolean updateProfile(User user) {
		Connection conn1=null;
		PreparedStatement pstmt1=null;
		boolean flag = false;
		String query = "UPDATE ONGC_RT_USER SET ABOUT_ME=?, COMM_ADDRESS=?, EMAILID=?, PAN_NUMBER=?, CONTACTNO=?  WHERE CPF_NUMBER=?";
		try {
			conn1 = DatasourceConnection.getConnection();
			pstmt1 = conn1.prepareStatement(query);
			pstmt1.setString(1, user.getAboutMe());
			pstmt1.setString(2, user.getCommunicatedAddress());
			pstmt1.setString(3, user.getEmailId());
			pstmt1.setString(4, user.getPanNumber());
			pstmt1.setString(5, user.getMobileNo());
			pstmt1.setString(6, user.getCpfNo());
			flag = 0 < pstmt1.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn1,pstmt1);
		}
		return flag;
	}

	public boolean insertFacProfile(User user, Faculty fac) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean flag = false;
		// String
		// query="UPDATE ONGC_RT_USER SET ABOUT_ME=? ,COMM_ADDRESS=?  WHERE CPF_NUMBER=?";
		String query = "INSERT INTO RT_FACULTY_DETAILS(FACID,CPFNO,NAME,DESIGNATION,MOB,EMAIL,FAX,ADDRESS,CITY,SPECIALISATION,ACADEMICS,SUPERANNUATION,PROGRAM_TITLE,PROGRAM_CODE,DESCRIPTION,STATUS) "
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'Y')";

		try {
			conn = DatasourceConnection.getConnection();

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, getMaxIdFromTable("RT_FACULTY_DETAILS", "FACID"));
			pstmt.setString(2, user.getCpfNo());

			pstmt.setString(3, user.getUserName());

			pstmt.setString(4, user.getDesignation());

			pstmt.setString(5, fac.getMobileNo());
			pstmt.setString(6, fac.getEmailId());
			pstmt.setString(7, fac.getFaxno());
			pstmt.setString(8, fac.getAddress());

			pstmt.setString(9, fac.getCity());
			pstmt.setString(10, fac.getSpecialisation());
			pstmt.setString(11, fac.getAcademics());
			// SimpleDateFormat stm = new SimpleDateFormat("MM/dd/yyyy");
			// String dos = stm.format(user.getDateOfSep());
			// java.util.Date parsed = stm.parse(dos);

			if (user.getDateOfSep() != null) {
				java.sql.Date timestamp = new java.sql.Date(user.getDateOfSep()
						.getTime());

				pstmt.setDate(12, timestamp);

			} else {
				pstmt.setDate(12, null);
			}

			pstmt.setString(13, "");

			pstmt.setString(14, fac.getCode());
			pstmt.setString(15, fac.getDescription());
			flag = 0 < pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt);
		}
		return flag;
	}

	public boolean updateProfilePic(User user) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean flag = false;
		String query = "UPDATE ONGC_RT_USER SET PROFILE_PIC=? WHERE CPF_NUMBER=?";
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setBytes(1, user.getProfilePic());
			pstmt.setString(2, user.getCpfNo());
			flag = 0 < pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt);
		}
		return flag;
	}

	public byte[] getPicByCpfNo(String cpfNo) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		byte bt[] = null;
		String query = " SELECT PROFILE_PIC FROM ONGC_RT_USER WHERE CPF_NUMBER=?";
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cpfNo);
			set = pstmt.executeQuery();
			if (set.next()) {
				bt = set.getBytes("PROFILE_PIC");
			}
		} catch (Exception e) {
			bt = null;
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt, set);
		}

		return bt;

	}

	public Faculty getDataById(String facid) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		Faculty fbean = null;
		int id=Integer.parseInt(facid);
		String query = "";
		try {
			query = "select b.facid,b.cpfno,b.name,b.designation,b.mob,b.email,b.fax,b.address,b.city,b.specialisation,b.academics,b.superannuation,b.program_title,b.program_code,b.description,b.posted_date from rt_faculty_details b where b.facid=? order by b.posted_date desc ";
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);

			set = pstmt.executeQuery();
			if (set.next()) {
				fbean = new Faculty();

				fbean.setFacid(set.getInt("facid"));
				fbean.setCpfno(set.getString("cpfno"));
				fbean.setUsername(set.getString("name"));
				fbean.setDesignation(set.getString("designation"));
				fbean.setMobileNo(set.getString("mob"));

				fbean.setEmailId(set.getString("email"));
				fbean.setFaxno(set.getString("fax"));
				fbean.setAddress(set.getString("address"));

				if (set.getString("city") != null) {
					fbean.setCity(set.getString("city"));
				} else
					fbean.setCity("");
				fbean.setSpecialisation(set.getString("specialisation"));
				fbean.setAcademics(set.getString("academics"));

				SimpleDateFormat stm = new SimpleDateFormat("MM/dd/yyyy");
				String dos = "";
				if (set.getDate("superannuation") != null) {
					dos = stm.format(set.getDate("superannuation"));
				}
				fbean.setDos(dos);
				// fbean.setDos(res.getString("superannuation"));

				fbean.setTitle(set.getString("program_title"));
				fbean.setCode(set.getString("program_code"));
				if (set.getString("description") != null) {
					fbean.setDescription(set.getString("description"));
				} else
					fbean.setDescription("");

				// String posdate = stm.format(set.getDate("posted_date"));
				fbean.setPosteddt(set.getDate("posted_date"));

			}
		} catch (Exception e) {
			fbean = null;
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt, set);
		}
		return fbean;

	}

	private int getMaxIdFromTable(String tableName, String colName) {
		int id = 0;
		Connection connection=null;
		String query = "select max(" + colName + ") from " + tableName;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			connection = DatasourceConnection.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				id = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(connection, stmt,rs );
		}

		return ++id;
	}

	public boolean insertTalentData(User user, Talent tp) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean flag = false;

		String query = "INSERT INTO TALENT_POOL(TID,CPFNO,NAME,DOB,QUALIFICATION,DOS,DESIGNATION,LST_TEN_FRM,LST_TEN_TO,DEPT,SKILL,DOMAIN,MOB,EMAILID,ADDRESS,WILLING,WILLINGAREA,STATUS) "
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'Active')";

		try {
			conn = DatasourceConnection.getConnection();

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, getMaxIdFromTable("TALENT_POOL", "TID"));
			pstmt.setString(2, user.getCpfNo());

			pstmt.setString(3, user.getUserName());

			SimpleDateFormat stm = new SimpleDateFormat("dd-MM-yyyy");

			pstmt.setDate(4, new java.sql.Date(user.getDateOfBirth().getTime()));
			pstmt.setString(5, tp.getQualification());
			pstmt.setDate(6, new java.sql.Date(user.getDateOfSep().getTime()));

			pstmt.setString(7, user.getDesignation());

			Date frmdate = stm.parse(tp.getLastTenFrm());
			pstmt.setDate(8, new java.sql.Date(frmdate.getTime()));

			Date todate = stm.parse(tp.getLastTenTo());
			pstmt.setDate(9, new java.sql.Date(todate.getTime()));
			pstmt.setString(10, tp.getDepartment());

			pstmt.setString(11, tp.getSkillSet());

			pstmt.setString(12, tp.getDomain());
			pstmt.setString(13, tp.getMobileNo());

			pstmt.setString(14, tp.getEmailId());
			pstmt.setString(15, tp.getAddress());
			pstmt.setString(16, tp.getWilling());
			pstmt.setString(17, tp.getWillingarea());
			flag = 0 < pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt);
		}
		return flag;
	}

	public ArrayList<Talent> getTalentData(String cpfno) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		ArrayList<Talent> tlist = new ArrayList<Talent>();
		StringBuffer query = new StringBuffer();
		try {

			query.append("select a.tid,a.cpfno,a.name,a.dob,a.qualification,a.dos,a.designation,a.lst_ten_frm,a.lst_ten_to,a.dept,a.skill,a.domain,a.mob,a.emailid,a.address,a.willing,a.willingarea,a.posted_date from talent_pool a where a.status='Active' ");
			if (!cpfno.equalsIgnoreCase("0")) {
				query.append(" and a.cpfno='" + cpfno + "' ");
			}
			query.append(" order by a.tid desc ");
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query.toString());

			set = pstmt.executeQuery();
			while (set.next()) {
				Talent tbean = new Talent();
				SimpleDateFormat stm = new SimpleDateFormat("dd-MM-yyyy");
				tbean.setTid(set.getInt("tid"));
				tbean.setCpfno(set.getString("cpfno"));
				tbean.setUsername(set.getString("name"));
				String dob = "";
				if (set.getDate("dob") != null) {
					dob = stm.format(set.getDate("dob"));
				}
				tbean.setDob(dob);
				tbean.setQualification(set.getString("qualification"));
				String dos = "";
				if (set.getDate("dos") != null) {
					dos = stm.format(set.getDate("dos"));
				}
				tbean.setDos(dos);

				tbean.setDesignation(set.getString("designation"));
				String lstfrm = "";
				if (set.getDate("lst_ten_frm") != null) {
					lstfrm = stm.format(set.getDate("lst_ten_frm"));
				}
				String lstto = "";
				if (set.getDate("lst_ten_to") != null) {
					lstto = stm.format(set.getDate("lst_ten_to"));
				}
				tbean.setLastTenFrm(lstfrm);
				tbean.setLastTenTo(lstto);
				tbean.setDepartment(set.getString("dept"));
				tbean.setSkillSet(set.getString("skill"));
				tbean.setDomain(set.getString("domain"));
				tbean.setMobileNo(set.getString("mob"));
				tbean.setEmailId(set.getString("emailid"));
				tbean.setAddress(set.getString("address"));
				tbean.setWilling(set.getString("willing"));
				tbean.setWillingarea(set.getString("willingarea"));

				if(set.getTimestamp("posted_date")!=null) {
					Date dt = new  Date(set.getTimestamp("posted_date").getTime());

					tbean.setPosteddt(dt);
				}
				tlist.add(tbean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt, set);
		}
		return tlist;
	}

	public Talent getTalentDataById(String tid) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		Talent tbean = null;
		String query = "";
		try {

			query = "select a.tid,a.cpfno,a.name,a.dob,a.qualification,a.dos,a.designation,a.lst_ten_frm,a.lst_ten_to,a.dept,a.skill,a.domain,a.mob,a.emailid,a.address,a.willing,a.willingarea,a.posted_date from talent_pool a where a.status='Active' and a.tid=? ";
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(tid));

			set = pstmt.executeQuery();
			while (set.next()) {
				tbean = new Talent();
				SimpleDateFormat stm = new SimpleDateFormat("dd-MM-yyyy");
				tbean.setTid(set.getInt("tid"));
				tbean.setCpfno(set.getString("cpfno"));
				tbean.setUsername(set.getString("name"));
				String dob = "";
				if (set.getDate("dob") != null) {
					dob = stm.format(set.getDate("dob"));
				}
				tbean.setDob(dob);
				tbean.setQualification(set.getString("qualification"));
				String dos = "";
				if (set.getDate("dos") != null) {
					dos = stm.format(set.getDate("dos"));
				}
				tbean.setDos(dos);

				tbean.setDesignation(set.getString("designation"));
				String lstfrm = "";
				if (set.getDate("lst_ten_frm") != null) {
					lstfrm = stm.format(set.getDate("lst_ten_frm"));
				}
				String lstto = "";
				if (set.getDate("lst_ten_to") != null) {
					lstto = stm.format(set.getDate("lst_ten_to"));
				}
				tbean.setLastTenFrm(lstfrm);
				tbean.setLastTenTo(lstto);
				tbean.setDepartment(set.getString("dept"));
				tbean.setSkillSet(set.getString("skill"));
				tbean.setDomain(set.getString("domain"));
				tbean.setMobileNo(set.getString("mob"));
				tbean.setEmailId(set.getString("emailid"));
				tbean.setAddress(set.getString("address"));
				tbean.setWilling(set.getString("willing"));
				tbean.setWillingarea(set.getString("willingarea"));

				Date dt = new java.util.Date(set.getTimestamp("posted_date")
						.getTime());

				tbean.setPosteddt(dt);

			}

		} catch (Exception e) {
			LOGGER.info("getTalentDataById Exception  " + e.getMessage());
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt, set);
		}
		return tbean;

	}
	public User getUserByEmailId(String emailId){
		Connection connection=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		User user=null;
		String query="SELECT * from ONGC_RT_USER WHERE  UPPER(EMAILID) = ? ";
		try{
			connection=DatasourceConnection.getConnection();
			pstmt=connection.prepareStatement(query);

			pstmt.setString(1, emailId.toUpperCase());
			set=pstmt.executeQuery();
			//LOGGER.info(pstmt);
			LOGGER.info("emailId======"+emailId);
			if (set.next()) {
				LOGGER.info("CPF======"+set.getString("CPF_NUMBER"));
				user = new User();
				user.setCpfNo(set.getString("CPF_NUMBER"));
				user.setPassword(set.getString("PASSWORD"));
				user.setTittle(set.getString("TITLE"));
				user.setUserName(set.getString("EMP_NAME"));
				user.setEmailId(set.getString("EMAILID"));
				user.setMobileNo(set.getString("CONTACTNO"));
				user.setLandLineNo(set.getString("LAND_LINE_NUMBER"));
				user.setLocation(set.getString("LOCATION"));
				user.setAddress(set.getString("ADDRESS"));
				user.setCommunicatedAddress(set.getString("COMM_ADDRESS"));
				user.setAboutMe(set.getString("ABOUT_ME"));
				user.setDesignation(set.getString("PAST_DESIGNATION"));
				user.setPanNumber(set.getString("PAN_NUMBER"));
				user.setDateOfBirth(set.getDate("DT_OF_BIRTH"));
				user.setDateOfJoining(set.getDate("DT_OF_JOINING"));
				user.setDateOfSep(set.getDate("DT_OF_SEP"));
				user.setCreatedBy(set.getString("CREATED_BY"));
				user.setCreationDate(set.getDate("CREATION_DATE"));
				user.setActive(set.getInt("ACTIVE"));
				user.setProfilePic(set.getBytes("PROFILE_PIC"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DatasourceConnection.closeConnection(connection, pstmt, set);
		} 
		return user;	

	}
	
	
	public User getReportUserByCpfNo(String cpfNo) {
		LOGGER.info("**********getReportUserByCpfNo()***************");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		User user = null;
		try {
			String query = "SELECT CPF_NUMBER,EMP_NAME,E_MAILID_OFF,LOCAL_ADDRESS,ABOUT_ME,RESIDENCE_ADDRESS,PLACE_POSTING FROM ONGC_USER_MASTER WHERE CPF_NUMBER=?";
			conn = EdbConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cpfNo);

			set = pstmt.executeQuery();
			if (set.next()) {
				user = new User();
				user.setCpfNo(set.getString("CPF_NUMBER"));
				user.setUserName(set.getString("EMP_NAME"));
				user.setEmailId(set.getString("E_MAILID_OFF"));
				//user.setMobileNo(set.getString("MOBILE_NUMBER"));
				user.setLocation(set.getString("PLACE_POSTING"));
				user.setAddress(set.getString("LOCAL_ADDRESS"));
				user.setAboutMe(set.getString("ABOUT_ME"));
				user.setCommunicatedAddress(set.getString("RESIDENCE_ADDRESS"));
				//user.setProfilePic(set.getBytes("PROFILE_PIC"));
			}
		} catch (Exception e) {
			user = null;
			LOGGER.info("In getReportUserByCpfNo Method :: " + e);
		} finally {
			EdbConnection.closeConnection(conn,pstmt,set);
		}
		return user;
	}
	
}
