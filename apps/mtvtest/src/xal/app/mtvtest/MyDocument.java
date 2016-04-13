package xal.app.mtvtest;

import java.net.URL;
import xal.extension.application.smf.AcceleratorDocument;

public class MyDocument  extends AcceleratorDocument {

	public MyDocument(URL url) {
		setSource(url);
		if (url == null)
			return;
	}

	public MyDocument() {
		this(null);
	}

	@Override
	public void makeMainWindow() {
		mainWindow = new MyWindow(this);

	}
	
	public void acceleratorChanged() {
		if(mainWindow != null) ((MyWindow) mainWindow).setAccelerator(this.accelerator);	
		
    }

	public void selectedSequenceChanged() {
    	((MyWindow) mainWindow).setSequence(this.selectedSequence);
    }

	@Override
	public void saveDocumentAs(URL url) {
		// TODO Auto-generated method stub

	}

}
