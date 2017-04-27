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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;
import javax.swing.SwingWorker;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

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
	/** the bpm plot panel */
	private InjMatchPlot bpmPlotPanel;
	/** the frequency plot panel */
	private InjMatchPlot freqPlotPanel;
	/** X plane */
	private JCheckBox xSelectionCheckbox;
	/** Y plane */
	private JCheckBox ySelectionCheckbox;
	/** Matlab Connect Button */
	private JButton matlabButton;
	/** matlab engine */
	private Future<MatlabEngine> matlabEngine;
	/** bpm data */
	private List<Double> bpmData;
	/** bpm freq result */
	private Future<double[]> bpmFreqData;

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
		
		final NumberFormat textFormat = NumberFormat.getNumberInstance();
		final DefaultFormatterFactory formatterFactory = new DefaultFormatterFactory(new NumberFormatter(textFormat));

		final JFormattedTextField bunchNumber = (JFormattedTextField) windowReference.getView("Bunch Number");
		bunchNumber.setFormatterFactory(formatterFactory);		
		final JFormattedTextField turns = (JFormattedTextField) windowReference.getView("Turns");
		turns.setFormatterFactory(formatterFactory);

		final LocalFileChooser bpmChooser = new LocalFileChooser("BPM DATA");

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

		final JFormattedTextField x_A11 = (JFormattedTextField) windowReference.getView("X A11");
		final JFormattedTextField x_A21 = (JFormattedTextField) windowReference.getView("X A21");
		final JFormattedTextField x_A12 = (JFormattedTextField) windowReference.getView("X A12");
		final JFormattedTextField x_A22 = (JFormattedTextField) windowReference.getView("X A22");
		final JFormattedTextField xTune = (JFormattedTextField) windowReference.getView("X Tune");
		final JFormattedTextField y_A11 = (JFormattedTextField) windowReference.getView("Y A11");
		final JFormattedTextField y_A21 = (JFormattedTextField) windowReference.getView("Y A21");
		final JFormattedTextField y_A12 = (JFormattedTextField) windowReference.getView("Y A12");
		final JFormattedTextField y_A22 = (JFormattedTextField) windowReference.getView("Y A22");
		final JFormattedTextField yTune = (JFormattedTextField) windowReference.getView("Y Tune");

		final JFileChooser matrixChooser = new JFileChooser();

		final JButton fourierTransform = (JButton) windowReference.getView("Fourier Transform");
		final JButton fourierFit = (JButton) windowReference.getView("Fourier Fit");
		final JButton freqPlotButton = (JButton) windowReference.getView("Freq Plot");
		final FunctionGraphsJPanel bpmTimeGraph = (FunctionGraphsJPanel) windowReference.getView("BPM Time Data Graph");
		final FunctionGraphsJPanel bpmFreqGraph = (FunctionGraphsJPanel) windowReference.getView("BPM Frequency Graph");
		bpmPlotPanel = new InjMatchPlot(bpmTimeGraph);
		freqPlotPanel = new InjMatchPlot(bpmFreqGraph);

		final JFormattedTextField Re_Xv = (JFormattedTextField) windowReference.getView("Re X");
		final JFormattedTextField Im_Xv = (JFormattedTextField) windowReference.getView("Im X");
		final JFormattedTextField Re_Yv = (JFormattedTextField) windowReference.getView("Re Y");
		final JFormattedTextField Im_Yv = (JFormattedTextField) windowReference.getView("Im Y");
		final JButton calculationButton = (JButton) windowReference.getView("Calculation Button");

		final JFormattedTextField X_injPoint = (JFormattedTextField) windowReference.getView("X Displacement");
		final JFormattedTextField X_injAngle = (JFormattedTextField) windowReference.getView("X Angle");
		final JFormattedTextField Y_injPoint = (JFormattedTextField) windowReference.getView("Y Displacement");
		final JFormattedTextField Y_injAngle = (JFormattedTextField) windowReference.getView("Y Angle");

		/************************************************************
		 * Add action
		 *****************************/
		// add actionListener to these Get BPM Data Button
		getBPMDataButton.addActionListener(event -> {
			if (bpmSources.isSelected(bpmLocal.getModel())) {
				File file = bpmChooser.showDialog(DOCUMENT.mainWindow);
				if (file != null) {
					bpmData = MODEL.getDataFromFile(file);
					turns.setText(String.valueOf(bpmData.size()));

					bpmTimeGraph.removeAllGraphData();
					bpmFreqGraph.removeAllGraphData();

					if (bpmData != null && bpmData.size() != 0) {
						bpmPlotPanel.showPlot(bpmData);
						bpmFreqData = MODEL.doFourierTransform(bpmData);
					}
				}
			}

		});

		getMatrixButton.addActionListener(event -> {
			if (matrixSources.isSelected(matrixLocal.getModel())) {
				matrixChooser.setDialogTitle("Get Matrix From Local File");
				int selectedsate = matrixChooser.showOpenDialog(DOCUMENT.mainWindow);
				if (selectedsate == JFileChooser.APPROVE_OPTION) {
					File matrixFile = matrixChooser.getSelectedFile();
					List<Double> matrixData = MODEL.getDataFromFile(matrixFile);
				}
			}
		});

		// fourier transform button action
		fourierTransform.addActionListener(event -> {
			bpmFreqGraph.removeAllGraphData();
			if (bpmFreqData != null) {
				try {
					double[] freqArray = bpmFreqData.get();
					List<Double> freqList = new ArrayList<Double>(freqArray.length);
					for (double j : freqArray) {
						freqList.add(j);
					}
					freqPlotPanel.showPlot(freqList);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

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
