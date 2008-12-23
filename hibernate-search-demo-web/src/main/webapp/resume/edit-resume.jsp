<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <title>Edit Resume</title>     
</head>

<body>  
<div class="AddNew"> 		
	<s:form action="save-editresume" theme="simple">
		<div class="AddNewBox">
				<div class="Header">
					<s:text name="resume.edit.title"></s:text>
				</div>
				<div class="Content">
					<div>
						<span class="Text">
							<s:text name="resume.summary"/>
						</span>
						<span class="Input">
							<s:textarea cols="50" rows="3" name="summary" value="%{#request.resume.summary}"/>
						</span>
					</div>
					<div>
						<span class="Text">
							<s:text name="resume.content"/>
						</span>
						<span class="Input">
							<s:textarea cols="50" rows="5" name="content" value="%{#request.resume.contentString}"/>
						</span>
				</div>
				</div>
				
				<div class="Footer">
					<input type="hidden" name="id" value="<s:property value="%{#request.resume.id}"/>"/>
					<input type="hidden" name="page" value="<s:property value="%{#request.page}"/>"/>
					<input type="submit" value="<s:text name="button.save"/>"/>
					<input type="button" value="<s:text name="button.cancel"/>" onclick="goURL('<s:url value="home.htm?page=%{#request.page}" includeParams="none"/>');"/>	
				</div>
		</div>
	</s:form>
</div>
</body>
</html>
