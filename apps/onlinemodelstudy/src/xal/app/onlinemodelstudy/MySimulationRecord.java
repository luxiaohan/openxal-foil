/**
 * 
 */
package xal.app.onlinemodelstudy;

import xal.model.probe.traj.ProbeState;
import xal.tools.beam.PhaseVector;
import xal.tools.beam.Twiss;
import xal.tools.beam.calc.SimResultsAdaptor;
import xal.tools.math.r3.R3;

/**
 * @author xl7
 *
 */
public class MySimulationRecord {
	
	/** probe state wrapped by this record */
	private final ProbeState<?> PROBE_STATE;

	/** twiss parameters */
	private final Twiss[] TWISS_PARAMETERS;

	/** betatron phase */
	private final R3 BETATRON_PHASE;
	
	/**phase vector*/
	private final PhaseVector PHASE_VECTOR;
	
	
	/** Constructor */
    public MySimulationRecord( final SimResultsAdaptor resultsAdaptor, final ProbeState<?> probeState ) {
		PROBE_STATE = probeState;		
		TWISS_PARAMETERS = resultsAdaptor.computeTwissParameters( probeState );
		BETATRON_PHASE = resultsAdaptor.computeBetatronPhase( probeState );
		PHASE_VECTOR = resultsAdaptor.computeFixedOrbit( probeState );
    }

}
