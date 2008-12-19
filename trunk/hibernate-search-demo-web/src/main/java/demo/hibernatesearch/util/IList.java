/*
 * Copyright (c) 2006 Pyramid Consulting. All rights reserved.
 * Created on Feb 20, 2006
 * $Id: IList.java,v 1.1 2007/11/16 01:47:26 tientrinh.nguyen Exp $
 *
 */
package demo.hibernatesearch.util;

import java.util.List;


public interface IList<E> extends List {

    public int getPageIndex();

    public int getPageSize();

    public boolean isFirstPage();

    public boolean isMiddlePage();

    public boolean isLastPage();

    public boolean isNextPageAvailable();

    public boolean isPreviousPageAvailable();

    public int getTotalPages();
    
    public int getTotalItemCount();
    
    public void setTotalItemCount(int totalItemCount);
    
    public void setList(List list);
    public List getList();
    
}
