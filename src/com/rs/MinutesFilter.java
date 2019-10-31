package com.rs;

import java.io.IOException;

public class MinutesFilter implements Filter{
	private int myMin;
	private int myMax;
	public MinutesFilter(int min, int max){
		myMin = min;
		myMax = max;
	}
	@Override
	public boolean satisfies(String id) throws IOException {
		return (MovieDatabase.getMinutes(id)>=myMin&&MovieDatabase.getMinutes(id)<=myMax);
	}
}
