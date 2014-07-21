/**
 * SingleDevDaqCtrlPanel.java
 *
 * @author Christopher K. Allen
 * @since  Nov 10, 2011
 *
 */

/**
 * SingleDevDaqCtrlPanel.java
 *
 * @author  Christopher K. Allen
 * @since	Nov 10, 2011
 */
package xal.app.pta.view.cmn;

import xal.app.pta.MainScanController;
import xal.app.pta.view.daq.ScanControlPanelDepr;
import xal.app.pta.view.daq.ScanProgressPanel;
//import xal.app.pta.view.daq.ScanProgressPanel;
import xal.app.pta.view.plt.LiveScanDisplayPanel;
import xal.app.pta.view.plt.LiveDisplayBase.FORMAT;
import xal.smf.impl.WireScanner;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

/**
 * This panel is used for the dynamic testing of wire scanner actuators.
 * It amalgamates three internal panels used for activating the scanner,
 * monitoring the actuator position, and plotting the measured data, 
 * respectively.
 * 
 *
 * @author Christopher K. Allen
 * @since   Nov 10, 2011
 * 
 * @see ScanControlPanelDepr
 * @see ScanProgressPanel
 * @see LiveScanDisplayPanel
 */
public class ScannerTestingPanel extends JPanel {

    
    
    /*
     * Global Constants 
     */
    
    /** Serialization version */
    private static final long serialVersionUID = 1L;


    
    
    
    /*
     * Local Attributes
     */
    
    
    //
    // Hardware 
    //
   
    /** The device selected for scanning */
    private WireScanner             smfSelDev;
    
    /** The device controller used by this GUI component */
    private MainScanController           ctrDaq;

  
    //
    //  GUI Components
    //
    
    /** Scan controller */
    private ScanControlPanelDepr        pnlScanCtrlr;
//    private ScanControlPanel        pnlScanCtrlr;
    
    /** Scan status read back */
    private ScanProgressPanel         pnlScanStat;
    
    /** Live display of scan data */
    private LiveScanDisplayPanel     pnlDaqPlot;
    
    
    /*
     * Initialization
     */
    
    /**
     * Creates a new, uninitialized <code>ScannerTestingPanel</code>
     * object.
     *
     * @author  Christopher K. Allen
     * @since   Nov 10, 2011
     */
    public ScannerTestingPanel() {
        this.initGuiComponents();
        this.initEventActions();
        this.buildGuiPanel();
    }
    
    /*
     * Operations
     */

    /**
     * Sets the device controller that this user interface
     * sits on.  We also register to catch the events 
     * generated by the given DAQ controller. 
     *
     * @param ctrDaq    the controller object running the show
     * 
     * @since  Apr 12, 2010
     * @author Christopher K. Allen
     */
    public void setDaqController(MainScanController ctrDaq) {
        if (this.ctrDaq != null)  {
//            this.ctrDaq.removeControllerListener(this);
            this.ctrDaq.removeControllerListener(pnlDaqPlot);
        }
            
        this.ctrDaq = ctrDaq;
//        this.ctrDaq.registerControllerListener(this);
        this.pnlScanCtrlr.setDaqController(ctrDaq);   // CKA 5/5/2014
        this.ctrDaq.registerControllerListener(this.pnlDaqPlot);
    }
    
    /**
     * Responds to a new device selection event.  Sets
     * the selected lists of devices.
     *
     * @param smfSelDev   currently selected DAQ device 
     *  
     * @since   Nov 17, 2009
     * @author  Christopher K. Allen
     */
    public void setDevice(WireScanner smfSelDev) {
        if (smfSelDev == null)
            return;

        this.smfSelDev = smfSelDev;
        List<WireScanner> lstDevs = new LinkedList<WireScanner>();
        lstDevs.add( smfSelDev );
        
        this.pnlScanCtrlr.setDaqDevices(lstDevs);
        this.pnlScanStat.setDevice(smfSelDev);
    }

    /**
     * Clears the list of DAQ devices under management.
     *
     * 
     * @since  Apr 8, 2010
     * @author Christopher K. Allen
     */
    public void clearDevice() {
        this.smfSelDev = null;
        
        this.pnlScanCtrlr.clearDevice();
        this.pnlDaqPlot.clearGraphs();
        this.pnlScanStat.clearDevice();
    }
    

    
    
//    /*
//     * IScanControllerListener Interface
//     */
//    
//    /**
//     * @since Nov 10, 2011
//     * @see xal.app.pta.daq.ScannerController.IDaqControllerListener#scanInitiated(java.util.List)
//     */
//    @Override
//    public void scanInitiated(List<WireScanner> lstDevs) {
//        // TODO Auto-generated method stub
//        
//    }
//
//    /**
//     * @since Nov 10, 2011
//     * @see xal.app.pta.daq.ScannerController.IDaqControllerListener#scanCompleted(java.util.List)
//     */
//    @Override
//    public void scanCompleted(List<WireScanner> lstDevs) {
//        // TODO Auto-generated method stub
//        
//    }
//
//    /**
//     * @since Nov 10, 2011
//     * @see xal.app.pta.daq.ScannerController.IDaqControllerListener#scanAborted()
//     */
//    @Override
//    public void scanAborted() {
//        // TODO Auto-generated method stub
//        
//    }
//
//    /**
//     * @since Nov 10, 2011
//     * @see xal.app.pta.daq.ScannerController.IDaqControllerListener#scanActuatorsStopped()
//     */
//    @Override
//    public void scanActuatorsStopped() {
//        // TODO Auto-generated method stub
//        
//    }
//
//    /**
//     * @since Nov 10, 2011
//     * @see xal.app.pta.daq.ScannerController.IDaqControllerListener#scanActuatorsParked()
//     */
//    @Override
//    public void scanActuatorsParked() {
//        // TODO Auto-generated method stub
//        
//    }
//
//    /**
//     * @since Nov 10, 2011
//     * @see xal.app.pta.daq.ScannerController.IDaqControllerListener#scanDeviceFailure(xal.smf.impl.WireScanner)
//     */
//    @Override
//    public void scanDeviceFailure(WireScanner smfDev) {
//        // TODO Auto-generated method stub
//        
//    }

    
    /*
     * GUI Support
     */
    
    /**
     * Creates the GUI components for device
     * control.
     *
     * @since  Dec 3, 2009
     * @author Christopher K. Allen
     */
    private void initGuiComponents() {

        // Create GUI controls
        this.pnlDaqPlot = new LiveScanDisplayPanel(FORMAT.SINGLEGRAPH);
        this.pnlDaqPlot.setLiveData(true);
        this.pnlDaqPlot.setLiveDataButtonVisible(false);
        this.pnlDaqPlot.setClearDataButton(false);
        
        this.pnlScanCtrlr = new ScanControlPanelDepr();
//        this.pnlScanCtrlr = new ScanControlPanel();
        ;
        this.pnlScanStat = new ScanProgressPanel();
    }
    
    /**
     * Sets the response actions to the data acquisition
     * user events.
     * 
     * @since  Oct 29, 2009
     * @author Christopher K. Allen
     */
    private void initEventActions(){
   }

    /**
     * Builds the visible GUI panel that receives
     * user data acquisition commands.
     * 
     * @since  Oct 29, 2009
     * @author Christopher K. Allen
     */
    private void buildGuiPanel() {

        this.setLayout( new GridBagLayout() );
        
        GridBagConstraints  gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        this.add( this.pnlScanCtrlr, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        this.add(this.pnlScanStat, gbc);
        
        gbc.gridx = 2;
        gbc.weightx = 0.5;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(this.pnlDaqPlot, gbc);
    }


    

}
