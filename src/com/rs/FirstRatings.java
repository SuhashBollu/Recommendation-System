package com.rs;
import java.util.*;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
public class FirstRatings {
	public ArrayList<Movie> loadMovies(String filename) throws IOException{
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		CSVParser parser = new CSVParser(new FileReader(filename), CSVFormat.DEFAULT.withHeader());
		for (CSVRecord record : parser) {
			Movie myMov = new Movie(record.get("id"),record.get("title"),record.get("year"),record.get("genre"),record.get("director"),record.get("country"),record.get("poster"),Integer.parseInt(record.get("minutes")));
			movieList.add(myMov);
		}
		/*for(int i=0;i<movieList.size();i++){
			System.out.println(movieList.get(i).getTitle());
		}*/
		//System.out.println("Size"+movieList.size());
		parser.close();
		return movieList;
	}
	
	public int printComedy(ArrayList<Movie> ml){
		int count = 0;
		for(int i=0;i<ml.size();i++){
			if(ml.get(i).getGenres().contains("Comedy")){
				count++;
			}
		}
		return count;
	}
	
	public int printGreater(ArrayList<Movie> ml){
		int count = 0;
		for(int i=0;i<ml.size();i++){
			if(ml.get(i).getMinutes()>150){
				count++;
			}
		}
		return count;
	}
	
	public int maxMovies(ArrayList<Movie> ml){
		int max = 0;
		HashMap<String, Integer> nm = new HashMap<String, Integer>();
		for(int i=0;i<ml.size();i++){
			for(String dir:ml.get(i).getDirector().split(", ")){
				if(!nm.containsKey(dir)){
					nm.put(dir, null);
			}
			}
		}
		for(String key:nm.keySet()){
			//System.out.println(key);
			int count = 0;
			for(int j=0;j<ml.size();j++){
				//System.out.println("if");
				//System.out.println(ml.get(j).getDirector());
				//System.out.println(key);
				/*if(ml.get(j).getDirector().equals(key)){
					count++;*/
				for(String dir:ml.get(j).getDirector().split(", ")){
					if(dir.equals(key)){
						count++;
					}
				}
					
					//System.out.println("ii");
					//System.out.println("count"+count);
				}
			nm.putIfAbsent(key, count);
		}
		String name = "";
		for(String key:nm.keySet()){
			
			System.out.println(key+" "+nm.get(key));
			if(nm.get(key)>max){
				max = nm.get(key);
				name = key;
			}
		}
		System.out.println("Name : "+name);
		return max;
	}
	
	public void testLoadMovies()throws IOException{
		ArrayList<Movie> sada = new ArrayList<Movie>();
		sada = loadMovies("data/ratedmoviesfull.csv");
		System.out.println(maxMovies(sada));
	}
	
	public ArrayList<Rater> loadRaters(String filename) throws IOException{
		ArrayList<Rater> raterList = new ArrayList<Rater>();
		CSVParser parser = new CSVParser(new FileReader(filename), CSVFormat.DEFAULT.withHeader());
		ArrayList<String> ids = new ArrayList<String>();
		for (CSVRecord record : parser) {
			String id = record.get("rater_id");
			if(!ids.contains(id)){
				ids.add(id);
			}
		}
		parser.close();
		
		for(String id:ids){
			CSVParser parser1 = new CSVParser(new FileReader(filename), CSVFormat.DEFAULT.withHeader());
			//System.out.println(ids.get(i));
			Rater r = new EfficientRater(id);
			for(CSVRecord record : parser1){
				//System.out.println("inside for");
				if(record.get("rater_id").equals(id)){
					//System.out.println("inside if");
					r.addRating(record.get("movie_id"), Double.parseDouble(record.get("rating")));
				}
			}
			raterList.add(r);
			parser1.close();
		}
	
		//System.out.println("Number of raters "+raterList.size());
		/*for(int i=0;i<raterList.size();i++){
			System.out.println("Rater id - "+raterList.get(i).getID()+" No of ratings "+raterList.get(i).numRatings());
			ArrayList<String> items = new ArrayList<String>();
			items = raterList.get(i).getItemsRated();
			for(int j=0;j<items.size();j++){
				System.out.println("         MovieId - "+items.get(j)+" Rating - "+raterList.get(i).getRating(items.get(j)));
			}
		}*/
		//System.out.println("Size"+movieList.size());
		
		return raterList;
	}
	
	public void noOfRatings(ArrayList<Rater> rt, String rater){
		int count = 0;
		for(int i=0;i<rt.size();i++){
			//System.out.println(rater);
			//System.out.println(rt.get(i).getID());
			if(rt.get(i).getID().equals(rater)){
				System.out.println(rt.get(i).getItemsRated().size());
			}
		}
		
	}
	
	public void maxRatings(ArrayList<Rater> rt){
		String name ="";
		int max = 0;
		for(int i=0;i<rt.size();i++){
			if(rt.get(i).getItemsRated().size()>max){
				max = rt.get(i).getItemsRated().size();
				name = rt.get(i).getID();
			}
		}
		System.out.println("Max ratings "+max+" by "+name);
	}
	
	public void noOfRatingsMovie(ArrayList<Rater> rt, String movieid){
		int count = 0;
		for(int i=0;i<rt.size();i++){
			ArrayList<String> items = rt.get(i).getItemsRated();
			for(String id:items){
				if(movieid.equals(id)){
					count++;
				}
			}
		}
		System.out.println("No. of ratings "+count);
	}
	
	public void noOfUniqueMoivies(ArrayList<Rater> rt){
		
		ArrayList<String> movies = new ArrayList<String>();
		for(Rater rst:rt){
			if(!movies.contains(rst.getID())){
				movies.add(rst.getID());
			}else{
				movies.remove(rst.getID());
			}
		}
		System.out.println("No. of unique movies "+movies.size());
	}
	
	public void testLoadRaters()throws IOException{
		ArrayList<Rater> sada = new ArrayList<Rater>();
		sada = loadRaters("data/ratings.csv");
		//maxRatings(sada);
		//noOfRatingsMovie(sada,"1798709");
		noOfUniqueMoivies(sada);
	}
	
	public static void main(String[] args) throws IOException{
		FirstRatings FirstRatings1 = new FirstRatings();
		FirstRatings1.testLoadRaters();
	}
}
