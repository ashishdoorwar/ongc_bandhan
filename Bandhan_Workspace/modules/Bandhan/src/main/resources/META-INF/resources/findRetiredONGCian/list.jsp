
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.ongc.liferay.bandhan.model.User"%>
<%@page import="java.util.List"%>
<%@ include file="/findRetiredONGCian/view.jsp"%>
<%
List<User> users = (List<User>) request.getAttribute("userList");

%>
<portlet:renderURL var="getUserData" windowState="<%=LiferayWindowState.POP_UP.toString()%>">
	<portlet:param name="mvcRenderCommandName" value="getRetiredONGCianInfo"/>
</portlet:renderURL>
<table class="table table-striped table-bordered stripe row-border order-column m-3"
			id="retiredONGCian" width="100%">
	<thead>
		<tr>
			<th>CPF Number</th>
			<th>Name</th>
			<th>Settlement Location</th>
			<th>Mobile</th>
			<th>Email-ID</th>
		</tr>
	</thead>
<%for(User userData:users){ %>
	<tr>
		<td><%=userData.getCpfNo() %></td>
		<td><a onclick="popup('<%= userData.getCpfNo() %>')" id="cpfNo"><%=userData.getUserName() %></a><br><%=userData.getDesignation() %></td>
		<td><%=userData.getLocation() %></td>
		<td><%=userData.getMobileNo() %></td>
		<td><%=userData.getEmailId() %></td>
		
	</tr>
<%} %>
</table>
<script type="text/javascript">
	$(document).ready(function() {
		var table = $('#retiredONGCian').DataTable({
			lengthChange: false,bFilter: false, bInfo: false

		});
	});
	
	
	
	 function popup(cpfNo){
		 var url="<%=getUserData%>&<portlet:namespace />cpfNo="+cpfNo;
		Liferay.Util.openWindow({
		    dialog: {
		        centered: true,
		        height: 600,
		        modal: true,
		        width: 800,
		        style:"background-color: #8c000d;color: #fff !important;",
		        cssClass: "ui-model",
		        destroyOnHide: true,
	            resizable: false,
		    },
		    id: "<portlet:namespace />popUpId",
		    title: "Employee Details",
		    uri: url
		}); 
		  Liferay.provide(window, '<portlet:namespace />closePopup', function(popUpId , id) {
	         Liferay.Util.Window.getById(popUpId).destroy();
	         location.reload();
	     },
	     ['liferay-util-window']
	     ); 
		 
		 
	}
	
</script>