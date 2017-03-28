package priorityQueue;

/**
 * Priority Queue - Array representation (Unordered).
 * 
 * Insert O(1), Delete Max O(n), Peek Max O(n)
 * 
 * Simple priority queue implementation similar to pushdown stacks. The code for
 * insert in the priority queue is the same as for push in the stack. To
 * implement remove the maximum, we use code like the inner loop of selection
 * sort to exchange the maximum item with the item at the end and then delete
 * that one, as we with pop() for stacks.
 */

public class UnorderedArrayMaxPQ<Key extends Comparable<Key>> {
	private Key[] pq; // elements
	private int numberOfElements;

	// set initial size of heap to hold size elements
	public UnorderedArrayMaxPQ(int capacity) {
		pq = (Key[]) new Comparable[capacity];
		numberOfElements = 0;
	}

	public boolean isEmpty() {
		return numberOfElements == 0;
	}

	public int size() {
		return numberOfElements;
	}

	public void insert(Key x) {
		pq[numberOfElements++] = x;
	}

	public Key delMax() {
		int max = 0;
		for (int i = 1; i < numberOfElements; i++){
			if (less(max, i)){
				max = i;
			}
		}
		exch(max, numberOfElements - 1);

		return pq[--numberOfElements];
	}

	
	private boolean less(int i, int j) {
		return pq[i].compareTo(pq[j]) < 0;
	}

	private void exch(int i, int j) {
		Key swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
	}

	
	public static void main(String[] args) {
		UnorderedArrayMaxPQ<String> pq = new UnorderedArrayMaxPQ<String>(10);
		pq.insert("aa");
		pq.insert("cc");
		pq.insert("zz");
		pq.insert("vv");
		while (!pq.isEmpty()) {
			System.out.println(pq.delMax() + " deleted");
		}
	}

}
