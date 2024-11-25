<%@page import="com.ongc.liferay.bandhan.model.Talent"%>
<%@page import="java.util.List"%>
<%@ include file="/init.jsp"%>

<%
List<Talent> talents = (List<Talent>) request.getAttribute("talentData");
%>
<portlet:renderURL var="addTalentPool">

	<portlet:param name="mvcPath"
		value="/talentPool/addAndViewTalent.jsp" />
</portlet:renderURL>
<div class="container">
<div class="row">

	<div class="col-md-12">
		<h1>Talent Data</h1>
	</div>
</div>

<div class="col-md-12 mb10" dir="ltr">
	<div class="pull-right" dir="ltr">
		<a href="<%=addTalentPool %>" class="btn btn-primary"
			style="text-transform: unset;">Apply Now </a>
		<%-- <a href="<%=exportToExcel %>" class="btn btn-primary"
			style="text-transform: unset;">Export to Excel </a> --%>
	</div>
</div>
<%@ include file="/talentPool/list.jsp"%>
</div>