<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.ongc.liferay.bandhan.model.FeedbackComment"%>
<%@page import="com.ongc.liferay.bandhan.model.FeedbackPost"%>
<%@page import="java.util.List"%>
<%@ include file="/init.jsp" %>




    
<%
List<FeedbackPost> postList = (List<FeedbackPost>) request.getAttribute("postList");
//int noOfRecords = (Integer) request.getAttribute("countRow");
List<FeedbackComment> feedbackComment = (List<FeedbackComment>) request.getAttribute("commentList");
//List<FeedbackReason> reasonList = (List<FeedbackReason>) request.getAttribute("reasonList");
//List<FeedbackHrEnablers> HREnablersList = (List<FeedbackHrEnablers>) request.getAttribute("commentList");
//List<FeedbackHrCategory> hrCategoryList = (List<FeedbackHrCategory>) request.getAttribute("commentList");
%>    
<portlet:renderURL var="myPostForm" > 
	<portlet:param name="mvcRenderCommandName" value="getMyPosts"/>
</portlet:renderURL>
   <portlet:renderURL var="searchFeedback" > 
	<portlet:param name="mvcRenderCommandName" value="searchFeedbackPost"/>
</portlet:renderURL>
   
 <portlet:renderURL var="feedbackPostForm" > 
 	<portlet:param name="mvcPath" value="/feedback/feedbackPostForm.jsp"/>
 </portlet:renderURL>
 
 <portlet:renderURL var="getEmployeeData" windowState="<%=LiferayWindowState.POP_UP.toString()%>">
	<portlet:param name="mvcRenderCommandName" value="getEmployeeInfo"/>
</portlet:renderURL>

 <portlet:renderURL var="getCommentData" >
	<portlet:param name="mvcRenderCommandName" value="getCommentInfo"/>
	<portlet:param name="backURL" value="<%= themeDisplay.getURLCurrent() %>"></portlet:param>
