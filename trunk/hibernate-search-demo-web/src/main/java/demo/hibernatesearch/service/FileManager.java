package demo.hibernatesearch.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import demo.hibernatesearch.model.FileUploadDTO;
import demo.pyco.handler.FileHandlerException;

public interface FileManager {
	public void indexFile(FileUploadDTO file) throws Exception;
	
	public void deleteIndex(String docId) throws FileHandlerException,
	FileNotFoundException, IOException;
}
