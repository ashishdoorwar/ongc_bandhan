<%@page import="com.ongc.liferay.bandhan.service.Impl.UserServiceImpl"%>
<%@page import="com.ongc.liferay.bandhan.model.User"%>
<%@page import="com.ongc.liferay.bandhan.service.UserService"%>
<%@ include file="/init.jsp" %>
<div style="margin-top:40px">
		<h1>Asha Kiran Form</h1>
		<h3></h3>

	</div>
	
	<!-- <div>
	<p> Please write your contact number in your post, enabling ONGC to contact you.</p>
	</div> -->
	<%
	UserService userService=new UserServiceImpl();
	User userData= userService.getUser();
%>
	<%--********************************************************************************************************************** --%>
		<!-- <a href="javascript:viewAllCovid19();"
			style="cursor: pointer; color: blue;" title="View all">View All</a> -->
	<div class="mainform" style="width: 95%">
	
		<div style="height:100%;width:100%;margin-bottom:10px">
			
		<a href="<%=request.getContextPath()%>/ashakirandoc/<%=userData.getCpfNo()%>.pdf" download>
				Please click here to download your Asha Kiran Form.
			</a>
		</div>

	</div>
	<div class="blank1"></div>
