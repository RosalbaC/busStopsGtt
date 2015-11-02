package it.rosalba.busstopsgtt;

import it.rosalba.busstopsgtt.R;
import it.rosalba.busstopsgtt.data.GttStop;
import it.rosalba.busstopsgtt.data.GttStopDetail;
import it.rosalba.buststopsgtt.json.GttRequestData;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

public class MainActivity extends Activity implements
		SearchView.OnQueryTextListener {
	ArrayList<GttStop> allStops = new ArrayList<GttStop>();
	GttRequestData request = new GttRequestData();
	private AlertDialog alertInfo;
	
	private String url = "http://www.5t.torino.it/ws2.1/rest/stops/all";
	private ListView listStops;
	private TextView searchStop;
	private SearchView searchViewStop;
	private ArrayAdapter<String> adapterStops;
	private Button goToMap;

	final Context context = this;
	// private AutoCompleteTextView autoSearchStops;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.searchViewStop = (SearchView) findViewById(R.id.searchViewStop);
		// this.searchStop = (TextView) findViewById(R.id.searchStop);

		this.listStops = (ListView) findViewById(R.id.ListViewStops);
		// this.autoSearchStops = (AutoCompleteTextView)
		// findViewById(R.id.searchStop);
		
		this.goToMap = (Button) findViewById(R.id.buttonToMap);
		this.goToMap.setEnabled(true);
		this.goToMap.setOnClickListener(stopsMap);
		
		try {
			allStops = request.getAllStops(url);
			Log.d("List", "allStops size  " + allStops.size());
			setStopsList();
			
			
		} catch (IOException e) {
		
			e.printStackTrace();
		}

	}

	void setStopsList() {
		// ArrayList of all the rules inside "allRules"
		final ArrayList<String> listStops = new ArrayList<String>();
		for (int i = 0; i < allStops.size(); i++) {
			listStops.add(allStops.get(i).getId() + " "
					+ allStops.get(i).getName());
		}
		// popolo la list View con le fermate
		// creo e istruisco l'adattatore.
		this.adapterStops = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listStops);// R.layout.customize_item_select,listStops);
		// inietto i dati
		// this.autoSearchStops.setAdapter(this.adapterStops);
		// this.autoSearchStops.setHint("Search a stop");
		this.listStops.setAdapter(this.adapterStops);

		this.listStops
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapter, View view,
							int position, long id) {
						// per la selezione della riga di colore diverso
						view.setSelected(true);

						String selected = (String) adapter
								.getItemAtPosition(position);
						int idStopSelected = Integer.MAX_VALUE;

						for (int i = 0; i < allStops.size(); i++) {
							String stop = allStops.get(i).getId() + " "
									+ allStops.get(i).getName();
							if (stop.equals(selected)) {
								String[] splitInfo = stop.split(" ");
								String findId = splitInfo[0]; // contiene l'id
								int idClicked;
								try {
									idClicked = Integer.parseInt(findId);

								} catch (Exception e) {
									idClicked = -1;
								}
								String urlDepartures = "http://www.5t.torino.it/ws2.1/rest/stops/"
										+ idClicked + "/departures";
								String message;
								try {
									ArrayList<GttStopDetail> details = request
											.getDetailStop(urlDepartures,
													idClicked);
									Log.e("array details", "details " + details.size());
									message = createMessage(details);
									Log.e("message", "message " + message);
								} catch (Exception e) {
									e.printStackTrace();
									message = " Error ";
								}

								showAlert(message);
							}
						}

					}

				});

		this.listStops.setTextFilterEnabled(true);

		setupSearchView();
	}

	private String createMessage(ArrayList<GttStopDetail> details) {
		
		String messageDepartures = "";
		if(details.size() > 0){
		String busStop = "Stop number : " + details.get(0).getIdStop() + " \n";
		messageDepartures = busStop + "\nBuses : \n-->";
		String busDetail = "";
		for (int i = 0 ; i < details.size(); i++){
			busDetail = busDetail + "-->" + details.get(i).getNameBus() + " \n --" + details.get(i).getLongNameBus() + " -- \n";
			String departures = "Departures : " ;
			for (int j = 0 ; j < details.get(i).getDepartures().size(); j++){
				departures = departures + details.get(i).getDepartures().get(j).getTime() + " ";
				if (details.get(i).getDepartures().get(j).getRealTime() == true)
				{ 
					departures = departures + "real time";
				}
				
		}
			busDetail = busDetail + departures + "\n";
			
		}
		messageDepartures = messageDepartures + busDetail;
		
		
		}
		return messageDepartures;

	}

	private void showAlert(String message) {
		Log.e("showAlert", "showAlert ");
		// create dialog alert for info about departures
		AlertDialog.Builder builderInfo = new AlertDialog.Builder(context);

		builderInfo
				.setMessage(message)
				
				.setNeutralButton("ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User cancelled the dialog
								dialog.cancel();
							}
						});
		this.alertInfo = builderInfo.create();
		this.alertInfo.show();
			
	}

	
	private void setupSearchView() {
		// searchViewStop.setIconifiedByDefault(false);
		searchViewStop.setOnQueryTextListener(this);
		// searchViewStop.setSubmitButtonEnabled(true);
		searchViewStop.setQueryHint("Search Here");
	}

	public boolean onQueryTextChange(String newText) {
		if (TextUtils.isEmpty(newText)) {
			listStops.clearTextFilter();
		} else {
			listStops.setFilterText(newText.toString());
		}
		return true;
	}

	public boolean onQueryTextSubmit(String query) {
		return false;
	}
	
	
	
