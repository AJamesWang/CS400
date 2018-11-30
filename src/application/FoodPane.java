package application;

import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import java.util.ArrayList;
import data.FoodItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

public class FoodPane extends BorderPane{
    private TableView foodTable = new TableView(); // table to display food options
    private ArrayList<FoodItem> currSelected = new ArrayList<FoodItem>(); // current food items selected
    
    /*
     * Constructs a FoodPane containing information
     * about the name and nutritional content of various foods.
     */
    public FoodPane() {
        try {
            this.setRight(foodPane());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
     * Returns a VBox containing the TableView displaying
     * the food data. 
     * @return VBox of TableView displaying the food data
     */
    public VBox foodPane() {
        Label foodLabel = new Label("Food List:");
        foodLabel.setId("section-heading");
        Button addFoodBtn = new Button("Add food(s) to meal");
        
        // name the columns
        setupColumns();
        
        // format table size and enable selection of multiple foods at once
        this.foodTable.setPrefWidth(500);
        this.foodTable.setPrefHeight(500);
        this.foodTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.foodTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        // Set up and display scene
        VBox foodPane = new VBox(10);
        foodPane.setId("food-data");
        foodPane.setPadding(new Insets(20, 20, 20, 20));
        foodPane.getChildren().addAll(foodLabel, foodTable, addFoodBtn);
        
        // when button is pressed, add selected food item(s) to meal list
        addFoodBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                ObservableList<FoodItem> selected = foodTable.getSelectionModel().getSelectedItems();
                ArrayList<FoodItem> selectedArr = new ArrayList<FoodItem>();
               
                for (FoodItem f: selected) {
                    selectedArr.add(f);
                }
                
                updateSelectedFood(selectedArr);
                
                // FIXME: How to pass selected foods to MealListPane upon pressing button?
                // May require allowing this class access to the MealListPane via the GUIManager.
                // Perhaps passing a reference to the GUIManager instance in the FoodPane constructor, 
                // And making the GUIManager instance a global variable in FoodPane, allowing FoodPane
                // to call updateMealListPane()
            }
       });
        return foodPane;
    }
    
    /*
     * Takes in an ArrayList of FoodItems and fills the Table
     * with those foods.
     * @param food The ArrayList of FoodItems
     */
    public void setInitialTableData(ArrayList<FoodItem> food) {      
        ObservableList<FoodItem> foodObsList = FXCollections.observableList(food);
        
        // get data from food list to display in table
        this.foodTable.setItems(foodObsList);
      
    }
    
    /*
     * Returns an ArrayList containing the FoodItems 
     * selected from the table.
     * @return an ArrayList containing the selected FoodItems
     */
    public ArrayList<FoodItem> getNewItems() {
        return this.currSelected;
    }
    
    /*
     * Updates the selected food items in the table.
     * @param selected An ArrayList of the newly selected items.
     */
    private void updateSelectedFood(ArrayList<FoodItem> selected) {
        this.currSelected = selected;
    }
    
    /*
     * Sets up the columns in the table.
     * Sets up Name, Calories, Fat, Carbs, Fiber, and Protein
     * columns in that order.
     * 
     * FIXME: The TableView type requires exact getters for every column
     * it contains in order to read and display the data from the objects
     * passed to it. Getters will need to be added to the FoodItem class
     * in order to enable this funtionality. 
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setupColumns() {
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
        foodTable.getColumns().setAll(nameCol, caloriesCol, fatCol, carbsCol, fiberCol, proteinCol);
        
        // set the height of Food Table to a ratio of the screen's height
        foodTable.setMinHeight((0.60) * Screen.getPrimary().getBounds().getHeight());
    }

}
