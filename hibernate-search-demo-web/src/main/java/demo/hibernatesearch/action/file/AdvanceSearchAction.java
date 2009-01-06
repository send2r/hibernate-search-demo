package demo.hibernatesearch.action.file;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.opensymphony.xwork2.Action;

import demo.hibernatesearch.application.Constants;
import demo.hibernatesearch.model.FileAdvanceSearchDTO;
import demo.hibernatesearch.model.FileUploadDTO;
import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.model.User;
import demo.hibernatesearch.util.IList;

public class AdvanceSearchAction extends SearchAction {

	private String allWords;
	private String wordPhrase;
	private String oneOrMore;
	private String noneWords;
	private String fromDate;
	private String toDate;
	private String docType;
	
	@Override
	public IList<FileUploadDTO> search(int pageIndex, int pageSize) throws Exception {
		
		FileAdvanceSearchDTO searchDTO = new FileAdvanceSearchDTO();
		
		try {
			String strValue= URLDecoder.decode(allWords, "utf-8");
			searchDTO.setAllWords(strValue);
			strValue= URLDecoder.decode(wordPhrase, "utf-8");
			searchDTO.setWordPhrase(strValue);
			strValue= URLDecoder.decode(oneOrMore, "utf-8");
			searchDTO.setOneOrMore(strValue);
			strValue= URLDecoder.decode(noneWords, "utf-8");
			searchDTO.setNoneWords(strValue);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		searchDTO.setDocType(docType);
		searchDTO.setFromDate(fromDate);
		searchDTO.setToDate(toDate);
		
		IList<FileUploadDTO> result = fileManager.advanceSearch(pageIndex > 0
				? pageIndex - 1
						: pageIndex, Constants.PAGE_SIZE, searchDTO);
		
		return result;

	}
	public String initSearch() throws Exception{
		return Action.SUCCESS;
	}
	
	public String getAllWords() {
		return allWords;
	}
	public void setAllWords(String allWords) {
		this.allWords = allWords;
	}
	public String getWordPhrase() {
		return wordPhrase;
	}
	public void setWordPhrase(String wordPhrase) {
		this.wordPhrase = wordPhrase;
	}
	public String getOneOrMore() {
		return oneOrMore;
	}
	public void setOneOrMore(String oneOrMore) {
		this.oneOrMore = oneOrMore;
	}
	public String getNoneWords() {
		return noneWords;
	}
	public void setNoneWords(String noneWords) {
		this.noneWords = noneWords;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
}
