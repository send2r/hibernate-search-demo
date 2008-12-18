package demo.hibernatesearch.action;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.model.User;
import demo.hibernatesearch.service.ResumeManager;


public class PersonAction implements Preparable {
    
	@Autowired
	private ResumeManager resumeManager;


    public ResumeManager getResumeManager() {
		return resumeManager;
	}

	public void setResumeManager(ResumeManager resumeManager) {
		this.resumeManager = resumeManager;
	}

	public String execute() throws Exception {
    	User applicant1 = new User();
		applicant1.setFirstName("Mike");
		applicant1.setLastName("Wilson");
		applicant1.setMiddleName("Lee");
		applicant1.setEmailAddress("mwilson@demohibernatesearch.com");

		Set<Resume> resumes = new HashSet<Resume>();
		applicant1.setResumes(resumes);

		Resume resume1 = new Resume();
		resume1.setApplicant(applicant1);
		resume1.setSummary("A Java developer with Struts, Webwork, JSF, and Wicket based web application development experience");
		resume1.setLastUpdated(new GregorianCalendar(2008, 1, 20).getTime());
		resume1.setContent("Nguyen congson".getBytes());
		resumeManager.saveApplicant(applicant1);
		System.out.println(">>>>>>>>>>>>heheheh save");
       return Action.SUCCESS;
    }

	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

    
}
