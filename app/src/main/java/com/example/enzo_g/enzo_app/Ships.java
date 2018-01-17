package com.example.enzo_g.enzo_app;

/**
 * Created by enzo_g on 17/01/18.
 */

public class Ships {
        private String name;
        private String cost_in_credits;

        public Ships(){}

        public String getName() {
            return name;
        }
        public String getCost_in_credit(){
            return cost_in_credits;
        }
        public void setName(String name){
            this.name=name;
        }
        public void setCost_in_credits(String cost_in_credits){ this.cost_in_credits=cost_in_credits;}
    }

