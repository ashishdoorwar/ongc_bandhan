
<%@page import="com.ongc.liferay.bandhan.model.Talent"%>


<button id="excel" class="btn btn-primary m-2">Export to
			excel</button>
		<div class="table-responsive">
<table class="table table-striped table-bordered stripe row-border order-column m-3"
	id="faculty" width="100%">
	<thead>
		<tr>

			<th>CPF No</th>
			<th>Name</th>
			<th>Mobile</th>

			<th>Department</th>
			<th>Skill</th>
			<th>Posted On</th>
			<th>Action</th>


		</tr>
	</thead>
	<%
			for (Talent talent : talents) {
		%>

	<tr>
		<portlet:renderURL var="viewTalentDetail">
			<portlet:param name="mvcRenderCommandName" value="getTalentDetail" />
			<portlet:param name="id" value="<%=String.valueOf(talent.getTid())%>" />
		</portlet:renderURL>
		<td><%=talent.getCpfno()%></td>
		<td><%=talent.getUsername()%></td>
		<td><%=talent.getMobileNo()%></td>
		<td><%=talent.getDepartment()%></td>
		<td><%=talent.getSkillSet()%></td>
		<td><%=talent.getPosteddt()%></td>
		<td><a href="<%=viewTalentDetail%>">View</a></td>
	</tr>
	<%
			}
		%>

</table>
</div>	
<script type="text/javascript">
	$(document).ready(function() {
		var table = $('#faculty').DataTable({
			lengthChange: false,bFilter: false, bInfo: false

		});
	});
	
	$("#excel").click(function() {
		$("#faculty").table2excel({
			// exclude CSS class
			filename : "SomeFile.xls",//do not include extension
		});
	});
</script>
