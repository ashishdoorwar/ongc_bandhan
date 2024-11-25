package com.ongc.liferay.bandhan.command;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.ongc.liferay.bandhan.constants.BandhanPortletKeys;
import com.ongc.liferay.bandhan.model.Faculty;
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
			"javax.portlet.name=" +BandhanPortletKeys.FacultyApplication,
			"mvc.command.name=add_faculty"
		},
		service = MVCActionCommand.class
	)
public class AddFacultyActionCommand extends BaseMVCActionCommand{

	private UserService userService=new UserServiceImpl();
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		String cpfno =ParamUtil.getString(actionRequest, "cpfno");
		String username =ParamUtil.getString(actionRequest, "username");
		String designation =ParamUtil.getString(actionRequest, "designation");
		String dos =ParamUtil.getString(actionRequest, "dos");
		String mobileNo =ParamUtil.getString(actionRequest, "mobileNo");
		String emailId =ParamUtil.getString(actionRequest, "emailId");
		String faxno =ParamUtil.getString(actionRequest, "faxno");
		String address =ParamUtil.getString(actionRequest, "address");
		String city =ParamUtil.getString(actionRequest, "city");
		String specialisation =ParamUtil.getString(actionRequest, "specialisation");
		String academics =ParamUtil.getString(actionRequest, "academics");
		String code =ParamUtil.getString(actionRequest, "code");
		String desc =ParamUtil.getString(actionRequest, "desc");
		boolean checkParameter = CommonUtil.checkParameter(actionRequest);
		if(checkParameter) {
			SessionErrors.add(actionRequest,"error");
		}
		else {
		Faculty faculty=new Faculty();
		
		faculty.setDos(dos);
		faculty.setMobileNo(mobileNo);
		faculty.setEmailId(emailId);
		faculty.setFaxno(faxno);
		faculty.setAddress(address);
		faculty.setCity(city);
		faculty.setSpecialisation(specialisation);
		faculty.setAcademics(academics);
		faculty.setCode(code);
		faculty.setDescription(desc);
		
		User user = userService.getUser();
		userService.insertFacProfile(user, faculty);
		
		}
	}

}
