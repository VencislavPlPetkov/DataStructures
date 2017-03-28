package priorityQueue;

/**
 * Priority Queue - Array representation (Ordered). 
 * 
 * Insert O(n), Delete Max O(1), Peek Max O(1)
 * 
 * Insert moves larger entries
 * one position to the right, thus keeping the entries in the array in order (as
 * in insertion sort). Thus the largest item is always at the end, and the code
 * for remove the maximum in the priority queue is the same as for pop in the
 * stack.
 * 
 */
public class OrderedArrayMaxPQ<Key extends Comparable<Key>> {
	private Key[] pq; 
	private int numberOfElements;

	public OrderedArrayMaxPQ(int capacity) {
		pq = (Key[]) (new Comparable[capacity]);
		numberOfElements = 0;
	}

	public boolean isEmpty() {
		return numberOfElements == 0;
	}

	public int size() {
		return numberOfElements;
	}

	public Key delMax() {
		return pq[--numberOfElements];
	}

	public void insert(Key key) {
		int i = numberOfElements - 1;
		while (i >= 0 && less(key, pq[i])) {
			pq[i + 1] = pq[i];
			i--;
		}
		pq[i + 1] = key;
		numberOfElements++;
	}

	private boolean less(Key v, Key w) {
		return v.compareTo(w) < 0;
	}

	public static void main(String[] args) {
		OrderedArrayMaxPQ<String> pq = new OrderedArrayMaxPQ<String>(10);
		pq.insert("aa");
		pq.insert("zz");
		pq.insert("dd");
		pq.insert("oo");
		while (!pq.isEmpty()) {
			System.out.println(pq.delMax());
		}
	}

}
