package demo.hibernatesearch.action;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

import demo.hibernatesearch.application.Constants;
import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.service.ResumeManager;
import demo.hibernatesearch.taglib.pager.PagerModel;
import demo.hibernatesearch.util.IList;
import demo.hibernatesearch.util.ListImpl;

public abstract class SearchAction implements Preparable, SessionAware,
		RequestAware {
	
	@Autowired
	protected ResumeManager resumeManager;
	
	protected Map session;
	protected Map request;
	private String page;
	private String query;

	public abstract IList<Resume> search(int pageIndex, int pageSize);

	public void prepare() throws Exception {
	}

	public String execute() throws Exception {

		int pageIndex;
		try {
			pageIndex = Integer.valueOf(this.page);
		} catch (Exception e) {
			pageIndex = 0;
		}
		IList<Resume> searchList = search(pageIndex, Constants.PAGE_SIZE);
		PagerModel pagerModel = (PagerModel) ServletActionContext
				.getServletContext().getAttribute(Constants.PAGER_MODEL);
		pagerModel.setPageSize(Constants.PAGE_SIZE);
		pagerModel.setBaseLink("simple-search.htm");
		pagerModel.setPageIndex(pageIndex);
		pagerModel.setParams("searchString");
		pagerModel.setTotalItems(searchList.getTotalItemCount());
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
	public ResumeManager getResumeManager() {
		return resumeManager;
	}

	public void setResumeManager(ResumeManager resumeManager) {
		this.resumeManager = resumeManager;
	}

}
