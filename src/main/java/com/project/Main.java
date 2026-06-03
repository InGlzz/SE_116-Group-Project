package com.project;

import java.util.ArrayList;
import java.util.List;
import com.project.reader.Reader;
import com.project.reader.Combiner;
import com.project.buildings.*;
import com.project.buildings.zones.*;
import com.project.buildings.utility.*;
import com.project.buildings.services.*;
import com.project.buildings.mainclasses.*;
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
		Reader rd = new Reader(mapLocation);
		rd.readTheMap();

		Combiner cmb = new Combiner();
		return cmb.convertBuildings(rd.getBuilding(), rd.getX(), rd.getY());
	}

	public static void runGame(int tickCount) {
		int poolPopulation = 0;
		int poolGoods = 0;
		int poolLifestyle = 0;

		for (int i = 0; i < tickCount; i++) {
			System.out.println("Tick " + (i + 1));

			// Step 0: Reset Transient Utilities
			for (int j = 0; j < map.length; j++) {
				for (int k = 0; k < map[j].length; k++) {
					if (map[j][k] instanceof Zone) {
						Zone zone = (Zone) map[j][k];
						zone.resetTick();
					}
				}
			}

			// Step 1 & 2: Distribute
			distributeService();
			distributeUtilities();

			// Step 3: Count Consumers
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

			// Step 3: Calculate Safe Shares
			int totalDemandingBuildings = commercialCount + industrialCount;
			int popShare = (totalDemandingBuildings > 0) ? (poolPopulation / totalDemandingBuildings) : 0;
			int goodsShare = (commercialCount > 0) ? (poolGoods / commercialCount) : 0;
			int lifestyleShare = (houseCount > 0) ? (poolLifestyle / houseCount) : 0;

			// Step 3: Distribute to Specific Zones
			for (int j = 0; j < map.length; j++) {
				for (int k = 0; k < map[j].length; k++) {
					if (map[j][k] instanceof Zone) {
						Zone zone = (Zone) map[j][k];

						if (zone instanceof House) {
							zone.setLifestyle(lifestyleShare);
						}
						else if (zone instanceof Commercial) {
							zone.setPopulation(popShare);
							zone.setGoods(goodsShare);
						}
						else if (zone instanceof Industrial) {
							zone.setPopulation(popShare);
						}
					}
				}
			}

			// Step 4: Tick Updates
			for (int a = 0; a < map.length; a++) {
				for (int b = 0; b < map[a].length; b++) {
					if (map[a][b] != null) {
						map[a][b].doTick();
					}
				}
			}

			// Step 5: Harvest Outputs for Next Tick
			poolPopulation = 0;
			poolLifestyle = 0;
			poolGoods = 0;

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

									if (service instanceof PoliceStation) {
										zone.setHasSecurity(true);
										System.out.println(type + " at (" + zone.getY() + "," + zone.getX() + ") received security service");
									} else if (service instanceof Hospital) {
										if (zone instanceof House) {
											zone.setHasHealth(true);
											System.out.println(type + " at (" + zone.getY() + "," + zone.getX() + ") received health service");
										}
									} else if (service instanceof School) {
										// Education ONLY goes to Houses
										if (zone instanceof House) {
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

		// The Teacher's BFS Order: Orthogonal (Cross) First, then Diagonals
		int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}, {-1, -1}, {1, -1}, {-1, 1}, {1, 1}};

		for (int[] dir : directions) {
			int newX = x + dir[0];
			int newY = y + dir[1];

			// Bounds check
			if (newY >= 0 && newY < map.length && newX >= 0 && newX < map[0].length) {
				Cell neighbor = map[newY][newX];

				// Reverting to your exact original rule: ONLY Empty Cells block propagation
				if (neighbor != null && !(neighbor instanceof EmptyCell)) {
					neighbors.add(neighbor);
				}
			}
		}
		return neighbors;
	}

	public static void distributeUtilities() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] instanceof Utility) {
					Utility provider = (Utility) map[i][j];
					Queue<Cell> queue = new LinkedList<>();
					Set<Cell> visited = new HashSet<>();

					queue.add(provider);
					visited.add(provider);

					int remainingCapacity = provider.getCapacity();

					while (!queue.isEmpty() && remainingCapacity > 0) {
						Cell current = queue.poll();

						if (current instanceof Zone) {
							Zone zone = (Zone) current;
							int demand = Math.max(1, zone.getOutput());
							int unmetDemand = 0;

							// Prevent Double Dipping & Filter Internet from Industrial
							if (provider instanceof PowerPlant) unmetDemand = demand - zone.getElectricity();
							else if (provider instanceof WaterPumpingStation) unmetDemand = demand - zone.getWater();
							else if (provider instanceof InternetHub) {
								if (!(zone instanceof Industrial)) { // Industrial doesn't use Internet
									unmetDemand = demand - zone.getInternet();
								}
							}

							if (unmetDemand > 0) {
								int provided = Math.min(unmetDemand, remainingCapacity);
								String type = zone.getClass().getSimpleName();

								if (provider instanceof PowerPlant) {
									zone.setElectricity(zone.getElectricity() + provided);
									System.out.println(type + " at (" + zone.getY() + "," + zone.getX() + ") received " + provided + " electricity");
								} else if (provider instanceof WaterPumpingStation) {
									zone.setWater(zone.getWater() + provided);
									System.out.println(type + " at (" + zone.getY() + "," + zone.getX() + ") received " + provided + " water");
								} else if (provider instanceof InternetHub) {
									zone.setInternet(zone.getInternet() + provided);
									System.out.println(type + " at (" + zone.getY() + "," + zone.getX() + ") received " + provided + " internet");
								}
								remainingCapacity -= provided;
							}
						}

						if (remainingCapacity <= 0) break;

						// EVERYTHING passes utility down the line except EmptyCells (handled in neighbor check)
						List<Cell> neighbors = getConnectableNeighbors(current);
						for (Cell neighbor : neighbors) {
							if (!visited.contains(neighbor)) {
								queue.add(neighbor);
								visited.add(neighbor);
							}
						}
					}
				}
			}
		}
	}
}