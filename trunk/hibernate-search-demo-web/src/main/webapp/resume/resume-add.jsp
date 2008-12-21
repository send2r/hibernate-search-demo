<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <title>Add New Resume</title>     
</head>

<body>  
<div class="AddNew"> 		
	<s:form action="save-newresume" id="addNewPeople" theme="simple">
		<!--<tr align="center">
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
		
		--><div class="AddNewBox">
				<div class="Header">
					<s:text name="resume.title"></s:text>
				</div>
				<div class="Content">
					<span>
						<s:property value="resume.summary"/>
					</span>
					<span>
						<s:textarea cols="50" rows="3" name="summary" key="resume.summary" />
					</span>
				</div>
				<div>
					<span>
						<s:property value="resume.content"/>
					</span>
					<span>
						<s:textarea cols="50" rows="5" name="content" key="resume.content"/>
					</span>
				</div>
				<div class="Footer">
					<a href="home.htm"><s:text name="link.home"/></a>
					<input type="submit" value="<s:text name="button.save"/>"/>
					<input type="reset" value="<s:text name="button.reset"/>"/>	
				</div>
		</div>
	</s:form>
</div>
</body>
</html>
