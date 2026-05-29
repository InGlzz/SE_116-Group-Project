package com.project.reader;
import com.project.buildings.*;

public class Combiner {

	public Combiner() {

	}
	//Convert the raw map gained from reader to a usable cell class.
	public Cell[][] convertBuildings(char[][] rawFile, int x, int y) {
		Cell[][] map = new Cell[y][x];

		for(int i = 0; i < y; i++) {
			for(int j = 0; j < x; j++) {
				char ht = rawFile[i][j];

				switch(ht) {
					case 'H':
						map[i][j] = new House(j,i,'H');
						break;
					case 'I':
						map[i][j] = new Industrial(j,i,'I');
						break;
					case 'C':
						map[i][j] = new Commercial(j,i,'C');
						break;
					case 'P':
						map[i][j] = new PowerPlant(j,i,'P');
						break;
					case 'W':
						map[i][j] = new WaterPumpingStation(j,i,'W');
						break;
					case 'T':
						map[i][j] = new InternetHub(j,i,'T');
						break;
					case 'F':
						map[i][j] = new PoliceStation(j,i,'F');
						break;
					case 'D':
						map[i][j] = new Hospital(j,i,'D');
						break;
					case 'S':
						map[i][j] = new School(j,i,'S');
						break;
					case 'R':
						map[i][j] = new Road(j,i,'R');
						break;
					case 'E':
						map[i][j] = new EmptyCell(j,i,'E');
						break;
					default:
						System.err.println("Wrong input detected!");
				}
			}
		}
		return map;
	}
}
