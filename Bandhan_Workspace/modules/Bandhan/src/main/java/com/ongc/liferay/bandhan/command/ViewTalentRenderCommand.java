package com.ongc.liferay.bandhan.command;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.ongc.liferay.bandhan.constants.BandhanPortletKeys;
import com.ongc.liferay.bandhan.model.Faculty;
import com.ongc.liferay.bandhan.model.Talent;
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
		 "javax.portlet.name="+BandhanPortletKeys.TalentPool,
		 "mvc.command.name=getTalentDetail"
		 }, service = MVCRenderCommand.class
		 )
public class ViewTalentRenderCommand implements MVCRenderCommand{

	private UserService userService=new UserServiceImpl();
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		String id= ParamUtil.getString(renderRequest, "id");
		Talent talent = userService.getTalentDataById(id);
		renderRequest.setAttribute("talent",talent);
		
		return "/talentPool/addAndViewTalent.jsp";
	}
}
