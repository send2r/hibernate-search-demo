/*
 * Copyright (c) 2007 Pyramid Consulting. All rights reserved. Created on Mar
 * 19, 2007 $Id: PagerModel.java,v 1.1 2007/11/16 01:47:34 tientrinh.nguyen Exp $
 *
 */
package demo.hibernatesearch.taglib.pager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import demo.hibernatesearch.taglib.UIModel;

/**
 * <Briefly describing the purpose of the class/interface...>
 *
 * @author $Author: tientrinh.nguyen $
 */
public class PagerModel extends UIModel {
    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_PAGE_INDEX = 1;
    public static final int DEFAULT_MAX_PAGE_INDEX = 10;
    public static final int DEFAULT_MAX_LAST_PAGE_INDEX = 2;
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int DEFAULT_TOTAL_ITEMS = 0;
    public static final String DEFAULT_ITEM_NAME = "Item";
    public static final String DEFAULT_INFO = "Displaying #itemName# #startItemIndex# to #endItemIndex# of #totalItems# #itemName#(s) found.";
    public static final String DEFAULT_LAYOUT = "info-first-previous-current-next-last-goto";
    public static final String DEFAULT_FIRST_TEXT = "|<";
    public final String DEFAULT_PREVIOUS_TEXT = "<";
    public static final String DEFAULT_NEXT_TEXT = ">";
    public static final String DEFAULT_LAST_TEXT = ">|";
    public static final String DEFAULT_GOTO_TEXT = "go";
    public static final String DEFAULT_PAGE_INDEX_TEXT = "page";
    public static final String DEFAULT_PAGE_SIZE_TEXT = "size";
    public static final String DEFAULT_CSS_CLASS = "Pager";
    public static final String DEFAULT_INVALID_PAGE_INPUT_ALERT = "Please input page in the range 1 to #totalPages#.";
    public static final String DEFAULT_NO_ITEM_TEXT = "No #itemName# in list";

    public static final String HIDDEN_MODE = "hidden";
    public static final String DISABLE_MODE = "disable";

    public static final String POST_METHOD = "post";
    public static final String GET_METHOD = "get";

    private String baseLink;
    private int pageIndex;
    private int pageSize;
    private int totalItems;
    private String layout;
    private String params;
    private String builtParams;
    private int maxPageIndex;
    private int maxLastPageIndex;

    private String info;
    private String info1;
    private String info2;
    private String info3;
    private String info4;
    private String info5;
    private String itemName;
    private String noItemText;
    private String firstText;
    private String previousText;
    private String nextText;
    private String lastText;
    private String gotoText;
    private String pageIndexText;
    private String pageSizeText;
    private String invalidPageInputAlert;
    private String directionNotAvaibleMode;
    private boolean visiblePageSize;
    private String method;
    private boolean usingAJAX;
    private String objectAJAX;
    
	public PagerModel() {
        baseLink = "/";
        pageIndex = DEFAULT_PAGE_INDEX;
        pageSize = DEFAULT_PAGE_SIZE;
        totalItems = 0;
        maxPageIndex = DEFAULT_MAX_PAGE_INDEX;
        maxLastPageIndex = DEFAULT_MAX_LAST_PAGE_INDEX;
        layout = DEFAULT_LAYOUT;

        info = DEFAULT_INFO;
        info1 = null;
        info2 = null;
        info3 = null;
        info4 = null;
        info5 = null;
        itemName = DEFAULT_ITEM_NAME;
        noItemText = DEFAULT_NO_ITEM_TEXT;
        firstText = DEFAULT_FIRST_TEXT;
        previousText = DEFAULT_PREVIOUS_TEXT;
        nextText = DEFAULT_NEXT_TEXT;
        lastText = DEFAULT_LAST_TEXT;
        gotoText = DEFAULT_GOTO_TEXT;
        pageIndexText = DEFAULT_PAGE_INDEX_TEXT;
        pageSizeText = DEFAULT_PAGE_SIZE_TEXT;
        cssClass = DEFAULT_CSS_CLASS;
        invalidPageInputAlert = DEFAULT_INVALID_PAGE_INPUT_ALERT;
        directionNotAvaibleMode = DISABLE_MODE;
        visiblePageSize = false;
        usingAJAX = true;
        objectAJAX = "";
        method = GET_METHOD;
    }

    public String getBaseLink() {
        return baseLink;
    }

    public void setBaseLink(String baseLink) {
        this.baseLink = baseLink;
    }

    public String getDirectionNotAvaibleMode() {
        return directionNotAvaibleMode;
    }

    public void setDirectionNotAvaibleMode(String directionNotAvaibleMode) {
        if (!directionNotAvaibleMode.equalsIgnoreCase(HIDDEN_MODE)
                && !directionNotAvaibleMode.equalsIgnoreCase(DISABLE_MODE)) {
            directionNotAvaibleMode = DISABLE_MODE;
        }
        this.directionNotAvaibleMode = directionNotAvaibleMode;
    }

    public String getFirstText() {
        return firstText;
    }

    public void setFirstText(String firstText) {
        this.firstText = firstText;
    }

    public String getGotoText() {
        return gotoText;
    }

