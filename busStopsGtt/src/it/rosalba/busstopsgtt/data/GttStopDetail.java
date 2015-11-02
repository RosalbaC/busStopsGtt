package it.rosalba.busstopsgtt.data;

import java.util.ArrayList;

public class GttStopDetail {
	private int idStop;
	private String longNameBus;
	private String nameBus;
	private String lineType;
	private ArrayList<Departure> departures = new ArrayList<Departure>();

	public int getIdStop() {
		return this.idStop;
	}

	public void setIdStop(int id) {
		this.idStop = id;
	}

	public String getLongNameBus() {
		return this.longNameBus;
	}

	public void setLongNameBus(String longNameBus) {
		this.longNameBus = longNameBus;
	}

	public String getNameBus() {
		return this.nameBus;
	}

	public void setNameBus(String name) {
		this.nameBus = name;
	}

	public ArrayList<Departure> getDepartures() {
		return this.departures;
	}

	public void setDepartures(ArrayList<Departure> departures) {
		this.departures = departures;
	}

	public String getLineType() {
		return this.lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}
}
