package com.ongc.liferay.bandhan.command;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.ongc.liferay.bandhan.constants.BandhanPortletKeys;
import com.ongc.liferay.bandhan.service.UserService;
import com.ongc.liferay.bandhan.service.Impl.UserServiceImpl;

import java.io.File;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.apache.commons.io.FileUtils;
/**
 *  
 * @author Ranjeet
 */
@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" +BandhanPortletKeys.BandhanProfile,
			"mvc.command.name=update_bandhan_profile_pic"
		},
		service = MVCActionCommand.class
	)
public class EditUserProfilePictureActionCommand extends BaseMVCActionCommand {

	private UserService userService=new UserServiceImpl();
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		ResourceBundle szResBundl = ResourceBundle.getBundle("/content/bandhan");				
		String filePath = szResBundl.getString("profileFilePath").toString().trim();
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		String cpfNo=ParamUtil.getString(actionRequest, "cpfNo");		
		FileUtils.copyFile(uploadPortletRequest.getFile("userPic"), new File(filePath,cpfNo.concat(".jpg")));
		}
	

}
