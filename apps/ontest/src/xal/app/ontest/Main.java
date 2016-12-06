/**
 * 
 */
package xal.app.ontest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author xl7
 *
 */
public class Main {
	
	public static void main( String[] args ) throws IOException {
		TestVice test = new TestVice();
		
		String flag="allfringe";
		
		String tuneFlag="tune"+flag+".txt";
		String betaFlag="beta"+flag+".txt";
        File fileOutputTune = new File("/home/luxiaohan/workspace/MATLABATROOT/openxal/",tuneFlag);
        FileWriter writerTune = new FileWriter(fileOutputTune);
        
        File fileOutput = new File("/home/luxiaohan/workspace/MATLABATROOT/openxal/",betaFlag);
        FileWriter writerBeta = new FileWriter(fileOutput);
        test.computeRingTunes( writerTune );
 //       test.testComputeTwissParameters();
        test.computeTwissParameters( writerBeta );
//        test.testTurnByTurnResponse();
	}
}
