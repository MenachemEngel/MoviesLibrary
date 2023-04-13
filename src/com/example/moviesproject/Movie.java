package com.example.moviesproject;

import java.io.Serializable;

public class Movie implements Serializable{
	
	private String movieId;
	private String movieTitle;
	private String movieDescription;
	private String movieImage;
	
	public Movie(String movieId, String movieTitle, String movieDescription, String movieImage) {
		
		this.movieId = movieId;
		this.movieTitle = movieTitle;
		this.movieDescription = movieDescription;
		this.movieImage = movieImage;
	}
	
	public Movie(String movieTitle, String movieDescription, String movieImage) {
		
		this.movieTitle = movieTitle;
		this.movieDescription = movieDescription;
		this.movieImage = movieImage;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public String getMovieDescription() {
		return movieDescription;
	}

	public void setMovieDescription(String movieDescription) {
		this.movieDescription = movieDescription;
	}

	public String getMovieImage() {
		return movieImage;
	}

	public void setMovieImage(String movieImage) {
		this.movieImage = movieImage;
	}

	@Override
	public String toString() {
		return movieTitle;
	}
	
	

	
	
	
	

}
