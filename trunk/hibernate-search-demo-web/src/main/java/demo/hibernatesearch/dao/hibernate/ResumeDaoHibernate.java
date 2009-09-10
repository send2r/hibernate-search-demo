package demo.hibernatesearch.dao.hibernate;

import static org.hibernate.search.jpa.Search.createFullTextEntityManager;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.RangeQuery;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.search.FullTextFilter;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import demo.hibernatesearch.dao.ResumeDao;
import demo.hibernatesearch.model.AdvanceSearchDTO;
import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.model.User;
import demo.hibernatesearch.util.IList;
import demo.hibernatesearch.util.ListImpl;

/**
 * Declare POJO Spring Component DAO
 */
@Repository("resumeDao")
public class ResumeDaoHibernate implements ResumeDao {

	protected final Log log = LogFactory.getLog(getClass());

	JpaTemplate jpaTemplate;

	@Autowired
	public ResumeDaoHibernate(EntityManagerFactory entityManagerFactory) {
		this.jpaTemplate = new JpaTemplate(entityManagerFactory);
	}
	
	public JpaTemplate getJpaTemplate() {
		return jpaTemplate;
	}

	public void setJpaTemplate(JpaTemplate jpaTemplate) {
		this.jpaTemplate = jpaTemplate;
	}

	/**
	 * Auto Indexing
	 */
	public void saveApplicant(User applicant) {
		getJpaTemplate().persist(applicant);
	}

	/**
	 * Auto Indexing
	 */
	public void updateApplicant(User applicant) {
		getJpaTemplate().refresh(applicant);
	}

	/**
	 * Index Read
	 */
	public User getApplicant(Long id) {
		return getJpaTemplate().find(User.class, id);
	}

	/**
	 * Auto Indexing
	 */
	public void deleteApplicant(User applicant) {
		getJpaTemplate().remove(applicant);
	}

	/**
	 * Auto Indexing
	 */
	public void saveResume(Resume resume) {
		getJpaTemplate().persist(resume);
	}

	/**
	 * Auto Indexing
	 */
	public void updateResume(Resume resume) {
		getJpaTemplate().merge(resume);
		//getJpaTemplate().refresh(resume);
	}

	/**
	 * Index Read
	 */
	public Resume getResume(Long id) {
		return getJpaTemplate().find(Resume.class, id);
	}

	/**
	 * Auto Indexing
	 */
	public void deleteResume(Resume resume) {
		Resume rs = getJpaTemplate().getReference(Resume.class, resume.getId());
		getJpaTemplate().remove(rs);
	}

	/**
	 * JPA query, no index search
	 */
	@SuppressWarnings("unchecked")
	public List<Resume> dbFindResumesForUser(String emailAddress) {
		return (List<Resume>) getJpaTemplate()
				.find(
						"from Resume resume left join fetch applicant where resume.applicant.emailAddress='"
								+ emailAddress + "'");
	}

