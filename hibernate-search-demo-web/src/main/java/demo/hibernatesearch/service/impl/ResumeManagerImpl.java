package demo.hibernatesearch.service.impl;

import demo.hibernatesearch.dao.ResumeDao;
import demo.hibernatesearch.model.AdvanceSearchDTO;
import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.model.User;
import demo.hibernatesearch.service.ResumeManager;
import demo.hibernatesearch.util.IList;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Declare POJO Spring Service Bean
 */
@Service("resumeManager")
public class ResumeManagerImpl implements ResumeManager {

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ResumeDao resumeDao;

	public ResumeDao getResumeDao() {
		return resumeDao;
	}

	public void setResumeDao(ResumeDao resumeDao) {
		this.resumeDao = resumeDao;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveApplicant(User applicant) {
		resumeDao.saveApplicant(applicant);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateApplicant(User applicant) {
		resumeDao.updateApplicant(applicant);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public User getApplicant(Long id) {
		return resumeDao.getApplicant(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteApplicant(User applicant) {
		resumeDao.deleteApplicant(applicant);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveResume(Resume resume) {
		resumeDao.saveResume(resume);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateResume(Resume resume) {
		resumeDao.updateResume(resume);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Resume getResume(Long id) {
		return resumeDao.getResume(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteResume(Resume resume) {
		resumeDao.deleteResume(resume);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Resume> dbFindResumesForUser(String emailAddress) {
		return resumeDao.dbFindResumesForUser(emailAddress);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Resume> seFindResumesForUser(String emailAddress) {
		return resumeDao.seFindResumesForUser(emailAddress);
	}
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IList<Resume> seFindResumesForUserWithPagination(final String emailAddress, final int pageIndex, final int pageSize){
		return resumeDao.seFindResumesForUserWithPagination(emailAddress, pageIndex, pageSize);
	}
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int dbFindMatchCount(Date beginDate, Date endDate,
			String... keywordsInSummary) {
		return resumeDao
				.dbFindMatchCount(beginDate, endDate, keywordsInSummary);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int seFindMatchCount(Date beginDate, Date endDate,
			String... keywordsInSummary) {
		return resumeDao
				.seFindMatchCount(beginDate, endDate, keywordsInSummary);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Resume> dbFindResumesWithPagination(int fetchCursor,
			int fetchSize, Date beginDate, Date endDate,
			String... keywordsInSummary) {

		return resumeDao.dbFindResumesWithPagination(fetchCursor, fetchSize,
				beginDate, endDate, keywordsInSummary);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Resume> seFindResumesWithPagination(int fetchCursor,
			int fetchSize, Date beginDate, Date endDate,
			String... keywordsInSummary) {

		return resumeDao.seFindResumesWithPagination(fetchCursor, fetchSize,
				beginDate, endDate, keywordsInSummary);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Resume> seFindResumesWithDocHandler(Date beginDate,
			Date endDate, String... keywordsInWordDoc) {

		return resumeDao.seFindResumesWithDocHandler(beginDate, endDate,
				keywordsInWordDoc);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<Resume, Float> seFindResumeProjectionsWithoutDatabaseAccess(
			Date beginDate, Date endDate, String... keywordsInSummary) {

		return resumeDao.seFindResumeProjectionsWithoutDatabaseAccess(
				beginDate, endDate, keywordsInSummary);
	}
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IList<Resume> getAllResum(final int pageIndex, final int pageSize){
		// TODO Auto-generated method stub
		return resumeDao.getAllResum(pageIndex, pageSize);
	}
	
	public User getUserByEmail(String emailAddress) {
		
		return resumeDao.getUserByEmail(emailAddress);
	}
	
	public IList<Resume> simpleSearch(final int pageIndex, final int pageSize,final String searchString) {
		return resumeDao.simpleSearch(pageIndex, pageSize, searchString);
	}
	
	public IList<Resume> simpleSearchWithEmail(final int pageIndex, final int pageSize,final String searchString, final String email) {
		return resumeDao.simpleSearchWithEmail(pageIndex, pageSize, searchString, email);
	}
	
	public IList<Resume> advanceSearch(final int pageIndex, final int pageSize,final AdvanceSearchDTO searchDTO) {
		return resumeDao.advanceSearch(pageIndex, pageSize, searchDTO);
	}
	
	public IList<Resume> advanceSearchWithEmail(final int pageIndex, final int pageSize,final AdvanceSearchDTO searchDTO, String email) {
		return resumeDao.advanceSearchWithEmail(pageIndex, pageSize, searchDTO, email);
	}	

}
