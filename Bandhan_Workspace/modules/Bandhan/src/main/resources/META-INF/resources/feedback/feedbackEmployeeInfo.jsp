<%@page import="java.util.Calendar"%>
<%@page import="com.ongc.liferay.bandhan.model.User"%>
<%@page import="java.util.List"%>
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
<td rowspan="4" align="center" class="valign-m"><img class="user-img" src="/wps/PA_ONGC_Report_M/DisplayImage?number=84010"></td></tr>
<tr><th>Employee Name</th><td colspan="2"> <%= userList.getUserName() %></td></tr>
<tr><th>Designation</th><td colspan="2"><%= userList.getDesignation() %></td></tr>
<%-- <tr><th>Workplace</th><td colspan="2"><%= userList.getWorkPlace() %></td></tr>
<tr><th class="td-wd1">Employee Level</th><td class="td-wd1"><%= userList.getEmpLevel() %></td><th class="td-wd1">Place of Posting</th><td class="td-wd1"><%= userList.getCurrentPlace() %></td></tr>
<tr><th>Department</th><td><%= userList.getDepartment() %></td><th>Blood Group</th><td><%= userList.getBloodGroup() %></td></tr>
<tr><th>Email ID (Office)</th><td><%= userList.getEmailIdOfficial() %></td><th>Mobile</th><td><%= userList.getMobileNo() %></td></tr>
<tr><th>Email ID (Personal)</th><td><%= userList.getEmailIdPersonal() %></td><th>Fax Number</th><td><%= userList.getFaxNumber() %></td></tr>
<tr><th>Office Direct No</th><td><%= userList.getPhoneNumberOffice() %></td><th>Residence Direct No</th><td><%= userList.getPhoneNumberHome() %></td></tr>
<tr><th>Office Extension No</th><td><%= userList.getExtensionNumber() %></td><th>Office Board No</th><td><%= userList.getBoardNumber() %></td></tr>
<tr><th>Office Address</th><td><%= userList.getOfficeAddress() %></td><th>Residence Address</th><td><%= userList.getResidenceAddress() %></td></tr>
<tr><th>About Me</th><td><%= userList.getAboutMe() %></td><th>Birth Day</th><td><%= day %><%= monthS %></td></tr> --%>
<%-- <tr><th>Passion</th><td colspan="3"><% for (int i = 0; i < userPassion.size(); i++) { %><%=userPassion.get(i).getPassionName() %> | <%= userPassion.get(i).getSubPassion()%> , <%} %></td></tr> --%>
</tbody></table>
</div>