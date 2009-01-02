<?xml version="1.0" encoding="utf-8"?>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/WEB-INF/tld/belga-ui.tld" prefix="belga"%>
<head>
<link type="text/css"
	href="<s:url value='/styles/pager.css' includeParams='none'/>"
	rel="stylesheet" />
<link type="text/css"
	href="<s:url value='/styles/mainfile.css' includeParams='none'/>"
	rel="stylesheet" />
</head>
<body>

<div id="search">
	<form action="simple-search.htm" method="get" >
		<div id="simpleSearch">
			<span class="Link">
				<a href="<s:url value="config-search.htm" includeParams="none"/>" ><img src="<s:url value="/images/advancedsearch.gif" includeParams="none"/>" alt="" /></a>
			</span>
			<span class="Form">
				<s:textfield name="searchString" size="60"></s:textfield>
				<input value="Search" type="submit"/>
			</span>
		</div>
	</form>
</div>

<s:form name="home" action="delete-resume.htm" theme="simple">
	<s:iterator value="#request.listFiles">
		<div class="FileBox">
			<img alt="image" width="50" height="70" src="<s:property value="imageIcon"/>"/>
			<div class="Content">
				<a class="Title"><s:property value="fileName"/></a>
				<p class="Text"><s:property value="sortContent"/></p>
				<a class="More">Download</a>
			</div>
		</div>
	</s:iterator>
</s:form>
<div id="uploadControl">
	<input type="button" value="UploadFile" onclick="showForm();"/>
</div>
<div id="uploadFile">
	<div id="content">
		<s:include value="/files/uploadFile.jsp"/>
	</div>
</div>
<belga:pager id="pager" cssClass="Pager" dataModel="pager"
	templateConfig="templateConfig" templateName="pager.ftl" />
</body>
</html>