package com.ongc.liferay.bandhan.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.ongc.liferay.bandhan.constants.BandhanPortletKeys;
import com.ongc.liferay.bandhan.dao.Impl.UserDaoImpl;
import com.ongc.liferay.bandhan.model.FeedbackCategory;
import com.ongc.liferay.bandhan.model.FeedbackComment;
import com.ongc.liferay.bandhan.model.FeedbackHrEnablers;
import com.ongc.liferay.bandhan.model.FeedbackPost;
import com.ongc.liferay.bandhan.model.User;
import com.ongc.liferay.bandhan.service.FeedbackCategoryService;
import com.ongc.liferay.bandhan.service.FeedbackCommentService;
import com.ongc.liferay.bandhan.service.FeedbackPostService;
import com.ongc.liferay.bandhan.service.UserService;
import com.ongc.liferay.bandhan.service.Impl.FeedbackCategoryServiceImpl;
import com.ongc.liferay.bandhan.service.Impl.FeedbackCommentServiceImpl;
import com.ongc.liferay.bandhan.service.Impl.FeedbackPostServiceImpl;
import com.ongc.liferay.bandhan.service.Impl.UserServiceImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
				"javax.portlet.display-name=Feedback",
				"javax.portlet.init-param.template-path=/",
				"javax.portlet.init-param.view-template=/feedback/view.jsp",
				"javax.portlet.name=" + BandhanPortletKeys.FEEDBACK,
				"javax.portlet.resource-bundle=content.Language",
				"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
		)
public class FeedbackPortlet extends MVCPortlet{
	private static final Log LOGGER = LogFactoryUtil.getLog(FeedbackPortlet.class);
	private FeedbackPostService feedbackPostService=new FeedbackPostServiceImpl();
	private FeedbackCategoryService feedbackCategoryService =new FeedbackCategoryServiceImpl();
	private FeedbackCommentService feedbackCommentService=new FeedbackCommentServiceImpl();
	private UserService userService= new UserServiceImpl();

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		User user= userService.getUser();

		String cpf=user.getCpfNo();
		FeedbackPost post = null;
		FeedbackCategory category = null;
		int stratFrom = 0, showRow = 10, noOfRecords = 0;
		String catId = renderRequest.getParameter("category.categoryId");
		String type = renderRequest.getParameter("type");
		//		if ("query".equalsIgnoreCase(type)) {
		//			return "askexpert";
		//		}
		if (post != null && post.getPageId() != 0)
			stratFrom = (post.getPageId() - 1) * 10;

