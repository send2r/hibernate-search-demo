<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="Upload"> 		
	<s:form action="doUpload.htm" method="POST" enctype="multipart/form-data" theme="simple">
		<div class="UploadBox">
				<div class="Header">
					<s:text name="upload-file.title"></s:text>
				</div>
				<div class="Content">
					<s:file name="upload" label="File" size="60"/>
				</div>
				<div class="Footer">
					<span class="Text">
					</span>
					<span class="Input">
						<input type="submit" value="<s:text name="button.upload"/>"/>
						<input type="button" value="<s:text name="button.cancel"/>" onclick="hiddenForm();"/>
					</span>	
				</div>
		</div>
	</s:form>
</div>
