
package application;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import javax.swing.GroupLayout.Alignment;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class MealListPane extends BorderPane {
    private TableView mealTable = new TableView();
    private ArrayList<Food> mealArr = new ArrayList<Food>(); // list of current Foods in meal
    private int cal = 0;
    private int fat = 0;
    private int carbs = 0;
    private int fib = 0;
    private int pro = 0;
    private VBox mealAnalysisBox;
    HBox calBox = new HBox();
    HBox fatBox = new HBox();
    HBox carbBox = new HBox();
    HBox fibBox = new HBox();
    HBox proBox = new HBox();
    private TableView mealAnalysisTable = new TableView();
    TableColumn nameCol = new TableColumn("Name");
    TableColumn calsCol = new TableColumn("Calories");
    TableColumn fatCol = new TableColumn("Fat");
    TableColumn proteinCol = new TableColumn("Protein");
    TableColumn fiberCol = new TableColumn("Fiber");
    TableColumn carbsCol = new TableColumn("Carbs");
    private Food totalFood;
    private ArrayList<Food> totalFoodList = new ArrayList<Food>();

    public MealListPane(){
        try {
            VBox borderPaneRight = new VBox();
            borderPaneRight.getChildren().addAll(mealPane());
            this.setRight(borderPaneRight);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public interface EventHandler<T extends Event> extends EventListener
    {
        public void handle(T event);
    }

    class myHandler implements EventHandler<ActionEvent>, javafx.event.EventHandler<ActionEvent> {
        Button analyzeButton;
        myHandler(Button analyzeButton) {this.analyzeButton = analyzeButton; }
        @Override
        public void handle(ActionEvent event) {
            if (!mealArr.isEmpty()) {
                calculateTotals(mealArr);
            }
            updateMealAnalysis();
        }
    }


    class DeleteHandler implements EventHandler<ActionEvent>, javafx.event.EventHandler<ActionEvent> {
        Button deleteButton;
        DeleteHandler(Button deleteButton) {this.deleteButton = deleteButton; }
        @Override
        public void handle(ActionEvent event) {
            Food selectedFood = (Food) mealTable.getSelectionModel().getSelectedItem();
            mealTable.getItems().remove(selectedFood);
        }
    }

    public void calculateTotals(ArrayList<Food> food) {
        totalFood = new Food();
        totalFood.setCalories(0);
        totalFood.setCalories(0);
        totalFood.setFat(0);
        totalFood.setFiber(0);
        totalFood.setName("Total Food");
        totalFood.setProtein(0);

//        for (Food item : food) {
//            this.cal += item.getCalories();
//            this.fat += item.getFat();
//            this.carbs += item.getCarbs();
//            this.fib += item.getFiber();
//            this.pro += item.getProtein();
//        }
        
        for (Food item : food) {
            totalFood.setCalories(totalFood.getCalories()+ item.getCalories());
            totalFood.setFat(totalFood.getFat()+ item.getFat());
            totalFood.setCarbs(totalFood.getCarbs()+ item.getCarbs());
            totalFood.setFiber(totalFood.getFiber()+ item.getFiber());
            totalFood.setProtein(totalFood.getProtein()+ item.getProtein());
        }
    }    


    public VBox initializeMealAnalysis() {
        this.mealAnalysisTable.getColumns().setAll(nameCol, calsCol, fatCol, carbsCol, fiberCol, proteinCol);
        this.mealAnalysisTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.mealAnalysisTable.setMinHeight(0.5 * Screen.getPrimary().getBounds().getHeight());
        mealAnalysisBox = new VBox();
        
        this.setId("food-data");//sets the default font to food-data (see CSS)
        Label mealAnalysis = new Label("Meal Analysis: ");
        mealAnalysis.setId("section-heading");
        HBox hBox1 = new HBox();
        hBox1.getChildren().add(mealAnalysis);
        hBox1.setAlignment(Pos.TOP_CENTER);
//        Label totalCals = new Label("Total calories:");
//        Label calNum = new Label(" " + cal);
//        Label carbsNum = new Label(" " + carbs);
//        Label totalCarbs = new Label("Total carbs:");
//        Label carbNum = new Label(" " + cal);
//        Label totalFat = new Label("Total fat:");
//        Label fatNum = new Label(" " + fat);
//        Label totalProtein = new Label("Total protein:");
//        Label proNum = new Label(" " + pro);
//        Label totalFiber = new Label("Total fiber:");
//        Label fibNum = new Label(" " + fib);
//
//        calBox.getChildren().setAll(totalCals, calNum);
//        fatBox.getChildren().setAll(totalFat, fatNum);
//        carbBox.getChildren().setAll(totalCarbs, carbNum);
//        proBox.getChildren().setAll(totalProtein, proNum);
//        fibBox.getChildren().setAll(totalFiber, fibNum);


        // Analyze Meal Button
        Button analyzeMeal = new Button("Analyze Meal");
        myHandler analyzeButton = new myHandler(analyzeMeal);
        analyzeMeal.setOnAction(analyzeButton);         
        HBox analyzeButtonBox= new HBox();
        analyzeButtonBox.getChildren().add(analyzeMeal);
        analyzeButtonBox.setAlignment(Pos.TOP_CENTER);
  //      mealAnalysisBox.getChildren().setAll(analyzeButtonBox, hBox1, calBox, fatBox, carbBox, proBox, fibBox);
        mealAnalysisBox.getChildren().setAll(hBox1, analyzeButtonBox, mealAnalysisTable );
        return mealAnalysisBox;
    }
    
    public void updateMealAnalysis() {
        this.totalFoodList.add(totalFood);
        ObservableList<Food> totalFoodList = FXCollections.observableList(this.totalFoodList);
        this.mealAnalysisTable.setItems(totalFoodList);
    }
    
    
    public VBox mealPane() {
        Label mealLabel = new Label("Meal:");
        mealLabel.setId("section-heading");
        HBox hBox1 = new HBox();
        hBox1.getChildren().add(mealLabel);
        hBox1.setAlignment(Pos.TOP_LEFT);

        VBox vBox = new VBox();

        // Delete Food Button
        Button deleteButton = new Button("Delete Food");
        DeleteHandler deleteHandler = new DeleteHandler(deleteButton);
        deleteButton.setOnAction(deleteHandler);            
        HBox hBox3 = new HBox();
        hBox3.getChildren().add(deleteButton);
        hBox3.setAlignment(Pos.BASELINE_CENTER);


        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory("Name"));
        TableColumn cals = new TableColumn("Calories");
        cals.setCellValueFactory(new PropertyValueFactory("Calories"));
        TableColumn fat = new TableColumn("Fat");
        fat.setCellValueFactory(new PropertyValueFactory("Fat"));
        TableColumn protein = new TableColumn("Protein");
        protein.setCellValueFactory(new PropertyValueFactory("Protein"));
        TableColumn fiber = new TableColumn("Fiber");
        fiber.setCellValueFactory(new PropertyValueFactory("fiber"));
        TableColumn carbs = new TableColumn("Carbs");
        carbs.setCellValueFactory(new PropertyValueFactory("Carbs"));
        this.mealTable.getColumns().setAll(name, cals, fat, carbs, fiber, protein);
        this.mealTable.setMinHeight((0.27) * Screen.getPrimary().getBounds().getHeight());
        this.mealTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        vBox.getChildren().addAll(hBox1, mealTable, hBox3);
        return vBox;
    }

    public void updateMlpData(ArrayList<Food> food) {
        this.mealArr.addAll(food);
        ObservableList<Food> mealObsList = FXCollections.observableList(mealArr);
        this.mealTable.setItems(mealObsList);
    }

}