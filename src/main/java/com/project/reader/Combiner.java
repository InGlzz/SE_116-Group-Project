package com.project.reader;
import com.project.buildings.*;

public class Combiner {

	public Combiner() {

	}
	//Convert the raw map gained from reader to a usable cell class.
	public Cell[][] convertHouses(char[][] rawFile, int x, int y) {
		Cell[][] map = new Cell[y][x];

		for(int i = 0; i < y; i++) {
			for(int j = 0; j < x; j++) {
				char ht = rawFile[i][j];

				switch(ht) {
				//Continue after buildings are finished
				}
			}
		}
		return map;
	}
}
