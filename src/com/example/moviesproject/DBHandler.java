package com.example.moviesproject;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DBHandler implements Constants{
	
	private DBHelper dbHelper;

	public DBHandler(Context context) {
		super();
		dbHelper = new DBHelper(context);
	}
	
	public void addMovie(Movie movie){
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(MOVIE_TITLE ,movie.getMovieTitle());
		cv.put(MOVIE_DESCRIPTION ,movie.getMovieDescription());
		cv.put(MOVIE_IMAGE ,movie.getMovieImage());
		try {
			db.insert(TABLE_NAME, "default value", cv);			
		} catch (SQLiteException e) {
			e.getCause();
		}finally{
			if(db.isOpen()){
				db.close();
			}
		}
	}
	
	public Movie getMovie(String movieId){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_NAME, null, MOVIE_ID+"=?", new String[]{movieId}, null, null, null);
		int indexId = cursor.getColumnIndex(MOVIE_ID);
		int indexTitle = cursor.getColumnIndex(MOVIE_TITLE);
		int indexDescription = cursor.getColumnIndex(MOVIE_DESCRIPTION);
		int indexImage = cursor.getColumnIndex(MOVIE_IMAGE);
		
		cursor.moveToNext();
		String id = cursor.getString(indexId);
		String title = cursor.getString(indexTitle);
		String description = cursor.getString(indexDescription);
		String imageUrl = cursor.getString(indexImage);
		
		Movie movie = new Movie(id, title, description, imageUrl);
		
		return movie;
	}
	
	public ArrayList<Movie> getAllMovies(){
		
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		int indexId = cursor.getColumnIndex(MOVIE_ID);
		int indexTitle = cursor.getColumnIndex(MOVIE_TITLE);
		int indexDescription = cursor.getColumnIndex(MOVIE_DESCRIPTION);
		int indexImage = cursor.getColumnIndex(MOVIE_IMAGE);
		
		ArrayList<Movie> arrMovie2 = new ArrayList<Movie>();
		while(cursor.moveToNext()){
		String id = cursor.getString(indexId);
		String title = cursor.getString(indexTitle);
		String description = cursor.getString(indexDescription);
		String imageUrl = cursor.getString(indexImage);
		
		Movie movie = new Movie(id, title, description, imageUrl);
		arrMovie2.add(movie);
		}
		
		return arrMovie2;
	}
	
	
	public void updateMovie(Movie movie) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		
		cv.put(MOVIE_TITLE, movie.getMovieTitle());
		cv.put(MOVIE_DESCRIPTION, movie.getMovieDescription());
		cv.put(MOVIE_IMAGE, movie.getMovieImage());
		
		try {
			db.update(TABLE_NAME, cv, MOVIE_ID + "=?", new String[]{movie.getMovieId()});			
		} catch (SQLiteException e) {
			e.getCause();
		}finally{
			if(db.isOpen()){
				db.close();
			}
		}
	}
	
	public void deleteMovie(String movieId){
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		try {
			db.delete(TABLE_NAME, MOVIE_ID+"=?", new String[]{movieId});			
		} catch (SQLiteException e) {
			e.getCause();
		}finally{
			if(db.isOpen()){
				db.close();
			}
		}
		
	}
	
	public void deleteAllMovis() {
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
				db.delete(TABLE_NAME, null, null);
		} catch (SQLiteException e) {
			e.getCause();
		}finally{
			if(db.isOpen()){
				db.close();
			}
		}

	}
	
	

}
