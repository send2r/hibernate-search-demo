<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <title>Add New Resume</title>     
</head>

<body>  
<div class="AddNew"> 		
	<s:form action="save-newresume" id="addNewPeople" theme="simple">
		<div class="AddNewBox">
				<div class="Header">
					<s:text name="resume.title"></s:text>
				</div>
				<div class="Content">
					<div>
						<span class="Text">
							<s:text name="resume.summary"/>
						</span>
						<span class="Input">
							<s:textarea cols="50" rows="3" name="summary" key="resume.summary" />
						</span>
					</div>
					<div>
						<span class="Text">
							<s:text name="resume.content"/>
						</span>
						<span class="Input">
							<s:textarea cols="50" rows="5" name="content" key="resume.content"/>
						</span>
				</div>
				</div>
				
				<div class="Footer">
					<input type="hidden" name="page" value="<s:property value="%{page}"/>"/>
					<input type="button" value="<s:text name="button.cancel"/>" onclick="goURL('<s:url value="home.htm"/>');"/>
					<input type="submit" value="<s:text name="button.save"/>"/>
					<input type="reset" value="<s:text name="button.reset"/>"/>	
				</div>
		</div>
	</s:form>
</div>
</body>
</html>
