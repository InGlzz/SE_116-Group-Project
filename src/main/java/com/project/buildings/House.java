package com.project.buildings;

public class House extends Zone {
    public House(int x,int y, char mapInput){
        super(x,y,mapInput);
    }
    @Override
    public void doTick(){
        if(getElectricity() > 0 && getWater() > 0){ //for growth; electricity n water are essential
            if (getLevel() < 3) {
                setLevel(getLevel() + 1); // if it is not level 3 it can level up
            }
            setPopulation(getLevel()*10);//population is increases 10 people for each level
            if(getInternet()>0){
                setLifestyle(getLifestyle()+5);
            }
        }else{
            if(getPopulation()>0){
                setPopulation(getPopulation()-1);//if there are no water n electricity population might be decrease
            }
        }
    }
}
