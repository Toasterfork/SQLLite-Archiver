package com.trw.sqllitearchiver;

import java.util.ArrayList;

/**
 * Represents a block of data in the csv file.
 * @author Domenic Portuesi
 *
 */
public class CSVData 
{
	public ArrayList<String> lines;
	
	public CSVData(ArrayList<String> line)
	{
		lines = line;
	}
}
