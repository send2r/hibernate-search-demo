/*
* Copyright (c) 2007 Pyramid Consulting. All rights reserved.
* Created on Mar 26, 2007
* $Id: UIModel.java,v 1.1 2007/11/16 01:47:32 tientrinh.nguyen Exp $
*
*/
package demo.hibernatesearch.taglib;

import java.io.Serializable;

/**
 * <Briefly describing the purpose of the class/interface...>
 * @author $Author: tientrinh.nguyen $
 */
public class UIModel implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String id;
    protected String cssClass;

    public String getCssClass() {
        return cssClass;
    }
    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


}
