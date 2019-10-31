package com.rs;
import java.io.IOException;
import java.util.ArrayList;
public class DirectorsFilter implements Filter{
	private String[] myDirectors;
	public DirectorsFilter(String directors){
		myDirectors = directors.split(",");
	}
	@Override
	public boolean satisfies(String id) throws IOException {
		boolean flg = false;
		for(int i=0;i<myDirectors.length;i++){
		if(MovieDatabase.getDirector(id).contains(myDirectors[i]))
			flg=true;}
		return flg;
	}
}
