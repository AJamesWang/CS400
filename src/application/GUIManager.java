package application;
	
import java.util.Optional;
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
			FrontPage fp = new FrontPage();
			SearchPane sp = new SearchPane(this);
			InfoPane ip = new InfoPane();
			GridPane root = new GridPane();
			MealListPane mlp = new MealListPane();
			root.add(fp, 0, 0, 10, 10);
			root.add(sp, 10, 0);
			root.add(ip, 10, 10);
			root.add(mlp, 40, 0);
			
			Scene scene = new Scene(root,1400,1000);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
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
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
