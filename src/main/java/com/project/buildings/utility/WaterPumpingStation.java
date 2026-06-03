package com.project.buildings.utility;

import com.project.buildings.mainclasses.Utility;

public class WaterPumpingStation extends Utility {
    public WaterPumpingStation(int x, int y, char mapInput){
        super(x, y, mapInput);// location + mapInput(W)
    }
    @Override
    public void doTick(){// Must be empty because there is no internal logic needed
    }
}
