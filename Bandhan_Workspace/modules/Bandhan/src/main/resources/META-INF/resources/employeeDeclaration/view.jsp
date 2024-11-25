<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.ongc.liferay.bandhan.model.User"%>
<%@page import="com.ongc.liferay.bandhan.service.Impl.UserServiceImpl"%>
<%@page import="com.ongc.liferay.bandhan.service.UserService"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/init.jsp"%>

<%
	UserService userService=new UserServiceImpl();
	User userData= userService.getUser();
	SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy");
%>
<jsp:useBean id="now" class="java.util.Date"/> 
<div class="declation">

	<div class="row">
		<div class="col-md-12 text-center mb10 bldfnt redclr mb-30">
			<h2>Declaration for availing ONGC Post Retirement Medical
				Benefit</h2>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 employeeDtl">
			<p>
				I <span> <strong><%=userData.getUserName() %> </strong>
				</span> Identification No.<span><%=userData.getCpfNo() %></span>
				retired/voluntary retired as <span><%=userData.getDesignation() %>
				</span> on <span> <%=userData.getDateOfSep() %>
				</span>residing at <span><%=userData.getLocation() %></span> hereby declare
				that I have not taken-up any full time regular employment outside
				ONGC after retirement/separation.
			</p>
		</div>
	</div>

	<div class="row">
		<div class="col-md-12 employeeDtl">
			<p>I do further solemnly declare that when I will take-up full
				time regular employment outside ONGC, I shall immediately inform
				ONGC about the employment and will not avail medical facility during
				the duration of my full time regular employment.</p>
		</div>

		<div class="col-md-12 employeeDtl">

			<p>I solemnly affirm that the above declaration is true to the
				best of my knowledge and belief. I understand that in the event of
				the declaration being found to be incorrect at a later date or
				failure to inform ONGC within a month of taking up employment
				outside ONGC, I shall be liable to be debarred from availing medical
				facility under ONGC Post Retirement Medical Scheme.</p>
		</div>
	</div>
</div>
<!-- 

add_employee_declaration

 -->
 
 <portlet:actionURL var="addEmployeeDeclaration" name="add_employee_declaration"></portlet:actionURL>
 
 <liferay-ui:success key="success"  message="Request submit successfully."/>
 <liferay-ui:error key="exist"  message="Request already exist for this year"/>
 <liferay-ui:error key="error"  message="Password Updated Successfully"/>
 <liferay-ui:error key="somethingwrong"  message="Someting went wrong."/>
<aui:form action="${addEmployeeDeclaration}" name="fm" method="post">
	<aui:fieldset-group markupView="lexicon">
		<aui:row>
			<aui:col width="33">
				<aui:input  name="cpfno" type="hidden" value="<%=userData.getCpfNo() %>" />
				<aui:input label="Date" name="currentDt" type="text" value="<%= sdf.format(now) %>" readonly="true"/>
			</aui:col>
		</aui:row>
		<aui:row>
			<aui:col width="33">
				<aui:input label="Current Residence address" name="currentPlace" type="textarea" >
				<aui:validator name="required" />
				</aui:input>
			</aui:col>
		</aui:row>
		<aui:button-row cssClass="text-center">

			<aui:button cssClass="btn btn-primary" type="submit" value="Submit" />

			<aui:button cssClass="btn btn-primary" value="Reset" />

		</aui:button-row>
	</aui:fieldset-group>
	
</aui:form>
