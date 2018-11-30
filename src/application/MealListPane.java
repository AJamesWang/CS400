
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
    public MealListPane(){
        try {
            VBox borderPaneRight = new VBox();
            borderPaneRight.getChildren().addAll(mealPane(), mealAnalysis(20, 30, 40, 50));
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
            System.out.println("button pressed"); //CHANGE TO CALCULATE 
        }
    }
    
    public VBox mealAnalysis(int cal, int fat, int pro, int fib) {
    	this.setId("food-data");//sets the default font to food-data (see CSS)
        Label mealAnalysis = new Label("Meal Analysis: ");
        mealAnalysis.setId("section-heading");
        HBox hBox1 = new HBox();
        hBox1.getChildren().add(mealAnalysis);
        hBox1.setAlignment(Pos.TOP_LEFT);
        Label totalCals = new Label("Total calories:");
        HBox subBox1 = new HBox();
        Label numCals = new Label(" " + cal);
        subBox1.getChildren().add(numCals);
        subBox1.setAlignment(Pos.BASELINE_RIGHT); // trying to align numbers to the right?
        Label calNum = new Label(" " + cal);
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
        
        hBox2.getChildren().addAll(totalCals, subBox1);
        hBox3.getChildren().addAll(totalFat, fatNum);
        hBox4.getChildren().addAll(totalProtein, proNum);
        hBox5.getChildren().addAll(totalFiber, fibNum);
        
        VBox vBox1 = new VBox();
        vBox1.getChildren().addAll(hBox1, hBox2, hBox3, hBox4, hBox5);
   
        return vBox1;
    }
    public VBox mealPane() {
            Label mealLabel = new Label("Meal:");
            mealLabel.setId("section-heading");
            HBox hBox1 = new HBox();
            hBox1.getChildren().add(mealLabel);
            hBox1.setAlignment(Pos.TOP_LEFT);
            Button analyzeMeal = new Button("Analyze Meal");
            myHandler analyzeButton = new myHandler(analyzeMeal);
            analyzeMeal.setOnAction(analyzeButton);
            HBox hBox2 = new HBox();
            hBox2.getChildren().add(analyzeMeal);
            hBox2.setAlignment(Pos.BASELINE_CENTER);
            ScrollBar scroll = new ScrollBar();
            scroll.setOrientation(Orientation.VERTICAL);
            VBox vBox = new VBox();
            
            TableView<List<String>> mealTable = new TableView<>();
            mealTable.setMaxWidth(500);
            TableColumn name = new TableColumn("Name");
            name.setMinWidth(100);
 //           name.setCellValueFactory(new PropertyValueFactory<Food, String> ("name"));
            TableColumn cals = new TableColumn("Calories");
            cals.setMinWidth(20);
 //           cals.setCellValueFactory(new PropertyValueFactory<Food, Integer>("cals"));
            TableColumn fat = new TableColumn("Fat");
            fat.setMinWidth(20);
 //           fat.setCellValueFactory(new PropertyValueFactory<Food, Integer>("fat"));
            TableColumn protein = new TableColumn("Protein");
            protein.setMinWidth(20);
 //           protein.setCellValueFactory(new PropertyValueFactory<Food, Integer>("protein"));
            TableColumn fiber = new TableColumn("Fiber");
            fiber.setMinWidth(20);
 //           fiber.setCellFactory(new PropertyValueFactory<Food, Integer>("fiber"));
            TableColumn carbs = new TableColumn("Carbs");
            carbs.setMinWidth(20);
 //           fiber.setCellFactory(new PropertyValueFactory<Food, Integer>("fiber"));
            
            mealTable.getColumns().addAll(name, cals, fat, carbs, fiber, protein);
            ListView mealList = new ListView();
            ObservableList<String> list = FXCollections.observableArrayList();
            
            
            list.add("Pear");
            list.add( "Pineapple");
            list.add("Apple");
            mealList.setItems(list);
            mealTable.setMinHeight((0.40) * Screen.getPrimary().getBounds().getHeight());
//            mealList.setMinHeight(Screen.getPrimary().getBounds().getHeight());
            mealList.setMaxSize(100, 100);
   //         vBox.getChildren().addAll(hBox1, mealList, hBox2);
            vBox.getChildren().addAll(hBox1, mealTable, hBox2);
            return vBox;
    }

}