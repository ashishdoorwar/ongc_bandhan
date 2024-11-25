<div class="formwrapper">
	<h3>Talent Pool</h3>
<aui:form name="fm" method="post">
	<aui:fieldset-group markupView="lexicon">
		<aui:row>
			<aui:col width="33">
				<aui:input label="CPF Number" name="cpfno" type="text"
					value="<%=talent.getCpfno()%>" />
			</aui:col>
			<aui:col width="33">
				<aui:input label="Name of Employee" name="userName" type="text"
					value="<%=talent.getUsername()%>" />
			</aui:col>

			<aui:col width="33">
				<aui:input label="Date of Birth " name="dob" type="text"
					value="<%=talent.getDob()%>">
				</aui:input>
			</aui:col>
		</aui:row>
		<aui:row>
			<aui:col width="33">
				<aui:input label="Qualification" name="qualification" type="text"
				value="<%=talent.getQualification()%>"
				>
					<aui:validator name="required" />
				</aui:input>
			</aui:col>
			<aui:col width="33">
				<aui:input label="Date of Separation" name="dateOfSep" type="text"
					value="<%=talent.getDos()%>" />
			</aui:col>


			<aui:col width="33">
				<aui:input label="Designation at separation" name="designation"
					type="text" value="<%=talent.getDesignation()%>" />
			</aui:col>
		</aui:row>
		<aui:row>
			<aui:col width="33">
				<aui:input label="Last Assignment From" name="lastTenFrm" value="<%=talent.getLastTenFrm()%>"
					type="text" >
					<aui:validator name="required" />
						</aui:input>
			</aui:col>
			<aui:col width="33">
				<aui:input label="Last Assignment to" name="lastTenTo" type="text" value="<%=talent.getLastTenTo()%>"
				>
				<aui:validator name="required" />
				</aui:input>
			</aui:col>
			<aui:col width="33">
				<aui:input label="Department (Last Posted)" name="department" value="<%=talent.getDepartment()%>"
					type="text">
					<aui:validator name="required" />
				</aui:input>
			</aui:col>
		</aui:row>
		<aui:row>
			<aui:col width="33">
				<aui:input label="Skill Set" name="skillSet" type="text" value="<%=talent.getSkillSet()%>">
					<aui:validator name="required" />
				</aui:input>
			</aui:col>
			<aui:col width="33">
				<aui:input label="Domain Speciality" name="domain" type="text" value="<%=talent.getDomain()%>">
					<aui:validator name="required" />
				</aui:input>
			</aui:col>
			<aui:col width="33">
				<aui:input label="Mobile No" name="mobileNo" type="text" value="<%=talent.getMobileNo()%>">
					<aui:validator name="required" />
				</aui:input>
			</aui:col>
		</aui:row>
		<aui:row>
			<aui:col width="33">
				<aui:input label="Email Id" name="emailId" type="text" value="<%=talent.getEmailId()%>">
				<aui:validator name="required" />
				</aui:input>
			</aui:col>
			<aui:col width="33">
				<aui:input label="Address" name="address" type="textarea" value="<%=talent.getAddress()%>">
				<aui:validator name="required" />
				</aui:input>
				
			</aui:col>
			<aui:col width="33">
				<aui:field-wrapper name="legalStatus" required="true">
					<aui:fieldset-group>
						<label class="aui-field-label">Willingness for being
							considered for any engagement as
							Expert/Specialist/Advisor/Consultant </label>
						<aui:input label="Yes" name="legalStatus" type="radio" />
						<aui:input label="No" name="legalStatus" type="radio" />
					</aui:fieldset-group>
				</aui:field-wrapper>
			</aui:col>
		</aui:row>

		<aui:button-row cssClass="text-center">


			<aui:button cssClass="btn btn-primary" value="Back" />

		</aui:button-row>
	</aui:fieldset-group>

</aui:form>
</div>