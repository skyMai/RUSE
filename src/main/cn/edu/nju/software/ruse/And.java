package cn.edu.nju.software.ruse;

import java.util.*;

public class And extends Operator{
/*This method get a operandStack parameter from outside.It pop the two top
 * elements from the stack and use them as the operand in the "and" operation.
 * Mention that each element stored in the stack is a string LinkedList of
 * filenames.After getting a result list,the method push it to the operand 
 * stack and return the new stack
 */
	public Stack operation(Stack operandStack,String indexFile) {
		Object a=operandStack.pop();
		Object b=operandStack.pop();
		LinkedList first=(LinkedList)a;
		LinkedList second=(LinkedList)b;
		LinkedList result=new LinkedList();
		if(first.size()>0&&second.size()>0){	
		for(int i=0;i<first.size();i++){
			String str=(String)first.get(i);
			 if(second.contains(str)){
					result.push(str);	
			 }		
		}
		}
		operandStack.push(result);
		return operandStack;
	}
}
