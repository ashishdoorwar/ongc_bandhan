package com.ongc.liferay.bandhan.command;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.ongc.liferay.bandhan.connection.DatasourceConnection;
import com.ongc.liferay.bandhan.constants.BandhanPortletKeys;
import com.ongc.liferay.bandhan.util.CommonUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" +BandhanPortletKeys.WelfareTrust,
			"mvc.command.name=saveTrust"
		},
		service = MVCActionCommand.class
	)
public class SaveTrustActionCommand extends BaseMVCActionCommand{

	private static final Log LOGGER = LogFactoryUtil.getLog(SaveTrustActionCommand.class);
	
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		// TODO Auto-generated method stub
		String cpfnumber=ParamUtil.getString(actionRequest,"cpfnumber");
		String trustName=ParamUtil.getString(actionRequest,"trustName");
		String reportName=ParamUtil.getString(actionRequest,"reportName");
		String fy=ParamUtil.getString(actionRequest,"fy");
		String quarter=ParamUtil.getString(actionRequest,"quarter");
		String panNo=ParamUtil.getString(actionRequest,"panNo");
		String mobileNo=ParamUtil.getString(actionRequest,"mobileNo");
		String email=ParamUtil.getString(actionRequest,"email");
		/**************Email Update********************  */

		boolean checkParameter = CommonUtil.checkParameter(actionRequest);
		if(checkParameter) {
			SessionErrors.add(actionRequest,"error");
		}
		else {


		PreparedStatement ps =null;
		ResultSet res=null;
		Connection conn=null;
		try {
		 conn=DatasourceConnection.getConnection();
		 ps = conn.prepareStatement("INSERT INTO WELFARE_TRUST_REQUEST (CPFNUM, TRUST_NAME, REPORT, FIN_YR, QUARTER, PAN_NO, MOBILE, EMAIL, STATUS,CREATED_ON) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'G',now())");

		if(cpfnumber !=null){
		ps.setString(1, cpfnumber);
		ps.setString(2, trustName);
		ps.setString(3, reportName);
		ps.setString(4, fy);
		ps.setString(5, quarter);
		ps.setString(6, panNo);
		ps.setString(7, mobileNo);
		ps.setString(8, email);
		int i=ps.executeUpdate(); 

		if(i==1){
//		out.println("Your details successully inserted.");
		}else
		{
//		out.println("Error");
		}
		}else{
//		out.println("Error");
		}
//		log.info("tttttttttttttttttttttttttt-->");
		}catch(Exception e2){
			LOGGER.info("Exception inside Welfare process jsp "+e2);
		e2.printStackTrace();
		//response.sendRedirect("facilitationForm.jsp?status=2");
		}
		 finally {
		     		if(res!=null)
						res.close();			
					if(ps!=null)
						ps.close();			
					if(conn!=null)
						conn.close();
		      
			}
		}
	}

}
