package cn.edu.nju.software.ruse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ProximityQuery {
	String dir;
	String query;
	public ProximityQuery(String dir, String query) {
		this.dir=dir;
		this.query=query;
	}
	LinkedList<String> searchQuery(){
		StringTokenizer tokenizer=new StringTokenizer(query,"\"~");
		String wholeTerm=tokenizer.nextToken();
		if(hasSign()){
		int d=Integer.parseInt(tokenizer.nextToken());
		  return searchProxi(d,wholeTerm);
		}
		else{
			return searchProxi(0,wholeTerm);
		}
	}
	
	LinkedList<String> searchProxi(int d,String wholeTerm){
		LinkedList termlist=new LinkedList();
		LinkedList filelist=new LinkedList();
		ContentsQuery query=new ContentsQuery(dir,wholeTerm);
		StringTokenizer tokenizer=new StringTokenizer(wholeTerm,"+");
		while(tokenizer.hasMoreTokens()){
			String temp=tokenizer.nextToken();
			termlist.add(temp);	
			filelist.add(query.searchTermQuery(temp));
		}
		Stack stack=new Stack();
		for(int i=0;i<filelist.size();i++){
			stack.push(filelist.get(i));
		}
		And and=new And();
		for(int i=0;i<termlist.size()-1;i++){
			stack=and.operation(stack, "");
		}
		LinkedList andResult=(LinkedList) stack.pop();
		LinkedList[] hashNodeList=new LinkedList[andResult.size()];
		for(int i=0;i<andResult.size();i++){
			hashNodeList[i]=new LinkedList();
		}
		for(int i=0;i<termlist.size();i++){
			  try{
					BufferedReader input = new BufferedReader((new FileReader(dir)));
					String line;   
					while((line=input.readLine())!=null){
						StringTokenizer termTokenizer=new StringTokenizer(line," ()");
						String wordInIndex=termTokenizer.nextToken();
							if(((String) termlist.get(i)).equals(wordInIndex)){  //ignoreCase有需求疑问
								 while(termTokenizer.hasMoreTokens()){
									 String str=termTokenizer.nextToken();
									
										if(andResult.contains(str)){
											int filepos=andResult.indexOf(str);
											String posStr=termTokenizer.nextToken();
											LinkedList pos=new LinkedList();
											StringTokenizer posToken=new StringTokenizer(posStr,",");
											while(posToken.hasMoreTokens()){
												pos.add(Integer.parseInt(posToken.nextToken()));
											}
											hashNodeList[filepos].add(pos);
										}		
										else{
											termTokenizer.nextToken();
										}
			            		   }
							}				     
					}		
		    }
				    catch(FileNotFoundException e){
						   e.printStackTrace();
					   }
					   catch(IOException e){
						   e.printStackTrace();
					   }
		}
		LinkedList result=new LinkedList();
		for(int i=0;i<andResult.size();i++){
			if(posSatisfied(-1,hashNodeList[i],d)){
			     result.add(andResult.get(i));
			} 
		}
		 
		return result;
	}
	
	boolean hasSign(){
		boolean result=false;
		for(int i=0;i<query.length();i++){
			
			if(query.charAt(i)=='~')
		    	result=true;
			
		}
		return result;
	}
	
	boolean posSatisfied(int prepos,LinkedList list,int d){
		if(prepos<0){
			LinkedList first=(LinkedList)list.get(0);
			boolean hasFound=false;
			LinkedList sublist=new LinkedList();
		   if(list.size()>2){
			   for(int j=1;j<list.size();j++){
				   sublist.add(list.get(j));
			   }   
		   }
		   else{
			   sublist.add(((LinkedList)list.get(1)));
		   }
		   for(int j=0;j<first.size();j++){
			   int tempPre=((Integer)(first.get(j))).intValue();
			   if(posSatisfied(tempPre,sublist,d)){
				   hasFound=true;
			   break;
			   }
		   }
			   if(hasFound)
				   return true;
			   else 
				   return false;
		}
		else{
			if(list.size()==1){
				LinkedList last=(LinkedList)(list.get(0));
				int i=0;
				boolean hasFound=false;
				for( ;i<last.size();i++){
					if((Integer)(last.get(i))-prepos-1<=d&&(Integer)(last.get(i))-prepos-1>=0){
						hasFound=true;
						break;
					}
				}
				if(hasFound){
					return true;
				}
				else
					return false;
			}
			else{
				boolean hasFound=false;
				LinkedList sublist=new LinkedList();
				for(int j=1;j<list.size();j++){
					   sublist.add(list.get(j));
				   }   
				LinkedList temp=((LinkedList)(list.get(0)));
				for(int j=0;j<((LinkedList)(list.get(0))).size();j++){
					int newPre=((Integer)temp.get(j)).intValue();
					if(newPre-prepos-1>=0&&newPre-prepos-1<=d&&posSatisfied(newPre,sublist,d)){
						hasFound=true;
						break;
					}		
				}
				if(hasFound)
					return true;
				else
					return false;
			}		
		}	
	}
	
	
	
}  
