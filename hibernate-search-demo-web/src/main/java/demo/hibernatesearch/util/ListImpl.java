/*
 * Copyright (c) 2006 Pyramid Consulting. All rights reserved.
 * Created on Feb 20, 2006
 * $Id: ListImpl.java,v 1.1 2007/11/16 01:47:25 tientrinh.nguyen Exp $
 *
 */
package demo.hibernatesearch.util;

import java.io.Serializable;
import java.util.*;

/**
 * <Briefly describing the purpose of the class/interface...>
 * 
 * @author $Author: tientrinh.nguyen $
 */
public class ListImpl implements IList, Serializable {

	public static final long serialVersionUID = 0;

	private List list;

	private int totalItems;

	private int pageIndex;

	private int pageSize;

	public ListImpl() {
		this(new ArrayList(), 0, 0, 0);
	}

	public ListImpl(int totalItems, int pageIndex, int pageSize) {
		this.list = new ArrayList();
		this.totalItems = totalItems;

		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		validParams();

	}

	private void validParams() {
		if (this.pageSize <= 0)
			this.pageSize = 1;

		if (this.pageIndex < 0)
			this.pageIndex = 0;
		else if (this.pageIndex >= getTotalPages())
			this.pageIndex = getTotalPages() - 1;
	}

	public ListImpl(List list, int totalItems, int pageIndex, int pageSize) {
		this.list = list;
		this.totalItems = totalItems;

		validParams();

		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public boolean isFirstPage() {
		return (pageIndex == 0);
	}

	public boolean isMiddlePage() {
		return !(isFirstPage() || isLastPage());
	}

	public boolean isLastPage() {
		return (pageIndex == getTotalPages() - 1);
	}

	public boolean isNextPageAvailable() {
		return !isLastPage();
	}

	public boolean isPreviousPageAvailable() {
		return !isFirstPage();
	}

	public int getTotalPages() {
		int remainder = (this.totalItems % this.pageSize == 0) ? 0 : 1;

		return this.totalItems / this.pageSize + remainder;
	}

	public void setTotalItemCount(int totalItemCount) {
		this.totalItems = totalItemCount;
	}

	public int getTotalItemCount() {
		return this.totalItems;
	}

	public int size() {
		return this.list.size();
	}

	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	public boolean contains(Object o) {
		return this.list.contains(o);
	}

	public Iterator iterator() {
		return this.list.iterator();
	}

	public Object[] toArray() {
		return this.list.toArray();
	}

	public Object[] toArray(Object[] arg0) {
		return this.list.toArray(arg0);
	}

	public boolean add(Object arg0) {
		return this.list.add(arg0);
	}

	public boolean remove(Object o) {
		return this.list.remove(o);
	}

	public boolean containsAll(Collection arg0) {
		return this.list.containsAll(arg0);
	}

	public boolean addAll(Collection arg0) {
		return this.list.addAll(arg0);
	}

	public boolean addAll(int arg0, Collection arg1) {
		return this.list.addAll(arg0, arg1);
	}

	public boolean removeAll(Collection arg0) {
		return this.list.removeAll(arg0);
	}

	public boolean retainAll(Collection arg0) {
		return this.list.retainAll(arg0);
	}

	public void clear() {
		this.list.clear();
	}

	public Object get(int index) {
		return this.list.get(index);
	}

	public Object set(int arg0, Object arg1) {
		return this.list.set(arg0, arg1);
	}

	public void add(int arg0, Object arg1) {
		this.list.add(arg0, arg1);
	}

	public Object remove(int index) {
		return this.list.remove(index);
	}

	public int indexOf(Object o) {
		return this.list.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return this.list.lastIndexOf(o);
	}

	public ListIterator listIterator() {
		return this.list.listIterator();
	}

	public ListIterator listIterator(int index) {
		return this.list.listIterator(index);
	}

	public List subList(int fromIndex, int toIndex) {
		return this.list.subList(fromIndex, toIndex);
	}

}
