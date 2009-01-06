package demo.pyco.handler;


import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import org.pdfbox.cos.COSDocument;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDDocumentInformation;
import org.pdfbox.encryption.DecryptDocument;
import org.pdfbox.exceptions.InvalidPasswordException;
import org.pdfbox.exceptions.CryptographyException;
import org.pdfbox.util.PDFTextStripper;

import demo.hibernatesearch.application.Constants;
import demo.pyco.handler.DocumentHandler;
import demo.pyco.handler.DocumentHandlerException;

public class PDFBoxPDFHandler implements DocumentHandler {

  public static String password = "-password";

  public Document getDocument(InputStream is)
    throws DocumentHandlerException {

    COSDocument cosDoc = null;
    try {
      cosDoc = parseDocument(is);
    }
    catch (IOException e) {
      closeCOSDocument(cosDoc);
      throw new DocumentHandlerException(
        "Cannot parse PDF document", e);
    }

    // decrypt the PDF document, if it is encrypted
    try {
      if (cosDoc.isEncrypted()) {
        DecryptDocument decryptor = new DecryptDocument(cosDoc);
        decryptor.decryptDocument(password);
      }
    }
    catch (CryptographyException e) {
      closeCOSDocument(cosDoc);
      throw new DocumentHandlerException(
        "Cannot decrypt PDF document", e);
    }
    catch (InvalidPasswordException e) {
      closeCOSDocument(cosDoc);
      throw new DocumentHandlerException(
        "Cannot decrypt PDF document", e);
    }
    catch (IOException e) {
      closeCOSDocument(cosDoc);
      throw new DocumentHandlerException(
        "Cannot decrypt PDF document", e);
    }

    // extract PDF document's textual content
    String docText = null;
    try {
      PDFTextStripper stripper = new PDFTextStripper();
      docText = stripper.getText(new PDDocument(cosDoc));
    }
    catch (IOException e) {
      closeCOSDocument(cosDoc);
      throw new DocumentHandlerException(
        "Cannot parse PDF document", e);
//       String errS = e.toString();
//       if (errS.toLowerCase().indexOf("font") != -1) {
//       }
    }

    Document doc = new Document();
    doc.add(new Field("docType", Constants.PDF,Field.Store.YES,Field.Index.UN_TOKENIZED));
    if (docText != null) {
      doc.add(new Field("body", docText,Field.Store.YES,Field.Index.TOKENIZED));
    }

    // extract PDF document's meta-data
    PDDocument pdDoc = null;
    try {
      pdDoc = new PDDocument(cosDoc);
      PDDocumentInformation docInfo =
          pdDoc.getDocumentInformation();
      String author   = docInfo.getAuthor();
      String title    = docInfo.getTitle();
      String keywords = docInfo.getKeywords();
      String summary  = docInfo.getSubject();
      if ((author != null) && (!author.equals(""))) {
        doc.add(new Field("author", author, Field.Store.YES, Field.Index.TOKENIZED));
      }
      if ((title != null) && (!title.equals(""))) {
        doc.add(new Field("title", title, Field.Store.YES, Field.Index.TOKENIZED));
      }
      if ((keywords != null) && (!keywords.equals(""))) {
        doc.add(new Field("keywords", keywords, Field.Store.YES, Field.Index.UN_TOKENIZED));
      }
      if ((summary != null) && (!summary.equals(""))) {
        doc.add(new Field("summary", summary, Field.Store.YES, Field.Index.TOKENIZED));
      }
    }
    catch (Exception e) {
      closeCOSDocument(cosDoc);
      closePDDocument(pdDoc);
      System.err.println("Cannot get PDF document meta-data: "
        + e.getMessage());
    }
    closeCOSDocument(cosDoc);
    closePDDocument(pdDoc);
    return doc;
  }

  private static COSDocument parseDocument(InputStream is)
    throws IOException {
    PDFParser parser = new PDFParser(is);
    parser.parse();
    return parser.getDocument();
  }

  private void closeCOSDocument(COSDocument cosDoc) {
    if (cosDoc != null) {
      try {
        cosDoc.close();
      }
      catch (IOException e) {
        // eat it, what else can we do?
      }
    }
  }

  private void closePDDocument(PDDocument pdDoc) {
    if (pdDoc != null) {
      try {
        pdDoc.close();
      }
      catch (IOException e) {
        // eat it, what else can we do?
      }
    }
  }

}
