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
	href="<s:url value='/styles/table.css' includeParams='none'/>"
	rel="stylesheet" />
</head>
<body>

<div id="search">
	<form action="simple-search.htm" method="get" >
		<s:hidden name="sortField" value="%{sortField}"/>
		<s:hidden name="order" value="%{order}"/>
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
<table class="ItemList">
	<tr>
		<th class="Id"><s:text name="home.table.id" /></th>
		<th class="UserName"><s:text name="home.table.username" /></th>
		<th class="Email">
			<s:if test="%{sortField == 'Email'}">
				<s:if test="%{order == 'ASC'}">
					<s:url id="email" >
						<s:param name="order">DESC</s:param>
						<s:param name="sortField">Email</s:param>
					</s:url>
					<a href="${email}"><s:text name="home.table.email" /></a>
					<img src="<s:url value="/images/up.png" includeParams="none"/>"/>
				</s:if>
				<s:else>
					<s:url id="email" >
						<s:param name="order">ASC</s:param>
						<s:param name="sortField">Email</s:param>
					</s:url>
					<a href="${email}"><s:text name="home.table.email" /></a>
					<img src="<s:url value="/images/down.png" includeParams="none"/>"/>
				</s:else>
			</s:if>
			<s:else>
				<s:url id="email" >
					<s:param name="order">ASC</s:param>
					<s:param name="sortField">Email</s:param>
				</s:url>
				<a href="${email}"><s:text name="home.table.email" /></a>
			</s:else>
		</th>
		<th class="Summary">
			<s:if test="%{sortField == 'Summary'}">
				<s:if test="%{order == 'ASC'}">
					<s:url id="summary" >
						<s:param name="order">DESC</s:param>
						<s:param name="sortField">Summary</s:param>
					</s:url>
					<a href="${summary}"><s:text name="home.table.summary" /></a>
					<img src="<s:url value="/images/up.png" includeParams="none"/>"/>
				</s:if>
				<s:else>
					<s:url id="summary" >
						<s:param name="order">ASC</s:param>
						<s:param name="sortField">Summary</s:param>
					</s:url>
					<a href="${summary}"><s:text name="home.table.summary" /></a>
					<img src="<s:url value="/images/down.png" includeParams="none"/>"/>
				</s:else>
			</s:if>
			<s:else>
				<s:url id="summary" >
					<s:param name="order">ASC</s:param>
					<s:param name="sortField">Summary</s:param>
				</s:url>
				<a href="${summary}"><s:text name="home.table.summary" /></a>
			</s:else>
			
		</th>
		<th class="CreatedDate">
			<s:if test="%{sortField == 'CreateDate'}">
				<s:if test="%{order == 'ASC'}">
					<s:url id="createdate" >
						<s:param name="order">DESC</s:param>
						<s:param name="sortField">CreateDate</s:param>
					</s:url>
					<a href="${createdate}"><s:text name="home.table.creation-date" /></a>
					<img src="<s:url value="/images/up.png" includeParams="none"/>"/>
				</s:if>
				<s:else>
					<s:url id="createdate" >
						<s:param name="order">ASC</s:param>
						<s:param name="sortField">CreateDate</s:param>
					</s:url>
					<a href="${createdate}"><s:text name="home.table.creation-date" /></a>
					<img src="<s:url value="/images/down.png" includeParams="none"/>"/>
				</s:else>
			</s:if>
			<s:else>
				<s:url id="createdate" >
					<s:param name="order">ASC</s:param>
					<s:param name="sortField">CreateDate</s:param>
				</s:url>
				<a href="${createdate}"><s:text name="home.table.creation-date" /></a>
			</s:else>
		</th>
		<s:if test="#session.currentUser != null">
			<th class="Delete"><input type="checkbox" onclick="checkAll(this, document.home.listDelete);"/></th>
		</s:if>
	</tr>
	<s:iterator value="#request.listResume" >
		<tr>
			<td class="Id"><s:property value="id" /></td>
			<td class="UserName"><s:property value="applicant.fullName" /></td>
			<td class="Email">
				<s:property value="applicant.emailAddress" />
			</td>
			<s:if test="#session.currentUser != null">
				<td class="Summary"><div><a href="<s:url value="edit-resume.htm?id=%{id}" />"><s:property value="summary" escape="true" /></a></div></td>
			</s:if>
			<s:else>
				<td class="Summary"><div><s:property value="summary" escape="true"/></div></td>
			</s:else>
			<td class="CreatedDate"><s:property value="lastUpdated" /></td>
			<s:if test="#session.currentUser != null">
				<td class="Delete"><input type="checkbox" value="<s:property value="id"/>" name="listDelete"/></td>
			</s:if>
		</tr>
	</s:iterator>
	<s:if test="#session.currentUser != null">
		<tr>
			<td colspan="6" class="Function">
				<input type="button" value="AddResume" onclick="goURL('<s:url value="resume-add.htm?page=%{#request.pager.pageIndex}" includeParams="none"/>');"/>
				<input type="submit" value="Delete"/>
			</td>
		</tr>
	</s:if>
</table>
</s:form>
<belga:pager id="pager" cssClass="Pager" dataModel="pager"
	templateConfig="templateConfig" templateName="pager.ftl" />
</body>
</html>