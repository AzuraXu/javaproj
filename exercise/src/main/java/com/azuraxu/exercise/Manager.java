package com.azuraxu.exercise;
import com.azuraxu.exercise.item;
import com.google.gson.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.Reader;
import java.util.Scanner;
import java.util.ArrayList;

public class Manager {
    
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Gson gson = new Gson();
        Scanner userInput = new Scanner(System.in);
        exerciseItem[] rawExList = new exerciseItem[1];
        foodItem[] rawFoodList = new foodItem[1];
        

        //On load - database imports
        // Import exercises
            try (
                InputStream reader = Manager.class.getClassLoader().getResourceAsStream("exercise_dataset_formatted.json");
                InputStream reader2 = Manager.class.getClassLoader().getResourceAsStream("food_dataset_formatted.json");
            ) {
                if (reader != null && reader2 != null) {
                    // Process the input stream (e.g., read its content)
                    byte[] buffer = new byte[reader.available()];
                    reader.read(buffer);
                    String exerciseDatabase = new String(buffer);
                    rawExList = gson.fromJson(exerciseDatabase, exerciseItem[].class);
                    byte[] buffer2 = new byte[reader2.available()];
                    reader2.read(buffer2);
                    String foodDatabase = new String(buffer2);
                    rawFoodList = gson.fromJson(foodDatabase, foodItem[].class); 


                } else {
                    System.out.println("Resource not found."); }

            // Test code to see if it's working
            // for (exerciseItem item : rawExList) {
            //     System.out.println(item.activity);
            //     System.out.println(item.cal_per_lb);       
            // }
             for (foodItem item : rawFoodList) {
                System.out.println(item.FoodItem);
                System.out.println(item.Cals_per100grams);       
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        
    }

    
}
class exerciseItem{
    public String activity;
    public double cal_per_lb;
    exerciseItem(){
        //blank constructor
    } 


}

class foodItem{
    public String FoodItem;
    public int Cals_per100grams;
    foodItem(){
        //blank constructor
    } 


}