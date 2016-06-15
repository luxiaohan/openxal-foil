/**
 * 
 */
package xal.app.servicetest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import xal.extension.bricks.WindowReference;
import xal.extension.service.ServiceDirectory;
import xal.extension.service.ServiceListener;
import xal.extension.service.ServiceRef;
import xal.service.worker.Working;

/**
 * @author xl7
 *
 */
public class WorkController {
	
	/**the work service handler*/
	private WorkService _workService; 
	
	/**Constructor*/
	public WorkController( final WindowReference windowReference ) {
		setupView( windowReference );
		monitorService();
	}
	
	/**setup the view*/
	private void setupView( final WindowReference windowReference ) {
		final JTextField inputField = (JTextField)windowReference.getView( "InputField" );
		
		final JTextField output = (JTextField)windowReference.getView( "OutputField" );
		
		NumberFormat number = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter( number );
		DefaultFormatterFactory factory = new DefaultFormatterFactory( formatter );
		
		final JFormattedTextField foreText = (JFormattedTextField)windowReference.getView( "ForeText" );
		foreText.setFormatterFactory( factory );
		
		final JFormattedTextField afterText = (JFormattedTextField)windowReference.getView( "AfterText" );
		foreText.setFormatterFactory( factory );
		
		final JTextField sumField = (JTextField)windowReference.getView( "SumField" );
		
		final JButton button = (JButton)windowReference.getView( "SayHello" );
		button.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String in = inputField.getText();
				String out;
				if ( _workService != null )
					 out = _workService.sayHelloTo(in);
				else 
					 out = "No Services";
				output.setText(out);
				
			}
		});
		
		final JButton sumButton = (JButton)windowReference.getView( "SumButton" );
		sumButton.addActionListener( event -> {
			String fore = foreText.getText();
			String after = afterText.getText();
			Double result;
			if ( _workService != null )
				result = _workService.add( Double.parseDouble( fore ), Double.parseDouble( after ) );
			else
				result = Double.NaN;
			sumField.setText( result.toString() );
		});
		
		
		final JButton totalButton = (JButton)windowReference.getView( "TotalButton" );
	
		
	}
	
	/**find the service*/
	private void monitorService() {
		ServiceDirectory.defaultDirectory().addServiceListener( Working.class, new ServiceListener() {
			
			@Override
			public void serviceRemoved(ServiceDirectory directory, String type, String name) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void serviceAdded(ServiceDirectory directory, ServiceRef serviceRef) {
				final Working proxy = directory.getProxy( Working.class, serviceRef );
				final String id = serviceRef.getRawName();
				System.out.println("find a service :" + id );
				_workService = new WorkService( proxy );
			}
		});
	}

}
