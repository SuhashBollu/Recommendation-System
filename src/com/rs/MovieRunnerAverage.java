package com.rs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerAverage {
	public void printAverageRatings()throws IOException{
		SecondRatings sr = new SecondRatings("data/ratedmoviesfull.csv","data/ratings.csv"); 
		//System.out.println(sr.getMovieSize());
		//System.out.println(sr.getRaterSize());
		ArrayList<Rating> rl = sr.getAverageRatings(12);
		Collections.sort(rl);
		for(Rating rt:rl){
			System.out.println(rt.getValue()+" "+sr.getTitle(rt.getItem()));
		}
		System.out.println("size"+rl.size());
	}
	
	public static void main(String[] args)throws IOException{
		MovieRunnerAverage MovieRunnerAverage1 = new MovieRunnerAverage();
		MovieRunnerAverage1.printAverageRatings();
	}
}
