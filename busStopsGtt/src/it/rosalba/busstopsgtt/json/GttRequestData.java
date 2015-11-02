package it.rosalba.buststopsgtt.json;

import it.rosalba.busstopsgtt.data.GttStop;
import it.rosalba.busstopsgtt.data.GttStopDetail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.os.StrictMode;
import android.util.Log;

public class GttRequestData {

	private static final String TAG = null;
	ArrayList<GttStop> stops;

	public GttRequestData() {

	}

	/*
	 * public GttRequestData(String url, int choose){ stops = new
	 * ArrayList<GttStop>(); String data; data = getData(url); /*if (choose == 0
	 * ) {
	 * 
	 * stops = getAll(data); } else if (choose == 1){ stopDetail =
	 * getStopDetail(data);
	 * 
	 * } }
	 */

	private static String getRestData(String url) throws IOException {

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		URL myUrl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();

		if (conn.getResponseCode() != 200) {
			throw new IOException(conn.getResponseMessage());
		}

		// Buffer the result into a string
		BufferedReader bufferR = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		while ((line = bufferR.readLine()) != null) {
			stringBuilder.append(line);
		}
		bufferR.close();

		conn.disconnect();
		return stringBuilder.toString();
	}

	public ArrayList<GttStop> getAllStops(String url) throws IOException {

		ArrayList<GttStop> allStopsList = new ArrayList<GttStop>();
		String data = getRestData(url);

		// parseJson
		ParseJsonAllStops parseAllStops = new ParseJsonAllStops();
		if (data != null) {
			Log.e(TAG, " json!= null ");
			Log.e(TAG, " json" + data);
			allStopsList = parseAllStops.parserJson(data);
		}
		Log.e(TAG, " allStopsList size" + allStopsList.size());
		return allStopsList;

	}

	public ArrayList<GttStopDetail> getDetailStop(String url, int idStop)
			throws IOException {

		String data = getRestData(url);
		
		ArrayList<GttStopDetail> stopDetail = new ArrayList<GttStopDetail>();

		// parseJson
		ParseJsonStopDetail parseDeparture = new ParseJsonStopDetail();
		if (data != null) {
			Log.e(TAG, " data details != null ");
			stopDetail = parseDeparture.parserJson(data, idStop);
		}

		return stopDetail;

	}

}
