package data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Implementation of a B+ tree to allow efficient access to
 * many different indexes of a large data set. 
 * BPTree objects are created for each type of index
 * needed by the program.  BPTrees provide an efficient
 * range search as compared to other types of data structures
 * due to the ability to perform log_m N lookups and
 * linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food item
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {
    
    // Root of the tree
    private Node root;
    
    // Branching factor is the number of children nodes 
    // for internal nodes of the tree
    private int branchingFactor;
    
    
    /**
     * Default constructor, branchingFactor = 5;
     */
    public BPTree() {
    	this.branchingFactor = 5;
    }
    /**
     * Public constructor
     * 
     * @param branchingFactor 
     */
    public BPTree(int branchingFactor) {
        if (branchingFactor <= 2) {
            throw new IllegalArgumentException(
               "Illegal branching factor: " + branchingFactor);
        }
        this.branchingFactor = branchingFactor;
    }
    
    /*
     * Searches for the location of the value
     * @return pointer to the leaf node containing the value
     * If value doesn't exist, returns pointer to the node which /should/ contain the value
     */
    /*
     * (non-Javadoc)
     * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
     */
    @Override
    public void insert(K key, V value) {
    	if(this.root == null){
    		this.root = new LeafNode();
    	}
		this.root.insert(key,  value);
		if(this.root.isOverflow()){
			Node newChild = root.split();
			List<Node> children = new ArrayList<Node>();
			children.add(root);
			children.add(newChild);
			this.root = new InternalNode(children);
		}
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
     */
    @Override
    public List<V> rangeSearch(K key, String comparator) {
        if (!comparator.contentEquals(">=") && 
            !comparator.contentEquals("==") && 
            !comparator.contentEquals("<=") )
            return new ArrayList<V>();
        // TODO : Complete
        throw new RuntimeException("NOT YET IMPLEMENTED");
    }
    
    /*
     * prints out the tree, for debuggin
     */
     private void printTree(){
    	 System.out.println(this.toString());
     }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        Queue<List<Node>> queue = new LinkedList<List<Node>>();
        queue.add(Arrays.asList(root));
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
            while (!queue.isEmpty()) {
                List<Node> nodes = queue.remove();
                sb.append('{');
                Iterator<Node> it = nodes.iterator();
                while (it.hasNext()) {
                    Node node = it.next();
                    sb.append(node.toString());
                    if (it.hasNext())
                        sb.append(", ");
                    if (node instanceof BPTree.InternalNode)
                        nextQueue.add(((InternalNode) node).children);
                }
                sb.append('}');
                if (!queue.isEmpty())
                    sb.append(", ");
                else {
                    sb.append('\n');
                }
            }
            queue = nextQueue;
        }
        return sb.toString();
    }
    
    /**
     * This abstract class represents any type of node in the tree
     * This class is a super class of the LeafNode and InternalNode types.
     * 
     * @author sapan
     */
    private abstract class Node {
        
        // List of keys
        List<K> keys;
        
        /**
         * Package constructor
         */
        Node() {
            this.keys = new ArrayList<K>();
        }
        
        /**
         * Inserts key and value in the appropriate leaf node 
         * and balances the tree if required by splitting
         *  
         * @param key
         * @param value
         */
        abstract void insert(K key, V value);

        /**
         * Gets the first leaf key of the tree
         * 
         * @return key
         */
        abstract K getFirstLeafKey();
        
        /**
         * Gets the new sibling created after splitting the node
         * 
         * @return Node
         */
        abstract Node split();
        
        /*
         * (non-Javadoc)
         * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
         */
        abstract List<V> rangeSearch(K key, String comparator);

        /**
         * 
         * @return boolean
         */
        abstract boolean isOverflow();
        
        public String toString() {
            return keys.toString();
        }
    
    } // End of abstract class Node
    
    /**
     * This class represents an internal node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations
     * required for internal (non-leaf) nodes.
     * 
     * @author sapan
     */
    private class InternalNode extends Node {

        // List of children nodes
        List<Node> children;
        
        /**
         * Package constructor
         */
        private InternalNode() {
            super();
            this.children = new LinkedList<Node>();
        }
        
        /**
         * constructs an InternalNode with set children
         * @param children
         */
        private InternalNode(List<Node> children){
        	this();
        	this.children = children;
        	for(Node child : this.children.subList(1, this.children.size())){
        		this.keys.add(child.getFirstLeafKey());
        	}
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        //TODO: make more efficient
        K getFirstLeafKey() {
        	return this.children.get(0).getFirstLeafKey();
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
        	return this.children.size() > branchingFactor;
        }
        
        /**
         * Inserts k/v pair into child at index i
         * If child doesn't exist, creates it. 
         */
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
         */
        void insert(K key, V value) {
        	//searches for correct child
        	int index = 0;
        	while(index<this.keys.size() && key.compareTo(keys.get(index)) >= 0){
        		index++;
        	}
        	
        	Node target = children.get(index);
        	//inserts
        	target.insert(key,  value);
        	//if exceeds branching factor, split
        	if(target.isOverflow()){
        		Node newChild = target.split();
        		if(index+1 < this.children.size()){
					this.children.add(index+1, newChild);
					this.keys.add(index+1, newChild.getFirstLeafKey());
        		} else{
					this.children.add(newChild);
					this.keys.add(newChild.getFirstLeafKey());
        		}
        		
        	}
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
        	int mid = this.children.size()/2;
//        	Node newChild = new InternalNode(this.children.subList(mid, this.children.size()));
//        	this.children = this.children.subList(0, mid);
//        	this.keys = this.keys.subList(0, mid-1);
        	Object[] children = this.children.toArray();
        	List<Node> newChildren = new ArrayList<Node>();
        	for(int i=mid; i<this.children.size(); i++){
        		newChildren.add((Node)children[i]);
        	}
        	Node newChild = new InternalNode(newChildren);
        	
        	this.children.subList(mid, this.children.size()).clear();
        	this.keys.subList(mid-1, this.keys.size()).clear();
        	
        	return newChild;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
         */
        List<V> rangeSearch(K key, String comparator) {
            // TODO : Complete
			throw new RuntimeException("NOT YET IMPLEMENTED");
        }
    
    } // End of class InternalNode
    
    
    /**
     * This class represents a leaf node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations that
     * required for leaf nodes.
     * 
     * @author sapan
     */
    private class LeafNode extends Node {
        
        // List of values
        // TODO: figure out how to ensure values remain linked to keys
        List<V> values;
        
        // Reference to the next leaf node
        LeafNode next;
        
        // Reference to the previous leaf node
        LeafNode previous;
        
        /**
         * Package constructor
         */
        private LeafNode() {
            super();
            this.values = new LinkedList<V>();
        }
        
        private LeafNode(List<K> keys, List<V> values){
        	this.keys = keys;
        	this.values = values;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
        	return this.keys.get(0);
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
        	return this.keys.size()>branchingFactor;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(Comparable, Object)
         */
        void insert(K key, V value) {
        	int count = 0;
        	while(count<this.keys.size()//loops through each value until
        		  && key.compareTo(this.keys.get(count)) > 0){// key is greater than all preceding values
        		count++;
        	}//count is at the proper index
        	this.keys.add(count, key);
        	this.values.add(count, value);
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
        	int mid = this.keys.size()/2;
        	//Due to how List.subList works, changing newKeys[] or newVals[]
        	//Causes keys[] and values[] to change as well
        	List<K> newKeys = this.keys.subList(mid, this.keys.size());
        	List<V> newVals = this.values.subList(mid, this.values.size());
        	Node newChild = new LeafNode(new ArrayList<K>(newKeys), new ArrayList<V>(newVals));
        	newKeys.clear();
        	newVals.clear();
        	return newChild;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
            // TODO : Complete
            throw new RuntimeException("NOT YET IMPLEMENTED");
        }
        
    } // End of class LeafNode
    
    
    /**
     * Contains a basic test scenario for a BPTree instance.
     * It shows a simple example of the use of this class
     * and its related types.
     * 
     * @param args
     */
//    public static void main(String[] args) {
//        // create empty BPTree with branching factor of 3
//        BPTree<Double, Double> bpTree = new BPTree<>(3);
//
//        // create a pseudo random number generator
//        Random rnd1 = new Random();
//
//        // some value to add to the BPTree
//        Double[] dd = {0.0d, 0.5d, 0.2d, 0.8d};
//
//        // build an ArrayList of those value and add to BPTree also
//        // allows for comparing the contents of the ArrayList 
//        // against the contents and functionality of the BPTree
//        // does not ensure BPTree is implemented correctly
//        // just that it functions as a data structure with
//        // insert, rangeSearch, and toString() working.
//        List<Double> list = new ArrayList<>();
//        for (int i = 0; i < 400; i++) {
//            Double j = dd[rnd1.nextInt(4)];
//            list.add(j);
//            bpTree.insert(j, j);
//            System.out.println("\n\nTree structure:\n" + bpTree.toString());
//        }
//        List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
//        System.out.println("Filtered values: " + filteredValues.toString());
//    }

} // End of class BPTree
