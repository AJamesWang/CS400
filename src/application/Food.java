package application;

public class Food {
    private String name;
    private int calories;
    private double fat;
    private int carbs;
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
    Food(String id, String name, int calories, double fat, int carbs, double fiber, double protein) {
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
     * @return id
     */
    public String idGenerator() {
        return "";
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getCalories() {
        return this.calories;
    }
    
    public void setCalories(int calories) {
        this.calories = calories;
    }
    
    public double getFat() {
        return this.fat;
    }
    
    public void setFat(double fat) {
        this.fat = fat;
    }
    
    public int getCarbs() {
        return this.carbs;
    }
    
    public void setCarbs(int carbs) {
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
}
