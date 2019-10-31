package com.rs;

import java.io.IOException;
import java.util.ArrayList;

public class SecondRatings{
	private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings() throws IOException{
        // default constructor
        this("data/ratedmoviesfull.csv", "data/ratings.csv");
    }

	public SecondRatings(String moviefile, String ratingsfile) throws IOException{
		// TODO Auto-generated constructor stub
		FirstRatings fr = new FirstRatings();
		myMovies = fr.loadMovies(moviefile);
		myRaters = fr.loadRaters(ratingsfile);
	}

	public int getMovieSize(){
		return myMovies.size();
	}
	
	public int getRaterSize(){
		return myRaters.size();
	}
	
	private double getAverageById(String id, int minimalRaters){
		//System.out.println("MId"+id);
		//System.out.println("mr"+minimalRaters);
		double average = 0.0;
		int count = 0;
		double sum = 0.0;
			//for(Movie mv:myMovies){
				//System.out.println("inside fm");
				int rc = 0;
				for(Rater rt:myRaters){
					//System.out.println("rid"+rt.getID());
					//System.out.println("ins fr");
					if(rt.getItemsRated().contains(id)){
						//System.out.println("inside rc++");
						rc++;
					}
				}
				//System.out.println("rc"+rc);
				if(rc>=minimalRaters){
					//System.out.println("if rc>");
				for(Rater rt:myRaters){
					if(rt.getItemsRated().contains(id)){
					//System.out.println("frt");
					sum = sum+rt.getRating(id);
					count++;}
				}
				//}
			}
			//System.out.println("sum"+sum);
			if(sum!=0.0)
				average = sum/count;
			//System.out.println("avg"+average);
		return average;
	}
	
	public ArrayList<Rating> getAverageRatings(int minimalRaters){
		ArrayList<Rating> rs = new ArrayList<Rating>();
		//for(Movie mv:myMovies)
			//System.out.println(mv.getID());
		for(Movie mv:myMovies){
			//System.out.println("inside for");
			/*int rc = 0;
			for(Rater rt:myRaters){
				if(rt.getItemsRated().contains(rt.getID())){
					rc++;
				}
			}
			if(rc>=minimalRaters){
				//rs.add(new Rating(mv.getID(),))
				for(Rater rt:myRaters){*/
			//System.out.println(getAverageById(mv.getID(),minimalRaters));
			if(getAverageById(mv.getID(),minimalRaters)!=0.0){
				//System.out.println("ins if");
				rs.add(new Rating(mv.getID(),getAverageById(mv.getID(),minimalRaters)));}
			
					
				//}
			//}
		}
		return rs;
	}
	
	public String getTitle(String id){
		String title = "";
		for(Movie mv:myMovies){
			if(mv.getID().equals(id))
				title = mv.getTitle();
		}
		return title;
	}
}
