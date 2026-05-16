package com.project.buildings;

public class Commercial extends Zone {
    public Commercial(int x, int y, char mapInput){
        super(x, y, mapInput);
    }

    @Override
    public void doTick(){
        int currentLevel = getLevel();

        // if there are population,goods,elec.,water and internet it can level up to level 1
        if (getPopulation() > 0 && getGoods() > 10 && getElectricity() > 0 && getWater() > 0 && getInternet() > 0) {

            //in commercial, it sells goods so goods stock must be decreases
            setGoods(getGoods() - 10);
            if (currentLevel == 0) {
                setLevel(1);
            }
            // if it is on level 1 and it has level 2 it can level up to level 2
            else if (currentLevel == 1) {
                if (getHasSecurity() == true) {
                    setLevel(2);
                }
            }

            // if it is on level 2 and it has excees population(like 25) and excees goods(like 30) it can level up to level 3
            else if (currentLevel == 2) {
                if (getPopulation() > 25 && getGoods() > 30) {
                    setLevel(3);
                }
            }
            // lifestyle is proportional to level so if it level up, lifestyle is also life style is increases
            setLifestyle(getLifestyle() + (getLevel() * 5));

        } else {
            //if it can access to essential things trade is end
        }
    }
}