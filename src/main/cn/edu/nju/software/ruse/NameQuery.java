package cn.edu.nju.software.ruse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class NameQuery extends ElementQuery{
	
	public NameQuery(String dir, String term) {
		super(dir, term);
	}
	//NameQuery must have prefix
	 boolean hasPrefix(){
		return true;
	 }
	   boolean isRangeQuery(){
		  if(term.charAt(9)=='['||term.charAt(9)=='{')
			  return true;
		  else
			  return false;
	   }
	 
	   LinkedList searchRangeQuery(){
		   boolean inclusive=true;
		   if(term.charAt(9)=='[')
			  inclusive=true;
		   else if(term.charAt(9)=='{')
			   inclusive=false;
		   StringTokenizer tokenizer=new StringTokenizer(term,":{[-}]");
		   tokenizer.nextToken();
		   String word1=tokenizer.nextToken();
		   String word2=tokenizer.nextToken();
		   LinkedList result=new LinkedList();
		    BufferedReader input;
		    try{
			input = new BufferedReader((new FileReader(indexFile)));
			String line;   LinkedList<String> operandFile=new LinkedList<String>();
			while((line=input.readLine())!=null){
				StringTokenizer termTokenizer=new StringTokenizer(line);
				String wordInIndex=termTokenizer.nextToken();
				if(inclusive){
					if(word1.compareTo(wordInIndex)<=0&&word2.compareTo(wordInIndex)>=0){
						
							operandFile.add(wordInIndex);
						   
					}				     
				}
				else{
					if(word1.compareTo(wordInIndex)<0&&word2.compareTo(wordInIndex)>0){
						 
							operandFile.add(wordInIndex);
						   
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
		   StringTokenizer tokenizerl=new StringTokenizer(term,":");
		   tokenizerl.nextToken();
		   String word=tokenizerl.nextToken();
		   LinkedList result=new LinkedList();
		    BufferedReader input;
		    try{
			input = new BufferedReader((new FileReader(indexFile)));
			String line;   LinkedList<String> operandFile=new LinkedList<String>();
			while((line=input.readLine())!=null){
				StringTokenizer termTokenizer=new StringTokenizer(line);
				String wordInIndex=termTokenizer.nextToken();
					if(word.equals(wordInIndex)){
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
