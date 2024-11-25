package com.ongc.liferay.bandhan.command;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.ongc.liferay.bandhan.constants.BandhanPortletKeys;
import com.ongc.liferay.bandhan.model.User;
import com.ongc.liferay.bandhan.service.UserService;
import com.ongc.liferay.bandhan.service.Impl.UserServiceImpl;
import com.ongc.liferay.bandhan.util.CommonUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

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
			"javax.portlet.name=" +BandhanPortletKeys.BandhanProfile,
			"mvc.command.name=update_bandhan_profile"
		},
		service = MVCActionCommand.class
	)
public class EditUserProfileActionCommand extends BaseMVCActionCommand {

	private UserService userService=new UserServiceImpl();
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		
		String cpfNo=ParamUtil.getString(actionRequest, "cpfNo");
		String userName=ParamUtil.getString(actionRequest, "userName");
		String designation=ParamUtil.getString(actionRequest, "designation");
		String dateOfSep=ParamUtil.getString(actionRequest, "dateOfSep");
		String dob=ParamUtil.getString(actionRequest, "dob");
		String dateOfJoining=ParamUtil.getString(actionRequest, "dateOfJoining");
		String mobileNo=ParamUtil.getString(actionRequest, "mobileNo");
		String landLineNo=ParamUtil.getString(actionRequest, "landLineNo");
		String location=ParamUtil.getString(actionRequest, "location");
		String panNumber=ParamUtil.getString(actionRequest, "panNumber");
		String emailId=ParamUtil.getString(actionRequest, "emailId");
//		String settlementAddress=ParamUtil.getString(actionRequest, "settlementAddress");
		String communicatedAddress=ParamUtil.getString(actionRequest, "communicatedAddress");
		String aboutMe=ParamUtil.getString(actionRequest, "aboutMe");
		boolean checkParameter = CommonUtil.checkParameter(actionRequest);
		if(checkParameter) {
			SessionErrors.add(actionRequest,"error");
		}
		else {
		User user= new User();
		user.setCpfNo(cpfNo);
		user.setUserName(userName);
		user.setDesignation(designation);
		user.setDateOfSep(new SimpleDateFormat("dd-MM-yyyy").parse(dateOfSep));
		user.setDateOfBirth(new SimpleDateFormat("dd-MM-yyyy").parse(dob));
		user.setDateOfJoining(new SimpleDateFormat("dd-MM-yyyy").parse(dateOfJoining));
		user.setMobileNo(mobileNo);
		user.setLandLineNo(landLineNo);
		user.setLocation(location);
		user.setPanNumber(panNumber);
		user.setEmailId(emailId);
		user.setCommunicatedAddress(communicatedAddress);
		user.setAboutMe(aboutMe);
		
		
		userService.updateProfile(user);
		}
	}

}
