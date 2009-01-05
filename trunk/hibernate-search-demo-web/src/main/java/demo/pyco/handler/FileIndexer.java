package demo.pyco.handler;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Date;

/**
 * A File Indexer capable of recursively indexing a directory tree.
 */
public class FileIndexer {
	protected FileHandler fileHandler;

	public FileIndexer(Properties props) throws IOException {
		fileHandler = new ExtensionFileHandler(props);
	}

	public void index(IndexWriter writer, File file)
			throws FileHandlerException {

		if (file.canRead()) {
			if (file.isDirectory()) {
				String[] files = file.list();
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						index(writer, new File(file, files[i]));
					}
				}
			} else {
				System.out.println("Indexing " + file);
				try {
					Document doc = fileHandler.getDocument(file);
					if (doc != null) {
						String fileName = file.getName();
						doc.add(new Field("filename", fileName, Field.Store.YES, Field.Index.UN_TOKENIZED));
						writer.addDocument(doc);
					} else {
						System.err.println("Cannot handle "
								+ file.getAbsolutePath() + "; skipping");
					}
				} catch (IOException e) {
					System.err.println("Cannot index " + file.getAbsolutePath()
							+ "; skipping (" + e.getMessage() + ")");
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {

		String proPath = "bin\\demo\\pyco\\framework\\handler.properties";
		String directory = "D:\\Lucene\\fk_files";
		String indexPath = "D:\\Lucene\\fk_indexed";

		Properties props = new Properties();
		props.load(new FileInputStream(proPath));

		Directory dir = FSDirectory.getDirectory(indexPath, true);
		// Analyzer analyzer = new SimpleAnalyzer();
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriter writer = new IndexWriter(dir, analyzer, true);

		FileIndexer indexer = new FileIndexer(props);

		long start = new Date().getTime();
		indexer.index(writer, new File(directory));
		writer.optimize();
		writer.close();
		long end = new Date().getTime();

		System.out.println();
		IndexReader reader = IndexReader.open(dir);
		System.out.println("Documents indexed: " + reader.numDocs());
		System.out.println("Total time: " + (end - start) + " ms");
		reader.close();
	}

}
