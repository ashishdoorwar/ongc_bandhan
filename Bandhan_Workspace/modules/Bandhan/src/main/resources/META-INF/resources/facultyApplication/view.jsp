<%@page import="com.ongc.liferay.bandhan.model.Faculty"%>
<%@page import="java.util.List"%>
<%@ include file="/init.jsp"%>


<%
List<Faculty> faculties = (List<Faculty>) request.getAttribute("facultyData");
%>


<portlet:renderURL var="addFacultyPost">

	<portlet:param name="mvcPath"
		value="/facultyApplication/addAndViewFaculty.jsp" />
</portlet:renderURL>
<div class="row">

	<div class="col-md-12">
		<h1>Summary</h1>
	</div>
</div>

<div class="col-md-12 mb10" dir="ltr">
	<div class="pull-right" dir="ltr">
		<a href="<%=addFacultyPost %>"
			class="btn btn-primary" style="text-transform: unset;">Apply Now
		</a>
	</div>
</div>
<%@ include file="/facultyApplication/list.jsp"%>
