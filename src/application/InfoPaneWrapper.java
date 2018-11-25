package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class InfoPaneWrapper extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			root.getChildren().add(new InfoPane());
			
			
			Scene scene = new Scene(root,400,400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public class InfoPane extends VBox{
		Label test;
		private InfoPane(){
			super(8);
			this.getChildren().addAll(new Label("Test"),new Label("Test"),new Label("Test"),new Button("ahh"));
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
