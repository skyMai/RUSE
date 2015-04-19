package test;

import cn.edu.nju.software.ruse.SizeQuery;
import junit.framework.TestCase;

public class SizeQueryTest extends TestCase {
    SizeQuery a=new SizeQuery("","");
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddZero() {
		assertEquals("004004",a.addZero("4004"));
	}

}
