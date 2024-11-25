<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ongc.liferay.bandhan.service.Impl.UserServiceImpl"%>
<%@page import="com.ongc.liferay.bandhan.service.UserService"%>
<%@page import="com.ongc.liferay.bandhan.model.User"%>
<%@ include file="/init.jsp"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<portlet:defineObjects />
<portlet:actionURL var="updateuser" name="update_bandhan_profile">
</portlet:actionURL>
<portlet:actionURL var="updateuserpic" name="update_bandhan_profile_pic">
</portlet:actionURL>
<%
	UserService userService = new UserServiceImpl();
	User userData = userService.getUser();
	if(userData!=null){
%>
<div class="row">

	<div class="col-md-12">
		<h1>Profile</h1>
	</div>
</div>


<div class="bg-grey ongc-academy formWrapper">
	<h3>My Profile</h3>
	
	
	<aui:form action="${updateuserpic}" name="fm" method="post" enctype="multipart/form-data">
			<aui:fieldset-group markupView="lexicon">
				<aui:row>
					<aui:col width="50">
					<img class="rounded img-thumbnail" style="max-width:63px;"
			src="<%= themeDisplay.getURLPortal() %>/o/blade/bandhanServlet/bandhanImageServlet?cpfno=<%=userData.getCpfNo()%>">
						<aui:input label="Profile Pic :" name="userPic" type="file" onChange="form.submit()">
						<aui:input  name="cpfNo" type="hidden" value="<%=userData.getCpfNo()%>" />
						<aui:validator name="acceptFiles">'jpg'</aui:validator>
						</aui:input>
					</aui:col>
					</aui:row>
					</aui:fieldset-group>
					</aui:form>
					
	
	
	
	<aui:form action="<%=updateuser%>" method="post" name="myForm">
		<aui:fieldset-group markupView="lexicon">
			<aui:row>
				<aui:col width="33"> 
					<aui:input label="CPF Number Demo" name="cpfNo" type="text" readonly="true"
						value="<%=userData.getCpfNo()%>" />
				</aui:col>
				<aui:col width="33">
					<aui:input label="Name of Employee" name="userName" type="text" readonly="true"
						value="<%=userData.getUserName()%>" />
				</aui:col>
				<aui:col width="33">  
					<aui:input label="Past Designation" name="designation" type="text" readonly="true"
						value="<%=userData.getDesignation()%>" />
				</aui:col>
			</aui:row>
			<aui:row>
				<aui:col width="33">
					
					<aui:input label="Date of Superannuation" name="dateOfSep" readonly="true"
						type="text" value="<%=new SimpleDateFormat("dd-MM-yyyy").format(userData.getDateOfSep())%>" />
				</aui:col>
				<aui:col width="33">
					<aui:input label="Date of Birth " name="dob" type="text" readonly="true"
						value="<%=new SimpleDateFormat("dd-MM-yyyy").format(userData.getDateOfBirth())%>" />
				</aui:col>
				<aui:col width="33">
					<aui:input label="Date of Joining" name="dateOfJoining" type="text" readonly="true"
						value="<%=new SimpleDateFormat("dd-MM-yyyy").format(userData.getDateOfJoining())%>" />
				</aui:col>
			</aui:row>
			<aui:row>
				<aui:col width="33">
					<aui:input label="Mobile No." name="mobileNo" type="text"
						value="<%=userData.getMobileNo()%>" />
				</aui:col>
				<aui:col width="33">
					<aui:input label="Land Line No." name="landLineNo" type="text" readonly="true"
						value="<%=userData.getLandLineNo()%>" />
				</aui:col>
				<aui:col width="33">
					<aui:input label="Location" name="location" type="text" readonly="true"
						value="<%=userData.getLocation()%>" />
				</aui:col>
			</aui:row>
			<aui:row>
				<aui:col width="33">
					<aui:input label="PAN No." name="panNumber" type="text"
						value="<%=userData.getPanNumber()%>" />
				</aui:col>
				<aui:col width="33">
					<aui:input label="Email-ID" name="emailId" type="text"
						value="<%=userData.getEmailId()%>" />
				</aui:col>
				<aui:col width="33">
					<aui:input label="Settlement Address" name="settlementAddress" readonly="true"
						type="text" />
				</aui:col>
			</aui:row>
			<aui:row>
				<aui:col width="33">
					<aui:input label="Communication Address	"
						name="communicatedAddress" type="textarea"
						value="<%=userData.getCommunicatedAddress()%>" />
				</aui:col>
				<aui:col width="33">
					<aui:input label="About Me" name="aboutMe" type="textarea"
						value="<%=userData.getAboutMe()%>" />
				</aui:col>

			</aui:row>
			<aui:button-row cssClass="text-center">

				<aui:button cssClass="btn btn-primary" type="submit" value="Modify" />

				

			</aui:button-row>
		</aui:fieldset-group>


	</aui:form>

</div>
<p>
	<span class="text-danger">Note:</span> To update profile (email, mobile
	no. etc) go to webice portal/retiree through Online claim Tab on
	Bandhan Homepage.
</p>
<p dir="ltr">&nbsp;</p>
<%}else{ %>
<div id="nodata">No Data Available</div>
<%} %>
<script>
	
function isNumber(evt) {
  evt = (evt) ? evt : window.event;
  var charCode = (evt.which) ? evt.which : evt.keyCode;
  if (charCode > 31 && (charCode < 48 || charCode > 57)) {
    alert("Please Enter Only Numbers.");
    return false;
  }

  return true;
}
	
	
	function showFunction() {
       $(".prf-file").show();
     }
	 function hideFunction() {
       $(".prf-file").hide();
     }
		 $(document).ready(function() {
		 
		 $(".prf-file").hide();
		 $(".edit-pic1 img").click(function(){
		 
		 $(".prf-file").show();
		 });
		 $("#prf-back").click(function(){
		 
		  $(".prf-file").hide();
		  });
		});
	</script>