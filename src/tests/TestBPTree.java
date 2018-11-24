package tests;

import data.BPTree;
import static org.junit.jupiter.api.Assertions.*; 
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
			printTree();
		}
		print("passed!");
	}
		
		

//	@Test
	void testRangeSearch() {
		fail("Not yet implemented");
	}

//	@Test
	void testToString() {
		fail("Not yet implemented");
	}
	
	private static void printTree(){
		print(String.format("\n%s", bpt.toString()));
	}
	private static void print(String s){
		System.out.print(s);
	}

}
