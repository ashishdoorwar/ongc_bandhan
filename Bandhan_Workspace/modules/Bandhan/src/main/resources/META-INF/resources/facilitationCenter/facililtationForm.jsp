<%@page import="com.ongc.liferay.bandhan.service.Impl.UserServiceImpl"%>
<%@page import="com.ongc.liferay.bandhan.service.UserService"%>
<%@page import="com.ongc.liferay.bandhan.util.RTConstant"%>
<%@page import="com.ongc.liferay.bandhan.model.User"%>
<%@ include file="/init.jsp"%>

<style>
.requiredValFldMsg {
	display: none;
}

.mand {
	color: red
}
</style>
<script>
	function change(subcat) {

		if (subcat == "Matrimonial") {

			document.getElementById("accom").style.display = 'none';
			document.getElementById("locationfield").style.display = 'none';
			document.getElementById("matrimony").style.display = 'block';
		}
		if (subcat == "Accommodation") {
			document.getElementById("matrimony").style.display = 'none';
			document.getElementById("accom").style.display = 'block';
			document.getElementById("locationfield").style.display = 'block';
		}
		if (subcat == "Car Pooling" || subcat == "House Hold Goods"
				|| subcat == "Vehicle" || subcat == "Vehicle" || subcat == "") {
			document.getElementById("accom").style.display = 'none';
			document.getElementById("matrimony").style.display = 'none';
			document.getElementById("locationfield").style.display = 'block';
		}
	}

	var _chkclk = true;
	function save() {
		console.log(_chkclk);
		console.log(' call save ');
		var frm = $("#facilitation-post");
		if (validateForm(frm)) {

			var yesNo = confirm('Are you sure you want to submit?');
			if (yesNo == true) {
				console.log(' _chkclk: ' + _chkclk);
				if (_chkclk) {
					_chkclk = false;
					$("#facilitation-post").submit();
				} else {
					return false;
				}

			}
		}

	}
</script>

