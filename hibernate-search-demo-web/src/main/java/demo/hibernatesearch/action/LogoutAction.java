package demo.hibernatesearch.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

import demo.hibernatesearch.application.Constants;

public class LogoutAction implements Preparable, SessionAware {

	private Map session;

	public String execute() throws Exception {
		this.session.remove(Constants.CURRENT_USER);
		return Action.SUCCESS;
	}

	public void prepare() throws Exception {
		// TODO Auto-generated method stub

	}

	public void setSession(Map session) {
		this.session = session;

	}

}
