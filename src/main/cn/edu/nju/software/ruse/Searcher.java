package cn.edu.nju.software.ruse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import cn.edu.nju.software.ruse.Tester.Result;

public class Searcher {
	private String indexFile="indexFile.txt";
	private String timeFile;
	private String sizeFile;
	private String sizeRecord;
	private String timeRecord;
   public Searcher(){}
   public Searcher(String indexDir){
	   indexFile=indexDir+"/index.txt"; 
	   timeFile=indexDir+"/timeIndex.txt";
	   sizeFile=indexDir+"/sizeIndex.txt";
	   sizeRecord=indexDir+"/sizeRecord.txt";
	   timeRecord=indexDir+"/timeRecord.txt";
   }
   
 /* This method get a query string,analyse it and search a index file
  * to get a LinkedList that contains the file name satisfying the query
  * and finally return the LinkedList as a result.
  */   
   public LinkedList<Result> query(String exp,int order) {
	   exp=normalize(exp);
	   StringTokenizer tokenizer=new StringTokenizer(exp," ()",true);
	   Stack<LinkedList<String>> operandStack=new Stack<LinkedList<String>>();
	   Stack<String> operatorStack=new Stack<String>();
	   ElementQuery query; 
	   while(tokenizer.hasMoreTokens()){ 
        String token=tokenizer.nextToken();
       
           if(token.equals(" ")){
        	 continue;     
           }
           else if(token.equals("(")){
			   operatorStack.push(token);	   
		   }
		   else if(tokenIsOperator(token)){
			   operatorStack.push(token);	   
		   }
		   else if(token.equals(")")){
			  String operator=(String)operatorStack.pop(); 
			  Operation operation=new Operation();
			  
				operandStack=operation.calculate(operandStack,operator,indexFile);
			
			  operatorStack.pop();
		   }
		   else if(token.length()>9&&token.substring(0, 9).equals("fileName:")){
			   query=new NameQuery(timeRecord,token);
			   operandStack.push(query.searchQuery());
		   }
		   else if(token.length()>8&&token.substring(0, 8).equals("modTime:")){
			   query=new TimeQuery(timeFile,token);
			   operandStack.push(query.searchQuery());
		   }
           else if(token.length()>9&&token.substring(0, 9).equals("fileSize:")){
        	   query=new SizeQuery(sizeFile,token);
			   operandStack.push(query.searchQuery());
		   }
     //if we get a term, create the corresponding file list from the index
           else if(token.charAt(0)=='"'){
        	   ProximityQuery proxiQuery=new ProximityQuery(indexFile,token);
        	   operandStack.push(proxiQuery.searchQuery());
           }
		   else{
			   query=new ContentsQuery(indexFile,token);
			   operandStack.push(query.searchQuery());
		   }
	 	  
	    } 	
	   
	   if(!operatorStack.empty()){
	    String lastOperator=(String) operatorStack.pop();
	    if(tokenIsOperator(lastOperator)){
	    	  Operation operationLast=new Operation();
			  operandStack=operationLast.calculate(operandStack,lastOperator,indexFile);
	    }
	   }	   
	    //the final result is stored in operandStack
	   LinkedList finalResult=(LinkedList)operandStack.pop();
	   LinkedList realFinal=finalResult;
	    realFinal=toBeSingle(finalResult);  
	  if(order==1)
	    realFinal=rank(realFinal,sizeRecord);
	  else if(order==2)
	    realFinal=rank(realFinal,timeRecord);
	 LinkedList<Result> result=new LinkedList<Result>();
	 for(int i=0;i<realFinal.size();i++){
		 Tester.Result fullNode=new Tester.Result();
		 fullNode.fileName=(String) realFinal.get(i);
		 fullNode.fileSize=Integer.parseInt(getOtherInfo((String)realFinal.get(i),sizeRecord));
		 fullNode.modTime=getOtherInfo((String)realFinal.get(i),timeRecord);
		 result.add(fullNode);
	 }
	      return result;
   }
   
  String getOtherInfo(String fileName,String indexDir){
	  String otherInfo="";
	   try{
			 BufferedReader input = new BufferedReader((new FileReader(indexDir)));
				String line;  String file;
				while((line=input.readLine())!=null){
					StringTokenizer tokenizer=new StringTokenizer(line);
					file=tokenizer.nextToken();	
					if(fileName.equals(file)){
						otherInfo=tokenizer.nextToken();
						break;
					}
				}}
			   catch(FileNotFoundException e){
				   e.printStackTrace();
			   }
			   catch(IOException e){
				   e.printStackTrace();
			   }
	    return otherInfo;
   }
   
   /* this method make sure there is no duplicate elements in a list, every
    * element in the list is single. 
    */
    public LinkedList toBeSingle(LinkedList list){
    	for(int i=0;i<list.size()&&i>=0;i++){
    		for(int j=i+1;j<list.size();j++){
    			if(list.get(i).equals(list.get(j))){
    				list.remove(j);
    				j--;
    			}
             }		
    	}
    	  return list;
    }
   
   /* this method judge whether a string is an operator string.
    */
   public boolean tokenIsOperator(String token){
	   if(token.equalsIgnoreCase("and")||token.equalsIgnoreCase("or")
			   ||token.equalsIgnoreCase("not"))  
	   return true;
	   else
		   return false;
   }
   
 /* if pass indexDir=timeRecord, you rank result by time. 
    else if pass indexDir=sizeRecord, you rank result by size.   */
   public LinkedList rank(LinkedList list,String indexDir){
	   long size[]=new long[list.size()];
	   for(int i=0;i<list.size();i++){
		   try{
		 BufferedReader input = new BufferedReader((new FileReader(indexDir)));
			String line;  String file;
			long filesize;
			while((line=input.readLine())!=null){
				StringTokenizer tokenizer=new StringTokenizer(line);
				file=tokenizer.nextToken();	
				if(list.get(i).equals(file)){
					filesize=Long.parseLong(tokenizer.nextToken());
					size[i]=filesize;
				}
			}}
		   catch(FileNotFoundException e){
			   e.printStackTrace();
		   }
		   catch(IOException e){
			   e.printStackTrace();
		   }
	   }
	   for(int i=1;i<list.size();i++){
		   long temp=size[i];  int j=i; String fileTemp=(String)list.get(i);
		   while(j>0&&temp>size[j-1]){
			   size[j]=size[j-1];
			   list.remove(j);
			   list.add(j, list.get(j-1));
			   j--;
		   }
		   size[j]=temp;
		   list.remove(j);
		   list.add(j, fileTemp);		   
	   }
	   return list;
   }
     public String normalize(String str){
    	  int count=0;
    	  char[] result=str.toCharArray();
    	  for(int i=0;i<str.length();i++){
    		  if(str.charAt(i)=='"'){
    			  count++;
    		  }
    		  if(count%2==1&&str.charAt(i)==' '){
    			  result[i]='+';
    		  }
    	  }
    	  return String.valueOf(result);
      }

     
     
}
