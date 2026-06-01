package com.project.buildings;

public class Industrial extends Zone {
    public Industrial(int x, int y, char mapInput){
        super(x, y, mapInput);
    }

    @Override
    public void doTick(){
        int currentLevel = getLevel();
        int previousLevel = getLevel();

        // if there are population, electricity and water it can level up to level 1
        if (getPopulation() > 0 && getElectricity() > 0 && getWater() > 0) {

            //calculate the m value
            int m = getElectricity();
            if (getWater() < m) {
                m = getWater();
            }

            // Level up control
            if (currentLevel == 0) {
                setLevel(1);
            }

            // if it is on level 1 and it has security it can level up to level 2
            else if (currentLevel == 1) {
                if (getHasSecurity() == true) {
                    setLevel(2);
                }
            }
            else if (currentLevel == 2) {
                    setLevel(3);
                }
            // logic of setOutput
            if (getLevel() == 1) {
                setOutput(m);
            } else if (getLevel() == 2) {
                setOutput(m * 2);
            } else if (getLevel() == 3) {
                setOutput((m * 2) + getPopulation());
            }
        } else {
            // if basic needs not met zone collapses instantly
            setOutput(0);
            setLevel(0);
        }

        System.out.println("Industrial at (" + getX() + "," + getY() + ") generated " + getOutput() + " goods");

        if (getLevel() > previousLevel) {
            System.out.println("Industrial at (" + getX() + "," + getY() + ") levels up from " + previousLevel + " to " + getLevel());
        } else if (getLevel() < previousLevel) {
            System.out.println("Industrial at (" + getX() + "," + getY() + ") levels down from " + previousLevel + " to " + getLevel());
        }
    }
}