	/**
	 * Index search followed by JPA query
	 * 
	 * getJpaTemplate().getEntityManager() appears returning null
	 */
	@SuppressWarnings("unchecked")
	public List<Resume> seFindResumesForUser(final String emailAddress) {
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {

				FullTextEntityManager fullTextEntityManager = createFullTextEntityManager(em);

				TermQuery tq = new TermQuery(new Term("applicant.emailAddress",
						emailAddress));

				FullTextQuery fq = fullTextEntityManager.createFullTextQuery(
						tq, Resume.class);
				return fq.getResultList();
			}
		});
		return (List<Resume>) results;
	}
	
	/**
	 * Index search followed by JPA query
	 * 
	 * getJpaTemplate().getEntityManager() appears returning null
	 */
	@SuppressWarnings("unchecked")
	public IList<Resume> seFindResumesForUserWithPagination(final String emailAddress, final int pageIndex, final int pageSize) {
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {

				FullTextEntityManager fullTextEntityManager = createFullTextEntityManager(em);

				TermQuery tq = new TermQuery(new Term("applicant.emailAddress",
						emailAddress));

				FullTextQuery fq = fullTextEntityManager.createFullTextQuery(
						tq, Resume.class);
				fq.setFirstResult(pageIndex*pageSize).setMaxResults(pageSize);
				
				IList<Resume> results = new ListImpl(fq.getResultList(), fq.getResultSize(), pageIndex, pageSize);
				
				return results;
			}
		});
		return (IList<Resume>) results;
	}

	/**
	 * JPA query, no index search
	 */
	public int dbFindMatchCount(final Date beginDate, final Date endDate,
			final String... keywordsInSummary) {
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {

				StringBuilder jpaql = new StringBuilder(
						"select count(resume) from Resume resume join resume.applicant where ");

				for (int i = 0; i < keywordsInSummary.length; i++) {
					jpaql.append("resume.summary like '%"
							+ keywordsInSummary[i] + "%' ");
					if (i < keywordsInSummary.length - 1)
						jpaql.append(" and ");
				}

				Session session = ((Session) em.getDelegate());

				session.enableFilter("rangeFilter").setParameter("beginDate",
						beginDate).setParameter("endDate", endDate);
				//((Long)session.createQuery(jpaql.toString()).iterate().next()).intValue();
				return ((Long) getJpaTemplate().find(jpaql.toString())
						.iterator().next()).intValue();
			}
		});
		return (Integer) results;
	}

	/**
	 * only index search, no JPA query
	 */
	public int seFindMatchCount(final Date beginDate, final Date endDate,
			final String... keywordsInSummary) {
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {

				FullTextEntityManager fullTextEntityManager = createFullTextEntityManager(em);

				BooleanQuery bq = new BooleanQuery();

				for (String q : keywordsInSummary) {
					TermQuery tq = new TermQuery(new Term("summary", q));
					bq.add(new BooleanClause(tq, BooleanClause.Occur.MUST));
				}

				FullTextQuery fq = fullTextEntityManager.createFullTextQuery(
						bq, Resume.class);

				FullTextFilter ff = fq.enableFullTextFilter("rangeFilter");
				ff.setParameter("fieldName", "lastUpdated");
				ff.setParameter("lowerTerm", DateTools.dateToString(beginDate,
						DateTools.Resolution.DAY));
				ff.setParameter("upperTerm", DateTools.dateToString(endDate,
						DateTools.Resolution.DAY));
				ff.setParameter("includeLower", true);
				ff.setParameter("includeUpper", true);

				return fq.getResultSize();
			}
		});
		return (Integer) results;
	}

	/**
	 * only JPA query without index search, pagination enabled
	 */
	@SuppressWarnings("unchecked")
	public List<Resume> dbFindResumesWithPagination(final int fetchCursor,
			final int fetchSize, final Date beginDate, final Date endDate,
			final String... keywordsInSummary) {
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {

				StringBuilder jpaql = new StringBuilder(
						"from Resume resume join fetch resume.applicant where ");

				for (int i = 0; i < keywordsInSummary.length; i++) {
					jpaql.append("resume.summary like '%"
							+ keywordsInSummary[i] + "%' ");
					if (i < keywordsInSummary.length - 1)
						jpaql.append(" and ");
				}

				Session session = ((Session) em.getDelegate());

				session.enableFilter("rangeFilter").setParameter("beginDate",
						beginDate).setParameter("endDate", endDate);

				Query query = em.createQuery(jpaql.toString());

				query.setFirstResult(fetchCursor);
				query.setMaxResults(fetchSize);

				return (List<Resume>) query.getResultList();
			}
		});
		return (List<Resume>) results;
	}

	/**
	 * index search followed by JPA query
	 */
	@SuppressWarnings("unchecked")
	public List<Resume> seFindResumesWithPagination(final int fetchCursor,
			final int fetchSize, final Date beginDate, final Date endDate,
			final String... keywordsInSummary) {
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {

				FullTextEntityManager fullTextEntityManager = createFullTextEntityManager(em);

				BooleanQuery bq = new BooleanQuery();

				for (String q : keywordsInSummary) {
					TermQuery tq = new TermQuery(new Term("summary", q));
					bq.add(new BooleanClause(tq, BooleanClause.Occur.MUST));
				}

				FullTextQuery fq = fullTextEntityManager.createFullTextQuery(
						bq, Resume.class);

				FullTextFilter ff = fq.enableFullTextFilter("rangeFilter");
				ff.setParameter("fieldName", "lastUpdated");
				ff.setParameter("lowerTerm", DateTools.dateToString(beginDate,
						DateTools.Resolution.DAY));
				ff.setParameter("upperTerm", DateTools.dateToString(endDate,
						DateTools.Resolution.DAY));
				ff.setParameter("includeLower", true);
				ff.setParameter("includeUpper", true);

				fq.setFirstResult(fetchCursor);
				fq.setMaxResults(fetchSize);

				return (List<Resume>) fq.getResultList();
			}
		});
		return (List<Resume>) results;
	}

	/**
	 * index search followed by JPA query, indexing with WordDocHandlerBridge
	 */
	@SuppressWarnings("unchecked")
	public List<Resume> seFindResumesWithDocHandler(final Date beginDate,
			final Date endDate, final String... keywordsInWordDoc) {
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {

				FullTextEntityManager fullTextEntityManager = createFullTextEntityManager(em);

				BooleanQuery bq = new BooleanQuery();

				for (String q : keywordsInWordDoc) {
					TermQuery tq = new TermQuery(new Term("resume", q));
					bq.add(new BooleanClause(tq, BooleanClause.Occur.MUST));
				}

				FullTextQuery fq = fullTextEntityManager.createFullTextQuery(
						bq, Resume.class);

				FullTextFilter ff = fq.enableFullTextFilter("rangeFilter");
				ff.setParameter("fieldName", "lastUpdated");
				ff.setParameter("lowerTerm", DateTools.dateToString(beginDate,
						DateTools.Resolution.DAY));
				ff.setParameter("upperTerm", DateTools.dateToString(endDate,
						DateTools.Resolution.DAY));
				ff.setParameter("includeLower", true);
				ff.setParameter("includeUpper", true);

				return (List<Resume>) fq.getResultList();
			}
		});
		return (List<Resume>) results;
	}

	/**
	 * By using projection, all search results are returned from indexes, no
	 * database access is required.
	 */
	@SuppressWarnings("unchecked")
	public Map<Resume, Float> seFindResumeProjectionsWithoutDatabaseAccess(
			final Date beginDate, final Date endDate,
			final String... keywordsInSummary) {
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {

				FullTextEntityManager fullTextEntityManager = createFullTextEntityManager(em);

				BooleanQuery bq = new BooleanQuery();

				for (String q : keywordsInSummary) {
					TermQuery tq = new TermQuery(new Term("summary", q));
					bq.add(new BooleanClause(tq, BooleanClause.Occur.MUST));
				}

				FullTextQuery fq = fullTextEntityManager.createFullTextQuery(
						bq, Resume.class);

				FullTextFilter ff = fq.enableFullTextFilter("rangeFilter");
				ff.setParameter("fieldName", "lastUpdated");
				ff.setParameter("lowerTerm", DateTools.dateToString(beginDate,
						DateTools.Resolution.DAY));
				ff.setParameter("upperTerm", DateTools.dateToString(endDate,
						DateTools.Resolution.DAY));
				ff.setParameter("includeLower", true);
				ff.setParameter("includeUpper", true);

				fq.setProjection(FullTextQuery.SCORE, "id",
						"summary",
						// "lastUpdated",
						"applicant.id", "applicant.firstName",
						"applicant.lastName", "applicant.middleName",
						"applicant.emailAddress");

				Map<Resume, Float> resumes = new HashMap<Resume, Float>();

				for (Object[] result : (List<Object[]>) fq.getResultList()) {
					Resume resume = new Resume();
					User applicant = new User();
					resume.setApplicant(applicant);

					resume.setId((Long) result[1]);
					resume.setSummary((String) result[2]);
					// resume.setLastUpdated((Date) result[3]);
					/**
					 * Can't project Date field marked with annotation
					 * DateBridge
					 */
					/** WordDoc content is left blank. */
					applicant.setId((Long) result[3]);
					applicant.setFirstName((String) result[4]);
					applicant.setLastName((String) result[5]);
					applicant.setMiddleName((String) result[6]);
					applicant.setEmailAddress((String) result[7]);

					resumes.put(resume, (Float) result[0]);
				}
				return resumes;
			}
		});
		return (Map<Resume, Float>) results;
	}

	public Object getEntityLimit(final Class... limit) {
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {

				FullTextEntityManager fullTextEntityManager = createFullTextEntityManager(em);
				
				BooleanQuery bq = new BooleanQuery();
				bq.add(new BooleanClause(new TermQuery(new Term("id", "2")), BooleanClause.Occur.MUST));
				//bq.add(new BooleanClause(new TermQuery(new Term("firstName", "Mike")), BooleanClause.Occur.MUST));
			
				
				FullTextQuery fq = fullTextEntityManager.createFullTextQuery(
						bq, limit);
				return fq.getResultList();
			}
		});
		return results;
	}
	
	public Object seFetchStrategy() {
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Session se = (Session)em.getDelegate();
				Criteria criteria = se.createCriteria(User.class).setFetchMode( "resumes", FetchMode.JOIN );
				FullTextEntityManager fullTextEntityManager = createFullTextEntityManager(em);
				BooleanQuery bq = new BooleanQuery();
				bq.add(new BooleanClause(new TermQuery(new Term("id", "2")), BooleanClause.Occur.MUST));
				FullTextQuery fq = fullTextEntityManager.createFullTextQuery(bq).setCriteriaQuery(criteria);
				
				return fq.getResultList();
			}
		});
		return results;
	}
	
	public IList<Resume> getAllResum(final int pageIndex, final int pageSize) {
		
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				FullTextEntityManager fullTextEntityManager = createFullTextEntityManager(em);
				FullTextQuery fq = fullTextEntityManager.createFullTextQuery (
						new WildcardQuery(new Term("id","*")), Resume.class);
				fq.setFirstResult(pageIndex*pageSize).setMaxResults(pageSize);
				IList pageList = new ListImpl(fq.getResultSize(), pageIndex, pageSize);
				pageList.setList(fq.getResultList());
				return pageList;
			}
		});
		return (IList<Resume>) results;
	}
	
