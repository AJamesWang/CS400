package application;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private List<FoodItem> foodItemList;

    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, FoodItem>> indexes;
    
   
    /**
     * FoodData constructor 
     */
    public FoodData() {
        this.foodItemList = new ArrayList<FoodItem>();
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
            FoodItem foodItem = new FoodItem(food.getID(), food.getName());
            foodItem.addNutrient("calories", food.getCalories());
            foodItem.addNutrient("fat", food.getFat());
            foodItem.addNutrient("carbohydrate", food.getCarbs());
            foodItem.addNutrient("fiber", food.getFiber());
            foodItem.addNutrient("protein", food.getProtein());
            
            foodItemList.add(foodItem);
            
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
        List<FoodItem> filtered = new ArrayList<FoodItem>();
        
        for (FoodItem foodItem: foodItemList) {
            if (foodItem.getName().contains(substring)) {
                filtered.add(foodItem);
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
        // TODO : Complete
        return null;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
     */
    @Override
    public void addFoodItem(FoodItem food) {
        // TODO : Complete
    }

    /*Returns a list of the food items. 
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoodItems()
     */
    @Override
    public List<FoodItem> getAllFoodItems() {
        return this.foodItemList;
    }


	@Override
	public void saveFoodItems(String filename) {
		
		
	}

}
