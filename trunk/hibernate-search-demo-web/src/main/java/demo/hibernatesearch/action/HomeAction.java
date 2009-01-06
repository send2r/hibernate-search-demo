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
import demo.hibernatesearch.model.User;
import demo.hibernatesearch.service.ResumeManager;
import demo.hibernatesearch.taglib.pager.PagerModel;
import demo.hibernatesearch.util.IList;
import demo.hibernatesearch.util.Utils;

public class HomeAction implements Preparable, SessionAware, RequestAware {

	@Autowired
	private ResumeManager resumeManager;
	private Map session;
	private Map request;
	private String page;
	private String sortField;
	private String order;

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

	public ResumeManager getResumeManager() {
		return resumeManager;
	}

	public void setResumeManager(ResumeManager resumeManager) {
		this.resumeManager = resumeManager;
	}

	public String execute() throws Exception {
		
		int pageIndex;
		try{
			pageIndex = Integer.valueOf(this.page);
		}catch(Exception e){
			pageIndex = 0;
		}
		
		User currentUser = (User)session.get(Constants.CURRENT_USER);
		IList<Resume> listResume = null;
		if(currentUser == null){
		
			/*listResume = resumeManager.getAllResum(pageIndex > 0
						? pageIndex - 1
						: pageIndex, Constants.PAGE_SIZE);*/
			boolean reverse = "ASC".equals(order) ? false: true;
			String sortFieldT = Utils.convertSortField(sortField);
			listResume = resumeManager.getAllResumAndSort(pageIndex > 0
					? pageIndex - 1
					: pageIndex, Constants.PAGE_SIZE, sortFieldT, reverse);
		} else {
			listResume = resumeManager.seFindResumesForUserWithPagination(currentUser.getEmailAddress(),pageIndex > 0
					? pageIndex - 1
							: pageIndex, Constants.PAGE_SIZE);
		}
		
		request.put("listResume", listResume);
		PagerModel pagerModel = (PagerModel)ServletActionContext.getServletContext().getAttribute(Constants.PAGER_MODEL);
		pagerModel.setPageSize(Constants.PAGE_SIZE);
		pagerModel.setParams("order, sortField");
		pagerModel.setBaseLink("home.htm");
		pagerModel.setPageIndex(pageIndex);
		pagerModel.setTotalItems(listResume.getTotalItemCount());
		request.put("listResume", listResume);
		request.put("pager", pagerModel);
		return Action.SUCCESS;
	}

	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		if(order == null || "".equals(order)){
			order = "ASC";
		}
		if(sortField == null || "".equals(sortField)){
			sortField = "Email";
		}
	}
	
	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setSession(Map session) {
		this.session = session;

	}

	public void setRequest(Map request) {
		this.request = request;

	}

}
