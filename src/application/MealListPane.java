
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


    public MealListPane(){
        try {
            VBox borderPaneRight = new VBox();
            borderPaneRight.getChildren().addAll(mealPane(), mealAnalysis(cal, fat, carbs, fib, pro));
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
            if (mealArr.isEmpty()) {
                calculateTotals(mealArr);
            }
            mealAnalysis(cal, fat, carbs, fib, pro);
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

        for (Food item : food) {
            this.cal += item.getCalories();
            this.fat += item.getFat();
            this.carbs += item.getCarbs();
            this.fib += item.getFiber();
            this.pro += item.getProtein();
        }
    }    


    public VBox getMealAnalysisBox() {
        return mealAnalysisBox;
    }


    public VBox mealAnalysis( int cal, int fat, int carbs, int fib, int pro) {
        mealAnalysisBox = new VBox();
        this.setId("food-data");//sets the default font to food-data (see CSS)
        Label mealAnalysis = new Label("Meal Analysis: ");
        mealAnalysis.setId("section-heading");
        HBox hBox1 = new HBox();
        hBox1.getChildren().add(mealAnalysis);
        hBox1.setAlignment(Pos.TOP_LEFT);
        Label totalCals = new Label("Total calories:");
        Label calNum = new Label(" " + cal);
        Label carbsNum = new Label(" " + carbs);
        Label totalCarbs = new Label("Total carbs:");
        Label carbNum = new Label(" " + cal);
        Label totalFat = new Label("Total fat:");
        Label fatNum = new Label(" " + fat);
        Label totalProtein = new Label("Total protein:");
        Label proNum = new Label(" " + pro);
        Label totalFiber = new Label("Total fiber:");
        Label fibNum = new Label(" " + fib);


        HBox hBox2 = new HBox();
        HBox hBox3 = new HBox();
        HBox hBox4 = new HBox();
        HBox hBox5 = new HBox();
        HBox hBox6 = new HBox();

        hBox2.getChildren().addAll(totalCals, calNum);
        hBox3.getChildren().addAll(totalFat, fatNum);
        hBox4.getChildren().addAll(totalCarbs, carbNum);
        hBox6.getChildren().addAll(totalProtein, proNum);
        hBox5.getChildren().addAll(totalFiber, fibNum);


        // Analyze Meal Button
        Button analyzeMeal = new Button("Analyze Meal");
        myHandler analyzeButton = new myHandler(analyzeMeal);
        analyzeMeal.setOnAction(analyzeButton);         
        HBox analyzeButtonBox= new HBox();
        analyzeButtonBox.getChildren().add(analyzeMeal);
        analyzeButtonBox.setAlignment(Pos.BASELINE_CENTER);

        mealAnalysisBox.getChildren().addAll(analyzeButtonBox, hBox1, hBox2, hBox3, hBox4, hBox5, hBox6);
        return mealAnalysisBox;
    }
    public VBox mealPane() {
        Label mealLabel = new Label("Meal:");
        mealLabel.setId("section-heading");
        HBox hBox1 = new HBox();
        hBox1.getChildren().add(mealLabel);
        hBox1.setAlignment(Pos.TOP_LEFT);

        // Analyze Meal Button
        Button analyzeMeal = new Button("Analyze Meal");
        myHandler analyzeButton = new myHandler(analyzeMeal);
        analyzeMeal.setOnAction(analyzeButton);         
        HBox hBox2 = new HBox();
        hBox2.getChildren().add(analyzeMeal);
        hBox2.setAlignment(Pos.BASELINE_CENTER);
        ScrollBar scroll = new ScrollBar();
        scroll.setOrientation(Orientation.VERTICAL);
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
        hBox2.setMinHeight(20);
        vBox.getChildren().addAll(hBox1, mealTable, hBox3);

        return vBox;
    }

    public void updateMlpData(ArrayList<Food> food) {
        this.mealArr.addAll(food);
        ObservableList<Food> mealObsList = FXCollections.observableList(mealArr);
        this.mealTable.setItems(mealObsList);
    }

}