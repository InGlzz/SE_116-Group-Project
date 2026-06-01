package com.project.buildings;

public class Commercial extends Zone {
    public Commercial(int x, int y, char mapInput){
        super(x, y, mapInput);
    }

    @Override
    public void doTick(){
        int currentLevel = getLevel();
        int previousLevel = getLevel();

        // if there are population,goods,elec.,water and internet it can level up to level 1
        if (getPopulation() > 0 && getGoods() > 0 && getElectricity() > 0 && getWater() > 0 && getInternet() > 0) {


            //calculate the value of m
            int m = getElectricity();
            if (getWater() < m) {
                m = getWater();
            }
            if (getInternet() < m) {
                m = getInternet();
            }
            // level up check
            if (currentLevel == 0) {
                setLevel(1);
            }
            // if it is on level 1 and it has level 2 it can level up to level 2
            else if (currentLevel == 1) {
                if (getHasSecurity() == true) {
                    setLevel(2);
                }
            }
            else if (currentLevel == 2) {
                    setLevel(3);
            }
            if(getLevel()==1){
                setOutput(m);
            } else if (getLevel()==2) {
                setOutput(m*2);
            } else if (getLevel()==3) {
                int minPopulationGoods = getPopulation();
                if(getGoods()<minPopulationGoods){
                    minPopulationGoods=getGoods();
                }
                setOutput((m*2)+minPopulationGoods);
            }
        } else {
            //if it can access to essential things commerce is end
            setOutput(0);
            setLevel(0);
        }
        
        System.out.println("Commercial at (" + getX() + "," + getY() + ") generated " + getOutput() + " lifestyle");

        if (getLevel() > previousLevel) {
            System.out.println("Commercial at (" + getX() + "," + getY() + ") levels up from " + previousLevel + " to " + getLevel());
        } else if (getLevel() < previousLevel) {
            System.out.println("Commercial at (" + getX() + "," + getY() + ") levels down from " + previousLevel + " to " + getLevel());
        }
    }
}