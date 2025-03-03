package demo.pyco.handler;


import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;

import demo.hibernatesearch.application.Constants;
import demo.pyco.handler.DocumentHandler;
import demo.pyco.handler.DocumentHandlerException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.HashMap;

public class SAXXMLHandler

  extends DefaultHandler implements DocumentHandler {

  /** A buffer for each XML element */
  private StringBuffer elementBuffer = new StringBuffer();
  private HashMap attributeMap = new HashMap();

  private Document doc;

  public Document getDocument(InputStream is)
    throws DocumentHandlerException {

    SAXParserFactory spf = SAXParserFactory.newInstance();
    try {
      SAXParser parser = spf.newSAXParser();
      parser.parse(is, this);
    }
    catch (IOException e) {
      throw new DocumentHandlerException(
        "Cannot parse XML document", e);
    }
    catch (ParserConfigurationException e) {
      throw new DocumentHandlerException(
        "Cannot parse XML document", e);
    }
    catch (SAXException e) {
      throw new DocumentHandlerException(
        "Cannot parse XML document", e);
    }

    return doc;
  }

  public void startDocument() {
    doc = new Document();
    doc.add(new Field("docType", Constants.XML, Field.Store.YES, Field.Index.UN_TOKENIZED));
  }

  public void startElement(String uri, String localName,
    String qName, Attributes atts)
    throws SAXException {

    elementBuffer.setLength(0);
    attributeMap.clear();
    if (atts.getLength() > 0) {
      attributeMap = new HashMap();
      for (int i = 0; i < atts.getLength(); i++) {
        attributeMap.put(atts.getQName(i), atts.getValue(i));
      }
    }
  }

  public void characters(char[] text, int start, int length) {
    elementBuffer.append(text, start, length);
  }

  public void endElement(String uri, String localName, String qName)
    throws SAXException {

    if (!"".equals(qName)) {
      Iterator iter = attributeMap.keySet().iterator();
      while (iter.hasNext()) {
        String attName = (String) iter.next();
        String attValue = (String) attributeMap.get(attName);
        doc.add(new Field("body", attValue, Field.Store.YES, Field.Index.UN_TOKENIZED));
      }
      doc.add(new Field("body", elementBuffer.toString(), Field.Store.YES, Field.Index.UN_TOKENIZED));
    }
  }

}
