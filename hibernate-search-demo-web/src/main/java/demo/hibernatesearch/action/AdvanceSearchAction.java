package demo.hibernatesearch.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.opensymphony.xwork2.Action;

import demo.hibernatesearch.application.Constants;
import demo.hibernatesearch.model.AdvanceSearchDTO;
import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.model.User;
import demo.hibernatesearch.util.IList;

public class AdvanceSearchAction extends SearchAction {

	private String allWords;
	private String wordPhrase;
	private String oneOrMore;
	private String noneWords;
	private String[] fields;
	private String fromDate;
	private String toDate;
		
	@Override
	public IList<Resume> search(int pageIndex, int pageSize) {
		
		AdvanceSearchDTO searchDTO = new AdvanceSearchDTO();
		
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
		searchDTO.setFields(fields);
		searchDTO.setFromDate(fromDate);
		searchDTO.setToDate(toDate);
		User currentUser = (User)session.get(Constants.CURRENT_USER);
		IList<Resume> listResume = null;
		if(currentUser == null) {
			return resumeManager.advanceSearch(pageIndex > 0 ? pageIndex - 1 : pageIndex, pageSize, searchDTO);
		} else {
			return resumeManager.advanceSearchWithEmail(pageIndex > 0 ? pageIndex - 1 : pageIndex, pageSize, searchDTO, currentUser.getEmailAddress());
		}

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
	public String[] getFields() {
		return fields;
	}
	public void setFields(String[] fields) {
		this.fields = fields;
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

}
