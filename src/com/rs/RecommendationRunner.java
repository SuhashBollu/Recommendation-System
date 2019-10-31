package com.rs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RecommendationRunner implements Recommender{
	
	private Random myRand;
	private int numItemsToRate;
	private int numSimRaters;
	private int numMinRaters;
	private int maxRecs;
	
	public RecommendationRunner(){
		myRand = new Random();
		numItemsToRate = 10;
		numSimRaters = 10;
		numMinRaters = 3;
		maxRecs = 20;
	}
	
	public ArrayList<String> getItemsToRate() throws IOException{
		MovieDatabase.initialize("ratedmoviesfull.csv");
		ArrayList<String> listToRate = new ArrayList<String>();
		Filter f = new TrueFilter();
		ArrayList<String> allMovies = MovieDatabase.filterBy(f);
		for(int i=0;i<numItemsToRate;i++){
			 int currId = myRand.nextInt(MovieDatabase.size());;
	            String currMovieID = allMovies.get(currId);
	            listToRate.add(currMovieID);
		}
		return listToRate;
	}
	
	public void printRecommendationsFor(String webRaterID) throws IOException{
		MovieDatabase.initialize("ratedmoviesfull.csv");
		FourthRatings fr = new FourthRatings();
		ArrayList<Rating> result = fr.getSimilarRatings(webRaterID, numSimRaters, numMinRaters);
		int num = result.size();
		 if (num == 0){
	            System.out.println("Recommendation List:");
	            System.out.println("Not Found");
	        } else{
	        	if (num > maxRecs){
	                num = maxRecs;
	            }
	        	StringBuffer sb = new StringBuffer();
	        	
	        	String header = ("<style>div{border-radius:5px;background-color:grey;margin-top: auto;margin-bottom: auto;margin-right:auto;margin-left:auto;}body{background-image: url(\"https://images5.alphacoders.com/445/thumb-1920-445161.jpg\");}table, th, td {  border: 1px solid black;padding: 15px;}tr:nth-child(even) {background-color: #f2f2f2;}table{ border-collapse: collapse;  margin-left: auto;margin-right: auto;}h1, h2{text-align:center; margin-left: auto;margin-right: auto;}</style><div><h1>Welcome user!!</h1><h2>Movie Recommendations exclusively for you..</h2><table> <tr> <th>Title</th> <th>Genre</th><th>Poster</th></tr>");
	            String body = "";
	            
	            for (int k=0; k<num; k++){
	                Rating currRating = result.get(k);
	                String currMovieID = currRating.getItem();
	                // System.out.println(MovieDatabase.getTitle(currMovieID) + " : " + currRating.getValue());
	                String currMovieTitle = MovieDatabase.getTitle(currMovieID);
	                double currRatingValue = currRating.getValue();
	                String currGenre = MovieDatabase.getGenres(currMovieID);
	                String currPoster = MovieDatabase.getPoster(currMovieID);
	                System.out.println("currGenre"+currGenre);
	                body += printOut(currMovieTitle, currGenre, currPoster);
	            }
	            sb.append(header + body + "</table></div></html>");
	            FileWriter fstream = new FileWriter("MyHtml.html");
	    	    BufferedWriter out = new BufferedWriter(fstream);
	    	    out.write(sb.toString());
	    	    out.close();
	    	    try{

	    	        if ((new File("MyHtml.html")).exists()) {

	    	            Process p = Runtime
	    	               .getRuntime()
	    	               .exec("rundll32 url.dll,FileProtocolHandler MyHtml.html");
	    	            p.waitFor();

	    	        } else {

	    	            System.out.println("File does not exist");

	    	        }

	    	      } catch (Exception ex) {
	    	        ex.printStackTrace();
	    	      }
	            System.out.println(header + body + "</table></div>");
	            
	        }
	}
	
	private String printOut(String title, String genre, String poster){
        return ("<tr> <td>" + title + "</td> <td>" + genre + "</td> <td><img src=\"" + poster + "\"></td></tr>");
    }
	
	
	public static void main(String[] args)throws IOException{
		RecommendationRunner RecommendationRunner1 = new RecommendationRunner();
		RecommendationRunner1.printRecommendationsFor("512");
	}
}
