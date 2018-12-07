package application;

import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Pair;

public class FoodPane extends BorderPane{
    private TableView foodTable = new TableView(); // table to display food options
    private MealListPane mlp; // reference to meal list pane
    private ArrayList<Food> foodArrList = new ArrayList<Food>();
    TextField filterField;
    
    /*
     * Constructs a FoodPane containing information
     * about the name and nutritional content of various foods.
     * @param mlp A reference to the MealListPane that will be fed data from the FoodPane.
     */
    public FoodPane(MealListPane mlp) {
        try {
            this.mlp = mlp;
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
        this.filterField = new TextField();
        this.filterField.setPromptText("filter name here");
        Button addFoodToMealBtn = new Button("Add food(s) to meal");
        Button addSingleFoodBtn = new Button("Add single food to food list");
        Button loadAddtnlFoodBtn = new Button("Load new food list from file");
        Button saveFoodsBtn = new Button("Save current food list");
        
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
        foodPane.getChildren().addAll(foodLabel, filterField, foodTable, addFoodToMealBtn, addSingleFoodBtn,
                        loadAddtnlFoodBtn, saveFoodsBtn);
        
        ///////////////////////////
        // Button Event Handling //
        ///////////////////////////
        
        // when add food to meal is pressed, add selected food item(s) to meal list
        addFoodToMealBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Food> selected = foodTable.getSelectionModel().getSelectedItems();
                ArrayList<Food> selectedArr = new ArrayList<Food>();
               
                for (Food f: selected) {
                    selectedArr.add(f);
                }
                                
                updateMealListPane(selectedArr);
            }
       });
        
        // when add food to list is pressed, deploy form and add food to list
        addSingleFoodBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
               addSingularFood();
            }
       });
        
        // when load new food list from file is pressed, deploy form and load new data into list
        loadAddtnlFoodBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                loadNewFoodFile();
            }
       });
        
        // when load save food list button is pressed, deploy form for user to name the file then export the data
        saveFoodsBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
               saveFoodToFile();
            }
       });
        
        return foodPane;
    }
    
    /*
     * Deploys a form to get user input about a food and then
     * adds that food to the food list.
     */
    public void addSingularFood() {
        //Creating a GridPane container
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        
        //Defining the Name text field
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setPrefColumnCount(10);
        GridPane.setConstraints(nameField, 0, 0);
        
        //Defining the Calories text field
        TextField calsField = new TextField();
        calsField.setPromptText("Calories");
        GridPane.setConstraints(calsField, 0, 1);
        
        //Defining the Fat text field
        TextField fatField = new TextField();
        fatField.setPromptText("Fat");
        GridPane.setConstraints(fatField, 0, 2);
        
        //Defining the Carbs text field
        TextField carbsField = new TextField();
        carbsField.setPromptText("Carbs");
        GridPane.setConstraints(carbsField, 0, 3);
        
        //Defining the Fiber text field
        TextField fiberField = new TextField();
        fiberField.setPromptText("Fiber");
        GridPane.setConstraints(fiberField, 0, 4);
        
        //Defining the Protein text field
        TextField proteinField = new TextField();
        proteinField.setPromptText("Protein");
        GridPane.setConstraints(proteinField, 0, 5);
        
        grid.getChildren().addAll(nameField, calsField, fatField, carbsField, fiberField, proteinField);
        Dialog<Food> dialog = new Dialog<Food>();
        dialog.setTitle("Add Food");
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
        
        // get user input and add food to table
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String id = "invalidID";
                String name = "invalidName";
                double calories = -1;
                double fat = -1;
                double carbs = -1;
                double fiber = -1;
                double protein = -1;
                
                try {                    
                    name = nameField.getText();
                    calories = Double.parseDouble(calsField.getText());
                    fat = Double.parseDouble(fatField.getText());
                    carbs = Double.parseDouble(carbsField.getText());
                    fiber = Double.parseDouble(fiberField.getText());
                    protein = Double.parseDouble(proteinField.getText());
                    
                    // verify that all nutrient fields are non negative
                    if (calories < 0 || fat < 0 || carbs < 0 || fiber < 0 || protein < 0) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Negative Value(s) Not Permitted");
                        alert.setHeaderText(null);
                        alert.setContentText("Negative values for nutrient fields are not permitted.");
                        alert.showAndWait();
                        return null;
                    }
                } catch (NumberFormatException e) {
                    // tell user that error occurred and food was not added
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("An error occured while adding single food to food list.");
                    alert.showAndWait();
                    return null;
                }
                
                return new Food(name, calories, fat, carbs, fiber, protein);
                                 
            }
            return null;
        });
        
        // add food to food list
        Optional<Food> foodData = dialog.showAndWait();
        if (foodData.isPresent()) {
            foodArrList.add(foodData.get());
            updateFoodPane(foodArrList);
            dialog.close();
        }   
    }
    
    /*
     * Deploys form to get path to food file from the user
     * and then loads those foods into the food list.
     */
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
            IOHandler csvReader = new IOHandler();
            updateFoodPane(csvReader.read(path.get()));
            dialog.close();
        }
    }
    
    /*
     * Deploys form to get name of food file from the user
     * and then saves the currently filtered foods to that file.
     * 
     * FIXME: Not sure if saving filtered data or all foods available.
     * Ask James how the filtering works/how to access the filtered data
     * from here.
     */
    public void saveFoodToFile() {
        TextInputDialog dialog = new TextInputDialog("ex: FoodList");
        dialog.setTitle("Meal Planner");
        dialog.setGraphic(null);
        dialog.setHeaderText("Enter name of file to be created.");
        dialog.setContentText("Name:");
        dialog.getDialogPane().setMinWidth(500);
        
        // Get file name from user and load current filtered foods into new file
        Optional<String> fileName = dialog.showAndWait();
        if (fileName.isPresent()) {
            IOHandler csvWriter = new IOHandler();
            csvWriter.write(fileName.get(), foodArrList);
            dialog.close();
        }
        
    }
    protected void updateMealListPane(ArrayList<Food> selectedArr) {
        this.mlp.updateMealListPane(selectedArr);       
    }

    /*
     * Takes in an ArrayList of FoodItems and fills the Table
     * with those foods.
     * @param food The ArrayList of FoodItems
     */
     //TODO: maybe make foodObsList a class variable? I think to add new elements, you'll need to access it
     //Or maybe filteredData?

    public void updateFoodPane(ArrayList<Food> food) {
        this.foodArrList = food;

        //taken from https://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
        FilteredList<Food> filteredData = new FilteredList<Food>(FXCollections.observableList(foodArrList), p->true);

        this.filterField.textProperty().addListener((observable, oldVal, newVal) -> {
        	//I think the predicate determines what's shown and what isn't
        	filteredData.setPredicate( target -> {
        		//Empty filter, show everything
				if (newVal==null || newVal.isEmpty()){
					return true;
				}
				
				String input = newVal.toLowerCase();//filter isn't case sensitive
				if(target.getName().toLowerCase().contains(input)){
					return true;
				} else{
					return false;
				}
				
        	});
        });
        SortedList<Food> sortedData = new SortedList<Food>(filteredData);
        //tbh, not sure what's the diff between Observable, Filtered, and Sorted list.
        sortedData.comparatorProperty().bind(this.foodTable.comparatorProperty());
        this.foodTable.setItems(sortedData);   

      
    }
    
    
    /*
     * Sets up the columns in the table.
     * Sets up Name, Calories, Fat, Carbs, Fiber, and Protein
     * columns in that order.
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
        this.foodTable.getColumns().setAll(nameCol, caloriesCol, fatCol, carbsCol, fiberCol, proteinCol);
        
        // set the height of Food Table to a ratio of the screen's height
        this.foodTable.setMinHeight((0.60) * Screen.getPrimary().getBounds().getHeight());
        
        // make alphabetically sorted based on name
        this.foodTable.getSortOrder().add(nameCol);
    }

}
