package demo.hibernatesearch.action.file;

import com.opensymphony.xwork2.Action;


import demo.hibernatesearch.application.Constants;
import demo.hibernatesearch.model.FileAdvanceSearchDTO;

import demo.hibernatesearch.model.FileUploadDTO;
import demo.hibernatesearch.util.IList;

public class AdvanceSearchAction extends SearchAction {

	private String allWords;
	private String wordPhrase;
	private String oneOrMore;
	private String noneWords;
	private String typeFile;
	private String fromDate;
	private String toDate;

	
	@Override

	public IList<FileUploadDTO> search(int pageIndex, int pageSize) throws Exception {

		FileAdvanceSearchDTO searchDTO = new FileAdvanceSearchDTO();
		searchDTO.setAllWords(allWords);
		searchDTO.setWordPhrase(wordPhrase);
		searchDTO.setOneOrMore(oneOrMore);
		searchDTO.setNoneWords(noneWords);
		searchDTO.setDocType(typeFile);
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
	public String getTypeFile() {
		return typeFile;
	}
	public void setTypeFile(String typeFile) {
		this.typeFile = typeFile;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

}
