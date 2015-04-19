package cn.edu.nju.software.ruse;

import java.util.Stack;

public class Operation {
	private Operator generalOp;
	/* Following method get a operandStack to obtain operand and analyse the
	 * type of an operator and invoke corresponding class to do and operation.
	 * The result will be pushed into the operand stack and the stack will be returned. 
	 */
    public Stack calculate(Stack operandStack,String operator,String indexFile){
    	if(operator.equalsIgnoreCase("and")){
    		generalOp=new And();	
    	}
    	else if(operator.equalsIgnoreCase("or")){
    		generalOp=new Or();
    		
    	}
    	else if(operator.equalsIgnoreCase("not")){
    		generalOp=new Not();
    		
    	}
     	operandStack=generalOp.operation(operandStack,indexFile);
    	return operandStack;
    }
}
