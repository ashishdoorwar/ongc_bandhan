package com.ongc.liferay.bandhan.command;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.ongc.liferay.bandhan.constants.BandhanPortletKeys;
import com.ongc.liferay.bandhan.model.User;
import com.ongc.liferay.bandhan.service.SearchService;
import com.ongc.liferay.bandhan.service.Impl.SearchServiceImpl;

import java.util.List;

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
		 "javax.portlet.name="+BandhanPortletKeys.FindRetiredONGCian,
		 "mvc.command.name=search_retired_ONGCian"
		 }, service = MVCRenderCommand.class
		 )
public class SearchFindRetiredONGCian implements MVCRenderCommand{

	private SearchService searchService=new SearchServiceImpl(); 
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		// TODO Auto-generated method stub
		User user=new User();
		String cpfNo =ParamUtil.getString(renderRequest, "cpfNo");
		String empName =ParamUtil.getString(renderRequest, "empName");
		String designation =ParamUtil.getString(renderRequest, "designation");
		String location =ParamUtil.getString(renderRequest, "location");
		String mobileNo =ParamUtil.getString(renderRequest, "mobileNo");
		user.setCpfNo(cpfNo);
		user.setUserName(empName);
		user.setLocation(location);
		user.setMobileNo(mobileNo);
		user.setDesignation(designation);
		List<User> userList = searchService.searchUser(user,false);
		renderRequest.setAttribute("userList", userList);
		return "/findRetiredONGCian/list.jsp";
	}
}
