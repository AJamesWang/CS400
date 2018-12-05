package application;

import java.util.ArrayList;
import java.util.HashMap;

public class FoodList {
	private HashMap<String, BPTree<Double, Food>> foodTrees;
	
	public FoodList(){
		for(String id : Food.NUTRIENT_IDS){
			foodTrees.put(id, new BPTree<Double, Food>());
		}
	}
	
	public ArrayList<Food> filterFoods(String id, double min, double max){
		//TODO: implement
	}
	
	public void addFood(Food food){
		for(String id : Food.NUTRIENT_IDS){
			BPTree<Double, Food> target = foodTrees.get(id);
			target.insert(food.getNutrientValue(id), food);
		}
	}
	
}
