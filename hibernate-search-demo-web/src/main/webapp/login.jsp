<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <title>Add New Resume</title>     
</head>

<body>  
<div id="content"> 		

	<s:form action="check-login" validate="true" id="addNewPeople">
		<tr align="center">
			<td colspan="4">
				<SPAN style="font-weight: bold; font-size: 15px;"><s:text name="login.title"/></SPAN>
			</td>	
		</tr>
	    <s:textfield name="email" key="login.email" />
	    <tr>
			<td colspan="2" align="right">
				<input type="submit" value="<s:text name="button.login"/>"/>
			</td>
		</tr>
	</s:form>
</div>
</body>
</html>
