package com.project.buildings.zones;

import com.project.buildings.mainclasses.Zone;

public class Commercial extends Zone {
    public Commercial(int x, int y, char mapInput) {
        super(x, y, mapInput);
    }

    @Override
    public void doTick() {
        int currentLevel = getLevel();
        int targetLevel = 0;

        //basic needs
        if (getElectricity() > 0 && getWater() > 0 && getInternet() > 0) {

            //calcualte the m value ( minimum amount of utility)
            int m = getElectricity();
            if (getWater() < m) m = getWater();
            if (getInternet() < m) m = getInternet();

            //population and goods are aldo basic needs for level 1
            if (getPopulation() > 0 && getGoods() > 0) targetLevel = 1; //if it has population and good it can level up to level 1

            if (getPopulation() > 0 && getGoods() > 0 && getHasSecurity()) targetLevel = 2;//if it is on level 1 and it has security it can level up to level 2

            if (getPopulation() > 0 && getGoods() > 0 && getHasSecurity()) targetLevel = 3;



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
                int minPopulationGoods = getPopulation();
                if (getGoods() < minPopulationGoods) {
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