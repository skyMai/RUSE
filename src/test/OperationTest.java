package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

import cn.edu.nju.software.ruse.And;
import cn.edu.nju.software.ruse.Operation;
import junit.framework.TestCase;

public class OperationTest extends TestCase {
	    LinkedList<String> list1=new LinkedList<String>();
	    LinkedList<String> list2=new LinkedList<String>();
	    LinkedList<String> list3=new LinkedList<String>();
	    Stack<LinkedList<String>> stack1=new Stack<LinkedList<String>>();
	    Stack<LinkedList<String>> stack2=new Stack<LinkedList<String>>();
	    Operation op;
	protected void setUp() throws Exception {
		super.setUp();
		list1.add("apple");
		list1.add("pear");
		list2.add("pear");
		list3.add("pear");
		stack1.push(list2);
		stack1.push(list1);
		stack2.push(list3);
		op=new Operation();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCalculate() throws FileNotFoundException, IOException {
		assertEquals(stack2,op.calculate(stack1,"and","file.txt"));
	}

}
