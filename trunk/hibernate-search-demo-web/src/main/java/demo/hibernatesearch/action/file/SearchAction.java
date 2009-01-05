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

public abstract class SearchAction implements Preparable, SessionAware,
		RequestAware {
	
	@Autowired
	protected FileManager fileManager;
	
	protected Map session;
	protected Map request;
	protected String searchString;
	
	private String page;
	private String query;

	public abstract IList<FileUploadDTO> search(int pageIndex, int pageSize);

	public void prepare() throws Exception {
	}

	public String execute() throws Exception {

		int pageIndex;
		try {
			pageIndex = Integer.valueOf(this.page);
		} catch (Exception e) {
			pageIndex = 0;
		}
		IList<FileUploadDTO> searchList = search(pageIndex, Constants.PAGE_SIZE);
		PagerModel pagerModel = (PagerModel) ServletActionContext
				.getServletContext().getAttribute(Constants.PAGER_MODEL);
		pagerModel.setPageSize(Constants.PAGE_SIZE);
		pagerModel.setBaseLink("simple-search.htm");
		pagerModel.setPageIndex(pageIndex);
		pagerModel.setParams("searchString");
		pagerModel.setTotalItems(searchList.getTotalItemCount());
		request.put("searchString", searchList.getSearchString());
		searchString = searchList.getSearchString();
		request.put("listResume", searchList);
		request.put("pager", pagerModel);

		return Action.SUCCESS;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public Map getRequest() {
		return request;
	}

	public void setRequest(Map request) {
		this.request = request;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
	
	public FileManager getFileManager() {
		return fileManager;
	}

	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}


}
