package com.ongc.liferay.bandhan.dao.Impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.ongc.liferay.bandhan.command.SaveTrustActionCommand;
import com.ongc.liferay.bandhan.connection.DatasourceConnection;
import com.ongc.liferay.bandhan.dao.FeedbackPostDao;
import com.ongc.liferay.bandhan.model.FeedbackCategory;
import com.ongc.liferay.bandhan.model.FeedbackHrCategory;
import com.ongc.liferay.bandhan.model.FeedbackHrEnablers;
import com.ongc.liferay.bandhan.model.FeedbackPost;
import com.ongc.liferay.bandhan.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FeedbackPostDaoImpl implements FeedbackPostDao {

	
	private static final Log LOGGER = LogFactoryUtil.getLog(FeedbackPostDaoImpl.class);
	public FeedbackPost saveFeedbackPost(FeedbackPost post) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query = "INSERT INTO RPTT_FEEDBACK_POST(POST_ID,CATEGORY_ID,CPF_NO,SUBJECT,MESSAGE,NO_OF_REPLIES,MESSAGE1,MESSAGE2,MESSAGE3,MESSAGE4,CELL_NUMBER,CHIEF_ER) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			conn = DatasourceConnection.getConnection();
			int postId = getMaxIdFromTable("RPTT_FEEDBACK_POST", "POST_ID",conn);
			post.setPostId(postId);

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, post.getPostId());
			pstmt.setInt(2, post.getCategoryId());
			pstmt.setString(3, post.getUser().getCpfNo());
			pstmt.setString(4, post.getSubject());

			String kk = post.getMessage();
			String kk1 = kk.substring(0, kk.length() / 5);
			String kk2 = kk.substring(kk.length() / 5, 2 * (kk.length() / 5));
			String kk3 = kk.substring(2 * (kk.length() / 5),
					3 * (kk.length() / 5));
			String kk4 = kk.substring(3 * (kk.length() / 5),
					4 * (kk.length() / 5));
			String kk5 = kk.substring(4 * (kk.length() / 5));
			pstmt.setInt(6, 0);
			pstmt.setString(5, kk1);
			pstmt.setString(7, kk2);
			pstmt.setString(8, kk3);
			pstmt.setString(9, kk4);
			pstmt.setString(10, kk5);
			
			pstmt.setString(11, post.getCellNumber());
			pstmt.setString(12, "NO");
			
			pstmt.executeUpdate();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}

		return post;
	}

	public List<FeedbackPost> getPostListByCategoryId(int categoryId) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		List<FeedbackPost> postList = new ArrayList<FeedbackPost>();
		String query = "SELECT P.POST_ID, P.SUBJECT,P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,P.MESSAGE4,P.POST_DATE,P.NO_OF_REPLIES,P.CHIEF_ER,P.OC_STATUS,P.RV_STATUS,P.CELL_NUMBER,U.CPF_NUMBER,U.EMP_NAME,U.PAST_DESIGNATION AS DESIGNATION,LOCATION PLACE_POSTING,C.DESCRIPTION FROM RPTT_FEEDBACK_POST P, ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID AND P.CATEGORY_ID=?";
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, categoryId);
			set = pstmt.executeQuery();
			FeedbackPost post = null;
			while (set.next()) {
				post = new FeedbackPost();
				post.setUser(new User());
				post.setCategory(new FeedbackCategory());
				post.setPostId(set.getInt("POST_ID"));
				post.setSubject(set.getString("SUBJECT"));
				post.setMessage(set.getString("MESSAGE")+ set.getString("MESSAGE1") + set.getString("MESSAGE2")+ set.getString("MESSAGE3") + set.getString("MESSAGE4"));
				post.setPostDate(set.getDate("POST_DATE"));
				post.setNoOfReplies(set.getInt("NO_OF_REPLIES"));
				post.setChiefEr(set.getString("CHIEF_ER"));
				post.setOpStatus(set.getString("OC_STATUS"));
				post.setRvStatus(set.getString("RV_STATUS"));
				post.setCellNumber(set.getString("CELL_NUMBER"));
				post.getCategory().setDescription(set.getString("DESCRIPTION"));
				post.getUser().setCpfNo(set.getString("CPF_NUMBER"));
				post.getUser().setUserName(set.getString("EMP_NAME"));
				post.getUser().setAddress(set.getString("PLACE_POSTING"));
				post.getUser().setDesignation(set.getString("DESIGNATION"));
				postList.add(post);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt, set);
		}

		return postList;
	}

	public List<FeedbackPost> getPostListByFeedbackId(int feedbackId,
			int startFrom, int showRows, FeedbackCategory category) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		List<FeedbackPost> postList = new ArrayList<FeedbackPost>();
		int catid = category.getCategoryId();
		Date d1 = null, d2 = null;
		SimpleDateFormat stm = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat stm1 = new SimpleDateFormat("yyyy-MM-dd");

		// String query =
		// "SELECT P.POST_ID,P.STATUS, P.SUBJECT,P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,P.MESSAGE4,P.POST_DATE,P.NO_OF_REPLIES, P.CHIEF_ER, P.OC_STATUS, P.RV_STATUS, U.CPF_NUMBER,U.EMP_NAME,U.DESIGNATION,C.DESCRIPTION,coalesce((select max(post_date) from rptt_feedback_comment where post_id=p.post_id), p.post_date) srt_date,coalesce((select (select emp_name from ONGC_RT_USER where CPF_NUMBER=CPF_NO) from rptt_feedback_comment where post_id=p.post_id and post_date=(select max(post_date) from rptt_feedback_comment where post_id=p.post_id)), U.EMP_NAME) lst_name,coalesce((SELECT count(*) from rptt_feedback_comment where authresponse='Y' and post_id=p.post_id),0) auth_status, P.CATEGORY_ID FROM RPTT_FEEDBACK_POST P,ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID AND C.FEEDBACK_ID=? AND p.status=? order by srt_date desc";
		StringBuilder query = new StringBuilder();
		query.append("SELECT Q.SLNO,Q.POST_ID, Q.STATUS, Q.SUBJECT, Q.MESSAGE, Q.MESSAGE1, Q.MESSAGE2, Q.MESSAGE3, Q.MESSAGE4, " 
				+"Q.POST_DATE, Q.NO_OF_REPLIES, "
				+ "Q.CHIEF_ER, Q.OC_STATUS, Q.RV_STATUS, Q.CPF_NUMBER, Q.EMP_NAME, Q.DESIGNATION, Q.PLACE_POSTING, Q.DESCRIPTION, "
				+ "Q.CATEGORY_ID, Q.LAST_COMMENTED_ON,Q.CELL_NUMBER " +
				" FROM (SELECT ROW_NUMBER() OVER(ORDER BY P.LAST_COMMENTED_ON DESC) AS  SLNO, P.POST_ID, P.STATUS, "
				+ "P.SUBJECT, P.MESSAGE, P.MESSAGE1, P.MESSAGE2, P.MESSAGE3, P.MESSAGE4, P.POST_DATE," +
				" P.NO_OF_REPLIES,P.CHIEF_ER, P.OC_STATUS, P.RV_STATUS, U.CPF_NUMBER,U.EMP_NAME,U.PAST_DESIGNATION AS DESIGNATION,U.LOCATION PLACE_POSTING, "
				+ "C.DESCRIPTION, P.CATEGORY_ID, P.LAST_COMMENTED_ON,P.CELL_NUMBER  FROM RPTT_FEEDBACK_POST P,ONGC_RT_USER U, RPTT_FEEDBACK_CATEGORY C "
				+ "WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID AND C.FEEDBACK_ID=? AND p.status=? ");
		
		
		
		if (catid != 0 && catid > 0) {
			query.append("  and P.category_id=" + catid);
		}

		if (!"".equalsIgnoreCase(category.getStartDate())
				&& category.getStartDate() != null
				&& !"abc".equalsIgnoreCase(category.getStartDate().toString())) {
			String startDate = category.getStartDate();
			query.append("   AND P.POST_DATE  >= DATE('" + startDate + "')");
		}
		if (!"".equalsIgnoreCase(category.getEndDate())
				&& category.getEndDate() != null
				&& !"abc".equalsIgnoreCase(category.getEndDate().toString())) {
			String endDate = category.getEndDate();
			query.append("   AND P.POST_DATE  <= DATE('" + endDate + "')");
		}
		if (category.getPostId() != null
				&& !"".equalsIgnoreCase(category.getPostId())
				&& !"abc".equalsIgnoreCase(category.getPostId())) {
			query.append(" and P.post_id=" + category.getPostId());
		}
		if (category.getKeyword() != null && !"".equalsIgnoreCase(category.getKeyword()) && !"abc".equalsIgnoreCase(category.getKeyword())) {
			query.append(" and lower(P.subject) like lower('%" + category.getKeyword() + "%')");
		}
		query.append(" ORDER BY P.LAST_COMMENTED_ON DESC) Q WHERE Q.SLNO>" + startFrom + " and Q.SLNO<=" + (startFrom + showRows));
		// query.append( " ) b)  " +"where rank>"+start+" and rank<="+end);
	
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, feedbackId);
			pstmt.setString(2, "Active");//RTConstant.COMMENT_STATUS_ACTIVE
			set = pstmt.executeQuery();
			LOGGER.info(pstmt);
			FeedbackPost post = null;
			while (set.next()) {
				post = new FeedbackPost();
				post.setUser(new User());
				post.setCategory(new FeedbackCategory());

				post.setPostId(set.getInt("POST_ID"));
				post.setSubject(set.getString("SUBJECT"));
				String comm = set.getString("MESSAGE")
						+ set.getString("MESSAGE1") + set.getString("MESSAGE2")
						+ set.getString("MESSAGE3") + set.getString("MESSAGE4");

				if (comm != null) {
					comm = comm.replaceAll("&gt;", ">");
					comm = comm.replaceAll("&lt;", "<");
				}
				post.setMessage(comm);
				post.setPostDate(set.getDate("POST_DATE"));
				post.setSrt_date(set.getDate("LAST_COMMENTED_ON"));
				post.setNoOfReplies(set.getInt("NO_OF_REPLIES"));
				post.setChiefEr(set.getString("CHIEF_ER"));
				post.setOpStatus(set.getString("OC_STATUS"));
				post.setRvStatus(set.getString("RV_STATUS"));
				post.getCategory().setDescription(set.getString("DESCRIPTION"));
				post.setCategoryId(set.getInt("CATEGORY_ID"));
				post.getUser().setCpfNo(set.getString("CPF_NUMBER"));
				post.getUser().setUserName(set.getString("EMP_NAME"));
				post.getUser().setAddress(set.getString("PLACE_POSTING"));
				post.getUser().setDesignation(set.getString("DESIGNATION"));
				post.setStatus(set.getString("STATUS"));
				// post.setLastUpdName(set.getString("lst_name"));
				// post.setAuth_status(set.getString("auth_status"));
				post.setCellNumber(set.getString("CELL_NUMBER"));
				postList.add(post);
			}
			pstmt.close();
			String lastUpdName = null;
			String auth_status = null;
			for (FeedbackPost fpost : postList) {
				lastUpdName = getLastUpdNameByPostId(fpost.getPostId());
				fpost.setLastUpdName((lastUpdName == null) ? fpost.getUser().getUserName() : lastUpdName);

				auth_status = getAuth_statusByPostId(fpost.getPostId());
				fpost.setAuth_status((auth_status == null) ? "0" : auth_status);
			}
			category.setStartDate(category.getStartDate());
			category.setEndDate(category.getEndDate());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}

		return postList;
	}
	public List<FeedbackPost> getPostListByFeedbackIdAll(int feedbackId, FeedbackCategory category) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		List<FeedbackPost> postList = new ArrayList<FeedbackPost>();
		int catid = category.getCategoryId();
		Date d1 = null, d2 = null;
		SimpleDateFormat stm = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat stm1 = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder query = new StringBuilder();
		query.append("SELECT Q.SLNO,Q.POST_ID, Q.STATUS, Q.SUBJECT, Q.MESSAGE, Q.MESSAGE1, Q.MESSAGE2, Q.MESSAGE3, Q.MESSAGE4, " 
				+"Q.POST_DATE, Q.NO_OF_REPLIES, "
				+ "Q.CHIEF_ER, Q.OC_STATUS, Q.RV_STATUS, Q.CPF_NUMBER, Q.EMP_NAME, Q.DESIGNATION, Q.PLACE_POSTING, Q.DESCRIPTION, "
				+ "Q.CATEGORY_ID, Q.LAST_COMMENTED_ON,Q.CELL_NUMBER,Q.AUTHRESPONSE " +
				" FROM (SELECT ROW_NUMBER() OVER(ORDER BY P.LAST_COMMENTED_ON DESC) AS  SLNO, P.POST_ID, P.STATUS, "
				+ "P.SUBJECT, P.MESSAGE, P.MESSAGE1, P.MESSAGE2, P.MESSAGE3, P.MESSAGE4, P.POST_DATE," +
				" P.NO_OF_REPLIES,P.CHIEF_ER, P.OC_STATUS, P.RV_STATUS, U.CPF_NUMBER,U.EMP_NAME,U.PAST_DESIGNATION AS DESIGNATION,U.LOCATION PLACE_POSTING, "
				+ "C.DESCRIPTION, P.CATEGORY_ID, P.LAST_COMMENTED_ON,P.CELL_NUMBER,P.AUTHRESPONSE  FROM RPTT_FEEDBACK_POST P,ONGC_RT_USER U, RPTT_FEEDBACK_CATEGORY C "
				+ "WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID AND C.FEEDBACK_ID=? AND p.status=? ");
		
		
		
		if (catid != 0 && catid > 0) {
			query.append("  and P.category_id=" + catid);
		}

		if (!"".equalsIgnoreCase(category.getStartDate())
				&& category.getStartDate() != null
				&& !"abc".equalsIgnoreCase(category.getStartDate().toString())) {
			String startDate = category.getStartDate();
			query.append("   AND P.POST_DATE  >= DATE('" + startDate + "')");
		}
		if (!"".equalsIgnoreCase(category.getEndDate())
				&& category.getEndDate() != null
				&& !"abc".equalsIgnoreCase(category.getEndDate().toString())) {
			String endDate = category.getEndDate();
			query.append("   AND P.POST_DATE  <= DATE('" + endDate + "')");
		}
		if (category.getPostId() != null
				&& !"".equalsIgnoreCase(category.getPostId())
				&& !"abc".equalsIgnoreCase(category.getPostId())) {
			query.append(" and P.post_id=" + category.getPostId());
		}
		if (category.getKeyword() != null && !"".equalsIgnoreCase(category.getKeyword()) && !"abc".equalsIgnoreCase(category.getKeyword())) {
			query.append(" and lower(P.subject) like lower('%" + category.getKeyword() + "%')");
		}
		 query.append( " ) Q ");
	
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, feedbackId);
			pstmt.setString(2, "Active");//RTConstant.COMMENT_STATUS_ACTIVE
			set = pstmt.executeQuery();
			FeedbackPost post = null;
			while (set.next()) {
				post = new FeedbackPost();
				post.setUser(new User());
				post.setCategory(new FeedbackCategory());

				post.setPostId(set.getInt("POST_ID"));
				post.setSubject(set.getString("SUBJECT"));
				String comm = set.getString("MESSAGE")
						+ set.getString("MESSAGE1") + set.getString("MESSAGE2")
						+ set.getString("MESSAGE3") + set.getString("MESSAGE4");

				if (comm != null) {
					comm = comm.replaceAll("&gt;", ">");
					comm = comm.replaceAll("&lt;", "<");
				}
				post.setMessage(comm);
				post.setPostDate(set.getDate("POST_DATE"));
				post.setSrt_date(set.getDate("LAST_COMMENTED_ON"));
				post.setNoOfReplies(set.getInt("NO_OF_REPLIES"));
				post.setChiefEr(set.getString("CHIEF_ER"));
				post.setOpStatus(set.getString("OC_STATUS"));
				post.setRvStatus(set.getString("RV_STATUS"));
				post.getCategory().setDescription(set.getString("DESCRIPTION"));
				post.setCategoryId(set.getInt("CATEGORY_ID"));
				post.getUser().setCpfNo(set.getString("CPF_NUMBER"));
				post.getUser().setUserName(set.getString("EMP_NAME"));
				post.getUser().setAddress(set.getString("PLACE_POSTING"));
				post.getUser().setDesignation(set.getString("DESIGNATION"));
				post.setStatus(set.getString("STATUS"));
				// post.setLastUpdName(set.getString("lst_name"));
				
				///log.info("set::"+set.getString("AUTHRESPONSE"));
				post.setAuth_status(set.getString("AUTHRESPONSE"));
				post.setCellNumber(set.getString("CELL_NUMBER"));
				postList.add(post);
			}
			pstmt.close();
			String lastUpdName = null;
			String auth_status = null;
			for (FeedbackPost fpost : postList) {
				lastUpdName = getLastUpdNameByPostId(fpost.getPostId());
				fpost.setLastUpdName((lastUpdName == null) ? fpost.getUser().getUserName() : lastUpdName);

				/*auth_status = getAuth_statusByPostId(fpost.getPostId());
				fpost.setAuth_status((fpost.getAuth_status() == null) ? "0" : auth_status);*/
			}
			category.setStartDate(category.getStartDate());
			category.setEndDate(category.getEndDate());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}

		return postList;
	}

	public int getNoOfFeedbacks(FeedbackCategory category, String stat) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		int count = 0;
		// String query =
		// "SELECT P.POST_ID,P.STATUS, P.SUBJECT,P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,P.MESSAGE4,P.POST_DATE,P.NO_OF_REPLIES, P.CHIEF_ER, P.OC_STATUS, P.RV_STATUS, U.CPF_NUMBER,U.EMP_NAME,U.DESIGNATION,C.DESCRIPTION,coalesce((select max(post_date) from rptt_feedback_comment where post_id=p.post_id), p.post_date) srt_date,coalesce((select (select emp_name from ONGC_RT_USER where CPF_NUMBER=CPF_NO) from rptt_feedback_comment where post_id=p.post_id and post_date=(select max(post_date) from rptt_feedback_comment where post_id=p.post_id)), U.EMP_NAME) lst_name,coalesce((SELECT count(*) from rptt_feedback_comment where authresponse='Y' and post_id=p.post_id),0) auth_status, P.CATEGORY_ID FROM RPTT_FEEDBACK_POST P,ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID AND C.FEEDBACK_ID=? AND p.status=? order by srt_date desc";
		StringBuilder query = new StringBuilder();
		Date d1 = null, d2 = null;
		SimpleDateFormat stm = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat stm1 = new SimpleDateFormat("yyyy-MM-dd");

		if (stat.equalsIgnoreCase("Active"))
			query.append("Select count(*) as cnt from rptt_feedback_post p where lower(status) =lower('"+ stat + "')");
		else
			query.append("Select count(*) as cnt from rptt_feedback_post p where (status) in ('Active','Inactive') ");

		int catid = category.getCategoryId();
		if (catid != 0 && catid > 0) {
			query.append("  and p.category_id=" + catid);
		}

		if (!"".equalsIgnoreCase(category.getStartDate())
				&& category.getStartDate() != null
				&& !"abc".equalsIgnoreCase(category.getStartDate().toString())) {
			String startDate = category.getStartDate();
			query.append("   AND P.POST_DATE  >= DATE('" + startDate + "')");
		}
		if (!"".equalsIgnoreCase(category.getEndDate())
				&& category.getEndDate() != null
				&& !"abc".equalsIgnoreCase(category.getEndDate().toString())) {
			String endDate = category.getEndDate();
			query.append("   AND P.POST_DATE  <= DATE('" + endDate + "')");
		}
		if (category.getPostId() != null
				&& !"".equalsIgnoreCase(category.getPostId())
				&& !"abc".equalsIgnoreCase(category.getPostId())) {
			query.append(" and p.post_id=" + category.getPostId());
		}
		if (category.getKeyword() != null && !"".equalsIgnoreCase(category.getKeyword()) && !"abc".equalsIgnoreCase(category.getKeyword())) {
			query.append(" and lower(p.subject) like lower('%"+ category.getKeyword() + "%')");
		}
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query.toString());
			// pstmt.setString(1, ReportConstant.COMMENT_STATUS_ACTIVE);
			set = pstmt.executeQuery();

			while (set.next()) {
				count = set.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}
		return count;
	}

	public String getCatDesc(String catId) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String desc = "";
		// String query =
		// "SELECT P.POST_ID,P.STATUS, P.SUBJECT,P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,P.MESSAGE4,P.POST_DATE,P.NO_OF_REPLIES, P.CHIEF_ER, P.OC_STATUS, P.RV_STATUS, U.CPF_NUMBER,U.EMP_NAME,U.DESIGNATION,C.DESCRIPTION,coalesce((select max(post_date) from rptt_feedback_comment where post_id=p.post_id), p.post_date) srt_date,coalesce((select (select emp_name from ONGC_RT_USER where CPF_NUMBER=CPF_NO) from rptt_feedback_comment where post_id=p.post_id and post_date=(select max(post_date) from rptt_feedback_comment where post_id=p.post_id)), U.EMP_NAME) lst_name,coalesce((SELECT count(*) from rptt_feedback_comment where authresponse='Y' and post_id=p.post_id),0) auth_status, P.CATEGORY_ID FROM RPTT_FEEDBACK_POST P,ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID AND C.FEEDBACK_ID=? AND p.status=? order by srt_date desc";
		String query = "Select description from rptt_feedback_category a where a.category_id="
				+ catId;
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			// pstmt.setString(1, ReportConstant.COMMENT_STATUS_ACTIVE);
			set = pstmt.executeQuery();

			while (set.next()) {
				desc = set.getString("description");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}

		return desc;
	}

	public List<FeedbackPost> searchPostList(FeedbackCategory category) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		List<FeedbackPost> postList = new ArrayList<FeedbackPost>();
		try {
			pstmt=createQuery(category);
			LOGGER.info(pstmt);
			set = pstmt.executeQuery();
			FeedbackPost post = new FeedbackPost();
			while (set.next()) {
				post = new FeedbackPost();
				post.setUser(new User());
				post.setCategory(new FeedbackCategory());
				post.setPostId(set.getInt("POST_ID"));
				post.setSubject(set.getString("SUBJECT"));
				post.setMessage(set.getString("MESSAGE") + set.getString("MESSAGE1") + set.getString("MESSAGE2") + set.getString("MESSAGE3") + set.getString("MESSAGE4"));
				post.setPostDate(set.getDate("POST_DATE"));
				post.setSrt_date(set.getDate("srt_date"));
				post.setNoOfReplies(set.getInt("NO_OF_REPLIES"));
				post.setOpStatus(set.getString("OC_STATUS"));
				post.setRvStatus(set.getString("RV_STATUS"));
				post.getCategory().setDescription(set.getString("DESCRIPTION"));
				post.setCategoryId(set.getInt("CATEGORY_ID"));
				post.getUser().setCpfNo(set.getString("CPF_NUMBER"));
				post.getUser().setUserName(set.getString("EMP_NAME"));
//				post.getUser().setAddress(set.getString("PLACE_POSTING"));
				post.getUser().setDesignation(set.getString("PAST_DESIGNATION"));
				post.setStatus(set.getString("STATUS"));
				post.setLastUpdName(set.getString("lst_name"));
				post.setAuth_status(set.getString("auth_status"));
				postList.add(post);
			}
			post.setStartDate(category.getStartDate());
			post.setEndDate(category.getEndDate());
			post.setCategId(category.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}

		return postList;
	}

	private PreparedStatement createQuery(FeedbackCategory category) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		String query = "";
		try {
			conn = DatasourceConnection.getConnection();
			if (category != null) {
				if (category.getPostId() == null || category.getPostId().equals("")) {
					if (category.getFeedbackId() == 0)
						category.setFeedbackId(1);
					if (category.getCategoryId() == 0
							&& (category.getStartDate() == null
									|| category.getStartDate().trim().length() < 3
									|| category.getEndDate() == null || category
									.getEndDate().trim().length() < 3)) {

						query = "SELECT P.POST_ID,P.STATUS,P.SUBJECT,P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,P.MESSAGE4,P.POST_DATE,P.NO_OF_REPLIES, P.OC_STATUS, P.RV_STATUS,U.CPF_NUMBER,U.EMP_NAME,U.PAST_DESIGNATION,"
								+ "C.DESCRIPTION,coalesce((select max(post_date) from rptt_feedback_comment where post_id=p.post_id), p.post_date) srt_date,coalesce((select (select emp_name from ONGC_RT_USER where CPF_NUMBER=CPF_NO) from rptt_feedback_comment where post_id=p.post_id and post_date=(select max(post_date) from rptt_feedback_comment where post_id=p.post_id)), U.EMP_NAME) lst_name,coalesce((SELECT count(*) from rptt_feedback_comment where authresponse='Y' and post_id=p.post_id),0) auth_status, P.CATEGORY_ID FROM RPTT_FEEDBACK_POST P,ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID AND C.FEEDBACK_ID=? order by srt_date desc";
						pstmt = conn.prepareStatement(query);
						pstmt.setInt(1, category.getFeedbackId());
					} else if (category.getCategoryId() != 0
							&& (category.getStartDate() == null
									|| category.getStartDate().trim().length() < 3
									|| category.getEndDate() == null || category
									.getEndDate().trim().length() < 3)) {

						query = "SELECT P.POST_ID,P.STATUS,P.SUBJECT,P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,P.MESSAGE4,P.POST_DATE,P.NO_OF_REPLIES, P.OC_STATUS, P.RV_STATUS,U.CPF_NUMBER,"
								+ "U.EMP_NAME,U.PAST_DESIGNATION,C.DESCRIPTION,coalesce((select max(post_date) from rptt_feedback_comment where post_id=p.post_id), p.post_date) srt_date,coalesce((select (select emp_name from ONGC_RT_USER where CPF_NUMBER=CPF_NO) from rptt_feedback_comment where post_id=p.post_id and post_date=(select max(post_date) from rptt_feedback_comment where post_id=p.post_id)), U.EMP_NAME) lst_name,coalesce((SELECT count(*) from rptt_feedback_comment where authresponse='Y' and post_id=p.post_id),0) auth_status, P.CATEGORY_ID FROM RPTT_FEEDBACK_POST P,ONGC_RT_USER U,"
								+ "RPTT_FEEDBACK_CATEGORY C WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID AND C.CATEGORY_ID=? order by srt_date desc";

						pstmt = conn.prepareStatement(query);
						pstmt.setInt(1, category.getCategoryId());
						// category.getCategoryId());
					} else if (category.getCategoryId() == 0
							&& (category.getStartDate() != null
									&& category.getStartDate().trim().length() > 3
									&& category.getEndDate() != null && category
									.getEndDate().trim().length() > 3)) {

						query = "SELECT P.POST_ID,P.STATUS,P.SUBJECT,P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,P.MESSAGE4,P.POST_DATE,P.NO_OF_REPLIES, P.OC_STATUS, P.RV_STATUS,"
								+ "U.CPF_NUMBER,U.EMP_NAME,U.PAST_DESIGNATION,C.DESCRIPTION,coalesce((select max(post_date) from rptt_feedback_comment where post_id=p.post_id), p.post_date) srt_date,coalesce((select (select emp_name from ONGC_RT_USER where CPF_NUMBER=CPF_NO) from rptt_feedback_comment where post_id=p.post_id and post_date=(select max(post_date) from rptt_feedback_comment where post_id=p.post_id)), U.EMP_NAME) lst_name,coalesce((SELECT count(*) from rptt_feedback_comment where authresponse='Y' and post_id=p.post_id),0) auth_status, P.CATEGORY_ID FROM RPTT_FEEDBACK_POST P,"
								+ "ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID AND "
								+ "C.FEEDBACK_ID=? AND TO_DATE(TO_CHAR(P.POST_DATE ,'YYYY-MM-DD' ) ,'YYYY-MM-DD') >= TO_DATE(?,'YYYY-MM-DD') "
								+ "AND TO_DATE(TO_CHAR(P.POST_DATE ,'YYYY-MM-DD' ) ,'YYYY-MM-DD')  <= TO_DATE(?,'YYYY-MM-DD') order by srt_date desc";

						pstmt = conn.prepareStatement(query);
						// Date d1=DateUtils.parseDate(category.getStartDate());
						// Date d2=DateUtils.parseDate(category.getEndDate());

						pstmt.setInt(1, category.getFeedbackId());
						pstmt.setString(2, category.getStartDate());
						pstmt.setString(3, category.getEndDate());
					} else {

						query = "SELECT P.POST_ID,P.STATUS,P.SUBJECT,P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,P.MESSAGE4,P.POST_DATE,P.NO_OF_REPLIES, P.OC_STATUS, P.RV_STATUS,"
								+ "U.CPF_NUMBER,U.EMP_NAME,U.PAST_DESIGNATION,C.DESCRIPTION,coalesce((select max(post_date) from rptt_feedback_comment where post_id=p.post_id), p.post_date) srt_date,coalesce((select (select emp_name from ONGC_RT_USER where CPF_NUMBER=CPF_NO) from rptt_feedback_comment where post_id=p.post_id and post_date=(select max(post_date) from rptt_feedback_comment where post_id=p.post_id)), U.EMP_NAME) lst_name,coalesce((SELECT count(*) from rptt_feedback_comment where authresponse='Y' and post_id=p.post_id),0) auth_status, P.CATEGORY_ID FROM RPTT_FEEDBACK_POST P,"
								+ "ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER "
								+ "AND C.CATEGORY_ID=? AND CAST( P.POST_DATE as date)  >= TO_DATE(?,'YYYY-MM-DD') "
								+ " AND CAST( P.POST_DATE as date)  <= TO_DATE(?,'YYYY-MM-DD') order by srt_date desc";

						pstmt = conn.prepareStatement(query);
						// Date d1=DateUtils.parseDate(category.getStartDate());
						// Date d2=DateUtils.parseDate(category.getEndDate());

						pstmt.setInt(1, category.getCategoryId());
						pstmt.setString(2, category.getStartDate());
						pstmt.setString(3, category.getEndDate());
					}
				} else {
					query = "SELECT P.POST_ID,P.STATUS,P.SUBJECT,P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,P.MESSAGE4,P.POST_DATE,P.NO_OF_REPLIES, P.OC_STATUS, P.RV_STATUS,U.CPF_NUMBER,U.EMP_NAME,U.PAST_DESIGNATION,"
							+ "C.DESCRIPTION,coalesce((select max(post_date) from rptt_feedback_comment where post_id=p.post_id), p.post_date) srt_date,coalesce((select (select emp_name from ONGC_RT_USER where CPF_NUMBER=CPF_NO) from rptt_feedback_comment where post_id=p.post_id and post_date=(select max(post_date) from rptt_feedback_comment where post_id=p.post_id)), U.EMP_NAME) lst_name,coalesce((SELECT count(*) from rptt_feedback_comment where authresponse='Y' and post_id=p.post_id),0) auth_status, P.CATEGORY_ID FROM RPTT_FEEDBACK_POST P,ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID AND P.POST_ID=? order by srt_date desc";

					pstmt = conn.prepareStatement(query);
					// pstmt.setInt(1,category.getFeedbackId());
					pstmt.setInt(1, Integer.parseInt(category.getPostId()));
				}
			} else {
				query = "SELECT P.POST_ID,P.STATUS,P.SUBJECT,P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,P.MESSAGE4,P.POST_DATE,"
						+ "P.NO_OF_REPLIES, P.OC_STATUS, P.RV_STATUS,U.CPF_NUMBER,U.EMP_NAME,U.PAST_DESIGNATION,"
						+ "C.DESCRIPTION,coalesce((select max(post_date) from rptt_feedback_comment where post_id=p.post_id), p.post_date) srt_date,coalesce((select (select emp_name from ONGC_RT_USER where CPF_NUMBER=CPF_NO) from rptt_feedback_comment where post_id=p.post_id and post_date=(select max(post_date) from rptt_feedback_comment where post_id=p.post_id)), U.EMP_NAME) lst_name,coalesce((SELECT count(*) from rptt_feedback_comment where authresponse='Y' and post_id=p.post_id),0) auth_status, P.CATEGORY_ID FROM RPTT_FEEDBACK_POST P,ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C "
						+ "WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID AND C.FEEDBACK_ID=? order by srt_date desc";

				// log.info("category based : Nothing selected..."+query);
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, 1);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pstmt;
	}

	public boolean updateNoOfComment(int postId, boolean type) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		boolean flage = false;
		String query = null;
		if (type) {
			query = "UPDATE RPTT_FEEDBACK_POST SET NO_OF_REPLIES=(NO_OF_REPLIES+1), LAST_COMMENTED_ON=CURRENT_TIMESTAMP WHERE POST_ID=?";
		} else {
			query = "UPDATE RPTT_FEEDBACK_POST SET NO_OF_REPLIES=(NO_OF_REPLIES - 1), LAST_COMMENTED_ON=CURRENT_TIMESTAMP WHERE POST_ID=?";
		}
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, postId);
			flage = 0 < pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt);
		}

		return flage;
	}

	public FeedbackPost getFeedbackPost(FeedbackPost post) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query = "SELECT P.POST_ID, P.SUBJECT,P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,P.MESSAGE4,P.POST_DATE,P.NO_OF_REPLIES,U.CPF_NUMBER,U.EMP_NAME,U.COMM_ADDRESS OFFICE_ADDRESS,U.PAST_DESIGNATION DESIGNATION,UPPER(U.LOCATION) PLACE_POSTING,C.DESCRIPTION, P.OC_STATUS, P.RV_STATUS, P.HR_CAT_ID, P.CATEGORY_ID, P.CHIEF_ER,u.LOCATION sub_location FROM RPTT_FEEDBACK_POST P,ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID AND P.POST_ID=?";
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, post.getPostId());
			set = pstmt.executeQuery();
			if (set.next()) {
				post.setUser(new User());
				post.setCategory(new FeedbackCategory());
				post.setPostId(set.getInt("POST_ID"));
				post.setSubject(set.getString("SUBJECT"));

				String comm = set.getString("MESSAGE")
						+ set.getString("MESSAGE1") + set.getString("MESSAGE2")
						+ set.getString("MESSAGE3") + set.getString("MESSAGE4");
				if (comm != null) {
					comm = comm.replaceAll("&gt;", ">");
					comm = comm.replaceAll("&lt;", "<");
				}
				post.setMessage(comm);
				// post.setPostDate(set.getDate("POST_DATE"));
				post.setPostDate(set.getTimestamp("POST_DATE"));
				post.setNoOfReplies(set.getInt("NO_OF_REPLIES"));
				post.getCategory().setDescription(set.getString("DESCRIPTION"));
				post.getCategory().setCategoryId(set.getInt("CATEGORY_ID"));
				post.getUser().setCpfNo(set.getString("CPF_NUMBER"));
				post.getUser().setUserName(set.getString("EMP_NAME"));
				post.getUser().setAddress(set.getString("PLACE_POSTING"));
				post.getUser().setDesignation(set.getString("DESIGNATION"));
				post.getUser().setLocation(set.getString("PLACE_POSTING"));
				//post.getUser().setSubLocation(set.getString("sub_location"));
				post.setOpStatus(set.getString("OC_STATUS"));
				post.setRvStatus(set.getString("RV_STATUS"));
				post.setHrCatId(set.getInt("HR_CAT_ID"));
				post.setChiefEr(set.getString("CHIEF_ER"));
			} else {
				post = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}
		return post;
	}

	public boolean saveFeedbackLike(String postid, String cpfnum, String status) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query = "INSERT INTO RPTT_FEEDBACK_LIKES(HITS_ID,POST_ID, CPF_NO, STATUS)VALUES(?,?,?,?)";
		boolean flage = true;
		try {
			if (!getFeedBackLikeByCpfNo(cpfnum, postid)) {
				conn = DatasourceConnection.getConnection();
				int id = getMaxIdFromTable("RPTT_FEEDBACK_LIKES", "HITS_ID",conn);
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, id);
				pstmt.setString(2, postid);
				pstmt.setString(3, cpfnum);
				pstmt.setString(4, status);
				flage = pstmt.executeUpdate() > 0;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt);
		}
		return flage;
	}

	public boolean getFeedBackLikeByCpfNo(String cpf, String postid) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query = "SELECT post_date FROM RPTT_FEEDBACK_LIKES WHERE CPF_NO =? AND POST_ID = ? and STATUS='L'";
		boolean flag = true;
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cpf);
			pstmt.setString(2, postid);
			set = pstmt.executeQuery();
			// SimpleDateFormat stm =new SimpleDateFormat("MM/dd/yyyy");
			// String date =stm.format(set.getDate("post_date"));

			flag = set.next();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt);
		}
		return flag;
	}

	public int getFeedbackLikeCount(String postid) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query = "SELECT count(HITS_ID) as TOTAL FROM RPTT_FEEDBACK_LIKES WHERE POST_ID = ? AND STATUS='L' ";
		int i = 0;
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, postid);
			set = pstmt.executeQuery();
			while (set.next())
				i = set.getInt("TOTAL");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt);
		}
		return i;
	}

	
	public int getFeedbackDisLikeCount(String postid) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query = "SELECT count(HITS_ID) as TOTAL FROM RPTT_FEEDBACK_LIKES WHERE POST_ID = ? AND STATUS='D' ";
		int i = 0;
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, postid);
			set = pstmt.executeQuery();
			while (set.next())
				i = set.getInt("TOTAL");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt);
		}
		return i;
	}

	public boolean getFeedbackLikeByCPf(String postid, String cpfnum) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query = "SELECT HITS_ID as TOTAL FROM RPTT_FEEDBACK_LIKES WHERE POST_ID = ? and CPF_NO=? ";
		boolean flag = false;
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, postid);
			pstmt.setString(2, cpfnum);
			set = pstmt.executeQuery();

			flag = set.next();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt);
		}
		return flag;
	}

	public boolean saveFeedbackHits(String postid, String cpfnum) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query = "INSERT INTO rptt_feedback_hits(HITS_ID,POST_ID, CPF_NO)VALUES(?,?,?)";
		boolean flage = true;
		try {

			if (!getFeedBackHitByCpfNo(postid, cpfnum)) {
				conn = DatasourceConnection.getConnection();
				int id = getMaxIdFromTable("rptt_feedback_hits", "HITS_ID", conn);
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, id);
				pstmt.setString(2, postid);
				pstmt.setString(3, cpfnum);

				flage = pstmt.executeUpdate() > 0;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt);
		}
		return flage;
	}

	public boolean getFeedBackHitByCpfNo(String postid, String cpfnum) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query = "SELECT HITS_ID as TOTAL FROM RPTT_FEEDBACK_HITS WHERE POST_ID = ? and CPF_NO=? ";
		boolean flag = false;
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, postid);
			pstmt.setString(2, cpfnum);
			set = pstmt.executeQuery();

			flag = set.next();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt);
		}
		return flag;
	}

	public int getFeedbackHitCount(String postid) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query = "SELECT count(HITS_ID) as TOTAL FROM RPTT_FEEDBACK_HITS WHERE POST_ID = ? ";
		int i = 0;
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, postid);
			set = pstmt.executeQuery();
			while (set.next())
				i = set.getInt("TOTAL");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt);
		}
		return i;
	}

	public boolean archiveFeedbackPost(String status, int postId) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		boolean flage = false;
		String query = "UPDATE RPTT_FEEDBACK_POST SET STATUS = ? WHERE POST_ID = ?";
		PreparedStatement pstmt1 = null;
		try {
			conn = DatasourceConnection.getConnection();
			pstmt1 = conn.prepareStatement(query);
			pstmt1.setString(1, status);
			pstmt1.setInt(2, postId);
			flage = 0 < pstmt1.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt);
		}
		return flage;
	}
	/**********************************************************************************
	 * getCommentListByStatus in case of (selected cpf)
	 ***********************************************************************************/

	public List<FeedbackPost> getCommentListByStatus(int feedbackId,
			
			int startFrom, int showRows, FeedbackCategory category) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		List<FeedbackPost> commentList = new ArrayList<FeedbackPost>();
		
		int catid = category.getCategoryId();
		Date d1 = null, d2 = null;
		SimpleDateFormat stm = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat stm1 = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder query = new StringBuilder();
		

		query.append("SELECT Q.SLNO,Q.POST_ID, Q.STATUS, Q.SUBJECT, Q.MESSAGE, Q.MESSAGE1, Q.MESSAGE2, Q.MESSAGE3, Q.MESSAGE4, Q.POST_DATE, Q.NO_OF_REPLIES, "
				+ "Q.CHIEF_ER, Q.OC_STATUS, Q.RV_STATUS, Q.CPF_NUMBER, Q.EMP_NAME, Q.DESIGNATION, Q.PLACE_POSTING, Q.DESCRIPTION, Q.CATEGORY_ID, Q.LAST_COMMENTED_ON FROM "
				+ "(SELECT ROW_NUMBER() OVER(ORDER BY P.LAST_COMMENTED_ON DESC) AS  SLNO, P.POST_ID, P.STATUS, P.SUBJECT, P.MESSAGE, P.MESSAGE1, P.MESSAGE2, P.MESSAGE3, P.MESSAGE4, "
				+ "P.POST_DATE,P.NO_OF_REPLIES,P.CHIEF_ER, P.OC_STATUS, P.RV_STATUS, U.CPF_NUMBER,U.EMP_NAME,U.PAST_DESIGNATION DESIGNATION,U.LOCATION PLACE_POSTING, "
				+ "C.DESCRIPTION, P.CATEGORY_ID, P.LAST_COMMENTED_ON  FROM RPTT_FEEDBACK_POST P,ONGC_RT_USER U, "
				+ "RPTT_FEEDBACK_CATEGORY C WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID AND C.FEEDBACK_ID=? ");
		if (catid != 0 && catid > 0) {
			query.append("  and p.category_id=" + catid);
		}

		if (!"".equalsIgnoreCase(category.getStartDate()) && category.getStartDate() != null && !"abc".equalsIgnoreCase(category.getStartDate().toString())) {
			String startDate = category.getStartDate();
			/*try {
				d1 = stm.parse(category.getStartDate());
				startDate = stm1.format(d1);
			} catch (ParseException e) {
				log.debug(e);
			}*/
			query.append("   AND p.POST_DATE  >= DATE('" + startDate + "')");
		}
		if (!"".equalsIgnoreCase(category.getEndDate()) && category.getEndDate() != null && !"abc".equalsIgnoreCase(category.getEndDate().toString())) {
			String endDate = category.getEndDate();
			
			query.append("   AND p.POST_DATE  <= DATE('" + endDate + "')");
		}
		if (category.getPostId() != null && !"".equalsIgnoreCase(category.getPostId()) && !"abc".equalsIgnoreCase(category.getPostId())) {
			query.append(" and p.post_id=" + category.getPostId());
		}
		if (category.getKeyword() != null && !"".equalsIgnoreCase(category.getKeyword()) && !"abc".equalsIgnoreCase(category.getKeyword())) {
			query.append(" and lower(p.subject) like lower('%"+ category.getKeyword() + "%')");
		}
		query.append(" ORDER BY P.LAST_COMMENTED_ON DESC) Q WHERE Q.SLNO>"+ startFrom + " and Q.SLNO<=" + (startFrom + showRows));

		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, feedbackId);
			set = pstmt.executeQuery();
			FeedbackPost post = null;
			while (set.next()) {
				post = new FeedbackPost();
				post.setUser(new User());
				post.setCategory(new FeedbackCategory());
				post.setPostId(set.getInt("POST_ID"));
				post.setSubject(set.getString("SUBJECT"));
				String comm = set.getString("MESSAGE") + set.getString("MESSAGE1") + set.getString("MESSAGE2") + set.getString("MESSAGE3") + set.getString("MESSAGE4");
				if (comm != null) {
					comm = comm.replaceAll("&gt;", ">");
					comm = comm.replaceAll("&lt;", "<");
				}
				post.setMessage(comm);
				post.setPostDate(set.getDate("POST_DATE"));
				post.setSrt_date(set.getDate("LAST_COMMENTED_ON"));
				post.setNoOfReplies(set.getInt("NO_OF_REPLIES"));
				post.setChiefEr(set.getString("CHIEF_ER"));
				post.setOpStatus(set.getString("OC_STATUS"));
				post.setRvStatus(set.getString("RV_STATUS"));
				post.getCategory().setDescription(set.getString("DESCRIPTION"));
				post.setCategoryId(set.getInt("CATEGORY_ID"));
				post.getUser().setCpfNo(set.getString("CPF_NUMBER"));
				post.getUser().setUserName(set.getString("EMP_NAME"));
				post.getUser().setAddress(set.getString("PLACE_POSTING"));
				post.getUser().setDesignation(set.getString("DESIGNATION"));
				post.setStatus(set.getString("STATUS"));
				// post.setLastUpdName(set.getString("LST_NAME"));
				// post.setAuth_status(set.getString("AUTH_STATUS"));
				commentList.add(post);
			}
			pstmt.close();
			String lastUpdName = null;
			String auth_status = null;
			for (FeedbackPost fpost : commentList) {
				
				lastUpdName = getLastUpdNameByPostId(fpost.getPostId());
				fpost.setLastUpdName((lastUpdName == null) ? fpost.getUser().getUserName() : lastUpdName);

				auth_status = getAuth_statusByPostId(fpost.getPostId());
				fpost.setAuth_status((auth_status == null) ? "0" : auth_status);
			}

			category.setStartDate(category.getStartDate());
			category.setEndDate(category.getEndDate());
			category.setKeyword(category.getKeyword());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}
		return commentList;
	}

	public Date getSrt_dateByPostId(int postId) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		Date srt_date = null;
		try {
			conn = DatasourceConnection.getConnection();
			String query1 = "SELECT MAX(POST_DATE) FROM RPTT_FEEDBACK_COMMENT WHERE POST_ID=?";
			pstmt = conn.prepareStatement(query1);
			pstmt.setInt(1, postId);
			set = pstmt.executeQuery();
			while (set.next()) {
				srt_date = set.getDate(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}
		return srt_date;
	}

	public String getAuth_statusByPostId(int postId) {
		String auth_status = null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;

		try {
			conn = DatasourceConnection.getConnection();
			String query1 = "SELECT COUNT(*) FROM RPTT_FEEDBACK_COMMENT WHERE AUTHRESPONSE='Y' AND POST_ID=?";
			pstmt = conn.prepareStatement(query1);
			pstmt.setInt(1, postId);
			set = pstmt.executeQuery();
			while (set.next()) {
				auth_status = set.getString(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}

		return auth_status;
	}

	public String getLastUpdNameByPostId(int postId) {
		String lastUpdName = null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;

		try {
			conn = DatasourceConnection.getConnection();
			String query1 = "SELECT u.EMP_NAME FROM RPTT_FEEDBACK_COMMENT c, ONGC_RT_USER u where c.post_id=? and u.CPF_NUMBER=c.CPF_NO order by post_date desc fetch first 1 rows only";
			pstmt = conn.prepareStatement(query1);
			pstmt.setInt(1, postId);
			set = pstmt.executeQuery();
			while (set.next()) {
				lastUpdName = set.getString(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}
		return lastUpdName;
	}

	public List<FeedbackPost> getRecentPost() {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		List<FeedbackPost> rcntPost = new ArrayList<FeedbackPost>();
		try {
			conn = DatasourceConnection.getConnection();
			String query1 = "Select post_id,cpf_no,initcap(emp_name) as emp_name,subject from ( select a.post_id,a.cpf_no, b.emp_name,a.subject,rownum as rnum from "
					+ "rptt_feedback_post a, ONGC_RT_USER b where a.cpf_no=b.cpf_number and a.status='Active' order by a.post_date desc )"
					+ " where rownum <5";
			pstmt = conn.prepareStatement(query1);
			set = pstmt.executeQuery();
			FeedbackPost rcPost = null;
			while (set.next()) {

				rcPost = new FeedbackPost();
				rcPost.setUser(new User());

				rcPost.setPostId(set.getInt("post_id"));

				rcPost.setSubject(set.getString("subject"));

				rcPost.getUser().setUserName(set.getString("emp_name"));

				// + rcPost.getPostId() + " " + rcPost.getSubject() + "  "
				// + rcPost.getUser().getEmpName());
				rcntPost.add(rcPost);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}
		return rcntPost;
	}

	public List<FeedbackPost> getRecentEnablerComm() {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		List<FeedbackPost> rcntEnabCom = new ArrayList<FeedbackPost>();
		try {
			conn = DatasourceConnection.getConnection();
			String query1 = "Select  post_id,cpf_no,initcap(emp_name) as emp_name,subject from ( select a.post_id,a.cpf_no,b.emp_name,c.subject "
					+ "from rptt_feedback_comment a,ONGC_RT_USER b, rptt_feedback_post c where a.authresponse='Y' and a.status='Active' "
					+ "and a.cpf_no=b.cpf_number and a.post_id=c.post_id order by a.post_date desc)where rownum <2";
			pstmt = conn.prepareStatement(query1);
			set = pstmt.executeQuery();
			FeedbackPost enCom = null;
			while (set.next()) {

				enCom = new FeedbackPost();
				enCom.setUser(new User());

				enCom.setPostId(set.getInt("post_id"));

				enCom.setSubject(set.getString("subject"));

				enCom.getUser().setUserName(set.getString("emp_name"));

				// + enCom.getPostId() + " " + enCom.getSubject() + "  "
				// + enCom.getUser().getEmpName());
				rcntEnabCom.add(enCom);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}
		return rcntEnabCom;
	}

	public List<FeedbackPost> getRecentHREnablerComm() {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		List<FeedbackPost> rcntHREnabCom = new ArrayList<FeedbackPost>();
		try {
			conn = DatasourceConnection.getConnection();
			String query2 = "Select  feedback_id,action_by,initcap(emp_name) as emp_name,subject from "
					+ "( select x.feedback_id,x.action_by,y.emp_name,z.subject "
					+ "from rptt_feedback_rep_status x,ONGC_RT_USER y,rptt_feedback_post z "
					+ "where x.action_by=y.cpf_number and x.feedback_id=z.post_id order by x.action_on desc) where rownum <3";
			pstmt = conn.prepareStatement(query2);
			set = pstmt.executeQuery();
			FeedbackPost enHRCom = null;
			while (set.next()) {

				enHRCom = new FeedbackPost();
				enHRCom.setUser(new User());

				enHRCom.setPostId(set.getInt("feedback_id"));

				enHRCom.setSubject(set.getString("subject"));

				enHRCom.getUser().setUserName(set.getString("emp_name"));

				// + enHRCom.getPostId() + " " + enHRCom.getSubject() + "  "
				// + enHRCom.getUser().getEmpName());
				rcntHREnabCom.add(enHRCom);

			}

		} catch (Exception e) {
			e.printStackTrace();
			// + e);
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}
		return rcntHREnabCom;
	}

	public List<FeedbackPost> getRecentComm() {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		List<FeedbackPost> rcntEnabCom = new ArrayList<FeedbackPost>();
		try {
			conn = DatasourceConnection.getConnection();
			String query1 = "Select  post_id,cpf_no,initcap(emp_name) as emp_name,subject from ( select a.post_id,a.cpf_no,b.emp_name,c.subject "
					+ "from rptt_feedback_comment a,ONGC_RT_USER b, rptt_feedback_post c where a.authresponse='N' and a.status='Active' "
					+ "and a.cpf_no=b.cpf_number and a.post_id=c.post_id order by a.post_date desc)where rownum <5";
			pstmt = conn.prepareStatement(query1);
			set = pstmt.executeQuery();
			FeedbackPost enCom = null;
			while (set.next()) {

				enCom = new FeedbackPost();
				enCom.setUser(new User());

				enCom.setPostId(set.getInt("post_id"));

				enCom.setSubject(set.getString("subject"));

				enCom.getUser().setUserName(set.getString("emp_name"));

				// + enCom.getPostId() + " " + enCom.getSubject() + "  "
				// + enCom.getUser().getEmpName());
				rcntEnabCom.add(enCom);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}
		return rcntEnabCom;
	}

	public List<FeedbackPost> getCommentListByStatus() {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		List<FeedbackPost> commentList = new ArrayList<FeedbackPost>();

		String query = "Select * from RPTT_FEEDBACK_POST";
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			// pstmt.setString(1,ReportConstant.COMMENT_STATUS_INACTIVE);
			set = pstmt.executeQuery();
			FeedbackPost post = null;
			while (set.next()) {
				// P.POST_ID, P.SUBJECT,P.MESSAGE,P.POST_DATE,P.NO_OF_REPLIES
				post = new FeedbackPost();
				post.setUser(new User());
				post.setCategory(new FeedbackCategory());
				post.setPostId(set.getInt("POST_ID"));
				post.setSubject(set.getString("SUBJECT"));
				post.setMessage(set.getString("MESSAGE")
						+ set.getString("MESSAGE1") + set.getString("MESSAGE2")
						+ set.getString("MESSAGE3") + set.getString("MESSAGE4"));
				post.setPostDate(set.getDate("POST_DATE"));
				post.setNoOfReplies(set.getInt("NO_OF_REPLIES"));
				post.getCategory().setDescription(set.getString("DESCRIPTION"));
				post.getUser().setCpfNo(set.getString("CPF_NUMBER"));
				post.getUser().setUserName(set.getString("EMP_NAME"));
				post.getUser().setAddress(set.getString("PLACE_POSTING"));
				post.getUser().setDesignation(set.getString("DESIGNATION"));
				post.setStatus(set.getString("STATUS"));
				commentList.add(post);
			}
		} catch (Exception e) {
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}
		return commentList;
	}

	public List<FeedbackPost> getTagListByFeedbackId() {
		List<FeedbackPost> postList = new ArrayList<FeedbackPost>();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		try {
			String query = "SELECT  * FROM    RPTT_FEEDBACK_TAGCLOUD WHERE   LAST_UPDATE >= SYSDATE - 1";
			String queryc = "SELECT POST_ID, SUBJECT, NO_OF_REPLIES, AUTH_STATUS FROM RPTT_FEEDBACK_TAGCLOUD";

			conn = DatasourceConnection.getConnection();

			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet res = ps.executeQuery();

			if (!res.next()) {
				PreparedStatement ps1 = conn
						.prepareStatement("DELETE FROM RPTT_FEEDBACK_TAGCLOUD");
				PreparedStatement ps2 = conn
						.prepareStatement("INSERT INTO RPTT_FEEDBACK_TAGCLOUD (POST_ID, SUBJECT, NO_OF_REPLIES, AUTH_STATUS) SELECT POST_ID, SUBJECT, NO_OF_REPLIES, AUTH_STATUS FROM (SELECT * FROM (SELECT * FROM (SELECT P.POST_ID,P.SUBJECT,P.NO_OF_REPLIES,coalesce((select max(post_date) from rptt_feedback_comment where post_id=p.post_id), p.post_date) srt_date,coalesce((select (select emp_name from ONGC_RT_USER where CPF_NUMBER=CPF_NO) from rptt_feedback_comment where post_id=p.post_id and post_date=(select max(post_date) from rptt_feedback_comment where post_id=p.post_id)), U.EMP_NAME) lst_name,coalesce((SELECT count(*) from rptt_feedback_comment where authresponse='Y' and post_id=p.post_id),0) auth_status FROM RPTT_FEEDBACK_POST P,ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID AND C.FEEDBACK_ID='1' AND p.status='Active' order by srt_date desc) WHERE ROWNUM<=150 ) ORDER BY NO_OF_REPLIES DESC) WHERE ROWNUM<=20");
				ps1.execute();
				ps2.execute();
				ps1.close();
				ps2.close();
				pstmt = conn.prepareStatement(queryc);
				set = pstmt.executeQuery();
				FeedbackPost post = null;
				while (set.next()) {
					post = new FeedbackPost();
					post.setPostId(set.getInt("POST_ID"));
					post.setSubject(set.getString("SUBJECT"));
					post.setNoOfReplies(set.getInt("NO_OF_REPLIES"));
					post.setAuth_status(set.getString("AUTH_STATUS"));
					postList.add(post);
				}
			} else {
				pstmt = conn.prepareStatement(queryc);
				set = pstmt.executeQuery();
				FeedbackPost post = null;
				while (set.next()) {
					post = new FeedbackPost();
					post.setPostId(set.getInt("POST_ID"));
					post.setSubject(set.getString("SUBJECT"));
					post.setNoOfReplies(set.getInt("NO_OF_REPLIES"));
					post.setAuth_status(set.getString("AUTH_STATUS"));
					postList.add(post);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}

		return postList;
	}

	/********************************************************************
	 * getHREnablersList
	 ********************************************************************/

	public List<FeedbackHrEnablers> getHREnablersList() {
		List<FeedbackHrEnablers> enablersList = new ArrayList<FeedbackHrEnablers>();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query = "select a.cpf_no, a.role, a.hr_cat_id, a.location,a.sub_location from rptt_feedback_enablers_master a";
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			set = pstmt.executeQuery();
			FeedbackHrEnablers enablers = null;
			while (set.next()) {
				enablers = new FeedbackHrEnablers();
				enablers.setCpfNo(set.getString("CPF_NO"));
				enablers.setRole(set.getString("ROLE"));
				enablers.setHrCatId(set.getInt("HR_CAT_ID"));
				enablers.setHrLocation(set.getString("LOCATION"));
				enablers.setSubLocation(set.getString("sub_location"));
				enablersList.add(enablers);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}
		
		return enablersList;
	}

	/************************************************************************************
	 * getEnablerByLocation
	 *************************************************************************************/
	public FeedbackHrEnablers getEnablerByLocation(String enblerLocation) {
		
		FeedbackHrEnablers enablers =  new FeedbackHrEnablers();;
		String query = "SELECT A.CPF_NO, A.ROLE, A.HR_CAT_ID, A.LOCATION,A.SUB_LOCATION,U.CONTACTNO,U.EMAILID FROM RPTT_FEEDBACK_ENABLERS_MASTER A,ONGC_RT_USER U WHERE U.CPF_NUMBER=A.CPF_NO AND A.LOCATION like UPPER('" + enblerLocation.substring(0, 3) + "%') AND A.ROLE='L1'";
		//String query = "SELECT A.CPF_NO, A.ROLE, A.HR_CAT_ID, A.LOCATION,A.SUB_LOCATION,U.CONTACTNO,U.EMAILID FROM RPTT_FEEDBACK_ENABLERS_MASTER A,ONGC_RT_USER U WHERE U.CPF_NUMBER=A.CPF_NO AND A.LOCATION LIKE ?)";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		try {
			conn = DatasourceConnection.getConnection();
			PreparedStatement ps1 = conn.prepareStatement(query);
			//ps1.setString(1, enblerLocation.toUpperCase());
			set = ps1.executeQuery();
			while (set.next()) {
				
				enablers.setCpfNo(set.getString("CPF_NO"));
				enablers.setRole(set.getString("ROLE"));
				enablers.setHrCatId(set.getInt("HR_CAT_ID"));
				enablers.setHrLocation(set.getString("LOCATION"));
				enablers.setSubLocation(set.getString("SUB_LOCATION")!=null?set.getString("SUB_LOCATION"):"");
				enablers.setMobileNo(set.getString("CONTACTNO")!=null?set.getString("CONTACTNO"):"");
				enablers.setEmailId(set.getString("EMAILID")!=null?set.getString("EMAILID"):"");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
			
		}
		
		return enablers;
	}
	
	
	public boolean closeComment(FeedbackPost post) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		boolean flage = false;
		String query = "UPDATE RPTT_FEEDBACK_POST SET OC_STATUS = ? WHERE POST_ID = ?";
		String query1 = "INSERT INTO RPTT_FEEDBACK_REP_STATUS(SNO, FEEDBACK_ID, STATUS, ACTION_BY, REASON) values(?, ?, ?, ?, ?)";

		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;

		try {
			conn = DatasourceConnection.getConnection();

			int id = getMaxIdFromTable("RPTT_FEEDBACK_REP_STATUS", "SNO", conn);
			// conn.setAutoCommit(false);
			pstmt1 = conn.prepareStatement(query);
			pstmt1.setString(1, post.getOpStatus());
			pstmt1.setString(1, post.getOpStatus());
			pstmt1.setInt(2, post.getPostId());
			flage = 0 < pstmt1.executeUpdate();
			pstmt2 = conn.prepareStatement(query1);
			pstmt2.setInt(1, id);
			pstmt2.setInt(2, post.getPostId());
			pstmt2.setString(3, post.getOpStatus());
			pstmt2.setString(4, post.getUser().getCpfNo());
			pstmt2.setString(5, post.getRevertReason());
			pstmt2.executeUpdate();
			// conn.commit();
		} catch (Exception e) {
			/*
			 * try{ conn.rollback(); } catch(SQLException ex) {
			 */
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt1);
			pstmt2 = null;
		}
		return flage;

	}

	public boolean openComment(FeedbackPost post) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		boolean flage = false;
		String query = "UPDATE RPTT_FEEDBACK_POST SET OC_STATUS = ? WHERE POST_ID = ?";
		String query1 = "INSERT INTO RPTT_FEEDBACK_REP_STATUS(SNO, FEEDBACK_ID, STATUS, ACTION_BY) values(?, ?, ?, ?)";

		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		try {
			conn = DatasourceConnection.getConnection();
			int id = getMaxIdFromTable("RPTT_FEEDBACK_REP_STATUS", "SNO", conn);
			pstmt1 = conn.prepareStatement(query);
			pstmt1.setString(1, post.getOpStatus());
			pstmt1.setInt(2, post.getPostId());
			flage = 0 < pstmt1.executeUpdate();
			pstmt2 = conn.prepareStatement(query1);
			pstmt2.setInt(1, id);
			pstmt2.setInt(2, post.getPostId());
			pstmt2.setString(3, post.getOpStatus());
			pstmt2.setString(4, post.getUser().getCpfNo());
			pstmt2.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt1);
			pstmt2 = null;
		}
		return flage;
	}

	public boolean escalateCorporate(FeedbackPost post) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		boolean flage = false;
		String query = "UPDATE RPTT_FEEDBACK_POST SET RV_STATUS = ?, HR_CAT_ID=? WHERE POST_ID = ?";
		String query1 = "INSERT INTO RPTT_FEEDBACK_REP_STATUS(SNO, FEEDBACK_ID, STATUS, ACTION_BY, REASON, HR_CAT_ID) values(?, ?, ?, ?, ?, ?)";

		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		try {
			conn = DatasourceConnection.getConnection();
			int id = getMaxIdFromTable("RPTT_FEEDBACK_REP_STATUS", "SNO", conn);
			pstmt1 = conn.prepareStatement(query);
			pstmt1.setString(1, post.getRvStatus());
			pstmt1.setInt(2, post.getHrCatId());
			pstmt1.setInt(3, post.getPostId());
			flage = 0 < pstmt1.executeUpdate();
			pstmt2 = conn.prepareStatement(query1);
			pstmt2.setInt(1, id);
			pstmt2.setInt(2, post.getPostId());
			pstmt2.setString(3, post.getRvStatus());
			pstmt2.setString(4, post.getUser().getCpfNo());
			pstmt2.setString(5, post.getRevertReason());
			pstmt2.setInt(6, post.getHrCatId());
			pstmt2.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt1);
			pstmt2 = null;
		}
		return flage;
	}

	public boolean revertCorporate(FeedbackPost post) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		boolean flage = false;
		String query = "UPDATE RPTT_FEEDBACK_POST SET RV_STATUS = ? WHERE POST_ID = ?";
		String query1 = "INSERT INTO RPTT_FEEDBACK_REP_STATUS(SNO, FEEDBACK_ID, STATUS, ACTION_BY, REASON) values(?, ?, ?, ?, ?)";

		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		try {
			conn = DatasourceConnection.getConnection();
			int id = getMaxIdFromTable("RPTT_FEEDBACK_REP_STATUS", "SNO", conn);
			pstmt1 = conn.prepareStatement(query);
			pstmt1.setString(1, post.getRvStatus());
			pstmt1.setInt(2, post.getPostId());
			flage = 0 < pstmt1.executeUpdate();
			pstmt2 = conn.prepareStatement(query1);
			pstmt2.setInt(1, id);
			pstmt2.setInt(2, post.getPostId());
			pstmt2.setString(3, post.getRvStatus());
			pstmt2.setString(4, post.getUser().getCpfNo());
			pstmt2.setString(5, post.getRevertReason());
			pstmt2.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt1);
			pstmt2 = null;
		}
		return flage;
	}

	public List<FeedbackHrCategory> getHRCategoryList() {
		List<FeedbackHrCategory> hrCategoryList = new ArrayList<FeedbackHrCategory>();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
//		String query = "select id, category from rptt_feedback_hr_corp_category order by category";
		String query = "select id, category from rptt_feedback_category order by category";
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			set = pstmt.executeQuery();
			FeedbackHrCategory hrCategory = null;
			while (set.next()) {
				hrCategory = new FeedbackHrCategory();
				hrCategory.setHrCatId(set.getInt("ID"));
				hrCategory.setHrCategory(set.getString("CATEGORY"));
				hrCategoryList.add(hrCategory);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}
		return hrCategoryList;
	}
	
	public boolean sendPostToAuthority2(int postId) {
		boolean flage = false;
		String query = "UPDATE RPTT_FEEDBACK_POST SET RV_STATUS = ? WHERE POST_ID = ?";
		PreparedStatement pstmt1 = null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		try {
			conn = DatasourceConnection.getConnection();
			pstmt1 = conn.prepareStatement(query);
			pstmt1.setString(1, "LOCAL2");
			pstmt1.setInt(2, postId);
			flage = 0 < pstmt1.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt1);
		}
		return flage;
	}
	public boolean sendPostToChief(int postId) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		boolean flage = false;
		String query = "UPDATE RPTT_FEEDBACK_POST SET CHIEF_ER = ? WHERE POST_ID = ?";
		PreparedStatement pstmt1 = null;

		try {
			conn = DatasourceConnection.getConnection();
			pstmt1 = conn.prepareStatement(query);
			pstmt1.setString(1, "YES");
			pstmt1.setInt(2, postId);
			flage = 0 < pstmt1.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt1);
		}
		return flage;
	}
	
	public boolean updateAuthResponse(int postId) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		boolean flage = false;
		String query = "UPDATE RPTT_FEEDBACK_POST SET AUTHRESPONSE = ? WHERE POST_ID = ?";
		PreparedStatement pstmt1 = null;

		try {
			conn = DatasourceConnection.getConnection();
			pstmt1 = conn.prepareStatement(query);
			pstmt1.setString(1, "Y");
			pstmt1.setInt(2, postId);
			flage = 0 < pstmt1.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt1);
		}
		return flage;
	}

	/*
	 * public String getUserCpf(FeedbackPost post) { String userCpf=""; String
	 * query="select cpf_no from rptt_feedback_post where post_id=?";
	 * PreparedStatement pstmt1 = null; ResultSet rs=null; try{
	 * conn=DatasourceConnection.getConnection(); pstmt1=conn.prepareStatement(query);
	 * pstmt1.setInt(1,post.getPostId()); rs=pstmt1.executeQuery();
	 * while(rs.next()){ userCpf=rs.getString("CPF_NO"); }
	 * 
	 * } catch (Exception e) { log.info(e.getMessage()); e.printStackTrace(); }
	 * finally{ DatasourceConnection.closeConnection(conn, pstmt1,rs); } return userCpf; }
	 */

	public String getCorporateCpf(FeedbackPost post) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String corporateCpf = "";
		String query = "select cpf_no from rptt_feedback_enablers_master where role=? and hr_cat_id=?";
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
		try {
			conn = DatasourceConnection.getConnection();
			pstmt1 = conn.prepareStatement(query);
			pstmt1.setString(1, "L2");
			pstmt1.setInt(2, post.getHrCatId());
			rs = pstmt1.executeQuery();
			while (rs.next()) {
				corporateCpf = rs.getString("CPF_NO");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt1,rs);
		}
		return corporateCpf;
	}

	public String getChiefCpf() {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String chiefCpf = "";
		String query = "select cpf_no from rptt_feedback_enablers_master where role=?";
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
		try {
			conn = DatasourceConnection.getConnection();
			pstmt1 = conn.prepareStatement(query);
			pstmt1.setString(1, "L3");
			rs = pstmt1.executeQuery();
			while (rs.next()) {
				chiefCpf = rs.getString("CPF_NO");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt1,rs);
		}
		return chiefCpf;
	}

	public String getAuthorityCpf(String role) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String authCpf = "";
		String query = "select cpf_no from rptt_feedback_enablers_master where role=?";
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
		try {
			conn = DatasourceConnection.getConnection();
			pstmt1 = conn.prepareStatement(query);
			pstmt1.setString(1, role);
			rs = pstmt1.executeQuery();
			while (rs.next()) {
				authCpf = rs.getString("CPF_NO");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt1,rs);
		}
		return authCpf;
	}
	
	public Date getPostForwardDate(int postId) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		Date fwdDate = null;
		String query = "select action_on from rptt_feedback_rep_status where feedback_id=? and status='CORPORATE'";
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
		try {
			conn = DatasourceConnection.getConnection();
			pstmt1 = conn.prepareStatement(query);
			pstmt1.setInt(1, postId);
			rs = pstmt1.executeQuery();
			while (rs.next()) {
				fwdDate = rs.getDate(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt1,rs);
		}

		return fwdDate;
	}

	public List<FeedbackPost> getfeedbackListbyLocation(String location) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		List<FeedbackPost> postList = new ArrayList<FeedbackPost>();
		String query = "SELECT P.POST_ID, P.SUBJECT,P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,P.MESSAGE4,P.POST_DATE,P.NO_OF_REPLIES,P.CHIEF_ER,P.OC_STATUS,P.RV_STATUS,U.CPF_NUMBER,U.EMP_NAME,U.PAST_DESIGNATION,C.DESCRIPTION FROM RPTT_FEEDBACK_POST P, ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID AND P.CATEGORY_ID=?";
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, location);
			set = pstmt.executeQuery();
			FeedbackPost post = null;
			while (set.next()) {
				post = new FeedbackPost();
				post.setUser(new User());
				post.setCategory(new FeedbackCategory());
				post.setPostId(set.getInt("POST_ID"));
				post.setSubject(set.getString("SUBJECT"));
				post.setMessage(set.getString("MESSAGE")
						+ set.getString("MESSAGE1") + set.getString("MESSAGE2")
						+ set.getString("MESSAGE3") + set.getString("MESSAGE4"));
				post.setPostDate(set.getDate("POST_DATE"));
				post.setNoOfReplies(set.getInt("NO_OF_REPLIES"));
				post.setChiefEr(set.getString("CHIEF_ER"));
				post.setOpStatus(set.getString("OC_STATUS"));
				post.setRvStatus(set.getString("RV_STATUS"));
				post.getCategory().setDescription(set.getString("DESCRIPTION"));
				post.getUser().setCpfNo(set.getString("CPF_NUMBER"));
				post.getUser().setUserName(set.getString("EMP_NAME"));
				post.getUser().setAddress(set.getString("PLACE_POSTING"));
				post.getUser().setDesignation(set.getString("DESIGNATION"));
				postList.add(post);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}

		return postList;
	}

	public List<FeedbackPost> getfeedbackList(String subLocation, int start,int end) {
		List<FeedbackPost> feedbackpostList = new ArrayList<FeedbackPost>();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		// String
		// query="SELECT P.POST_ID,P.STATUS,P.SUBJECT,P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,P.MESSAGE4,P.POST_DATE,P.NO_OF_REPLIES, P.CHIEF_ER, P.OC_STATUS, P.RV_STATUS, U.CPF_NUMBER,U.EMP_NAME,U.DESIGNATION,C.DESCRIPTION,coalesce((select max(post_date) from rptt_feedback_comment where post_id=p.post_id), p.post_date) srt_date,coalesce((select (select emp_name from ONGC_RT_USER where CPF_NUMBER=CPF_NO) from rptt_feedback_comment where post_id=p.post_id and post_date=(select max(post_date) from rptt_feedback_comment where post_id=p.post_id)), U.EMP_NAME) lst_name,coalesce((SELECT count(*) from rptt_feedback_comment where authresponse='Y' and post_id=p.post_id),0) auth_status, P.CATEGORY_ID FROM RPTT_FEEDBACK_POST P,ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID AND "
		// +
		// "C.FEEDBACK_ID=? AND P.POST_ID!=-1 order by srt_date desc";
		/*
		 * StringBuilder query=new StringBuilder(); query.append(
		 * " Select * FROM(SELECT b.*,  DENSE_RANK() OVER ( ORDER BY POST_DATE DESC) rank"
		 * ); query.append(
		 * "FROM(SELECT P.POST_ID,P.STATUS,P.SUBJECT,P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,"
		 * ); query.append(
		 * "P.MESSAGE4,P.POST_DATE,P.NO_OF_REPLIES,P.CHIEF_ER,P.OC_STATUS,P.RV_STATUS,"
		 * ); query.append(
		 * "U.CPF_NUMBER,U.EMP_NAME,U.DESIGNATION,C.DESCRIPTION,"
		 * ); query.append(
		 * "P.HR_CAT_ID,P.CATEGORY_ID,coalesce((SELECT count(*) from rptt_feedback_comment where authresponse='Y'and post_id=p.post_id),0) auth_status,"
		 * ); query.append(
		 * "coalesce((select (select emp_name from ONGC_RT_USER where CPF_NUMBER=CPF_NO) from rptt_feedback_comment where post_id=p.post_id"
		 * ); query.append(
		 * "and post_date=(select max(post_date) from rptt_feedback_comment where post_id=p.post_id)), U.EMP_NAME) lst_name,coalesce((SELECT MAX(post_date) FROM rptt_feedback_comment WHERE post_id=p.post_id), p.post_date) srt_date"
		 * ); query.append(
		 * "FROM RPTT_FEEDBACK_POST P, ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID and  "
		 * );
		 */
		String query = "Select * FROM(SELECT b.*,  DENSE_RANK() OVER ( ORDER BY POST_DATE DESC) rank "
				+ "FROM(SELECT P.POST_ID,P.STATUS,P.SUBJECT,P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,"
				+ "P.MESSAGE4,P.POST_DATE,P.NO_OF_REPLIES,P.CHIEF_ER,P.OC_STATUS,P.RV_STATUS,"
				+ "U.CPF_NUMBER,U.EMP_NAME,U.PAST_DESIGNATION,C.DESCRIPTION,"
				+ "P.HR_CAT_ID,P.CATEGORY_ID,coalesce((SELECT count(*) from rptt_feedback_comment "
				+ "where authresponse='Y'and post_id=p.post_id),0) auth_status,"
				+ "coalesce((select (select emp_name from ONGC_RT_USER where CPF_NUMBER=CPF_NO)"
				+ "from rptt_feedback_comment where post_id=p.post_id "
				+ "and post_date=(select max(post_date) from rptt_feedback_comment where post_id=p.post_id)),"
				+ " U.EMP_NAME) lst_name,coalesce((SELECT MAX(post_date) FROM rptt_feedback_comment WHERE post_id=p.post_id), p.post_date) srt_date "
				+ "FROM RPTT_FEEDBACK_POST P, ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C "
				+ "WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID  ";
		if (!subLocation.equalsIgnoreCase("All")) {
			query += " and lower(u.sub_location)=lower('" + subLocation + "') ";
		}

		// "lower(u.place_posting)=lower(?)and" +
		query += "and p.oc_status='OPEN' and p.rv_status='LOCAL' and  p.category_id in(42,62,63,64,65,66,67,68,61) " + ") b)  "
				+ " where rank>" + start + " and rank<=" + end;

		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			// pstmt.setString(1,location);
			// pstmt.setString(1, subLocation);
			set = pstmt.executeQuery();
			FeedbackPost post = null;
			while (set.next()) {
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy");
				// P.POST_ID, P.SUBJECT,P.MESSAGE,P.POST_DATE,P.NO_OF_REPLIES
				post = new FeedbackPost();
				post.setUser(new User());
				post.setCategory(new FeedbackCategory());
				post.setPostId(set.getInt("POST_ID"));
				post.setSubject(set.getString("SUBJECT"));
				String comm = set.getString("MESSAGE")
						+ set.getString("MESSAGE1") + set.getString("MESSAGE2")
						+ set.getString("MESSAGE3") + set.getString("MESSAGE4");

				if (comm != null) {
					comm = comm.replaceAll("&gt;", ">");
					comm = comm.replaceAll("&lt;", "<");
				}
				post.setMessage(comm);
				String strpostdate = sdf1.format(set.getDate("POST_DATE"));
			
				post.setPstdate(strpostdate);
				post.setPostDate(set.getDate("POST_DATE"));
				String str_srtdt = sdf1.format(set.getDate("srt_date"));
				post.setCmmntdate(str_srtdt);
				post.setSrt_date(set.getDate("srt_date"));// not
				post.setNoOfReplies(set.getInt("NO_OF_REPLIES"));
				post.setChiefEr(set.getString("CHIEF_ER"));
				post.setOpStatus(set.getString("OC_STATUS"));
				post.setRvStatus(set.getString("RV_STATUS"));
				post.getCategory().setDescription(set.getString("DESCRIPTION"));
				post.setCategoryId(set.getInt("CATEGORY_ID"));
				post.getUser().setCpfNo(set.getString("CPF_NUMBER"));
				post.getUser().setUserName(set.getString("EMP_NAME"));
				post.getUser().setAddress(set.getString("PLACE_POSTING"));
				post.getUser().setDesignation(set.getString("DESIGNATION"));
				post.setStatus(set.getString("STATUS"));// not
				post.setLastUpdName(set.getString("lst_name"));// not
				post.setAuth_status(set.getString("auth_status"));

				if (post.getRvStatus().equalsIgnoreCase("CORPORATE")
						&& post.getOpStatus().equalsIgnoreCase("OPEN"))
					post.setWorkdays(calculateWorkDays(post.getPostId()));
				else
					post.setWorkdays(0);

				Date date1 = sdf1.parse(strpostdate);
				Date date2 = sdf1.parse("14.10.2015");

				if (date1.after(date2))
					post.setColor("Yes");
				else
					post.setColor("No");

				feedbackpostList.add(post);
				// + feedbackpostList.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}
		return feedbackpostList;
	}

	public List<FeedbackPost> getfeedbackListCorp(String username, int start,
			int end) {
		List<FeedbackPost> feedbackpostList = new ArrayList<FeedbackPost>();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		// String
		// query="SELECT P.POST_ID,P.STATUS,P.SUBJECT,P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,P.MESSAGE4,P.POST_DATE,P.NO_OF_REPLIES, P.CHIEF_ER, P.OC_STATUS, P.RV_STATUS, U.CPF_NUMBER,U.EMP_NAME,U.DESIGNATION,C.DESCRIPTION,coalesce((select max(post_date) from rptt_feedback_comment where post_id=p.post_id), p.post_date) srt_date,coalesce((select (select emp_name from ONGC_RT_USER where CPF_NUMBER=CPF_NO) from rptt_feedback_comment where post_id=p.post_id and post_date=(select max(post_date) from rptt_feedback_comment where post_id=p.post_id)), U.EMP_NAME) lst_name,coalesce((SELECT count(*) from rptt_feedback_comment where authresponse='Y' and post_id=p.post_id),0) auth_status, P.CATEGORY_ID FROM RPTT_FEEDBACK_POST P,ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID AND "
		// +
		// "C.FEEDBACK_ID=? AND P.POST_ID!=-1 order by srt_date desc";
		String query = null;
		// if(username.equalsIgnoreCase("81098") )
		query = "SELECT * FROM   (SELECT b.*,  DENSE_RANK() OVER ( ORDER BY POST_DATE DESC) rank FROM (SELECT P.POST_ID,P.STATUS,P.SUBJECT,P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,"
				+ "P.MESSAGE4,P.POST_DATE,P.NO_OF_REPLIES,P.CHIEF_ER,P.OC_STATUS,P.RV_STATUS,"
				+ "U.CPF_NUMBER,U.EMP_NAME,U.PAST_DESIGNATION,C.DESCRIPTION,"
				+ "P.HR_CAT_ID,P.CATEGORY_ID,coalesce((SELECT count(*) from rptt_feedback_comment "
				+ "where authresponse='Y'and post_id=p.post_id),0) auth_status,"
				+ "coalesce((select (select emp_name from ONGC_RT_USER where CPF_NUMBER=CPF_NO)"
				+ "from rptt_feedback_comment where post_id=p.post_id "
				+ "and post_date=(select max(post_date) from rptt_feedback_comment where post_id=p.post_id)),"
				+ " U.EMP_NAME) lst_name,coalesce((SELECT MAX(post_date) FROM rptt_feedback_comment WHERE post_id=p.post_id), p.post_date) srt_date "
				+ "FROM RPTT_FEEDBACK_POST P, ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C "
				+ "WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID and "
				+ " p.hr_cat_id in(SELECT hr_cat_id FROM RPTT_FEEDBACK_ENABLERS_MASTER WHERE CPF_NO=?) "
				+ "and p.oc_status='OPEN' and p.rv_status='CORPORATE' and "
				+ " p.category_id in(42,62,63,64,65,66,67,68,61)"
				+ ") b)  "
				+ "where rank>" + start + " and rank<=" + end;
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			set = pstmt.executeQuery();
			FeedbackPost post = null;
			while (set.next()) {
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy");
				// P.POST_ID, P.SUBJECT,P.MESSAGE,P.POST_DATE,P.NO_OF_REPLIES
				post = new FeedbackPost();
				post.setUser(new User());
				post.setCategory(new FeedbackCategory());
				post.setPostId(set.getInt("POST_ID"));
				post.setSubject(set.getString("SUBJECT"));
				String comm = set.getString("MESSAGE")
						+ set.getString("MESSAGE1") + set.getString("MESSAGE2")
						+ set.getString("MESSAGE3") + set.getString("MESSAGE4");

				if (comm != null) {
					comm = comm.replaceAll("&gt;", ">");
					comm = comm.replaceAll("&lt;", "<");
				}
				post.setMessage(comm);
				String strpostdate = sdf1.format(set.getDate("POST_DATE"));
				post.setPstdate(strpostdate);
				post.setPostDate(set.getDate("POST_DATE"));
				String str_srtdt = sdf1.format(set.getDate("srt_date"));
				post.setCmmntdate(str_srtdt);
				post.setSrt_date(set.getDate("srt_date"));// not
				post.setNoOfReplies(set.getInt("NO_OF_REPLIES"));
				post.setChiefEr(set.getString("CHIEF_ER"));
				post.setOpStatus(set.getString("OC_STATUS"));
				post.setRvStatus(set.getString("RV_STATUS"));
				post.getCategory().setDescription(set.getString("DESCRIPTION"));
				post.setCategoryId(set.getInt("CATEGORY_ID"));
				post.getUser().setCpfNo(set.getString("CPF_NUMBER"));
				post.getUser().setUserName(set.getString("EMP_NAME"));
				post.getUser().setAddress(set.getString("PLACE_POSTING"));
				post.getUser().setDesignation(set.getString("DESIGNATION"));
				post.setStatus(set.getString("STATUS"));// not
				post.setLastUpdName(set.getString("lst_name"));// not
				post.setAuth_status(set.getString("auth_status"));

				if (post.getRvStatus().equalsIgnoreCase("CORPORATE")
						&& post.getOpStatus().equalsIgnoreCase("OPEN"))
					post.setWorkdays(calculateWorkDays(post.getPostId()));
				else
					post.setWorkdays(0);

				Date date1 = sdf1.parse(strpostdate);
				Date date2 = sdf1.parse("14.10.2015");

				if (date1.after(date2))
					post.setColor("Yes");
				else
					post.setColor("No");
				feedbackpostList.add(post);
				// + feedbackpostList.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}
		return feedbackpostList;
	}

	public int getNoOfLocalFeedbacks(String sublocation) {
		int count = 0;
		// String query =
		// "SELECT P.POST_ID,P.STATUS, P.SUBJECT,P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,P.MESSAGE4,P.POST_DATE,P.NO_OF_REPLIES, P.CHIEF_ER, P.OC_STATUS, P.RV_STATUS, U.CPF_NUMBER,U.EMP_NAME,U.DESIGNATION,C.DESCRIPTION,coalesce((select max(post_date) from rptt_feedback_comment where post_id=p.post_id), p.post_date) srt_date,coalesce((select (select emp_name from ONGC_RT_USER where CPF_NUMBER=CPF_NO) from rptt_feedback_comment where post_id=p.post_id and post_date=(select max(post_date) from rptt_feedback_comment where post_id=p.post_id)), U.EMP_NAME) lst_name,coalesce((SELECT count(*) from rptt_feedback_comment where authresponse='Y' and post_id=p.post_id),0) auth_status, P.CATEGORY_ID FROM RPTT_FEEDBACK_POST P,ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID AND C.FEEDBACK_ID=? AND p.status=? order by srt_date desc";
		StringBuilder query = new StringBuilder();

		// query.append("select count(*) as cnt FROM RPTT_FEEDBACK_POST P WHERE p.hr_cat_id    =1 AND p.oc_status ='OPEN' AND p.rv_status='LOCAL' AND p.category_id IN(42,62,63,64,65,66,67,68,61) ");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		query.append("SELECT COUNT(*) AS cnt FROM RPTT_FEEDBACK_POST P, ONGC_RT_USER a WHERE  p.oc_status    ='OPEN' AND p.rv_status    ='LOCAL' and a.cpf_number= p.cpf_no AND p.category_id IN(42,62,63,64,65,66,67,68,61) ");

		if (!"All".equalsIgnoreCase(sublocation)) {
			query.append(" and a.sub_location=?");
		}

		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query.toString());
			if (!"All".equalsIgnoreCase(sublocation)) {
				pstmt.setString(1, sublocation);
			}
			set = pstmt.executeQuery();

			while (set.next()) {
				count = set.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}
		return count;
	}

	public int getNoOfCorpFeedbacks(String username) {
		int count = 0;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		// String query =
		// "SELECT P.POST_ID,P.STATUS, P.SUBJECT,P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,P.MESSAGE4,P.POST_DATE,P.NO_OF_REPLIES, P.CHIEF_ER, P.OC_STATUS, P.RV_STATUS, U.CPF_NUMBER,U.EMP_NAME,U.DESIGNATION,C.DESCRIPTION,coalesce((select max(post_date) from rptt_feedback_comment where post_id=p.post_id), p.post_date) srt_date,coalesce((select (select emp_name from ONGC_RT_USER where CPF_NUMBER=CPF_NO) from rptt_feedback_comment where post_id=p.post_id and post_date=(select max(post_date) from rptt_feedback_comment where post_id=p.post_id)), U.EMP_NAME) lst_name,coalesce((SELECT count(*) from rptt_feedback_comment where authresponse='Y' and post_id=p.post_id),0) auth_status, P.CATEGORY_ID FROM RPTT_FEEDBACK_POST P,ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND P.CATEGORY_ID=C.CATEGORY_ID AND C.FEEDBACK_ID=? AND p.status=? order by srt_date desc";
		StringBuilder query = new StringBuilder();
		// if(username.equalsIgnoreCase("81098") )
		if (username.equalsIgnoreCase("127088")) {
			query.append("select count(*) as cnt FROM RPTT_FEEDBACK_POST P WHERE p.hr_cat_id in(1,4,6,9) AND p.oc_status ='OPEN' AND p.rv_status='CORPORATE' AND p.category_id IN(42,62,63,64,65,66,67,68,61) ");
		} else {

			query.append("select count(*) as cnt FROM RPTT_FEEDBACK_POST P WHERE p.hr_cat_id in(2,3,5,7,8) AND p.oc_status ='OPEN' AND p.rv_status='CORPORATE' AND p.category_id IN(42,62,63,64,65,66,67,68,61) ");
		}

		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query.toString());

			set = pstmt.executeQuery();

			while (set.next()) {
				count = set.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}
		return count;
	}

	public boolean check_cpf(String cpf) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query = "select cpfnum from leadership_board where cpfnum='"+ cpf + "'";
		boolean flage = false;
		try {
			
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			ResultSet res = pstmt.executeQuery();
			if (!res.next()) {
				flage = true;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt);
		}
		return flage;

	}
	
	public boolean saveUser(String cpf, String chk) {
		String query = "";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		if ("feedback_post".equalsIgnoreCase(chk)) {
			query = "insert into leadership_board(cpfnum,feedback_posts,lid) VALUES(?,?,?)";
		} else if ("enabler_close".equalsIgnoreCase(chk)|| "auth_response".equalsIgnoreCase(chk)) {
			query = "insert into leadership_board(cpfnum,feedback_enabler,lid) VALUES(?,?,?)";
		} else {
			query = "insert into leadership_board(cpfnum,feedback_comments,lid) VALUES(?,?,?)";
		}
		boolean flage = false;
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cpf);

			if ("feedback_post".equalsIgnoreCase(chk)) {
				pstmt.setInt(2, 20);

			} else if ("enabler_close".equalsIgnoreCase(chk)
					|| "auth_response".equalsIgnoreCase(chk)) {
				pstmt.setInt(2, 20);
			} else {
				pstmt.setInt(2, 10);
			}
			
			int lid = getMaxIdFromTable("leadership_board","lid",conn);
			pstmt.setInt(3, lid);
			flage = pstmt.executeUpdate() > 0;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt);
		}
		return flage;
	}

	public boolean updateUser(String cpf, String chk) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query = "";
		if ("feedback_post".equalsIgnoreCase(chk)) {
			query = "update leadership_board set feedback_posts=? where cpfnum = ? ";
		} else if ("enabler_close".equalsIgnoreCase(chk)
				|| "auth_response".equalsIgnoreCase(chk)) {
			query = "update leadership_board set feedback_enabler=? where cpfnum = ? ";
		} else {
			query = "update leadership_board set feedback_comments=? where cpfnum = ? ";
		}
		boolean flage = false;
		try {

			int fcount = getFeedbackCount(cpf, chk);
			if ("feedback_post".equalsIgnoreCase(chk)) {
				fcount = fcount + 20;
			} else if ("enabler_close".equalsIgnoreCase(chk)
					|| "auth_response".equalsIgnoreCase(chk)) {
				fcount = fcount + 20;
			} else {
				fcount = fcount + 10;
			}
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, fcount);
			pstmt.setString(2, cpf);

			flage = pstmt.executeUpdate() > 0;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt);
		}
		return flage;
	}

	public int getFeedbackCount(String cpf, String chk) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query = "";
		if ("feedback_post".equalsIgnoreCase(chk)) {
			query = "Select feedback_posts from leadership_board where cpfnum= ? ";
		} else if ("enabler_close".equalsIgnoreCase(chk)
				|| "auth_response".equalsIgnoreCase(chk)) {
			query = "Select feedback_enabler from leadership_board where cpfnum= ? ";
		} else {
			query = "Select feedback_comments from leadership_board where cpfnum= ? ";
		}
		int count = 0;
		try {

			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cpf);

			set = pstmt.executeQuery();
			while (set.next()) {
				count = set.getInt(1);

			}

		} catch (Exception ex) {
				ex.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}
		return count;
	}