</head>
<body>
	<%
		UserService userService = new UserServiceImpl();
		User userData = userService.getUser();
		String username = " ";
		String cpf = " ";
		if (userData != null) {
			username = userData.getUserName();
			cpf = userData.getCpfNo();
		}
	%>
	<%--********************************************************************************************************************** --%>

	<portlet:actionURL var="addFacilitation" name="add_facilitation">
	</portlet:actionURL>
	<div class="mainform container">
		<div class="formWrapper">
			<h3>Facilitation Center</h3>
		<aui:form action="${addFacilitation}" name="fm" method="post">
			<aui:fieldset-group markupView="lexicon">
				<aui:row>

					<aui:col width="50">
						<aui:input name="cpfno" type="hidden"
							value="<%=userData.getCpfNo()%>" />
						<aui:input label="Name of Employee" readonly="true" name="userName" type="text"
							value="<%=userData.getUserName()%>" />
					</aui:col>
					<aui:col width="50">
						<aui:input label="Email Id" name="emailId" type="text" >
						<aui:validator name="required" />
						<aui:validator name="email" />
						</aui:input>
						
					</aui:col>

				</aui:row>
				<aui:row>
					<aui:col width="50">
						<aui:input label="Title for post" name="title" type="text" >
						<aui:validator name="required" />
						</aui:input>
					</aui:col>
					<aui:col width="50">
						<aui:input label="Mobile No" name="mobileNo" type="text" >
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
					<aui:col width="50">
						<aui:select id="category" name="category"
							class="sbHolder2 requiredValFld form-control"
							onchange="change(this.value)" aria-required="true"
							role="combobox" aria-live="assertive" label="Category">
							<aui:option value=''>Select Category</aui:option>
							<aui:option value="Car Pooling">Car Pooling</aui:option>
							<aui:option value="House Hold Goods">House Hold Goods</aui:option>
							<aui:option value="Accommodation">Accommodation</aui:option>
							<aui:option value="Vehicle">Vehicle</aui:option>
							<aui:option value="Matrimonial">Matrimonial</aui:option>
							
							<aui:validator name="required" />
						</aui:select>
					</aui:col>
					<aui:col width="50">
						<div id="matrimony" style="display: none;">
							<label class="control-label " for="sub_matrx"
								aria-required="true">Sub Category: <span class="mand">*</span>
							</label>
							<div class=" radio  mb10">
								<label> <input type="radio" id="sub_matrx"
									name="sub_matrx" value="Bride_L" /> Looking for Bride
								</label> <label> <input type="radio" id="sub_matrx"
									name="sub_matrx" value="Groom_L" /> Looking for Groom
								</label>
							</div>
						</div>
						<div id="accom" style="display: none;">
							<label class="control-label " for="sub_accom"
								aria-required="true"> Sub Category: <span class="mand">*</span>
							</label>
							<div class=" radio  mb10">
								<label><input type="radio" name="sub_accom"
									value="Buyer" />Buyer</label> <label><input type="radio"
									name="sub_accom" value="Seller" /> Seller</label> <label><input
									type="radio" name="sub_accom" value="RENT_G" /> Offer for Rent</label>
								<label><input type="radio" name="sub_accom"
									value="RENT_T" /> Take on Rent</label>
							</div>
						</div>
						<div id="locationfield">
							
							<div class=" mb10">
								<aui:select id="location" name="location"
									class="sbHolder2 requiredValFld form-control"
									aria-required="true" role="combobox" aria-live="assertive">
									<aui:option value="">Select Location</aui:option>
									<aui:option value="Agartala">Agartala</aui:option>
									<aui:option value="Ahmedabad">Ahmedabad</aui:option>
									<aui:option value="Ankleshwar">Ankleshwar</aui:option>
									<aui:option value="Bokaro">Bokaro</aui:option>
									<aui:option value="Cambay">Cambay</aui:option>
									<aui:option value="Chennai">Chennai</aui:option>
									<aui:option value="Dehradun">Dehradun</aui:option>
									<aui:option value="Delhi">Delhi</aui:option>
									<aui:option value="Goa">Goa</aui:option>
									<aui:option value="Hazira">Hazira</aui:option>
									<aui:option value="Jodhpur">Jodhpur</aui:option>
									<aui:option value="Jorhat">Jorhat</aui:option>
									<aui:option value="Kakinada">Kakinada</aui:option>
									<aui:option value="Karaikal">Karaikal</aui:option>
									<aui:option value="Kolkata">Kolkata</aui:option>
									<aui:option value="Mehsana">Mehsana</aui:option>
									<aui:option value="Mumbai">Mumbai</aui:option>
									<aui:option value="Nazira">Nazira</aui:option>
									<aui:option value="Panvel">Panvel</aui:option>
									<aui:option value="Rajahmundry">Rajahmundry</aui:option>
									<aui:option value="Silchar">Silchar</aui:option>
									<aui:option value="Sivasagar">Sivasagar</aui:option>
									<aui:option value="Uran">Uran</aui:option>
									<aui:option value="Vadodara">Vadodara</aui:option>
									<aui:validator name="required" />
								</aui:select> <span class='requiredValFldMsg'> Required fields are
									missing.</span>
							</div>
						</div>
					</aui:col>

				</aui:row>
				<aui:row>
					<aui:col width="50">
						<aui:input label="Upload Photo" name="imgfile" type="file" >
						</aui:input>
					</aui:col>
					<aui:col width="50">
						<aui:input label="Description" name="description" type="text" >
						<aui:validator name="required" />
						<aui:validator name="min" >10</aui:validator>
						
						</aui:input>
					</aui:col>

				</aui:row>
				<aui:button-row cssClass="text-center">

					<aui:button cssClass="btn btn-primary" type="submit" value="Submit" />
				</aui:button-row>
			</aui:fieldset-group>
		</aui:form>
	</div>

</div>



		<%--********************************************************************************************************************** --%>
		<script>
			$(document).ready(function(e) {
				var setin = setInterval(function() {
					findUname()
				}, 1000);
				function findUname() {
					var usname = $("#getUserName").val();
					var uniqueCpf = $("#uniqueCpf").val();
					if (usname != 0 && usname.length != 0) {
						$('#name').val(usname);
						$('#cpfno').val(uniqueCpf);
						clearInterval(setin);
					}
				}

				if (nonOngcian == "y") {
					$('#usertype').val('N');
				}

			});
			function ValidateFileUpload() {

				var fuData = document.getElementById('imgfile');
				var FileUploadPath = fuData.value;

				if (FileUploadPath == '') {
					//alert("Please upload an image");

				} else {
					var Extension = FileUploadPath.substring(
							FileUploadPath.lastIndexOf('.') + 1).toLowerCase();
					if (Extension == "gif" || Extension == "png"
							|| Extension == "bmp" || Extension == "jpeg"
							|| Extension == "jpg") {

						if (fuData.files && fuData.files[0]) {

							var size = fuData.files[0].size;

							if (size > 100000) {
								alert("Maximum file size exceeds, max file size 100kb");
								fuData.value = "";
								return;
							} else {
								var reader = new FileReader();
								reader.onload = function(e) {
									$('#upimg').attr({
										src : e.target.result,
										width : '150'
									});
								}
								reader.readAsDataURL(fuData.files[0]);
							}
						}
					} else {
						alert("Photo only allows file types of GIF, PNG, JPG, JPEG and BMP. ");
						fuData.value = "";
						return;
					}
				}
			}

			$("#imgfile").change(function(e) {
				ValidateFileUpload();
			});

			//*****************************************************
		</script>