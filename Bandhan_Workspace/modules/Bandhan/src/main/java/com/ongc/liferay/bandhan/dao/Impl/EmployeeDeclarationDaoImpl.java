package com.ongc.liferay.bandhan.dao.Impl;

import com.ongc.liferay.bandhan.connection.DatasourceConnection;
import com.ongc.liferay.bandhan.dao.EmployeeDeclarationDao;
import com.ongc.liferay.bandhan.util.EmployeeDeclarationModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeDeclarationDaoImpl implements EmployeeDeclarationDao{
	
	public List<EmployeeDeclarationModel> getAll() {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		List<EmployeeDeclarationModel> list = new ArrayList<EmployeeDeclarationModel>();
		try {
			String query = "SELECT * FROM TT_EMPLOYEE_DECLARATION_DETAILS ORDER BY CREATED_DATE DESC";
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			set = pstmt.executeQuery();
			while (set.next()) {
				EmployeeDeclarationModel em = new EmployeeDeclarationModel();
				em.setCpfNo(set.getString("CPFNO"));
				em.setCreatedDate(set.getString("CREATED_DATE"));
				em.setEmployementStatus(set.getString("EMPLOYEMENT_STATUS"));
				em.setYear(set.getString("YEAR"));
				list.add(em);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt, set);
		}
		return list;

	}

	public boolean insertEmployeeDecalarationModel(EmployeeDeclarationModel ed) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean flag = false;

		String query = "INSERT INTO TT_EMPLOYEE_DECLARATION_DETAILS(CPFNO,YEAR,EMPLOYEMENT_STATUS,REMARK,CREATED_DATE,CURRENT_PLACE)"
				+ " VALUES (?,?,?,?,now(),?)";
		try {
			Date d = new Date();
			java.sql.Date timestamp = new java.sql.Date(d.getTime());
			
			conn = DatasourceConnection.getConnection();

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, ed.getCpfNo());
			pstmt.setString(2, ed.getYear());
			pstmt.setString(3, ed.getEmployementStatus()!=null?ed.getEmployementStatus():"");
			pstmt.setString(4, ed.getRemark()!=null?ed.getRemark():"");
//			pstmt.setDate(5, timestamp);
			pstmt.setString(5, ed.getCurrentPlace());
			
			flag = 0 < pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn,pstmt );
		}
		return flag;
	}

	public boolean isExist(String yr,String cpfno){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		boolean flag=false;
		int count=0;
		String query="SELECT count(*) COUNT FROM TT_EMPLOYEE_DECLARATION_DETAILS WHERE YEAR=? AND CPFNO=?";
		try {
			conn = DatasourceConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, yr);
			pstmt.setString(2, cpfno);
			set = pstmt.executeQuery();
			while (set.next()) {
				 count=Integer.parseInt(set.getString("COUNT"));
			}
			if(count>0)
				flag=true;
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			DatasourceConnection.closeConnection(conn, pstmt, set);
		}
		
		return flag;
	}
	
	public int getMaxIdFromTable(String tableName, String colName) {
		Connection connection=null;
		int id = 0;
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
			DatasourceConnection.closeConnection(connection, stmt, rs);
		}

		return ++id;
	}
}
