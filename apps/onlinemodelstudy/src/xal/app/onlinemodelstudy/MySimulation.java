/**
 * 
 */
package xal.app.onlinemodelstudy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import xal.model.probe.Probe;
import xal.model.probe.traj.ProbeState;
import xal.model.probe.traj.Trajectory;
import xal.tools.beam.calc.SimpleSimResultsAdaptor;

/**
 * @author xl7
 *
 */
public class MySimulation {
	
    /** states for every element */
    final List<MySimulationRecord> SIMULATION_RECORDS;
    /**the trajectory*/
    private Trajectory<?> trajectory;
    
	/** Constructor */
    public MySimulation( final Probe<?> probe ) {
        trajectory = probe.getTrajectory();
		final SimpleSimResultsAdaptor resultsAdaptor = new SimpleSimResultsAdaptor( trajectory );
		
        SIMULATION_RECORDS = new ArrayList<MySimulationRecord>( trajectory.numStates() );
        final Iterator<? extends ProbeState<?>> stateIter = trajectory.stateIterator();
        while ( stateIter.hasNext() ) {
            final ProbeState<?> state = stateIter.next();
			final MySimulationRecord simulationRecord = new MySimulationRecord( resultsAdaptor, state );
            SIMULATION_RECORDS.add( simulationRecord );
        }
    }
    
    /**
     * get the simulation records
     * @return
     */
    public List<MySimulationRecord> getRecords() {
    	return SIMULATION_RECORDS;
    }
}
