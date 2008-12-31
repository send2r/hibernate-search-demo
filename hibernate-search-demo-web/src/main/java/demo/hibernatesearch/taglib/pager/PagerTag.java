/*
 * Copyright (c) 2007 Pyramid Consulting. All rights reserved. Created on Mar
 * 19, 2007 $Id: PagerTag.java,v 1.1 2007/11/16 01:47:34 tientrinh.nguyen Exp $
 *
 */
package demo.hibernatesearch.taglib.pager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.hibernatesearch.taglib.UITag;
import demo.hibernatesearch.util.Utils;

/**
 * <Briefly describing the purpose of the class/interface...>
 *
 * @author $Author: tientrinh.nguyen $
 */
public class PagerTag extends UITag<PagerModel> {
    private static final long serialVersionUID = 1L;

    private String baseLink;
    private int pageIndex;
    private int pageSize;
    private int totalItems;
    private boolean usingAJAX;
    private String objectAJAX; 
    private String layout;

    public PagerTag() {
        baseLink = null;
        pageIndex = -1;
        pageSize = -1;
        totalItems = -1;
        layout = null;
    }

    public String getBaseLink() {
        return baseLink;
    }

    public void setBaseLink(String baseLink) {
        this.baseLink = baseLink;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    @Override
    public PagerModel getModel() {
        PagerModel model = super.getModel();
        if (model == null) {
            model = new PagerModel();
        }
        if(id != null) {
            model.setId(id);
        }
        if (cssClass != null) {
            model.setCssClass(cssClass);
        }
        if (baseLink != null) {
            model.setBaseLink(baseLink);
        }
        if (pageIndex >= 0) {
            model.setPageIndex(pageIndex);
        }
        if (pageSize >= 0) {
            model.setPageSize(pageSize);
        }
        if (totalItems >= 0) {
            model.setTotalItems(totalItems);
        }
        if (layout != null) {
            model.setLayout(layout);
        }
        
       	model.setUsingAJAX(usingAJAX);
        if (objectAJAX != null) {
        	model.setObjectAJAX(objectAJAX);
        }
        
        // build params
        HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
        StringBuffer buff = new StringBuffer();
        String parames = model.getParams();
        if(parames != null){
        	String[] paramNames = model.getParams().split(",");
        	 for (int i = 0; i < paramNames.length; i++) {
                 String paramName = paramNames[i].trim();
                 String[] paramValues = request.getParameterValues(paramName);
                 if (paramValues != null) {
                     for (int j = 0; paramValues != null && j < paramValues.length; j++) {
                         try{
                        	 buff.append("&" + paramName + "=" + paramValues[j]);
                         }catch(Exception ex){
                             //buff.append("&" + paramName + "=" + response.encodeURL(Utils.convertFromISO88591ToUTF8(paramValues[j])));
                         }
                     }
                 } else if (request.getAttribute(paramName) != null) {
                	 String paramValue = (String) request.getAttribute(paramName);
                	 buff.append("&" + paramName + "=" + paramValue);
                 }
             }
             model.setBuiltParams(buff.toString());
        }
        model.validIndexAndSize();

        return model;
    }

	public String getObjectAJAX() {
		return objectAJAX;
	}

	public void setObjectAJAX(String objectAJAX) {
		this.objectAJAX = objectAJAX;
	}

	public boolean isUsingAJAX() {
		return usingAJAX;
	}

	public void setUsingAJAX(boolean usingAJAX) {
		this.usingAJAX = usingAJAX;
	}

}
