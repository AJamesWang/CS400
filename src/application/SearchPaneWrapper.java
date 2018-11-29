package application;
	
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.FoodItem;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class SearchPaneWrapper extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			GridPane root = new GridPane();
			SearchPane sp = new SearchPane(new GUIManager());
			root.getChildren().add(sp);
			
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public class SearchPane extends VBox{
		GUIManager manager;
		BorderPane titlePane;
		GridPane dataPane;
		Map<String, TextField> mins;
		Map<String, TextField> maxes;
		Map<String, Double> curMins;
		Map<String, Double> curMaxes;
		HBox buttonPane;
		
		protected SearchPane(GUIManager manager){
			this.manager = manager;
			this.generateTitle();
			this.generateData();
			this.generateButtons();
		}
		
		/*
		 * Sets all the values in the text boxes to whatever's listed in CurVals
		 */
		 protected void update(){
			 for(String id : FoodItem.NUTRIENT_IDS){
				 mins.get(id).clear();
				 mins.get(id).setPromptText(FoodItem.format(curMins.get(id)));
				 maxes.get(id).clear();
				 maxes.get(id).setPromptText(FoodItem.format(curMaxes.get(id)));
			 }
		 }
		/*
		 * Updates curMins and curMaxes data values
		 * TODO: make it so that text boxes reset to curVals when unfocused?
		 */
		 private void updateCurVals(){
			 for(String id : FoodItem.NUTRIENT_IDS){
				 try{
					 this.curMins.put(id, Double.parseDouble(this.mins.get(id).getText()));
				 } catch(NumberFormatException e){
				 }
				 try{
					 this.curMaxes.put(id, Double.parseDouble(this.maxes.get(id).getText()));
				 } catch(NumberFormatException e){
				 }
			 }
		 }
		
		/*
		 * sends min/max data to GUIManager
		 * TODO: check that there's some way for user to know what the current filters are
		 */
		private void sendData(){
			this.manager.updateConstraints();
		}
		
		/*
		 * Things that happen when searchButton was clicked
		 */
		 private void search(){
			 this.updateCurVals();
			 this.sendData();
			 this.printData();
			 update();
			 System.out.println("Searching...");
		 }
		
		/*
		 * tells GUIManager to clear min/max data
		 */
		 private void clearData(){
			 this.curMins.clear();
			 this.curMaxes.clear();
			 this.manager.clearConstraints();
			 this.update();
			 this.printData();
			 System.out.println("Clearing...");
		 }
		 
		 private void printData(){
			 //TODO: implement the GUIManager method so I can delete this
			 for(String id : FoodItem.NUTRIENT_IDS){
				 System.out.println(String.format("ID:%s\tMin:%s\tMax:%s\t", id, this.curMins.get(id), this.curMaxes.get(id)));
			 }
		 }
		 
		/*
		 * Creates all necessary title components
		 */
		private void generateTitle(){
			Text title = new Text("Filter foods by nutrients:");
			title.setId("section-heading");
//			title.setFont(Font.font("Comic Sans", FontWeight.BOLD, 20));
//			title.setUnderline(true);
			
			this.titlePane = new BorderPane();
			this.titlePane.setCenter(title);
			this.getChildren().add(this.titlePane);
		}
		
		/*
		 * Creates all necessary data input components
		 */
		private void generateData(){
			this.dataPane = new GridPane();
			this.dataPane.setHgap(10);
			this.mins = new HashMap<String, TextField>();
			this.maxes = new HashMap<String, TextField>();
			this.curMins = new HashMap<String, Double>();
			this.curMaxes = new HashMap<String, Double>();
			/*Labels*/
			this.dataPane.add(new Label("Nutrient"), 0, 0);
			//TODO: find some cleaner way of padding columns (new problem, see below)
			this.dataPane.add(new Label("Min     "), 1, 0);
			this.dataPane.add(new Label("Max     "), 2, 0);
			/*Input fields*/
			for(int i=0; i<FoodItem.NUTRIENT_IDS.length; i++){
				int row = i+1;
				String id = FoodItem.NUTRIENT_IDS[i];
				String _nutrient = FoodItem.NUTRIENT_NAMES[i];
				Label nutrient = new Label(_nutrient);
				//TODO: get rid of the magic numbers
				TextField min = new TextField();
				min.setPromptText("-");
				min.setMaxWidth(70);
				TextField max = new TextField();
				max.setPromptText("-");
				max.setMaxWidth(70);
				//TODO: look into TextFormatter? To limit input to numbers?
				//https://stackoverflow.com/questions/8381374/how-to-implement-a-numberfield-in-javafx-2-0
				this.mins.put(id, min);
				this.maxes.put(id, max);
				//TODO: mark somewhere that null means no constraint (related to magic numbers)
				//put(null) not necessary b/c map.get() returns null anyway. However, good for clarity
				this.curMins.put(id, null);
				this.curMaxes.put(id, null);
				this.dataPane.add(nutrient, 0, row);
				this.dataPane.add(min, 1, row);
				this.dataPane.add(max, 2, row);
			}
			this.getChildren().add(this.dataPane);
		}
		
		/*
		 * Generates all necessary buttons
		 */
		private void generateButtons(){
			Button searchButton = new Button("search");//TODO: consider renaming to filter?
			searchButton.setOnAction(e->search());
			Button resetButton = new Button("reset");
			resetButton.setOnAction(e->clearData());
			this.buttonPane = new HBox();
			this.buttonPane.getChildren().addAll(searchButton, resetButton);
			this.getChildren().add(this.buttonPane);
		}
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
