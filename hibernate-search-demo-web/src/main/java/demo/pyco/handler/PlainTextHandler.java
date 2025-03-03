package demo.pyco.handler;


import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import demo.hibernatesearch.application.Constants;
import demo.pyco.handler.DocumentHandler;
import demo.pyco.handler.DocumentHandlerException;

import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;

public class PlainTextHandler implements DocumentHandler {

  public Document getDocument(InputStream is)
    throws DocumentHandlerException {

    String bodyText = "";

    try {
      BufferedReader br =
        new BufferedReader(new InputStreamReader(is));
      String line = null;
      while ((line = br.readLine()) != null) {
        bodyText += line;
      }
      br.close();
    }
    catch(IOException e) {
      throw new DocumentHandlerException(
        "Cannot read the text document", e);
    }

    if (!bodyText.equals("")) {
      Document doc = new Document();
      doc.add(new Field("docType", Constants.TEXT, Field.Store.YES, Field.Index.UN_TOKENIZED));
      doc.add(new Field("body", bodyText, Field.Store.YES, Field.Index.TOKENIZED));
      return doc;
    }

    return null;
  }

}
