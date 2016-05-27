/**
 * 
 */
package xal.app.onlinemodelstudy;

import xal.extension.bricks.WindowReference;

/**
 * @author X.H LU
 *The controller connects the Model with User Interface
 */
public class MyController {
	
	/** main window reference */
	final private WindowReference WINDOW_REFERENCE;
	
	
	/**The Constructor*/
	public MyController( final WindowReference windowReference ){
		WINDOW_REFERENCE = windowReference;
		setupMainView( WINDOW_REFERENCE );
	}
	
	/**setup the main view*/
	private void setupMainView( final WindowReference windowReference ) {
		
	}

}
