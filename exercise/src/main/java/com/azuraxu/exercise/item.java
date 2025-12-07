package com.azuraxu.exercise;

public class item {
    private boolean itemType;
    private int calAmount;
    private String name;


    public item(String activityName, int calAmount, boolean itemType){ 
        this.itemType = itemType;
        this.calAmount=calAmount;
        name = activityName;

        
    }

    public boolean getType(){
        // true for food, false for exercise
        return itemType;
    }
    public int getAmount(){
        return calAmount;
    }
    public String getName(){
        return name;
    }
    public void setAmount(int newAmount){
        calAmount = newAmount;
    }
    public void setName(String newName){
        name = newName;
    }
}
