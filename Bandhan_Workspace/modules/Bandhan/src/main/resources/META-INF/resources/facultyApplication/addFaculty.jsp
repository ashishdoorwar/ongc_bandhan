
<%@page import="com.ongc.liferay.bandhan.model.User"%>
<%
	User userData = userService.getUser();
%>

<portlet:actionURL var="addFaculty" name="add_faculty">

</portlet:actionURL>

<div class="formWrapper">
	<h3>Faculty Application</h3>
	<aui:form action="${addFaculty}" name="fm" method="post">
		<aui:fieldset-group markupView="lexicon">
			<aui:row>
				<aui:col width="33">
					<aui:input label="CPF Number" name="cpfno" type="text" readonly="true"
						value="<%=userData.getCpfNo()%>"/>
				</aui:col>
				<aui:col width="33">
					<aui:input label="Name of Employee" name="username" type="text" readonly="true"
						value="<%=userData.getUserName()%>" />
				</aui:col>
				<aui:col width="33">
					<aui:input label="Designation" name="designation" type="text"
						value="<%=userData.getDesignation()%>" />
				</aui:col>
			</aui:row>
			<aui:row>
				<aui:col width="33">
					<aui:input label="Date of Superannuation" name="dos" type="text" />
				</aui:col>
				<aui:col width="33">
					<aui:input label="Mobile No " name="mobileNo" type="text">
						<aui:validator name="required" />
						<aui:validator name="custom"
							errorMessage="You can enter a maximum of 15 numeric and special characters(like +)">
							<aui:validator name="maxLength">15</aui:validator>
							  function (val, fieldNode, ruleValue) {
							  var returnValue = true;
							  var iChars = "~`!@#$%^&-*()_=[]\\\';,./{}|\":<>?qwertyuiopasdfghjklzxcvbnm";
							                for (var i = 0; i < val.length; i++) {
							                    if (iChars.indexOf(val.charAt(i)) != -1) {                
							                     returnValue = false;
							                    }
							                }
							                return returnValue;
							        }
							</aui:validator>
					</aui:input>
				</aui:col>
				<aui:col width="33">
					<aui:input label="Email Id " name="emailId" type="email">
						<aui:validator name="required" />
						<aui:validator name="email" />
					</aui:input>
				</aui:col>
			</aui:row>
			<aui:row>
				<aui:col width="33">
					<aui:input label="Fax No" name="faxno" type="text" />
				</aui:col>
				<aui:col width="33">
					<aui:input label="Address" name="address" type="text" />
				</aui:col>
				<aui:col width="33">
					<aui:input label="City" name="city" type="text">
						<aui:validator name="required" />
					</aui:input>
				</aui:col>
			</aui:row>
			<aui:row>
				<aui:col width="33">
					<aui:input label="Area of Specialisation" name="specialisation"
						type="text">
						<aui:validator name="required" />
					</aui:input>
				</aui:col>
				<aui:col width="33">
					<aui:input label="Academic Qualification" name="academics"
						type="text">
						<aui:validator name="required" />
					</aui:input>
				</aui:col>
				<aui:col width="33">
					<aui:select label="Program Code" name="code" id="code">
						<aui:option value=''>Select Program Code</aui:option>
						<%
							for (String subjectBean : programCodeList) {
						%>
						<aui:option value="<%=subjectBean%>"><%=subjectBean%></aui:option>
						<%
							}
						%>
						<aui:validator name="required" />
					</aui:select>
				</aui:col>
			</aui:row>
			<aui:row>
				<aui:col width="33">
					<aui:input label="Description" name="desc" type="text" />
				</aui:col>
			</aui:row>

			<aui:button-row cssClass="text-center">

				<aui:button cssClass="btn btn-primary" type="submit" value="Submit" />

				<aui:button cssClass="btn btn-primary"
					onclick="javascript:history.back();" value="Back" />

			</aui:button-row>
		</aui:fieldset-group>

	</aui:form>
</div>