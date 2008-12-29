package demo.hibernatesearch.model;


public class AdvanceSearchDTO {
	
	private String allWords;
	
	private String wordPhrase;
	
	private String oneOrMore;
	
	private String noneWords;
	
	private String field;
	
	private String fromDate;
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
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
	private String toDate;
	
	
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
	
}
