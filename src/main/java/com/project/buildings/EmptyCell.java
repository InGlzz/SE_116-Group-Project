package com.project.buildings;

public class EmptyCell extends Cell {
    public EmptyCell(int x, int y, char mapInput){
        super(x, y, mapInput);
    }
    @Override
    public void doTick(){// Must be empty because there is no internal logic needed
    }
}
