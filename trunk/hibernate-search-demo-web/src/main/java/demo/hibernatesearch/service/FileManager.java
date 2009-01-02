package demo.hibernatesearch.service;

import demo.hibernatesearch.model.FileUploadDTO;

public interface FileManager {
	public void indexFile(FileUploadDTO file) throws Exception;
}
