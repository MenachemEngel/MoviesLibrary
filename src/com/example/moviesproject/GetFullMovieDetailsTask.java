package com.example.moviesproject;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;

public class GetFullMovieDetailsTask extends AsyncTask<String, Void, String> {

	private Activity activity;
	private GetMovieDetailsListener getMovieDetailsListener;
	
	
	
	public GetFullMovieDetailsTask(Activity activity) {
		this.activity = activity;
		getMovieDetailsListener = (GetMovieDetailsListener) activity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... arg0) {
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(arg0[0]);
		HttpResponse httpResponse;
		
		HttpEntity entity;
		JSONObject jsonObject = null;
		String response = null;
		
		try {
			httpResponse = client.execute(httpPost);
			entity = httpResponse.getEntity();
			response = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		return response;
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		
		try {
			JSONObject jsonObject = new JSONObject(result);
			String title = jsonObject.getString("Title");
			String description = jsonObject.getString("Plot");
			String imageUrl = jsonObject.getString("Poster");
			Movie movie = new Movie(title, description, imageUrl);
			getMovieDetailsListener.onGetMovieDetails(movie);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
