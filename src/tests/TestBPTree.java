package tests;

import data.BPTree;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

class TestBPTree {
	
	static BPTree<Integer, String> bpt;
	
	@BeforeEach
	void setup(){
		this.bpt = new BPTree<Integer, String>(3);
	}
	
	@AfterEach
	void teardown(){
		this.bpt = null;
	}
	
	
	@Test
	void testFirstInsert(){
		print("\ntestFirstInsert...");
		bpt.insert(1, "YAY");
		assertEquals("{[1]}\n", bpt.toString());
		print("passed!");
		
	}
	
	@Test
	void testLeafRootSplit(){
		print("\ntestLeafSplit...");
		for(int i=0; i<4; i++){
			bpt.insert(i,  "Foo");
		}
		assertEquals("{[2]}\n{[0, 1], [2, 3]}\n", bpt.toString());
		printTree();
		print("passed!");
	}
	
	@Test
	void testInternalRootSplit(){
		print("\ntestInternalRootSplit...");
		for(int i=0; i<10; i++){
			bpt.insert(i, "Bar");
		}
		assertEquals("{[4]}\n{[2], [6, 8]}\n{[0, 1], [2, 3]}, {[4, 5], [6, 7], [8, 9]}\n", bpt.toString());
		print("passed!");
	}
	
	/**
	 * No assertions, to test must inspect visually
	 */
	@Test
	void testNonRootSplits(){
		print("\ntestNonRootSplits...");
		for(int i=0; i<50; i++){
			bpt.insert(i,  "");
//			printTree();
		}
		printTree();
		print("didn't crash!");
		
	}
	
	/**
	 * No assertions, to test must inspect visually
	 */
	 @Test
	 void testDuplicates(){
		 print("\ntestDuplicates...");
		 for(int i=0; i<10; i++){
			 bpt.insert(i, "");
		 }
		 for(int i=0; i<5; i++){
			 bpt.insert(2, "a");
		 }
		 for(int i=10; i<20; i++){
			 bpt.insert(i, "");
		 }
		 printTree();
		 print("didn't crash!");
	 }
		 
	 

	@Test
	void testSimpleRangeSearch() {
		print("\ntestSimpleRangeSearch");
		for(int i=0; i<20; i++){
			bpt.insert(i, Integer.toString(i));
		}
		List<String> vals_1 = bpt.rangeSearch(10, ">=");
		List<String> vals_2 = bpt.rangeSearch(10, "<=");
		List<String> vals_3 = bpt.rangeSearch(10, "==");
		List<String> vals_4 = bpt.rangeSearch(10, "foo");
		for(int i=0; i<10; i++){
			assertEquals(Integer.toString(i+10), vals_1.get(i));
		}
		for(int i=0; i<11; i++){
			assertEquals(Integer.toString(10-i), vals_2.get(i));
		}
		assertEquals(Integer.toString(10), vals_3.get(0));
		assertEquals(0, vals_4.size());
	}

	private static void printTree(){
		print(String.format("\n%s", bpt.toString()));
	}
	private static void print(String s){
		System.out.print(s);
	}

}
