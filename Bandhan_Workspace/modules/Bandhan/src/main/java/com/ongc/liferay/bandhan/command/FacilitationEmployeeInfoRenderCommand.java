package com.ongc.liferay.bandhan.command;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.ongc.liferay.bandhan.constants.BandhanPortletKeys;
import com.ongc.liferay.bandhan.model.User;
import com.ongc.liferay.bandhan.service.UserService;
import com.ongc.liferay.bandhan.service.Impl.UserServiceImpl;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

@Component(
		 property = {
		 "javax.portlet.name="+ BandhanPortletKeys.FacilitationCenter,
		 "mvc.command.name=getEmployeeInfo"
		 }, service = MVCRenderCommand.class
		 )

public class FacilitationEmployeeInfoRenderCommand implements MVCRenderCommand{

	public final static String renderingEmployeePagePath = "/employeeInfo.jsp";
	private UserService userService=new UserServiceImpl();
	
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {

		String cpfNo=ParamUtil.getString(renderRequest,"cpfNo");
		User userByCPFNumber = userService.getUserByCPFNumber(cpfNo);
		renderRequest.setAttribute("userByCPFNumber", userByCPFNumber);
		return renderingEmployeePagePath;
	}

}

