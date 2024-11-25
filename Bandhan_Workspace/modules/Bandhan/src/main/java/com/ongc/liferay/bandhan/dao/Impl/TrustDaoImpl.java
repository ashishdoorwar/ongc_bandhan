package com.ongc.liferay.bandhan.dao.Impl;

import com.ongc.liferay.bandhan.connection.DatasourceConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import com.ongc.liferay.bandhan.dao.TrustDao;;

public class TrustDaoImpl implements TrustDao{



	public String getPanNo(String cpfNo){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String	panNo ="";
		try{

			String query="SELECT PAN_NUMBER FROM ONGC_RT_USER WHERE CPF_NUMBER=?";
			conn=DatasourceConnection.getConnection();
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, cpfNo);
			set=pstmt.executeQuery();
			if(set.next()){			
				panNo=set.getString("PAN_NUMBER");			
			}
		}
		catch (Exception e) {

			e.printStackTrace();
		}
		finally{
			DatasourceConnection.closeConnection(conn, pstmt,set);
		}
		return panNo;
	}

	public ArrayList<String> getYears(String cpfno) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		ArrayList<String> ylist = new ArrayList<String>();
		String year=null;
		String query ="";
		try {

			query = "select extract(year from a.dt_of_birth) as year from ongc_rt_user a where a.cpf_number =?";
			conn=DatasourceConnection.getConnection();
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, cpfno);
			set = pstmt.executeQuery();
			while (set.next()) {				
				year=set.getString("year");

			}
			if(!year.equalsIgnoreCase("") && year!=null){

				Calendar cal = Calendar.getInstance();
				int startyear=Integer.parseInt(year);
				int curryear = cal.get(Calendar.YEAR);

				while (startyear <= curryear) {

					ylist.add(String.valueOf(startyear++));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatasourceConnection.closeConnection(conn, pstmt, set);
		}
		return ylist;
	}


}
