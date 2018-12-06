package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {
   
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

                String id = foodData[0];
                String name = foodData[1];
                int calories = Integer.parseInt(foodData[3]);
                double fat = Double.parseDouble(foodData[5]);
                int carbs = Integer.parseInt(foodData[7]);
                double fiber = Double.parseDouble(foodData[9]);
                double protein = Double.parseDouble(foodData[11]);
                
                foodList.add(new Food(id, name, calories, fat, carbs, fiber, protein));               

            }          
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }        
        return foodList;
    }
        
 }
