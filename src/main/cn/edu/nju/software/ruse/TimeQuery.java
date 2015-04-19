package cn.edu.nju.software.ruse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class TimeQuery extends ElementQuery{

	public TimeQuery(String dir, String term) {
		super(dir, term);
	}
	boolean hasPrefix(){
		return true;
	}
	   //This is a hook 
	   boolean isRangeQuery(){
		   if(term.charAt(8)=='['||term.charAt(8)=='{')
				  return true;
			  else
				  return false;
	   }
	   //This is a hook 
	   LinkedList searchRangeQuery(){
		   boolean inclusive=true;
		   if(term.charAt(8)=='[')
			  inclusive=true;
		   else if(term.charAt(8)=='{')
			   inclusive=false;
		   StringTokenizer tokenizer=new StringTokenizer(term,":{[-]}");
		   tokenizer.nextToken();
		   String time1=tokenizer.nextToken();
		   String time2=tokenizer.nextToken();
		   LinkedList result=new LinkedList();
		    BufferedReader input;
		    try{
			input = new BufferedReader((new FileReader(indexFile)));
			String line;   LinkedList<String> operandFile=new LinkedList<String>();
			while((line=input.readLine())!=null){
				StringTokenizer termTokenizer=new StringTokenizer(line);
				String wordInIndex=termTokenizer.nextToken();
				if(inclusive){
					if(time1.compareTo(wordInIndex)<=0&&time2.compareTo(wordInIndex)>=0){
						 while(termTokenizer.hasMoreTokens()){
							operandFile.add(termTokenizer.nextToken());
						   }
					}				     
				}
				else{
					if(time1.compareTo(wordInIndex)<0&&time2.compareTo(wordInIndex)>0){
						 while(termTokenizer.hasMoreTokens()){
							operandFile.add(termTokenizer.nextToken());
						   }
					}				     
				}	
			}
			result=operandFile;
		    }
		    catch(FileNotFoundException e){
				   e.printStackTrace();
			   }
			   catch(IOException e){
				   e.printStackTrace();
			   }
		   
		   return result;
	   }
	   
	   //for all four kinds of queries which has prefix and are not rangeQueries
	    LinkedList searchSoloQuery(){
	    	StringTokenizer tokenizer=new StringTokenizer(term,":");
			   tokenizer.nextToken();
			   double time=Double.parseDouble(tokenizer.nextToken());
			   LinkedList result=new LinkedList();
			    BufferedReader input;
			    try{
				input = new BufferedReader((new FileReader(indexFile)));
				String line;   LinkedList<String> operandFile=new LinkedList<String>();
				while((line=input.readLine())!=null){
					StringTokenizer termTokenizer=new StringTokenizer(line);
					String wordInIndex=termTokenizer.nextToken();
						if(time==Double.parseDouble(wordInIndex)){
							 while(termTokenizer.hasMoreTokens()){
								operandFile.add(termTokenizer.nextToken());
							   }
						}				     
				}
				result=operandFile;
			    }
			    catch(FileNotFoundException e){
					   e.printStackTrace();
				   }
				   catch(IOException e){
					   e.printStackTrace();
				   }
			   
			   return result;
	    }
	
	   
}
