/**
 * 
 */
package xal.app.ontest;

import java.io.IOException;

/**
 * @author xl7
 *
 */
public class Main {
	
	public static void main( String[] args ) throws IOException {
		TestVice test = new TestVice();
        test.testComputeRingTunes();
        test.testComputeTwissParameters();
//        test.testTurnByTurnResponse();
	}

}
