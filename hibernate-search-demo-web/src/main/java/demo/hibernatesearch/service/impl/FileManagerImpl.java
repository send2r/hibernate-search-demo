package demo.hibernatesearch.service.impl;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.RangeQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.springframework.stereotype.Service;

import demo.hibernatesearch.application.ManagerResource;
import demo.hibernatesearch.model.FileAdvanceSearchDTO;
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
	
	String field = "body";

	public void indexFile(FileUploadDTO file) throws Exception {

		saveFile(file);
		addIndex(file);
	}

	public File saveFile(FileUploadDTO file) throws Exception {
		
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
					String docId = Utils.getDocumentId();
					String dateString = Utils.getDateString();
					String filePath = file.getAbsolutePath();
					doc.add(new Field("filename", fileName, Field.Store.YES,
							Field.Index.UN_TOKENIZED));
					doc.add(new Field("id", docId, Field.Store.YES,
							Field.Index.UN_TOKENIZED));
					doc.add(new Field("lastUpdated", dateString, Field.Store.YES,
							Field.Index.UN_TOKENIZED));
					doc.add(new Field("filePath", filePath, Field.Store.YES,
							Field.Index.UN_TOKENIZED));
					writer.addDocument(doc);
				} else {
					System.err.println("Cannot handle "
							+ file.getAbsolutePath() + "; skipping");
				}
			} catch (IOException e) {
				System.err.println("Cannot index " + file.getAbsolutePath()
						+ "; skipping (" + e.getMessage() + ")");
			} finally {
				writer.optimize();
				writer.close();
			}
		}
	}

	public void deleteIndex(String docId) throws FileHandlerException,
			FileNotFoundException, IOException {

		String indexPath = ManagerResource.getFileIndexFolder();
		IndexReader reader = IndexReader.open(indexPath);
		reader.deleteDocuments(new Term("id", docId));
		reader.close();
	}
	
	public void downloadFile(String docId) throws Exception {
		
//		String filePath = getFilePathById(docId);
//		
//		File                f        = new File(filename);
//        int                 length   = 0;
//        ServletOutputStream op       = resp.getOutputStream();
//        ServletContext      context  = getServletConfig().getServletContext();
//        String              mimetype = context.getMimeType( filename );
//
//        //
//        //  Set the response and go!
//        //
//        //
//        resp.setContentType( (mimetype != null) ? mimetype : "application/octet-stream" );
//        resp.setContentLength( (int)f.length() );
//        resp.setHeader( "Content-Disposition", "attachment; filename=\"" + original_filename + "\"" );
//
//        //
//        //  Stream to the requester.
//        //
//        byte[] bbuf = new byte[BUFSIZE];
//        DataInputStream in = new DataInputStream(new FileInputStream(f));
//
//        while ((in != null) && ((length = in.read(bbuf)) != -1))
//        {
//            op.write(bbuf,0,length);
//        }
//
//        in.close();
//        op.flush();
//        op.close();
        

	}
	
	public String getFilePathById(String docId) throws ParseException, CorruptIndexException, IOException {
		
		String filePath = "";	
		String indexPath = ManagerResource.getFileIndexFolder();   
		IndexSearcher is = new IndexSearcher(indexPath);
		org.apache.lucene.search.Query query;
		query = new TermQuery(new Term("id", docId));
		Hits hits = is.search(query);
		if (hits.length() > 0) {
			
			Document doc = hits.doc(0);
			filePath = doc.get("filePath");	
		}
		return filePath;
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
		      file.setContent(doc.get("body"));
		      result.add(file);
		}
				    

		pageList = new ListImpl(resultSize, pageIndex, pageSize);
		pageList.setList(result);
		pageList.setSearchString(searchString);
		return pageList;
        
	}

	public IList<FileUploadDTO> advanceSearch(final int pageIndex, final int pageSize,final FileAdvanceSearchDTO searchDTO) throws Exception {
		
		List result = new LinkedList();
		IList pageList;
		String indexPath = ManagerResource.getFileIndexFolder();   
		IndexSearcher is = new IndexSearcher(indexPath);
		BooleanQuery finalQuery = buildQuey(searchDTO);
		Hits hits = is.search(finalQuery);
		int resultSize = hits.length();
		int firstResult = pageIndex*pageSize;
		int lastResult = firstResult + pageSize;
		for (int i = firstResult; i < lastResult && i < resultSize; i++) {
		      Document doc = hits.doc(i);
		      FileUploadDTO file = new FileUploadDTO();
		      file.setDocId(doc.get("id"));
		      file.setFileName(doc.get("filename"));
		      file.setContent(doc.get("body"));
		      result.add(file);
		}

		pageList = new ListImpl(resultSize, pageIndex, pageSize);
		pageList.setList(result);
		pageList.setSearchString(finalQuery.toString());
		return pageList;
	}
	
	private BooleanQuery buildQuey(FileAdvanceSearchDTO searchDTO) {
		
		
		BooleanQuery finalQuery = new BooleanQuery();
		String docType = searchDTO.getDocType();
		if (!"all".equals(docType)) {
			finalQuery.add(new TermQuery(new Term("docType",docType)), BooleanClause.Occur.MUST);
		}
		
		if (searchDTO.getWordPhrase() != null && !"".equals(searchDTO.getWordPhrase().trim())) {
			
			String[] phrase = searchDTO.getWordPhrase().split(" ");
			PhraseQuery phraseQuery = new PhraseQuery();
			for (int i=0; i < phrase.length; i++) {
				phraseQuery.add(new Term(field, phrase[i]));
			}
			finalQuery.add(phraseQuery, BooleanClause.Occur.MUST);
		}
		
		if (searchDTO.getAllWords() != null && !"".equals(searchDTO.getAllWords().trim())) {
			String[] allWordsArr = searchDTO.getAllWords().split(" ");
			BooleanQuery queryAllWord = new BooleanQuery();
			for (int i = 0; i < allWordsArr.length; i++) {
				String string = allWordsArr[i];
				queryAllWord.add(new TermQuery(new Term(field,string)), BooleanClause.Occur.MUST);
			}
			finalQuery.add(queryAllWord,BooleanClause.Occur.MUST);
		}
		if (searchDTO.getOneOrMore() != null && !"".equals(searchDTO.getOneOrMore().trim())) {
			String[] oneOrMoreArr = searchDTO.getOneOrMore().split(" ");
			BooleanQuery queryOneOrMore = new BooleanQuery();
			for (int i = 0; i < oneOrMoreArr.length; i++) {
				String string = oneOrMoreArr[i];
				queryOneOrMore.add(new TermQuery(new Term(field,string)), BooleanClause.Occur.SHOULD);
			}
			finalQuery.add(queryOneOrMore,BooleanClause.Occur.MUST);
		}
		
		if (searchDTO.getNoneWords() != null && !"".equals(searchDTO.getNoneWords().trim())) {
			String[] noneWordsArr = searchDTO.getNoneWords().split(" ");
			for (int i = 0; i < noneWordsArr.length; i++) {
				String string = noneWordsArr[i];
				finalQuery.add(new TermQuery(new Term(field,string)), BooleanClause.Occur.MUST_NOT);
			}
		}
				
		Term from = null, to = null;
		if (searchDTO.getFromDate() != null && !"".equals(searchDTO.getFromDate())) {
			from = new Term("lastUpdated",searchDTO.getFromDate().replaceAll("-", ""));
		}
		if (searchDTO.getToDate() != null && !"".equals(searchDTO.getToDate()) ) {
			to = new Term("lastUpdated",searchDTO.getToDate().replaceAll("-", ""));
		}
		if (from != null || to != null) {
			RangeQuery range = new RangeQuery(from,to,true);
			finalQuery.add(range, BooleanClause.Occur.MUST);
		}
		if ("".equals(finalQuery.toString())) {
			finalQuery.add(new WildcardQuery(new Term("id","*")),BooleanClause.Occur.MUST);
		}
		
		return finalQuery;		
	}
}
