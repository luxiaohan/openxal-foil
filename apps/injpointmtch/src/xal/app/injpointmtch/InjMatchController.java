/*
 *  InjMatchController.java
 *
 *  xal.app.injpointmtch
 *
 *  Created on Mar 28, 2017  11:22:02 AM
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
import java.util.concurrent.Future;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.mathworks.engine.MatlabEngine;

import xal.extension.bricks.WindowReference;
import xal.extension.widgets.plot.FunctionGraphsJPanel;

/**
 * Controller for binding the Injection Match model to the user interface
 * 
 * @author luxiaohan
 * @version Mar 28, 2017,v 1.0
 * @sine JDK 1.8
 */
public class InjMatchController {

	/** The Injection match model */
	final private InjMatchModel MODEL;
	/** the Document */
	final private InjMatchDocument DOCUMENT;
	/** X plane */
	private JCheckBox xSelectionCheckbox;
	/** Y plane */
	private JCheckBox ySelectionCheckbox;
	/** Matlab Connect Button */
	private JButton matlabButton;
	/** matlab engine */
	private Future<MatlabEngine> matlabEngine;
	/** bpm data*/
	private List<Double> bpmData;

	/** Constructor */
	public InjMatchController(final InjMatchDocument document, final WindowReference windowReference) {
		DOCUMENT = document;
		MODEL = document.getModel();
		initMainWindow(windowReference);
		matlabEngine = MODEL.getMatlabEngine();

	}