/******************************************************************************************************
 * getMyPosts
 ***************************************************************************************************************************/
	public List<FeedbackPost> getMyPosts(String cpfno) {
		List<FeedbackPost> mypostList = new ArrayList<FeedbackPost>();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query = "SELECT ROW_NUMBER() OVER(ORDER BY P.POST_DATE DESC) AS  RANK, P.POST_ID,P.STATUS,P.SUBJECT,"
				+ "P.MESSAGE,P.MESSAGE1,P.MESSAGE2,P.MESSAGE3,P.MESSAGE4,P.POST_DATE,P.NO_OF_REPLIES,P.CHIEF_ER,P.OC_STATUS,"
				+ "P.RV_STATUS, U.CPF_NUMBER,initcap(U.EMP_NAME)as EMP_NAME,U.PAST_DESIGNATION AS DESIGNATION,U.LOCATION PLACE_POSTING,C.DESCRIPTION,	"
				+ "P.HR_CAT_ID,P.CATEGORY_ID,coalesce((SELECT count(*) from rptt_feedback_comment 	where authresponse='Y'and "
				+ "post_id=p.post_id),0) auth_status, coalesce((select (select initcap(emp_name) from ONGC_RT_USER where "
				+ "CPF_NUMBER=CPF_NO) from rptt_feedback_comment where post_id=p.post_id  and post_date=(select max(post_date) "
				+ "from rptt_feedback_comment where post_id=p.post_id)), initcap(U.EMP_NAME) ) lst_name,"
				+ "coalesce((SELECT MAX(post_date) FROM rptt_feedback_comment WHERE post_id=p.post_id), p.post_date) srt_date "
				+ "FROM RPTT_FEEDBACK_POST P, ONGC_RT_USER U,RPTT_FEEDBACK_CATEGORY C WHERE P.CPF_NO::varchar(255)=U.CPF_NUMBER AND "
				+ "P.CATEGORY_ID=C.CATEGORY_ID  and p.status='Active' and p.cpf_no=? ORDER BY P.POST_DATE DESC";

		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cpfno);

			set = pstmt.executeQuery();
			FeedbackPost post = null;
			while (set.next()) {
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy");
				post = new FeedbackPost();
				post.setUser(new User());
				post.setCategory(new FeedbackCategory());
				post.setPostId(set.getInt("POST_ID"));
				post.setSubject(set.getString("SUBJECT"));
				String comm = set.getString("MESSAGE")+ set.getString("MESSAGE1") + set.getString("MESSAGE2") + set.getString("MESSAGE3") + set.getString("MESSAGE4");

				if (comm != null) {
					comm = comm.replaceAll("&gt;", ">");
					comm = comm.replaceAll("&lt;", "<");
				}

				post.setMessage(comm);
				String strpostdate = sdf1.format(set.getDate("POST_DATE"));
				post.setPstdate(strpostdate);
				post.setPostDate(set.getDate("POST_DATE"));

				String str_srtdt = sdf1.format(set.getDate("srt_date"));
				post.setCmmntdate(str_srtdt);
				post.setSrt_date(set.getDate("srt_date"));
				post.setNoOfReplies(set.getInt("NO_OF_REPLIES"));
				post.setChiefEr(set.getString("CHIEF_ER"));
				post.setOpStatus(set.getString("OC_STATUS"));
				post.setRvStatus(set.getString("RV_STATUS"));
				post.getCategory().setDescription(set.getString("DESCRIPTION"));
				post.setCategoryId(set.getInt("CATEGORY_ID"));
				post.getUser().setCpfNo(set.getString("CPF_NUMBER"));
				post.getUser().setUserName(set.getString("EMP_NAME"));
				post.getUser().setAddress(set.getString("PLACE_POSTING"));
				post.getUser().setDesignation(set.getString("DESIGNATION"));
				post.setStatus(set.getString("STATUS"));// not
				post.setLastUpdName(set.getString("lst_name"));// not
				post.setAuth_status(set.getString("auth_status"));

				if (post.getRvStatus().equalsIgnoreCase("CORPORATE") && post.getOpStatus().equalsIgnoreCase("OPEN"))
					post.setWorkdays(calculateWorkDays(post.getPostId()));
				else
					post.setWorkdays(0);

				Date date1 = sdf1.parse(strpostdate);
				Date date2 = sdf1.parse("14.10.2015");

				if (date1.after(date2))
					post.setColor("Yes");
				else
					post.setColor("No");

				mypostList.add(post);

			}
		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}
		return mypostList;
	}

	public int calculateWorkDays(int pId) {
		Date startDt = getPostForwardDate(pId);
		Date endDt = new Date();
		int workDays = 0;

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDt);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDt);

		do {
			// excluding start date
			startCal.add(Calendar.DAY_OF_MONTH, 1);
			if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
					&& startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				++workDays;
			}
		} while (startCal.getTimeInMillis() < endCal.getTimeInMillis());

		return workDays;
	}

	public int getMaxIdFromTable(String tableName, String colName,
			Connection conn) {
		int id = 0;

		String query = "select max(" + colName + ") from " + tableName;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				id = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
//			 DatasourceConnection.closeConnection(conn, stmt,rs);
		}

		return ++id;
	}

	public String getReceiverMobNo(String toCpf) {
		String mobnum = null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query = "Select mobile_number from ONGC_RT_USER where cpf_number=?";
		boolean flage = false;
		try {

			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, toCpf);

			set = pstmt.executeQuery();
			while (set.next()) {
				mobnum = set.getString("mobile_number");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt,set );
		}
		return mobnum;
	}
}
