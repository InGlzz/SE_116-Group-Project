package com.project.reader;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Reader {
	int row;
	int col;
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
	}
}