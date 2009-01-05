package demo.hibernatesearch.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.springframework.stereotype.Service;

import demo.hibernatesearch.application.ManagerResource;
import demo.hibernatesearch.model.FileUploadDTO;
import demo.hibernatesearch.service.FileManager;
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
		reader.deleteDocuments(new Term("city", "Amsterdam"));
		reader.close();
	}

}
