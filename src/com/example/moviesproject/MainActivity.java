package com.example.moviesproject;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener ,Constants{

	private ListView listView;
	private ArrayList<Movie> movies;
	private DBHandler dbHandler;
	private String movieId;
	private ArrayAdapter<Movie> movieAdapter;
	
    @SuppressLint("NewApi") 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        listView = (ListView) findViewById(R.id.listView1);
        dbHandler = new DBHandler(this);
        
        findViewById(R.id.button1).setOnClickListener(this);
        
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1){
        	ActionBar actionBar = getActionBar();
        	actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        	actionBar.setCustomView(R.layout.activity_action_bar);
        	
        	View abView = actionBar.getCustomView();
        	
        	ImageView imageView = (ImageView) abView.findViewById(R.id.imageView1);
        	imageView.setOnClickListener(this);
        	registerForContextMenu(imageView);	
        	
       	
        }
        refreshList();
       listView.setOnItemLongClickListener(new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int positon, long id) {
			movieId = movies.get(positon).getMovieId();
			return false;
		}
	});
        
     //אירוע לחיצה על שורה מהליסט   
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long id) {
				
				movieId = movies.get(position).getMovieId();
				
				Intent intent = new Intent(MainActivity.this, DataActivity.class);
				intent.putExtra("mode", DATA_MOVIE);
				intent.putExtra("movieId", movieId);
				startActivity(intent);
			}
		});
        registerForContextMenu(listView);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.deleteMenu:
    			dbHandler.deleteAllMovis();
    			refreshList();
    			break;
    		case R.id.exitMenu:
    			finish();
    			break;
    	}
    	return super.onOptionsItemSelected(item);
    }
    

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			AddDialog addDialog = new AddDialog(this);
			addDialog.show();
			break;
		case R.id.imageView1:
			openOptionsMenu();
			break;

		default:
			break;
		}
		
	}
	

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == ADD_MYSELF_MOVIE_ACTIVITY || requestCode == UPDATE_MOVIE){
			if(resultCode == RESULT_OK){
				refreshList();
			}
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		
		MenuInflater menuSettings = getMenuInflater();
		
		menuSettings.inflate(R.menu.main_menu, menu);
//		menu.add(0, 4, 4, "test");
		
		
		return super.onCreateOptionsMenu(menu);
	}
	
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		
		MenuInflater menuListview = getMenuInflater();
		menuListview.inflate(R.menu.menu_listview1, menu);
		
		
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.update_message:
			updateMovie();
			break;
		case R.id.delete_message:
			dbHandler.deleteMovie(movieId);
			refreshList();
		default:
			break;
		}
		
		return super.onContextItemSelected(item);
	}
	private void updateMovie() {
		Intent intent = new Intent(MainActivity.this, AddMyselfMovieActivity.class);
		intent.putExtra("mode", UPDATE_MOVIE);
		intent.putExtra("movieId", movieId);
		startActivityForResult(intent, UPDATE_MOVIE);
	}
	
	
	 private void refreshList() {
		 movies = dbHandler.getAllMovies();
		 movieAdapter = new ArrayAdapter<Movie>(this, android.R.layout.simple_list_item_1, movies);
		 listView.setAdapter(movieAdapter);
	}

}
