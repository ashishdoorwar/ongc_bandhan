<%@page import="com.ongc.liferay.bandhan.service.Impl.UserServiceImpl"%>
<%@page import="com.ongc.liferay.bandhan.service.UserService"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ongc.liferay.bandhan.model.Faculty"%>
<%@ include file="/init.jsp"%>
<%
	Faculty faculty = (Faculty) request.getAttribute("faculty");
	UserService userService=new UserServiceImpl(); 
	ArrayList<String> programCodeList = userService.getProgramCodeList();
	
	
	boolean disable = true;
	if (faculty == null) {

		disable = false;
		faculty = new Faculty();
	}
%>
<%
	if (disable) {
%>
<%@ include file="/facultyApplication/viewFaculty.jsp"%>
<%
	} else {
%>
<%@ include file="/facultyApplication/addFaculty.jsp"%>
<%
	}
%>