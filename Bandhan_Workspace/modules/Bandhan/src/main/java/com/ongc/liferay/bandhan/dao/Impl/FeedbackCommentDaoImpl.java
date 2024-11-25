package com.ongc.liferay.bandhan.dao.Impl;

import com.ongc.liferay.bandhan.connection.DatasourceConnection;
import com.ongc.liferay.bandhan.dao.FeedbackCommentDao;
import com.ongc.liferay.bandhan.model.FeedbackComment;
import com.ongc.liferay.bandhan.model.FeedbackReason;
import com.ongc.liferay.bandhan.model.User;
import com.ongc.liferay.bandhan.util.RTConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FeedbackCommentDaoImpl implements FeedbackCommentDao {




	public boolean saveFeedbackComment(FeedbackComment comment){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		boolean flage=false;
		String query="INSERT INTO RPTT_FEEDBACK_COMMENT (COMMENT_ID, POST_ID, CPF_NO, COMMENT_TEXT,COMMENT_TEXT1,COMMENT_TEXT2,COMMENT_TEXT3,COMMENT_TEXT4, AUTHRESPONSE) VALUES(?,?,?,?,?,?,?,?,?)";
		try{
			conn=DatasourceConnection.getConnection();
		
			int id=getMaxIdFromTable("RPTT_FEEDBACK_COMMENT", "COMMENT_ID", conn);
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1,id);
			pstmt.setInt(2,comment.getPostId());
			pstmt.setString(3,comment.getUser().getCpfNo());

			String kk=comment.getCommentText();
			String kk1=kk.substring(0,kk.length()/5);
			String kk2=kk.substring(kk.length()/5,2*(kk.length()/5));
			String kk3=kk.substring(2*(kk.length()/5),3*(kk.length()/5));
			String kk4=kk.substring(3*(kk.length()/5),4*(kk.length()/5));
			String kk5=kk.substring(4*(kk.length()/5));


			pstmt.setString(4,kk1);
			pstmt.setString(5,kk2);
			pstmt.setString(6,kk3);
			pstmt.setString(7,kk4);
			pstmt.setString(8,kk5);
			if (comment.isAuthResp()) 
				pstmt.setString(9,"Y");
			else 
				pstmt.setString(9,"N");

			flage=0<pstmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DatasourceConnection.closeConnection(conn,pstmt);
		}
		return flage;
	}

	public boolean archiveFeedbackComment(String status , int postId){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		boolean flage=false;
		String query="UPDATE RPTT_FEEDBACK_COMMENT SET STATUS = ? WHERE COMMENT_ID = ?";
		PreparedStatement pstmt1 = null;
		try{
			conn=DatasourceConnection.getConnection(); 
			pstmt1=conn.prepareStatement(query);
			pstmt1.setString(1,status);
			pstmt1.setInt(2,postId);
			flage=0 < pstmt1.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		finally{
			DatasourceConnection.closeConnection(conn,pstmt1);
		}
		return flage;
	}

	public boolean deleteFeedbackComment(int postId,int commentId){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		boolean flage=false;
		String query="DELETE FROM RPTT_FEEDBACK_COMMENT WHERE COMMENT_ID = ? AND POST_ID=?";
		PreparedStatement pstmt1 = null;
		try{
			conn=DatasourceConnection.getConnection(); 
			pstmt1=conn.prepareStatement(query);

			pstmt1.setInt(1,commentId);
			pstmt1.setInt(2,postId);
			flage=0 < pstmt1.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{

			DatasourceConnection.closeConnection(conn,pstmt1);
		}
		return flage;
	}

	public List<FeedbackComment> getCommentListByPostId(int postId){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		List<FeedbackComment> commentList=new ArrayList<FeedbackComment>();

		String query="SELECT C.COMMENT_ID,C.STATUS,C.COMMENT_TEXT,C.COMMENT_TEXT1,C.COMMENT_TEXT2,C.COMMENT_TEXT3,C.COMMENT_TEXT4,C.POST_ID, C.POST_DATE, U.CPF_NUMBER,U.LOCATION PLACE_POSTING , U.EMP_NAME, U.PAST_DESIGNATION DESIGNATION,C.AUTHRESPONSE FROM RPTT_FEEDBACK_COMMENT C,ONGC_RT_USER U WHERE C.CPF_NO=U.CPF_NUMBER AND c.status = ? AND C.POST_ID=? order by C.POST_DATE";
		try{
			conn=DatasourceConnection.getConnection();
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1,RTConstant.COMMENT_STATUS_ACTIVE);
			pstmt.setInt(2,postId);
			set=pstmt.executeQuery();
			FeedbackComment comment=null;
			while(set.next()){
				comment= new FeedbackComment();
				comment.setUser(new User());
				comment.setCommentId(set.getInt("COMMENT_ID"));

				String comm = set.getString("COMMENT_TEXT")+set.getString("COMMENT_TEXT1")+set.getString("COMMENT_TEXT2")+set.getString("COMMENT_TEXT3")+set.getString("COMMENT_TEXT4");
				if(comm != null){
					comm = comm.replaceAll("&gt;", ">");
					comm = comm.replaceAll("&lt;", "<");	
				}
				comment.setCommentText(comm);

				//comment.setCommentText(set.getString("COMMENT_TEXT"));

				comment.setPostId(set.getInt("POST_ID"));
				comment.setPostDate(set.getTimestamp("POST_DATE"));
				comment.getUser().setCpfNo(set.getString("CPF_NUMBER"));
				comment.getUser().setUserName(set.getString("EMP_NAME"));
				comment.getUser().setDesignation(set.getString("DESIGNATION"));
				comment.getUser().setAddress(set.getString("PLACE_POSTING"));
				comment.setStatus(set.getString("STATUS"));
				if (set.getString("AUTHRESPONSE").equals("Y")) 
					comment.setAuthResp(true);
				else 
					comment.setAuthResp(false);


				commentList.add(comment);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DatasourceConnection.closeConnection(conn,pstmt,set );
		}
		return commentList;
	}


	public List<FeedbackComment> getAllCommentListByPostId(int postId){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		List<FeedbackComment> commentList=new ArrayList<FeedbackComment>();

		String query="SELECT C.COMMENT_ID,C.STATUS,C.COMMENT_TEXT,C.COMMENT_TEXT1,C.COMMENT_TEXT2,C.COMMENT_TEXT3,C.COMMENT_TEXT4,C.POST_ID, C.POST_DATE, U.CPF_NUMBER,U.LOCATION PLACE_POSTING , U.EMP_NAME, U.PAST_DESIGNATION DESIGNATION,C.AUTHRESPONSE FROM RPTT_FEEDBACK_COMMENT C,ONGC_RT_USER U WHERE C.CPF_NO=U.CPF_NUMBER AND C.COMMENT_ID!= ? AND C.POST_ID=? order by C.POST_DATE";
		try{
			conn=DatasourceConnection.getConnection();
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1,-1);
			pstmt.setInt(2,postId);
			set=pstmt.executeQuery();
			FeedbackComment comment=null;
			while(set.next()){
				comment= new FeedbackComment();
				comment.setUser(new User());
				comment.setCommentId(set.getInt("COMMENT_ID"));
				String comm = set.getString("COMMENT_TEXT")+set.getString("COMMENT_TEXT1")+set.getString("COMMENT_TEXT2")+set.getString("COMMENT_TEXT3")+set.getString("COMMENT_TEXT4");

				comm = comm.replaceAll("&gt;", ">");
				comm = comm.replaceAll("&lt;", "<");
				comment.setCommentText(comm);

				comment.setPostId(set.getInt("POST_ID"));
				comment.setPostDate(set.getTimestamp("POST_DATE"));
				comment.getUser().setCpfNo(set.getString("CPF_NUMBER"));
				comment.getUser().setUserName(set.getString("EMP_NAME"));
				comment.getUser().setDesignation(set.getString("DESIGNATION"));
				comment.getUser().setAddress(set.getString("PLACE_POSTING"));
				comment.setStatus(set.getString("STATUS"));
				if (set.getString("AUTHRESPONSE").equals("Y")) 
					comment.setAuthResp(true);
				else 
					comment.setAuthResp(false);

				commentList.add(comment);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DatasourceConnection.closeConnection(conn,pstmt,set );
		}
		return commentList;
	}

	public List<FeedbackComment> getCommentListByStatus(){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		List<FeedbackComment> commentList=new ArrayList<FeedbackComment>();

		String query="SELECT * FROM RPTT_FEEDBACK_COMMENT Where STATUS !=?";
		try{
			conn=DatasourceConnection.getConnection();
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1,RTConstant.COMMENT_STATUS_ACTIVE);

			set=pstmt.executeQuery();
			FeedbackComment comment=null;
			while(set.next()){
				comment= new FeedbackComment();
				comment.setUser(new User());
				comment.setCommentId(set.getInt("COMMENT_ID"));

				String comm = set.getString("COMMENT_TEXT")+set.getString("COMMENT_TEXT1")+set.getString("COMMENT_TEXT2")+set.getString("COMMENT_TEXT3")+set.getString("COMMENT_TEXT4");

				comm = comm.replaceAll("&gt;", ">");
				comm = comm.replaceAll("&lt;", "<");
				comment.setCommentText(comm);

				//comment.setCommentText(set.getString("COMMENT_TEXT"));
				comment.setPostId(set.getInt("POST_ID"));
				comment.setPostDate(set.getTimestamp("POST_DATE"));
				comment.getUser().setCpfNo(set.getString("CPF_NUMBER"));
				comment.getUser().setUserName(set.getString("EMP_NAME"));
				comment.getUser().setDesignation(set.getString("DESIGNATION"));
				commentList.add(comment);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DatasourceConnection.closeConnection(conn,pstmt,set );
		}
		return commentList;
	}

	public List<FeedbackReason> getReasonsListByPostId(int postId) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		List<FeedbackReason> reasonList=new ArrayList<FeedbackReason>();
		//String query="SELECT A.SNO, a.reason, a.action_by, A.STATUS, TO_CHAR(a.action_on, 'Month dd, yyyy HH:mm:ss') as action_on, B.EMP_NAME, B.PLACE_POSTING, B.DESIGNATION, c.role, A.HR_CAT_ID  FROM rptt_feedback_rep_status A, ONGC_RT_USER B, rptt_feedback_enablers_master C WHERE a.feedback_id=? AND a.action_by=b.cpf_number AND a.action_by=c.cpf_no ORDER BY a.action_on";
		//String query="Select SNO, reason, action_by, STATUS, action_on,EMP_NAME, PLACE_POSTING, DESIGNATION, role, HR_CAT_ID from(SELECT distinct A.SNO, a.reason, a.action_by, A.STATUS, TO_CHAR(a.action_on, 'Month dd, yyyy HH:mm:ss') as action_on,B.EMP_NAME, B.PLACE_POSTING, B.DESIGNATION, c.role, A.HR_CAT_ID  FROM rptt_feedback_rep_status A,ONGC_RT_USER B, rptt_feedback_enablers_master C WHERE a.feedback_id=? AND a.action_by=b.cpf_number AND a.action_by=c.cpf_no )ORDER BY action_on";
		String query="Select SNO, reason, action_by, STATUS, action_on,EMP_NAME, PLACE_POSTING, DESIGNATION, HR_CAT_ID from(SELECT distinct A.SNO, a.reason, a.action_by, A.STATUS, TO_CHAR(a.action_on, 'Month dd, yyyy HH:mm:ss') as action_on,B.EMP_NAME, B.LOCATION PLACE_POSTING, B.PAST_DESIGNATION DESIGNATION, A.HR_CAT_ID  FROM rptt_feedback_rep_status A,ONGC_RT_USER B WHERE a.feedback_id=? AND a.action_by=b.cpf_number ) as c ORDER BY action_on";
		try{
			conn=DatasourceConnection.getConnection();
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1,postId);
			set=pstmt.executeQuery();
			FeedbackReason reason=null;
			while(set.next()){
				reason= new FeedbackReason();
				reason.setUser(new User());
				reason.setReasonId(set.getInt("SNO"));
				reason.setReason(set.getString("REASON"));
				reason.setStatus(set.getString("STATUS"));
				reason.getUser().setCpfNo(set.getString("ACTION_BY"));
				reason.setReasonOn(set.getString("ACTION_ON"));
				reason.getUser().setUserName(set.getString("EMP_NAME"));
				reason.getUser().setAddress(set.getString("PLACE_POSTING"));
				reason.getUser().setDesignation(set.getString("DESIGNATION"));
				//reason.getUser().setEmpLevel(set.getString("ROLE"));
				reason.setHrCatId(set.getInt("HR_CAT_ID"));
				reasonList.add(reason);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DatasourceConnection.closeConnection(conn,pstmt,set );
		}
		return reasonList;
	}


	@Override
	public int getMaxIdFromTable(String tableName, String colName, Connection conn) {
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
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//DatasourceConnection.closeConnection(rs, stmt, connection);
		}

		return ++id;
	}

	@Override
	public boolean deleteFeedbackComment(int postId) {
		// TODO Auto-generated method stub
		return false;
	}



}
