package com.project.buildings.services;

import com.project.buildings.mainclasses.Service;

public class PoliceStation extends Service {
    public PoliceStation(int x,int y, char mapInput){
        super(x,y,mapInput,5);
    }
    @Override
    public void doTick(){// Must be empty because there is no internal logic needed
    }
}
