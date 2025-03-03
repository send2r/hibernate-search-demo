package demo.hibernatesearch.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import demo.hibernatesearch.model.AdvanceSearchDTO;
import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.model.User;
import demo.hibernatesearch.util.IList;

public interface ResumeDao {

	public void saveApplicant(User applicant);

	public void updateApplicant(User applicant);

	public User getApplicant(Long id);

	public void deleteApplicant(User applicant);

	public void saveResume(Resume resume);

	public void updateResume(Resume resume);

	public Resume getResume(Long id);

	public void deleteResume(Resume resume);

	public List<Resume> dbFindResumesForUser(String emailAddress);

	public List<Resume> seFindResumesForUser(String emailAddress);

	public IList<Resume> seFindResumesForUserWithPagination(final String emailAddress, final int pageIndex, final int pageSize);
	
	public int dbFindMatchCount(Date beginDate, Date endDate,
			String... keywordsInSummary);

	public int seFindMatchCount(Date beginDate, Date endDate,
			String... keywordsInSummary);

	public List<Resume> dbFindResumesWithPagination(int fetchCursor,
			int fetchSize, Date beginDate, Date endDate,
			String... keywordsInSummary);

	public List<Resume> seFindResumesWithPagination(int fetchCursor,
			int fetchSize, Date beginDate, Date endDate,
			String... keywordsInSummary);

	public List<Resume> seFindResumesWithDocHandler(Date beginDate,
			Date endDate, String... keywordsInWordDoc);

	public Map<Resume, Float> seFindResumeProjectionsWithoutDatabaseAccess(
			Date beginDate, Date endDate, String... keywordsInSummary);
	
	public Object getEntityLimit(Class...limit);
	public Object seFetchStrategy() ;
	public IList<Resume> getAllResum(int pageIndex, int pageSize);
	public IList<Resume> getAllResumAndSort(final int pageIndex, final int pageSize, final String sortField, final boolean reverse) ;
	public User getUserByEmail(String emailAddress);
	
	public IList<Resume> simpleSearch(final int pageIndex, final int pageSize,final String searchString, final String sortField, final boolean reverse)	;
	public IList<Resume> simpleSearchWithEmail(final int pageIndex, final int pageSize,final String searchString, final String email);
	
	public IList<Resume> advanceSearch(final int pageIndex, final int pageSize, final AdvanceSearchDTO searchDTO);
	
	public IList<Resume> advanceSearchWithEmail(final int pageIndex, final int pageSize,final AdvanceSearchDTO searchDTO, final String email);
}
