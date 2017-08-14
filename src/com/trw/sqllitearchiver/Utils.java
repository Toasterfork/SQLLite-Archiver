package com.trw.sqllitearchiver;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.sun.javafx.tk.Toolkit;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;

public class Utils
{
	public static final String DB_LOC = "E:/fowldb02_archive_6_29_17.db";
	public static final int DEFAULT_FONT_SIZE = 18;
	
	/**
	 * Creates a popup with the warning label and image.
	 * @param text
	 */
	public static void createWarningPopup(String text)
	{
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText(null);
		alert.setContentText(text);

		alert.showAndWait();
	}
	
	/**
	 * Creates an error dialog that prints and formats the given exception.
	 * @param ex
	 */
	public static void createExceptionPopup(Exception ex, String contentText)
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Exception Dialog");
		alert.setHeaderText("Exception Dialog");
		alert.setContentText(contentText);

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();	
	}
	
	/**
	 * Concats spaces to be 'length' long
	 * @param s
	 * @param length
	 * @return
	 */
	public static String setStringToDefinedLength(String s, int length)
	{
		String r = s;
		if(s.length() < length)
		{
			int spaces = length - s.length();
			for(int i = 0; i < spaces; i++)
			{
				r = r.concat(" ");
			}
		}
		
		return r;
	}
	
	/**
	 * Returns the width of the string in pixels
	 * @param s
	 * @return
	 */
	public static double getStringPixelWidth(String s)
	{
		//return new Text(s).getLayoutBounds().getWidth();
		return Toolkit.getToolkit().getFontLoader().computeStringWidth(s, Font.font(DEFAULT_FONT_SIZE));
	}
}
