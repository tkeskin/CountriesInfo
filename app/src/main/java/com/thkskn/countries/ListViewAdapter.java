package com.thkskn.countries;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();

	public ListViewAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;
		imageLoader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// Declare Variables
		TextView name;
		TextView region;
		TextView population;

		ImageView flag;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.listview_item, parent, false);
		// Get the position
		resultp = data.get(position);

		// Locate the TextViews in listview_item.xml
		name = (TextView) itemView.findViewById(R.id.name);
		region = (TextView) itemView.findViewById(R.id.region);
		//population = (TextView) itemView.findViewById(R.id.population);

		// Locate the ImageView in listview_item.xml
		flag = (ImageView) itemView.findViewById(R.id.flag);

		// Capture position and set results to the TextViews
		name.setText(resultp.get(MainActivity.NAME));
		region.setText(resultp.get(MainActivity.REGION));
		//population.setText(resultp.get(MainActivity.POPULATION));

		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		imageLoader.DisplayImage(resultp.get(MainActivity.FLAG), flag);
		// Capture ListView item click
		itemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Get the position
				resultp = data.get(position);
				Intent intent = new Intent(context, SingleItemView.class);
				// Pass all data name
				intent.putExtra("name", resultp.get(MainActivity.NAME));
				// Pass all data region
				intent.putExtra("region", resultp.get(MainActivity.REGION));
				// Pass all data population
				intent.putExtra("population",resultp.get(MainActivity.POPULATION));
				intent.putExtra("area",resultp.get(MainActivity.AREA));
				intent.putExtra("nativeName",resultp.get(MainActivity.NATIVENAME));

				intent.putExtra("capital",resultp.get(MainActivity.CAPITAL));
				intent.putExtra("subregion",resultp.get(MainActivity.SUBREGION));
                intent.putExtra("topLevelDomain",resultp.get(MainActivity.TOPLEVELDOMAIN));
                intent.putExtra("callingCodes",resultp.get(MainActivity.CALLINGCODES));

				// Pass all data flag
				intent.putExtra("flag", resultp.get(MainActivity.FLAG));
				// Start SingleItemView Class
				context.startActivity(intent);

			}
		});
		return itemView;
	}
}
