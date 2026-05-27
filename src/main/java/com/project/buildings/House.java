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
            //logic of setOutput
            if (getLevel()==1){
                setOutput(m);
            }else if (getLevel()==2){
                setOutput(m*2);
            } else if (getLevel()==3) {
                setOutput((m*2)+getLifestyle());
            }
        } else {
            // if basic needs not met zone collapses instantly
            setOutput(0);
            setLevel(0);
            }
        }
    }
