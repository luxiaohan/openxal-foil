/**
 * 
 */
package xal.app.onlinemodelstudy;

import xal.model.ModelException;
import xal.smf.AcceleratorSeq;

/**
 * The model holds the simulator and data
 * @author X.H LU
 *
 */
public class MyModel {
	
	/**The Simulator*/
	final private MySimulator SIMULATOR;
	
	
	/**The Constructor*/
	public MyModel() {
		SIMULATOR = new MySimulator(null);
	}
	
	/**Set the Accelerator sequence*/
	public void setSequence ( final AcceleratorSeq sequence ) throws ModelException {
		SIMULATOR.setSequence( sequence );
	}
	
	/**run the model*/
	public void runModel() {
		SIMULATOR.run();
	}

}
