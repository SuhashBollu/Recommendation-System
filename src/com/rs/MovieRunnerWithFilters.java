package com.rs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MovieRunnerWithFilters {
	//private static HashMap<String, Movie> rMovies;
	public void printAverageRatings()throws IOException{
		ThirdRatings tr = new ThirdRatings(); 
		//System.out.println(sr.getMovieSize());
		System.out.println("read data for "+tr.getRaterSize()+" raters");
	//	rMovies = MovieDatabase
		MovieDatabase.initialize("ratedmoviesfull.csv");
		System.out.println("read data for "+MovieDatabase.ourMovies.size()+" movies");
		//System.out.println(MovieDatabase.ourMovies.size());
		//ArrayList<Rating> rl = tr.getAverageRatings(35);
		//ArrayList<Rating> rl = tr.getAverageRatingsByFilter(20,new YearAfterFilter(2000));
		ArrayList<Rating> rl = tr.getAverageRatingsByFilter(20,new GenreFilter("Comedy"));
		System.out.println("found "+rl.size()+" movies");
		Collections.sort(rl);
		for(Rating rt:rl){
			System.out.println(rt.getValue()+" "+tr.getTitle(rt.getItem()));
		}
	}
	
	public void printAverageRatingsByGenre() throws IOException{
		ThirdRatings tr = new ThirdRatings("data/ratings_short.csv"); 
		System.out.println("read data for "+tr.getRaterSize()+" raters");
		MovieDatabase.initialize("ratedmovies_short.csv");
		System.out.println("read data for "+MovieDatabase.ourMovies.size()+" movies");
		ArrayList<Rating> rl = tr.getAverageRatingsByFilter(1,new GenreFilter("Crime"));
		System.out.println("found "+rl.size()+" movies");
		Collections.sort(rl);
		for(Rating rt:rl){
			System.out.println(rt.getValue()+" "+tr.getTitle(rt.getItem()));
			System.out.println("\t"+tr.getGenres(rt.getItem()));
		}
	}
	
	public void printAverageRatingsByMinutes() throws IOException{
		ThirdRatings tr = new ThirdRatings("data/ratings.csv"); 
		System.out.println("read data for "+tr.getRaterSize()+" raters");
		MovieDatabase.initialize("ratedmoviesfull.csv");
		System.out.println("read data for "+MovieDatabase.ourMovies.size()+" movies");
		ArrayList<Rating> rl = tr.getAverageRatingsByFilter(5,new MinutesFilter(105,135));
		System.out.println("found "+rl.size()+" movies");
		Collections.sort(rl);
		for(Rating rt:rl){
			System.out.println(rt.getValue()+" Time: "+tr.getMinutes(rt.getItem())+" "+tr.getTitle(rt.getItem()));
			//System.out.println("\t"+tr.getGenres(rt.getItem()));
		}
	}
	
	public void printAverageRatingsByDirectors() throws IOException{
		ThirdRatings tr = new ThirdRatings("data/ratings.csv"); 
		System.out.println("read data for "+tr.getRaterSize()+" raters");
		MovieDatabase.initialize("ratedmoviesfull.csv");
		System.out.println("read data for "+MovieDatabase.ourMovies.size()+" movies");
		ArrayList<Rating> rl = tr.getAverageRatingsByFilter(4,new DirectorsFilter("Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack"));
		System.out.println("found "+rl.size()+" movies");
		Collections.sort(rl);
		for(Rating rt:rl){
			System.out.println(rt.getValue()+" "+tr.getTitle(rt.getItem()));
			System.out.println("\t"+tr.getDirector(rt.getItem()));
		}
	}
	public void printAverageRatingsByYearAfterAndGenre() throws IOException{
		ThirdRatings tr = new ThirdRatings("data/ratings.csv"); 
		System.out.println("read data for "+tr.getRaterSize()+" raters");
		MovieDatabase.initialize("ratedmoviesfull.csv");
		System.out.println("read data for "+MovieDatabase.ourMovies.size()+" movies");
		AllFilters af = new AllFilters();
		af.addFilter(new YearAfterFilter(1990));
		af.addFilter(new GenreFilter("Drama"));
		ArrayList<Rating> rl = tr.getAverageRatingsByFilter(8,af);
		System.out.println("found "+rl.size()+" movies");
		Collections.sort(rl);
		for(Rating rt:rl){
			System.out.println(rt.getValue()+" "+tr.getTitle(rt.getItem()));
			System.out.println("\t"+tr.getGenres(rt.getItem()));
		}
	}
	
	public void printAverageRatingsByDirectorsAndMinutes() throws IOException{
		ThirdRatings tr = new ThirdRatings("data/ratings.csv"); 
		System.out.println("read data for "+tr.getRaterSize()+" raters");
		MovieDatabase.initialize("ratedmoviesfull.csv");
		System.out.println("read data for "+MovieDatabase.ourMovies.size()+" movies");
		AllFilters af = new AllFilters();
		af.addFilter(new MinutesFilter(90,180));
		af.addFilter(new DirectorsFilter("Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack"));
		ArrayList<Rating> rl = tr.getAverageRatingsByFilter(3,af);
		System.out.println("found "+rl.size()+" movies");
		Collections.sort(rl);
		for(Rating rt:rl){
			System.out.println(rt.getValue()+" Time: "+tr.getMinutes(rt.getItem())+" "+tr.getTitle(rt.getItem()));
			System.out.println("\t"+tr.getDirector(rt.getItem()));
		}
	}
	
	public static void main(String[] args)throws IOException{
		MovieRunnerWithFilters MovieRunnerWithFilters1 = new MovieRunnerWithFilters();
		MovieRunnerWithFilters1.printAverageRatings();
	}
}
