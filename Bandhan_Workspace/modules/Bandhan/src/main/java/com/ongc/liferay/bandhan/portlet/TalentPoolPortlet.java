package com.ongc.liferay.bandhan.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.ongc.liferay.bandhan.constants.BandhanPortletKeys;
import com.ongc.liferay.bandhan.model.Faculty;
import com.ongc.liferay.bandhan.model.Talent;
import com.ongc.liferay.bandhan.model.User;
import com.ongc.liferay.bandhan.service.UserService;
import com.ongc.liferay.bandhan.service.Impl.UserServiceImpl;

import java.io.IOException;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ranjeet 
 */
@Component(
		immediate = true,
		property = {
				"com.liferay.portlet.display-category=ONGC Bandhan",
				"com.liferay.portlet.header-portlet-css=/css/main.css",
				"com.liferay.portlet.instanceable=true",
				"javax.portlet.display-name=TalentPool",
				"javax.portlet.init-param.template-path=/",
				"javax.portlet.init-param.view-template=/talentPool/view.jsp",
				"javax.portlet.name=" + BandhanPortletKeys.TalentPool,
				"javax.portlet.resource-bundle=content.Language",
				"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
		)
public class TalentPoolPortlet  extends MVCPortlet{

	
	private UserService userService=new UserServiceImpl();
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		User user = userService.getUser();
		List<Talent> talentData = userService.getTalentData(user.getCpfNo());
		renderRequest.setAttribute("talentData", talentData);
		super.doView(renderRequest, renderResponse);
	}
	
}
