<%@page import="com.ongc.liferay.bandhan.model.User"%>
<%@ include file="/init.jsp"%>
<%
User userData=(User)request.getAttribute("activeUserData");
%>
<div class="table-responsive">
<table class="table table-bordered">
	
	<tbody>


		<tr>
			<th class="clr1" style="font-weight: bold;">CPF Number</th>
			<td colspan="2" class="clr1"><%=userData.getCpfNo() %></td>
			<td rowspan="2" class="clr1" align="center"><img
				src="<%= themeDisplay.getURLPortal()%>/o/blade/bandhanServlet/bandhanImageServlet?cpfno=<%= userData.getCpfNo() %>" width="150"
				height="100"></td>
		</tr>
		<tr>
			<th style="font-weight: bold;" class="clr2">Name of Employee</th>
			<td colspan="2" class="clr2"><%=userData.getUserName() %></td>
		</tr>

		<tr>
			<th style="font-weight: bold;" class="clr1">Location</th>
			<td class="clr1"><%=userData.getLocation() %></td>
			<th style="font-weight: bold;" class="clr1">Email-ID</th>
			<td class="clr1"><%=userData.getEmailId() %></td>
		</tr>

		<tr>
			<th style="font-weight: bold;" class="clr2">Settlement Address</th>
			<td class="clr2"><%=userData.getAddress() %></td>
			<th style="font-weight: bold;" class="clr2">Communication
				Address</th>
			<td class="clr2"><%=userData.getCommunicatedAddress() %></td>
		</tr>
		<tr>
			<th class="clr1" style="font-weight: bold;">About Me<span
				class="mand"></span>
			</th>
			<td colspan="3" class="clr1"><%=userData.getAboutMe() %></td>
		</tr>






	</tbody>
</table>
</div>