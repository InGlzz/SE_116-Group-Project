package com.project;

import com.project.reader.Reader;
import com.project.reader.Combiner;
import com.project.buildings.*;

public class Main {
	private static Cell[][] map;
	private static String location;
	private static int ticks;

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Wrong input. \nUsage: java -jar ObjectVilleGame.jar (Map Location) (Tick Number)");
			return;
		}

		location = args[0];
		ticks = Integer.parseInt(args[1]);

		map = startGame(location);
		runGame(ticks);
	}

	public static Cell[][] startGame(String mapLocation){
		System.out.println("Loading the map");
		Reader rd = new Reader(mapLocation);
		rd.readTheMap();

		Combiner cmb = new Combiner();
		System.out.println("Map loaded without any errors!");
		return cmb.convertBuildings(rd.getBuilding(), rd.getX(), rd.getY());
	}

	public static void runGame(int tickCount) {
		for(int i = 0; i < tickCount; i++) {
			System.out.println("Tick: " + i);

		}
	}

	public static void distributeService() {
		//Write service distribution with Euclidean or Manhattan here.
	}
}
