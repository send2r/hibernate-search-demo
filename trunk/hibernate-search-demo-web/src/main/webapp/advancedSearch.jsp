<?xml version="1.0" encoding="utf-8"?>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/WEB-INF/tld/belga-ui.tld" prefix="belga"%>
<head>
	<link rel="stylesheet" type="text/css" media="all" href="<s:url value='/scripts/jscalendar/calendar-blue2.css'/>" title="blue-2" />
	<script type="text/javascript" language="JavaScript" src="<s:url value='/scripts/jscalendar/calendar.js' includeParams='none'/>"></script>
	<script type="text/javascript" language="JavaScript" src="<s:url value='/scripts/jscalendar/calendar-setup.js' includeParams='none'/>"></script>
	<script type="text/javascript" language="JavaScript" src="<s:url value='/scripts/jscalendar/lang/calendar-en.js' includeParams='none'/>"></script>
</head>
<body>
<div class="Search"> 		
	<s:form action="advanced-search" theme="simple">
		<div class="SearchBox">
				<div class="Header">
					<s:text name="advanced-search.title"></s:text>
				</div>
				<div class="Content">
					<div>
						<span class="Text">
							<s:text name="advanced-search.all-words"/>
						</span>
						<span class="Input">
							<s:textfield name="allWords" cssClass="Full"/>
						</span>
					</div>
					<div>
						<span class="Text">
							<s:text name="advanced-search.phrase"/>
						</span>
						<span class="Input">
							<s:textfield name="wordPhrase" cssClass="Full"/>
						</span>
					</div>
					<div>
						<span class="Text">
							<s:text name="advanced-search.one-more"/>
						</span>
						<span class="Input">
							<s:textfield name="oneOrMore" cssClass="Full"/>
						</span>
					</div>
					<div>
						<span class="Text">
							<s:text name="advanced-search.none-words"/>
						</span>
						<span class="Input">
							<s:textfield name="noneWords" cssClass="Full"/>
						</span>
					</div>
					<div>
						<span class="Text">
							<s:text name="advanced-search.fields"/>
						</span>
						<span class="Input">
							<input type="checkbox" name="fields" value="summary"/> <s:text name="resume.summary"/>
							<input type="checkbox" name="fields" value="content"/> <s:text name="resume.content"/>
						</span>
					</div>
					<div>
						<span class="Text">
							<s:text name="advanced-search.from-date"/>
						</span>
						<span class="Input">
							<s:textfield id="fromDate" readonly="true" name="fromDate" ></s:textfield>
							<img src="<s:url value='/images/calendar.gif' includeParams='none'/>" align="absbottom" alt="" id="GeneralFromDateTime"  border="0"/>
							<script type="text/javascript">
						        Calendar.setup({
									inputField     :    "fromDate",     // id of the input field to store the date
									button         :    "GeneralFromDateTime",  // trigger for the calendar (button ID)
									align          :    "Tr",           // alignment (defaults to "Bl")
									singleClick    :    true,
									ifFormat       :    "%Y-%m-%d"
								});
					        </script>
						</span>
					</div>
					<div>
						<span class="Text">
							<s:text name="advanced-search.to-date"/>
						</span>
						<span class="Input">
							<s:textfield id="toDate" readonly="true" name="toDate" ></s:textfield>
							<img src="<s:url value='/images/calendar.gif' includeParams='none'/>" align="absbottom" alt="" id="GeneralToDateTime"  border="0"/>
							<script type="text/javascript">
						        Calendar.setup({
									inputField     :    "toDate",     // id of the input field to store the date
									button         :    "GeneralToDateTime",  // trigger for the calendar (button ID)
									align          :    "Tr",           // alignment (defaults to "Bl")
									singleClick    :    true,
									ifFormat       :    "%Y-%m-%d"
								});
					        </script>
						</span>
					</div>
					<div class="Footer">
						<span class="Text">
						</span>
						<span class="Input">
							<input type="submit" value="<s:text name="button.search"/>"/>
							<input type="button" value="<s:text name="button.cancel"/>" onclick="goURL('<s:url value="home.htm" includeParams="none"/>');"/>
						</span>	
					</div>
				</div>
		</div>
	</s:form>
</div>
</html>