		try {
			if (category == null) {
				category = new FeedbackCategory();
				category.setFeedbackId(1);
			}
			if (category.getFeedbackId() == 0)
				category.setFeedbackId(1);
			//setSessionAttribute("FEEDBACK_ID", category.getFeedbackId());
			List<FeedbackPost> postList = new ArrayList<FeedbackPost>();
			List<FeedbackCategory> categoryList = feedbackCategoryService.getCategoryListByFeedbakId(category.getFeedbackId());
			boolean roleCheck;
			if (user == null) {
				roleCheck = feedbackCategoryService.roleCheck(user.getCpfNo());
				if (cpf != null && (cpf.equals("76121") || cpf.equals("78619") || cpf.equals("122379"))) {
					postList = feedbackPostService.getCommentListByStatus(category.getFeedbackId(), stratFrom, showRow,category);
					noOfRecords = feedbackPostService.getNoOfFeedbacks(category, "Inactive");
				} else {
					postList = feedbackPostService.getPostListByFeedbackId(category.getFeedbackId(), stratFrom, showRow,category);
					noOfRecords = feedbackPostService.getNoOfFeedbacks(category, "Active");
				}
			} else {
				roleCheck = feedbackCategoryService.roleCheck(user.getCpfNo());
				if (user != null && (user.getCpfNo().equals("76121") || user.getCpfNo().equals("78619") || user.getCpfNo().equals("122379"))) {
					postList = feedbackPostService.getCommentListByStatus(category.getFeedbackId(), stratFrom, showRow,category);
					noOfRecords = feedbackPostService.getNoOfFeedbacks(category, "Inactive");
					postList = feedbackPostService.getCommentListByStatus();
				} else {
					LOGGER.info("In side else condition");
					postList = feedbackPostService.getPostListByFeedbackId(category.getFeedbackId(), stratFrom, showRow,category);
					noOfRecords = feedbackPostService.getNoOfFeedbacks(category, "Active");
				}
			}
			sendOpenPostToChief(postList);
			LOGGER.info(postList);
			renderRequest.setAttribute("categoryList", categoryList);
			renderRequest.setAttribute("postList", postList);
			renderRequest.setAttribute("rolecheck", roleCheck);
			//	renderRequest.setAttribute("noOfPages", noOfPages);
			renderRequest.setAttribute("countRow", noOfRecords);
			renderRequest.setAttribute("pageStart", stratFrom);
			if (catId != null && !"".equalsIgnoreCase(catId)) {
				String catdesc = feedbackPostService.getCatDesc(catId);
				renderRequest.setAttribute("catId", catId);
				renderRequest.setAttribute("catDesc", catdesc);
			} else {
				renderRequest.setAttribute("catId", "");
				renderRequest.setAttribute("catDesc", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.doView(renderRequest, renderResponse);
	}

	public void feedbackPostForm(ActionRequest actionRequest, ActionResponse actionResponse) {
		String cpfNo= ParamUtil.getString(actionRequest, "cpfNo");
		String subject= ParamUtil.getString(actionRequest, "subject");
		int category= ParamUtil.getInteger(actionRequest, "category");
		String content= ParamUtil.getString(actionRequest, "content");
//		String cap= ParamUtil.getString(actionRequest, "captcha");
		User user = userService.getUser();
		user.setCpfNo(cpfNo);
		FeedbackPost post = new FeedbackPost();
		post.setUser(user);
		post.setSubject(subject);
		post.setCategoryId(category);
		post.setMessage(content);
//		post.setCaptchaVal(cap);
		//feedbackPost.getUser().getCpfNo()
		String status = null;
		List<FeedbackHrEnablers> HREnablers = new ArrayList<FeedbackHrEnablers>();
		try {
//			if (cap.equals("123")) {
				feedbackPostService.saveFeedbackPost(post);
				boolean flag1 = false;
				flag1 = feedbackPostService.check_cpf(cpfNo);
				if (flag1) {
					feedbackPostService.saveUser(cpfNo, "feedback_post");
				} else {
					feedbackPostService.updateUser(user.getCpfNo(), "feedback_post");
				}
				
				HREnablers = feedbackPostService.getHREnablersList();
				
				
				Iterator<FeedbackHrEnablers> itr = HREnablers.iterator();
				
				
				while (itr.hasNext()) {
					FeedbackHrEnablers enablers = (FeedbackHrEnablers) itr.next();
					
					
					if (enablers.getRole()!= null && enablers.getRole().trim().equalsIgnoreCase("L1") && enablers.getSubLocation().trim().equalsIgnoreCase(user.getLocation().trim())) {

						String enablerCpf = enablers.getCpfNo();
						String enablerEmail = enablerCpf + "@ongc.co.in";
						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
						String dateStr = sdf.format(date);
						String message = "Dear Sir/Madam,<br /><br />The following Post [Id "
								+ post.getPostId()
								+ " dated "
								+ dateStr
								+ "] has been posted on Feedback Forum of ONGCReports.<br /><br /><br/>"
								+ "<i>Subject: "
								+ post.getSubject()
								+ "<br />'"
								+ post.getMessage()
								+ "'<br /><br />Posted by: "
								+ user.getEmpName()
								+ "<br />CPF No.: "
								+ user.getCpfNo()
								+ "<br />"
								+ "Mobile No.: "
								+ user.getMobileNo()
								+ "</i><br /><br /><br/>You are requested to visit the portal and respond to the query/ issue within 3 working days.<br /><br /><br /><br/>"
								+ "P.S. This is a system-generated mail from ONGCReports Team.  Please do not reply. <br /><br />";
						if (post.getCategoryId() == 42
								|| post.getCategoryId() == 62
								|| post.getCategoryId() == 63
								|| post.getCategoryId() == 64
								|| post.getCategoryId() == 65
								|| post.getCategoryId() == 66
								|| post.getCategoryId() == 67
								|| post.getCategoryId() == 68
								|| post.getCategoryId() == 61) {
							String smstext = "The following Post [Id "
									+ post.getPostId()
									+ " dated "
									+ dateStr
									+ "] has been posted on Feedback Forum of ONGCReports.\n\nYou are requested to visit the portal and respond to the query/ issue within 3 working days.\n\nONGCReports Team";
							// String
							// recieverMobileNo=postDao.getReceiverMobNo(enablerCpf);
							String recieverMobileNo = "09650463270";
							if (recieverMobileNo.length() >= 10) {
								recieverMobileNo = "91"+ recieverMobileNo.substring(recieverMobileNo.length() - 10,recieverMobileNo.length());
							}

						}
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return status;

	}

	private void sendOpenPostToChief(List<FeedbackPost> postList) {

		List<FeedbackHrEnablers> HREnablers = new ArrayList<FeedbackHrEnablers>();
		String chiefCpf = feedbackPostService.getChiefCpf();
		String chiefEmail = chiefCpf + "@ongc.co.in";
		String assetManager = "";
		HREnablers = feedbackPostService.getHREnablersList();
		Iterator<FeedbackPost> itr = postList.iterator();
		try {
			while (itr.hasNext()) {
				FeedbackPost post = (FeedbackPost) itr.next();

				if ((post.getCategoryId() == 42 || post.getCategoryId() == 62
						|| post.getCategoryId() == 63
						|| post.getCategoryId() == 64
						|| post.getCategoryId() == 65
						|| post.getCategoryId() == 66
						|| post.getCategoryId() == 67
						|| post.getCategoryId() == 68 || post.getCategoryId() == 61)
						&& post.getChiefEr().equalsIgnoreCase("NO")
						&& post.getOpStatus().equalsIgnoreCase("OPEN")) {
					FeedbackPost feedbackPost = new FeedbackPost();

					User postUser = userService.getUserByCPFNumber(post.getUser()
							.getCpfNo());

					Date date = post.getPostDate();
					SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
					String dateStr = sdf.format(date);
					String subject = "Posting of a Feedback query on ONGCReports - pending with Enabler.";
					String message = "";

					int workDays = 0;
					String enablerName = "";
					String localEnablerName = "";

					if (post.getRvStatus().equalsIgnoreCase("LOCAL")) {
						try {
							Date startDate = post.getPostDate();
							Date endDate = new Date();

							Calendar startCal = Calendar.getInstance();
							startCal.setTime(startDate);

							Calendar endCal = Calendar.getInstance();
							endCal.setTime(endDate);

							do {
								// excluding start date
								startCal.add(Calendar.DAY_OF_MONTH, 1);
								if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
										&& startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
									++workDays;
								}
							} while (startCal.getTimeInMillis() < endCal
									.getTimeInMillis()); // excluding end date

							Iterator<FeedbackHrEnablers> itr1 = HREnablers
									.iterator();
							feedbackPost = feedbackPostService.getFeedbackPost(post);
							while (itr1.hasNext()) {
								//log.info("FeedBack Action Test Inside While loop:::::");
								FeedbackHrEnablers enablers = (FeedbackHrEnablers) itr1.next();

								String enableRole=enablers.getRole();
								//log.info(":::FeedBack Action Role::<<<<enableRole>>>>:"+enableRole); 
								String enablerSubLocation=enablers.getSubLocation();
								String userSubLocation=feedbackPost.getUser().getLocation();
								if (enablers.getRole().equalsIgnoreCase("L1")
										&& enablers.getSubLocation().trim().equalsIgnoreCase(feedbackPost.getUser().getLocation())) {
									String L1enablerCpf = enablers.getCpfNo();
									User L1enablerUser = userService.getUserByCPFNumber(L1enablerCpf);
									enablerName = L1enablerUser.getEmpName();
								}
								if (enablers.getRole().equalsIgnoreCase("L11")
										&& enablers.getSubLocation().trim().equalsIgnoreCase(feedbackPost.getUser().getLocation())) {
									String L1enablerCpf = enablers.getCpfNo();
									assetManager = L1enablerCpf + "@ongc.co.in";
								}
							}
							message = "Dear Sir,<br /><br />The following Post [Id "
									+ post.getPostId()
									+ " dated "
									+ dateStr
									+ "]  has been posted on Feedback Forum of ONGCReports. <br /><br /><br/>"
									+ "<i>Subject: "
									+ post.getSubject()
									+ "<br />'"
									+ post.getMessage()
									+ "'<br /><br />Posted by: "
									+ postUser.getEmpName()
									+ "<br />CPF No.: "
									+ postUser.getCpfNo()
									+ "<br />"
									+ "Mobile No.: "
									+ postUser.getMobileNo()
									+ "</i><br /><br/><br/>The Post is pending with Local Enabler Mr/Ms "
									+ enablerName
									+ ", "
									+ postUser.getCurrentPlace()
									+ ".<br/><br /><br/>You are requested to kindly advise Mr/Ms "
									+ enablerName
									+ " to respond to the query immediately.<br /><br /><br /><br/>"
									+ "P.S. This is a system-generated mail from ONGCReports Team.  Please do not reply. <br /><br />";
							if (workDays > 3) {
								
								/* 
								 * commnet by Ranjeet
								 */
//								PassioSendMail sendData = new PassioSendMail();
//								feedbackPostService.sendPostToChief(post.getPostId());
							}
						} catch (Exception e) {
						}
					}

					if (post.getRvStatus().equalsIgnoreCase("CORPORATE")) {
						try {

							Date startDate = feedbackPostService.getPostForwardDate(post.getPostId());
							Date endDate = new Date();
							Calendar startCal = Calendar.getInstance();
							startCal.setTime(startDate);
							Calendar endCal = Calendar.getInstance();
							endCal.setTime(endDate);
							do {
								startCal.add(Calendar.DAY_OF_MONTH, 1);
								if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
										&& startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
									++workDays;
								}
							} while (startCal.getTimeInMillis() < endCal
									.getTimeInMillis()); // excluding end date

							Iterator<FeedbackHrEnablers> itr2 = HREnablers
									.iterator();
							feedbackPost = feedbackPostService.getFeedbackPost(post);
							while (itr2.hasNext()) {
								FeedbackHrEnablers enablers = (FeedbackHrEnablers) itr2
										.next();
								if (enablers.getRole().trim()
										.equalsIgnoreCase("L2")
										&& enablers.getHrCatId() == feedbackPost
										.getHrCatId()) {

									String L2enablerCpf = enablers.getCpfNo();
									User L2enablerUser = userService.getUserByCPFNumber(L2enablerCpf);
									enablerName = L2enablerUser.getEmpName();
								}
								if (enablers.getRole().trim().equalsIgnoreCase("L1")&& enablers.getSubLocation()
										.trim()
										.equalsIgnoreCase(
												feedbackPost
												.getUser()
												.getLocation()
												.trim())) {

									String localEnablerCpf = enablers
											.getCpfNo();
									User localEnablerUser = userService.getUserByCPFNumber(localEnablerCpf);
									localEnablerName = localEnablerUser.getEmpName();
								}
							}
							message = "Dear Sir,<br /><br />The following Post [Id "
									+ post.getPostId()
									+ " dated "
									+ dateStr
									+ "] has been posted on Feedback Forum of ONGCReports.<br/><br /><br />"
									+ "<i>Subject: "
									+ post.getSubject()
									+ "<br />'"
									+ post.getMessage()
									+ "'<br /><br />Posted by: "
									+ postUser.getEmpName()
									+ "<br />CPF No.: "
									+ postUser.getCpfNo()
									+ "<br />"
									+ "Mobile No.: "
									+ postUser.getMobileNo()
									+ "</i><br /><br/> <br/><br/>The Post has been forwarded by Mr/Ms "
									+ localEnablerName
									+ ", "
									+ postUser.getCurrentPlace()
									+ " and is pending with Corporate Enabler Mr/Ms "
									+ enablerName
									+ ".<br /><br /><br/>You are requested to kindly advise Mr/Ms "
									+ enablerName
									+ " to respond to the query immediately.<br /><br /><br /><br/>"
									+ "P.S. This is a system-generated mail from ONGCReports Team.  Please do not reply. <br /><br />";
							if (workDays > 4) {
//								PassioSendMail sendData = new PassioSendMail();
//								feedbackPostService.sendPostToChief(post.getPostId());
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}	

	public void feedbackPostCommentForm(ActionRequest actionRequest, ActionResponse actionResponse ) {
		int postId = ParamUtil.getInteger(actionRequest, "postId");
		String fcomment = ParamUtil.getString(actionRequest, "comment");
//		String captcha= ParamUtil.getString(actionRequest, "captcha");
		User user = userService.getUser();
		//String captcha = (String) getSessionAttribute("CAPTCHA_KEY");

		FeedbackPost post = new FeedbackPost();
		FeedbackComment comment = new FeedbackComment();
		post.setPostId(postId);
//		if (captcha.equals("123")) {
			comment.setPostId(post.getPostId());
			comment.setUser(user);
			comment.setCommentText(fcomment);
			if (feedbackCommentService.saveFeedbackComment(comment)) {
				feedbackPostService.updateNoOfComment(post.getPostId(), true);
				boolean flag1 = false;
				flag1 = feedbackPostService.check_cpf(user.getCpfNo());
				if (flag1) {

					if (comment.isAuthResp()) {
						feedbackPostService.saveUser(user.getCpfNo(), "auth_response");

					} else {
						feedbackPostService.saveUser(user.getCpfNo(), "feedback_comment");
					}
				} else {
					if (comment.isAuthResp()) {
						feedbackPostService.updateUser(user.getCpfNo(), "auth_response");
					} else {
						feedbackPostService.updateUser(user.getCpfNo(), "feedback_comment");
					}
				}

		} 	
	}
}
