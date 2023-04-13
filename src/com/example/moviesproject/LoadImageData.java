package com.example.moviesproject;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoadImageData extends AsyncTask<String, String, Bitmap> {

	private ProgressDialog progressDialog;
	private Activity activity;
	private ImageView imageView;
	
	private TextView view;
	
	public LoadImageData(Activity activity){
		this.activity = activity;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage("Loading image... ");
		progressDialog.show();
	}
	
	@Override
	protected Bitmap doInBackground(String... params) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream((InputStream) new URL(params[0]).getContent());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return bitmap;
	}
	@Override
	protected void onPostExecute(Bitmap result) {
		imageView = (ImageView) activity.findViewById(R.id.data_movie_image);
		if(result != null){
			imageView.setImageBitmap(result);
			progressDialog.dismiss();
		}else{
			progressDialog.dismiss();
			view = (TextView) activity.findViewById(R.id.textViewError);
			view.setText("Image dose not exist or network error!");
		}
	}

	

}
