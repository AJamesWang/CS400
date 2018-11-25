package application;
	
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
			root.add(new InfoPane(), 0, 0, 5, 5);
			Scene scene = new Scene(root,400,400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public class InfoPane extends GridPane{
		Label name;
		Label cals_l;
		Label cals;
		private InfoPane(){
			name = new Label("gaaah");
			this.add(name, 0, 0);
			cals_l = new Label("Cals:");
			this.add(cals_l, 0, 1);
			cals = new Label("100");
			this.add(cals, 1, 1);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
