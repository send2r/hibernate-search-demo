<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/WEB-INF/tld/sitemesh-decorator.tld" prefix="decorator" %>
<%@page import="java.util.ArrayList"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title><decorator:title default="Belga Video"/></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/> 
    <decorator:head/>
	<link type="text/css" href="<s:url value='/styles/main.css' includeParams='none'/>" rel="stylesheet"/>
	<link type="text/css" href="<s:url value='/styles/header.css' includeParams='none'/>" rel="stylesheet"/> 
	<link type="text/css" href="<s:url value='/styles/footer.css' includeParams='none'/>" rel="stylesheet"/>
	<link type="text/css" href="<s:url value='/styles/navigation.css' includeParams='none'/>" rel="stylesheet"/>
	<script type="text/javascript" src="<s:url value='/scripts/main.js' includeParams='none'/>"></script>
	<script type="text/javascript" src="<s:url value='/scripts/webtoolkit.js' includeParams='none'/>"></script>
</head>
<body top-marign="0">
	<div id="pageContent">
		<!--Starting header content -->
		<div id="header">
			<div id="headerContent">
				<s:if test="#session.currentUser != null">
					<a href="<s:url value='/resume/logout.htm' includeParams='none'/>">Logout</a>
				</s:if>
				<s:else>
					<a href="<s:url value='/resume/login.htm' includeParams='none'/>">Login</a>
				</s:else>
				<div id="navigation">
					<ul>
						<li class="${navigationSection=="RESUME_SECTION" ? "Active" : ""}">
							<a href="<s:url value='/resume/home.htm' includeParams='none'/>">Resume Management</a>
						</li>
						<li class="${navigationSection=="FILES_SECTION" ? "Active" : ""}">
							<a href="<s:url value='/file/file.htm' includeParams='none'/>">File Management</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<hr/>
		<!--End of header -->			
		<div id="mainContent">
			 <decorator:body/> 
		</div>
	<!--Footer content -->
	<div id="footer">
		<div id="footerContent">
			Copyright © 2008 Java Team
		</div>
	</div>
	<!--End of footer -->
	</div>
</body>
</html>