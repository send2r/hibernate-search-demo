package demo.hibernatesearch.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

import demo.hibernatesearch.service.ResumeManager;

public class HomeFilesAction implements Preparable, SessionAware, RequestAware {

	@Autowired
	private ResumeManager resumeManager;
	private Map session;
	private Map request;
	private String page;

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
		List<String> result = new ArrayList();
		for(int i = 0; i < 5; i++){
			result.add(new String(i + ""));
		}
		request.put("listFiles",result);
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

}