public IList<Resume> getAllResumAndSort(final int pageIndex, final int pageSize, final String sortField, final boolean reverse) {
		
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				FullTextEntityManager fullTextEntityManager = createFullTextEntityManager(em);
				FullTextQuery fq = fullTextEntityManager.createFullTextQuery (
						new WildcardQuery(new Term("id","*")), Resume.class);
				fq.setFirstResult(pageIndex*pageSize).setMaxResults(pageSize);
				Sort sort = new Sort(new SortField(sortField, reverse));
				fq.setSort(sort);
				IList pageList = new ListImpl(fq.getResultSize(), pageIndex, pageSize);
				pageList.setList(fq.getResultList());
				return pageList;
			}
		});
		return (IList<Resume>) results;
	}
	
	public User getUserByEmail(String emailAddress) {
		
		List<User> list = null;
		User result = null;
		list = (List<User>)getJpaTemplate().find("from User user where user.emailAddress='"	+ emailAddress + "'");
		if (list != null && list.size() != 0) {
			result = list.get(0);
		}
		return result;		
	}
	
	public IList<Resume> simpleSearch(final int pageIndex, final int pageSize,final String searchString, final String sortField, final boolean reverse) {
		
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				
				IList pageList = null;
				FullTextEntityManager fullTextEntityManager = createFullTextEntityManager(em);
				try {
					org.apache.lucene.search.Query query;
					if ("".equals(searchString) || "*".equals(searchString)) {
						query = new WildcardQuery(new Term("id","*"));
					} else {
						MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[]{"summary", "content"}, new StandardAnalyzer());
						query = parser.parse(searchString);
					}
					BooleanQuery finalQuery = new BooleanQuery();
					finalQuery.add(query, BooleanClause.Occur.MUST);
					FullTextQuery fq = fullTextEntityManager.createFullTextQuery(finalQuery, Resume.class);
					Sort sort = new Sort(new SortField(sortField, reverse));
					fq.setSort(sort);
					fq.setFirstResult(pageIndex*pageSize).setMaxResults(pageSize);
					pageList = new ListImpl(fq.getResultSize(), pageIndex, pageSize);
					pageList.setList(fq.getResultList());
					pageList.setSearchString(searchString);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (pageList == null) {
					pageList = new ListImpl();
				}
				return pageList;
			}
		});
		return (IList<Resume>) results;
	}
	
	public IList<Resume> simpleSearchWithEmail(final int pageIndex, final int pageSize,final String searchString, final String email) {
		
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				
				IList pageList = null;
				FullTextEntityManager fullTextEntityManager = createFullTextEntityManager(em);
				 
				
				try {
					
					org.apache.lucene.search.Query query01;
					if ("".equals(searchString.trim()) || "*".equals(searchString)) {
						query01 = new WildcardQuery(new Term("id","*"));
					} else {
						MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[]{"summary", "content"}, new StandardAnalyzer());
						query01 = parser.parse(searchString);
					}
					
					TermQuery query02 = new TermQuery(new Term("applicant.emailAddress", email));
					BooleanQuery finalQuery = new BooleanQuery();
					finalQuery.add(query01,BooleanClause.Occur.MUST);
					finalQuery.add(query02,BooleanClause.Occur.MUST);
					FullTextQuery fq = fullTextEntityManager.createFullTextQuery(finalQuery, Resume.class);
					fq.setFirstResult(pageIndex*pageSize).setMaxResults(pageSize);
					pageList = new ListImpl(fq.getResultSize(), pageIndex, pageSize);
					pageList.setList(fq.getResultList());
					pageList.setSearchString(searchString);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (pageList == null) {
					pageList = new ListImpl();
				}
				return pageList;
			}
		});
		return (IList<Resume>) results;
	}
	
	public IList<Resume> advanceSearch(final int pageIndex, final int pageSize,final AdvanceSearchDTO searchDTO) {
		
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				
				BooleanQuery finalQuery = buildQuey(searchDTO);
				IList pageList = null;
				FullTextEntityManager fullTextEntityManager = createFullTextEntityManager(em);
				FullTextQuery fq = fullTextEntityManager.createFullTextQuery(finalQuery, Resume.class);
				fq.setFirstResult(pageIndex).setMaxResults(pageSize);
				pageList = new ListImpl(fq.getResultSize(), pageIndex, pageSize);
				pageList.setList(fq.getResultList());
				pageList.setSearchString(finalQuery.toString());
				return pageList;
			}
		});
		return (IList<Resume>) results;
	}
	
	public IList<Resume> advanceSearchWithEmail( final int pageIndex, final int pageSize,final AdvanceSearchDTO searchDTO, final String email) {
		
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				

				BooleanQuery finalQuery = buildQuey(searchDTO);
				TermQuery queryEmail = new TermQuery(new Term("applicant.emailAddress", email));
				finalQuery.add(queryEmail,BooleanClause.Occur.MUST);
				IList pageList = null;
				FullTextEntityManager fullTextEntityManager = createFullTextEntityManager(em);
				FullTextQuery fq = fullTextEntityManager.createFullTextQuery(finalQuery, Resume.class);
				fq.setFirstResult(pageIndex).setMaxResults(pageSize);
				pageList = new ListImpl(fq.getResultSize(), pageIndex, pageSize);
				pageList.setList(fq.getResultList());
				pageList.setSearchString(finalQuery.toString());
				return pageList;
			}
		});
		return (IList<Resume>) results;
	}
	
	private BooleanQuery buildQuey( AdvanceSearchDTO searchDTO) {
		
		String field = searchDTO.getField();
		BooleanQuery finalQuery = new BooleanQuery();
		if (searchDTO.getWordPhrase() != null && !"".equals(searchDTO.getWordPhrase().trim())) {
			
			String[] phrase = searchDTO.getWordPhrase().split(" ");
			PhraseQuery phraseQuery = new PhraseQuery();
			for (int i=0; i < phrase.length; i++) {
				phraseQuery.add(new Term(field, phrase[i]));
			}
			finalQuery.add(phraseQuery, BooleanClause.Occur.MUST);
		}
		
		
		
		if (searchDTO.getAllWords() != null && !"".equals(searchDTO.getAllWords().trim())) {
			String[] allWordsArr = searchDTO.getAllWords().split(" ");
			BooleanQuery queryAllWord = new BooleanQuery();
			for (int i = 0; i < allWordsArr.length; i++) {
				String string = allWordsArr[i];
				queryAllWord.add(new TermQuery(new Term(field,string)), BooleanClause.Occur.MUST);
			}
			finalQuery.add(queryAllWord,BooleanClause.Occur.MUST);
		}
		if (searchDTO.getOneOrMore() != null && !"".equals(searchDTO.getOneOrMore().trim())) {
			String[] oneOrMoreArr = searchDTO.getOneOrMore().split(" ");
			BooleanQuery queryOneOrMore = new BooleanQuery();
			for (int i = 0; i < oneOrMoreArr.length; i++) {
				String string = oneOrMoreArr[i];
				queryOneOrMore.add(new TermQuery(new Term(field,string)), BooleanClause.Occur.SHOULD);
			}
			finalQuery.add(queryOneOrMore,BooleanClause.Occur.MUST);
		}
		
		if (searchDTO.getNoneWords() != null && !"".equals(searchDTO.getNoneWords().trim())) {
			String[] noneWordsArr = searchDTO.getNoneWords().split(" ");
			for (int i = 0; i < noneWordsArr.length; i++) {
				String string = noneWordsArr[i];
				finalQuery.add(new TermQuery(new Term(field,string)), BooleanClause.Occur.MUST_NOT);
			}
		}
		
		
		Term from = null, to = null;
		if (searchDTO.getFromDate() != null && !"".equals(searchDTO.getFromDate())) {
			from = new Term("lastUpdated",searchDTO.getFromDate().replaceAll("-", ""));
		}
		if (searchDTO.getToDate() != null && !"".equals(searchDTO.getToDate()) ) {
			to = new Term("lastUpdated",searchDTO.getToDate().replaceAll("-", ""));
		}
		if (from != null || to != null) {
			RangeQuery range = new RangeQuery(from,to,true);
			finalQuery.add(range, BooleanClause.Occur.MUST);
		}
		if ("".equals(finalQuery.toString())) {
			finalQuery.add(new WildcardQuery(new Term("id","*")),BooleanClause.Occur.MUST);
		}
		
		return finalQuery;		
	}

}
