package test;

import java.util.*;

import cn.edu.nju.software.ruse.Or;
import junit.framework.TestCase;

public class OrTest extends TestCase {
    LinkedList<String> list1=new LinkedList<String>();
    LinkedList<String> list2=new LinkedList<String>();
    LinkedList<String> list3=new LinkedList<String>();
    Stack<LinkedList<String>> stack1=new Stack<LinkedList<String>>();
    Stack<LinkedList<String>> stack2=new Stack<LinkedList<String>>();
    Or or=new Or();
	protected void setUp() throws Exception {
		super.setUp();
		list1.add("apple");
		list1.add("pear");
		list2.add("pear");
		list3.add("apple");
		list3.add("pear");
		stack1.push(list2);
		stack1.push(list1);
		stack2.push(list3);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testOperation() {
		assertEquals(stack2,or.operation(stack1,"file.txt"));
	}

}
