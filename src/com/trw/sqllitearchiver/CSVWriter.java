package com.trw.sqllitearchiver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CSVWriter 
{
	public static void createCSV(String path, String line)
	{
		PrintWriter pw;
		try 
		{
			pw = new PrintWriter(new File(path));
			StringBuilder sb = new StringBuilder();
	        sb.append(line);
	        sb.append('\n');

	        pw.write(sb.toString());
	        pw.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}
