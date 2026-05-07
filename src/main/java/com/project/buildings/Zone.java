package com.project.buildings;

public abstract class Zone extends Cell {
	private int level = 0;
	private int electricity = 0;
	private int water = 0;
	private int internet = 0;
	private boolean hasSecurity = false;
	private boolean hasHealth = false;
	private boolean hasEducation = false;
	private int population = 0;
	private int goods = 0;
	private int lifestyle = 0;
	private int output = 0;

	public Zone(int x, int y, char mapInput) {
		super(x, y, mapInput);
	}

	public int getLevel() { return this.level; }
	public int getElectricity() { return this.electricity; }
	public int getWater() { return this.water; }
	public int getInternet() { return this.internet; }
	public boolean getHasSecurity() { return this.hasSecurity; }
	public boolean getHasHealth() { return this.hasHealth; }
	public boolean getHasEducation() { return this.hasEducation; }
	public int getPopulation() { return this.population; }
	public int getGoods() { return this.goods; }
	public int getLifestyle() { return this.lifestyle; }
	public int getOutput() { return this.output; }

	public void setLevel(int level) { if(level >= 0 && level <= 3) this.level = level; }
	public void setElectricity(int electricity) { this.electricity = electricity; }
	public void setWater(int water) { this.water = water; }
	public void setInternet(int internet) { this.internet = internet; }
	public void setHasSecurity(boolean hasSecurity) { this.hasSecurity = hasSecurity; }
	public void setHasHealth(boolean hasHealth) { this.hasHealth = hasHealth; }
	public void setHasEducation(boolean hasEducation) { this.hasEducation = hasEducation; }
	public void setPopulation(int population) { this.population = population; }
	public void setGoods(int goods) { this.goods = goods; }
	public void setLifestyle(int lifestyle) { this.lifestyle = lifestyle; }
	public void setOutput(int output) { this.output = output; }

	public void resetTick() {
		this.electricity = 0;
		this.water = 0;
		this.internet = 0;
		this.hasSecurity = false;
		this.hasHealth = false;
		this.hasEducation = false;
		this.population = 0;
		this.goods = 0;
		this.lifestyle = 0;
	}
}
