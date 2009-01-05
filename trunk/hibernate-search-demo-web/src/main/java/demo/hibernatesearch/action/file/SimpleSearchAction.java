package demo.hibernatesearch.action.file;


import demo.hibernatesearch.application.Constants;
import demo.hibernatesearch.model.FileUploadDTO;
import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.model.User;
import demo.hibernatesearch.util.IList;

public class SimpleSearchAction extends SearchAction {

	@Override
	public IList<FileUploadDTO> search(int pageIndex, int pageSize) {
		User currentUser = (User)session.get(Constants.CURRENT_USER);
		IList<Resume> listResume = null;
		if(currentUser == null) {
			return null;
		} else {
			return null;
		}

	}
}
