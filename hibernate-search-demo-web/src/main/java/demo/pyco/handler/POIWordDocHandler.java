package demo.pyco.handler;


import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import org.apache.poi.hdf.extractor.WordDocument;

import demo.hibernatesearch.application.Constants;
import demo.pyco.handler.DocumentHandler;
import demo.pyco.handler.DocumentHandlerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.PrintWriter;

public class POIWordDocHandler implements DocumentHandler {

  public Document getDocument(InputStream is)
    throws DocumentHandlerException {

    String bodyText = null;

    try {
      WordDocument wd = new WordDocument(is);
      StringWriter docTextWriter = new StringWriter();
      wd.writeAllText(new PrintWriter(docTextWriter));
      docTextWriter.close();
      bodyText = docTextWriter.toString();
    }
    catch (Exception e) {
      throw new DocumentHandlerException(
        "Cannot extract text from a Word document", e);
    }

    if ((bodyText != null) && (bodyText.trim().length() > 0)) {
      Document doc = new Document();
      doc.add(new Field("docType", Constants.DOC, Field.Store.YES,Field.Index.UN_TOKENIZED));
      doc.add(new Field("body", bodyText, Field.Store.YES,Field.Index.TOKENIZED));
      return doc;
    }
    return null;
  }

}