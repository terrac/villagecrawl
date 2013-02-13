/**
 * Rise to the Stars
 * @author Mindgamer
 */
package gwt.client.astar.pathfinder.openlist.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import gwt.client.astar.pathfinder.node.PathfindingNode;
import gwt.client.astar.pathfinder.openlist.OpenList;


/**
 * @author Mindgamer
 */
public class BinaryHeap implements OpenList {

	/** The default capacity for a binary heap. */
	private final static int DEFAULT_CAPACITY = 100;
	
	/** Our heap - simple array of Objects */
	private PathfindingNode[] heap;
	
	/** The number of elements currently in this heap. */
	int size;
	
	/**
	 * Constructs a new minimum binary heap.
	 */
	public BinaryHeap(){
		// +1 as 0 is noop
		heap = new PathfindingNode[DEFAULT_CAPACITY + 1];
	}
	
	public void add(PathfindingNode node) {
		if (isFull()) {
			grow();
		}
		percolateUpHeap(node);
	}

	
	public void clear() {
		heap = new PathfindingNode[heap.length];	// For GC
		size = 0;
	}

	
	public PathfindingNode getFirstAndRemove() {
		final PathfindingNode result = heap[1];
		heap[1] = heap[size--];
		/*
		 * Set the unused element to 'null' so that the garbage collector
		 * can free the object if not used anywhere else.(remove reference)
		 */ 
		heap[size + 1] = null;
		if (size != 0) {
			percolateDownHeap(1);		// Percolate top element to it's place in tree
		}
		return result;
	}


	public PathfindingNode[] getOpenList() {
		return heap;
	}


	public void resort(PathfindingNode node) {
		for (int i = 1; i < size; i++){
			if (node == heap[i]) percolateUpHeap(i);
		}
	}

	
	public int size() {
		return size;
	}
	
	
	/**
	 * Tests if queue is full.
	 * 
	 * @return true if queue is full; false otherwise.
	 */
	private boolean isFull() {
		return heap.length == size + 1;
	}
	
	
	/**
	 * Tests if queue is empty.
	 * 
	 * @return true if queue is empty; false otherwise.
	 */
	private boolean isEmpty() {
		return size == 0;
	}

	
	/**
	 * Increases the size of the heap to support additional elements
	 */
	private void grow() {
		final PathfindingNode[] elements = new PathfindingNode[heap.length * 2];
		System.arraycopy(heap, 0, elements, 0, heap.length);
		heap = elements;
	}
	
	
	/**
	 * Percolates a new element up heap from the bottom. 
	 * 
	 * @param element
	 *            the element
	 */
	private void percolateUpHeap(final PathfindingNode element) {
		heap[++size] = element;
		percolateUpHeap(size);
	}
	
	
	/**
	 * Percolates element down heap from the position given by the index.<br>
	 * <br>
	 * Assumes it is a minimum heap.
	 * 
	 * @param index
	 *            the index for the element
	 */
	private void percolateDownHeap(final int index) {
		final PathfindingNode element = heap[index];
		int hole = index;

		while ((hole * 2) <= size) {
			int child = hole * 2;

			// if we have a right child and that child can not be percolated
			// up then move onto other child
			if (child != size && compare(heap[child + 1], heap[child]) < 0) {
				child++;
			}

			// if we found resting place of bubble then terminate search
			if (compare(heap[child], element) >= 0) {
				break;
			}

			heap[hole] = heap[child];
			hole = child;
		}

		heap[hole] = element;
	}
	
	
	/**
	 * Percolates element up heap from the position given by the index.<br>
	 * <br>
	 * Assumes it is a minimum heap.
	 * 
	 * @param index
	 *            the index of the element to be percolated up
	 */
	protected void percolateUpHeap(final int index) {
		int hole = index;
		PathfindingNode element = heap[hole];
		while (hole > 1 && compare(element, heap[hole / 2]) < 0) {
			// save element that is being pushed down
			// as the element "bubble" is percolated up
			final int next = hole / 2;
			heap[hole] = heap[next];
			hole = next;
		}
		heap[hole] = element;
	}
	
	
	/**
	 * Compares two objects 
	 * 
	 * @param a
	 *            the first object
	 * @param b
	 *            the second object
	 * @return -ve if a less than b, 0 if they are equal, +ve if a greater than
	 *         b
	 */
	private int compare(PathfindingNode a, PathfindingNode b) {
		return ((Comparable) a).compareTo(b);
	}
	
}