View.OnClickListener stopsMap = new View.OnClickListener() {
		
		public void onClick(View v)
		{
			
			int REQUEST_CODE = 1; // code for request
			// ArrayList<DeviceHome> d = new ArrayList<DeviceHome>();
			// d = ;
			Intent intentMapActivity = new Intent(MainActivity.this, MapActivity.class);
			//intentMapActivity.putExtra(n("Stops", allStops);
			startActivityForResult(intentMapActivity, REQUEST_CODE);
			
		}
	};
	
	/*
	 * private void setupSearchView() {
	 * 
	 * // Capture Text in EditText this.searchStop.addTextChangedListener(new
	 * TextWatcher() {
	 * 
	 * @Override public void afterTextChanged(Editable s) {
	 * 
	 * }
	 * 
	 * @Override public void beforeTextChanged(CharSequence s, int start, int
	 * count, int after) {
	 * 
	 * }
	 * 
	 * @Override public void onTextChanged(CharSequence s, int start, int
	 * before, int count) { // TODO Auto-generated method stub
	 * adapterStops.getFilter().filter(s.toString()); } }); }
	 */

	
	/*this.addNewRule = (Button) findViewById(R.id.buttonAddNewRule);
			this.addNewRule.setOnClickListener(newRule);
			// inizialmente bottone disabilitato
			this.addNewRule.setEnabled(false);
			// click bottone "AddNewRule"-> interfaccia rule composition
	
	View.OnClickListener newRule = new View.OnClickListener() {
		
		public void onClick(View v)
		{
			
			int REQUEST_CODE = 1; // code for request
			// ArrayList<DeviceHome> d = new ArrayList<DeviceHome>();
			// d = ;
			Intent intentNewRule = new Intent(MainActivityHomeRules.this, ActivityRuleComposition.class);
			intentNewRule.putParcelableArrayListExtra("DEVICES_HOME", devicesHomeList);
			// intentNewRule.putExtra("SUGGESTIONS_MODE", 0);
			intentNewRule.putStringArrayListExtra("NAMES_OF_RULES", namesOfRules);
			// set the request code to any code you like,you can identify the
			// callback via this code
			startActivityForResult(intentNewRule, REQUEST_CODE);
			
		}
	};
	
	*/

}
