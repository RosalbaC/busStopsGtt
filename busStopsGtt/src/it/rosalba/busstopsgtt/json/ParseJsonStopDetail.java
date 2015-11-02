package it.rosalba.busstopsgtt.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import it.rosalba.busstopsgtt.data.Departure;
import it.rosalba.busstopsgtt.data.GttStopDetail;

public class ParseJsonStopDetail {

	public ParseJsonStopDetail() {

	}

	public ArrayList<GttStopDetail> parserJson(String jsonString, int idStop) {
		ArrayList<GttStopDetail> stopDetailList = new ArrayList<GttStopDetail>();

		if (jsonString != null) {
			try {
				JSONArray gttStop = new JSONArray(jsonString);// null;
				Log.e("object json ", "details object json " + gttStop.toString());
				Log.e("details ", "details lenght " + gttStop.length());
				// looping through All GttStop
				for (int j = 0; j < gttStop.length(); j++) {
					GttStopDetail stopDetail = new GttStopDetail();
					JSONObject jsonGttStop = gttStop.getJSONObject(j);

					if (jsonGttStop.has("longName")) {
						String longNameBus = jsonGttStop.getString("longName");
						stopDetail.setLongNameBus(longNameBus);

					} else {
						stopDetail.setLongNameBus("");

					}
					if (jsonGttStop.has("name")) {
						String nameBus = jsonGttStop.getString("name");
						stopDetail.setNameBus(nameBus);

					} else {
						stopDetail.setNameBus("");

					}

					if (jsonGttStop.has("lineType")) {
						String lineType = jsonGttStop.getString("lineType");
						stopDetail.setLineType(lineType);

					} else {
						stopDetail.setLineType("");

					}

					if (jsonGttStop.has("departures")) {
						// array di departures
						ArrayList<Departure> departuresList = new ArrayList<Departure>();
						JSONArray gttDepartures = null;
						// Getting JSON Array node
						gttDepartures = jsonGttStop.getJSONArray("departures");
						for (int z = 0; z < gttDepartures.length(); z++) {
							Departure departure = new Departure();
							JSONObject jsonObjDeparture = gttDepartures.getJSONObject(z);
							if (jsonObjDeparture.has("arrivalTimeInt")) {
								int arrivalTimeInt = jsonObjDeparture.getInt("arrivalTimeInt");
								departure.setArrivalTimeInt(arrivalTimeInt);

							} else {
								departure.setArrivalTimeInt(0);

							}

							if (jsonObjDeparture.has("time")) {
								String timeString = jsonObjDeparture.getString("time");
								departure.setTime(timeString);

							} else {
								departure.setTime("");

							}
							if (jsonObjDeparture.has("rt")) {
								Boolean realTime = jsonObjDeparture.getBoolean("rt");
								departure.setRealTime(realTime);

							} else {
								departure.setRealTime(false);

							}
							departuresList.add(departure);
						}
						stopDetail.setDepartures(departuresList);
					} else {
						ArrayList<Departure> departuresList = new ArrayList<Departure>();
						stopDetail.setDepartures(departuresList);
					}

					// add stop into the list
					stopDetail.setIdStop(idStop);
					stopDetailList.add(stopDetail);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		return stopDetailList;
	}

}
