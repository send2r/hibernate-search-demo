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
			<img alt="image" width="50" height="50"/>
			<div class="Content">
				<a class="Title">asjdflsdjflksdjfskfsls</a>
				<p class="Text">content</p>
				<a class="More">asjdflsdjflksdjfskfsls</a>
			</div>
		</div>
	</s:iterator>
</s:form>
<input type="button" value="UploadFile" onclick="showForm();"/>
<div id="uploadFile">
	<div id="content">
		<s:form action="doUpload.htm" method="POST" enctype="multipart/form-data" theme="simple">
			<s:file name="upload" label="File"/>
			<s:textfield name="caption" label="Caption"/>
			<s:submit />
		</s:form>
	</div>
</div>
<belga:pager id="pager" cssClass="Pager" dataModel="pager"
	templateConfig="templateConfig" templateName="pager.ftl" />
</body>
</html>