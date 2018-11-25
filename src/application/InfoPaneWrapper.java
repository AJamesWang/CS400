package application;
	
import java.util.HashMap;

import data.FoodItem;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


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
			double[] nutrients = new double[]{1000, 10, 15, 5, 0};
			HashMap<String, Double> tet = foo.getNutrients();
			for(int i=0; i<nutrients.length; i++){
				foo.addNutrient(FoodItem.NUTRIENT_IDS[i], nutrients[i]);
			}
			ip.setFood(foo);
			root.add(ip, 0, 0, 5, 5);
			
			Scene scene = new Scene(root,400,400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public class InfoPane extends GridPane{
		FoodItem target;
		private InfoPane(){
			this.setHgap(10);
		}
		
		public void setFood(FoodItem target){
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
		
		public void update(){
			this.getChildren().clear();
			this.add(new Label(target.getName()), 0, 0, 2, 1);
			for(int i=0; i<FoodItem.NUTRIENT_IDS.length; i++){
				int row = i+2;
				String nutrient = FoodItem.NUTRIENT_IDS[i];
				String value = this.format(target.getNutrientValue(nutrient)) + FoodItem.NUTRIENT_UNITS[i];
				
				this.add(new Label(nutrient), 0, row);
				this.add(new Label(value), 1, row);
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
