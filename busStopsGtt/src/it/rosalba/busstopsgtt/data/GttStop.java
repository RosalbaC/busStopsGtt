package it.rosalba.busstopsgtt.data;

import java.util.ArrayList;

public class GttStop {
	private int id;
	private String location;
	private String name;
	private String placement;
	private String type;
	private ArrayList<String> lines = new ArrayList<String>();
	private double lat;
	private double lng;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlaceName() {
		return this.placement;
	}

	public void setPlaceName(String placement) {
		this.placement = placement;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<String> getLines() {
		return this.lines;
	}

	public void setArrayLines(ArrayList<String> lines) {
		this.lines = lines;
	}

	public void setLines(String data) {
		// modifica la stringa e setta l'array di stringhe
		ArrayList<String> listLines = new ArrayList<String>();
		String[] allLines = data.split(",");
		for (int i = 0; i < allLines.length; i++) {
			String line = allLines[i];
			listLines.add(line);
		}

		this.lines = listLines;
	}

	public double getLatitude() {
		return this.lat;
	}

	public void setLatitude(double lat) {
		this.lat = lat;
	}

	public double getLongitude() {
		return this.lng;
	}

	public void setLongitude(double lng) {
		this.lng = lng;
	}
}
