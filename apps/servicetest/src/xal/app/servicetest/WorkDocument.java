/**
 * 
 */
package xal.app.servicetest;

import java.net.URL;

import xal.extension.application.XalDocument;
import xal.extension.application.XalWindow;
import xal.extension.bricks.WindowReference;

/**
 * @author xl7
 *
 */
public class WorkDocument extends XalDocument {
	
	/**The Window Reference*/
	final private WindowReference WINDOW_REFERENCE;
	/**The work controller*/
	final private WorkController WORK_CONTROLLER;
	
	public WorkDocument() {
		setSource(null);
		WINDOW_REFERENCE = getDefaultWindowReference( "MainWindow", this );
		WORK_CONTROLLER = new WorkController( WINDOW_REFERENCE );
	}

	@Override
	public void makeMainWindow() {
		mainWindow = (XalWindow)WINDOW_REFERENCE.getWindow();	
	}

	@Override
	public void saveDocumentAs(URL url) {
		// TODO Auto-generated method stub
		
	}

}
