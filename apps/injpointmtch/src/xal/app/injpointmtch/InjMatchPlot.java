/*
 *  InjMatchPlot.java
 *
 *  xal.app.injpointmtch
 *
 *  Created on Mar 29, 2017  12:01:50 PM
 *
 *  Created by luxiaohan (luxh@ihep.ac.cn)
 *
 *  Copyright (c) 2017 China Spallation Neutron Source(CSNS). All Rights Reserved
 * 
 *  DongGuan,GuangDong 523803
 *
 */
package xal.app.injpointmtch;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import xal.extension.widgets.apputils.SimpleChartPopupMenu;
import xal.extension.widgets.plot.BasicGraphData;
import xal.extension.widgets.plot.FunctionGraphsJPanel;

/**
 * Plot the data
 * 
 * @author luxiaohan
 * @version Mar 29, 2017,v 1.0
 * @sine JDK 1.8
 */
public class InjMatchPlot {
	/** plot panel */
	private FunctionGraphsJPanel plotPanel;
	/** plot name */
	private String name;
	/** axis X */
	private String axis_x;
	/** axis Y */
	private String axis_y;

	/**
	 * Creates a new instance of InjMatchPlot.
	 * 
	 * @param plotPanel
	 */
	public InjMatchPlot(final FunctionGraphsJPanel plotPanel) {
		this.plotPanel = plotPanel;
		configPlotPanel();
	}
	
	public void showPlot(final List<Double> yList) {
		List<Double> xList = new ArrayList<Double>(yList.size());
		for (int i=0; i<yList.size(); i++){
			double j = i+1;
			xList.add(j);
		}		
		showPlot(xList,yList);
		
	}

	public void showPlot(final List<Double> xList, final List<Double> yList) {
		int length = yList.size();
		double[] xArray = new double[length];
		double[] yArray = new double[length];
		for (int i=0; i<length; i++) {
			xArray[i] = xList.get(i);
			yArray[i] = yList.get(i);
		}
		BasicGraphData plotData = new BasicGraphData();
		plotData.updateValues(xArray, yArray);
		
		plotPanel.addGraphData(plotData);

	}
	
	public void setPlotPanel( final FunctionGraphsJPanel panel) {
		this.plotPanel = panel;
	}
	
	private void configGraphic(final BasicGraphData graphData) {
		
	}

	private void configPlotPanel() {
		plotPanel.setName(name);
		plotPanel.setAxisNameX(axis_x);
		plotPanel.setAxisNameY(axis_y);

		// add legend support
		plotPanel.setLegendButtonVisible(true);
		plotPanel.setLegendBackground(Color.white);
		// add popup menu to the plot panel
		SimpleChartPopupMenu.addPopupMenuTo(plotPanel);
	}

}
