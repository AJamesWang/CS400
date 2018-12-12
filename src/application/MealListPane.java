
package application;

import java.util.ArrayList;
import java.util.EventListener;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
/**
 * This class simulates a meal list that can be used to then calculate the total amount of 
 * certain nutrients for the meal!
 * The user can pick food items from the food list and then the foods are added to another
 * arraylist, the meal arraylist, and that new list is shown in the table.
 * The user can also click the button that says "Analyze Meal" and the labels that
 * show the total calorie, fat, fiber, protein, and carb count for the meal by calculating
 * the total numbers from the foods chosen.
 *
 *@author d-team 57
 */

public class MealListPane extends BorderPane {
    private TableView mealTable = new TableView();
    private ArrayList<Food> mealArr = new ArrayList<Food>(); // list of current Foods in meal
    private VBox mealAnalysisBox;
    private TableView mealAnalysisTable = new TableView();
    private ArrayList<Food> totalFoodList = new ArrayList<Food>();
    private Button analyzeMeal;
    private HBox analyzeButtonBox;
    private HBox titleBox;

    /**
     * Creates a meal list pane by setting the right of the pane to the VBox 
     * that has all the info in it.
     */
    protected void create(){
        try {
            VBox borderPaneRight = new VBox();
            borderPaneRight.getChildren().addAll(mealPane());
            this.setRight(borderPaneRight);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculates the total amounts of cals, fat, carbs, fiber, and protein and stores
     * them into a new food item.
     * @param food   the total food item array list.
     * @return the new food item that was created
     */
    public Food calculateTotals(ArrayList<Food> food) {
        Food totalFood = new Food("Total Food", 0, 0, 0, 0, 0);
        
        for (Food item : food) {
            totalFood.setCalories(totalFood.getCalories()+ item.getCalories());
            totalFood.setFat(totalFood.getFat()+ item.getFat());
            totalFood.setCarbs(totalFood.getCarbs()+ item.getCarbs());
            totalFood.setFiber(totalFood.getFiber()+ item.getFiber());
            totalFood.setProtein(totalFood.getProtein()+ item.getProtein());
        }
      
        return totalFood;
    }    


    /**
     * initializes the meal analysis section, creates the labels and boxes and sets
     * the totals all to 0.
     * @return  the VBox containing all of the mealAnalysis information
     */
    public VBox initializeMealAnalysis() {
        this.mealAnalysisTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.mealAnalysisTable.setMinHeight(0.5 * Screen.getPrimary().getBounds().getHeight());
        mealAnalysisBox = new VBox();
        
        this.setId("food-data");//sets the default font to food-data (see CSS)
        Label mealAnalysis = new Label("Meal Analysis: ");
        mealAnalysis.setId("section-heading");
        titleBox = new HBox();
        titleBox.getChildren().add(mealAnalysis);
        titleBox.setAlignment(Pos.TOP_CENTER);

        // Analyze Meal Button
        analyzeMeal = new Button("Analyze Meal");
        analyzeMeal.setOnAction(new EventHandler<ActionEvent>() {
            @Override  //implements functionality
            public void handle(ActionEvent Event) {
                Food totalFoodData = calculateTotals(mealArr);
                
                Label totalCals = new Label("Total Calories: " + totalFoodData.getCalories());
                Label totalFat = new Label("Total Fat: " + totalFoodData.getFat());
                Label totalCarbs = new Label("Total Carbs: " + totalFoodData.getCarbs());
                Label totalFiber = new Label("Total Fiber: " + totalFoodData.getFiber());
                Label totalProtein = new Label("Total Protein: " + totalFoodData.getProtein());
                VBox totalBox = new VBox();
                totalBox.getChildren().addAll(totalCals, totalFat, totalCarbs, totalFiber, totalProtein);
                mealAnalysisBox.getChildren().setAll(titleBox, analyzeButtonBox, totalBox);
                
            }
        });
        
        analyzeButtonBox= new HBox();
        analyzeButtonBox.getChildren().add(analyzeMeal);
        analyzeButtonBox.setAlignment(Pos.TOP_CENTER);
        setupColumns(mealAnalysisTable);
        
        
        // Meal Analysis Box
        Label totalCals = new Label("Total Calories: " + 0);
        Label totalFat = new Label("Total Fat: " + 0);
        Label totalCarbs = new Label("Total Fat: " + 0);
        Label totalFiber = new Label("Total Fiber: " + 0);
        Label totalProtein = new Label("Total Protein: " + 0);
        
        VBox totalBox = new VBox();
        totalBox.getChildren().addAll(totalCals, totalFat, totalCarbs, totalFiber, totalProtein);
        mealAnalysisBox.getChildren().setAll(titleBox, analyzeButtonBox, totalBox);
        mealAnalysisBox.setPadding(new Insets(20, 20, 20, 20));
        return mealAnalysisBox;
    }
       
    /**
     * Creates the meal pane with the meal list in it. 
     * @return the VBox that has the meal list in it.
     */
    public VBox mealPane() {
        Label mealLabel = new Label("Meal:");
        mealLabel.setId("section-heading");
        HBox hBox1 = new HBox();
        hBox1.getChildren().add(mealLabel);
        hBox1.setAlignment(Pos.TOP_LEFT);

        VBox vBox = new VBox();

        // Delete Food Button
        Button deleteButton = new Button("Remove (r)");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
        //implements functionality     
        @Override
            public void handle(ActionEvent event) {
                Food selectedFood = (Food) mealTable.getSelectionModel().getSelectedItem();
                mealTable.getItems().remove(selectedFood);
            }
        });
            
        KeyCodeCombination removeFood = new KeyCodeCombination(KeyCode.R);
        this.getScene().getAccelerators().put(removeFood, ()->deleteButton.fire());
        HBox hBox3 = new HBox();
        hBox3.getChildren().add(deleteButton);
        hBox3.setAlignment(Pos.BASELINE_CENTER);//change alignment maybe?

        // set up Meal Table      
        setupColumns(this.mealTable);
        this.mealTable.setMinHeight((0.27) * Screen.getPrimary().getBounds().getHeight());
        this.mealTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.setPadding(new Insets(20, 20, 20, 20));
        
        vBox.getChildren().addAll(hBox1, mealTable, hBox3);
        return vBox;
    }

