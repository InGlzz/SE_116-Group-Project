package com.project.buildings.utility;

import com.project.buildings.mainclasses.Utility;

public class InternetHub extends Utility {
    public InternetHub(int x, int y, char mapInput){
        super(x, y, mapInput);//location + mapInput(T)
    }
    @Override
    public void doTick(){// Must be empty because there is no internal logic needed
    }
}
