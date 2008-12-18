<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <title>Edit Persons</title>
</head>
<body>
<br/>  
<div id="content">     
<s:form action="save-editpeople" validate="true" id="saveEditPeople">	       
	    <s:textfield key="person.id" name="id" value="%{people.id}" readonly="true"/>  
	    <s:textfield key="person.firstname" name="firstname" value="%{people.name}" />
	    <s:textfield key="person.lastname" name="lastname" value="%{people.lastName}"/>   
	    <s:textfield key="person.age" name="age" value="%{people.age}"/>        	        
	    <s:select key="person.sex" name="sex" value="%{people.sex}" list="{'Male','Female'}"/>  
	    <s:textfield key="person.email" name="email" value="%{people.email}"/>           
	    <s:textfield key="person.homepage" name="homepage" value="%{people.homepage}"/>   
	    <tr>
			<td colspan="2" align="right"><input type="submit" value="<s:text name="button.edit"/>"/></td>
		</tr>	
</s:form>
</div>
</body>
</html>
