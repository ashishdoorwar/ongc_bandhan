package com.ongc.liferay.bandhan.command;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.ongc.liferay.bandhan.constants.BandhanPortletKeys;
import com.ongc.liferay.bandhan.model.User;
import com.ongc.liferay.bandhan.service.UserService;
import com.ongc.liferay.bandhan.service.Impl.UserServiceImpl;

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
		 "javax.portlet.name="+BandhanPortletKeys.FindActiveONGCian,
		 "mvc.command.name=getActiveONGCianInfo"
		 }, service = MVCRenderCommand.class
		 )
public class ViewActiveONGCianRenderCommand implements MVCRenderCommand {

	private UserService userService=new UserServiceImpl();
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		// TODO Auto-generated method stub
		
		
		String cpfNo=ParamUtil.getString(renderRequest,"cpfNo");
		User activeUserData=userService.getReportUserByCpfNo(cpfNo);
		
		renderRequest.setAttribute("activeUserData", activeUserData);
		return "/findActiveONGCian/viewActiveONGCian.jsp";
	}

}
