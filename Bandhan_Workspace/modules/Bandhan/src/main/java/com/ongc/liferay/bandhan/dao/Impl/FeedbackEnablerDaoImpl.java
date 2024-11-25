package com.ongc.liferay.bandhan.dao.Impl;

import com.ongc.liferay.bandhan.connection.DatasourceConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedbackEnablerDaoImpl {


	
	public List<Map<String, String>> getFeedbackEnablerList(){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		Statement stmt=null;
		List<Map<String, String>> enablerList=new ArrayList<Map<String, String>>();
		
		try{
			conn=DatasourceConnection.getConnection();
			String query="Select a.cpf_no,initcap(b.emp_name) as name,b.designation,a.location  from RPTT_FEEDBACK_ENABLERS_MASTER a, ONGC_USER_MASTER b where a.cpf_no=b.cpf_number and a.role='L1'";
			stmt=conn.createStatement();
			set=stmt.executeQuery(query);
			int i=1;
			while(set.next()){
				Map<String, String> map=new HashMap<String, String>();
				map.put("SLNO", i+"");
				map.put("NAME", set.getString("NAME"));
				map.put("CPF", set.getString("cpf_no"));
				map.put("DESIGNATION", set.getString("designation"));
				map.put("PLACE", set.getString("location"));
				
				enablerList.add(map);
				i++;
			}			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		  finally{
				DatasourceConnection.closeConnection(conn, stmt,set);
			}
		return enablerList;
	}
}