    public void setGotoText(String gotoText) {
        this.gotoText = gotoText;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getInfo3() {
        return info3;
    }

    public void setInfo3(String info3) {
        this.info3 = info3;
    }

    public String getInfo4() {
        return info4;
    }

    public void setInfo4(String info4) {
        this.info4 = info4;
    }

    public String getInfo5() {
        return info5;
    }

    public void setInfo5(String info5) {
        this.info5 = info5;
    }

    public String getInvalidPageInputAlert() {
        return invalidPageInputAlert;
    }

    public void setInvalidPageInputAlert(String invalidPageInputAlert) {
        this.invalidPageInputAlert = invalidPageInputAlert;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getLastText() {
        return lastText;
    }

    public void setLastText(String lastText) {
        this.lastText = lastText;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public int getMaxPageIndex() {
        return maxPageIndex;
    }

    public void setMaxPageIndex(int maxPageIndex) {
        this.maxPageIndex = maxPageIndex;
    }

    public int getMaxLastPageIndex() {
        return maxLastPageIndex;
    }

    public void setMaxLastPageIndex(int maxLastPageIndex) {
        this.maxLastPageIndex = maxLastPageIndex;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        if (!method.equalsIgnoreCase(GET_METHOD) && !method.equalsIgnoreCase(POST_METHOD)) {
            method = GET_METHOD;
        }
        this.method = method;
    }

    public String getNextText() {
        return nextText;
    }

    public void setNextText(String nextText) {
        this.nextText = nextText;
    }

    public String getNoItemText() {
        return noItemText;
    }

    public void setNoItemText(String noItemText) {
        this.noItemText = noItemText;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPageIndexText() {
        return pageIndexText;
    }

    public void setPageIndexText(String pageIndexText) {
        this.pageIndexText = pageIndexText;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageSizeText() {
        return pageSizeText;
    }

    public void setPageSizeText(String pageSizeText) {
        this.pageSizeText = pageSizeText;
    }

    public String getBuiltParams() {
        return builtParams;
    }

    public void setBuiltParams(String builtParams) {
        this.builtParams = builtParams;
    }

    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getPreviousText() {
        return previousText;
    }

    public void setPreviousText(String previousText) {
        this.previousText = previousText;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public boolean isVisiblePageSize() {
        return visiblePageSize;
    }

    public void setVisiblePageSize(boolean visiblePageSize) {
        this.visiblePageSize = visiblePageSize;
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

	public int getTotalPages() {
        int remainder = (this.totalItems % this.pageSize == 0) ? 0 : 1;
        return this.totalItems / this.pageSize + remainder;
    }

    @Override
    public PagerModel clone() {
        PagerModel cpyPager = new PagerModel();

        cpyPager.baseLink = this.baseLink;
        cpyPager.params = this.params;
        cpyPager.pageIndex = this.pageIndex;
        cpyPager.pageSize = this.pageSize;
        cpyPager.totalItems = this.totalItems;
        cpyPager.maxPageIndex = this.maxPageIndex;
        cpyPager.maxLastPageIndex = this.maxLastPageIndex;
        cpyPager.layout = this.layout;

        cpyPager.info = this.info;
        cpyPager.info1 = this.info1;
        cpyPager.info2 = this.info2;
        cpyPager.info3 = this.info3;
        cpyPager.info4 = this.info4;
        cpyPager.info5 = this.info5;
        cpyPager.itemName = this.itemName;
        cpyPager.noItemText = this.noItemText;
        cpyPager.firstText = this.firstText;
        cpyPager.previousText = this.previousText;
        cpyPager.nextText = this.nextText;
        cpyPager.lastText = this.lastText;
        cpyPager.gotoText = this.gotoText;
        cpyPager.pageIndexText = this.pageIndexText;
        cpyPager.pageSizeText = this.pageSizeText;
        cpyPager.cssClass = this.cssClass;
        cpyPager.invalidPageInputAlert = this.invalidPageInputAlert;
        cpyPager.directionNotAvaibleMode = this.directionNotAvaibleMode;
        cpyPager.visiblePageSize = this.visiblePageSize;
        cpyPager.method = this.method;
        cpyPager.usingAJAX = this.usingAJAX;
        cpyPager.objectAJAX = this.objectAJAX;
        return cpyPager;
    }

    public List<String> getLayoutElements() {
        if(layout == null) {
            return new ArrayList<String>();
        }

        layout = layout.toLowerCase();
        String[] elements = layout.split("-");
        return Arrays.asList(elements);
    }

    public boolean isNextPageAvailable() {
        return pageIndex != getTotalPages();
    }

    public boolean isPreviousPageAvailable() {
        return pageIndex != 1;
    }

    public int getEndItemIndex() {
        int endIndex = pageIndex * pageSize;
        if (endIndex > totalItems)
            endIndex = totalItems;
        return endIndex;
    }

    public int getStartItemIndex() {
        return (pageIndex - 1) * pageSize + 1;
    }

    public void validIndexAndSize() {
        if(pageSize <= 0) {
            pageSize = 1;
        }

        int totalPages = getTotalPages();
        if(pageIndex <= 0) {
            pageIndex = 1;
        } else if (pageIndex > totalPages) {
            pageIndex = totalPages;
        }
    }
}
