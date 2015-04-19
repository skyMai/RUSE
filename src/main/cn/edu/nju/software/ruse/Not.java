package cn.edu.nju.software.ruse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Not extends Operator{
	
	/*This method get a operandStack parameter from outside.It pop the top
	 * elements from the stack and use it as the operand in the "not" operation.
	 * Mention that each element stored in the stack is a string LinkedList of
	 * filenames.After getting a result list,the method push it to the operand 
	 * stack and return the new stack
	 */
	public Stack operation(Stack operandStack,String indexFile) {
		Object operand=operandStack.pop();
		
		try{
		BufferedReader input=new BufferedReader((new FileReader(indexFile)));
		String line;
		LinkedList wordList=new LinkedList();
//add all file names to the list wordList
		while((line=input.readLine())!=null){
			   StringTokenizer tokenizer=new StringTokenizer(line);
			   tokenizer.nextToken();
			   while(tokenizer.hasMoreTokens()){
				   String filename=tokenizer.nextToken();
				  if(!wordList.contains(filename))
				    wordList.add(filename);		 
			   }		
			}
	
//delete the file names in the discardList
		LinkedList discardList=(LinkedList)operand;
		if(discardList.size()>0){		
			for(int i=0;i<discardList.size();i++){
				String element=(String)discardList.get(i);
				if(wordList.contains(element)){
					wordList.remove(element);				
				}
			}
		}
		
	     operandStack.push(wordList);		
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return operandStack;
	}
}
