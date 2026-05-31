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

	public static Cell[][] startGame(String mapLocation) {
		System.out.println("Loading the map");
		Reader rd = new Reader(mapLocation);
		rd.readTheMap();

		Combiner cmb = new Combiner();
		System.out.println("Map loaded without any errors!");
		return cmb.convertBuildings(rd.getBuilding(), rd.getX(), rd.getY());
	}

	public static void runGame(int tickCount) {
		int poolPopulation = 0;
		int poolGoods = 0;
		int poolLifestyle = 0;
		int totalZones = 0;
		for (int i = 0; i < tickCount; i++) {
			System.out.println("Tick: " + i);
			//step 1 services are provided
			distributeService();

			//step 2 BFS system
			//not ready


			//step 3 previous tick's production is distributed
			if (i > 0 && totalZones > 0) {

				int populationShare = poolPopulation / totalZones;
				int goodsShare = poolGoods / totalZones;
				int lifestyleShare = poolLifestyle / totalZones;

				for (int j = 0; j < map.length; j++) {
					for (int k = 0; k < map[j].length; k++) {
						if (map[j][k] instanceof Zone) {
							Zone zone = (Zone) map[j][k];
							zone.setPopulation(populationShare);
							zone.setGoods(goodsShare);
							zone.setLifestyle(lifestyleShare);
						}
					}
				}
			}
			//step 4 zones are updated
			for (int a = 0; a < map.length; a++) {
				for (int b = 0; b < map[a].length; b++) {
					if (map[a][b] != null) {
						map[a][b].doTick();
					}
				}
			}
			//step 5 reset pools and count them for next tick
			poolPopulation = 0;
			poolLifestyle = 0;
			poolGoods = 0;
			totalZones = 0;

				for (int j = 0; j < map.length; j++) {
					for (int k = 0; k < map[j].length; k++) {
						if (map[j][k] instanceof Zone) {
							Zone zone = (Zone) map[j][k];
							poolPopulation += zone.getOutputPopulation();
							poolGoods += zone.getGoods();
							poolLifestyle += zone.getLifestyle();
							totalZones++;
						}
					}
				}
				System.out.println("Tick " + i + " completed.Pool ==>> Population: " + poolPopulation + ", Goods: " + poolGoods + ", Lifestyle: " + poolLifestyle);

		}

	}

	public static void distributeService() {
		//Write service distribution with Euclidean or Manhattan here.
	}
}