package com.project.buildings.mainclasses;

public abstract class Service extends Cell {
	private int radius;

	public Service(int x, int y, char mapInput,int radius) {
		super(x, y, mapInput);
		this.radius = radius;
	}

	public int getRadius() {return radius;}
}
