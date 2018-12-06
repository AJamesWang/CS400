package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


public class GUIManager extends Application {
    private MealListPane mlp = new MealListPane();
    private FoodPane fp = new FoodPane(mlp);
    
    @Override
    public void start(Stage primaryStage) {
        try {
            SearchPane sp = new SearchPane(this);			
			GridPane root = new GridPane();
			root.add(fp, 0, 0, 1, 2);
			root.add(sp, 1,0);
			root.add(mlp, 1, 1);
	        root.add(mlp.initializeMealAnalysis(), 2, 1);

			Scene scene = new Scene(root,1400,1000);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("COOLEST CS400 PROJECT EVER!!!11!11111!!");
			primaryStage.show();

            // The Java 8 way to get the response value (with lambda expression).
            //result.ifPresent(name -> System.out.println("Your name: " + name));

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadNewFoodFile() {
        TextInputDialog dialog = new TextInputDialog("ex: User/Desktop/FoodList.csv");
        dialog.setTitle("Meal Planner");
        dialog.setGraphic(null);
        dialog.setHeaderText("Enter path to Food Data");
        dialog.setContentText("Path:");
        dialog.getDialogPane().setMinWidth(500);


        // Get filepath from user and load data into Food List
        Optional<String> path = dialog.showAndWait();
        if (path.isPresent()) {
            CSVReader csvReader = new CSVReader();
            updateFoodPane(FXCollections.observableList(csvReader.read(path.get())));
            dialog.close();
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
     * Receives an ObservableList of FoodItems
     * and loads them into the FoodPane
     * 
     * @param food An ArrayList of FoodItems
     */
    protected void updateFoodPane(ObservableList<Food> food) {
        this.fp.updateFoodPane(food);
    }

    //////////////////////
    // MealListPane methods //
    //////////////////////
    /*
     * Receives an ArrayList of FoodItems
     * and loads them into the mealListPane
     * 
     * @param food An ArrayList of FoodItems
     */
    protected void updateMealListPane(ArrayList<Food> food) {
        this.mlp.updateMealListPane(food);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
