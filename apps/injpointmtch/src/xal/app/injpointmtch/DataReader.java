/*
 *  DataReader.java
 *
 *  xal.app.injpointmtch
 *
 *  Created on Mar 29, 2017  8:24:17 AM
 *
 *  Created by luxiaohan (luxh@ihep.ac.cn)
 *
 *  Copyright (c) 2017 China Spallation Neutron Source(CSNS). All Rights Reserved
 * 
 *  DongGuan,GuangDong 523803
 *
 */
package xal.app.injpointmtch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mathworks.engine.MatlabEngine;

/**
 * Read data from different sources
 * 
 * @author luxiaohan
 * @version Mar 29, 2017,v 1.0
 * @sine JDK 1.8
 */
public class DataReader<T> {

	/** buffered reader */
	private BufferedReader bufferedReader;
	/** the file */
	private File file;
	/** the data type */
	private Class<T> type;

	/**
	 * Creates a new instance of DataReader.
	 * 
	 * @param type
	 *            the data type
	 */
	public DataReader(final Class<T> type) {
		this(null, type);
	}

	/**
	 * Creates a new instance of DataReader.
	 * 
	 * @param file
	 *            the data file
	 * @param type
	 *            the data type
	 */
	public DataReader(final File file, final Class<T> type) {
		this.file = file;
		this.type = type;
	}

	/**
	 * set the file to read
	 * 
	 * @param file
	 *            the file
	 *
	 * @author luxiaohan
	 * @since JDK 1.8
	 * @version Mar 29, 2017,v 1.0
	 */
	public void setFile(final File file) {
		this.file = file;
	}

	/**
	 * read data from file to an array list
	 * 
	 * @return the data list
	 *
	 * @author luxiaohan
	 * @since JDK 1.8
	 * @version Mar 29, 2017,v 1.0
	 */
	public List<T> ReadData() {// ToDo the read data method must be more general
		List<T> dataList = new ArrayList<T>();
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				dataList.addAll(parseLineData(line));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataList;

	}

	/**
	 * read bpm data from file to an array list
	 * 
	 * @return the data list
	 *
	 * @author luxiaohan
	 * @since JDK 1.8
	 * @version Mar 29, 2017,v 1.0
	 */
	public List<T> ReadBPMData() {// ToDo this method keep for massed bpm data
		List<T> dataList = new ArrayList<T>();
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			char[] buf = new char[8];
			while (bufferedReader.read(buf) != -1) {
				dataList.add(dataTransfer(String.valueOf(buf)));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return dataList;

	}

	/**
	 * read data from file to an array list by the matlab engine
	 * 
	 * @param engine
	 *            the matlab engine
	 * @return the dataList
	 *
	 * @author luxiaohan
	 * @since JDK 1.8
	 * @version Mar 29, 2017,v 1.0
	 */
	public List<T> ReadData(final MatlabEngine engine) {// ToDo
		List<T> dataList = new ArrayList<T>();

		return dataList;
	}

	/**
	 * parse line data to the specified type
	 * 
	 * @param lineData
	 *            the data
	 * @return the parsed data
	 *
	 * @author luxiaohan
	 * @since JDK 1.8
	 * @version Mar 29, 2017,v 1.0
	 */
	private List<T> parseLineData(final String lineData) {
		List<T> parsedData = new ArrayList<T>();
		char[] split = lineData.toCharArray();
		List<Character> oneNumber = new ArrayList<Character>();
		for (int i = 0; i < split.length; i++) {
			char c = split[i];
			if (Character.isDigit(c) || c == '.' || c == '-' || c == 'e') {
				oneNumber.add(c);
				char d;
				if (i < split.length - 1) {
					d = split[i + 1];
				} else {
					d = ' ';
				}
				if (d == ',' || d == ' ' || d == ';') {// the data separator
					char[] ch = new char[oneNumber.size()];
					for (int j = 0; j < oneNumber.size(); j++) {
						ch[j] = oneNumber.get(j);
					}
					String one = String.valueOf(ch);
					oneNumber.clear();
					parsedData.add(dataTransfer(one));
				}
			}
		}

		return parsedData;
	}

	/**
	 * transfer raw data to the specified type
	 * 
	 * @param rawData
	 *            the raw data
	 * @return result the transfered data
	 *
	 * @author luxiaohan
	 * @since JDK 1.8
	 * @version Mar 28, 2017,v 1.0
	 */
	@SuppressWarnings("unchecked")
	private T dataTransfer(final String rawData) {
		T result = null;
		if (Double.class.equals(type)) {
			result = (T) Double.valueOf(rawData);
		} else if (Long.class.equals(type)) {
			result = (T) Long.valueOf(rawData);
		} else if (Integer.class.equals(type)) {
			result = (T) Integer.valueOf(rawData);
		}

		return result;
	}

}
