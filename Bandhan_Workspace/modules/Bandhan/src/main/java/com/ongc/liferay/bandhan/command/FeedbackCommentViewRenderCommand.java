package com.ongc.liferay.bandhan.command;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.ongc.liferay.bandhan.constants.BandhanPortletKeys;
import com.ongc.liferay.bandhan.model.FeedbackComment;
import com.ongc.liferay.bandhan.model.FeedbackHrCategory;
import com.ongc.liferay.bandhan.model.FeedbackHrEnablers;
import com.ongc.liferay.bandhan.model.FeedbackPost;
import com.ongc.liferay.bandhan.model.FeedbackReason;
import com.ongc.liferay.bandhan.model.User;
import com.ongc.liferay.bandhan.service.FeedbackCommentService;
import com.ongc.liferay.bandhan.service.FeedbackPostService;
import com.ongc.liferay.bandhan.service.UserService;
import com.ongc.liferay.bandhan.service.Impl.FeedbackCommentServiceImpl;
import com.ongc.liferay.bandhan.service.Impl.FeedbackPostServiceImpl;
import com.ongc.liferay.bandhan.service.Impl.UserServiceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

@Component(
		 property = {
		 "javax.portlet.name="+ BandhanPortletKeys.FEEDBACK,
		 "mvc.command.name=getCommentInfo"
		 }, service = MVCRenderCommand.class
		 )
public class FeedbackCommentViewRenderCommand implements MVCRenderCommand{

	public final static String renderingCommentPagePath = "/feedback/feedbackCommentInfo.jsp";

	private FeedbackPostService feedbackPostService=new FeedbackPostServiceImpl();
	private FeedbackCommentService feedbackCommentService = new FeedbackCommentServiceImpl();
	private UserService userService = new UserServiceImpl();
	
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		// TODO Auto-generated method stub
		int postId = ParamUtil.getInteger(renderRequest, "postId");
		String backURL=ParamUtil.getString(renderRequest,"backURL");
		String kword = null;
		FeedbackPost post = new FeedbackPost();
		post.setPostId(postId);
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");
		List<FeedbackComment> commentList = new ArrayList<FeedbackComment>();
		List<FeedbackReason> reasonList = new ArrayList<FeedbackReason>();
		List<FeedbackHrCategory> hrCategoryList = new ArrayList<FeedbackHrCategory>();
		List<FeedbackHrEnablers> HREnablers = new ArrayList<FeedbackHrEnablers>();
		int hits = 0;
		int likes = 0;
		int dislikes = 0;
		User user = userService.getUser();
		post = feedbackPostService.getFeedbackPost(post);
		post.setPostDateText(post.getPostDate().toString());
		if (user != null && (user.getCpfNo().equals("122385") || user.getCpfNo().equals("57392")
			|| user.getCpfNo().equals("121229")	|| user.getCpfNo().equals("76121")|| user.getCpfNo().equals("78619") 
			|| user.getCpfNo().equals("testb"))) {
			commentList = feedbackCommentService.getAllCommentListByPostId(postId);
		} else {
			commentList = feedbackCommentService.getCommentListByPostId(postId);
		}
		for (int i = 0; i < commentList.size(); i++) {
			commentList.get(i).setPostTextDate(sdf.format(commentList.get(i).getPostDate()));
		}
		reasonList = feedbackCommentService.getReasonsListByPostId(post.getPostId());
//		hrCategoryList = feedbackPostService.getHRCategoryList();
		hrCategoryList = null;
		HREnablers = feedbackPostService.getHREnablersList();
		//this.pName = request.getParameter("pageName");
		//kword = request.getParameter("category.keyword");
		renderRequest.setAttribute("HREnablersList", HREnablers);
		renderRequest.setAttribute("user", user);
		renderRequest.setAttribute("hrCategoryList", hrCategoryList);
		renderRequest.setAttribute("commentList", commentList);
		renderRequest.setAttribute("reasonList", reasonList);
		renderRequest.setAttribute("post", post);
		renderRequest.setAttribute("backURL", backURL);
		return renderingCommentPagePath;
	}

}
