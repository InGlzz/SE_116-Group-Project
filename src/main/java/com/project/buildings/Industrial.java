package com.project.buildings;

public class Industrial extends Zone {
    public Industrial(int x, int y, char mapInput){
        super(x, y, mapInput);
    }

    @Override
    public void doTick(){
        int currentLevel = getLevel();

        // if there are population, electricity and water it can level up to level 1
        if (getPopulation() > 0 && getElectricity() > 0 && getWater() > 0) {
            if (currentLevel == 0) {
                setLevel(1);
            }

            // if it is on level 1 and it has security it can level up to level 2
            else if (currentLevel == 1) {
                if (getHasSecurity() == true) {
                    setLevel(2);
                }
            }
            //if it is on level 2 and it has excees population like 25 it can level up to level 3
            else if (currentLevel == 2) {
                if (getPopulation() > 25) {
                    setLevel(3);
                }
            }
            // goods is proportional to level so if it level up, goods are also increase
            setGoods(getLevel() * 10);

        } else {
            // if there are no population and essential things can't generate goods
            setGoods(0);
        }
    }
}