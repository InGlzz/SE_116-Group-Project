package com.project.buildings;

public class House extends Zone {
    public House(int x, int y, char mapInput){
        super(x, y, mapInput);
    }

    @Override
    public void doTick(){
        int currentLevel = getLevel();
        //if there are electrictiy, water and internet it can level up to level 1
        if (getElectricity() > 0 && getWater() > 0 && getInternet() > 0) {

          //calculate the m value
            int m = getElectricity();
            if(getWater()<m){
                m = getWater();
            }
            if(getInternet()<m){
                m = getInternet();
            }

           if (currentLevel == 0) {//level up control
                setLevel(1);
            }
            else if (currentLevel == 1) {
                if (getHasSecurity() == true && getHasHealth() == true && getHasEducation() == true) {//if there are security, health and education it can level up to level 2
                    setLevel(2);
                }
            }
            //if house get lifestyle it can level up to level 3
            else if (currentLevel == 2) {
                if (getLifestyle() > 0) {
                    setLevel(3);
                }
            }
            if(getLevel() == 1){
                setPopulation(m);
            } else if (getLevel()==2) {
                setPopulation(2*m);
            } else if (getLevel()==3) {
                setPopulation((2*m)+getLifestyle());
            }
        } else {
            // if there are no electric health and education while there are population, the populaiton will decreasse
            if (getPopulation() > 0) {
                setPopulation(getPopulation() - 1);
            }
        }
    }
}