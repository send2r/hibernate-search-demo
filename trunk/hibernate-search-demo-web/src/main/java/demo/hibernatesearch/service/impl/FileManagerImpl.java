package demo.hibernatesearch.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.WildcardQuery;
import org.hibernate.search.jpa.FullTextQuery;
import org.springframework.stereotype.Service;

import demo.hibernatesearch.application.ManagerResource;
import demo.hibernatesearch.model.FileUploadDTO;
import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.service.FileManager;
import demo.hibernatesearch.util.IList;
import demo.hibernatesearch.util.ListImpl;
import demo.hibernatesearch.util.Utils;
import demo.pyco.handler.FileHandler;
import demo.pyco.handler.FileHandlerException;

@Service("fileManager")
public class FileManagerImpl implements FileManager {

	protected FileHandler fileHandler;

	public void indexFile(FileUploadDTO file) throws Exception {

		saveFile(file);
		file.setDocId(Utils.getDocumentId());
		addIndex(file);
	}

	public File saveFile(FileUploadDTO file) throws Exception {
		// TODO Auto-generated method stub
		String fullFileName = ManagerResource.getUploadFolder() + "/"
				+ file.getFileName();
		File theFile = new File(fullFileName);
		FileUtils.copyFile(file.getFileUpload(), theFile);
		file.setFileUpload(theFile);
		return theFile;
	}

	public void addIndex(FileUploadDTO fileDTO) throws FileHandlerException,
			FileNotFoundException, IOException {

		String indexPath = ManagerResource.getFileIndexFolder();
		fileHandler = ManagerResource.getFileHandler();
		IndexWriter writer = new IndexWriter(indexPath, new StandardAnalyzer(),
				false);
		File file = fileDTO.getFileUpload();
		if (file.canRead()) {

			System.out.println("Indexing " + file);
			try {
				Document doc = fileHandler.getDocument(file);
				if (doc != null) {
					
					String fileName = file.getName();
					String docId = fileDTO.getDocId();
					doc.add(new Field("filename", fileName, Field.Store.YES,
							Field.Index.UN_TOKENIZED));
					doc.add(new Field("id", docId, Field.Store.YES,
							Field.Index.UN_TOKENIZED));
					doc.add(new Field("lastUpdated", Utils.getDateString(), Field.Store.YES,
							Field.Index.UN_TOKENIZED));
					writer.addDocument(doc);
				} else {
					System.err.println("Cannot handle "
							+ file.getAbsolutePath() + "; skipping");
				}
			} catch (IOException e) {
				System.err.println("Cannot index " + file.getAbsolutePath()
						+ "; skipping (" + e.getMessage() + ")");
			}
			writer.optimize();
			writer.close();
		}
	}

	public void deleteIndex(String docId) throws FileHandlerException,
			FileNotFoundException, IOException {

		String indexPath = ManagerResource.getFileIndexFolder();
		IndexReader reader = IndexReader.open(indexPath);
		reader.deleteDocuments(new Term("id", docId));
		reader.close();
	}
	
	public IList<FileUploadDTO> simpleSearch(int pageIndex, int pageSize, String searchString) throws ParseException, CorruptIndexException, IOException {
		
		List result = new LinkedList();
		IList pageList;
		String indexPath = ManagerResource.getFileIndexFolder();   
		IndexSearcher is = new IndexSearcher(indexPath);
		org.apache.lucene.search.Query query;
		if ("".equals(searchString) || "*".equals(searchString)) {
			query = new WildcardQuery(new Term("id","*"));
		} else {
			MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[]{"filename", "body"}, new StandardAnalyzer());
			query = parser.parse(searchString);
		}
		
		Hits hits = is.search(query);
		int resultSize = hits.length();
		int firstResult = pageIndex*pageSize;
		int lastResult = firstResult + pageSize;
		for (int i = firstResult; i < lastResult && i < resultSize; i++) {
		      Document doc = hits.doc(i);
		      FileUploadDTO file = new FileUploadDTO();
		      file.setDocId(doc.get("id"));
		      file.setFileName(doc.get("filename"));
		      file.setContent(doc.getBinaryValue("body"));
		      result.add(file);
		}
				    

		pageList = new ListImpl(resultSize, pageIndex, pageSize);
		pageList.setList(result);
		pageList.setSearchString(searchString);
		return pageList;
        
	}

}
