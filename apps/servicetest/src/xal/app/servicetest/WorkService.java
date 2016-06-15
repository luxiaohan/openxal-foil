/**
 * 
 */
package xal.app.servicetest;

import java.util.Date;

import xal.service.worker.Working;

/**
 * @author xl7
 *
 */
public class WorkService {
	
	/**The proxy of Worker*/
	final Working PROXY;
	
	public WorkService( final Working proxy ){
		PROXY = proxy;
	}

	public double add(double summand, double addend) {
		return PROXY.add( summand, addend );
	}

	public int sumIntegers(int[] summands) {
		return PROXY.sumIntegers( summands );
	}

	public Date getLaunchTime() {
		return PROXY.getLaunchTime();
	}

	public double[] generateSinusoid(double amplitude, double frequency, double phase, int numPoints) {
		return PROXY.generateSinusoid( amplitude, frequency, phase, numPoints );
	}

	public String sayHelloTo(String name) {
		return PROXY.sayHelloTo( name );
	}

	public void shutdown(int code) {
		PROXY.shutdown( code );
	}

}
