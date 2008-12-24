package demo.hibernatesearch.action;

import com.opensymphony.xwork2.Action;

import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.util.IList;

public class AdvanceSearchAction extends SearchAction{

	private String allWords;
	private String wordPhrase;
	private String oneOrMore;
	private String noneWords;
	private String[] fields;
	private String fromDate;
	private String toDate;
	
	@Override
	public IList<Resume> search(int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		return null;
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
