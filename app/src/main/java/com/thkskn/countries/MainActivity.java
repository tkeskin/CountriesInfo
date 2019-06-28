package com.thkskn.countries;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private AdView adView;
    private InterstitialAd interstitial;
    private static final String AD_ID = "ca-app-pub-2367630010898474/1682492364";

    ListView listview;
    ListViewAdapter adapter;
    ArrayList<HashMap<String, String>> arraylist;

    static String NAME = "name";
    static String REGION = "region";
    static String POPULATION = "population";
    static String CAPITAL = "capital";
    static String SUBREGION ="subregion";
    static String FLAG = "flag";
    static String AREA = "area";
    static String NATIVENAME = "nativeName";
    static String TOPLEVELDOMAIN = "topLevelDomain";
    static String CALLINGCODES = "callingCodes";

    // URL Address
    String feedUrl = "http://restcountries.eu/rest/v1/all";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        if(checkConnection()) {
            loadAds();
            getCitiesInfo();
            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.listview);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(MainActivity.this, arraylist);
            // Set the adapter to the ListView
            listview.setAdapter(adapter);
            startAnalytics();
        }else{
            new AlertDialog.Builder(MainActivity.this)
                    //.setIcon(android.R.drawable.ic_dialog_info)
                    //.setTitle("Hata")
                    .setMessage("Bağlantı yok,Kontrol eder misin?")
                    .setNegativeButton(R.string.action_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }

        //assign toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        //home icon shows for navigationDrawer
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //assign navigationDrawer
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp((DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

    }

    private void getCitiesInfo() {
        // Create an array
        arraylist = new ArrayList<HashMap<String, String>>();

        //RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(feedUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try{

                                JSONObject j = response.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                JSONArray jArray = j.getJSONArray("topLevelDomain");
                                JSONArray callingCodes = j.getJSONArray("callingCodes");
                                String a = jArray.getString(0).replace(".","").trim();
                                String imageLink = "http://www.geonames.org/flags/x/" + a + ".gif";

                                map.put("name",j.getString("name"));
                                map.put("region", j.getString("region"));
                                map.put("population",j.getString("population"));
                                map.put("capital",j.getString("capital"));
                                map.put("subregion",j.getString("subregion"));
                                map.put("area",j.getString("area"));
                                map.put("nativeName",j.getString("nativeName"));
                                map.put("topLevelDomain",jArray.getString(0));
                                map.put("callingCodes",callingCodes.getString(0));

                                map.put("flag", imageLink);
                                arraylist.add(map);
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }

    //load Analytics start -------------
    public void startAnalytics(){
        // Get tracker.
        Tracker t = ((AppController) getApplication()).getTracker(
                AppController.TrackerName.APP_TRACKER);
        // Enable Advertising Features.
        t.enableAdvertisingIdCollection(true);
        // Set screen name.
        t.setScreenName("Anasayfa Ulkeler");
        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());
    }
    //load Analytics end -------------

    //checkInternet connection start -------------
    boolean checkConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
        return conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected();
    }
    //checkInternet connection end -------------

    //load ad start -------------
    public void loadAds() {
        // Java code required.
        // testDevices and loadAdOnCreate attributes are
        // no longer available.
        adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("0B9B9AAF554192C06B891F7B14DD7F7F")
                .build();
        adView.loadAd(adRequest);
        // Create the interstitial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(AD_ID);
        // Begin loading your interstitial.
        interstitial.loadAd(adRequest);
        //displayInterstitial();
    }
    //load ad end -------------

    //if use ad you must ad this section start -------------
    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    // Invoke displayInterstitial() when you are ready to display an interstitial.
    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
    //if use ad you must ad this section end -------------

    //onBackPressed start -------------
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                //.setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(R.string.text_close)
                .setMessage(R.string.text_close_message)
                .setPositiveButton(R.string.text_close_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.text_close_no, null)
                .show();
        if(checkConnection())
            displayInterstitial();
    }
    //onBackPressed end -------------

    //menu start -------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        /*MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            new AlertDialog.Builder(this)
                    //.setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle(R.string.action_about)
                    .setMessage(R.string.action_about_text)
                    .setNegativeButton(R.string.action_ok, null)
                    .show();
        }
        /*if (id == R.id.navigate){
            startActivity(new Intent(this, SubActivity.class));
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void onExit(View view) {
        onBackPressed();
    }

    public void onSettings(View view) {
        startActivity(new Intent(this,SettingsActivity.class));
    }

    public void onInfo(View view) {
        startActivity(new Intent(this,SubActivity.class));
    }
    //menu end -------------

}