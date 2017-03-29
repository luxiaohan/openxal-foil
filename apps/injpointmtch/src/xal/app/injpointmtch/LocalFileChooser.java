/*
 *  LocalFileChooser.java
 *
 *  xal.app.injpointmtch
 *
 *  Created on Mar 28, 2017  2:30:33 PM
 *
 *  Created by luxiaohan (luxh@ihep.ac.cn)
 *
 *  Copyright (c) 2017 China Spallation Neutron Source(CSNS). All Rights Reserved
 * 
 *  DongGuan,GuangDong 523803
 *
 */
package xal.app.injpointmtch;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import xal.tools.apputils.files.FileFilterFactory;

/**
 * Choose file from local
 * 
 * @author luxiaohan
 * @version Mar 28, 2017,v 1.0
 * @sine JDK 1.8
 */
public class LocalFileChooser {

	/** file chooser */
	final private JFileChooser FILE_CHOOSER;

	/**
	 * Creates a new instance of LocalFileChooser.
	 */
	public LocalFileChooser() {
		this(new String[] { "*" }, "file");
	}

	/**
	 * Creates a new instance of LocalFileChooser.
	 * 
	 * @param title
	 *            the file chooser dialog title
	 */
	public LocalFileChooser(final String title) {
		this(new String[] { "*" }, title);
	}

	/**
	 * Creates a new instance of LocalFileChooser.
	 * 
	 * @param filters
	 *            the file filters
	 * @param title
	 *            the file chooser dialog title
	 */
	public LocalFileChooser(final String[] filters, final String title) {
		FILE_CHOOSER = new JFileChooser();
		FILE_CHOOSER.setDialogTitle("Get" + " " + title + " " + "From Local File");
		FileFilterFactory.applyFileFilters(FILE_CHOOSER, filters);
		configDialog();
	}

	/**
	 * show file open dialog and return file if selected any.
	 * 
	 * @param parent
	 *            the parent component
	 * @return the selected file.
	 *
	 * @author luxiaohan
	 * @since JDK 1.8
	 * @version Mar 28, 2017,v 1.0
	 */
	public File showDialog(final Component parent) {

		File file = null;
		int selectedstate = FILE_CHOOSER.showOpenDialog(parent);

		switch (selectedstate) {
		case JFileChooser.APPROVE_OPTION:
			file = FILE_CHOOSER.getSelectedFile();
			break;
		case JFileChooser.CANCEL_OPTION:
			break;
		default:
			break;
		}

		return file;
	}

	/**
	 * configure the file open dialog
	 * 
	 * @author luxiaohan
	 * @since JDK 1.8
	 * @version Mar 28, 2017,v 1.0
	 */
	private void configDialog() {
		final Box accessory = new Box(BoxLayout.Y_AXIS);
		// Dimension maxSize = new Dimension(100, 200);
		// accessory.setMaximumSize(maxSize);
		final JScrollPane scrollPane = new JScrollPane();
		final JTextArea preview = new JTextArea();
		scrollPane.setViewportView(preview);
		preview.setEditable(false);
		preview.setLineWrap(true);
		FILE_CHOOSER.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName() == JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) {
					File file = (File) evt.getNewValue();
					if (file == null) {
						preview.setText("");
						return;
					}

					try (FileChannel channel = FileChannel.open(file.toPath())) {
						MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
						Charset charset = Charset.forName("UTF-8");
						CharBuffer cb = charset.decode(buffer);
						preview.setText(cb.toString());
						preview.setCaretPosition(0);
					} catch (IOException e) {
						preview.setText("");
					}
				}

			}
		});

		final JButton recentButton = new JButton("Recent...");

		accessory.add(scrollPane);
		accessory.add(recentButton);

		FILE_CHOOSER.setAccessory(accessory);
	}

}
