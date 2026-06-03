package com.project;

import java.util.ArrayList;
import java.util.List;

import com.project.buildings.*;
import com.project.buildings.mainclasses.*;
import com.project.buildings.services.*;
import com.project.buildings.utility.*;
import com.project.buildings.zones.*;
import com.project.reader.Reader;
import com.project.reader.Combiner;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashSet;
import java.util.Set;

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
			System.out.println("Tick " + (i + 1));
			//step 0 reset all zones before new services are provided
			for (int j = 0; j < map.length; j++) {
				for (int k = 0; k < map[j].length; k++) {
					if (map[j][k] instanceof Zone) {
						Zone zone = (Zone) map[j][k];
						zone.resetTick();
					}
				}
			}

			//step 1 services are provided
			distributeService();

			//step 2 BFS system
			distributeUtilities();

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
						String type = zone.getClass().getSimpleName();

						if (zone instanceof House) {
							zone.setLifestyle(lifestyleShare);
							if (lifestyleShare > 0) {
								System.out.println(type + " at (" + zone.getY() + "," + zone.getX() + ") received " + lifestyleShare + " lifestyle");
							}
						} else if (zone instanceof Commercial) {
							zone.setPopulation(popShareToCommercial);
							zone.setGoods(goodsShare);
							if (popShareToCommercial > 0) {
								System.out.println(type + " at (" + zone.getY() + "," + zone.getX() + ") received " + popShareToCommercial + " population");
							}
							if (goodsShare > 0) {
								System.out.println(type + " at (" + zone.getY() + "," + zone.getX() + ") received " + goodsShare + " goods");
							}
						} else if (zone instanceof Industrial) {
							zone.setPopulation(popShareToIndustrial);
							if (popShareToIndustrial > 0) {
								System.out.println(type + " at (" + zone.getY() + "," + zone.getX() + ") received " + popShareToIndustrial + " population");
							}
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
						} else if (zone instanceof Industrial) {
							poolGoods += zone.getOutput();
						} else if (zone instanceof Commercial) {
							poolLifestyle += zone.getOutput();
						}
						totalZones++;
					}
				}
			}
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
									String type = zone.getClass().getSimpleName();

									// Security applies to all zones
									if (service instanceof PoliceStation) {
										if (!zone.getHasSecurity()) {
											zone.setHasSecurity(true);
											System.out.println(type + " at (" + zone.getY() + "," + zone.getX() + ") received security service");
										}
									}
									// Health and Education ONLY apply to Houses (Fixes the Factory/School Bug)
									else if (service instanceof Hospital) {
										if (zone instanceof House && !zone.getHasHealth()) {
											zone.setHasHealth(true);
											System.out.println(type + " at (" + zone.getY() + "," + zone.getX() + ") received health service");
										}
									} else if (service instanceof School) {
										if (zone instanceof House && !zone.getHasEducation()) {
											zone.setHasEducation(true);
											System.out.println(type + " at (" + zone.getY() + "," + zone.getX() + ") received education service");
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

	public static List<Cell> getConnectableNeighbors(Cell cell) {
		List<Cell> neighbors = new ArrayList<>();
		int x = cell.getX();
		int y = cell.getY();

		int[][] directions = {
				{0, -1}, {1, 0}, {0, 1}, {-1, 0},
				{1, -1}, {1, 1}, {-1, 1}, {-1, -1}
		};

		for (int[] dir : directions) {
			int newX = x + dir[0];
			int newY = y + dir[1];

			if (newY >= 0 && newY < map.length && newX >= 0 && newX < map[0].length) {
				Cell neighbor = map[newY][newX];
				if (!(neighbor instanceof EmptyCell)) {
					neighbors.add(neighbor);
				}
			}
		}
		return neighbors;
	}

	public static void distributeUtilities() {
		// Group providers by type
		Class<?>[] utilityTypes = {InternetHub.class, WaterPumpingStation.class, PowerPlant.class};
		String[] resourceNames = {"internet", "water", "electricity"};

		for (int u = 0; u < utilityTypes.length; u++) {
			Class<?> utilityClass = utilityTypes[u];
			String resourceName = resourceNames[u];

			// Find EACH provider of this type and execute a completely separate BFS for it
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					if (utilityClass.isInstance(map[i][j])) {
						Utility provider = (Utility) map[i][j];

						// Reset Queue and Visited for EACH specific provider
						Queue<Cell> queue = new LinkedList<>();
						Set<Cell> visited = new HashSet<>();
						int remainingCapacity = provider.getCapacity();

						queue.add(provider);
						visited.add(provider);

						// Distribute outward ensuring each zone is touched exactly once by THIS provider
						while (!queue.isEmpty() && remainingCapacity > 0) {
							Cell current = queue.poll();

							if (current instanceof Zone) {
								Zone zone = (Zone) current;
								int demand = Math.max(1, zone.getOutput());
								int unmetDemand = 0;

								if (utilityClass == PowerPlant.class) {
									unmetDemand = demand - zone.getElectricity();
								} else if (utilityClass == WaterPumpingStation.class) {
									unmetDemand = demand - zone.getWater();
								} else if (utilityClass == InternetHub.class) {
									// Strict check to keep Industrial zones from taking internet
									if (!(zone instanceof Industrial)) {
										unmetDemand = demand - zone.getInternet();
									}
								}

								if (unmetDemand > 0) {
									int provided = Math.min(unmetDemand, remainingCapacity);
									String type = zone.getClass().getSimpleName();

									if (utilityClass == PowerPlant.class) {
										zone.setElectricity(zone.getElectricity() + provided);
									} else if (utilityClass == WaterPumpingStation.class) {
										zone.setWater(zone.getWater() + provided);
									} else if (utilityClass == InternetHub.class) {
										zone.setInternet(zone.getInternet() + provided);
									}

									System.out.println(type + " at (" + zone.getY() + "," + zone.getX() + ") received " + provided + " " + resourceName);
									remainingCapacity -= provided;
								}
							}

							if (remainingCapacity <= 0) break;

							List<Cell> neighbors = getConnectableNeighbors(current);
							for (Cell neighbor : neighbors) {
								if (!visited.contains(neighbor)) {
									queue.add(neighbor);
									visited.add(neighbor); // Marks the building so it can NEVER be double-dipped by this provider
								}
							}
						}
					}
				}
			}
		}
	}
}