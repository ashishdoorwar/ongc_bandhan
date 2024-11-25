/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.ongc.liferay.bandhan.command;

import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.UserPasswordException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManager;
import com.liferay.portal.kernel.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.kernel.util.UsersAdmin;
import com.ongc.liferay.bandhan.constants.BandhanPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BandhanPortletKeys.ChanngePassword,
		"mvc.command.name=update_password"
	},
	service = MVCActionCommand.class
)
public class UpdatePasswordMVCActionCommand extends BaseMVCActionCommand {

	private static final Log _log = LogFactoryUtil.getLog(UpdatePasswordMVCActionCommand.class);
	
	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		
		_log.info("UpdatePasswordMVCActionCommand Bandhan Start======");
		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
			//User user = _portal.getSelectedUser(actionRequest);
			User user =themeDisplay.getUser();
			UserPermissionUtil.check(
				themeDisplay.getPermissionChecker(), user.getUserId(),
				ActionKeys.UPDATE);

			String newPassword1 = ParamUtil.getString(actionRequest,"password1");
			String newPassword2 = ParamUtil.getString(actionRequest,"password2");

			boolean passwordReset = ParamUtil.getBoolean(
					actionRequest, "passwordReset");
				PasswordPolicy passwordPolicy = user.getPasswordPolicy();

				boolean ldapPasswordPolicyEnabled =
					LDAPSettingsUtil.isPasswordPolicyEnabled(user.getCompanyId());
				if ((user.getLastLoginDate() == null) &&
					(((passwordPolicy == null) && !ldapPasswordPolicyEnabled) ||
					 ((passwordPolicy != null) && passwordPolicy.isChangeable() &&
					  passwordPolicy.isChangeRequired()))) {
					passwordReset = true;
				}

				String reminderQueryQuestion = BeanParamUtil.getString(
					user, actionRequest, "reminderQueryQuestion");
				if (reminderQueryQuestion.equals(UsersAdmin.CUSTOM_QUESTION)) {
					
					reminderQueryQuestion = BeanParamUtil.getStringSilent(
						user, actionRequest, "reminderQueryCustomQuestion");
				}

				String reminderQueryAnswer = BeanParamUtil.getString(
					user, actionRequest, "reminderQueryAnswer");
				boolean passwordModified = false;

				if (Validator.isNotNull(newPassword1) ||
					Validator.isNotNull(newPassword2)) {
					User user1=_userLocalService.updatePassword(
						user.getUserId(), newPassword1, newPassword2,
						passwordReset);
					passwordModified = true;
				}

				_userLocalService.updatePasswordReset(
					user.getUserId(), passwordReset);
				if (Validator.isNotNull(reminderQueryQuestion) &&
					Validator.isNotNull(reminderQueryAnswer) &&
					!reminderQueryAnswer.equals(Portal.TEMP_OBFUSCATION_VALUE)) {
					_userLocalService.updateReminderQuery(
						user.getUserId(), reminderQueryQuestion,
						reminderQueryAnswer);
					
				
				}

				if ((user.getUserId() == themeDisplay.getUserId()) &&
					passwordModified) {
					String login = null;

					Company company = themeDisplay.getCompany();

					String authType = company.getAuthType();

					if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
						login = user.getEmailAddress();
					}
					else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
						login = user.getScreenName();
					}
					else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
						login = String.valueOf(user.getUserId());
					}
					_authenticatedSessionManager.login(
						_portal.getOriginalServletRequest(
							_portal.getHttpServletRequest(actionRequest)),
						_portal.getHttpServletResponse(actionResponse), login,
						newPassword1, false, null);
					_log.info("UpdatePasswordMVCActionCommand Bandhan Success ======");
				}
			}
			catch (Exception exception) {
				if (exception instanceof NoSuchUserException ||
					exception instanceof PrincipalException) {
					_log.info("UpdatePasswordMVCActionCommand1 ======"+exception);
					SessionErrors.add(actionRequest, exception.getClass());

					actionResponse.setRenderParameter("mvcPath", "/error.jsp");
				}
				else if (exception instanceof UserPasswordException) {
					_log.info("UpdatePasswordMVCActionCommand2 ======"+exception);
					SessionErrors.add(
						actionRequest, exception.getClass(), exception);

					String redirect = _portal.escapeRedirect(
						ParamUtil.getString(actionRequest, "redirect"));

					if (Validator.isNotNull(redirect)) {
						sendRedirect(actionRequest, actionResponse, redirect);
					}
				}
				else {
					_log.info("UpdatePasswordMVCActionCommand3 ======"+exception);
					throw exception;
				}
			}
		}

		@Reference
		private AuthenticatedSessionManager _authenticatedSessionManager;

		@Reference
		private Portal _portal;

		@Reference
		private UserLocalService _userLocalService;

		@Reference
		private UserService _userService;

	}