</portlet:renderURL>

	<div>
		<div class="row">
			<div class="col-md-12">
				<div class="formWrapper">
                <h3>Feedback</h3>

				
			<aui:form action="<%= searchFeedback %>" method="post" name="myForm">
	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset >
			<aui:row>
				<aui:col width="18" style="padding-right: 5px;padding-left: 5px;">
					<aui:input label="Start Date" name="startDate" type="date" onchange="checkParameter();checkStartDate();"/>
				</aui:col>
				<aui:col width="18" style="padding-right: 5px;padding-left: 5px;">
					<aui:input label="End Date" name="endDate" type="date" onchange="checkParameter();checkEndDate();"/>
				</aui:col>
				<aui:col width="18" style="padding-right: 5px;padding-left: 5px;">
						<aui:select label="Category" name="category" onchange="checkParameter()">
					 		<aui:option value="">Select</aui:option>
							<aui:option value="1">Online Medical Claims</aui:option>
							<aui:option value="2">HR Claims</aui:option>
							<aui:option value="3">Trust</aui:option>
    						<aui:option value="4">Others</aui:option>
						</aui:select>
				</aui:col>
				<aui:col width="18" style="padding-right: 5px;padding-left: 5px;">
					<aui:input label="Post ID" name="postID" onkeyup="checkParameter()" type="text" />
				</aui:col>
				<aui:col width="18" style="padding-right: 5px;padding-left: 5px;">
					<aui:input label="Keyword" name="keyword" onkeyup="checkParameter()" type="text" />
				</aui:col>
				<aui:button-row style="margin-top:2.7%;">
				<aui:button name="submitButton" type="submit" value="Search" cssClass="btn btn-primary" />
				<aui:button name="resetButton" type="cancel" onclick="this.form.reset();checkParameter()" value="Reset" cssClass="btn btn-primary" />
			</aui:button-row>
				</aui:row>
			</aui:fieldset>
			</aui:fieldset-group>
			</aui:form>
		</div>
		</div>
	</div>
		<div class="row">
			<div class="col-md-12">
			<a href="#agreeBox" class="btn btn-primary" data-toggle="modal" >Post Message</a>
			<a class="btn btn-primary" href="<%= myPostForm %>" >My Posts</a>
			</div>
		</div>
		<div class="row mt-4">
			<div class="col-md-12">
				<div class="table-responsive">
		   <table id= "viewFeedback" class="table table-striped table-bordered" >
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
			        <% int i=0;
		            int workDays=0;
			      for(FeedbackPost post:postList){ i++;
			       SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		            Date date1 = sdf1.parse(post.getPostDate().toString());
		            Date date2 = sdf1.parse("2015-10-14"); %>
			       <tr>
			       <td><%= i %></td>
			       <td><%= post.getPostId() %></td>
			       <td><a onclick="commentDetails(<%= post.getPostId() %>)"><span title=''><%= post.getSubject() %></span></a></td>
			       <td><%= post.getCategory().getDescription() %></td>
			       <td><a onclick="popup('<%= post.getUser().getCpfNo() %>')"><%=post.getUser().getUserName()%></a> on <%=new SimpleDateFormat("dd-MM-yyyy").format( post.getPostDate()) %></td>
			       <td><a onclick="popup('<%= post.getUser().getCpfNo() %>')"><%= post.getLastUpdName() %></a> on <%=new SimpleDateFormat("dd-MM-yyyy").format(post.getSrt_date())%></td>
			         <td><% if(post.getCategoryId()==42 || post.getCategoryId()==62 || post.getCategoryId()==63 || post.getCategoryId()==64 || post.getCategoryId()==65 || post.getCategoryId()==66 || post.getCategoryId()==67 || post.getCategoryId()==68 || post.getCategoryId()==61) {
								if(post.getOpStatus()!=null && post.getOpStatus().equalsIgnoreCase("OPEN") && post.getRvStatus()!=null && post.getRvStatus().equalsIgnoreCase("LOCAL") && post.getChiefEr()!=null && post.getChiefEr().equalsIgnoreCase("NO") && date1.after(date2))
								{%>
								  <img src='<%=request.getContextPath()%>/images/green-enabler.png' style='padding-left: 13px;'/>
								<% }
								if(post.getOpStatus()!=null && post.getOpStatus().equalsIgnoreCase("OPEN") && post.getRvStatus()!=null && post.getRvStatus().equalsIgnoreCase("LOCAL") && post.getChiefEr()!=null && post.getChiefEr().equalsIgnoreCase("YES") && date1.after(date2))
								{%>
								  <img src='<%=request.getContextPath()%>/images/yellow-henabler.png' style='padding-left: 13px;'/>
								<%}								
								if(post.getOpStatus()!=null && post.getOpStatus().equalsIgnoreCase("CLOSE") && post.getRvStatus()!=null && post.getRvStatus().equalsIgnoreCase("LOCAL") && date1.after(date2) )
								{%>
								  <img src='<%=request.getContextPath()%>/images/red-henabler.png' style='padding-left: 13px;'/>
								<%}							
								if(post.getOpStatus()!=null && post.getOpStatus().equalsIgnoreCase("OPEN") && post.getRvStatus()!=null && post.getRvStatus().equalsIgnoreCase("CORPORATE") && workDays<3 && date1.after(date2) ){
									%>
									  <img src='<%=request.getContextPath()%>/images/blue-henabler.png' style='padding-left: 13px;'/>
									<%}							
								if(post.getOpStatus()!=null && post.getOpStatus().equalsIgnoreCase("OPEN") && post.getRvStatus()!=null && post.getRvStatus().equalsIgnoreCase("CORPORATE")&& workDays>3 && date1.after(date2)){
									%>
									  <img src='<%=request.getContextPath()%>/images/yellow-henabler.png' style='padding-left: 13px;'/>
									<%}
																
								if(post.getOpStatus()!=null && post.getOpStatus().equalsIgnoreCase("CLOSE") && post.getRvStatus()!=null && post.getRvStatus().equalsIgnoreCase("CORPORATE") && date1.after(date2)){
									%>
									  <img src='<%=request.getContextPath()%>/images/red-henabler.png' style='padding-left: 13px;'/>
									<%}
								}
								else if(!post.getAuth_status().equals("0")){
									%>
									  <img src='<%=request.getContextPath()%>/images/comment.png' alt="response" style='padding-left: 13px;'/>
									<%}%>
					</td>  
			       <td><%= post.getNoOfReplies() %></td>
			       </tr>
			       <% } %>
			        </tbody>
			 </table>    
			</div>
		</div> 
		</div> 
	</div>
<div class="modal" id="agreeBox">
		<div class="agreeBlock modal-dialog">
			<div class="modal-content">
			<div class="modal-header">
          <a href="javascript:void(0)" class="close" id="closebox" data-dismiss="modal">&times;</a>
          <h4 class="modal-title">Your feedback is valuable to us. We will make sure to
				address your queries and issues to your satisfaction.</h4>
        </div>
		 <div class="modal-body">
			<ol>
				<li>We request you to please go through the previous feedback
					topics to check if similar query has already been posted and
					answered.</li>
				<li>Please post your Issue in a clear manner along with
					relevant details, which shall help us in responding faster.</li>
				<li>Please follow netiquette guidelines while posting your
					feedback. In case netiquette guidelines are violated, the post
					shall be closed and archived/ deleted. Management reserves the
					right to take suitable action in case of repeated violations by an
					individual. Please read the netiquette under interface in ONGC
					Reports.</li>
			</ol>
			<div class="btn-box text-center">
				<a id="closebox" tile="Disagree" data-dismiss="modal" class="btn btn-primary">I Disagree</a>&nbsp;&nbsp;
				<a onclick="showfeedbackForm();" id="iagree" title="Agree" class="btn btn-primary">I
					Agree</a>
			</div>
