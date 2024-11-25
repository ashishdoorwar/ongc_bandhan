package com.ongc.liferay.bandhan.command;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.ongc.liferay.bandhan.constants.BandhanPortletKeys;
import com.ongc.liferay.bandhan.model.Faculty;
import com.ongc.liferay.bandhan.service.UserService;
import com.ongc.liferay.bandhan.service.Impl.UserServiceImpl;

import java.util.ArrayList;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 *  
 * @author Ranjeet
 */
@Component(
		 property = {
		 "javax.portlet.name="+BandhanPortletKeys.FacultyApplication,
		 "mvc.command.name=getFacultyDetail"
		 }, service = MVCRenderCommand.class
		 )
public class ViewFacultyRenderCommand implements MVCRenderCommand{
	private UserService userService=new UserServiceImpl();
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		String id= ParamUtil.getString(renderRequest, "id");
		String backUrl = ParamUtil.getString(renderRequest, "backURL");
		Faculty faculty = userService.getDataById(id);
		ArrayList<String> programCodeList= userService.getProgramCodeList();
		renderRequest.setAttribute("faculty",faculty);
		renderRequest.setAttribute("programCodeList",programCodeList);
		renderRequest.setAttribute("backUrl",backUrl);
		return "/facultyApplication/addAndViewFaculty.jsp";
	}

}
