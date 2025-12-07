package com.azuraxu.exercise;
import com.azuraxu.exercise.item;
import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.util.Scanner;
import java.util.List;
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
    public static ArrayList<item> itemList = new ArrayList<item>();

    public static Scanner userInput = new Scanner(System.in);
    public static int userWeight;



    public static void main(String[] args) {
        System.out.println("Hello world!");
        Gson gson = new Gson();
        exerciseItem[] rawExList = new exerciseItem[1];
        foodItem[] rawFoodList = new foodItem[1];
        boolean created;

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
            created = saveData.createNewFile();
            saveWriter = new FileWriter("savedata.txt", true);    
            saveReader = new FileReader("savedata.txt");    
        }
        catch (IOException e) {
      System.out.println("An error occurred while initializing save file reading / writing");
        throw new RuntimeException(e);
    }
        if(created){
            initialSetup(); }
        else {
            try{readFileToArray();}
            catch (IOException e) {
      System.out.println("An error occurred while reading save data");
        throw new RuntimeException(e);
    }
            System.out.println("Welcome back!");
        }



    while (true){

        System.out.println("Welcome! Choose what to do next from the following options:\n");
        System.out.println("1: Edit starting data.");
        System.out.println("2: Add new item");
        System.out.println("3: Edit or delete existing item");
        System.out.println("4: See current goals");

        switch(readInt("Enter an integer to proceed.")) {
            case 1:
                try {
                    int newWeight = readInt("Enter your new weight (in lbs):");
                editWeight(newWeight);
                int newGoal = readInt("Enter your new daily calorie goal:");
                editGoal(newGoal);
                    
                } catch (Exception e) {
                  System.out.println("An error occurred while editing starting data");
                throw new RuntimeException(e);
                }
                break;
            case 2:
                System.out.println("What type of item would you like to add?");
                System.out.println("1: Custom item");
                System.out.println("2: Food item from database");
                System.out.println("3: Exercise item from database");
                switch(readInt("Enter an integer to proceed.")){
                    case 1:
                        boolean type = true;
                        while(true){
                        System.out.println("What type of item is this? (Enter 'true' for exercise, 'false' for food):");
                        String answer = userInput.nextLine();
                        try {  
                         type = Boolean.parseBoolean(answer); 
                         break;       
                            }         
                            catch(Exception e){  
                        System.out.println("Invalid answer (not an integer). Please try again.");
                                continue;
                        }          
                        }
                        System.out.println("Enter the name of your custom item:");
                        String itemName = userInput.nextLine();
                        int itemCals = readInt("Enter the calorie amount of your custom item \n (Enter it as a negative number for exercise):");
                        item newItem = new item(itemName, itemCals, type);
                        itemList.add(newItem);
                        System.out.println("Custom item added successfully.");
                        break;
                    case 2:
                        System.out.println("Enter part of the name of the food item you wish to add:");
                        String foodSearch = userInput.nextLine().toLowerCase();
                        ArrayList<Integer> foodMatches = new ArrayList<Integer>();
                        int displayIndex = 0;
                        for (int i=0; i < rawFoodList.length; i++){
                            if (rawFoodList[i].FoodItem.toLowerCase().contains(foodSearch)){
                                foodMatches.add(i);
                                System.out.println(String.valueOf(displayIndex)+": " + rawFoodList[i].FoodItem + " - " + String.valueOf(rawFoodList[i].Cals_per100grams) + " calories per 100 grams.");
                                displayIndex += 1;
                            }
                        }
                        if (foodMatches.size() == 0){
                            System.out.println("No matches found. Returning to main menu.");
                            break;
                        }
                        int foodChoice = readInt("Enter the number cooresponding to the food item you wish to add:");
                        if (foodChoice < 0 || foodChoice >= foodMatches.size()){
                            System.out.println("Invalid choice. Returning to main menu.");
                        }
                        else {
                            int selectedIndex = foodMatches.get(foodChoice);
                            int foodAmount = readInt("Enter the amount of this food item you consumed (in grams):");
                            int calorieCount = Math.round(((rawFoodList[selectedIndex].Cals_per100grams * foodAmount) / 100));
                            item selectedFood = new item(rawFoodList[selectedIndex].FoodItem, calorieCount, true);
                            itemList.add(selectedFood);
                            System.out.println("Food item added successfully.");
                        }
                        break;
                    case 3:
                        System.out.println("Enter part of the name of the exercise item you wish to add:");
                        String exerciseSearch = userInput.nextLine().toLowerCase();
                        ArrayList<Integer> exerciseMatches = new ArrayList<Integer>();
                                int displayIndex2 = 0;

                        for (int i=0; i < rawExList.length; i++){
                            if (rawExList[i].activity.toLowerCase().contains(exerciseSearch)){
                                exerciseMatches.add(i);
                                System.out.println(String.valueOf(displayIndex2)+": " + rawExList[i].activity + " - " + String.valueOf((rawExList[i].cal_per_lb)*userWeight) + " calories burned per hour of exercise.");
                                displayIndex2 += 1;
                            }
                        }
                        if (exerciseMatches.size() == 0){
                            System.out.println("No matches found. Returning to main menu.");
                            break;
                        }
                        int exerciseChoice = readInt("Enter the number cooresponding to the exercise item you wish to add:");
                        if (exerciseChoice < 0 || exerciseChoice >= exerciseMatches.size()){
                            System.out.println("Invalid choice. Returning to main menu.");
                        }
                        else {
                            int selectedIndex = exerciseMatches.get(exerciseChoice);
                            int exerciseDuration = readInt("Enter the duration of this exercise (in minutes):");
                            int calsLost = Math.round((float)(-1 * rawExList[selectedIndex].cal_per_lb *userWeight * (exerciseDuration / 60)));
                            item selectedexercise = new item(rawExList[selectedIndex].activity, calsLost, true);
                            itemList.add(selectedexercise);
                            System.out.println("exercise item added successfully.");
                        }
                        break;
                    
                    default:
                        System.out.println("Invalid input (outside of range). Enter only the number that cooresponds to one listed above. Returning to main menu.");
                        break;
                }

                break;
            case 3:
                if (itemList.size() == 0){
                    System.out.println("You have no existing items to edit. Returning to main menu.");
                    break;
                }
                System.out.println("Here are your existing items:");
                for (int i=0; i < itemList.size(); i++){
                    System.out.println(String.valueOf(i)+": " + itemList.get(i).getName());
                }
                int choice = readInt("Enter the number cooresponding to the item you wish to edit:");
                if (choice < 0 || choice >= itemList.size()){
                    System.out.println("Invalid choice. Returning to main menu.");
                }
                else {
                    item selectedItem = itemList.get(choice);
                    System.out.println("You have selected: " + selectedItem.getName());
                    switch(readInt("What would you like to do with this item?\n1: Delete item\n2: Edit item details")){
                        case 1:
                            deleteItem(choice);
                            System.out.println("Item deleted successfully.");
                            break;
                        case 2:
                    System.out.println("Current name: " + selectedItem.getName());
                    String newName = userInput.nextLine();
                    System.out.println("Current amount: " + selectedItem.getAmount());
                    int newAmount = readInt("Enter new amount:");
                    editItem(choice, newName, newAmount);
                    System.out.println("Item updated successfully.");
                    break;
                    default:
                    System.out.println("Invalid input (outside of range). Returning to main menu.");
                    break;
                }
            }
                

                break;
            case 4:
                        try {
                            String[] currentData = readFileToArray();
                            System.out.println("Your current daily calorie goal is: " + currentData[0] + " calories.");
                            int totalCalories = 0;
                            for (item it : itemList){
                                totalCalories += it.getAmount();
                            }
                            System.out.println("Your current calorie intake today is: " + totalCalories + " calories.");
                            int calDiff = Integer.parseInt(currentData[0]) - totalCalories;
                            if (calDiff >= 0){
                                System.out.println("That's " + calDiff + " calories below your goal for the day.");
                            }
                            else {
                                System.out.println("That's " + (-1 * calDiff) + " calories above your goal for the day.");
                            }   
                            System.out.println("Your current weight is: " + userWeight + " lbs.");
                        } catch (IOException e) {
                            System.out.println("An error occurred while reading save data");
                            throw new RuntimeException(e);}
                        break;
            default:
            System.out.println("Invalid input (outside of range). Enter only the number that cooresponds to one listed above. Try again:");


        }


    }

    }

    public static void initialSetup(){
        try {
            // write initial placeholder and close immediately so reads see the content
            try (FileWriter initial = new FileWriter("savedata.txt")) {
                initial.write("200\n2000"); // placeholder values
            }
            int weight = readInt("Enter your weight (in lbs):");
            int targetWeight = readInt("What would you like your final weight to be? (also in lbs): ");
            editWeight(weight);
            int weeks = readInt("Over how many weeks would you like to lose this weight?");
            int finalGoal = 2300 + (((targetWeight - weight) * 3500) / (7 * weeks));
            System.out.println("Your final calculated daily weight goal is: " + finalGoal +" calories.");
            System.out.println("You may edit this at any time.");
            editGoal(finalGoal);
        }
        catch (IOException e) {
      System.out.println("An error occurred during initial setup");
        throw new RuntimeException(e);
    }

    }

    public static void editGoal(int x) throws IOException {
        String[] current = readFileToArray();
        current[0] = String.valueOf(x);
        try (FileWriter overwriter = new FileWriter("savedata.txt")) {
            overwriter.write(current[0] + "\n" + current[1]);
        }
    }

    public static void editWeight(int x) throws IOException {
        String[] current = readFileToArray();
        current[1] = String.valueOf(x);
        userWeight = x;
        try (FileWriter overwriter = new FileWriter("savedata.txt")) {
            overwriter.write(current[0] + "\n" + current[1]);
        }
    }
    public static void deleteItem(int index){
        itemList.remove(index);
    }
    public static void editItem(int index, String newName, int newAmount){
        item selectedItem = itemList.get(index);
        selectedItem.setName(newName);
        selectedItem.setAmount(newAmount);
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
    public static String[] readFileToArray() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("savedata.txt"));
    userWeight = Integer.parseInt(lines.get(1));
    return lines.toArray(new String[0]);
}

}
