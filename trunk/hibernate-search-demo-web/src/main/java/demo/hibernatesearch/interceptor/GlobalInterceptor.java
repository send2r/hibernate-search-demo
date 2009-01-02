/*
* Copyright (c) 2007 Pyramid Consulting. All rights reserved.
* Created on Apr 9, 2007
* $Id: GlobalInterceptor.java,v 1.2 2008/01/23 08:39:02 son.nguyencong Exp $
*
*/
package demo.hibernatesearch.interceptor;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import demo.hibernatesearch.application.Constants;
import demo.hibernatesearch.application.ManagerResource;

/**
 * <Briefly describing the purpose of the class/interface...>
 * @author $Author: son.nguyencong $
 */
public class GlobalInterceptor extends AbstractInterceptor {
    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(GlobalInterceptor.class);

	@Override
    public String intercept(ActionInvocation invocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        extractRequestPath(request);
        return invocation.invoke();
    }

    private String extractRequestPath(HttpServletRequest request) {
    	  String contextPath = request.getContextPath();
          String servletPath = request.getServletPath();
          String pathInfo = request.getPathInfo();
          String query = request.getQueryString();
          //Checking to query the navigation of user
          Hashtable<String, String> navigations =  ManagerResource.getNavigations();
          Enumeration<String> enumeration = navigations.keys();
          Pattern pattern;
          Matcher matcher;
          String currentSection = "";
          while (enumeration.hasMoreElements()) {
          	String key = (String) enumeration.nextElement();
          	String regValue = (String) navigations.get(key);
          	pattern = Pattern.compile(regValue);
          	matcher = pattern.matcher(servletPath);
          	if (matcher.matches()) {
          		currentSection = key;
          		break;
          	}
          }
          request.setAttribute(Constants.NAVIGATION, currentSection);

          return (contextPath == null ? "" : contextPath)
                  + (servletPath == null ? "" : servletPath)
                  + (pathInfo == null ? "" : pathInfo)
                  + (query == null ? "" : ("?" + query));
    }
}
