package demo.hibernatesearch.action.file;


import demo.hibernatesearch.application.Constants;
import demo.hibernatesearch.model.FileUploadDTO;
import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.model.User;
import demo.hibernatesearch.util.IList;

public class SimpleSearchAction extends SearchAction {

	@Override
	public IList<FileUploadDTO> search(int pageIndex, int pageSize) throws Exception {
		
		IList<FileUploadDTO> result = fileManager.simpleSearch(pageIndex > 0
				? pageIndex - 1
						: pageIndex, Constants.PAGE_SIZE, searchString);
		
		return result;
	}
}
