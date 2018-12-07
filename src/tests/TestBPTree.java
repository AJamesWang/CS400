package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import application.BPTree;
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
//	@Test
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
//	 @Test
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
		print("\ntestSimpleRangeSearch...");
		for(int i=0; i<20; i++){
			bpt.insert(i, Integer.toString(i));
		}
		List<String> vals_1 = bpt.rangeSearch(10, ">=");
		for(int i=0; i<10; i++){
			assertEquals(Integer.toString(i+10), vals_1.get(i));
		}
		
		List<String> vals_2 = bpt.rangeSearch(10, "<=");
		for(int i=0; i<11; i++){
			assertEquals(Integer.toString(10-i), vals_2.get(i));
		}
		
		List<String> vals_3 = bpt.rangeSearch(10, "==");
		assertEquals(Integer.toString(10), vals_3.get(0));
		
		List<String> vals_4 = bpt.rangeSearch(10, "foo");
		assertEquals(0, vals_4.size());
		
		List<String> vals_5 = bpt.rangeSearch(0, ">=");
		for(int i=0; i<20; i++){
			assertEquals(Integer.toString(i), vals_5.get(i));
		}
		List<String> vals_6 = bpt.rangeSearch(100, "<=");
		for(int i=0; i<20; i++){
			assertEquals(Integer.toString(19-i), vals_6.get(i));
		}
		print("passed!");
	}
	
	@Test
	void testDuplicateRangeSearch() {
		print("\ntestDuplicateRangeSearch...");
		bpt.insert(1, "1");
		for(int i=0; i<3; i++) bpt.insert(2, "2");
		for(int i=0; i<5; i++) bpt.insert(3, "3");
		bpt.insert(4, "4");
		//[1, 2] [2, 2] [3, 3] [3, 3] [3, 4]
		//		^	   ^             ^		-point checked forwards and backwards
		List<String> vals_0 = bpt.rangeSearch(2, "<=");
		assertArrayEquals(new String[]{"2", "2", "2", "1"}, vals_0.toArray());
		List<String> vals_1 = bpt.rangeSearch(2, ">=");
		assertArrayEquals(new String[]{"2", "2", "2", "3", "3", "3", "3", "3", "4"}, vals_1.toArray());
		List<String> vals_2 = bpt.rangeSearch(2, "==");
		System.out.println(vals_2);
		assertArrayEquals(new String[]{"2", "2", "2"}, vals_2.toArray());
		
		List<String> vals_3 = bpt.rangeSearch(3, "<=");
		assertArrayEquals(new String[]{"3", "3", "3", "3", "3", "2", "2", "2", "1"}, vals_3.toArray());
		List<String> vals_4 = bpt.rangeSearch(3, ">=");
		assertArrayEquals(new String[]{"3", "3", "3", "3", "3", "4"}, vals_4.toArray());
		List<String> vals_5 = bpt.rangeSearch(3, "==");
		assertArrayEquals(new String[]{"3", "3", "3", "3", "3"}, vals_5.toArray());
		print("passed!");
	}

	/*
	 * Figuring out what's going on with multiple inserts
	 * {[0.0, 0.0, 0.2], [0.2, 0.2]}, {[0.2, 0.2, 0.2], [0.5, 0.5]}, {[0.5, 0.5], [0.8, 0.8], [0.8, 0.8, 0.8]}
	 * insert 0
	 */
	 @Test
	 void test1(){
		 Double[] in = new Double[]{.0, .3, .3, .2, .0, .0};
		 BPTree<Double, String> tree = new BPTree<Double, String>(3);
		 for(Double d:in){
			 tree.insert(d, "");
			print(String.format("\n%s", tree.toString()));
		 }
	 }

	private static void printTree(){
		print(String.format("\n%s", bpt.toString()));
	}
	private static void print(String s){
		System.out.print(s);
	}

}
