package com.ongc.liferay.bandhan.command;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.ongc.liferay.bandhan.constants.BandhanPortletKeys;
import com.ongc.liferay.bandhan.model.FeedbackCategory;
import com.ongc.liferay.bandhan.model.FeedbackPost;
import com.ongc.liferay.bandhan.service.FeedbackCategoryService;
import com.ongc.liferay.bandhan.service.FeedbackPostService;
import com.ongc.liferay.bandhan.service.Impl.FeedbackCategoryServiceImpl;
import com.ongc.liferay.bandhan.service.Impl.FeedbackPostServiceImpl;
import com.ongc.liferay.bandhan.util.ReportConstant;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

@Component(
		 property = {
		 "javax.portlet.name="+ BandhanPortletKeys.FEEDBACK,
		 "mvc.command.name=searchFeedbackPost"
		 }, service = MVCRenderCommand.class
		 )

public class FeedbackPostSearchRenderCommand implements MVCRenderCommand{

	private FeedbackPostService feedbackPostService=new FeedbackPostServiceImpl();;
	private FeedbackCategoryService feedbackCategoryService =new FeedbackCategoryServiceImpl();
	public final static String renderingFeedbackSearchPagePath = "/feedback/viewFeedbackList.jsp";
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		int catId = ParamUtil.getInteger(renderRequest,"category");
		String stdate = ParamUtil.getString(renderRequest,"startDate");
		String endate = ParamUtil.getString(renderRequest,"endDate");
		String posId = ParamUtil.getString(renderRequest,"postID");
		FeedbackCategory category = new FeedbackCategory();
		category.setCategoryId(catId);
		category.setStartDate(stdate);
		category.setEndDate(endate);
		category.setPostId(posId);
		int page = 1;
		int recordsPerPage = 20;
		List<FeedbackPost> postList = feedbackPostService.searchPostList(category);
		int noOfRecords = postList.size();
		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
//		if (post.getPageId() > 0)
//			page = post.getPageId();
//		int start = (page - 1) * recordsPerPage;
//		int end = start + recordsPerPage;
		List<FeedbackCategory> categoryList = feedbackCategoryService.getCategoryListByFeedbakId(category.getFeedbackId());
		renderRequest.setAttribute("postList", postList);
		renderRequest.setAttribute("categoryList", categoryList);
		renderRequest.setAttribute("noOfPages", noOfPages);
		renderRequest.setAttribute("currentPage", page);
		//renderRequest.setSessionAttribute(ReportConstant.FEEDBACKPOST_LIST_ATTR_NAME,postList);
		return renderingFeedbackSearchPagePath;
	}

}
