package com.project.buildings;

public abstract class Utility extends Cell {
	private int capacity;

	public Utility(int x, int y, char mapInput) {
		super(x, y, mapInput);
		this.capacity = 100;
	}

	public int getCapacity() {return capacity;}
}
