package it.rosalba.busstopsgtt;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import it.rosalba.busstopsgtt.data.GttStop;

public class MapActivity extends Activity implements OnMapReadyCallback {
	private final String version = "1.0a";
	private Button goToList;
	private ArrayList<GttStop> allStopsGtt = new ArrayList<GttStop>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);
		this.goToList = (Button) findViewById(R.id.buttonToList);
		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync((OnMapReadyCallback) this);

		this.goToList.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intentReturnData = new Intent();
				setResult(RESULT_OK, intentReturnData);
				// Termine dell'activity, si ritorna all'activity padre
				finish();
			}
		});
	}

	public void onMapReady(GoogleMap map) {
		LatLng torino = new LatLng(45.070551, 7.685774);

		map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(torino, 15));

		map.addMarker(new MarkerOptions().title("Torino").snippet(" City services by GTT buses ").position(torino)
				.visible(true));

		this.allStopsGtt = MainActivity.getGttStop();
		for (int i = 0; i < allStopsGtt.size(); i++) {
			Double lat = (double) allStopsGtt.get(i).getLatitude();
			Double lng = (double) allStopsGtt.get(i).getLongitude();
			LatLng temp = new LatLng(lat, lng);
			String buses = "";
			for (int j = 0; j < allStopsGtt.get(i).getLines().size(); j++) {
				buses = buses + "--" + allStopsGtt.get(i).getLines().get(j);
			}
			buses = buses + "--";

			map.addMarker(new MarkerOptions()
					.title(Integer.toString(allStopsGtt.get(i).getId()) + " - " + allStopsGtt.get(i).getName())
					.snippet(buses).position(temp)
					.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
		}

	}

}
