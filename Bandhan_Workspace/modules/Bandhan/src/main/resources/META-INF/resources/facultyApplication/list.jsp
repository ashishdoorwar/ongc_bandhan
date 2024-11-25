<%@page import="com.ongc.liferay.bandhan.model.Faculty"%>

<div class="table-responsive">
<table class="table table-striped table-bordered stripe row-border order-column m-3"
			id="faculty" width="100%">
	<thead>
		<tr>
			<th>CPF No</th>
			<th>Name</th>
			<th>Mobile</th>
			<th>Program code</th>
			<th>Posted On</th>
			<th>Action</th>
		</tr>
	</thead>
	<%
		for (Faculty faculty : faculties) {
	%>
	<tr>
	<portlet:renderURL var="viewFacultyDetail">
			<portlet:param name="mvcRenderCommandName"
				value="getFacultyDetail" />
				<portlet:param name="id" value="<%=String.valueOf(faculty.getFacid()) %>" />
				<portlet:param name="backURL"
		value="<%=themeDisplay.getURLCurrent()%>"></portlet:param>
				</portlet:renderURL>
		<td><%=faculty.getCpfno()%></td>
		<td><%=faculty.getUsername()%></td>
		<td><%=faculty.getMobileNo()%></td>
		<td><%=faculty.getCode()%></td>
		<td><%=faculty.getPosteddt()%></td>
		<td><a href="<%=viewFacultyDetail%>">View</a></td>
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
</script>