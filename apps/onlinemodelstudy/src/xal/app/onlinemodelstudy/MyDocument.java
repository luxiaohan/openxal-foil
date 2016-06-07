/*
 * MyDocument.java
 *
 * Created on May 26, 2016
 */

package xal.app.onlinemodelstudy;

import java.net.*;
import javax.swing.*;
import javax.swing.JToggleButton.ToggleButtonModel;

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
	
    
    // simple instance variable to hold state for the Demo application
    private boolean isRunning = false;
    private boolean isPaused = false;
    private Action startAction;
    private Action stopAction;
    private Action pauseAction;
    
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
     * get the model
     * @return
     */
    public MyModel getModel() {
    	return MODEL;
    }
       
    
    /**
     * Implement a preference panel for the document in this case.  One could 
     * implement application wide preferences or a combination of both application and 
     * document preferences.
     * This method is optional to define.  If this method is missing, the preferences menu
     * item will be disabled.  Here we demonstrate how to implement a simple preference panel
     * for a document.
     * @param document The document whose preferences are being changed.  Subclass may ignore.
     */
    public void editPreferences(XalDocument document) {
//        ((MyDocument)document).editPreferences();
    }
    
    
    /**
     * Register actions for the Special menu items.  These actions simply 
     * print to standard output or standard error.
     * This code demonstrates how to define custom actions for menus and the toolbar
     * This method is optional.  You may similarly define actions in the document class
     * if those actions are document specific.  Here the actions are application wide.
     * @param commander The commander with which to register the custom commands.
     */
    public void customizeCommands(Commander commander) {
    	
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
        
        
        // define the "start run" demo action
        startAction = new AbstractAction( "start-run" ) {
            /** serialization identifier */
            private static final long serialVersionUID = 1L;
            
            public void actionPerformed(ActionEvent event) {
            	MODEL.runModel();
                isRunning = true;
                isPaused = false;
                updateActions();        // update the menu item enable state for user feedback
                System.out.println("Starting the run...");
				Logger.getLogger("global").log( Level.INFO, "Starting the run." );
            }
        };
        commander.registerAction(startAction);
        
        // define the "pause run" demo action
        pauseAction = new AbstractAction( "pause-run" ) {
            /** serialization identifier */
            private static final long serialVersionUID = 1L;
            
            public void actionPerformed(ActionEvent event) {
                isRunning = false;
                isPaused = true;
                updateActions();        // update the menu item enable state for user feedback
                System.out.println("Pausing the run...");
				Logger.getLogger("global").log( Level.INFO, "Pausing the run." );
            }
        };        
        commander.registerAction(pauseAction);
        
        
        // define the "stop run" demo action
        stopAction = new AbstractAction( "stop-run" ) {
            /** serialization identifier */
            private static final long serialVersionUID = 1L;
            
            public void actionPerformed(ActionEvent event) {
                isRunning = false;
                isPaused = false;
                updateActions();        // update the menu item enable state for user feedback
                System.err.println( "Stopping the run..." );
				Logger.getLogger( "global" ).log( Level.INFO, "Stopping the run." );
            }
        };        
        commander.registerAction(stopAction);
        
        
        // define the "whatif mode" button model
		ToggleButtonModel whatifModel = new ToggleButtonModel();
		whatifModel.setSelected(true);
		whatifModel.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("changed to whatif mode...");
				Logger.getLogger("global").log( Level.INFO, "Changed to whatif mode." );
            }
		});
        commander.registerModel("whatif-mode", whatifModel);
        
        
        // define the "live mode" button model
		ToggleButtonModel liveModel = new ToggleButtonModel();
		liveModel.setSelected(false);
		liveModel.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("changed to live mode...");
				Logger.getLogger("global").log( Level.INFO, "Changed to live mode." );
            }
		});
        commander.registerModel("live-mode", liveModel);

        
        updateActions();
    }
    
    
    /**
     * Update the action enable states.  This method demonstrates how to enable
     * or disable menu or toolbar items.  Often this is unnecessary, but it is
     * included to demonstrate how to do this if you wish to provide this kind
     * of feedback to the user.
     */
    protected void updateActions() {
        startAction.setEnabled(!isRunning);
        pauseAction.setEnabled(isRunning);
        stopAction.setEnabled(isRunning || isPaused);
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
