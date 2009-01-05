package demo.hibernatesearch.action.file;

import java.io.File;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

import demo.hibernatesearch.model.FileUploadDTO;
import demo.hibernatesearch.service.FileManager;

public class FileUploadAction implements Preparable, SessionAware, RequestAware {
	
	@Autowired
	private FileManager fileManager;
	private Map session;
	private Map request;

	private File upload;
	private String uploadContentType;
	private String uploadFileName;

	public String execute() throws Exception {

		try {
			FileUploadDTO fileUpload = new FileUploadDTO();
			fileUpload.setFileName(uploadFileName);
			fileUpload.setFileUpload(upload);
			fileUpload.setMineType(uploadContentType);
			fileManager.indexFile(fileUpload);
		} catch (Exception e) {
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	
	public Map getSession() {
		return session;
	}

	public Map getRequest() {
		return request;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void prepare() throws Exception {
		// TODO Auto-generated method stub

	}

	public void setSession(Map session) {
		this.session = session;

	}

	public void setRequest(Map request) {
		this.request = request;

	}

}
