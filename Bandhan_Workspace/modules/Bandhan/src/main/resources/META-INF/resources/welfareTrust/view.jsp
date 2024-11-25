<%@page import="com.ongc.liferay.bandhan.service.Impl.UserServiceImpl"%>
<%@page import="com.ongc.liferay.bandhan.model.User"%>
<%@page import="com.ongc.liferay.bandhan.service.UserService"%>
<%@ include file="/init.jsp"%>
<!-- JavaScript Bundle with Popper -->
<!-- CSS only -->


<div class="row">

	<div class="col-md-12">
		<h1>Trust Data</h1>
	</div>
</div>

<portlet:renderURL var="editUser">

	<portlet:param name="mvcPath" value="/welfareTrust/modifyUserDetails.jsp" />
</portlet:renderURL>
<%
	UserService userService = new UserServiceImpl();
	User userData = userService.getUser();
	
%>
<div class="bandhan2020">
	<div class="seldiv">

		
		<a href="<%=editUser%>"> Kindly update your email account,mobile
			number and PAN number for sharing of Trusts related information etc.In
			case it is already updated please ignore</a>

	</div>

	<div class="container">
		<div id="videoModal" class="modal-content-dialog" tabindex="-1"
			role="dialog">
			<div class="modal-dialog modal-dialog-centered modal-lg"
				role="document" style="max-width: 100%;">
				<div class="modal-content">
					<div>
						<button type="button" class="close text-white"
							data-dismiss="modal" aria-label="Close">
							<span class="close">&times;</span>
						</button>
					</div>
					<video id="videoPreview" width="100%" height="450" controls
						poster="">
						<source type="video/mp4">
					</video>
				</div>
			</div>
		</div>

		<div class="text-center">
			<strong><a
				href="<%=request.getContextPath()%>/video/welfaretrust.mp4"
				data-fancybox class="play-now-video" title="Play Video">Please
					click here to see the demo audio-visual for Online Claims on this
					website</a></strong>
		</div>

	</div>
	<div class="d-flex">
		<div class="mr-4">
			<label for="yId">Please Choose Year :</label>
		</div>
		<div>

			<select name="year" id="yId" style="width: 170px;">
				<option value="-1">Select year</option>
				<option value="2022">2022</option>
				<option value="2021">2021</option>
				<option value="2020">2020</option>
				<option value="2019">2019</option>
				<option value="2018">2018</option>
				<option value="2017">2017</option>
				<option value="2017">2016</option>
				<option value="2017">2015</option>
				<option value="2017">2014</option>
				<option value="2017">2013</option>
				<option value="2017">2012</option>
				<option value="2017">2011</option>
				<option value="2017">2010</option>
				<option value="2017">2009</option>
				<option value="2017">2008</option>
			</select>
		</div>
	</div>
	
	<div style="clear: both;"></div>


	<div class="nav" role="navigation" id="tdiv" style="display: none;">
		<div class="row">
			<div class="col-md-12">
				<div class="accordion" id="accordionExample">
					<div class="card">
						<div class="card-header">
							<button class="btn btn-link" data-toggle="collapse"
								data-target="#collapseOne">
								<i class="fa fa-plus"></i> ECPF
							</button>
						</div>
						<div class="collapse show" id="collapseOne"
							data-parent="#accordionExample">
							<div class="card-body">
								<ul>
									<!--  <li><a href="javascript:viewDocs('ecpf','forms','2020')">Forms/ FAQ</a></li> -->
									<li><a href="javascript:viewDocs('ecpf','tds_q1')">3rd
											Party Q1 Docs (TDS Certificates)</a></li>
									<li><a href="javascript:viewDocs('ecpf','tds_q2')">3rd
											Party Q2 Docs (TDS Certificates)</a></li>
									<li><a href="javascript:viewDocs('ecpf','tds_q3')">3rd
											Party Q3 Docs (TDS Certificates)</a></li>
									<li><a href="javascript:viewDocs('ecpf','tds_q4')">3rd
											Party Q4 Docs (TDS Certificates)</a></li>
									<li><a href="javascript:viewDocs('ecpf','stmt')">CPF
											Statement</a></li>
									<li><a href="javascript:viewDocs('ecpf','balstmt')">Balance
											Statement</a></li>
									<li><a href="javascript:viewDocs('ecpf','finstmt')">Final
											Settlement</a></li>
									<li><a href="javascript:viewDocs('ecpf','IITI')"> Interim Interest & Tax Information</a></li>

								</ul>
							</div>
						</div>
					</div>

					<div class="card">
						<div class="card-header">
							<button class="btn btn-link" data-toggle="collapse"
								data-target="#collapseTwo">
								<i class="fa fa-plus"></i> CSSS
							</button>
						</div>
						<div class="collapse" id="collapseTwo"
							data-parent="#accordionExample">
							<div class="card-body">
								<ul>
									<!--  <li><a href="javascript:viewDocs('csss','forms','2020')">Forms / FAQ</a></li> -->
									<li><a href="javascript:viewDocs('csss','finstmt')">Final
											Statement</a></li>
									<li><a href="javascript:viewDocs('csss','balstmt')">Balance
											Statement</a></li>
									<li><a href="javascript:viewDocs('csss','tds_q1')">3rd
											Party Q1 Docs (TDS Certificates)</a></li>
									<li><a href="javascript:viewDocs('csss','tds_q2')">3rd
											Party Q2 Docs (TDS Certificates)</a></li>
									<li><a href="javascript:viewDocs('csss','tds_q3')">3rd
											Party Q3 Docs (TDS Certificates)</a></li>
									<li><a href="javascript:viewDocs('csss','tds_q4')">3rd
											Party Q4 Docs (TDS Certificates)</a></li>


								</ul>
							</div>
						</div>
					</div>

					<div class="card">
						<div class="card-header">
							<button class="btn btn-link" data-toggle="collapse"
								data-target="#collapseThree">
								<i class="fa fa-plus"></i> PRBS
							</button>
						</div>
						<div class="collapse" id="collapseThree"
							data-parent="#accordionExample">
							<div class="card-body">
								<ul>
									<!--  <li><a href="javascript:viewDocs('prbs','forms','2020')">Forms / FAQ</a></li> -->
									<li><a href="javascript:viewDocs('prbs','tds')">Final
											Settlement Letter</a></li>
									<li><a href="javascript:viewDocs('prbs','stmt')">3rd
											Party (LIC Annuity letter)</a></li>
									<li><a href="javascript:viewDocs('prbs','laststmt')">Last
											Statement</a></li>
								</ul>

							</div>
						</div>
					</div>
					<div class="card">
						<div class="card-header">
							<button class="btn btn-link" data-toggle="collapse"
								data-target="#collapse4">
								<i class="fa fa-plus"></i> Get Reports through Email using
								automation(in case no data found above).
							</button>
						</div>
						<div class="collapse" id="collapse4"
							data-parent="#accordionExample">
							<div class="card-body">
								<portlet:actionURL var="saveTrust"
				name="saveTrust"></portlet:actionURL>
								<aui:form  name="fm" action="<%= saveTrust%>" method="post">
									<aui:fieldset-group markupView="lexicon">
										<aui:row>
											<aui:col width="50">
												<aui:input label="CPF Number:" name="cpfnumber"
													value="<%=userData.getCpfNo()%>" type="text" />
											</aui:col>
											<aui:col width="50">
												<aui:select label="Trust Name:" name="trustName"
													onchange="reportVlaue();">
													<aui:option value="ECPF">ECPF</aui:option>
													<aui:option value="CSSS">CSSS</aui:option>
													<aui:option value="PRBS">PRBS</aui:option>
												</aui:select>
											</aui:col>
										</aui:row>
										<aui:row>
											<aui:col width="50">

												<aui:select name="reportName" label="Report"
													onchange="reportOnchange();">
													<aui:option value="Annual Statement">Annual Statement</aui:option>
													<aui:option value="Final Settlement Letter">Final
												Settlement Letter</aui:option>
													<aui:option value="Balance Statement">Balance Statement</aui:option>
													<aui:option value="Settlement Status">Settlement Status</aui:option>
													<aui:option value="TDS Certificate">TDS Certificate</aui:option>
												</aui:select>
											</aui:col>
											<aui:col width="50">

												<aui:select name="quarter" label="Quarter:"
													onchange="reportOnchange();">
													<aui:option value="">---Select----</aui:option>
													<aui:option value="Q1">Q1</aui:option>
													<aui:option value="Q2">Q2</aui:option>
													<aui:option value="Q3">Q3</aui:option>
													<aui:option value="Q4">Q4</aui:option>
												</aui:select>
											</aui:col>
										</aui:row>
										<aui:row>

											<aui:col width="50">
												<aui:select label="Financial Year:" name="fy"
													onchange="reportVlaue();">
													<aui:option value="ECPF">ECPF</aui:option>
													<aui:option value="2020-21">2020-21</aui:option>
													<aui:option value="2019-20">2019-20</aui:option>
													<aui:option value="2018-19">2018-19</aui:option>
													<aui:option value="2017-18">2017-18</aui:option>
													<aui:option value="2017-18">2016-17</aui:option>
													<aui:option value="2017-18">2015-16</aui:option>
													<aui:option value="2017-18">2014-15</aui:option>
													<aui:option value="2017-18">2013-14</aui:option>
													<aui:option value="2017-18">2012-13</aui:option>
													<aui:option value="2017-18">2011-12</aui:option>
													<aui:option value="2017-18">2010-11</aui:option>
													<aui:option value="2017-18">2009-10</aui:option>
													<aui:option value="2017-18">2008-09</aui:option>
												</aui:select>
											</aui:col>
											<aui:col width="50">
												<aui:input label="PAN Number:" name="panNo"
													value="<%=userData.getPanNumber()%>" type="text" />
											</aui:col>
										</aui:row>
										<aui:row>
										<aui:col width="50">
												<aui:input label="Mobile Number:" name="mobileNo"
													value="<%=userData.getMobileNo()%>" type="text" />
											</aui:col>
											<aui:col width="50">
												<aui:input label="Email:" name="email"  onchange="emailCheck()"
													value="<%=userData.getEmailId()%>" type="email" >
													
													</aui:input>
											</aui:col>
										</aui:row>
									
										
										
										
									</aui:fieldset-group>
									<aui:button-row cssClass="text-center mt-2">

										<aui:button cssClass="btn btn-primary" type="submit"
											value="Submit" />

										

									</aui:button-row>
								</aui:form>
								
							</div>
						</div>
					</div>

				</div>
				<!-- .accordion -->
			</div>
		</div>





	</div>

