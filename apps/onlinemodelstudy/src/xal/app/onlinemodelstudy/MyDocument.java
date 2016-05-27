/*
 * MyDocument.java
 *
 * Created on May 26, 2016
 */

package xal.app.onlinemodelstudy;

import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.logging.*;

import xal.extension.application.*;
import xal.extension.application.smf.AcceleratorDocument;
import xal.extension.application.smf.DefaultAcceleratorWindow;
import xal.extension.bricks.WindowReference;
import xal.model.ModelException;
import xal.smf.Accelerator;
import xal.tools.data.DataAdaptor;
import xal.tools.data.DataListener;
import xal.tools.xml.XmlDataAdaptor;

/**
 * MyDocument is a custom XalDocument for my application.  Each document instance 
 * manages a single plain text document.  The document manages the data that is 
 * displayed in the window.
 *
 * @author  X.H LU
 */
public class MyDocument extends AcceleratorDocument implements DataListener{
	
 	/** the data adaptor label used for reading and writing this document */
	static public final String DATA_LABEL = "MyDocument";
	/** main window reference */
	final private WindowReference WINDOW_REFERENCE;
	/**the controller*/
	final private MyController MY_CONTROLLER;
	/**the model*/
	final private MyModel MODEL;
    
    /** Create a new empty document */
    public MyDocument() {
        this(null);
    }
   
    /** 
     * Create a new document loaded from the URL file 
     * @param url The URL of the file to load into the new document.
     */
    public MyDocument( java.net.URL url) {
        setSource(url);
        
        WINDOW_REFERENCE = getDefaultWindowReference( "MainWindow", this );
        MY_CONTROLLER = new MyController( WINDOW_REFERENCE );
        MODEL = new MyModel();
        
		if ( url != null ) {
            System.out.println( "Opening document: " + url.toString() );
            final DataAdaptor documentAdaptor = XmlDataAdaptor.adaptorForUrl( url, false );
            update( documentAdaptor.childAdaptor( dataLabel() ) );
        }
		
		setHasChanges( false );
    }
    
    
    /**
     * Make a main window by instantiating the my custom window.  Set the text 
     * pane to use the textDocument variable as its document.
     */
    public void makeMainWindow() {
        mainWindow = (XalWindow) WINDOW_REFERENCE.getWindow();
        setHasChanges(false);
    }

    
    /**
     * Save the document to the specified URL.
     * @param url The URL to which the document should be saved.
     */
    public void saveDocumentAs(URL url) { 	
    	writeDataTo( this, url);
	}
        
    
    /**
     * Register custom actions for the document.
     * @param commander The commander with which to register the custom commands.
     */
    public void customizeCommands( final Commander commander ) {
        // define the "Export" demo action
        final Action exportAction = new AbstractAction( "export-data" ) {
            /** serialization identifier */
            private static final long serialVersionUID = 1L;
            
            public void actionPerformed( final ActionEvent event ) {
                System.out.println( "Exporting data..." );
				Logger.getLogger("global").log( Level.INFO, "Exporting data." );
                displayConfirmDialog( "Demo Export", "Just simulating the export of data for demo. No data actually exported..." );
            }
        };
        commander.registerAction( exportAction );
    }
    
    @Override
    public void acceleratorChanged() {
    	super.acceleratorChanged();
    	try {
			MODEL.setSequence( null );
		} catch (ModelException e) {
			e.printStackTrace();
			displayError("Error Setting Accelerator", "Simulator Configuration Exception", e);
		}
    }
    
    @Override
    public void selectedSequenceChanged() {
    	super.selectedSequenceChanged();
    	try {
			MODEL.setSequence( getSelectedSequence() );
		} catch (ModelException e) {
			e.printStackTrace();
			displayError("Error Setting Accelerator", "Simulator Configuration Exception", e);
		}
    }


	@Override
	public String dataLabel() {
		return DATA_LABEL;
	}


	@Override
	public void update(DataAdaptor adaptor) {
		
	}


	@Override
	public void write(DataAdaptor adaptor) {	
		// TODO Auto-generated method stub
		
	}
}
