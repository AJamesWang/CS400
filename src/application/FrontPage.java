package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.GridPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * 
 *
 */
public class FrontPage extends GridPane{

	
	public FrontPage(){
		
		
		Label title = new Label("Meal Planner");
		title.setFont(Font.font("Arial",FontWeight.BOLD,48));
		
		TextField userInput = new TextField("Enter path");
		Label userInput2 = new Label("Enter path to food data:");
		userInput2.setFont(Font.font("Arial",FontWeight.BOLD,24));
		userInput2.setLabelFor(userInput);
		
		VBox vbox = new VBox(); 
		vbox.getChildren().add(title); 
		
		VBox vbox2 = new VBox();
		vbox2.getChildren().addAll(userInput2, userInput); 
		
		vbox.getChildren().add(vbox2);
		vbox.setSpacing(400); 
		
		this.getChildren().add(vbox);
		
	}
}
