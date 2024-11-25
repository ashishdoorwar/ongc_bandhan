<%@ include file="/init.jsp"%>

<portlet:renderURL var="searchRetiredONGCian">
	<portlet:param name="mvcRenderCommandName" value="search_retired_ONGCian" />
</portlet:renderURL>
<div class="row">

	<div class="col-md-12">
		<h1>Find Retired ONGCian</h1>
	</div>
	<!--<div class="col-md-4">
		<ul class="breadcrumb pull-right">
			<li><a href="/wps/wcm/connect/Reconnect/Home" title="Home">Home</a></li>



			<li>Find ONGCian</li>
		</ul>
	</div>-->
</div>

<div class="bg-grey search-box formWrapper" dir="ltr" id="findExOngcianDIV">
	<h3 role="heading" aria-level="2">Search ONGCians</h3>

	<form action="${searchRetiredONGCian}" method="post" name="serchFrm">

		<aui:fieldset-group markupView="lexicon">
			<aui:row>
				<aui:col width="33">
					<aui:input label="CPF Number" name="cpfNo" onchange="checkParameter()" type="text" />
				</aui:col>
				<aui:col width="33">
					<aui:input label="Employee Name" name="empName" onchange="checkParameter()" type="text" />
				</aui:col>
				<aui:col width="33">
					<aui:input label="Last Designation" name="designation" onchange="checkParameter()" type="text" />
				</aui:col>
			</aui:row>
			<aui:row>
				<aui:col width="33">
					<aui:input label="Settlement Location" name="location" onchange="checkParameter()" type="text" />
				</aui:col>
				<aui:col width="33">
					<aui:input label="Mobile No" name="mobileNo" onchange="checkParameter()" type="text" />
				</aui:col>
			</aui:row>
		</aui:fieldset-group>


		<aui:button-row cssClass="text-center">
			<aui:button cssClass="btn btn-primary" name="submitButton" type="submit" value="Search" />
		</aui:button-row>
	</form>
</div>

<script>
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
	  	var  cpfNo=document.getElementById('<portlet:namespace/>cpfNo').value;
	  	var  empName=document.getElementById('<portlet:namespace/>empName').value;
	  	var  designation=document.getElementById('<portlet:namespace/>designation').value;
	  	var  location=$('#<portlet:namespace/>location').val();
	  	var  mobileNo=$('#<portlet:namespace/>mobileNo').val();
	       if(cpfNo != "" || empName != "" || designation != "" || location != "" || mobileNo != "") {
	    	   document.getElementById("<portlet:namespace/>submitButton").disabled = false;
	       	flag = true;
	          return flag;
	       }
	  
	   return flag;
	}


</script>