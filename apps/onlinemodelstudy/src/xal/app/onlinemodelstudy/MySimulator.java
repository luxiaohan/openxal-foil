/**
 * 
 */
package xal.app.onlinemodelstudy;

import java.util.logging.Level;
import java.util.logging.Logger;

import xal.model.IAlgorithm;
import xal.model.ModelException;
import xal.model.alg.TransferMapTracker;
import xal.model.probe.EnvelopeProbe;
import xal.model.probe.Probe;
import xal.model.probe.TransferMapProbe;
import xal.sim.scenario.AlgorithmFactory;
import xal.sim.scenario.ProbeFactory;
import xal.sim.scenario.Scenario;
import xal.smf.AcceleratorSeq;
import xal.smf.Ring;

/**
 * @author X.H LU
 *The simulator executes the simulation 
 */
public class MySimulator {
	
	/**the sequence*/
	private AcceleratorSeq _sequence;
	/**the entrance probe*/
	private Probe<?> _entranceProbe;
	/**the scenario*/
	private Scenario _scenario;
	/**the running status*/
	private boolean _isRunning;
	
	
	/**The Constructor*/
	public MySimulator( final AcceleratorSeq sequence ) {
		_isRunning = false;	
	}
	
	
	/**
	 * set the sequence and entrance probe
	 * @param sequence The sequence
	 * @param entranceProbe The entrance probe
	 * @throws ModelException Exception
	 */
	public void setSequenceProbe( final AcceleratorSeq sequence, final Probe<?> entranceProbe ) throws ModelException {
		if ( !_isRunning ) {
			
			_sequence = sequence;
			
			_entranceProbe = entranceProbe != null ? entranceProbe : sequence != null ? getDefaultProbe( sequence ) : null;
			
			configScenario();
		}
		else {
			throw new RuntimeException( "Can't change sequence while a simulation is in progress!" );
		}
	}
	
	/**Set the sequence*/
	public void setSequence(final AcceleratorSeq sequence ) throws ModelException {
		setSequenceProbe( sequence, null );
	}
	
    /** Create a new scenario */
    private void configScenario() throws ModelException {    	
        if ( _sequence != null ) {
            _scenario = Scenario.newScenarioFor( _sequence );
        }
        else {
            _scenario = null;
        }
    }
	
	/**
	 * Run the simulation.
	 * @return the generated simulation or null if the run failed.
	 */
	public void run() {
		try {
			_isRunning = true;
//			final Probe<?> probe = copyProbe( _entranceProbe );	// perform a deep copy of the entrance probe leaving the entrance probe unmodified
			final Probe<?> probe = _entranceProbe;
            _scenario.setProbe( probe );
			_scenario.resync();
			_scenario.run();
		}
		catch( Exception exception ) {
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log( Level.SEVERE, "Exception running the online model.", exception );
			exception.printStackTrace();
			System.out.println( "online model calculation failed..." );
		}
		finally {
			_isRunning = false;
		}
	}
	
	
	
	
	
	
	
	/**
	 * Get the default probe for the specified sequence.
	 * @param sequence the sequence for which to get the default probe
	 * @return the default probe for the specified sequence
	 */
	private Probe<?> getDefaultProbe( final AcceleratorSeq sequence ) {
		try {
			final Probe<?> probe = ( sequence instanceof Ring ) ? createRingProbe( sequence ) : createEnvelopeProbe( sequence );
//			probe.getAlgorithm().setRfGapPhaseCalculation( _useRFGapPhaseSlipCalculation );
			return probe;
		}
		catch( InstantiationException exception ) {
			exception.printStackTrace();
			throw new RuntimeException( "Exception creating the default probe.", exception );
		}
	}
	
	/** create a new ring probe */
	private TransferMapProbe createRingProbe( final AcceleratorSeq sequence ) throws InstantiationException {
		final TransferMapTracker tracker = AlgorithmFactory.createTransferMapTracker( sequence );
		return ProbeFactory.getTransferMapProbe( sequence, tracker );
	}


	/** create a new envelope probe */
	private EnvelopeProbe createEnvelopeProbe( final AcceleratorSeq sequence ) throws InstantiationException {
		final IAlgorithm tracker = AlgorithmFactory.createEnvTrackerAdapt( sequence );
		return ProbeFactory.getEnvelopeProbe( sequence, tracker );
	}
	
	

}
