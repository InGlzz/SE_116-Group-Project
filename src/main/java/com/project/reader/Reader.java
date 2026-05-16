package com.project.reader;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Reader {
	int x;
	int y;
	char[][] building;
	String fileName;
	List<String> lines;

	public Reader(String fileName) {
		this.fileName = fileName;
		lines = new ArrayList<>();
	}

	public void readTheMap() {
		try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
			String line;
			while((line = br.readLine()) != null) {
				if(!line.trim().isEmpty()) {
					lines.add(line);
				}
			}
		} catch (IOException e) {
			System.err.println("File Error! " + this.fileName);
		}

		//calculate x and y values
		this.y = lines.size();
		this.x = lines.get(0).length();
		this.building = new char[this.y][this.x];

		//set the Characters in a 2d array.
		for(int i = 0; i < this.y; y++) {
			String line = lines.get(i);
			for(int j = 0; j < this.x; x++) {
				this.building[y][x] = line.charAt(x);
			}
		}
	}

	//getter methods
	public int getX() {return this.x;}
	public int getY() {return this.y;}
	public char[][] getBuilding() {return this.building;}
}