package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class GUIManager extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FrontPage fp = new FrontPage();
			InfoPane ip = new InfoPane();
			SearchPane sp = new SearchPane(this);
			HBox root = new HBox();
			root.getChildren().addAll(fp, ip, sp);
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
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
