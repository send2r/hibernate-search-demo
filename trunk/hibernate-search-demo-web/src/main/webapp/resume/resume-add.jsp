<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <title>Add New Resume</title>     
</head>

<body>  
<div id="content"> 		

	<s:form action="save-newresume" validate="true" id="addNewPeople">
		<tr align="center">
			<td colspan="4">
				<SPAN style="font-weight: bold; font-size: 15px;"><s:text name="resume.title"/></SPAN>
			</td>	
		</tr>
	    <s:textfield name="summary" key="resume.summary" />
	    <s:textfield name="content" key="resume.content"/>
	    <tr>
			<td colspan="2" align="right"><input type="submit" value="<s:text name="button.save"/>"/></td>
		</tr>	
	</s:form>
</div>
</body>
</html>
