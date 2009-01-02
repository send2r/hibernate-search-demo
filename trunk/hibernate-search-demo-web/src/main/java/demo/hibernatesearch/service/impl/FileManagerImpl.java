package demo.hibernatesearch.service.impl;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import demo.hibernatesearch.application.ManagerResource;
import demo.hibernatesearch.model.FileUploadDTO;
import demo.hibernatesearch.service.FileManager;

@Service("fileManager")
public class FileManagerImpl implements FileManager{

	public void indexFile(FileUploadDTO file) throws Exception{
		// TODO Auto-generated method stub
		String fullFileName = ManagerResource.getUploadFolder() + "/" + file.getFileName();
		File theFile = new File(fullFileName);
		FileUtils.copyFile(file.getFileUpload(), theFile);
	}

}
