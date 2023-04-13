package com.example.moviesproject;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class GetMovieListTask extends AsyncTask<String, Void, String> {

	private Activity activity;
	private OnGetMovieListDoneListenr doneListenr;
	private ProgressDialog progressDialog;
	
	
	public GetMovieListTask(Activity activity) {
		this.activity = activity;
		doneListenr = (OnGetMovieListDoneListenr) activity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage("Loading list movies... ");
		progressDialog.show();
	}


	@Override
	protected String doInBackground(String... arg0) {
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(arg0[0]);
		HttpResponse httpResponse;
		
		HttpEntity httpEntity;
		JSONObject jsonObject = null;
		String response = null;
		try {
			httpResponse = client.execute(httpPost);
			httpEntity = httpResponse.getEntity();
			response = EntityUtils.toString(httpEntity);
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
		
		String name = null;
		String description = null;
		String url = null;
		String image = null;
		
		ArrayList<InternetMovie> movieList = new ArrayList<InternetMovie>();
		try {
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("Search");
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject movie = (JSONObject) jsonArray.get(i);
				
				String title = movie.getString("Title");
				String movieIdInternet = movie.getString("imdbID");
				InternetMovie internetMovie = new InternetMovie(title, movieIdInternet);
				movieList.add(internetMovie);
				progressDialog.dismiss();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		doneListenr.onGetListDone(movieList);
		
	}

}
