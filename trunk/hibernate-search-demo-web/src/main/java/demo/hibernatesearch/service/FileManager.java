package demo.hibernatesearch.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;

import demo.hibernatesearch.model.FileAdvanceSearchDTO;
import demo.hibernatesearch.model.FileUploadDTO;
import demo.hibernatesearch.util.IList;
import demo.pyco.handler.FileHandlerException;

public interface FileManager {
	public void indexFile(FileUploadDTO file) throws Exception;

	public void deleteIndex(String docId) throws FileHandlerException,
			FileNotFoundException, IOException;

	public IList<FileUploadDTO> simpleSearch(int pageIndex, int pageSize,
			String searchString) throws ParseException, CorruptIndexException,
			IOException;

	public IList<FileUploadDTO> advanceSearch(final int pageIndex,
			final int pageSize, final FileAdvanceSearchDTO searchDTO)
			throws Exception;
}
