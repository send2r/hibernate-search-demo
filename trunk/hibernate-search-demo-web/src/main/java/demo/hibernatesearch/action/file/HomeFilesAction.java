package demo.hibernatesearch.action.file;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

import demo.hibernatesearch.application.Constants;
import demo.hibernatesearch.model.FileUploadDTO;
import demo.hibernatesearch.service.FileManager;
import demo.hibernatesearch.taglib.pager.PagerModel;
import demo.hibernatesearch.util.IList;

public class HomeFilesAction implements Preparable, SessionAware, RequestAware {

	@Autowired
	private FileManager fileManager;
	private Map session;
	private Map request;
	private String page;
	private String docId;
	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Map getSession() {
		return session;
	}

	public Map getRequest() {
		return request;
	}



	public FileManager getFileManager() {
		return fileManager;
	}

	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	public String execute() throws Exception {
		int pageIndex;
		try{
			pageIndex = Integer.valueOf(this.page);
		}catch(Exception e){
			pageIndex = 0;
		}
		IList<FileUploadDTO> result = fileManager.simpleSearch(pageIndex > 0
				? pageIndex - 1
						: pageIndex, Constants.PAGE_SIZE, "");
		
		/*for(int i = 0; i < 5; i++) {
			FileUploadDTO fileUpload  = new FileUploadDTO();
			fileUpload.setFileName("File " + (i+1));
			fileUpload.setMineType("image/jpg");
			fileUpload.setSortContent("The file upload interceptor also does the validation and adds errors, these error messages are stored in the struts-messsages.properties file. The values of the messages can be overridden by providing the text for the following keys");
			result.add(fileUpload);
		}*/
		
		PagerModel pagerModel = (PagerModel)ServletActionContext.getServletContext().getAttribute(Constants.PAGER_MODEL);
		pagerModel.setPageSize(Constants.PAGE_SIZE);
		pagerModel.setBaseLink("file.htm");
		pagerModel.setPageIndex(pageIndex);
		pagerModel.setTotalItems(result.getTotalItemCount());
		request.put("pager", pagerModel);
		request.put("listFiles",result);
		return Action.SUCCESS;
	}

	public String downloadFile() throws Exception {
		return Action.NONE;
	}
	
	public String deleteFile() throws Exception {
		return Action.SUCCESS;
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

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

}
