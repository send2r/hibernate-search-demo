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
	    <s:textarea cols="50" rows="3" name="summary" key="resume.summary" />
	    <s:textarea cols="50" rows="5" name="content" key="resume.content"/>
	    <tr>
			<td colspan="2" align="right">
				<input type="submit" value="<s:text name="button.save"/>"/>
				<input type="reset" value="<s:text name="button.reset"/>"/>			
			</td>
		</tr>
		<tr>
			<td colspan="2" align="left">
				<a href="home.htm"><s:text name="link.home"/></a>
			</td>
		</tr>
	</s:form>
</div>
</body>
</html>
