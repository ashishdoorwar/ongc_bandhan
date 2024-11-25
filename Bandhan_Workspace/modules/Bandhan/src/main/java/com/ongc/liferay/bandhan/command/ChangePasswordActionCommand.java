package com.ongc.liferay.bandhan.command;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.ongc.liferay.bandhan.constants.BandhanPortletKeys;
import com.ongc.liferay.bandhan.util.CommonUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.Cookie;

import org.osgi.service.component.annotations.Component;

/**
 *  
 * @author Ranjeet
 */
@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + BandhanPortletKeys.ChanngePassword,
				"mvc.command.name=change_password"
		},
		service = MVCActionCommand.class
		)
public class ChangePasswordActionCommand extends BaseMVCActionCommand  {
	private static final Log LOGGER = LogFactoryUtil.getLog(ChangePasswordActionCommand.class);
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		// TODO Auto-generated method stub
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String current =ParamUtil.getString(actionRequest, "current");
		String password1 = ParamUtil.getString(actionRequest, "password1");
		String password2 =ParamUtil.getString(actionRequest, "password2");
		String errorKey = "";
		boolean checkParameter = CommonUtil.checkParameter(actionRequest);
		if(checkParameter) {
			SessionErrors.add(actionRequest,"error");
		}
		else {
		try {
			String authType = themeDisplay.getCompany().getAuthType();
			String login = "";

			if(authType.equals(CompanyConstants.AUTH_TYPE_EA)){
				login = themeDisplay.getUser().getEmailAddress();
			}else if(authType.equals(CompanyConstants.AUTH_TYPE_SN)){
				login = themeDisplay.getUser().getScreenName();
			}else if(authType.equals(CompanyConstants.AUTH_TYPE_ID)){
				login = String.valueOf(themeDisplay.getUser().getUserId());
			}

			long userId=UserLocalServiceUtil.authenticateForBasic(themeDisplay.getCompanyId(), authType, login, current);
			if(themeDisplay.getUserId()!=userId)
			{
				errorKey = "invalid-current-password";
				throw new Exception("Invalid current password.");
			}
			if(!password1.equals(password2))
			{
				errorKey = "confirm-new-password";
				throw new Exception("Please confirm new password.");
			}
			User user= UserLocalServiceUtil.getUser(userId);
			user.setPasswordModified(true);

			user = UserLocalServiceUtil.updatePassword(userId, password1, password2, false);
			
			Cookie[] delCookies = actionRequest.getCookies();
			try {
			for( Cookie cookie : delCookies) {
			cookie.setMaxAge(0);
			actionResponse.addProperty(cookie);
			}
			}catch(Exception e) {
			LOGGER.info("del:::cookieValue::exception:::"+e);
			}
			
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(Validator.isNull(errorKey)){
			SessionMessages.add(actionRequest, "password-update-success");
		}else{
			SessionErrors.add(actionRequest, errorKey);
		}
		}
	}

}
