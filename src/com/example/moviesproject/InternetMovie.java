package com.example.moviesproject;

public class InternetMovie {
	
	private String title;
	private String movieIdInternet;
	
	public InternetMovie(String title, String movieIdInternet) {
		super();
		this.title = title;
		this.movieIdInternet = movieIdInternet;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMovieIdInternet() {
		return movieIdInternet;
	}

	public void setMovieIdInternet(String movieIdInternet) {
		this.movieIdInternet = movieIdInternet;
	}

	@Override
	public String toString() {
		return  title ;
	}
	
	

}
