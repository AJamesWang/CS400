package application;
	
import java.util.HashMap;

import data.FoodItem;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class InfoPaneWrapper extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
//			BorderPane root = new BorderPane();
//			root.setCenter(new InfoPane());
//			root.getChildren().add(new InfoPane());
			GridPane root = new GridPane();
			InfoPane ip = new InfoPane();
			FoodItem foo = new FoodItem("0", "Cake");
			double[] nutrients = new double[]{1000, 10, 15, 5, 0.5};
			for(int i=0; i<nutrients.length; i++)
				foo.addNutrient(FoodItem.NUTRIENT_IDS[i], nutrients[i]);
			ip.setFood(foo);
			root.add(ip, 0, 0);
			
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public class InfoPane extends VBox{
		FoodItem target;
		BorderPane titlePane;
		GridPane dataPane;
		HBox buttonPane;
		protected InfoPane(){
			this.generateTitle();
			this.generateData();
			this.generateButtons();
		}
		
		/*
		 * updates the food which InfoPane looks at
		 */
		protected void setFood(FoodItem target){
			this.target = target;
			this.update();
		}
		
		
		/*
		 * updates all the data info
		 */
		protected void update(){
			dataPane.getChildren().clear();
			
			Text name = new Text(target.getName());
			name.setId("food-name");

			dataPane.add(name, 0, 0);
			dataPane.add(new Label("     "), 1, 0);//padding so the numbers aren't too close to the labels 
			
			for(int i=0; i<FoodItem.NUTRIENT_IDS.length; i++){
				int row = i+1;
				String id = FoodItem.NUTRIENT_IDS[i];
				Text nutrient = new Text(FoodItem.NUTRIENT_NAMES[i]);
				nutrient.setId("food-data");
				Text value = new Text(FoodItem.format(target.getNutrientValue(id)));
				value.setId("food-data");
				Text units = new Text(FoodItem.NUTRIENT_UNITS[i]);
				units.setId("food-data");
				
				GridPane.setHalignment(value, HPos.RIGHT);
				
				dataPane.add(nutrient, 0, row);
				dataPane.add(value, 1, row);
				dataPane.add(units, 2, row);
			}
		}
		
		/**
		 * Pads dataPane with empty labels to keep it from collapsing once data is cleared
		 */
		private void padDataPane(){
			for(int i=0; i<FoodItem.NUTRIENT_IDS.length+1; i++){
				this.dataPane.add(new Label(), 0, i);
			}
		}
		
		/*
		 * Clears selected food data
		 * Consider removing, no purpose for it to exist?
		 * At least, remove the button...
		 */
		protected void clear(){
			this.dataPane.getChildren().clear();
			this.padDataPane();
		}
		
		/*
		 * Generates title, makes it all pretty
		 */
		private void generateTitle(){
			Text title = new Text("Nutrients of selected item:");
			this.setId("section-heading");
			
			this.titlePane = new BorderPane();
			this.titlePane.setCenter(title);
			this.getChildren().add(this.titlePane);
		}
		
		/*
		 * Generates the initial, blank dataPane
		 */
		private void generateData(){
			this.dataPane = new GridPane();
			this.padDataPane();
			this.getChildren().add(this.dataPane);
		}
		
		/*
		 * Creates buttons
		 * TODO: delete? Not sure if we need these buttons...
		 */
		private void generateButtons(){
			Button clearButton = new Button("clear");
			clearButton.setOnAction(e->clear());
			
			this.buttonPane = new HBox();
			this.buttonPane.getChildren().add(clearButton);
			this.getChildren().add(this.buttonPane);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
