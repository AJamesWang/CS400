package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class GUIManager extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
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
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
