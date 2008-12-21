package demo.hibernatesearch.action;

import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.util.IList;
import demo.hibernatesearch.util.ListImpl;

public class SimpleSearchAction extends SearchAction{

	@Override
	public IList<Resume> search(int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		return new ListImpl();
	}

}
