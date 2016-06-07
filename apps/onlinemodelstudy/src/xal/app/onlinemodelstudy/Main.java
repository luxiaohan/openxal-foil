/*
 * Main.java
 *
 * Created on May 26, 2016
 */

package xal.app.onlinemodelstudy;

import javax.swing.*;
import javax.swing.JToggleButton.ToggleButtonModel;
import java.awt.event.*;
import java.util.logging.*;

import xal.extension.application.*;
import xal.extension.application.smf.AcceleratorApplication;


/**
 * Main class
 * @author  X.H LU
 */
public class Main extends ApplicationAdaptor {
    // --------- Variables specific to this application ------------------------
    

    // --------- Document management -------------------------------------------
    
    /**
     * Returns the text file suffixes of files this application can open.
     * @return Suffixes of readable files
     */
    public String[] readableDocumentTypes() {
        return new String[] {"txt", "text"};
    }
    
    
    /**
     * Returns the text file suffixes of files this application can write.
     * @return Suffixes of writable files
     */
    public String[] writableDocumentTypes() {
        return new String[] {"txt", "text"};
    }
    
    
    /**
     * Implement this method to return an instance of my custom document.
     * @return An instance of my custom document.
     */
    public XalDocument newEmptyDocument() {
        return new MyDocument();
    }
    
    
    /**
     * Implement this method to return an instance of my custom document 
     * corresponding to the specified URL.
     * @param url The URL of the file to open.
     * @return An instance of my custom document.
     */
    public XalDocument newDocument(java.net.URL url) {
        return new MyDocument(url);
    }
    
    
    // --------- Global application management ---------------------------------
    
    
    /**
     * Specifies the name of my application.
     * @return Name of my application.
     */
    public String applicationName() {
        return "onlinemodelstudy";
    }
    
    
 
        
    
    // --------- Application events --------------------------------------------
    
    /** 
     * Capture the application launched event and print it.  This is an optional
     * hook that can be used to do something useful at the end of the application launch.
     */
    public void applicationFinishedLaunching() {
        System.out.println("Application has finished launching!");
		Logger.getLogger("global").log( Level.INFO, "Application has finished launching." );
    }
    
    
    
    
    /** The main method of the application. */
    static public void main(String[] args) {
        try {
            System.out.println("Starting application...");
			Logger.getLogger("global").log( Level.INFO, "Starting application..." );
            AcceleratorApplication.launch( new Main() );
        }
        catch(Exception exception) {
			Logger.getLogger("global").log( Level.SEVERE, "Error starting application.", exception );
            System.err.println( exception.getMessage() );
            exception.printStackTrace();
			Application.displayApplicationError("Launch Exception", "Launch Exception", exception);
			System.exit(-1);
        }
    }
}
