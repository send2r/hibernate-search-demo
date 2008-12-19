package demo.hibernatesearch.application;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ApplicationInit extends HttpServlet{
	private static final long serialVersionUID = -5930983748137897247L;
	private static Log log = LogFactory.getLog(ApplicationInit.class);
	public ApplicationInit() {
		super();
	}
	public void init() throws ServletException {
		log.info("Hibernate Search Demo start ...............");
		getServletContext().setAttribute(Constants.PAGER_MODEL, ManagerResource.getPagerModel());
		try {
			getServletContext().setAttribute(Constants.TEMPLATE_CONFIG, ManagerResource.getTemplateConfig(getServletContext()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
