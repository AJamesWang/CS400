package application;
	
import java.util.ArrayList;
import java.util.Optional;
import data.FoodItem;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class GUIManager extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			InfoPane ip = new InfoPane();
			SearchPane sp = new SearchPane(this);
			MealListPane mlp = new MealListPane();
	        FoodPane fp = new FoodPane();

			
			// FIXME: TEMPORARY STATIC ARRAY OF FOODITEMS FOR 
			// DISPLAYING IN MILESTONE 2, DELETE LATER
			ArrayList<FoodItem> tempStaticArr = new ArrayList<FoodItem>();
			tempStaticArr.add(new FoodItem("apple", "apple"));
			tempStaticArr.add(new FoodItem("banana", "banana"));
			
			// load food data into food pane
			updateFoodPane(fp, tempStaticArr);
		
			mlp.setMinHeight(primaryStage.getHeight());
			
			GridPane root = new GridPane();
			root.add(ip, 2, 0);
			root.add(sp, 2,1);
			root.add(mlp, 0, 0);
			root.add(fp, 1, 0);
			
			Scene scene = new Scene(root,1400,1000);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();
			
			TextInputDialog dialog = new TextInputDialog("ex: User/Desktop/FoodList.csv");
			dialog.setTitle("Meal Planner");
			dialog.setHeaderText("Enter path to Food Data");
			dialog.setContentText("Path:");

			// Traditional way to get the response value.
			Optional<String> path = dialog.showAndWait();
			if (path.isPresent()) {
			    dialog.close();
			}

			// The Java 8 way to get the response value (with lambda expression).
			//result.ifPresent(name -> System.out.println("Your name: " + name));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	////////////////////
	//InfoPane methods//
	////////////////////
	/*
	 * Once a food is selected, sends data to infoPane to be displayed
	 */
	protected void updateInfoPane(){
	    // call getNewItems() from FoodPane
	    // load food into info pane one by one
		System.out.println("updateInfoPane NOT IMPLEMENTED YET");
	}
	/*
	 * Once a food is deselected, clears infoPane
	 */
	 protected void clearInfoPane(){
		 System.out.println("clearInfoPane NOT IMPLEMENTED YET");
	 }
	
	//////////////////////
	//SearchPane methods//
	//////////////////////
	/*
	 * receives a list of constraints from SearchPane
	 * updates FoodList accordingly
	 */
	 protected void updateConstraints(){
		 System.out.println("updateConstraints NOT IMPLEMENTED YET");
	 }
	 protected void clearConstraints(){
		 System.out.println("clearConstraints NOT IMPLEMENTED YET");
	 }
	 
     //////////////////////
	 // FoodPane methods //
     //////////////////////
    /*
     * Receives an ArrayList of FoodItems
     * and loads them into the passed in FoodPane
     * @param fp The FoodPane to be updated
     * @param food An ArrayList of FoodItems
     */
	 protected void updateFoodPane(FoodPane fp, ArrayList<FoodItem> food) {
	     fp.setInitialTableData(food);
	 }
	 
	
	public static void main(String[] args) {
		launch(args);
	}
}
