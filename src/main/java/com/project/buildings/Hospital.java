package com.project.buildings;

public class Hospital extends Service{
    public Hospital(int x,int y, char mapInput){
        super(x,y,mapInput,3);
    }
    @Override
    public void doTick(){// Must be empty because there is no internal logic needed
    }
}
