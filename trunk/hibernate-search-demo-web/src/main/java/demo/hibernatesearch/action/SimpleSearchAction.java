package demo.hibernatesearch.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import demo.hibernatesearch.application.Constants;
import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.model.User;
import demo.hibernatesearch.service.ResumeManager;
import demo.hibernatesearch.util.IList;
import demo.hibernatesearch.util.ListImpl;

public class SimpleSearchAction extends SearchAction {

	private String searchString;
	
	@Override
	public IList<Resume> search(int pageIndex, int pageSize) {
		
		User currentUser = (User)session.get(Constants.CURRENT_USER);
		IList<Resume> listResume = null;
		if(currentUser == null) {
			return resumeManager.simpleSearch(pageIndex > 0 ? pageIndex - 1 : pageIndex, pageSize, searchString);
		} else {
			return resumeManager.simpleSearchWithEmail(currentUser.getEmailAddress(), pageIndex > 0 ? pageIndex - 1 : pageIndex, pageSize, searchString);
		}
	}
	
	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

}
