package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodList {
	private HashMap<String, BPTree<Double, Food>> foodTrees;
	
	public FoodList(){
		for(String id : Food.NUTRIENT_IDS){
			foodTrees.put(id, new BPTree<Double, Food>());
		}
	}
	
	public List<Food> filterFoods(String id, double min, double max){
		List<Food> mins = this.foodTrees.get(id).rangeSearch(min, ">=");
		List<Food> maxes = this.foodTrees.get(id).rangeSearch(max, ">=");
		ArrayList<Food> out = new ArrayList<Food>();
		for(Food food:maxes){
			if(mins.contains(food))
				out.add(food);
		}
		return out;
	}
	
	public void addFood(Food food){
		for(String id : Food.NUTRIENT_IDS){
			BPTree<Double, Food> target = foodTrees.get(id);
			target.insert(food.getNutrientValue(id), food);
		}
	}
	
}
