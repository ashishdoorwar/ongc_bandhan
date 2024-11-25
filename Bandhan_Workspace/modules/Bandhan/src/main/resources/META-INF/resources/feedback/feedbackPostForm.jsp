<%@page import="com.ongc.liferay.bandhan.model.User"%>
<%@page import="com.ongc.liferay.bandhan.service.Impl.UserServiceImpl"%>
<%@page import="com.ongc.liferay.bandhan.service.UserService"%>
<%@page import="com.liferay.portal.kernel.theme.ThemeDisplay"%>
<%@ include file="/init.jsp" %>

<portlet:defineObjects />
<% 
String backURL = (String) request.getAttribute("backURL");
UserService userService = new UserServiceImpl();
User employee= userService.getUser();
%>
 <div class="container">
 <div class="row">
 <div class="col-lg-12">
 <portlet:actionURL var="postFeedback" name="feedbackPostForm" ></portlet:actionURL>
  <aui:form action="<%= postFeedback %>" method="post" name="myForm">
	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset label="Message">
			<aui:row>
				<aui:col width="50">
					<aui:input label="CPF No." name="cpfNo" type="hidden" value="<%= employee.getCpfNo() %>"/>
					<aui:input label="Subject" name="subject" type="text" placeholder="Enter Your Subject (Maximum 100 Characters)">
					   <aui:validator name="maxLength">100</aui:validator>
					   <aui:validator name="required" />
					</aui:input>
				</aui:col>
				<aui:col width="50">
					<aui:select label="Category" name="category" required="true">
					  <aui:option value="">Select</aui:option>
							<aui:option value="45">Online Medical Claims</aui:option>
							<aui:option value="51">HR Claims</aui:option>
    						<aui:option value="52">Others</aui:option>	
					</aui:select>
				</aui:col>
			</aui:row>
			<aui:row>
				<aui:col width="100">
					<label for="cke_<portlet:namespace/>content">Description</label>
				</aui:col>
				<aui:col width="100">
					<liferay-ui:input-editor name="content" initMethod="initEditor" width="100" height="400" resizable="true" toolbarSet="liferay-article">
					<aui:validator name="maxLength">500</aui:validator>
					<aui:validator name="required" />
					</liferay-ui:input-editor>
				</aui:col>
			</aui:row>
			<aui:row>
			<!-- <aui:col width="50">
					<img src="" alt="Captcha"/>
				</aui:col>
				<aui:col width="50">
					<aui:input label="Captcha" name="captcha" type="text" ><aui:validator name="required" /></aui:input>
				</aui:col>
			</aui:row> -->
			<aui:button-row cssClass="text-center">
				<aui:button name="submitButton" type="submit" value="Submit" cssClass="btn btn-primary" />
				<a onclick="javascript:history.go(-1)" class="btn btn-primary">Back</a>
			</aui:button-row>
		</aui:fieldset>
		<hr>
		<aui:row>
	<strong>Note</strong>
		<ul class="red_note">       
		  <li>Please use above text editor to write your comments.</li>
	      <li>Copying /pasting text directly from word doc file should be avoided as that may result in exceeding permissible character length (due to hidden tags).  </li>
	      <li>Instead notepad can be used for this purpose. Or copy word document to notepad first before pasting to above text editor </li>
      	</ul>	
    </aui:row>
	</aui:fieldset-group>
	
</aui:form>
</div>
</div>
</div>