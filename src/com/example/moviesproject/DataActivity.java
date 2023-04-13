package com.example.moviesproject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DataActivity extends Activity implements OnClickListener {

	private TextView tvTitle;
	private TextView TvDescription;
	private ImageView imageMovie;
	
	private LinearLayout linearLayout;
	
	private DBHandler db;
	private String movieId;
	private int mode;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_data);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		Intent intent = getIntent();
		mode = intent.getIntExtra("mode", -1);
		
		tvTitle = (TextView) findViewById(R.id.data_movie_title);
		TvDescription = (TextView) findViewById(R.id.data_movie_description);
		imageMovie = (ImageView) findViewById(R.id.data_movie_image);
		
		linearLayout = (LinearLayout) findViewById(R.id.linearLayoutData);
		
		movieId = intent.getStringExtra("movieId");
		db = new DBHandler(this);
		Movie movie = db.getMovie(movieId);
//	הצגת תמונה	
		String img = movie.getMovieImage();
		
			if(img.toString().startsWith("content")){
				Uri uri1 = Uri.parse(img.toString());
				showImageFromSDCard(uri1);
			}else{
				LoadImageData loadImage = new LoadImageData(this);
				loadImage.execute(img.toString());
			}
		
//	---------------------------------------------------------	
		setDataMovie(movie);
		
		findViewById(R.id.data_movie_button_cancel).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.data_movie_button_cancel:
			finish();
			break;

		default:
			break;
		}

	}
	
	private void setDataMovie(Movie movie) {
		tvTitle.setText(movie.getMovieTitle());
		TvDescription.setText(movie.getMovieDescription());
		
	}
private void showImageFromSDCard(Uri uri) {
		try {
			String[] filePathColomn = {MediaStore.Images.Media.DATA};
			Log.d("Exeptin", ""+filePathColomn[0]);
			Cursor cursor = getContentResolver().query(uri, filePathColomn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColomn[0]);
			String filePath = cursor.getString(columnIndex);
			cursor.close();
			
			Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
			
			imageMovie.setImageBitmap(yourSelectedImage);	
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
