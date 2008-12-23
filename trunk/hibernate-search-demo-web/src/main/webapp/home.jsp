<?xml version="1.0" encoding="gb2312"?>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/WEB-INF/tld/belga-ui.tld" prefix="belga"%>
<head>
<link type="text/css"
	href="<s:url value='/styles/pager.css' includeParams='none'/>"
	rel="stylesheet" />
<link type="text/css"
	href="<s:url value='/styles/table.css' includeParams='none'/>"
	rel="stylesheet" />
</head>
<body>

<div id="search">
	<form action="simple-search.htm">
		<s:textfield name="searchString"></s:textfield>
		<input value="Search" type="submit"/>
	</form>
</div>
<s:form name="home" action="resume-add.htm" theme="simple">
<table class="ItemList">
	<tr>
		<th class="Id"><s:text name="home.table.id" /></th>
		<th class="UserName"><s:text name="home.table.username" /></th>
		<th class="Email"><s:text name="home.table.email" /></th>
		<th class="Summary"><s:text name="home.table.summary" /></th>
		<th class="CreatedDate"><s:text name="home.table.creation-date" /></th>
		<s:if test="#session.currentUser != null">
			<th class="Delete"><input type="checkbox" onclick="checkAll(this, document.home.listDelete);"/></th>
		</s:if>
	</tr>
	<s:iterator value="#request.listResume" id="resume">
		<tr>
			<td class="Id"><s:property value="id" /></td>
			<td class="UserName"><s:property value="applicant.fullName" /></td>
			<td class="Email"><s:property value="applicant.emailAddress" /></td>
			
			<td class="Summary"><div><a href="<s:url action="edit-resume.htm" />"><s:property value="summary" /></a></div></td>
			<td class="CreatedDate"><s:property value="lastUpdated" /></td>
			<s:if test="#session.currentUser != null">
				<td class="Delete"><input type="checkbox" value="<s:property value="id"/>" name="listDelete"/></td>
			</s:if>
		</tr>
	</s:iterator>
	<s:if test="#session.currentUser != null">
		<tr>
			<td colspan="6" class="Function">
				<input type="submit" value="AddResume"/>
				<input type="button" value="Delete"/>
			</td>
		</tr>
	</s:if>
</table>
</s:form>
<belga:pager id="pager" cssClass="Pager" dataModel="pager"
	templateConfig="templateConfig" templateName="pager.ftl" />
</body>
</html>