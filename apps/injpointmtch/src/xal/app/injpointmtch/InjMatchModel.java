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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import javax.swing.JFileChooser;
import com.mathworks.engine.EngineException;
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
				String setFolder = "cd " + getMatlabFolder();				
				String[] options = {"-r",setFolder};
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
	
	public String getMatlabFolder() {
		Path path = Paths.get(System.getenv("HOME"));//initial directory
		String mainPath = System.getenv("OPENXAL_HOME");
		String title = "Set Matlab Folder";
		if (mainPath == null) {
			JFileChooser folder = new JFileChooser();
			folder.setDialogTitle(title);
			folder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			folder.showOpenDialog(null);
			path= Paths.get(folder.getSelectedFile().getPath());
		} else {
			path = Paths.get(mainPath,"apps","injpointmtch","matlabsources");
		}		
		return path.toString();
	}

	/**
	 * get the data from specified file
	 * 
	 * @param file
	 *            the file
	 * @return the data
	 *
	 * @author luxiaohan
	 * @since JDK 1.8
	 * @version Mar 29, 2017,v 1.0
	 */
	public List<Double> getDataFromFile(final File file) {
		DataReader<Double> reader = new DataReader<Double>(file, Double.class);
		List<Double> data = reader.ReadData();
		return data;
	}

	public Future<double[]> doFourierTransform(final List<Double> bpmData) {
		List<Double> sList = new ArrayList<Double>(bpmData.size());
		for (int i = 0; i < bpmData.size(); i++) {
			double j = i + 1;
			sList.add(j);
		}
		return doFourierTransform(sList, bpmData);
	}

	public Future<double[]> doFourierTransform(final List<Double> sList, final List<Double> bpmData) {
		Future<double[]> result = null;
		double[] sArray = new double[sList.size()];
		double[] bpmArray = new double[bpmData.size()];
		for(int i=0; i<sList.size(); i++) {
			sArray[i] = sList.get(i);
			bpmArray[i] = bpmData.get(i);			
		}
		try {
			result = MATLAB_ENGINE.get().fevalAsync("javatest", bpmArray);
		} catch (RejectedExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/** get matlab engine */
	public Future<MatlabEngine> getMatlabEngine() {
		return MATLAB_ENGINE;
	}

}
