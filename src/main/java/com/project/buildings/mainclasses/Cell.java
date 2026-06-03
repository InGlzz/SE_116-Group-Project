package com.project.buildings.mainclasses;

public abstract class Cell {
	//x and y grid, map input is going to be the map files contents(H, E...)
	private int x, y;
	private char mapInput;

	public Cell(int x, int y, char mapInput) {
		this.x = x;
		this.y = y;
		this.mapInput = mapInput;
	}

	public int getX() {return this.x;}
	public int getY() {return this.y;}
	public char getMapInput() {return mapInput;}

	//Write what you will do in a tick.
	public abstract void doTick();
}