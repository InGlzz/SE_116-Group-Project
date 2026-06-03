package com.project.buildings.services;

import com.project.buildings.mainclasses.Service;

public class School extends Service {
    public School(int x,int y, char mapInput){
        super(x,y,mapInput,4);
    }
    @Override
    public void doTick(){// Must be empty because there is no internal logic needed
    }
}
