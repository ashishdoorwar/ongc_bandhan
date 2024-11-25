package com.ongc.liferay.bandhan.command;
/**
 *  
 * @author Ranjeet
 */

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.ongc.liferay.bandhan.constants.BandhanPortletKeys;
import com.ongc.liferay.bandhan.dao.EmployeeDeclarationDao;
import com.ongc.liferay.bandhan.dao.Impl.EmployeeDeclarationDaoImpl;
import com.ongc.liferay.bandhan.dao.Impl.FeedbackPostDaoImpl;
import com.ongc.liferay.bandhan.dao.Impl.UserDaoImpl;
import com.ongc.liferay.bandhan.model.FeedbackHrEnablers;
import com.ongc.liferay.bandhan.model.User;
import com.ongc.liferay.bandhan.util.CommonUtil;
import com.ongc.liferay.bandhan.util.EmployeeDeclarationModel;
import com.ongc.liferay.bandhan.util.Mailutils;
import com.ongc.liferay.bandhan.util.PdfGeneration;
import com.ongc.liferay.bandhan.util.SmsUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" +BandhanPortletKeys.EmployeeDeclaration,
			"mvc.command.name=add_employee_declaration"
		},
		service = MVCActionCommand.class
	)
public class SaveEmployeeDeclarationActionCommand extends BaseMVCActionCommand{

	
	final static Log log = LogFactoryUtil.getLog(SaveEmployeeDeclarationActionCommand.class.getName());
	EmployeeDeclarationDao declarationDao=new EmployeeDeclarationDaoImpl(); 
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		// TODO Auto-generated method stub
		
		EmployeeDeclarationModel ed=new EmployeeDeclarationModel();
		boolean result=false;
		String rFlag = "success";
		try {
		String cpfno=ParamUtil.getString(actionRequest, "cpfno");
		String currentPlace=ParamUtil.getString(actionRequest, "currentPlace");
		ed.setCpfNo(cpfno);
		ed.setCurrentPlace(currentPlace);
		ed.setYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
		boolean checkParameter = CommonUtil.checkParameter(actionRequest);
		if(checkParameter) {
			SessionErrors.add(actionRequest,"error");
		}
		else {
			boolean isExist=declarationDao.isExist(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)), cpfno);
			if(isExist) {
				SessionErrors.add(actionRequest, "exist");
			}else {
				result=declarationDao.insertEmployeeDecalarationModel(ed);
				if(result) {
					rFlag = "Request submit successfully.";
					SessionMessages.add(actionRequest, "success");	
					
					postMail(ed,"");
				}
				else {
					rFlag = "Something went wrong.";
					SessionErrors.add(actionRequest, "somethingwrong");	
				}				
			}		
		}
		
		}catch(Exception e) {
			log.info(e);
		}
	}
	
public boolean postMail(EmployeeDeclarationModel model,String msg) {
		

		boolean flag = false;
		log.info("**************postMail*****************");
		log.info("**************Postmail For Testing for CPFNO*****************");
		String subject = "Non-employment Declaration Form from retired employee";
		UserDaoImpl userDao=new UserDaoImpl();
		User user = userDao.getUserByCpfNo(model.getCpfNo());
		log.info("**************Postmail For Testing for CPFNO*****************"+user);
		log.info("**************Location*****************"+user.getLocation());
		
		String Name = user.getUserName();
		log.info("**************Location*****************"+Name);
		
		String filePath="/ongcrevampdata/appdata/ongcbandhan/employee-declaration/pdfs/"+user.getCpfNo()+"_"+model.getYear()+".pdf";
		
		String sms = "Non-employee Declaration form has been submitted by  [Cpf No "
				+ model.getCpfNo()+ " on "
				+ convertDateToString(new Date())
				+ "]" ;
		log.info("**************postMail***********Path******"+filePath);
		msg=setMessge(user,model);
		
		log.info("user name:"+user.getUserName()+",user location :"+user.getLocation());

		PdfGeneration.genrateEmployeeDeclarationFormPdf(user,model,filePath);
		log.info("pdf filePath:"+filePath);
		FeedbackHrEnablers enabler=new FeedbackHrEnablers();
		FeedbackPostDaoImpl postDao = new FeedbackPostDaoImpl();
		log.info("**************postMail***********CPF Number** Before****");
		enabler=postDao.getEnablerByLocation(user.getLocation());
		log.info("**************postMail***********CPF Number******"+enabler.getCpfNo());
		
		if(enabler.getCpfNo()!=null){
			log.info("**************Postmail For Testing    IF ********1*********");
			log.info(">enabler :++++++++:"+enabler.getCpfNo());
			flag = Mailutils.sendEnablerMail(subject, msg,enabler.getCpfNo(),filePath);
			log.info(">**************enabler :"+enabler.getMobileNo());
			SmsUtil.sendSMStoEnabler(enabler.getMobileNo(), sms);
		}		
		
		log.info("postMail/sms flag" + flag);

		return flag;
	}
public String convertDateToString(Date dt){
	SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy");
	String currentDt=sf.format(dt);
	return currentDt;
}

private String setMessge(User user,EmployeeDeclarationModel model){
	
	StringBuilder sf=new StringBuilder();
	sf.append("Dear Sir/Madam,<br /><br />");
	sf.append("Mr./Mrs. <strong>"+user.getUserName()+ "</strong>,CPF  <strong>"+model.getCpfNo()); 
	sf.append("</strong> has submitted his/her non-employment form for the year <strong>"+ model.getYear() +"</strong> on bandhan.ongc.co.in.");
	
	sf.append("on Date:"+convertDateToString(new Date()));
	sf.append("<br /><br /> Current Residence Address:"+model.getCurrentPlace()+"<br /><br />");
	return sf.toString();
}
	
}
