package com.rs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DummyTest {
	public static void main(String[] args) throws IOException{
	/*String s ="Suhash,Mounika,Bollu";
	String[] df = s.split(",");
	System.out.println(df[0]+"\n"+df[1]+"\n"+df[2]);*/
		StringBuilder sb = new StringBuilder();
	    sb.append("<html>");
	    sb.append("<head>");
	    sb.append("<title>Title Of the page");
	    sb.append("</title>");
	    sb.append("</head>");
	    sb.append("<body> <b>Hello World</b>");
	    sb.append("</body>");
	    sb.append("</html>");
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
	}
}
