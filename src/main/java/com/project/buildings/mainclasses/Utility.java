package com.project.buildings.mainclasses;

public abstract class Utility extends Cell {
	private int maximumCapacity;

	public Utility(int x, int y, char mapInput) {
		super(x, y, mapInput);
		this.maximumCapacity = 100;
	}

	public int getCapacity() {return maximumCapacity;}
}
