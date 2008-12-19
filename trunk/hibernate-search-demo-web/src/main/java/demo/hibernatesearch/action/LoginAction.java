package demo.hibernatesearch.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.model.User;
import demo.hibernatesearch.service.ResumeManager;


public class LoginAction implements Preparable, SessionAware, RequestAware {
    
	@Autowired
	private ResumeManager resumeManager;
	private Map session;
	private Map request;
	private String email;

	public String validateUser() throws Exception {
		
	   User user = resumeManager.getUserByEmail(email);
	   if (user != null) {
		   
		   System.out.println("AAAAAAAAAa");
		   session.put("currentUser", user);
		   return Action.SUCCESS;
		   
	   } else {
		   return Action.NONE;
	   }
    }
	
	public String execute() throws Exception {
//		   List<Resume> listResume = resumeManager.getAllResum();
//		   ServletActionContext.getRequest().setAttribute("listResume", listResume);
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
	
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ResumeManager getResumeManager() {
		return resumeManager;
	}

	public void setResumeManager(ResumeManager resumeManager) {
		this.resumeManager = resumeManager;
	}

    
}
