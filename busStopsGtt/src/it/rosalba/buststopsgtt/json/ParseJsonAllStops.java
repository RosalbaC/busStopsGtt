package it.rosalba.buststopsgtt.json;

import it.rosalba.busstopsgtt.data.GttStop;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ParseJsonAllStops {
	public ParseJsonAllStops() {

	}

	public ArrayList<GttStop> parserJson(String jsonString) {
		ArrayList<GttStop> stopsList = new ArrayList<GttStop>();
		if (jsonString != null) {
			try {
				JSONObject jsonObj = new JSONObject(jsonString);
				JSONArray gttStop = null;
				// Getting JSON Array node
				gttStop = jsonObj.getJSONArray("stops");
				Log.e("stops ", "stops lenght " + gttStop.length());
				// looping through All GttStop
				for (int j = 0; j < gttStop.length(); j++) {
					GttStop newGttStop = new GttStop();
					JSONObject jsonGttStop = gttStop.getJSONObject(j);
					if (jsonGttStop.has("lat")) {
						float stopLat = jsonGttStop.getLong("lat");
						newGttStop.setLatitude(stopLat);
					} else {
						newGttStop.setLatitude(0);
					}
					if (jsonGttStop.has("lines")) {
						String stopLines = jsonGttStop.getString("lines");
						newGttStop.setLines(stopLines);

					} else {
						newGttStop.setLines("");

					}
					if (jsonGttStop.has("lng")) {
						float stopLng = jsonGttStop.getLong("lng");
						newGttStop.setLongitude(stopLng);

					} else {
						newGttStop.setLongitude(0);

					}
					if (jsonGttStop.has("location")) {
						String stopLocation = jsonGttStop.getString("location");
						newGttStop.setLocation(stopLocation);

					} else {
						newGttStop.setLocation("");

					}
					if (jsonGttStop.has("name")) {
						String stopName = jsonGttStop.getString("name");
						newGttStop.setName(stopName);

					} else {
						newGttStop.setName("");

					}
					if (jsonGttStop.has("placeName")) {
						String stopPlaceName = jsonGttStop
								.getString("placeName");
						newGttStop.setPlaceName(stopPlaceName);

					} else {
						newGttStop.setPlaceName("");

					}
					if (jsonGttStop.has("id")) {
						int stopId ;
						try{
						stopId = jsonGttStop.getInt("id");
						}catch (Exception e){
							stopId = -1;
						}
						newGttStop.setId(stopId);

					} else {
						newGttStop.setId(-1);

					}
					if (jsonGttStop.has("type")) {
						String stopType = jsonGttStop.getString("type");
						newGttStop.setType(stopType);

					} else {
						newGttStop.setType("");

					}

					// add stop into the list
					stopsList.add(newGttStop);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		return stopsList;
	}

}
