package demo.hibernatesearch.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import demo.hibernatesearch.application.Constants;
import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.model.User;
import demo.hibernatesearch.service.ResumeManager;

public class NewResumeAction extends ActionSupport implements SessionAware, RequestAware {

	private static final long serialVersionUID = -4954716886990860703L;

	@Autowired
	private ResumeManager resumeManager;
	private Map session;
	private Map request;
	private String summary;
	private String content;
	private String id;
	private String page;
	private String[] listDelete;
	
	public String[] getListDelete() {
		return listDelete;
	}

	public void setListDelete(String[] listDelete) {
		this.listDelete = listDelete;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String sayHello() throws Exception {

		System.out.println("This is Tin Tran's code");
		return Action.SUCCESS;
	}

	public String addResume() throws Exception{
		return Action.SUCCESS;
	}
	
	public String execute() throws Exception {

		Resume resume = new Resume();
		resume.setSummary(summary);
		resume.setContent(content.getBytes("utf-8"));
		resume.setLastUpdated(new Date());
		User user = (User)session.get(Constants.CURRENT_USER);
		resume.setApplicant(user);
		resumeManager.saveResume(resume);
		return Action.SUCCESS;
	}
	
	public String editResume() throws Exception{
		if(id == null){
			return Action.INPUT;
		}
		Resume resume = resumeManager.getResume(Long.valueOf(id));
		request.put("page", page);
		request.put("resume", resume);
		return Action.SUCCESS;
	}
	public String saveEditResume() throws Exception {
		System.out.println(" saveEditResume >>>>>>>>>>>>>>>>>>>" + id);
		Resume resume = new Resume();
		resume.setId(Long.valueOf(id));
		resume.setSummary(summary);
		resume.setContent(content.getBytes("utf-8"));
		resume.setLastUpdated(new Date());
		User user = (User)session.get(Constants.CURRENT_USER);
		resume.setApplicant(user);
		resumeManager.updateResume(resume);
		return Action.SUCCESS;
	}
	
	public String deleteResume() throws Exception{
		System.out.println(this.listDelete);
		if(listDelete != null){
			for(String resumeId: this.listDelete){
				Resume resume = new Resume();
				resume.setId(Long.valueOf(resumeId));
				resumeManager.deleteResume(resume);
			}
		}
		return Action.SUCCESS;
	}
	
	public void prepare() throws Exception {
		// TODO Auto-generated method stub

	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ResumeManager getResumeManager() {
		return resumeManager;
	}

	public void setResumeManager(ResumeManager resumeManager) {
		this.resumeManager = resumeManager;
	}

	public void setSession(Map session) {
		this.session = session;
		
	}

	public void setRequest(Map request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

}
