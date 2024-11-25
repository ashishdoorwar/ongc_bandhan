package com.ongc.liferay.bandhan.dao.Impl;

import com.ongc.liferay.bandhan.connection.DatasourceConnection;
import com.ongc.liferay.bandhan.dao.FeedbackCategoryDao;
import com.ongc.liferay.bandhan.model.FeedbackCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FeedbackCategoryDaoImpl implements FeedbackCategoryDao {




	public boolean saveFeedbackCategory(FeedbackCategory category) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		boolean flage = false;
		String query = "INSERT INTO RPTT_FEEDBACK_CATEGORY (FEEDBACK_ID,DESCRIPTION, CREATEDBY) VALUES(?,?,?)";
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, category.getFeedbackId());
			pstmt.setString(2, category.getDescription());
			pstmt.setString(3, category.getCreatedBy());

			flage = 0 < pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt);
		}

		return flage;
	}

	public List<FeedbackCategory> getCategoryListByFeedbakId(int feedbackId) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		List<FeedbackCategory> categoryList = new ArrayList<FeedbackCategory>();
		String query = "SELECT * FROM RPTT_FEEDBACK_CATEGORY WHERE FEEDBACK_ID=? order by CATEGORY_ID";
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, feedbackId);
			set = pstmt.executeQuery();
			FeedbackCategory category = null;
			while (set.next()) {
				category = new FeedbackCategory();
				if (set.getInt("CATEGORY_ID") != 60) {
					category.setCategoryId(set.getInt("CATEGORY_ID"));
					category.setDescription(set.getString("DESCRIPTION"));
					category.setCreatedBy(set.getString("CREATEDBY"));
					category.setCreatedOn(set.getDate("CREATEDON"));
					categoryList.add(category);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt, set);
		}

		return categoryList;
	}

	public List<FeedbackCategory> getCategoryList() {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		List<FeedbackCategory> categoryList = new ArrayList<FeedbackCategory>();
		String query = "SELECT * FROM RPTT_FEEDBACK_CATEGORY  order by CATEGORY_ID";
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			set = pstmt.executeQuery();
			FeedbackCategory category = null;
			while (set.next()) {
				category = new FeedbackCategory();
				category.setCategoryId(set.getInt("CATEGORY_ID"));
				category.setDescription(set.getString("DESCRIPTION"));
				category.setCreatedBy(set.getString("CREATEDBY"));
				category.setCreatedOn(set.getDate("CREATEDON"));
				categoryList.add(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt, set);
		}
		return categoryList;
	}
	/**********************************************************************************
	 * roleCheck
	 **********************************************************************************/
	public boolean roleCheck(String userCpf) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		boolean result = false;
		String query = "SELECT * FROM RPTT_FEEDBACK_ENABLERS_MASTER WHERE CPF_NO=? and role in ('L1','L2')";
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userCpf);
			set = pstmt.executeQuery();
			if (set.next()) {
				result = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt, set);
		}

		return result;
	}

	/**********************************************************************************
	 * getHREnablersRole
	 **********************************************************************************/
	public String getHREnablersRole(String cpf) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String role = "";
		String query = "select a.role from rptt_feedback_enablers_master a where a.cpf_no=?";
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cpf);
			set = pstmt.executeQuery();
			while (set.next()) {
				role = set.getString("role");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt, set);
		}
		return role;
	}

	public String getLocation(String userCpf) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String location = "";
		String query = "SELECT location FROM RPTT_FEEDBACK_ENABLERS_MASTER WHERE CPF_NO=?";
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userCpf);
			set = pstmt.executeQuery();
			while (set.next()) {
				location = set.getString("location");

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt, set);
		}

		return location;
	}

	public String getsubLocation(String userCpf) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String sublocation = "";
		String query = "SELECT sub_location FROM RPTT_FEEDBACK_ENABLERS_MASTER WHERE CPF_NO=?";
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userCpf);
			set = pstmt.executeQuery();
			while (set.next()) {
				sublocation = set.getString("sub_location");

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt, set);
		}

		return sublocation;
	}

	public ArrayList getCatId(String userCpf) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String catId = "";
		ArrayList catids = new ArrayList();
		String query = "SELECT hr_cat_id FROM RPTT_FEEDBACK_ENABLERS_MASTER WHERE CPF_NO=?";
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userCpf);
			set = pstmt.executeQuery();
			while (set.next()) {
				catId = set.getString("hr_cat_id");
				catids.add(catId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			DatasourceConnection.closeConnection(conn, pstmt, set);
		}

		return catids;
	}


}
