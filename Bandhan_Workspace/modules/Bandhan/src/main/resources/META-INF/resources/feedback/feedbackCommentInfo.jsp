<%@page import="com.ongc.liferay.bandhan.model.FeedbackReason"%>
<%@page import="com.ongc.liferay.bandhan.model.FeedbackHrCategory"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.ongc.liferay.bandhan.model.User"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.ongc.liferay.bandhan.model.FeedbackHrEnablers"%>
<%@page import="com.ongc.liferay.bandhan.model.FeedbackComment"%>
<%@page import="java.util.List"%>
<%@page import="com.ongc.liferay.bandhan.model.FeedbackPost"%>
<%@ include file="/init.jsp"%>
<%
	FeedbackPost post = (FeedbackPost) request.getAttribute("post");
	List<FeedbackHrEnablers> enablersList = (List<FeedbackHrEnablers>) request.getAttribute("HREnablersList");
	User users = (User) request.getAttribute("user");
	//out.println(post);
	List<FeedbackComment> feedbackComment = (List<FeedbackComment>) request.getAttribute("commentList");
	//out.println(feedbackComment.size());
	List<FeedbackReason> reasonList = (List<FeedbackReason>) request.getAttribute("reasonList");
	//out.println(feedbackComment);
	String backURL = (String) request.getAttribute("backURL");
	int srn = 1;
%>
<portlet:renderURL var="getEmployeeData"
	windowState="<%=LiferayWindowState.POP_UP.toString()%>">
	<portlet:param name="mvcRenderCommandName" value="getEmployeeInfo" />
</portlet:renderURL>
<portlet:renderURL var="feedbackCommentForm">
	<portlet:param name="mvcPath" value="/feedback/feedbackCommentForm.jsp" />
	<portlet:param name="postId"
		value="<%=Integer.toString(post.getPostId())%>" />
</portlet:renderURL>
<div class="row">
	<div class="col-md-12">
		<table class="table table-bordered table-striped">
			<tr>
				<td><a title="Click to view full profile"
					onclick="popup(<%=post.getUser().getCpfNo()%>)"> <%=post.getUser().getUserName()%></a>,
					<%=post.getUser().getDesignation()%>, <%=post.getUser().getLocation()%>
					<%
						Iterator it1 = enablersList.iterator();
						while (it1.hasNext()) {
							FeedbackHrEnablers enablers = (FeedbackHrEnablers) it1.next();
							if ((enablers.getCpfNo().equalsIgnoreCase(users.getCpfNo()))) {
								if (("L1".equalsIgnoreCase(enablers.getRole().trim()))
										|| users.getCpfNo().equalsIgnoreCase("78619")) {
					%> <%
 	if (post.getOpStatus().equals("OPEN")) {
 %> <input
					type="submit" value="Forward to Corporate" id="button"
					name="button" class="btn btn-primary pull-right"
					data-toggle="modal" data-target="#myModal" /> <input
					data-toggle="modal" data-target="#myModalC" type="submit"
					value="Comment by Local Enabler" id="button" name="button"
					class="btn mr-4 btn-primary pull-right" /> <%
 	} else if ((enablers.getRole().trim().equalsIgnoreCase("L2"))
 						|| users.getCpfNo().equalsIgnoreCase("78619")) {
 %> <%
 	if (post.getOpStatus().equals("OPEN") /* && post.getRvStatus().equals("CORPORATE") */) {
 %> <input type="submit" value="Comment by Corporate Enabler"
					id="button" name="button" class="btn btn-primary pull-right"
					data-toggle="modal" data-target="#myModalCp" /> <%
 	}
 				}
 			}
 		}
 	}
 %>
			</tr>
			<tr>
				<td><%=post.getPostDateText()%></td>
			</tr>
			<tr>
				<td><b>Subject: <%=post.getSubject()%></b></td>
			</tr>
			<tr>
				<td><p><%=post.getMessage()%></p></td>
			</tr>
			<s:if test="%{reasonList.isEmpty()}"></s:if>
			<s:else>
				<tr>
					<td><s:iterator value="reasonList">
							<%
								for (FeedbackReason reason : reasonList) {
							%>
							<table class="table table-bordered table-striped mb0">
								<tbody>
									<tr>
										<td><a title="Click to view full profile"
											onclick="popup(<%=users.getCpfNo()%>);"><%=users.getEmpName()%></a>,
											<%=users.getDesignation()%>,<%=users.getLocation()%> <span
											class="alert alert-danger pt5 pl5 pr5 pb5 pull-right mb0">
												<%
													if (reason.getStatus() == "CLOSE") {
												%>Closed<%
													}
												%> <%
 	if (reason.getStatus() == "CORPORATE") {
 %>Forwarded
												to Corporate Enabler<%
 	}
 %>
										</span></td>
									</tr>
									<tr>
										<td><%=reason.getReasonOn()%></td>
									</tr>
									<tr>
										<td><p class="locEnabColor"><%=reason.getReason()%></p></td>
									</tr>
								</tbody>
							</table>
							<%
								}
							%>
						</s:iterator></td>
				</tr>
			</s:else>
			<%
				for (FeedbackComment comment : feedbackComment) {
			%>
			<tr>
				<td>
					<table class="table table-bordered table-striped mb0">
						<tr>
							<td><a title="Click to view full profile"
								onclick="popup(<%=comment.getUser().getCpfNo()%>);"> <%=comment.getUser().getEmpName()%></a>,
								<%=comment.getUser().getDesignation()%>, <%=comment.getUser().getLocation()%>
							</td>
						</tr>
						<tr>
							<td><%=comment.getPostTextDate()%></td>
						</tr>
						<tr>
							<td>
								<%
									if (comment.isAuthResp()) {
								%> <img title="" width="30"
								height="30"> <br>Response<br> <br /> <%
 	} else {
 %>
								<b><%=srn++%>. Comment:</b>
								<%
									}
								%> <%
 	if (comment.isAuthResp()) {
 %> <span><%=comment.getCommentText()%></span>
								<%
									} else {
								%> <%=comment.getCommentText()%> <%
 	}
 %>
							</td>
						</tr>

					</table>
				</td>

			</tr>
			<%
				}
			%>
		</table>
	</div>
	<div class="col-lg-12 text-center" >
		<a href="<%=feedbackCommentForm%>" class="btn btn-primary">Give
			Your Comments</a> <a href="<%=backURL%>" class="btn btn-primary">Back</a>
	</div>
</div>
<script>
	 function popup(cpfNo){
		 var url="<%=getEmployeeData%>&<portlet:namespace />cpfNo="+cpfNo;
		Liferay.Util.openWindow({
		    dialog: {
		        centered: true,
		        height: 600,
		        modal: true,
		        width: 800,
		        style:"background-color: #8c000d;color: #fff !important;",
		        cssClass: "ui-model",
		        destroyOnHide: true,
	            resizable: false,
		    },
		    id: "<portlet:namespace />popUpId",
		    title: "Employee Details",
		    uri: url
		}); 
		  Liferay.provide(window, '<portlet:namespace />closePopup', function(popUpId , id) {
	         Liferay.Util.Window.getById(popUpId).destroy();
	         location.reload();
	     },
	     ['liferay-util-window']
	     );  
	}
	</script>