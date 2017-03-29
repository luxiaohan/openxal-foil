/*
 *  InjMatchModel.java
 *
 *  xal.app.injpointmtch
 *
 *  Created on Mar 28, 2017  11:04:11 AM
 *
 *  Created by luxiaohan (luxh@ihep.ac.cn)
 *
 *  Copyright (c) 2017 China Spallation Neutron Source(CSNS). All Rights Reserved
 * 
 *  DongGuan,GuangDong 523803
 *
 */
package xal.app.injpointmtch;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.mathworks.engine.MatlabEngine;

/**
 * The Injection Point Match Model to handle the match algorithms
 * 
 * @author luxiaohan
 * @version Mar 28, 2017,v 1.0
 * @sine JDK 1.8
 */
public class InjMatchModel {

	/** matlab engine */
	final private Future<MatlabEngine> MATLAB_ENGINE;

	/**
	 * Creates a new instance of InjMatchModel.
	 * 
	 */
	public InjMatchModel() {

		MATLAB_ENGINE = connectToMatlab();

	}

	/**
	 * Connect to Matlab and return the matlab engine
	 * 
	 * @return eng the matlab engine
	 *
	 * @author luxiaohan
	 * @since JDK 1.8
	 * @version Mar 28, 2017,v 1.0
	 */
	private Future<MatlabEngine> connectToMatlab() {

		Future<MatlabEngine> eng = null;

		Future<String[]> engines = MatlabEngine.findMatlabAsync();
		try {
			if (engines.get().length == 0) {

				String[] options = {}; // we can set startup options for MATLAB
										// here.
				eng = MatlabEngine.startMatlabAsync(options);
			} else {
				eng = MatlabEngine.connectMatlabAsync(engines.get()[0]);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return eng;

	}

	/**
	 * get the bpm data from specified file
	 * 
	 * @param file
	 *            the file
	 * @return bpm data
	 *
	 * @author luxiaohan
	 * @since JDK 1.8
	 * @version Mar 29, 2017,v 1.0
	 */
	public List<Double> getBpmData(final File file) {
		DataReader<Double> bpmReader = new DataReader<Double>(file, Double.class);
		List<Double> bpmdata = bpmReader.ReadData();
		return bpmdata;
	}

	/** get matlab engine */
	public Future<MatlabEngine> getMatlabEngine() {
		return MATLAB_ENGINE;
	}

	public void getdata() {
		// MATLAB_ENGINE.get().eval("");
	}

}
