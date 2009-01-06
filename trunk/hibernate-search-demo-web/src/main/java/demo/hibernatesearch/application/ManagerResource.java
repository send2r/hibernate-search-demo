package demo.hibernatesearch.application;

import java.io.File;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.apache.xpath.XPathAPI;

import demo.hibernatesearch.taglib.pager.PagerModel;
import demo.pyco.handler.ExtensionFileHandler;
import demo.pyco.handler.FileHandler;
import freemarker.template.DefaultObjectWrapper;

public class ManagerResource {

	private static final String CONFIG_NAME = "configuration.xml";
	private static final String CONFIG_HANDLE_NAME = "handler.properties";
	private static Hashtable<String, String> navigations;
	private static String uploadFolder;
	private static String fileIndexFolder;
	private static FileHandler fileHandler;
	
	private static Log log = LogFactory.getLog(ManagerResource.class);

	static {
		try {
			ClassLoader loader = ManagerResource.class.getClassLoader();
			InputStream configStream = loader.getResourceAsStream(CONFIG_NAME);
			Properties probs = new Properties();
			probs.load(loader.getResourceAsStream(CONFIG_HANDLE_NAME));
			fileHandler = new ExtensionFileHandler(probs);
			buildConfig(configStream);
			initIndexFolder();
		} catch (Exception ex) {
			log
					.error("Error occurs when load setting file: "
							+ CONFIG_NAME, ex);
			
		}
	}

	private static void buildConfig(InputStream configStream) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(configStream);
		
		navigations = new Hashtable<String, String>();
		NodeList navigationNodeList = XPathAPI.selectNodeList(document,
				"/configuration/navigation-setting/navigation-mapping");
		for (int i = 0; i < navigationNodeList.getLength(); i++) {
			Node navigationNode = navigationNodeList.item(i);
			String name = getNodeValue(navigationNode, "./@name");
			String value = getNodeValue(navigationNode, "./@value");
			navigations.put(name, value);
		}
		
		NodeList uploadFolderList = XPathAPI.selectNodeList(document,"/configuration/upload-folder");
		if(uploadFolderList.getLength() > 0){
			Node uploadFolderTemp = uploadFolderList.item(0);
			uploadFolder = getNodeValue(uploadFolderTemp,"./@value");
		}
		
		NodeList indexFolderList = XPathAPI.selectNodeList(document,"/configuration/file-index-folder");
		if(indexFolderList.getLength() > 0){
			Node indexFolderTemp = indexFolderList.item(0);
			fileIndexFolder = getNodeValue(indexFolderTemp,"./@value");
		}
		
		NodeList pageSizeList = XPathAPI.selectNodeList(document,"/configuration/page-size");
		if(pageSizeList.getLength() > 0){
			Node pageSizeListTemp = pageSizeList.item(0);
			String pagesize = getNodeValue(pageSizeListTemp,"./@value");
			Constants.PAGE_SIZE = Integer.valueOf(pagesize);
		}
	}
	
	private static void initIndexFolder() throws Exception{
		IndexWriter writer = null;
		try{
			writer = new IndexWriter(ManagerResource.getFileIndexFolder(), new StandardAnalyzer(), false);
		}catch (Exception e) {
			writer = new IndexWriter(ManagerResource.getFileIndexFolder(), new StandardAnalyzer(), true);
		}finally{
			writer.optimize();
			writer.close();
		}
		
	}

	private static String getNodeValue(Node contextNode, String xpath) {
		String val;
		try {
			Node node = XPathAPI.selectSingleNode(contextNode, xpath);
			val = node.getNodeValue();
		} catch (TransformerException ex) {
			log.warn("Error occurs when transform the node "
					+ contextNode.getNodeName()
					+ ". Returns default null value.");
			val = null;
		}
		return val;
	}

	public static PagerModel getPagerModel() {
		PagerModel pagerModel = new PagerModel();
		pagerModel.setLayout("first-previous-info1-next-last-info2-input");
		pagerModel
				.setInfo1("Page <strong>#pageIndex#</strong> of #totalPages#");
		pagerModel.setInfo2("| Go to page");
		pagerModel.setFirstText("|&laquo;");
		pagerModel.setPreviousText("&laquo;");
		pagerModel.setNextText("&raquo;");
		pagerModel.setLastText("&raquo;|");
		pagerModel.setItemName("item");
		return pagerModel;
	}

	public static freemarker.template.Configuration getTemplateConfig(
			ServletContext context) throws Exception {
		freemarker.template.Configuration templateConfig = null;
		if (templateConfig == null) {
			templateConfig = new freemarker.template.Configuration();
			String templatesUri = "/templates/taglib";
			String dirPath = context.getRealPath(templatesUri);
			templateConfig.setDirectoryForTemplateLoading(new File(dirPath));
			templateConfig.setObjectWrapper(new DefaultObjectWrapper());
		}
		return templateConfig;
	}

	public static Hashtable<String, String> getNavigations() {
		return navigations;
	}

	public static void setNavigations(Hashtable<String, String> navigations) {
		ManagerResource.navigations = navigations;
	}

	public static String getUploadFolder() {
		return uploadFolder;
	}

	public static void setUploadFolder(String uploadFolder) {
		ManagerResource.uploadFolder = uploadFolder;
	}
	
	public static String getFileIndexFolder() {
		return fileIndexFolder;
	}

	public static void setFileIndexFolder(String fileIndexFolder) {
		ManagerResource.fileIndexFolder = fileIndexFolder;
	}
	
	public static String getImageIcon(String fileName){
		String result = "../images/filesicon/";
		String mineType = fileName.substring(fileName.lastIndexOf(".") + 1);
		if("pdf".equalsIgnoreCase(mineType)){
			result += "PDF.png";
		}else if("doc".equalsIgnoreCase(mineType)){
			result += "DOC.png";
		}else if("html".equalsIgnoreCase(mineType)){
			result += "HTML.png";
		}else if("rtf".equalsIgnoreCase(mineType)){
			result += "RTF.png";
		}else if("jpeg".equalsIgnoreCase(mineType) || "jpg".equalsIgnoreCase(mineType)){
			result += "JPG.png";
		}else if("gif".equalsIgnoreCase(mineType)){
			result += "GIF.png";
		}else {
			result += "UNKNOWN.png";
		}
		return result;
	}

	public static FileHandler getFileHandler() {
		return fileHandler;
	}

	public static void setFileHandler(FileHandler fileHandler) {
		ManagerResource.fileHandler = fileHandler;
	}
}