    /**
     * Updates the items in the meal list to be the list of foods passed in.
     * @param food
     */
    public void updateMealListPane(ArrayList<Food> food) {
        this.mealArr.addAll(food);
        ObservableList<Food> mealObsList = FXCollections.observableList(mealArr);

        this.mealTable.setItems(mealObsList);
    }
    
    /**
     * Sets up the columns in the table.
     * Sets up Name, Calories, Fat, Carbs, Fiber, and Protein
     * columns in that order.
     * @param table TableView to be set up
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setupColumns(TableView table) {
        // name
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("Name"));
        
        // calories
        TableColumn caloriesCol = new TableColumn("Calories");
        caloriesCol.setCellValueFactory(new PropertyValueFactory("Calories"));
        
        // fat
        TableColumn fatCol = new TableColumn("Fat");
        fatCol.setCellValueFactory(new PropertyValueFactory("Fat"));
        
        // carbs
        TableColumn carbsCol = new TableColumn("Carbs");
        carbsCol.setCellValueFactory(new PropertyValueFactory("Carbs"));
        
        // fiber
        TableColumn fiberCol = new TableColumn("Fiber");
        fiberCol.setCellValueFactory(new PropertyValueFactory("Fiber"));
        
        // protein
        TableColumn proteinCol = new TableColumn("Protein");
        proteinCol.setCellValueFactory(new PropertyValueFactory("Protein"));
        
        // add all columns to table
        table.getColumns().setAll(nameCol, caloriesCol, fatCol, carbsCol, fiberCol, proteinCol);
        
        // set the height of Food Table to a ratio of the screen's height
       // table.setMinHeight((0.60) * Screen.getPrimary().getBounds().getHeight());
    }

}