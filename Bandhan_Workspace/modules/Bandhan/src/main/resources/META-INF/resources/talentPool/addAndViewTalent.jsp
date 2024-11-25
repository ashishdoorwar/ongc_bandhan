<%@page import="com.ongc.liferay.bandhan.model.Talent"%>
<%@page import="com.ongc.liferay.bandhan.service.Impl.UserServiceImpl"%>
<%@page import="com.ongc.liferay.bandhan.service.UserService"%>
<%@page import="java.util.ArrayList"%>
<%@ include file="/init.jsp"%>
<%
	Talent talent = (Talent) request.getAttribute("talent");
	UserService userService=new UserServiceImpl(); 
	
	
	boolean disable = true;
	if (talent == null) {

		disable = false;
		talent = new Talent();
	}
	
	
	
%>
<%
	if (disable) {
%>
<%@ include file="/talentPool/viewTalentDetail.jsp"%>
<%
	} else {
%>
<%@ include file="/talentPool/addTalent.jsp"%>
<%
	}
%>