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
            if(getWater() < m) m = getWater();
            if(getInternet() < m) m = getInternet();

            int targetLevel = 1;
            //if there are security, health and education it can level up to level 2
            if (getHasSecurity() && getHasHealth() && getHasEducation()) {
                targetLevel = 2;
                //if house get lifestyle it can level up to level 3
                if (getLifestyle() > 0) {
                    targetLevel = 3;
                }
            }

            if (currentLevel < targetLevel) {
                setLevel(currentLevel + 1);
            } else if (currentLevel > targetLevel) {
                setLevel(currentLevel - 1);
            }
            //logic of Output

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