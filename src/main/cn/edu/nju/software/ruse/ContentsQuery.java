package cn.edu.nju.software.ruse;

import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class ContentsQuery extends ElementQuery{
	public ContentsQuery(String dir, String term) {
		super(dir, term);
	}
	 boolean hasPrefix(){
		 if(term.length()>13&&term.substring(0, 13).equalsIgnoreCase("fileContents:"))
			 return true;
		 else 
			 return false;
		 
	 }
	 
	   LinkedList searchSoloQuery(){
		   StringTokenizer tokenizerl=new StringTokenizer(term,":");
		   tokenizerl.nextToken();
		   String word=tokenizerl.nextToken();
		   return searchTermQuery(word);
	   }
	   
	   LinkedList searchTermQuery(String str){
		   LinkedList result=new LinkedList();
		    BufferedReader input;
		    try{
			input = new BufferedReader((new FileReader(indexFile)));
			String line;   LinkedList<String> operandFile=new LinkedList<String>();
			while((line=input.readLine())!=null){
				StringTokenizer termTokenizer=new StringTokenizer(line," ()");
				String wordInIndex=termTokenizer.nextToken();
					if(str.equalsIgnoreCase(wordInIndex)){
						int i=1;
						 while(termTokenizer.hasMoreTokens()){
							if(i%2==1)
							operandFile.add(termTokenizer.nextToken());
							else
								termTokenizer.nextToken();
							i++;
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
