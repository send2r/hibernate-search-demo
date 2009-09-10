package demo.hibernatesearch.action.file;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.queryParser.ParseException;
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
	
	private static final int BUFSIZE = 1024;

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

	public String downloadFile() throws IOException, ParseException {
		
		HttpServletResponse response = ServletActionContext.getResponse();
		ServletOutputStream outputStr       = response.getOutputStream();
		String fileName = fileManager.getFilePathById(docId);
		if (!"".equals(fileName)) {
			String downloadFileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
			downloadFileName = downloadFileName.replaceAll(" ", "_");
			response.setContentType("application/octet-stream");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Content-Disposition", "attachment;filename=" + downloadFileName);
			DataInputStream inputStr = null;
			try {
				byte[] bbuf = new byte[BUFSIZE];
		        inputStr = new DataInputStream(new FileInputStream(fileName));
		        int length = 0;
		        while ((inputStr != null) && ((length = inputStr.read(bbuf)) != -1)) {
		        	outputStr.write(bbuf,0,length);
		        }
			} catch (IOException ioe) {
				// TODO: handle exception
			} finally {
				inputStr.close();
		        outputStr.flush();
		        outputStr.close();
			}
		}
		return Action.NONE;
	}
	
	public String deleteFile() throws Exception {
		fileManager.deleteIndex(docId);
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
