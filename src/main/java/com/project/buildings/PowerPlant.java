package com.project.buildings;

public class PowerPlant extends Utility{
    public PowerPlant(int x, int y, char mapInput){
        super(x, y, mapInput);// location + mapInput(P)
    }
    @Override
    public void doTick(){// Must be empty because there is no internal logic needed
    }
}
