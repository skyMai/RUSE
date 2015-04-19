package test;

import java.util.*;

import cn.edu.nju.software.ruse.Searcher;

import junit.framework.TestCase;

public class SearcherTest extends TestCase {
     LinkedList<String> list1=new LinkedList<String>();
     LinkedList<String> list2=new LinkedList<String>();
     Searcher searcher=new Searcher();
	protected void setUp() throws Exception {
		super.setUp();
		list1.add("apple");
		list1.add("apple");
		list1.add("orange");
		list1.add("rabbit");
		list2.add("apple");
		list2.add("orange");
		list2.add("rabbit");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testToBeSingle() {
		assertEquals(list2,searcher.toBeSingle(list1));
	}

	public void testTokenIsOperator() {
		assertEquals(false,searcher.tokenIsOperator("apple"));
	}

	public void testnormalize(){
		assertEquals("\"ipod+and+nano\"",searcher.normalize("\"ipod and nano\""));
	}
	
}
