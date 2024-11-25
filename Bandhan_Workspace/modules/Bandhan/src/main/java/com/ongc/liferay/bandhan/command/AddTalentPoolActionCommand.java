package com.ongc.liferay.bandhan.command;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.ongc.liferay.bandhan.constants.BandhanPortletKeys;
import com.ongc.liferay.bandhan.model.Talent;
import com.ongc.liferay.bandhan.model.User;
import com.ongc.liferay.bandhan.service.UserService;
import com.ongc.liferay.bandhan.service.Impl.UserServiceImpl;
import com.ongc.liferay.bandhan.util.CommonUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
/**
 *  
 * @author Ranjeet
 */
@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" +BandhanPortletKeys.TalentPool,
			"mvc.command.name=add_talent"
		},
		service = MVCActionCommand.class
	)
public class AddTalentPoolActionCommand extends BaseMVCActionCommand {
	private UserService userService=new UserServiceImpl();
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		
		// TODO Auto-generated method stub
		String cpfno =ParamUtil.getString(actionRequest, "cpfno");
		String userName =ParamUtil.getString(actionRequest, "userName");
		String dob =ParamUtil.getString(actionRequest, "dob");
		String qualification =ParamUtil.getString(actionRequest, "qualification");
		String dateOfSep =ParamUtil.getString(actionRequest, "dateOfSep");
		String designation =ParamUtil.getString(actionRequest, "designation");
		String lastTenFrm =ParamUtil.getString(actionRequest, "lastTenFrm");
		String lastTenTo =ParamUtil.getString(actionRequest, "lastTenTo");
		String department =ParamUtil.getString(actionRequest, "department");
		String skillSet =ParamUtil.getString(actionRequest, "skillSet");
		String domain =ParamUtil.getString(actionRequest, "domain");
		String mobileNo =ParamUtil.getString(actionRequest, "mobileNo");
		String emailId =ParamUtil.getString(actionRequest, "emailId");
		String address =ParamUtil.getString(actionRequest, "address");
		String willingNo =ParamUtil.getString(actionRequest, "willingNo");
		Talent talent=new Talent();
		User user = userService.getUser();
		boolean checkParameter = CommonUtil.checkParameter(actionRequest);
		if(checkParameter) {
			SessionErrors.add(actionRequest,"error");
		}
		else {
		talent.setCpfno(cpfno);
		talent.setUsername(userName);
		talent.setDob(dob);
		talent.setQualification(qualification);
		talent.setDos(dateOfSep);
		talent.setDesignation(designation);
		talent.setLastTenFrm(lastTenFrm);
		talent.setLastTenTo(lastTenTo);
		talent.setDepartment(department);
		talent.setSkillSet(skillSet);
		talent.setDomain(domain);
		talent.setMobileNo(mobileNo);
		talent.setEmailId(emailId);
		talent.setAddress(address);
		userService.insertTalentData(user, talent);
		}
	}

}
