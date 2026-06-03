package com.project.buildings;

public class Commercial extends Zone {
    public Commercial(int x, int y, char mapInput){
        super(x, y, mapInput);
    }

    @Override
    public void doTick(){
        int currentLevel = getLevel();

        // if there are population,goods,elec.,water and internet it can level up to level 1
        if (getElectricity() > 0 && getWater() > 0 && getInternet() > 0) {
            //calculate the value of m
            int m = getElectricity();
            if (getWater() < m) m = getWater();
            if (getInternet() < m) m = getInternet();

            int targetLevel = 1;

            //if it is on level 1 and has security it can level up to level 2
            if (getHasSecurity()) {
                targetLevel = 2;
                // if it is on level 2 and has population and goods it can level up to level 3
                if (getPopulation() > 0 && getGoods() > 0) {
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
                int minPopulationGoods = getPopulation();
                if(getGoods() < minPopulationGoods){
                    minPopulationGoods = getGoods();
                }
                setOutput((m * 2) + minPopulationGoods);
            } else {
                setOutput(0);
            }
        } else {
            setOutput(0);
            setLevel(0);
        }

        System.out.println("Commercial at (" + getY() + "," + getX() + ") generated " + getOutput() + " lifestyle");
        if (getLevel() > currentLevel) {
            System.out.println("Commercial at (" + getY() + "," + getX() + ") levels up from " + currentLevel + " to " + getLevel());
        } else if (getLevel() < currentLevel) {
            System.out.println("Commercial at (" + getY() + "," + getX() + ") levels down from " + currentLevel + " to " + getLevel());
        }
    }
}