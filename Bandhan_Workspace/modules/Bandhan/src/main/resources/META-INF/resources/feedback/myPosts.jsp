<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.ongc.liferay.bandhan.model.FeedbackPost"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ include file="/init.jsp" %>


<% 
List<FeedbackPost> mypostList =	(List<FeedbackPost>) request.getAttribute("mypostList");
%>
 <portlet:renderURL var="feedbackPosts" ></portlet:renderURL>
 <portlet:renderURL var="getEmployeeData" windowState="<%=LiferayWindowState.POP_UP.toString()%>">
	<portlet:param name="mvcRenderCommandName" value="getEmployeeInfo"/>
</portlet:renderURL>
 <portlet:renderURL var="getCommentData" >
	<portlet:param name="mvcRenderCommandName" value="getCommentInfo"/>
	<portlet:param name="backURL" value="<%= themeDisplay.getURLCurrent() %>"></portlet:param>
</portlet:renderURL>
<div class="container">
	<div class="row">
			<!-- <div class="col-lg-12" align="center"><a title="'Enablers' for responding to Feedback queries" target="_blank" aria-label="The restructured system for Enablers to respond to Feedback queries. Opens in new window">
				<strong>The restructured system for Enablers to respond to Feedback queries.</strong></a></div>
			<div class="col-lg-12"> -->
				<div class="col-lg-6" style="display:inline;float: left;"><h2 class="h2 heading-clr mt0 col-xs-6" role="heading">Feedback - My Posts</h2></div>
				<div class="col-lg-6" align="right" style="display:inline;float: right;"><a href="<%= feedbackPosts %>" class="btn btn-primary">View All Post</a></div>
			</div>
			<div class="col-lg">
			<table id= "viewFeedback" class="table table-striped table-bordered">
			       <thead>
			            <tr>
			                <th>SR</th>
			                <th>Post ID	</th>
			                <th>Subject</th>
			                <th>Category</th>
			                <th>Created by</th>
			                <th>Last Comment by</th>
			                <th>Auth Response</th>
			                <th>No. of Replies</th>
			            </tr>
			        </thead>
			        <tbody>
			      <% 
			      int i=0;
			      int workDays=0;
			      for(FeedbackPost myposts:mypostList){ i++;
			       SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		            Date date1 = sdf1.parse(myposts.getPostDate().toString());
		            Date date2 = sdf1.parse("2015-10-14");%>
			      <tr>
			       <td><%= i %></td>
			       <td><%= myposts.getPostId() %></td>
			       <td><a onclick="commentDetails(<%= myposts.getPostId() %>)"><%= myposts.getSubject() %></a></td>
			       <td><%= myposts.getCategory().getDescription() %></td>
			       <td><a onclick="popup('<%= myposts.getUser().getCpfNo() %>')"><%=myposts.getUser().getEmpName()%></a> on <%= myposts.getPstdate() %></td>
			       <td><% if(myposts.getCmmntdate() == null && myposts.getCmmntdate() == "") {}else{%>
			       <a onclick="popup('<%= myposts.getUser().getCpfNo() %>')"><%= myposts.getLastUpdName() %></a> on <%= myposts.getSrt_date() %>
			       <%} %></td>
			        <td><% if(myposts.getCategoryId()==42 || myposts.getCategoryId()==62 || myposts.getCategoryId()==63 || myposts.getCategoryId()==64 || myposts.getCategoryId()==65 || myposts.getCategoryId()==66 || myposts.getCategoryId()==67 || myposts.getCategoryId()==68 || myposts.getCategoryId()==61) {
								if(myposts.getOpStatus()!=null && myposts.getOpStatus().equalsIgnoreCase("OPEN") && myposts.getRvStatus()!=null && myposts.getRvStatus().equalsIgnoreCase("LOCAL") && myposts.getChiefEr()!=null && myposts.getChiefEr().equalsIgnoreCase("NO") && date1.after(date2))
								{%>
								  <img src='<%=request.getContextPath()%>/images/green-enabler.png' style='padding-left: 13px;'/>
								<% }
								if(myposts.getOpStatus()!=null && myposts.getOpStatus().equalsIgnoreCase("OPEN") && myposts.getRvStatus()!=null && myposts.getRvStatus().equalsIgnoreCase("LOCAL") && myposts.getChiefEr()!=null && myposts.getChiefEr().equalsIgnoreCase("YES") && date1.after(date2))
								{%>
								  <img src='<%=request.getContextPath()%>/images/yellow-henabler.png' style='padding-left: 13px;'/>
								<%}								
								if(myposts.getOpStatus()!=null && myposts.getOpStatus().equalsIgnoreCase("CLOSE") && myposts.getRvStatus()!=null && myposts.getRvStatus().equalsIgnoreCase("LOCAL") && date1.after(date2) )
								{%>
								  <img src='<%=request.getContextPath()%>/images/red-henabler.png' style='padding-left: 13px;'/>
								<%}							
								if(myposts.getOpStatus()!=null && myposts.getOpStatus().equalsIgnoreCase("OPEN") && myposts.getRvStatus()!=null && myposts.getRvStatus().equalsIgnoreCase("CORPORATE") && workDays<3 && date1.after(date2) ){
									%>
									  <img src='<%=request.getContextPath()%>/images/blue-henabler.png' style='padding-left: 13px;'/>
									<%}							
								if(myposts.getOpStatus()!=null && myposts.getOpStatus().equalsIgnoreCase("OPEN") && myposts.getRvStatus()!=null && myposts.getRvStatus().equalsIgnoreCase("CORPORATE")&& workDays>3 && date1.after(date2)){
									%>
									  <img src='<%=request.getContextPath()%>/images/yellow-henabler.png' style='padding-left: 13px;'/>
									<%}
																
								if(myposts.getOpStatus()!=null && myposts.getOpStatus().equalsIgnoreCase("CLOSE") && myposts.getRvStatus()!=null && myposts.getRvStatus().equalsIgnoreCase("CORPORATE") && date1.after(date2)){
									%>
									  <img src='<%=request.getContextPath()%>/images/red-henabler.png' style='padding-left: 13px;'/>
									<%}
								}
								else if(!myposts.getAuth_status().equals("0")){
									%>
									  <img src='<%=request.getContextPath()%>/images/comment.png' style='padding-left: 13px;'/>
									<%}%>
					</td>  
			       <td><%= myposts.getNoOfReplies() %></td>
			       </tr>
			       <% } %>
			        </tbody>
			 </table>
		</div> 
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
$(document).ready(function() {
    $('#viewFeedback').DataTable({searching: false,bLengthChange: false, info: false});
} ); 
function commentDetails(postId){
	 window.location.href="<%= getCommentData %>&<portlet:namespace />postId="+postId;
} 
</script>