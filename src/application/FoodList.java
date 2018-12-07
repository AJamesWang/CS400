package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FoodList {
	private HashMap<String, BPTree<Double, Food>> foodTrees;
	private Set<Food> foods;
	
	public FoodList(){
		foodTrees = new HashMap<String, BPTree<Double, Food>>();
		foods = new HashSet<Food>();
		for(String id : Food.NUTRIENT_IDS){
			foodTrees.put(id, new BPTree<Double, Food>());
		}
	}
	
	public ArrayList<Food> filterFoods(Map<String, Double> mins, Map<String, Double> maxes){
		Set<Food> out = new HashSet<Food>();
		out.addAll(this.foods);
		for(String id : Food.NUTRIENT_IDS){
			Double min = mins.get(id)==null?null:mins.get(id);
			Double max = maxes.get(id)==null?null:maxes.get(id);
			if(min==null &&  max==null){ continue; }
			List<Food> allowed = this.filterFoods(id, min, max);
			out.retainAll(allowed);
		}
		return new ArrayList<Food>(out);
	}
	
	/*
	 * assumes that at least one of the doubles is non-null
	 */
	public List<Food> filterFoods(String id, Double min, Double max){
		if(min==null){
			System.out.println(max);
			return this.foodTrees.get(id).rangeSearch(max, "<=");
		} else if(max==null){
			return this.foodTrees.get(id).rangeSearch(min, ">=");
		} else{
			List<Food> mins = this.foodTrees.get(id).rangeSearch(min, ">=");
			List<Food> maxes = this.foodTrees.get(id).rangeSearch(max, "<=");
			List<Food> out = new ArrayList<Food>();
			for(Food food:maxes){
				if(mins.contains(food))
					out.add(food);
			}
			return out;
		}
	}
	
	public void addFood(Food food){
		this.foods.add(food);
		for(String id : Food.NUTRIENT_IDS){
			BPTree<Double, Food> target = foodTrees.get(id);
			target.insert(food.getNutrientValue(id), food);
		}
	}
	
}
