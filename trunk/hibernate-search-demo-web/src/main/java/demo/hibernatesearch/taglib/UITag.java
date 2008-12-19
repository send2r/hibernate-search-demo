/*
 * Copyright (c) 2007 Pyramid Consulting. All rights reserved. Created on Jan
 * 30, 2007 $Id: UITag.java,v 1.1 2007/11/16 01:47:32 tientrinh.nguyen Exp $
 *
 */
package demo.hibernatesearch.taglib;

import java.io.Writer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * <Briefly describing the purpose of the class/interface...>
 *
 * @author $Author: tientrinh.nguyen $
 */
public abstract class UITag<M extends UIModel> extends TagSupport {
    private static final long serialVersionUID = 1L;

    protected String templateConfig;
    protected String dataModel;
    protected String templateName;
    protected String cssClass;

    public String getTemplateConfig() {
        return templateConfig;
    }

    public void setTemplateConfig(String templateConfig) {
        this.templateConfig = templateConfig;
    }

    public String getDataModel() {
        return dataModel;
    }

    public void setDataModel(String dataModel) {
        this.dataModel = dataModel;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    @Override
    public int doStartTag() throws JspException {
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        M model = getModel();
        if (model == null) {
            return SKIP_BODY;
        }
        try {
            Configuration config = (Configuration) getAtrtribute(templateConfig);
            Template template = config.getTemplate(templateName, "utf-8");

            Writer writer = pageContext.getOut();
            template.process(model, writer);
            writer.flush();
        } catch (Exception ex) {
            throw new JspException("Cannot process to render HTML from FreeMarker template: " + templateName, ex);
        }
        return EVAL_PAGE;
    }

    @Override
    public void release() {
        dataModel = null;
    }

    @SuppressWarnings("unchecked")
    public M getModel() {
        return (M) getAtrtribute(dataModel);
    }

    protected Object getAtrtribute(String attr) {
        Object value = null;
        // page scope
        value = pageContext.getAttribute(attr);
        if (value == null) {
            // request scope
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            value = request.getAttribute(attr);
            if (value == null) {
                // session scope
                HttpSession session = request.getSession();
                value = session.getAttribute(attr);
                if (value == null) {
                    // application scope
                    ServletContext application = session.getServletContext();
                    value = application.getAttribute(attr);
                }
            }
        }
        return value;
    }
}
