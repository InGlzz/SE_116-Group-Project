package com.project.buildings;
public class Commercial extends Zone {
    public Commercial(int x, int y, char mapInput){
        super(x,y,mapInput);
    }
    @Override
    public void doTick(){
        if(getElectricity() > 0 && getGoods() > 10){
            setGoods(getGoods()-10);//it sells goods that's why it decreases
            if(getLevel()<3){
                setLevel(getLevel()+1);//if there are goods n electricity; it can level up
            }
            setLifestyle(getLifestyle() + (getLevel() * 5));//As it make sales it bring a new lifestyle to the city
        }else{
        }
    }
}
