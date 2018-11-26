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
			HashMap<String, Double> tet = foo.getNutrients();
			for(int i=0; i<nutrients.length; i++)
				foo.addNutrient(FoodItem.NUTRIENT_IDS[i], nutrients[i]);
			ip.setFood(foo);
			root.add(ip, 0, 0, 5, 5);
			
			Scene scene = new Scene(root,400,400);
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
			this.titlePane = new BorderPane();
			this.dataPane = new GridPane();
			this.buttonPane = new HBox();
			this.generateTitle();
			this.generateButtons();
			
			this.getChildren().addAll(titlePane, dataPane, buttonPane);
		}
		
		/*
		 * updates the food which InfoPane looks at
		 */
		protected void setFood(FoodItem target){
			this.target = target;
			this.update();
		}
		
		private static final double LIMIT = .1;
		private String format(double val){
			if(Math.abs(val-(int)val) < LIMIT){
				return Integer.toString((int) val);
			} else{
				return Double.toString(val);
			}
		}
		
		/*
		 * updates all the data info
		 */
		protected void update(){
			//TODO: make the text prettier
			dataPane.getChildren().clear();
			dataPane.add(new Label(target.getName()), 0, 0, 2, 1);
			for(int i=0; i<FoodItem.NUTRIENT_IDS.length; i++){
				int row = i+1;
				String _nutrient = FoodItem.NUTRIENT_IDS[i];
				String _value = this.format(target.getNutrientValue(_nutrient));
				String _units = FoodItem.NUTRIENT_UNITS[i];
				Label nutrient = new Label(_nutrient);
				Label value = new Label(_value);
				Label units = new Label(_units);
				
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
			title.setFont(Font.font("Comic Sans", FontWeight.BOLD, 20));
			title.setUnderline(true);
			this.titlePane.setCenter(title);
			//TODO: look into Text.applyCss
		}
		
		/*
		 * Creates buttons
		 * TODO: delete? Not sure if we need these buttons...
		 */
		private void generateButtons(){
			Button clearButton = new Button("clear");
			clearButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e){
					clear();
				}
			});
			this.buttonPane.getChildren().add(clearButton);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
