package com.trw.sqllitearchiver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class SQLLiteDB
{
	private static SQLLiteDB instance = null;

	Connection conn = null;
		
	//singleton
	public static SQLLiteDB getInstance()
	{
		if(instance == null)
			instance = new SQLLiteDB();
		
		return instance;
	}
	
	public void loadDB()
	{
		loadDB(Utils.DB_LOC);
	}
	
	/**
	 * Connect to a sample database
	 */
	public void loadDB(String path)
	{
		String url = "jdbc:sqlite:" + path;
		
		conn = null;
		try
		{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(url);
		} 
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		} 
	}
	
	public void closeDB()
	{
		try
		{
			if(conn != null)
				conn.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if conn has a valid db, if not popup an exception.
	 * @param db
	 */
	public void isValid(String db)
	{
		loadDB(db);
		try
		{			
			String updateTableSQL = "SELECT 1 from PART";
			 
			PreparedStatement preparedStatement = conn.prepareStatement(updateTableSQL);		

			ResultSet rs = preparedStatement.executeQuery();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Utils.createExceptionPopup(e, "SQL Lite error. Make sure the file location specified is correct.");
		}
		closeDB();
	}
	
	/**
	 * Gets string value at column based on timestamp t
	 * @param db
	 * @param partID part id
	 * @param column column in table of value
	 * @return string value
	 */
	public ArrayList<String> getStringRecords(String db, String partID, String column, String table)
	{
		ArrayList<String> r = new ArrayList<String>();
		loadDB(db);
		try
		{			
			String updateTableSQL = "select " + column + " from " + table + " where PART_ID = ?;";
			
			PreparedStatement preparedStatement = conn.prepareStatement(updateTableSQL);	
		
			preparedStatement.setString(1, partID); 
			System.out.println(updateTableSQL + partID);

			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next())
			{
				r.add(rs.getString(1));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		closeDB();
		return r;
	}
	
	/**
	 * Returns a list of all column names.
	 * @param table
	 * @return
	 */
	public ArrayList<String> getTableColumnWithType(String db, String table)
	{
		//map with column label and column type
		ArrayList<String>  cols = new ArrayList<String> ();
		loadDB(db);
		try
		{						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM " + table);
			ResultSetMetaData md = rs.getMetaData();
			for (int i=1; i <= md.getColumnCount(); i++)
			{
				cols.add(md.getColumnLabel(i));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		closeDB();
		return cols;
	}
}
