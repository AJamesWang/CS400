package data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents a food item with all its properties.
 * 
 * @author aka
 */
public class FoodItem {

	public static final String[] NUTRIENT_IDS =   {"calories", "fat", "carbohydrate", "fiber", "protein"};
	public static final String[] NUTRIENT_NAMES = {"Calories: ", "Fat: ", "Carbs: ", "Fiber: ", "Protein: "};
	public static final String[] NUTRIENT_UNITS = {" Cal", " g", " g", " g", " g"};

    // The name of the food item.
    private String name;

    // The id of the food item.
    private String id;

    // Map of nutrients and value.
    private HashMap<String, Double> nutrients;
    
    /**
     * Constructor
     * @param name name of the food item
     * @param id unique id of the food item 
     */
    public FoodItem(String id, String name) {
    	this.nutrients = new HashMap<String, Double>();
    	this.id = id;
    	this.name = name;
    }
    
    /**
     * Gets the name of the food item
     * 
     * @return name of the food item
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the unique id of the food item
     * 
     * @return id of the food item
     */
    public String getID() {
    	return this.id;
    }
    
    /**
     * Gets the nutrients of the food item
     * 
     * @return nutrients of the food item
     */
    public HashMap<String, Double> getNutrients() {
    	return (HashMap<String, Double>) this.nutrients.clone();
    }

    /**
     * Adds a nutrient and its value to this food. 
     * If nutrient already exists, updates its value.
     */
    public void addNutrient(String name, double value) {
    	this.nutrients.put(name, value);
    }

    /**
     * Returns the value of the given nutrient for this food item. 
     * If not present, then returns 0.
     */
    public double getNutrientValue(String name) {
    	Double out = this.nutrients.get(name);
    	return out==null?0:out;
    }
    
    /*
     * Cuts off all trailing zeroes and converts value to String
     */
	private static final double LIMIT = .1;
	public static String format(double val){
		if(Math.abs(val-(int)val) < LIMIT){
			return Integer.toString((int) val);
		} else{
			return Double.toString(val);
		}
	}
 
}
