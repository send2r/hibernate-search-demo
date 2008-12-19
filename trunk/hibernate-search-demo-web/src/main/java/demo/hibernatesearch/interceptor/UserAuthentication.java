package demo.hibernatesearch.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.dispatcher.SessionMap;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import demo.hibernatesearch.application.Constants;
import demo.hibernatesearch.model.User;

public class UserAuthentication implements Interceptor {
	private static Log log = LogFactory.getLog(UserAuthentication.class);
	public void destroy() {
	}
	public void init() {
	}
	public String intercept(ActionInvocation invocation) throws Exception {
		log.info("[User Authentication");
		SessionMap session = (SessionMap) ActionContext.getContext().get(
				ActionContext.SESSION);
		User user = (User) session.get(Constants.CURRENT_USER);
		if (user == null) {
			return Action.LOGIN;
		}
		return invocation.invoke();
	}
}
