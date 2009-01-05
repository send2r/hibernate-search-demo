package demo.hibernatesearch.model;

import java.io.File;

import demo.hibernatesearch.application.ManagerResource;

public class FileUploadDTO {
	
	private File fileUpload;
	private String fileName;
	private String mineType;
	private byte[] content;
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
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public String getSortContent() {
		return sortContent;
	}
	public void setSortContent(String sortContent) {
		this.sortContent = sortContent;
	}
	public String getImageIcon(){
		return ManagerResource.getImageIcon(mineType);
	}
}
