package demo.hibernatesearch.model;

import java.io.File;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FileUtils;

import demo.hibernatesearch.application.ManagerResource;

public class FileUploadDTO {
	
	private File fileUpload;
	private String fileName;
	private String mineType;
	private String content;
	private String sortContent;
	private String docId;
	
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public File getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getMineType() {
		return mineType;
	}
	public void setMineType(String mineType) {
		this.mineType = mineType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSortContent() {
		StringBuilder result = new StringBuilder();
		if(content.length() >= 300){
			result.append(content.substring(0,300));
		}else{
			result.append(content);
		}
		result.delete(result.lastIndexOf(" "), result.length());
		result.append(" ...");
		return result.toString();
	}
	public void setSortContent(String sortContent) {
		this.sortContent = sortContent;
	}
	public String getImageIcon(){
		return ManagerResource.getImageIcon(fileName);
	}
}
