
<%@page import="com.ongc.liferay.bandhan.model.User"%>
<%
	User userData = userService.getUser(); %>
<portlet:actionURL var="addTalent" name="add_talent">

</portlet:actionURL>
<div class="formWrapper">
<h3>Talent Pool</h3>

<aui:form action="${addTalent}" name="fm" method="post">
	<aui:fieldset-group markupView="lexicon">
		<aui:row>
			<aui:col width="33">
				<aui:input label="CPF Number" name="cpfno" type="text" 
					value="<%=userData.getCpfNo()%>" readonly="true"/>
			</aui:col>
			<aui:col width="33">
				<aui:input label="Name of Employee" name="userName" type="text" readonly="true"
					value="<%=userData.getUserName()%>" />
			</aui:col>

			<aui:col width="33">
				<aui:input label="Date of Birth " name="dob" type="text"
					value="<%=userData.getDateOfBirth()%>">
				</aui:input>
			</aui:col>
		</aui:row>
		<aui:row>
			<aui:col width="33">
				<aui:input label="Qualification" name="qualification" type="text">
					<aui:validator name="required" />
				</aui:input>
			</aui:col>
			<aui:col width="33">
				<aui:input label="Date of Separation" name="dateOfSep" type="date"
					value="<%=userData.getDateOfSep()%>" />
			</aui:col>


			<aui:col width="33">
				<aui:input label="Designation at separation" name="designation"
					type="text" value="<%=userData.getDesignation()%>" />
			</aui:col>
		</aui:row>
		<aui:row>
			<aui:col width="33">
				<aui:input label="Last Assignment From" name="lastTenFrm"
					type="date" onchange="checkParameter();checkStartDate();">
					<aui:validator name="required" />
						</aui:input>
			</aui:col>
			<aui:col width="33">
				<aui:input label="Last Assignment to" name="lastTenTo" type="date" 
				onchange="checkParameter();checkEndDate();">
				<aui:validator name="required"  />
				</aui:input>
			</aui:col>
			<aui:col width="33">
				<aui:input label="Department (Last Posted)" name="department"
					type="text">
					<aui:validator name="required" />
				</aui:input>
			</aui:col>
		</aui:row>
		<aui:row>
			<aui:col width="33">
				<aui:input label="Skill Set" name="skillSet" type="text">
					<aui:validator name="required" />
				</aui:input>
			</aui:col>
			<aui:col width="33">
				<aui:input label="Domain Speciality" name="domain" type="text">
					<aui:validator name="required" />
				</aui:input>
			</aui:col>
			<aui:col width="33">
				<aui:input label="Mobile No" name="mobileNo" type="text">
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
		</aui:row>
		<aui:row>
			<aui:col width="33">
				<aui:input label="Email Id" name="emailId" type="email" >
				<aui:validator name="required" />
				<aui:validator name="email" />
				
				</aui:input>
			</aui:col>
			<aui:col width="33">
				<aui:input label="Address" name="address" type="textarea" >
				<aui:validator name="required" />
				</aui:input>
				
			</aui:col>
			<aui:col width="33">
				<aui:field-wrapper name="willingNo" required="true">
					<aui:fieldset-group>
						<label class="aui-field-label">Willingness for being
							considered for any engagement as
							Expert/Specialist/Advisor/Consultant </label>
						<aui:input label="Yes" name="willingNo" type="radio" />
						<aui:input label="No" name="willingNo" type="radio" />
					</aui:fieldset-group>
				</aui:field-wrapper>
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

<script>

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
</script>