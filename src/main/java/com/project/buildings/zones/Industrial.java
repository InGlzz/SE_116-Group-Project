package com.project.buildings.zones;

import com.project.buildings.mainclasses.Zone;

public class Industrial extends Zone {
    public Industrial(int x, int y, char mapInput){
        super(x, y, mapInput);
    }

    @Override
    public void doTick(){
        int currentLevel = getLevel();

        //basic needs
        if (getElectricity() > 0 && getWater() > 0) {

            //calculate the m value ( minimum amount of utility)
            int m = getElectricity();
            if (getWater() < m) {
                m = getWater();
            }
            int targetLevel = 0;

            //if it has basic needs + population
            if (getPopulation() > 0) {
                targetLevel = 1;
            }
            //if it is on level 2 and it has security
            if (getPopulation() > 0 && getHasSecurity()) {
                targetLevel = 2;
                if (getPopulation() > 0) {//excess population
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
                setOutput((m * 2) + getPopulation());
            } else {
                setOutput(0);
            }
        } else {
            setOutput(0);
            setLevel(0);
        }
        System.out.println("Industrial at (" + getY() + "," + getX() + ") generated " + getOutput() + " goods");
        if (getLevel() > currentLevel) {
            System.out.println("Industrial at (" + getY() + "," + getX() + ") levels up from " + currentLevel + " to " + getLevel());
        } else if (getLevel() < currentLevel) {
            System.out.println("Industrial at (" + getY() + "," + getX() + ") levels down from " + currentLevel + " to " + getLevel());
        }
    }
}