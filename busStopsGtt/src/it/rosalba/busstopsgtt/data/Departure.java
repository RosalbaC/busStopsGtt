package it.rosalba.busstopsgtt.data;




public class Departure {
	private boolean realTime;
	private String time;
	private int arrivalTimeInt;
	
	public boolean getRealTime() {
		return this.realTime;
	}

	public void setRealTime(boolean realTime) {
		this.realTime = realTime;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public int getArrivalTimeInt() {
		return this.arrivalTimeInt;
	}

	public void setArrivalTimeInt(int arrivalTimeInt) {
		this.arrivalTimeInt = arrivalTimeInt;
	}

	

	

}
