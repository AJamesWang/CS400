package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class IOHandler {
   
    /*
     * Reads info from CSV file and returns an ArrayList of 
     * the Food items it contains.
     * 
     * @param filePath String containing path to CSV file
     * @return ArrayList of Food items from file
     */
    public ArrayList<Food> read(String filePath) {
        ArrayList<Food> foodList = new ArrayList<Food>(); // ArrayList of Foods from the file
        BufferedReader br = null; 
        String line = "";
        String csvSplitBy = ","; // split lines based on commas
        
        try {
            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] foodData = line.split(csvSplitBy);
                
                // line does not have the proper amount of data points, so skip
                if (foodData.length != 12) {
                    continue;
                }
               
                // convert input to proper format, create Food and add to list
                String id = "invalidID";
                String name = "invalidName";
                double calories = -1;
                double fat = -1;
                double carbs = -1;
                double fiber = -1;
                double protein = -1;
                
                try {
                    id = foodData[0];
                    name = foodData[1];
                    if (!foodData[2].equals("calories")) { continue; }
                    calories = Double.parseDouble(foodData[3]);
                    if (!foodData[4].equals("fat")) { continue; }
                    fat = Double.parseDouble(foodData[5]);
                    if (!foodData[6].equals("carbohydrate")) { continue; }
                    carbs = Double.parseDouble(foodData[7]);
                    if (!foodData[8].equals("fiber")) { continue; }
                    fiber = Double.parseDouble(foodData[9]);
                    if (!foodData[10].equals("protein")) { continue; }
                    protein = Double.parseDouble(foodData[11]);
                } catch (NumberFormatException e) {
                   continue;
                }
                
                foodList.add(new Food(id, name, calories, fat, carbs, fiber, protein));               
            }          
        } catch (FileNotFoundException e) {
            // tell user that file could not be found
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("File Not Found");
            alert.setHeaderText(null);
            alert.setContentText("The file " + filePath + " could not be found. Could not load data.");
            alert.showAndWait();
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {                   
                }
            }
        }        
        return foodList;
    }
    
    /*
     * Writes food data to CSV file specified by filepath. 
     * If file doesn't already exist, it is created in the local directory.
     * 
     * @param filePath String containing path to CSV file
     * @param foods ArrayList of Food items to be saved
     */
    public void write(String filePath, ArrayList<Food> foods) {
        FileWriter fl = null;
        String csvSplitBy = ","; // split lines based on commas
        
        try {
            fl = new FileWriter(filePath + ".csv");
            
            for (Food food: foods) {
                fl.append(food.getID() + csvSplitBy);
                fl.append(food.getName() + csvSplitBy);
                fl.append("calories," + food.getCalories() + csvSplitBy);
                fl.append("fat," + food.getFat() + csvSplitBy);
                fl.append("carbohydrate," + food.getCarbs() + csvSplitBy);
                fl.append("fiber," + food.getFiber() + csvSplitBy);
                fl.append("protein," + food.getProtein() + csvSplitBy);
                fl.append("\n");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                fl.flush();
                fl.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }
        
 }
