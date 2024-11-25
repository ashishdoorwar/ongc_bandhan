<%@ include file="/init.jsp"%>

<!-- <portlet:renderURL var="changePassword">

	<portlet:param name="mvcRenderCommandName" value="change_password" />
</portlet:renderURL> -->


<portlet:actionURL var="changePassword" name="update_password">
	<portlet:param name="mvcPath" value="/ChangePassword/view.jsp" />
</portlet:actionURL>


<liferay-ui:success key="password-update-success"
	message="Password Updated Successfully" />

<liferay-ui:error key="invalid-current-password"
	message="Invalid Current Password" />
<liferay-ui:error key="confirm-new-password"
	message="New password and confirm password should be same" />

<aui:form method="post" action="${changePassword}">

	<aui:input name="current" type="password" label="current-password">
		<aui:validator name="required" />
	</aui:input>
	<aui:input name="password1" type="password" label="new-password">
		<aui:validator name="required" />
	</aui:input>

	<aui:input name="password2" type="password" label="confirm-password">
		<aui:validator name="required" />
	</aui:input>


	<aui:button-row>
		<aui:button type="submit" name="changePassword"
			value="Update Password" />
	</aui:button-row>

</aui:form>

<script>
	$("#changePassword").click(function() {
		//alert("hello");
		//alert("hello");
		window.location.reload();
	});
</script>