</div>

<script>
	function viewDocs(parent, child) {
		var year = $('#yId').val();
		if (year == -1) {
			$('#tdiv').hide();
			alert("Please select year");

		}
		var cpfNo="<%=userData.getCpfNo()%>"
		//var durl = "/wps/PA_RT_Ongcian/downloadFiles?trust="+parent+"&doc="+child+"&year="+year;
		var durl = "<%= themeDisplay.getURLPortal()%>/o/blade/trustPdfservlet/trustPdf?trust="
				+ parent + "&doc=" + child + "&year=" + year+"&cpfNo=" +cpfNo;
		
		response = httpGet(durl);
	
		if(response !='' && response !='null' ){
			
		window.open(durl, "", "", "");}
		else{
			alert("Document Not Available");
		}
	}
	
	function httpGet(theUrl) {
	    var xmlHttp = new XMLHttpRequest();
	    xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
	    xmlHttp.send( null );
	    return xmlHttp.responseText;
	}

	$('#yId').on('change', function(e) {
		var selectedyear = $("option:selected", this);
		var selectedyearvalue = this.value;
		//alert("selectedyear  "+selectedyear);
		//alert("selectedyearvalue "+selectedyearvalue);
		if (selectedyearvalue == -1) {
			$('#tdiv').hide();

		} else {
			$('#tdiv').show();
		}
		// console.log( $('#selectCountry').val());
		// console.log(place);
	});

	function reportOnchange() {

		var re = $('#reportName').val();
		if (re == "TDS Certificate") {
			$('#qq').show();
			//$('#qt').show();

		} else {
			$('#qq').hide();
			//$('#qt').hide();
		}
	}

	function reportVlaue() {
		//$("#reportName option[value='text']").remove();

		/* <option value="Annual Statement">Annual Statement</option>
					  <option value="Final Settlement Letter">Final Settlement Letter</option>
					  <option value="Balance Statement">Balance Statement</option>
					  <option value="Settlement Status">Settlement Status</option>
					  <option value="TDS Certificate">TDS Certificate</option> 
					  <option value="Annuity Letter">Annuity Letter</option>
		 */

		$("#reportName option[value='Annual Statement']").remove();
		$("#reportName option[value='Final Settlement Letter']").remove();
		$("#reportName option[value='Balance Statement']").remove();
		$("#reportName option[value='Settlement Status']").remove();
		$("#reportName option[value='TDS Certificate']").remove();
		$("#reportName option[value='Annuity Letter']").remove();

		var val = $('#trustName').val();
		if (val == "ECPF") {
			var newOption = $('<option value="Annual Statement">Annual Statement</option><option value="Final Settlement Letter">Final Settlement Letter</option><option value="Balance Statement">Balance Statement</option><option value="Settlement Status">Settlement Status</option><option value="TDS Certificate">TDS Certificate</option>');

			$('#reportName').append(newOption);
		} else {

		}
		if (val == "CSSS") {
			var newOption = $('<option value="Annual Statement">Annual Statement</option><option value="Final Settlement Letter">Final Settlement Letter</option><option value="TDS Certificate">TDS Certificate</option>');

			$('#reportName').append(newOption);

		} else {

		}

		if (val == "PRBS") {
			var newOption = $('<option value="Annual Statement">Annual Statement</option><option value="Final Settlement Letter">Final Settlement Letter</option><option value="Annuity Letter">Annuity Letter</option>');

			$('#reportName').append(newOption);

		} else {

		}

		/* $('#reportName option').append($('#t1').val("testttt"));
		 var newOption = $('<option value="xxx">xxxx</option>');
		 $('#reportName').append(newOption); */

	}

	function valTrust() {
		//alert($('#panNo').val());
		//alert($('#mobileNo').val());
		//alert($('#email').val());
		//alert($('#cpfnumber').val());

		var cellNo = $('#mobileNo').val();
		var panNumber = $('#panNo').val();
		var emailID = $('#email').val();
		var cpfNumber = $('#cpfnumber').val();

		if (cellNo == 'null' || cellNo == '') {
			alert("Mobile Number can not be blank or null");
			return false;
		}
		if (panNumber == '' || panNumber == 'null') {
			alert("Pan No  can not be blank or null");
			return false;
		}
		if (emailID != '' || emailID != 'null') {
			emailCheck();
		} else {
			alert("Email id can not be blank or null");
			return false;
		}
		if (cpfNumber == '' || cpfNumber == 'null') {
			alert("CPF Number can not be blank or null");
			return false;
		}
		//alert("2");
		/*
		 if($('#mobileNo').val()=='' || $('#mobileNo').val()=='null'){
		 alert("Mobile No can not be blank or null");
		 return false;
		 }else{
		 return true;
		 }
		 if($('#email').val()=='' || $('#email').val()=='null'){
		 alert("Email id can not be blank or null");
		 return false;
		 }else{
		 return true;
		 } */
		if ($('#reportName').val() == 'TDS Certificate') {
			var yr = $('#fy').val();
			if (yr == "2020-21") {
				//return true;
			} else if (yr == "2019-20") {
				//return true;
			} else if (yr == "2018-19") {
				//return true;
			} else {
				alert("Selected date is not valid.Please select 2018-19,2019-20,2020-21");
				return false;
			}
		}
		if ($('#reportName').val() == 'Balance Statement') {
			if ($('#fy').val() == "2020-21") {
				//return true;
			} else {
				alert("Selected date is not valid. Please select 2020-21");
				return false;
			}
		}
		if ($('#reportName').val() == 'Annual Statement') {
			if ($('#fy').val() == "2020-21") {
				alert("Selected date is not valid. Please select earlier than 2020-21");
				return false;
			} else {
				//return true;
			}
		}

	}

	/*var EMAIL_REGEX = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.((com)|(org)|(co.in)|(net)|(CO.IN)|(COM)|(ORG)|(NET))$/;
	 function emailCheck()
	 {
	 var email = $('#email').val();
	 alert(EMAIL_REGEX.test(email) == true ? 'valid' : 'invalid');
	 document.getElementById("result").innerText = EMAIL_REGEX.test(email) == true ? 'valid' : 'invalid';    
	 }*/

	function emailCheck() {
		//var email = "rahul.mishra018@yahooo.com";  
		var email = $('#email').val();
		var reg = /^([\w-\.]+@(?!gmail.com)(?!yahoo.com)(?!VELOCIS.CO.IN)(?!ONGC.CO.IN)(?!GMAIL.COM)(?!rediffmail.com)(?!yahoo.co.in)(?!hotmail.com)(?!REDIFFMAIL.COM)(?!ongc.co.in)(?!YAHOO.COM)([\w-]+\.)+[\w-]{2,4})?$/;
		//alert(reg.test(email));
		if (reg.test(email)) {
			alert("Please provide the valid Email ID!!!");
			return false;
		} else {
			return true;
		}
	}

	/* $(document).ready(function(e) {
	alert($('#twt'));
	if($('#twt').val()=="user"){
	$('#test').show();
	}else{
	$('#test').hide();
	}
	}); */

	$(document).ready(function() {
		var modalV = document.getElementById("videoModal");
		modalV.style.display = "none";
		var span = document.getElementsByClassName("close")[0];

		span.onclick = function() {
			modalV.style.display = "none";
			var video = document.getElementById('videoPreview');
			video.pause();
		}

		window.onclick = function(event) {
			if (event.target == modalV) {
				modalV.style.display = "none";
			}
		}
	});

	function videoPopup(videoUrl) {
		var video = document.getElementById('videoPreview');
		video.src = videoUrl;
		video.pause();
		var modalV = document.getElementById("videoModal");
		modalV.style.display = "block";
	}
</script>