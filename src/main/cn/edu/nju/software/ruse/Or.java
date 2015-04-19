package cn.edu.nju.software.ruse;

import java.util.*;

public class Or extends Operator{
	/*This method get a operandStack parameter from outside.It pop the two top
	 * elements from the stack and use them as the operand in the "or" operation.
	 * Mention that each element stored in the stack is a string LinkedList of
	 * filenames.After getting a result list,the method push it to the operand 
	 * stack and return the new stack
	 */
	public Stack operation(Stack operandStack,String indexFile) {
		Object a=operandStack.pop();
		Object b=operandStack.pop();
		LinkedList<String> first=(LinkedList)a;
		LinkedList second=(LinkedList)b;
		if(first.size()>0&&second.size()>0){
		
		for(int i=0;i<second.size();i++){
			String str=(String)second.get(i);
			if(!first.contains(str))
				first.add(str);
		}
		
		operandStack.push(first);
		
		}
		else if(first.size()==0&&second.size()!=0){
			operandStack.push(b);	
		}
		else if(second.size()==0&&first.size()!=0){
			operandStack.push(a);	
		}
		else {
			operandStack.push(a);
		}
		return operandStack;
	}
}
