package application;

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
     * Constructs a Food object with name, calories, fat, carbs, 
     * fiber, and protein specified.
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
    
    
    Food(String name) {
        this.name = name;
        this.id = idGenerator();
        this.calories = -1;
        this.fat = -1;
        this.carbs = -1;
        this.fiber = -1; 
        this.protein = -1;
    }
    
    /*
     * Default empty constructor.
     */
    Food() {
        this.name = "";
        this.calories = -1;
        this.fat = -1;
        this.carbs = -1;
        this.fiber = -1; 
        this.protein = -1;
    }
    
    /*
     * Generates a unique id.
     * 
     * FIXME: IMPLEMENT
     * @return id
     */
    private String idGenerator() {
        return "";
    }

    public String getID() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Double getCalories() {
        return this.calories;
    }
    
    public void setCalories(double calories) {
        this.calories = calories;
    }
    
    public double getFat() {
        return this.fat;
    }
    
    public void setFat(double fat) {
        this.fat = fat;
    }
    
    public double getCarbs() {
        return this.carbs;
    }
    
    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }
    
    public double getFiber() {
        return this.fiber;
    }
    
    public void setFiber(double fiber) {
        this.fiber = fiber;
    }
    
    public double getProtein() {
        return this.protein;
    }
    
    public void setProtein(double protein) {
        this.protein = protein;
    }
    
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
}
