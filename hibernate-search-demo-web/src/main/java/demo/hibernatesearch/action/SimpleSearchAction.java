package demo.hibernatesearch.action;


import demo.hibernatesearch.application.Constants;
import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.model.User;
import demo.hibernatesearch.util.IList;
import demo.hibernatesearch.util.Utils;

public class SimpleSearchAction extends SearchAction {

	@Override
	public IList<Resume> search(int pageIndex, int pageSize) {
		User currentUser = (User)session.get(Constants.CURRENT_USER);
		IList<Resume> listResume = null;
		if(currentUser == null) {
			boolean reverse = "ASC".equals(order) ? false: true;
			String sortFieldT = Utils.convertSortField(sortField);
			return resumeManager.simpleSearch(pageIndex > 0 ? pageIndex - 1 : pageIndex, pageSize, searchString, sortFieldT, reverse);
		} else {
			return resumeManager.simpleSearchWithEmail(pageIndex > 0 ? pageIndex - 1 : pageIndex, pageSize, searchString, currentUser.getEmailAddress());
		}

	}
}
