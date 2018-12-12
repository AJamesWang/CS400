package application;
	
import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
/**
 * Container for all search related GUI components.
 * Also holds higher-level searching methods
 * For lower-level searches, see FoodList or BPTree
 * 
 * @authors d-team 57
 */

public class SearchPane extends VBox{
	GUIManager manager;
	BorderPane titlePane;
	GridPane dataPane;
	Map<String, TextField> mins;
	Map<String, TextField> maxes;
	Map<String, Double> curMins;
	Map<String, Double> curMaxes;
	HBox buttonPane;
	
	/**
	 * Creates all the internal GUI components
	 * @param  manager - parent GUIManager which contains this pane
	 */
	protected void create(GUIManager manager){
		this.manager = manager;
        this.setPadding(new Insets(20, 20, 20, 20));
		this.generateTitle();
		this.generateData();
		this.generateButtons();
	}
	
	/**
	 * Sets all the values in the text boxes to whatever's listed in CurVals
	 */
	 protected void update(){
		 for(String id : Food.NUTRIENT_IDS){
			 mins.get(id).clear();
			 mins.get(id).setPromptText(Food.format(curMins.get(id)));
			 maxes.get(id).clear();
			 maxes.get(id).setPromptText(Food.format(curMaxes.get(id)));
		 }
	 }
	/**
	 * Updates curMins and curMaxes data values
	 */
	 private void updateCurVals(){
		 for(String id : Food.NUTRIENT_IDS){
			 try{
				 this.curMins.put(id, Double.parseDouble(this.mins.get(id).getText()));
			 } catch(NumberFormatException e){
			 }
			 try{
				 this.curMaxes.put(id, Double.parseDouble(this.maxes.get(id).getText()));
			 } catch(NumberFormatException e){
			 }
		 }
	 }
	
	/**
	 * tells GUIManager filter was updated
	 */
	private void promptGUIManager(){
		this.manager.constraintsUpdated();
	}
	
	
	/**
	 * gets current min constraints
	 * @return Map<String, Double> a map connecting each nutrient with its min value
	 */
	protected Map<String, Double> getMins(){
		return this.curMins;
	}
	
	/**
	 * gets current max constraints
	 * @return Map<String, Double. a mpa connecting each nutrient with its max value
	 */
	protected Map<String, Double> getMaxes(){
		return this.curMaxes;
	}
	
	
	/*
	 * Updates min/max values, then tells GUIManager that values were changed
	 */
	 private void search(){
		 this.updateCurVals();
		 this.promptGUIManager();
		 update();
	 }
	 
	
	/*
	 * tells GUIManager to clear min/max data
	 */
	 private void clearData(){
		 this.curMins.clear();
		 this.curMaxes.clear();
		 this.manager.constraintsUpdated();
		 this.update();
	 }
	 
	 
	/*
	 * Creates all necessary title components
	 */
	private void generateTitle(){
		Text title = new Text("Filter foods by nutrients:");
		title.setId("section-heading");
		
		this.titlePane = new BorderPane();
		this.titlePane.setCenter(title);
		this.getChildren().add(this.titlePane);
	}
	
	
	/*
	 * Creates all necessary data input components
	 */
	private void generateData(){
		this.dataPane = new GridPane();
		this.dataPane.setHgap(10);
		this.dataPane.setId("food-data");
		this.mins = new HashMap<String, TextField>();
		this.maxes = new HashMap<String, TextField>();
		this.curMins = new HashMap<String, Double>();
		this.curMaxes = new HashMap<String, Double>();
		/*Labels*/
		this.dataPane.add(new Label("Nutrient"), 0, 0);
		//TODO: find some cleaner way of padding columns (new problem, see below)
		this.dataPane.add(new Label("Min     "), 1, 0);
		this.dataPane.add(new Label("Max     "), 2, 0);
		/*Input fields*/
		for(int i=0; i<Food.NUTRIENT_IDS.length; i++){
			int row = i+1;
			String id = Food.NUTRIENT_IDS[i];
			String _nutrient = Food.NUTRIENT_NAMES[i];
			Label nutrient = new Label(_nutrient);
			nutrient.setId("food-data");
			//TODO: get rid of the magic numbers
			TextField min = new TextField();
			min.setPromptText("-");
			min.setMaxWidth(70);
			min.setId("food-data");
			min.setOnKeyPressed((ke)->{if(ke.getCode().equals(KeyCode.ENTER))this.search();});
			TextField max = new TextField();
			max.setPromptText("-");
			max.setMaxWidth(70);
			max.setId("food-data");
			max.setOnKeyPressed((ke)->{if(ke.getCode().equals(KeyCode.ENTER))this.search();});
			//TODO: look into TextFormatter? To limit input to numbers?
			//https://stackoverflow.com/questions/8381374/how-to-implement-a-numberfield-in-javafx-2-0
			this.mins.put(id, min);
			this.maxes.put(id, max);
			//TODO: mark somewhere that null means no constraint (related to magic numbers)
			//put(null) not necessary b/c map.get() returns null anyway. However, good for clarity
			this.curMins.put(id, null);
			this.curMaxes.put(id, null);
			this.dataPane.add(nutrient, 0, row);
			this.dataPane.add(min, 1, row);
			this.dataPane.add(max, 2, row);
		}
		this.dataPane.setAlignment(Pos.CENTER);
		this.getChildren().add(this.dataPane);
	}
	
	/*
	 * Generates all necessary buttons
	 */
	private void generateButtons(){
		Button searchButton = new Button("search (enter)");//TODO: consider renaming to filter?
		searchButton.setOnAction(e->search());
		KeyCodeCombination search = new KeyCodeCombination(KeyCode.F, KeyCodeCombination.SHORTCUT_DOWN);
		this.getScene().getAccelerators().put(search, ()->searchButton.fire());
		Button resetButton = new Button("clear (ctrl-c)");
		resetButton.setOnAction(e->clearData());
		KeyCodeCombination reset = new KeyCodeCombination(KeyCode.C, KeyCodeCombination.SHORTCUT_DOWN);
		this.getScene().getAccelerators().put(reset, ()->resetButton.fire());
		
		this.buttonPane = new HBox();
		this.buttonPane.getChildren().addAll(searchButton, resetButton);
		this.buttonPane.setAlignment(Pos.CENTER);
		this.getChildren().add(this.buttonPane);
	}
	
}