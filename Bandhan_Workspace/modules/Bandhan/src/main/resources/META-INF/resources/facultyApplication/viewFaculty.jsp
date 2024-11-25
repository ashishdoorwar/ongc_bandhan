<h1>Summary</h1>
<div class="formWrapper">
<h3>Faculty Application</h3>

<% String backUrl = (String) request.getAttribute("backUrl"); %>
<aui:form name="fm" method="post">
	<aui:fieldset-group markupView="lexicon">
		<aui:row>
			<aui:col width="33">
				<aui:input label="CPF Number" name="cpfno" type="text"
					disabled="true" value="<%=faculty.getCpfno()%>" />
			</aui:col>
			<aui:col width="33">
				<aui:input label="Name of Employee" name="username" type="text"
					disabled="true" value="<%=faculty.getUsername()%>" />
			</aui:col>
			<aui:col width="33">
				<aui:input label="Designation" name="designation" type="text"
					disabled="true" value="<%=faculty.getDesignation()%>" />
			</aui:col>
		</aui:row>
		<aui:row>
			<aui:col width="33">
				<aui:input label="Date of Superannuation" name="dos" type="text"
					disabled="true" value="<%=faculty.getDos()%>" />
			</aui:col>
			<aui:col width="33">
				<aui:input label="Mobile No " name="mobileNo" type="text"
					disabled="true" value="<%=faculty.getMobileNo()%>" />
			</aui:col>
			<aui:col width="33">
				<aui:input label="Email Id " name="emailId" type="text"
					disabled="true" value="<%=faculty.getEmailId()%>" />
			</aui:col>
		</aui:row>
		<aui:row>
			<aui:col width="33">
				<aui:input label="Fax No" name="faxno" type="text" disabled="true"
					value="<%=faculty.getFaxno()%>" />
			</aui:col>
			<aui:col width="33">
				<aui:input label="Address" name="address" type="text"
					disabled="true" value="<%=faculty.getAddress()%>" />
			</aui:col>
			<aui:col width="33">
				<aui:input label="City" name="city" type="text" disabled="true"
					value="<%=faculty.getCity()%>" />
			</aui:col>
		</aui:row>
		<aui:row>
			<aui:col width="33">
				<aui:input label="Area of Specialisation" name="specialisation"
					disabled="true" type="text"
					value="<%=faculty.getSpecialisation()%>" />
			</aui:col>
			<aui:col width="33">
				<aui:input label="Academic Qualification" name="academics"
					disabled="true" type="text" value="<%=faculty.getAcademics()%>" />
			</aui:col>
			<aui:col width="33">
				<aui:input label="Program Code" name="code" type="textarea"
					disabled="true" value="<%=faculty.getCode()%>" />

				
			</aui:col>
		</aui:row>
		<aui:row>
			<aui:col width="33">
				<aui:input label="Description" name="desc" type="textarea"
					disabled="true" value="<%=faculty.getCode()%>" />
			</aui:col>
		</aui:row>

		<aui:button-row cssClass="text-center">
			<a class="btn btn-primary" href="<%= backUrl %>">Back</a>
		</aui:button-row>
	</aui:fieldset-group>

</aui:form>
</div>