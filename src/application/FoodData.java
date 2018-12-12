package application;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the backend for managing all 
 * the operations associated with FoodItems
 * 
 * Note: Not used. Solely implemented because the assignment said that this was required
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class FoodData implements FoodDataADT<FoodItem> {
    //change 
    //change
    //change
    // List of all the food items.
    private FoodList foodItemList;

    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, FoodItem>> indexes;
    
   
    /**
     * FoodData constructor 
     */
    public FoodData() {
        this.foodItemList = new FoodList();
        this.indexes = new HashMap<String, BPTree<Double, FoodItem>>();
    }
    
    
    /*Loads food items from file and retrieves nutrient information for each 
     * one. 
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
     */
    @Override
    public void loadFoodItems(String filePath) {
        IOHandler csvReader = new IOHandler();
        ArrayList<Food> foodList = csvReader.read(filePath);
        
        for (Food food: foodList) {
            foodItemList.addFood(food);
        }
    }

    /*Sifts through food list for the items that match the searched for
     * name.
     * 
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByName(java.lang.String)
     */
    @Override
    public List<FoodItem> filterByName(String substring) {
    	substring = substring.toLowerCase();
        List<FoodItem> filtered = new ArrayList<FoodItem>();
        
        for (Food food: foodItemList.getAll()) {
            if (food.getName().toLowerCase().contains(substring)) {
                filtered.add(new FoodItem(food));
            }
        }
        return filtered;
    }
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    @Override
    public List<FoodItem> filterByNutrients(List<String> rules) {
    	Map<String, Double> mins = new HashMap<String, Double>();
    	Map<String, Double> maxes = new HashMap<String, Double>();
    	for(String rule : rules){
    		String[] vals = rule.split(" ");
    		Double num;
    		try{//ignores illegal rules
				num = Double.parseDouble(vals[1]);
    		} catch(NumberFormatException e){
    			continue;
    		}
    		switch(vals[2]){
    			case("=="):
    				mins.put(vals[0], num);
    				maxes.put(vals[0], num);
    				break;
    			case("<="):
    				maxes.put(vals[0], num);
    				break;
    			case(">="):
    				mins.put(vals[0], num);
    				break;
    			default://not necessary, put here for clarity
    				continue;
    		}
    	}
		ArrayList<Food> foods = this.foodItemList.filterFoods(mins, maxes);
		ArrayList<FoodItem> out = new ArrayList<FoodItem>();
		for(Food food : foods){
			out.add(new FoodItem(food));
		}
    	return out;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
     */
    @Override
    public void addFoodItem(FoodItem food) {
    	Food in = new Food(food.getID(), food.getName(),
    					   food.getNutrientValue("calories"),
    					   food.getNutrientValue("fat"),
    					   food.getNutrientValue("carbohydrate"),
    					   food.getNutrientValue("fiber"),
    					   food.getNutrientValue("protein"));
    	this.foodItemList.addFood(in);
    }

    /*Returns a list of the food items. 
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoodItems()
     */
    @Override
    public List<FoodItem> getAllFoodItems() {
    	ArrayList<FoodItem> out = new ArrayList<FoodItem>();
    	for(Food food : this.foodItemList.getAll()){
    		out.add(new FoodItem(food));
    	}
    	return out;
    }


	@Override
	public void saveFoodItems(String filename) {
		IOHandler io = new IOHandler();
		ArrayList<Food> out = new ArrayList<Food>(this.foodItemList.getAll());
		Collections.sort(out);
		io.write(filename, out);
	}

}