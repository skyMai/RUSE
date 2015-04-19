package cn.edu.nju.software.ruse;

import java.util.*;

public class Hasher {
//hashWord get a word and hash the term into a hashtable	
public LinkedList hashWord(String word, Hashtable hashtable,String filename,LinkedList keyList) {

	   if(hashtable.containsKey(word)){
		   
			  if(!((LinkedList)(hashtable.get(word))).contains(filename)){
				  ((LinkedList)(hashtable.get(word))).add(filename);
			  }   
		   }
		   else{
			 keyList.add(word);
			 LinkedList newFileList=new LinkedList();  
			 newFileList.add(filename);
			 hashtable.put(word, newFileList);
			   
		   }	 
	   return keyList;
}




}
