package demo.pyco.handler;

import org.apache.lucene.document.Document;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * A FileHandler implementation that delegates responsibility to appropriate
 * DocumentHandler implementation, based on a file extension.
 */
public class ExtensionFileHandler implements FileHandler {
	private Properties handlerProps;

	public ExtensionFileHandler(Properties props) {
		handlerProps = props;
	}

	public Document getDocument(File file) throws FileHandlerException {

		String name = file.getName();
		int dotIndex = name.indexOf(".");
		if ((dotIndex > 0) && (dotIndex < name.length())) {
			String ext = name.substring(dotIndex + 1, name.length());
			String handlerClassName = handlerProps.getProperty(ext);

			if (handlerClassName == null)
				return null;

			try {
				Class handlerClass = Class.forName(handlerClassName);
				DocumentHandler handler = (DocumentHandler) handlerClass
						.newInstance();
				return handler.getDocument(new FileInputStream(file));
			} catch (ClassNotFoundException e) {
				throw new FileHandlerException("Cannot create instance of : "
						+ handlerClassName, e);
			} catch (InstantiationException e) {
				throw new FileHandlerException("Cannot create instance of : "
						+ handlerClassName, e);
			} catch (IllegalAccessException e) {
				throw new FileHandlerException("Cannot create instance of : "
						+ handlerClassName, e);
			} catch (FileNotFoundException e) {
				throw new FileHandlerException("File not found: "
						+ file.getAbsolutePath(), e);
			} catch (DocumentHandlerException e) {
				throw new FileHandlerException("Document cannot be handler: "
						+ file.getAbsolutePath(), e);
			}
		}
		return null;
	}
}
