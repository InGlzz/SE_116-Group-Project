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
			//step 0 reset all zones before new services are provided
			for (int j = 0; j < map.length; j++) {
				for (int k = 0; k < map[j].length; k++) {
					if (map[j][k] instanceof Zone) {
						Zone zone = (Zone) map[j][k];
						zone.resetTick();//call resetTick from zone and reset everything
					}
				}
			}
				//step 1 services are provided
				distributeService();

				//step 2 BFS system
				//not ready

				//step 3 previous tick's production is distributed
				int houseCount = 0;
				int commercialCount = 0;
				int industrialCount = 0;

				for (int j = 0; j < map.length; j++) {
					for (int k = 0; k < map[j].length; k++) {
						if (map[j][k] instanceof Zone) {
							Zone zone = (Zone) map[j][k];

							if (zone instanceof House) houseCount++;
							else if (zone instanceof Commercial) commercialCount++;
							else if (zone instanceof Industrial) industrialCount++;
						}
					}
				}
				//divided equally among industrial and commercial zones
				int totalDemandingBuildings = commercialCount + industrialCount;
				int popShare = 0;
				if (totalDemandingBuildings > 0) {
					popShare = poolPopulation / totalDemandingBuildings;
				}

				int popShareToCommercial = popShare;
				int popShareToIndustrial = popShare;


				int goodsShare = 0;
				if (commercialCount > 0) {
					goodsShare = poolGoods / commercialCount;
				}
				int lifestyleShare = 0;
				if (houseCount > 0) {
					lifestyleShare = poolLifestyle / houseCount;
				}

				// distribute to specific zone
				for (int j = 0; j < map.length; j++) {
					for (int k = 0; k < map[j].length; k++) {
						if (map[j][k] instanceof Zone) {
							Zone zone = (Zone) map[j][k];

							if (zone instanceof House) {
								zone.setLifestyle(lifestyleShare);
							}
							else if (zone instanceof Commercial) {
								zone.setPopulation(popShareToCommercial);
								zone.setGoods(goodsShare);
							}
							else if (zone instanceof Industrial) {
								zone.setPopulation(popShareToIndustrial);
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
								if (zone instanceof House) {
									poolPopulation += zone.getOutput();
								}
								else if (zone instanceof Industrial) {
									poolGoods += zone.getOutput();
								}
								else if (zone instanceof Commercial) {
									poolLifestyle += zone.getOutput();
								}
								totalZones++;
							}
						}
					}
					System.out.println("Tick " + i + " completed.Pool ==>> Population: " + poolPopulation + ", Goods: " + poolGoods + ", Lifestyle: " + poolLifestyle);

			}

		}

		public static void distributeService() {
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					if (map[i][j] instanceof Service) {
						Service service = (Service) map[i][j];
						int radius = service.getRadius();
						
						for (int k = 0; k < map.length; k++) {
							for (int l = 0; l < map[k].length; l++) {
								if (map[k][l] instanceof Zone) {
									Zone zone = (Zone) map[k][l];

									double distance = Math.sqrt(Math.pow(i - k, 2) + Math.pow(j - l, 2));
									if (distance <= radius) {
										if (service instanceof PoliceStation) {
											zone.setHasSecurity(true);
										} else if (service instanceof Hospital) {
											zone.setHasHealth(true);
										} else if (service instanceof School) {
											zone.setHasEducation(true);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}