package com.project.buildings.zones;

import com.project.buildings.mainclasses.Zone;

public class House extends Zone {
    public House(int x, int y, char mapInput){
        super(x, y, mapInput);
    }

    @Override
    public void doTick(){
        int currentLevel = getLevel();

        //basic needs
        if (getElectricity() > 0 && getWater() > 0 && getInternet() > 0) {

            //calculate the m value ( minimum amount of utility)
            int m = getElectricity();
            if(getWater() < m) m = getWater();
            if(getInternet() < m) m = getInternet();

            int targetLevel = 0;

            targetLevel = 1;

            //if there are security, health and education it can level up to level 2
            if (getHasSecurity() && getHasHealth() && getHasEducation()) {
                targetLevel = 2;
                if (getLifestyle() > 0) {
                    targetLevel = 3;
                }
            }

            if (currentLevel < targetLevel) {
                setLevel(currentLevel + 1);
            } else if (currentLevel > targetLevel) {
                setLevel(currentLevel - 1);
            }

            //output logic
            if (getLevel() == 1) {
                setOutput(m);
            } else if (getLevel() == 2) {
                setOutput(m * 2);
            } else if (getLevel() == 3) {
                setOutput((m * 2) + getLifestyle());
            } else {
                setOutput(0);
            }
        } else {
            setOutput(0);
            setLevel(0);
        }
        System.out.println("House at (" + getY() + "," + getX() + ") generated " + getOutput() + " population");
        if (getLevel() > currentLevel) {
            System.out.println("House at (" + getY() + "," + getX() + ") levels up from " + currentLevel + " to " + getLevel());
        } else if (getLevel() < currentLevel) {
            System.out.println("House at (" + getY() + "," + getX() + ") levels down from " + currentLevel + " to " + getLevel());
        }
    }
}