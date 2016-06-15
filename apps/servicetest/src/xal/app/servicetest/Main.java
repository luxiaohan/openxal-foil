/**
 * 
 */
package xal.app.servicetest;

import java.net.URL;

import xal.extension.application.Application;
import xal.extension.application.ApplicationAdaptor;
import xal.extension.application.XalDocument;

/**
 * @author xl7
 *
 */
public class Main extends ApplicationAdaptor{
	
	

	@Override
	public XalDocument newEmptyDocument() {
		return new WorkDocument();
	}

	@Override
	public XalDocument newDocument(URL url) {
		return newEmptyDocument();
	}

	@Override
	public String[] readableDocumentTypes() {
		return new String[0];
	}

	@Override
	public String[] writableDocumentTypes() {
		return new String[0];
	}

	@Override
	public String applicationName() {
		return "Service Test";
	}
	
	public static void main( String[] args ) {
        try {
            System.out.println( "Service Test..." );
            Application.launch( new Main() );
        }
        catch(Exception exception) {
            System.err.println( exception.getMessage() );
            exception.printStackTrace();
			Application.displayApplicationError( "Launch Exception", "Launch Exception", exception );
			System.exit( -1 );
        }
	}

}
