package com.example.enzo_g.enzo_app;

/**
 * Created by enzo_g on 17/01/18.
 */

public class Vehicles {
    private String name;
    private String cost_in_credits;

    public Vehicles(){}

    public String getName() {
        return name;
    }
    public String getCost_in_credits(){return cost_in_credits;}
    public void setName(String name){
        this.name=name;
    }
    public void setCost_in_credits(String cost_in_credits){this.cost_in_credits=cost_in_credits;}
}
