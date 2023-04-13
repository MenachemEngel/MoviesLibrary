package com.example.moviesproject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.provider.MediaStore;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddMyselfMovieActivity extends Activity implements OnClickListener,Constants {

	private EditText etTitle;
	private EditText etDescription;
	private EditText etImageUrl;
	private DBHandler db;
	private int mode;
	private String movieId;
	private ImageView img;
	
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();

		findViewById(R.id.second_show_Button1).setOnClickListener(this);
		findViewById(R.id.second_Button2).setOnClickListener(this);
		findViewById(R.id.second_Button3).setOnClickListener(this);
		
		Intent intent = getIntent();
		
		img = (ImageView) findViewById(R.id.second_imageView1);
		img.setOnClickListener(this);
		
		mode = intent.getIntExtra("mode", -1);
		
		etTitle = (EditText) findViewById(R.id.second_editText1);
		etDescription = (EditText) findViewById(R.id.second_editText2);
		etImageUrl = (EditText) findViewById(R.id.second_editText3);
		
		if(mode == UPDATE_MOVIE){
			movieId = intent.getStringExtra("movieId");
			db = new DBHandler(this);
			Movie movie = db.getMovie(movieId);
			
			setData(movie);
		}else if(mode == INTERNET_ACTIVITY){
			Movie movie = (Movie) intent.getSerializableExtra("movie");
			
			setData(movie);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.second_show_Button1:
			if(etImageUrl.getText().toString().startsWith("content")){
				Uri uri1 = Uri.parse(etImageUrl.getText().toString());
				showImageFromSDCard(uri1);
			}else{
				LoadImage loadImage = new LoadImage(this);
				loadImage.execute(etImageUrl.getText().toString());
			}
			break;
		case R.id.second_Button2:
			if(etTitle.length() == 0){
				Toast.makeText(this, "The title line should not be empty", 0).show();
				return;
			}
			DBHandler db = new DBHandler(this);
			String title = etTitle.getText().toString();
			String description = etDescription.getText().toString();
			String image= etImageUrl.getText().toString();
			Movie movie = new Movie(title, description, image); 
			if(mode == ADD_MYSELF_MOVIE_ACTIVITY){
				db.addMovie(movie);
			}else if(mode == UPDATE_MOVIE){
				movie.setMovieId(movieId);
				db.updateMovie(movie);
			}else if(mode == INTERNET_ACTIVITY){
				db.addMovie(movie);
				Intent intent = new Intent(this ,MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return;
			}
			setResult(RESULT_OK);
			finish();
			break;
		case R.id.second_Button3:
			finish();
			break;
		case R.id.second_imageView1:
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intent.createChooser(intent, "Select picture"), SELECT_PICTURE);
			break;
		}
	}
	
	private void setData(Movie movie) {
		etTitle.setText(movie.getMovieTitle());
		etDescription.setText(movie.getMovieDescription());
		etImageUrl.setText(movie.getMovieImage());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == SELECT_PICTURE){
			if(resultCode == RESULT_OK){
				Uri selectedImage = data.getData();
				etImageUrl.setText(selectedImage.toString());
				String[] filePathColumn = {MediaStore.Images.Media.DATA};
				
				Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
				cursor.moveToFirst();
				
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String filePath = cursor.getString(columnIndex);
				cursor.close();
				
				Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
				img.setImageBitmap(yourSelectedImage);
				}
		}
	}
	
	private void showImageFromSDCard(Uri uri) {
		try {
			String[] filePathColomn = {MediaStore.Images.Media.DATA};
			Cursor cursor = getContentResolver().query(uri, filePathColomn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColomn[0]);
			String filePath = cursor.getString(columnIndex);
			cursor.close();
			
			Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
			
			img.setImageBitmap(yourSelectedImage);	
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
