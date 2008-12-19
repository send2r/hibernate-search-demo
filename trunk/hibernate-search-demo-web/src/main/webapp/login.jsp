<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <title>Login</title>     
</head>

<body>  
	<div class="Login"> 		
		<s:form action="check-login" theme="simple">
			<div class="LoginBox">
				<div class="Header">
					<s:text name="login.title"></s:text>
				</div>
				<div class="Content">
					<span><s:text name="login.email"></s:text>:</span>
					<span><s:textfield name="email" key="login.email" /></span>
				</div>
				<div class="Footer">
					<input type="submit" value="<s:text name="button.login"/>"/>
				</div>
			</div>
		</s:form>
	</div>
</body>
</html>