	/** initialize main window */
	private void initMainWindow(final WindowReference windowReference) {
		makeNewParametersView(windowReference);
		makeDiagnosticsView(windowReference);
		makeMatrixPlotView(windowReference);
		makeCalculationView(windowReference);
		makeCalculationHistoryView(windowReference);

		xSelectionCheckbox = (JCheckBox) windowReference.getView("X Selection Checkbox");
		ySelectionCheckbox = (JCheckBox) windowReference.getView("Y Selection Checkbox");

		matlabButton = (JButton) windowReference.getView("Matlab Button");

		matlabButton.addActionListener(event -> {
			try {
				matlabEngine.get().evalAsync("pwd");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

	/** setup new parameters view */
	private void makeNewParametersView(final WindowReference windowReference) {

	}

	/** setup diagnostics view */
	private void makeDiagnosticsView(final WindowReference windowReference) {

	}

	/** setup matrix plot view */
	private void makeMatrixPlotView(final WindowReference windowReference) {

	}

	/** setup calculation view */
	private void makeCalculationView(final WindowReference windowReference) {
		final JButton getBPMDataButton = (JButton) windowReference.getView("Get BPM Data");
		final JRadioButton bpmLocal = (JRadioButton) windowReference.getView("Get BPM Local");
		final JRadioButton bpmDatabase = (JRadioButton) windowReference.getView("Get BPM Database");
		final JRadioButton bpmOnline = (JRadioButton) windowReference.getView("Get BPM Online");

		bpmDatabase.setEnabled(false);
		bpmOnline.setEnabled(false); // this button disable for now

		final ButtonGroup bpmSources = new ButtonGroup();
		bpmLocal.setSelected(true);
		bpmSources.add(bpmLocal);
		bpmSources.add(bpmDatabase);
		bpmSources.add(bpmOnline);

		final JTextField bunchNumber = (JTextField) windowReference.getView("Bunch Number");
		final JTextField turns = (JTextField) windowReference.getView("Turns");

		final LocalFileChooser bpmChooser = new LocalFileChooser("BPM DATA");
		

		// add actionListener to these Get BPM Data Button
		getBPMDataButton.addActionListener(event -> {
			if (bpmSources.isSelected(bpmLocal.getModel())) {
				File file = bpmChooser.showDialog(DOCUMENT.mainWindow);
				if (file != null){
					bpmData = MODEL.getBpmData(file);				
				}
			}

		});

		final JButton getMatrixButton = (JButton) windowReference.getView("Get Matrix");
		final JRadioButton matrixLocal = (JRadioButton) windowReference.getView("Get Matrix Local");
		final JRadioButton matrixDatabase = (JRadioButton) windowReference.getView("Get Matrix Database");
		final JRadioButton matrixOnline = (JRadioButton) windowReference.getView("Get Matrix Online");

		matrixDatabase.setEnabled(false);
		matrixOnline.setEnabled(false); // set disable for now

		final ButtonGroup matrixSources = new ButtonGroup();
		matrixLocal.setSelected(true);
		matrixSources.add(matrixLocal);
		matrixSources.add(matrixDatabase);
		matrixSources.add(matrixOnline);

		final JTextField x_A11 = (JTextField) windowReference.getView("X A11");
		final JTextField x_A21 = (JTextField) windowReference.getView("X A21");
		final JTextField x_A12 = (JTextField) windowReference.getView("X A12");
		final JTextField x_A22 = (JTextField) windowReference.getView("X A22");
		final JTextField xTune = (JTextField) windowReference.getView("X Tune");
		final JTextField y_A11 = (JTextField) windowReference.getView("Y A11");
		final JTextField y_A21 = (JTextField) windowReference.getView("Y A21");
		final JTextField y_A12 = (JTextField) windowReference.getView("Y A12");
		final JTextField y_A22 = (JTextField) windowReference.getView("Y A22");
		final JTextField yTune = (JTextField) windowReference.getView("Y Tune");

		final JFileChooser matrixChooser = new JFileChooser();

		getMatrixButton.addActionListener(event -> {
			if (matrixSources.isSelected(matrixLocal.getModel())) {
				matrixChooser.setDialogTitle("Get Matrix From Local File");
				int selectedsate = matrixChooser.showOpenDialog(DOCUMENT.mainWindow);
				if (selectedsate == JFileChooser.APPROVE_OPTION) {
					File matrixFile = matrixChooser.getSelectedFile();
					System.out.println(matrixFile.getPath());
				}
			}
		});

		final JButton fourierTransform = (JButton) windowReference.getView("Fourier Transform");
		final FunctionGraphsJPanel bpmTimeGraph = (FunctionGraphsJPanel) windowReference.getView("BPM Time Data Graph");
		final FunctionGraphsJPanel bpmFrequencyGraph = (FunctionGraphsJPanel) windowReference
				.getView("BPM Frequency Graph");
		
		fourierTransform.addActionListener(event -> {
			if (bpmData != null && bpmData.size() != 0) {
				for (Double l : bpmData) {
					System.out.println(l);
				}
			}
		});

		final JFormattedTextField Re_Xv = (JFormattedTextField) windowReference.getView("Re X");
		final JFormattedTextField Im_Xv = (JFormattedTextField) windowReference.getView("Im X");
		final JFormattedTextField Re_Yv = (JFormattedTextField) windowReference.getView("Re Y");
		final JFormattedTextField Im_Yv = (JFormattedTextField) windowReference.getView("Im Y");
		final JButton calculationButton = (JButton) windowReference.getView("Calculation Button");

		final JFormattedTextField X_injPoint = (JFormattedTextField) windowReference.getView("X Displacement");
		final JFormattedTextField X_injAngle = (JFormattedTextField) windowReference.getView("X Angle");
		final JFormattedTextField Y_injPoint = (JFormattedTextField) windowReference.getView("Y Displacement");
		final JFormattedTextField Y_injAngle = (JFormattedTextField) windowReference.getView("Y Angle");

	}

	/** setup calculation history view */
	private void makeCalculationHistoryView(final WindowReference windowReference) {

	}

	static class EngSwingWorker extends SwingWorker<Double, Object> {
		Future<Double> future;

		public EngSwingWorker(Future<Double> future_) {
			future = future_;

		}

		@Override
		public Double doInBackground() {
			double result = 0;
			try {
				result = future.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void done() {
			try {
				System.out.println("Factorial: " + String.valueOf(this.get()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
