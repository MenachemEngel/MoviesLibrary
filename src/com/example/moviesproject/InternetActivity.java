package com.example.moviesproject;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class InternetActivity extends Activity implements OnClickListener ,GetMovieDetailsListener ,OnGetMovieListDoneListenr ,Constants{

	EditText editTextGo;
	ListView listViewInternet;
	ArrayList<InternetMovie> movieList;
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_internet);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();

		editTextGo = (EditText) findViewById(R.id.internet_editText);
		listViewInternet = (ListView) findViewById(R.id.from_internet_listView);
		
		listViewInternet.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				String idMovieList = movieList.get(arg2).getMovieIdInternet();
				String url = "http://www.omdbapi.com/?i="+idMovieList+"&plot=full&r=json";
				GetFullMovieDetailsTask detailsTask = new GetFullMovieDetailsTask(InternetActivity.this);
				detailsTask.execute(url);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finish();
			}
		});
		
		findViewById(R.id.internet_go_Button).setOnClickListener(this);
		findViewById(R.id.internet_cancel_button).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.internet_go_Button:
			String movieName = editTextGo.getText().toString();
			movieName = movieName.replaceAll(" ", "+");
			String movieToGo = "http://www.omdbapi.com/?s="+movieName;
			
			GetMovieListTask getMovieListTask = new GetMovieListTask(this);
			getMovieListTask.execute(movieToGo);
			break;
		case R.id.internet_cancel_button:
			finish();
			break;

		default:
			break;
		}
	}
	@Override
	public void onGetListDone(ArrayList<InternetMovie> movieList) {
		this.movieList = movieList;
		ArrayAdapter<InternetMovie> adapter = new ArrayAdapter<InternetMovie>(this ,android.R.layout.simple_list_item_1, movieList);
		listViewInternet.setAdapter(adapter);

	}
	

	@Override
	public void onGetMovieDetails(Movie movie) {
		Intent intent = new Intent(this ,AddMyselfMovieActivity.class);
//		intent.putExtra("id", movie.getMovieId());
//		intent.putExtra("title", movie.getMovieTitle());
//		intent.putExtra("description", movie.getMovieDescription());
//		intent.putExtra("image", movie.getMovieImage());
		intent.putExtra("movie", movie);
		intent.putExtra("mode", INTERNET_ACTIVITY);
		startActivity(intent);
	}
	
	

}
