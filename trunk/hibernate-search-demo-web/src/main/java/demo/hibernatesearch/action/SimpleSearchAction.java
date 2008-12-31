package demo.hibernatesearch.action;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import demo.hibernatesearch.application.Constants;
import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.model.User;
import demo.hibernatesearch.util.IList;

public class SimpleSearchAction extends SearchAction {

	@Override
	public IList<Resume> search(int pageIndex, int pageSize) {
		User currentUser = (User)session.get(Constants.CURRENT_USER);
		IList<Resume> listResume = null;
		if(currentUser == null) {
			return resumeManager.simpleSearch(pageIndex > 0 ? pageIndex - 1 : pageIndex, pageSize, searchString);
		} else {
			return resumeManager.simpleSearchWithEmail(pageIndex > 0 ? pageIndex - 1 : pageIndex, pageSize, searchString, currentUser.getEmailAddress());
		}

	}
}
