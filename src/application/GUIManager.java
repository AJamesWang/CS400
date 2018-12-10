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
    private FoodPane fp = new FoodPane();
    private SearchPane sp = new SearchPane();
    private FoodList fl = new FoodList();
    
    @Override
    public void start(Stage primaryStage) {
        try {
			GridPane root = new GridPane();

			Scene scene = new Scene(root,1400,1000);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("COOLEST CS400 PROJECT EVER!!!11!11111!!");
			primaryStage.show();
		//	loadNewFoodFile();
		
			//Moved to below scene creation b/c keyboard shortcuts need access to Scene
			root.add(fp, 0, 0, 1, 2);
			fp.create(mlp, this);
			root.add(sp, 1,0);
			sp.create(this);
			root.add(mlp, 1, 1);
			mlp.create();
	        root.add(mlp.initializeMealAnalysis(), 2, 1);//awkward, convert to inner class later?
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    //////////////////////
    //SearchPane methods//
    //////////////////////
    /*
     * receives a list of constraints from SearchPane
     * updates FoodList accordingly
     */
    protected void constraintsUpdated(){
    	this.updateFoodPane();
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
    protected void updateFoodPane() {
    	ArrayList<Food> shownFoods = this.fl.filterFoods(sp.getMins(), sp.getMaxes());
        this.fp.updateFoodArrList(shownFoods);
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
    protected void updateMealListPane(ArrayList<Food> foods) {
        this.mlp.updateMealListPane(foods);
    }
    /*
     * Adds food to FoodList, updates FoodPane accordingly
     */
    protected void addFood(Food food){
    	this.fl.addFood(food);
    	this.updateFoodPane();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
