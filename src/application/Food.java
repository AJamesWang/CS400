package application;

/*
 * Note: We implemented and used this separate Food class to have standard getters
 * and setters to use in the TableView data structures. TableView requires types with 
 * explicit getters in order to display data about the items it contains.
 * 
 * 
 */
public class Food implements Comparable {
	public static final String[] NUTRIENT_UNITS = {" Cal", " g", " g", " g", " g"};
	public static final String[] NUTRIENT_NAMES = {"Calories: ", "Fat: ", "Carbs: ", "Fiber: ", "Protein: "};
	public static final String[] NUTRIENT_IDS =   {"calories", "fat", "carbohydrate", "fiber", "protein"};
    private String name;
    private double calories;
    private double fat;
    private double carbs;
    private double fiber;
    private double protein;
    private String id;
    
    /*
     * Constructs a Food object with id, name, calories, fat, carbs, 
     * fiber, and protein specified.
     * 
     * Preconditions: All nutrient related fields must be nonnegative.
     * 
     * @param id
     * @param name
     * @param calories
     * @param fat
     * @param carbs
     * @param fiber
     * @param protein
     */
    /**
     * @param id
     * @param name
     * @param calories
     * @param fat
     * @param carbs
     * @param fiber
     * @param protein
     */
    Food(String id, String name, double calories, double fat, double carbs, double fiber, double protein) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.fiber = fiber;
        this.protein = protein;       
    }
    
    /*
     * Constructs a Food object with name, calories, fat, carbs, 
     * fiber, and protein specified. ID generated internally.
     * 
     * Preconditions: All nutrient related fields must be nonnegative.
     * 
     * @param name
     * @param calories
     * @param fat
     * @param carbs
     * @param fiber
     * @param protein
     */
    /**
     * @param name
     * @param calories
     * @param fat
     * @param carbs
     * @param fiber
     * @param protein
     */
    Food(String name, double calories, double fat, double carbs, double fiber, double protein) {
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.fiber = fiber;
        this.protein = protein;  
        this.id = idGenerator();
    }
    
    /*
     * Generates a unique id.
     * 
     * FIXME: IMPLEMENT
     * @return id
     */
    private String idGenerator() {
        return "IMPLEMENT ID GENERATOR IN FOOD CLASS";
    }
    
    /**
     * Returns ID
     */
    public String getID() {
        return this.id;
    }
    /**
     * Returns Name 
     */
    public String getName() {
        return this.name;
    }
    /**
     * Sets Name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Returns Calories 
     */
    public Double getCalories() {
        return this.calories;
    }
    /**
     * Sets calories 
     */
    public void setCalories(double calories) {
        this.calories = calories;
    }
    /**
     * Returns Fat
     */
    public double getFat() {
        return this.fat;
    }
    /**
     * Sets Fat
     */
    public void setFat(double fat) {
        this.fat = fat;
    }
    /**
     * Returns Carbs
     */
    public double getCarbs() {
        return this.carbs;
    }
    /**
     * Sets Carbs
     */
    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }
    /**
     * Returns Fiber
     */
    public double getFiber() {
        return this.fiber;
    }
    /**
     * Sets Fiber
     */
    public void setFiber(double fiber) {
        this.fiber = fiber;
    }
    /**
     * Returns Protein
     */
    public double getProtein() {
        return this.protein;
    }
    /**
     * Sets Protein 
     */
    public void setProtein(double protein) {
        this.protein = protein;
    }
    /**
     * Returns Nutrient Value
     */
    public double getNutrientValue(String id){
    	switch(id){
    		case("calories"):
    			return this.getCalories();
    		case("fat"):
    			return this.getFat();
    		case("carbohydrate"):
    			return this.getCarbs();
    		case("fiber"):
    			return this.getFiber();
    		case("protein"):
    			return this.getProtein();
    		default:
    			throw new RuntimeException("Damnit, ID not recognized");
    	}
    }
    /*
     * Cuts off all trailing zeroes and converts value to String
     */
	private static final double LIMIT = .01;
	public static String format(Double val){
		if(val==null) return "-";
		if(Math.abs(val-val.intValue()) < LIMIT){//the double is close enough to an int
			return Integer.toString(val.intValue());
		} else{
			return Double.toString(val);//Not val.toString b/c want to match above line 
		}
	}

    /*
     * Compares Food object to another Food object based on
     * the IDs.
     * 
     * @return -1 If the ID is lesser
     * @return 0 If the IDs are equal
     * @return 1 If the ID is greater
	public static final String[] NUTRIENT_IDS =   {"calories", "fat", "carbohydrate", "fiber", "protein"};
     */
    @Override
    public int compareTo(Object o) {
        return this.id.compareTo(((Food)o).getID());
    }
    /*Returns ID's hashCode 
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode(){
    	return this.id.hashCode();
    }
}
