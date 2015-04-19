package cn.edu.nju.software.ruse;

import java.util.Hashtable;
import java.util.LinkedList;

public class ComplexHasher {
	public LinkedList complexHashWord(int pos,String word, Hashtable hashtable,String filename,LinkedList keyList) {

		   if(hashtable.containsKey(word)){
			   boolean containFile=true;
			   int i=0;
			   while(i<((LinkedList)(hashtable.get(word))).size()){

				  if(((HashNode)((LinkedList)(hashtable.get(word))).get(i)).filename.equals(filename)){
					  if(!((HashNode)((LinkedList)(hashtable.get(word))).get(i)).position.contains(pos))
						  ((HashNode)((LinkedList)(hashtable.get(word))).get(i)).position.add(pos);	
					  break;
				  } 
				  i++;
			   }
			   if(i==((LinkedList)(hashtable.get(word))).size()){
				   containFile=false;
			   }
			   if(!containFile){
				   LinkedList poslist=new LinkedList();
				   poslist.add(pos);
			       HashNode node=new HashNode(filename,poslist); 
			       ((LinkedList)(hashtable.get(word))).add(node);
			   }
			   }
		  
			   else{
				 LinkedList poslist=new LinkedList();
				 poslist.add(pos);
				 HashNode node=new HashNode(filename,poslist);
				 LinkedList nodeList=new LinkedList();
				 nodeList.add(node);
				 hashtable.put(word, nodeList);
				 keyList.add(word);
			   }
		   
		   return keyList;
	}
}
