package com.rs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerSimilarRatings{
	
	private FourthRatings fr;
	
	public MovieRunnerSimilarRatings()throws IOException{
		fr = new FourthRatings("data/ratings.csv");
		MovieDatabase.initialize("ratedmoviesfull.csv");
		
	}
	
	public void printAverageRatings()throws IOException{
		System.out.println("read data for "+RaterDatabase.ourRaters.size()+" raters");
		System.out.println("read data for "+MovieDatabase.ourMovies.size()+" movies");
		//System.out.println(MovieDatabase.ourMovies.size());
		//ArrayList<Rating> rl = fr.getAverageRatings(35);
		//ArrayList<Rating> rl = fr.getAverageRatingsByFilter(20,new YearAfterFilter(2000));
		ArrayList<Rating> rl = fr.getAverageRatingsByFilter(20,new GenreFilter("Comedy"));
		System.out.println("found "+rl.size()+" movies");
		Collections.sort(rl);
		for(Rating rt:rl){
			System.out.println(rt.getValue()+" "+fr.getTitle(rt.getItem()));
		}
	}
	
	public void printAverageRatingsByYearAfterAndGenre() throws IOException{
		System.out.println("read data for "+RaterDatabase.ourRaters.size()+" raters");
		System.out.println("read data for "+MovieDatabase.ourMovies.size()+" movies");
		AllFilters af = new AllFilters();
		af.addFilter(new YearAfterFilter(1990));
		af.addFilter(new GenreFilter("Adventure"));
		ArrayList<Rating> rl = fr.getAverageRatingsByFilter(8,af);
		System.out.println("found "+rl.size()+" movies");
		Collections.sort(rl);
		for(Rating rt:rl){
			System.out.println(rt.getValue()+" "+fr.getTitle(rt.getItem()));
			System.out.println("\t"+fr.getGenres(rt.getItem()));
		}
	}
	
	public void printSimilarRatings()throws IOException{
        System.out.println("There are " + MovieDatabase.size() + " movies in the file.");
        System.out.println("There are " + RaterDatabase.size() + " raters in the file.\n");
        ArrayList<Rating> list = fr.getSimilarRatings("71", 20, 5);
        for (Rating r: list){
            System.out.println(MovieDatabase.getTitle(r.getItem()) + " : " + r.getValue());
        }
        System.out.println("\nThere are " + list.size() + " recommended movies were found.");
    }
    
    public void printSimilarRatingsByGenre()throws IOException{
        System.out.println("There are " + MovieDatabase.size() + " movies in the file.");
        System.out.println("There are " + RaterDatabase.size() + " raters in the file.\n");
        Filter genreFilter = new GenreFilter("Mystery");
        ArrayList<String> movieIDs = MovieDatabase.filterBy(genreFilter);
        ArrayList<Rating> list = fr.getSimilarRatings("964", 20, 5);
        int num = 0;
        for (Rating r: list){
            if (movieIDs.contains(r.getItem())){
                System.out.println(MovieDatabase.getTitle(r.getItem()) + " : " + r.getValue());
                System.out.println("    " + MovieDatabase.getGenres(r.getItem()));
                num += 1;
            }
        }
        System.out.println("\nThere are " + num + " recommended movies were found.");
    }
    
    public void printSimilarRatingsByDirector()throws IOException{
        System.out.println("There are " + MovieDatabase.size() + " movies in the file.");
        System.out.println("There are " + RaterDatabase.size() + " raters in the file.\n");
        Filter directorFilter = new DirectorsFilter("Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh");
        ArrayList<String> movieIDs = MovieDatabase.filterBy(directorFilter);
        ArrayList<Rating> list = fr.getSimilarRatings("120", 10, 2);
        int num = 0;
        for (Rating r: list){
            if (movieIDs.contains(r.getItem())){
                System.out.println(MovieDatabase.getTitle(r.getItem()) + " : " + r.getValue());
                System.out.println("    " + MovieDatabase.getDirector(r.getItem()));
                num += 1;
            }
        }
        System.out.println("\nThere are " + num + " recommended movies were found.");
    }
    
    public void printSimilarRatingsByGenreAndMinutes()throws IOException{
        System.out.println("There are " + MovieDatabase.size() + " movies in the file.");
        System.out.println("There are " + RaterDatabase.size() + " raters in the file.\n");
        Filter genreFilter = new GenreFilter("Drama");
        Filter minutesFilter = new MinutesFilter(80, 160);
        AllFilters all = new AllFilters();
        all.addFilter(genreFilter);
        all.addFilter(minutesFilter);
        ArrayList<String> movieIDs = MovieDatabase.filterBy(all);
        ArrayList<Rating> list = fr.getSimilarRatings("168", 10, 3);
        int num = 0;
        for (Rating r: list){
            if (movieIDs.contains(r.getItem())){
                System.out.println(MovieDatabase.getTitle(r.getItem()) + " : " + "Duration-" + MovieDatabase.getMinutes(r.getItem()) 
                                   + " Rating-" + r.getValue());
                System.out.println("    " + MovieDatabase.getGenres(r.getItem()));
                num += 1;
            }
        }
        System.out.println("\nThere are " + num + " recommended movies were found.");
    }
    
    public void printSimilarRatingsByYearAfterAndMinutes()throws IOException{	
        System.out.println("There are " + MovieDatabase.size() + " movies in the file.");
        System.out.println("There are " + RaterDatabase.size() + " raters in the file.\n");
        Filter yearAfterFilter = new YearAfterFilter(1975);
        Filter minutesFilter = new MinutesFilter(70, 200);
        AllFilters all = new AllFilters();
        all.addFilter(yearAfterFilter);
        all.addFilter(minutesFilter);
        ArrayList<String> movieIDs = MovieDatabase.filterBy(all);
        ArrayList<Rating> list = fr.getSimilarRatings("314", 10, 5);
        int num = 0;
        for (Rating r: list){
            if (movieIDs.contains(r.getItem())){
                System.out.println(MovieDatabase.getTitle(r.getItem()) + " : " + "Duration-" + MovieDatabase.getMinutes(r.getItem()) 
                                   + " Rating-" + r.getValue());
                System.out.println("    " + MovieDatabase.getYear(r.getItem()));
                num += 1;
            }
        }
        System.out.println("\nThere are " + num + " recommended movies were found.");
    }
	
	public static void main(String[] args)throws IOException{
		MovieRunnerSimilarRatings MovieRunnerWithFilters1 = new MovieRunnerSimilarRatings();
		MovieRunnerWithFilters1.printSimilarRatingsByYearAfterAndMinutes();
	}
}
