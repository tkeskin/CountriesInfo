package com.thkskn.countries;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class SingleItemView extends AppCompatActivity {
	// Declare Variables
	String name;
	String region;
	String population;
	String capital;
	String area;
	String nativeName;
	String subregion;
    String topLevelDomain;
    String callingCodes;

	String flag;
	String position;
	//ImageLoader imageLoader = new ImageLoader(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from single_item_viewew.xml
		setContentView(R.layout.single_item_view);

		Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Intent i = getIntent();
		// Get the result of name
		name = i.getStringExtra("name");
		// Get the result of region
		//region = i.getStringExtra("region");
		// Get the result of population
		population = i.getStringExtra("population");
		capital = i.getStringExtra("capital");
		area = i.getStringExtra("area");
		nativeName = i.getStringExtra("nativeName");
		//subregion = i.getStringExtra("subregion");
        topLevelDomain = i.getStringExtra("topLevelDomain");
        callingCodes = i.getStringExtra("callingCodes");
		// Get the result of flag
		//flag = i.getStringExtra("flag");

		// Locate the TextViews in single_item_viewew.xml
		TextView name = (TextView) findViewById(R.id.name);
		TextView nativeName = (TextView) findViewById(R.id.native_name);
		TextView population = (TextView) findViewById(R.id.population);
		TextView capital = (TextView) findViewById(R.id.capital);
		TextView area = (TextView) findViewById(R.id.area);
        TextView topLevelDomain = (TextView) findViewById(R.id.domain);
        TextView callingCodes = (TextView) findViewById(R.id.calling);

		// Locate the ImageView in single_item_viewew.xml
		//ImageView imgflag = (ImageView) findViewById(R.id.flag);

		// Set results to the TextViews
		name.setText(this.name);
		nativeName.setText(this.nativeName);
		population.setText(this.population);
		capital.setText(this.capital);
		area.setText(this.area);
        topLevelDomain.setText(this.topLevelDomain);
        callingCodes.setText(this.callingCodes);

		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		//imageLoader.DisplayImage(flag, imgflag);
	}
}