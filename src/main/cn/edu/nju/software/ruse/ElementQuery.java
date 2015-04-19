package cn.edu.nju.software.ruse;

import java.util.LinkedList;

public abstract class ElementQuery {
	String indexFile;
	String term;
	public ElementQuery(String dir,String term){
		indexFile=dir;
		this.term=term;
	}
	
   final LinkedList<String> searchQuery(){
    	if(hasPrefix()){
    		if(isRangeQuery()){
    			return searchRangeQuery();
    		}
    		else
    			return searchSoloQuery();
    	}
    	 else{
    	  return searchTermQuery(term);  	
    	    }
    }
   
   abstract boolean hasPrefix() ;
   //This is a hook 
   boolean isRangeQuery(){
	   return false;
   }
   //This is a hook 
   LinkedList searchRangeQuery(){
	   return new LinkedList();
   }
   
   //for all four kinds of queries which has prefix and are not rangeQueries
   abstract LinkedList searchSoloQuery();
   LinkedList searchTermQuery(String str){
	   return new LinkedList();
   }
	   
  
}
