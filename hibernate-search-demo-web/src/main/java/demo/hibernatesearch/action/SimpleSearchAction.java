package demo.hibernatesearch.action;

import org.springframework.beans.factory.annotation.Autowired;

import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.service.ResumeManager;
import demo.hibernatesearch.util.IList;
import demo.hibernatesearch.util.ListImpl;

public class SimpleSearchAction extends SearchAction {

	@Autowired
	private ResumeManager resumeManager;
	
	private String searchString;
	
	@Override
	public IList<Resume> search(int pageIndex, int pageSize) {

		return resumeManager.simpleSearch(pageIndex, pageSize, searchString);

	}
	
	public ResumeManager getResumeManager() {
		return resumeManager;
	}

	public void setResumeManager(ResumeManager resumeManager) {
		this.resumeManager = resumeManager;
	}
	
	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

}
