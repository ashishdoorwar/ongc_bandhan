package com.ongc.liferay.bandhan.portlet;

import javax.portlet.Portlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.ongc.liferay.bandhan.constants.BandhanPortletKeys;

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
		"javax.portlet.display-name=findActiveONGCian",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/findActiveONGCian/view.jsp",
		"javax.portlet.name=" + BandhanPortletKeys.FindActiveONGCian,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class FindActiveONGCianPortlet extends MVCPortlet{

}
