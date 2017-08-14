package com.trw.sqllitearchiver;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The main class of the program. Loads the db file via JavaFX and JDBC Lite and exports to cv with JXLS.
 * @author Domenic Portuesi
 *
 */
public class SQLLiteExporter extends Application
{
	private SQLLiteDB sql = SQLLiteDB.getInstance();
	
	private String dbPath = "";
	
	private ArrayList<CSVData> csvSheet = new ArrayList<CSVData>();
	
	//global gui variables
	private String partID = "";
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		BorderPane root = new BorderPane();
		ScrollPane scrollingRoot = new ScrollPane(root);

		root.setCenter(generateCenter(stage));
		
		stage.setTitle("SQL Lite Archiver");
		stage.setScene(new Scene(scrollingRoot, 600,400));
        stage.setMaximized(true);
        
		stage.show();
	}
	
	
	public VBox generateCenter(Stage stage)
	{
		VBox center = new VBox();
		center.setPadding(new Insets(20,20,20,40));
			
		Label title = new Label("SQL Lite Exporter");
		title.setStyle("-fx-font-weight: bold; -fx-font-size: 34;");
		title.setAlignment(Pos.TOP_CENTER);
		title.setPadding(new Insets(0, 0, 35, 0));
		
		HBox loadBox = new HBox(10);
		TextField fieldFile = new TextField();
		fieldFile.setFont(Font.font(Utils.DEFAULT_FONT_SIZE));
		fieldFile.focusedProperty().addListener(e -> {
			dbPath = fieldFile.getText();
			dbPath = dbPath.replace("\\", "/");

		});
		Button btnFile = new Button();
		ImageView btnFileIco = new ImageView(new Image(getClass().getResourceAsStream("chooser.png")));
		btnFile.setGraphic(btnFileIco);
		btnFile.setOnAction(e ->
		{
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Open Database File");
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SQL Lite files (*.db)", "*.db");
			chooser.getExtensionFilters().add(extFilter);
			File file = chooser.showOpenDialog(stage);
			
			if(file != null)
			{
				fieldFile.setText(file.getAbsolutePath());
				dbPath = file.getAbsolutePath();
				dbPath = dbPath.replace("\\", "/");
			}
		});
		loadBox.getChildren().addAll(btnFile, fieldFile);
		
		HBox partRow = new HBox(20);
		partRow.setPadding(new Insets(30,0,0,0));
		Label partIDLabel = new Label("Part ID: ");
		partIDLabel.setPrefHeight(40);
		partIDLabel.setFont(Font.font(Utils.DEFAULT_FONT_SIZE));
		TextField partIDField = new TextField();
		partIDField.setFont(Font.font(Utils.DEFAULT_FONT_SIZE));
		Button btnSearch = new Button("Search");
		btnSearch.setFont(Font.font(Utils.DEFAULT_FONT_SIZE));
		btnSearch.setPrefSize(80, 40);
		btnSearch.setOnAction(e -> 
		{
			partID = partIDField.getText();

			if(!partID.equals("") || partID != null)
			{
				sql.isValid(dbPath);
				
				center.getChildren().set(4, generateTableDataBasedOnID("PART_IDENTIFIER"));
				center.getChildren().set(6, generateTableDataBasedOnID("PART"));
				center.getChildren().set(8, generateTableDataBasedOnID("PART_ANALYSIS"));
				center.getChildren().set(10, generateTableDataBasedOnID("PROCESS_REQUEST"));
				center.getChildren().set(12, generateTableDataBasedOnID("PROCESS_COMPLETE"));
				center.getChildren().set(14, generateTableDataBasedOnID("PROCESS_DATA_VALUE"));
				center.getChildren().set(16, generateTableDataBasedOnID("STATUS_WORD_VALUE"));
				center.getChildren().set(18, generateTableDataBasedOnID("PART_FAILURE"));
			}
			else
			{
				Utils.createWarningPopup("No Part ID entered.");
			}
		});
		Button btnExport = new Button("Export");
		btnExport.setFont(Font.font(Utils.DEFAULT_FONT_SIZE));
		btnExport.setPrefSize(80, 40);
		btnExport.setOnAction(e ->{
			
		});
		
		partRow.getChildren().addAll(partIDLabel, partIDField, btnSearch, btnExport);
		
		//part iden section
		Label titlePartId = new Label("Part Identifier");
		titlePartId.setFont(Font.font(20));
		titlePartId.setPadding(new Insets(30,0,10,0));	
		
		Label titlePart = new Label("Part");
		titlePart.setFont(Font.font(20));
		titlePart.setPadding(new Insets(20,0,10,0));		
		
		Label titlePartAna = new Label("Part Analysis");
		titlePartAna.setFont(Font.font(20));
		titlePartAna.setPadding(new Insets(20,0,10,0));		
		
		Label titleProc = new Label("Process Request");
		titleProc.setFont(Font.font(20));
		titleProc.setPadding(new Insets(20,0,10,0));	
		
		Label titleProcC = new Label("Process Complete");
		titleProcC.setFont(Font.font(20));
		titleProcC.setPadding(new Insets(20,0,10,0));
		
		Label titleProcD = new Label("Process Data Value");
		titleProcD.setFont(Font.font(20));
		titleProcD.setPadding(new Insets(20,0,10,0));
		
		Label titleStatus = new Label("Status Word Value");
		titleStatus.setFont(Font.font(20));
		titleStatus.setPadding(new Insets(20,0,10,0));
		
		Label titlePartF = new Label("Part Failure");
		titlePartF.setFont(Font.font(20));
		titlePartF.setPadding(new Insets(20,0,10,0));
		
		center.getChildren().addAll(title,loadBox,partRow,
				titlePartId, generateTableDataBasedOnID("PART_IDENTIFIER"), 
				titlePart, generateTableDataBasedOnID("PART"), 
				titlePartAna, generateTableDataBasedOnID("PART_ANALYSIS"), 
				titleProc, generateTableDataBasedOnID("PROCESS_REQUEST"), 
				titleProcC, generateTableDataBasedOnID("PROCESS_COMPLETE"), 
				titleProcD, generateTableDataBasedOnID("PROCESS_DATA_VALUE"), 
				titleStatus, generateTableDataBasedOnID("STATUS_WORD_VALUE"),
				titlePartF, generateTableDataBasedOnID("PART_FAILURE"));
		
		return center;
	}
	
	/**
	 * Will generate a "grid" scroll pane of the data based on the table. Essentially it queries the column names and puts it in a list. 
	 * and then for every column, it queries their values in a separate list.
	 * @param table info to display
	 * @return a packed sroll pane
	 */
	public VBox generateTableDataBasedOnID(String table)
	{				
		VBox data = new VBox();
		data.setMinWidth(900);
		data.setPadding(new Insets(20,20,20,40));
		
		Label tableLbl = new Label("Table: " + table);
		tableLbl.setFont(Font.font("Lucida Console", Utils.DEFAULT_FONT_SIZE));
		
		data.getChildren().add(tableLbl);
		
		if(!partID.equals("")) //TODO fix the leftover column titles bug
		{
			//Create lists for our data
			ArrayList<String> columns = sql.getTableColumnWithType(dbPath, table); //list of columns
			
			ArrayList<String> dbStrings = new ArrayList<String>(); //list that will contain our fetched data	
			
			//fetch database data and put in the dbStrings list
			for(int i = 0; i < columns.size(); i++)
			{	
				//TODO create lines
				ArrayList<String> tempRecords = sql.getStringRecords(dbPath, partID, columns.get(i), table);
				for(String s : tempRecords)
				{
					dbStrings.add(s);		
				}
			}
			
			//If data is found
			if(!dbStrings.isEmpty())
			{
				//create the table headers
				HBox columnLabels = new HBox();
				columnLabels.setPadding(new Insets(20,0,8,0));
				for(String s : columns)
				{
					Label column = new Label(Utils.setStringToDefinedLength(s, 23));
					column.setFont(Font.font("Lucida Console", Utils.DEFAULT_FONT_SIZE));
					column.setMinWidth(100);
					columnLabels.getChildren().add(column);
				}
				
				data.getChildren().add(columnLabels);
				
				//create columns and rows for text fields
				VBox column = new VBox(15);
				HBox row = new HBox(15);
				row.setMinWidth(180);
				
				for(int j = 0; j < dbStrings.size(); j++)
				{
					String currentText = dbStrings.get(j);
					
					int numberOfCols = columns.size();
					int numberOfResults = dbStrings.size();
					
					TextField dataField = new TextField(currentText);
					dataField.setMinWidth(235);
					dataField.setMaxWidth(235);
					dataField.setFont(Font.font(Utils.DEFAULT_FONT_SIZE));
					dataField.setEditable(false);
					
					column.getChildren().add(dataField);
					
					if((j + 1) % (numberOfResults / numberOfCols) == 0) //TODO This only works if there are no empty fields
					{
						row.getChildren().add(column);
						column = new VBox(15);
					}
				}
				
				data.getChildren().add(row);
				
			}
			else //the query found nothing
			{
				Label noDataLbl = new Label("No data found.");
				noDataLbl.setPadding(new Insets(20,0,0,0));
				noDataLbl.setFont(Font.font(Utils.DEFAULT_FONT_SIZE));
				data.getChildren().add(noDataLbl);
			}	
		}
		else
		{
			Label noDataLbl = new Label("No data found.");
			noDataLbl.setPadding(new Insets(20,0,0,0));
			noDataLbl.setFont(Font.font(Utils.DEFAULT_FONT_SIZE));
			data.getChildren().add(noDataLbl);
		}	
		
		return data;
	}
}
