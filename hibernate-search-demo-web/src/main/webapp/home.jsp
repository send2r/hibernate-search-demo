<?xml version="1.0" encoding="gb2312"?>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="/struts-tags" prefix="s"%>
<head>

</head>
<body>
	<s:iterator value="#request.listResume" id="resume">
	<div>
				<s:property value="summary"/>
	</div>
		</s:iterator>
</body>
</html>