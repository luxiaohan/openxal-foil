/**
 * 
 */
package xal.app.servicetest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JTextField;

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
