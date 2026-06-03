package com.project.buildings;

public class Industrial extends Zone {
    public Industrial(int x, int y, char mapInput){
        super(x, y, mapInput);
    }

    @Override
    public void doTick(){
        int currentLevel = getLevel();

        // if there are population, electricity and water it can level up to level 1
        if (getElectricity() > 0 && getWater() > 0) {

            //calculate the m value
            int m = getElectricity();
            if (getWater() < m) {
                m = getWater();
            }
            int targetLevel = 1;


            // if it is on level 1 and it has security it can level up to level 2
            if (getHasSecurity()) {
                targetLevel = 2;
                //if it is also have population more than 0 it can level up to level 3
                if (getPopulation() > 0) {
                    targetLevel = 3;
                }
            }

            if (currentLevel < targetLevel) {
                setLevel(currentLevel + 1);
            } else if (currentLevel > targetLevel) {
                setLevel(currentLevel - 1);
            }

            //Output logic
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