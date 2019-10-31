package com.rs;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
public class FourthRatings {
    private static HashMap<String, Rater> ourRaters;
    private static HashMap<String, Movie> ourMovies;
    
    public FourthRatings() throws IOException{
        // default constructor
    	this("data/ratings.csv");
    }

	public FourthRatings(String ratingsfile) throws IOException{
		// TODO Auto-generated constructor stub
		RaterDatabase.initialize(ratingsfile);
	}

	private double getAverageById(String id, int minimalRaters){
		double average = 0.0;
		int count = 0;
		double sum = 0.0;
				int rc = 0;
				for(Rater rt:RaterDatabase.ourRaters.values()){
					if(rt.getItemsRated().contains(id)){
						rc++;
					}
				}
				if(rc>=minimalRaters){
				for(Rater rt:RaterDatabase.ourRaters.values()){
					if(rt.getItemsRated().contains(id)){
					sum = sum+rt.getRating(id);
					count++;}
				}
			}
			if(sum!=0.0)
				average = sum/count;
		return average;
	}
	
	public ArrayList<Rating> getAverageRatings(int minimalRaters){
		ArrayList<Rating> rs = new ArrayList<Rating>();
		for(Movie mv:MovieDatabase.ourMovies.values()){
			if(getAverageById(mv.getID(),minimalRaters)!=0.0){
				rs.add(new Rating(mv.getID(),getAverageById(mv.getID(),minimalRaters)));}
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
	
	private double dotProduct(Rater me, Rater r){
		double result = 0.0;
        ArrayList<String> myMovieIDs = me.getItemsRated();
        ArrayList<String> otherMovieIDs = r.getItemsRated();
        for (String s: myMovieIDs){
            if (otherMovieIDs.contains(s)){
                double myValue = me.getRating(s) - 5;
                double otherValue = r.getRating(s) - 5;
                result += myValue*otherValue;
            }
        }
        return result;
	}
	
	private ArrayList<Rating> getSimilarities(String id){
		ArrayList<Rating> list = new ArrayList<Rating>();
        Rater me = RaterDatabase.getRater(id);
        for (Rater r: RaterDatabase.getRaters()){
            String currOtherID = r.getID();
            if (!currOtherID.equals(id)){
                double currDotProduct = dotProduct(me, r);
                if (currDotProduct > 0.0){
                    list.add(new Rating(currOtherID, currDotProduct));
                }
            }
        }
        Collections.sort(list, Collections.reverseOrder());
        // The instance variables in every object of the arraylist are ID(String) and dotProduct(double).
        return list; 
	}
	
	public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters){
		ArrayList<Rating> sList = getSimilarities(id);
		HashMap<String, HashMap<String, Double>> ratMap = new HashMap <String, HashMap<String, Double>>();
		for(int i=0;i<numSimilarRaters;i++){
			String currRaterID = sList.get(i).getItem();
			Rater currRater = RaterDatabase.getRater(currRaterID);
			ArrayList<String> ratedMovies = currRater.getItemsRated();
			for(String currMovie:ratedMovies){
				if(!ratMap.containsKey(currMovie)){
					HashMap<String, Double> first = new HashMap<String, Double>();
                    first.put(currRaterID, currRater.getRating(currMovie));
                    ratMap.put(currMovie, first);
				}else{
					ratMap.get(currMovie).put(currRaterID, currRater.getRating(currMovie));
				}
			}
		}
		
		ArrayList<Rating> result = new ArrayList<Rating>();
        for (String currMovie : ratMap.keySet()){
            HashMap<String, Double> currValueMap = ratMap.get(currMovie);
            if (currValueMap.size() >= minimalRaters){
                double total = 0.0;
                for (String currRaterID: currValueMap.keySet()){
                    double currSimilarRating = 0.0;
                    // Find similar rating for the currRater.
                    for (Rating r: sList){
                        if (r.getItem().equals(currRaterID)){
                            currSimilarRating = r.getValue();
                        }
                    }
                    total += currValueMap.get(currRaterID)*currSimilarRating;
                }
                double weightedAverage = total/currValueMap.size();
                result.add(new Rating(currMovie, weightedAverage));
            }
        }
        Collections.sort(result, Collections.reverseOrder());
        return result;
	}
	
	
}
