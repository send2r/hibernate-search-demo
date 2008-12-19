package demo.hibernatesearch.application;

import java.io.File;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import demo.hibernatesearch.taglib.pager.PagerModel;
import freemarker.template.DefaultObjectWrapper;

public class ManagerResource {
	public static PagerModel getPagerModel(){
		PagerModel pagerModel = new PagerModel();
		pagerModel.setLayout("first-previous-info1-next-last-info2-input");
        pagerModel.setInfo1("Page <strong>#pageIndex#</strong> of #totalPages#");
        pagerModel.setInfo2("| Go to page");
        pagerModel.setFirstText("|&laquo;");
        pagerModel.setPreviousText("&laquo;");
        pagerModel.setNextText("&raquo;");
        pagerModel.setLastText("&raquo;|");
        pagerModel.setItemName("item");
		return pagerModel;
	}
	
	public static freemarker.template.Configuration getTemplateConfig(ServletContext context) throws Exception {
		freemarker.template.Configuration templateConfig = null;
        if (templateConfig == null) {
            templateConfig = new freemarker.template.Configuration();
            String templatesUri = "/templates/taglib";
            String dirPath = context.getRealPath(templatesUri);
            templateConfig.setDirectoryForTemplateLoading(new File(dirPath));
            templateConfig.setObjectWrapper(new DefaultObjectWrapper());
        }
        return templateConfig;
    }
}
