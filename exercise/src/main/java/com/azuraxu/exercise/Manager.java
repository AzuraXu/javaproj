package com.azuraxu.exercise;
import com.azuraxu.exercise.item;
import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.Reader;
import java.util.Scanner;
import java.util.ArrayList;


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

public class Manager {
    
    public static File saveData = new File("savedata.txt");
    public static FileReader saveReader;
    public static FileWriter saveWriter;

    public static Scanner userInput = new Scanner(System.in);



    public static void main(String[] args) {
        System.out.println("Hello world!");
        Gson gson = new Gson();
        exerciseItem[] rawExList = new exerciseItem[1];
        foodItem[] rawFoodList = new foodItem[1];

        //On load - database imports
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
     
        } catch (IOException e) {
            System.out.println("An error occurred while initializing databases");
            throw new RuntimeException(e);
        }
        // Test code to see if it's working
            // for (exerciseItem item : rawExList) {
            //     System.out.println(item.activity);
            //     System.out.println(item.cal_per_lb);       
            // }
            //  for (foodItem item : rawFoodList) {
            //     System.out.println(item.FoodItem);
            //     System.out.println(item.Cals_per100grams);       
            // }
        // --------- end database load
        
        try{
            saveWriter = new FileWriter("savedata.txt");    
            saveReader = new FileReader("savedata.txt");    
        }
        catch (IOException e) {
      System.out.println("An error occurred while initializing save reading / writing");
        throw new RuntimeException(e);
    }
        try {
        if(saveData.createNewFile()) {
            initialSetup(); }
        else {
            System.out.println("Welcome back!");
        }
          } catch (IOException e) {
      System.out.println("An error occurred while creating new save data");
        throw new RuntimeException(e);
    }

    }

    public static void initialSetup(){
        int weight = readInt("Enter your weight (in lbs):");
        int targetWeight = readInt("What would you like your final weight to be? (also in lbs): ");
        editWeight(weight);
        int weeks = readInt("Over how many weeks would you like to lose this weight?");
        int finalGoal = 2300 + (targetWeight - weight * 7700) / (7 * weeks);
        System.out.println("Your final calculated daily weight goal is: " + finalGoal +" calories.");
        System.out.println("You may edit this at any time.");
        editGoal(finalGoal);

    }

    public static void editGoal(int x){

    }

    public static void editWeight(int x){

    }
    public static int readInt(String prompt){
        System.out.println(prompt);
        String answer = userInput.nextLine();
        while(true){
            try {  
                return Integer.parseInt(answer);        
    }         
            catch(NumberFormatException e){  
                System.out.println("Invalid answer (not an integer). Please try again.");
                System.out.println(prompt);
                answer = userInput.nextLine();
    }          
        }
    }
    
}
