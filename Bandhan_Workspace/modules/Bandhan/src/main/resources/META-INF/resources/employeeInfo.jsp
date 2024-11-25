<%@page import="java.util.Calendar"%>
<%@page import="com.ongc.liferay.bandhan.model.User"%>
<%@page import="java.util.List"%>
<%@ include file="/init.jsp" %>
<%!
 
 public static String theMonth(int month){
    String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    return monthNames[month];
}
 %>
<%
User userList = (User) request.getAttribute("userByCPFNumber");
//out.println(userList);
Calendar cal = Calendar.getInstance();
cal.setTime(userList.getDateOfBirth());
int year = cal.get(Calendar.YEAR);
int month = cal.get(Calendar.MONTH);
int day = cal.get(Calendar.DAY_OF_MONTH);
 String monthS=theMonth(month);
%>
<div class="table-responsive">

<table class="table table-bordered table-striped">
<tbody>
<tr><th>CPF No</th>
<td colspan="2"><%= userList.getCpfNo() %></td>
<td rowspan="4" align="center" class="valign-m"><img class="img-thumbnail" style="max-height: 150px;" src="<%=themeDisplay.getURLPortal() %>/o/blade/bandhanServlet/bandhanImageServlet?cpfno=<%= userList.getCpfNo() %>"></td></tr>
<tr><th>Employee Name</th><td colspan="2"> <% if(userList.getEmpName()!= null){ %><%= userList.getEmpName() %><% } %></td></tr>
<tr><th>Designation</th><td colspan="2"> <% if(userList.getDesignation()!= null){ %><%= userList.getDesignation() %><% } %></td></tr>
<%-- <tr><th>Workplace</th><td colspan="2"> <% if(userList.getWorkPlace()!= null){ %><%= userList.getWorkPlace() %><% } %></td></tr>
<tr><th class="td-wd1">Employee Level</th><td class="td-wd1"> <% if(userList.getEmpLevel()!= null){ %><%= userList.getEmpLevel() %><% } %></td>
	<th class="td-wd1">Place of Posting</th><td class="td-wd1"> <% if(userList.getCurrentPlace()!= null){ %><%= userList.getCurrentPlace() %><% } %></td></tr>
	
<tr><th>Department</th><td> <% if(userList.getDepartment()!= null){ %><%= userList.getDepartment() %><% } %></td><th>Blood Group</th><td> <% if(userList.getBloodGroup()!= null){ %><%= userList.getBloodGroup() %><% } %></td></tr>
<tr><th>Email ID (Office)</th><td> <% if(userList.getEmailIdOfficial()!= null){ %><%= userList.getEmailIdOfficial() %><% } %></td><th>Mobile</th><td> <% if(userList.getMobileNo()!= null){ %><%= userList.getMobileNo() %><% } %></td></tr>
<tr><th>Email ID (Personal)</th><td> <% if(userList.getEmailIdPersonal()!= null){ %><%= userList.getEmailIdPersonal() %><% } %></td><th>Fax Number</th><td> <% if(userList.getFaxNumber()!= null){ %><%= userList.getFaxNumber() %><% } %></td></tr>
<tr><th>Office Direct No</th><td> <% if(userList.getPhoneNumberOffice()!= null){ %><%= userList.getPhoneNumberOffice() %><% } %></td><th>Residence Direct No</th><td> <% if(userList.getPhoneNumberHome()!= null){ %><%= userList.getPhoneNumberHome() %><% } %></td></tr>
<tr><th>Office Extension No</th><td> <% if(userList.getExtensionNumber()!= null){ %><%= userList.getExtensionNumber() %><% } %></td><th>Office Board No</th><td> <% if(userList.getBoardNumber()!= null){ %><%= userList.getBoardNumber() %><% } %></td></tr>
<tr><th>Office Address</th><td> <% if(userList.getOfficeAddress()!= null){ %><%= userList.getOfficeAddress() %><% } %></td><th>Residence Address</th><td> <% if(userList.getResidenceAddress()!= null){ %><%= userList.getResidenceAddress() %><% } %></td></tr>
<tr><th>About Me</th><td> <% if(userList.getAboutMe()!= null){ %><%= userList.getAboutMe() %><% } %></td><th>Birth Day</th><td> <%= day %> <%= monthS %></td></tr>
<tr><th>Passion</th><td colspan="3"><% for (int i = 0; i < userPassion.size(); i++) { %>
<%=userPassion.get(i).getPassionName() %> |  <%= userPassion.get(i).getSubPassion()%> , <%} %></td></tr> --%>
</tbody></table>
</div>