</div>
			</div>
		</div>
	</div>
	<script>
	
	 $("#<portlet:namespace />resetButton").click(function () {
	    	$("#<portlet:namespace/>category").val('Select');
	    	$("#<portlet:namespace/>startDate").val('');
	    	$("#<portlet:namespace/>endDate").val('');
	    	$("#<portlet:namespace/>postID").val('');
	    	$("#<portlet:namespace/>keyword").val('');
	       
	    });
	 
	 function popup(cpfNo){
		 var url="<%=getEmployeeData %>&<portlet:namespace />cpfNo="+cpfNo;
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
	 
	 function commentDetails(postId){
		 window.location.href="<%= getCommentData %>&<portlet:namespace />postId="+postId;
	 } 
	 
	 $(document).ready(function() {
		 var table = $('#viewFeedback').DataTable( {
			 lengthChange: false,bFilter: false, bInfo: false
		    });
		}); 
		 
	 function showfeedbackForm(){
				$("#agreeBox").modal('hide');
				window.location.href='<%= feedbackPostForm %>';
			}
	 
	 function checkStartDate(){
		 var today = new Date().toISOString().split('T')[0];
		 var  startDate=document.getElementById('<portlet:namespace/>lastTenFrm').value;
		 var divAlert = document.getElementById('<portlet:namespace/>alert');
		 if(startDate > today){
			 $('#alert').empty();
			 $('#alert').append('Last Assignment From must not be greater than today\'s date');
			 $('#alert').css("display", "block");
			 document.getElementById("<portlet:namespace/>submitButton").disabled = true;			 
		 }
		 else{
			$('#alert').css("display", "none");
		 	document.getElementById("<portlet:namespace/>submitButton").disabled = false;}
		
	}
	function checkEndDate(){
		 var today = new Date().toISOString().split('T')[0];
		 var  endDate=document.getElementById('<portlet:namespace/>lastTenTo').value;
		 var  startDate=document.getElementById('<portlet:namespace/>lastTenFrm').value;
		 var divAlert = document.getElementById('<portlet:namespace/>alert');
		 if(endDate > today & endDate > startDate ){
			 $('#alert').empty();
			 $('#alert').append('Last Assignment to date must not be greater than today\'s date and Last Assignment From Date');
			 $('#alert').css("display", "block");
			 document.getElementById("<portlet:namespace/>submitButton").disabled = true;			 
		 }
		 else if(endDate == startDate & endDate > today){
			 $('#alert').empty();
			 $('#alert').append('Last Assignment to Date must not be greater than today\'s date and Last Assignment From Date');
			 $('#alert').css("display", "block");
			 document.getElementById("<portlet:namespace/>submitButton").disabled = true;			 
		 }
		 else if(endDate < startDate){
			 $('#alert').empty();
			 $('#alert').append('Last Assignment to Date must not be less than Start Date');
			 $('#alert').css("display", "block");
			 document.getElementById("<portlet:namespace/>submitButton").disabled = true;			 
		 }
		 else{
			$('#alert').css("display", "none");
		 	document.getElementById("<portlet:namespace/>submitButton").disabled = false;}
		
	}
	
	
	
	
	$(document).ready(function() {
		 checkParameter();
	});
	function checkParameter(){
		 if(SearchCheck()) {
		 }
		    else {
		    	document.getElementById("<portlet:namespace/>submitButton").disabled = true;
		    }
	}
	function SearchCheck() {
		   var flag = false;
		  	var  keyword=document.getElementById('<portlet:namespace/>keyword').value;
		  	var  postID=document.getElementById('<portlet:namespace/>postID').value;
		  	var  category=document.getElementById('<portlet:namespace/>category').value;
		  	var  endDate=document.getElementById('<portlet:namespace/>endDate').value;
		  	var  startDate=document.getElementById('<portlet:namespace/>startDate').value;
		  	var  category=document.getElementById('<portlet:namespace/>category').value;
		       if(keyword != "" || postID != "" || category != "" || endDate != "" || startDate != ""|| category != "") {
		    	   document.getElementById("<portlet:namespace/>submitButton").disabled = false;
		       	flag = true;
		          return flag;
		       }
		  
		   return flag;
		}
	
	
	</script>