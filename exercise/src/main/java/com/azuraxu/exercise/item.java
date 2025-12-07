package com.azuraxu.exercise;

public class item {
    private boolean itemType;
    private int calAmount;
    private String name;


    public item(String activityName, int calAmount) {
// If only constructed with two, we know it's an exercise
        itemType = true;
        this.calAmount=calAmount;
        name = activityName;

        
    }

    public boolean getType(){
        return itemType;
    }
    public int getAmount(){
        return calAmount;
    }
    public String getName(){
        return name;
    }
}
