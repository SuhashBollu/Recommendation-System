package com.rs;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
public class ThirdRatings {
	//private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    private static HashMap<String, Movie> ourMovies;
    
    public ThirdRatings() throws IOException{
        // default constructor
        this("data/ratings.csv");
    }

	public ThirdRatings(String ratingsfile) throws IOException{
		// TODO Auto-generated constructor stub
		FirstRatings fr = new FirstRatings();
		//MovieDatabase.initialize("ratedmoviesfull.csv");
		myRaters = fr.loadRaters(ratingsfile);
	}

/*	public int getMovieSize(){
		return myMovies.size();
	}*/
	
	public int getRaterSize(){
		return myRaters.size();
	}
	
	/*private double getAverageById(String id, int minimalRaters){
		double average = 0.0;
			
		return average;
	}*/
	
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
		for(Movie mv:MovieDatabase.ourMovies.values()){
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
		String title = "NO SUCH MOVIE FOUND!!";
		for(Movie mv:MovieDatabase.ourMovies.values()){
			if(mv.getID().equals(id))
				title = mv.getTitle();
		}
		return title;
	}
	
	public String getGenres(String id){
		//System.out.println("ins gr");
		String genre = "NO GENRE FOUND!!";
		for(Movie mv:MovieDatabase.ourMovies.values()){
			if(mv.getID().equals(id))
				genre = mv.getGenres();
		}
		return genre;
	}
	
	public int getMinutes(String id){
		int minutes = 0;
		for(Movie mv:MovieDatabase.ourMovies.values()){
			if(mv.getID().equals(id))
				minutes = mv.getMinutes();
		}
		return minutes;
	}
	
	public String getDirector(String id){
		String director = "NO DIRECTOR FOUND!!";
		for(Movie mv:MovieDatabase.ourMovies.values()){
			if(mv.getID().equals(id)){
				director = mv.getDirector();
			}
		}
		return director;
	}
	
	public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filter) throws IOException{
		ArrayList<Rating> rs = new ArrayList<Rating>();
		ArrayList<String> ml = MovieDatabase.filterBy(filter);
		for(String mid:ml){
			if(getAverageById(mid,minimalRaters)!=0.0){
				rs.add(new Rating(mid,getAverageById(mid,minimalRaters)));
			}
		}
		return rs;
	}
}	
