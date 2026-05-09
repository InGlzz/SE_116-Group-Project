package com.project.buildings;

public class Industrial extends Zone {
    public Industrial(int x, int y,char mapInput){
        super(x,y,mapInput);
    }

    @Override
        public void doTick(){
        if(getElectricity()>0&&getWater()>0){//for growth; electricity n water are essential
            if(getLevel()<3){
                setLevel(getLevel()+1);// if it is not level 3 it can level up
            }
            setGoods(getLevel()*10);//goods is increases 10 goods for each level
        }else{
            setGoods(0);//if there are no electricity n water goods generate will stop
        }
    }
}
