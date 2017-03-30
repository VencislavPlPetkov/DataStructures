package hashes;

/**
 * Collision resolution by chaining (closed addressing)
 */

public class SeparateChainingHashST<Key, Value> {

	private int numKVPairs; // number of key-value pairs
	private int size; // hash table size
	private Node[] st; // array of linked-list symbol tables

	private static class Node {
		private Object key;
		private Object val;
		private Node next;

		public Node(Object key, Object val, Node next) {
			this.key = key;
			this.val = val;
			this.next = next;
		}
	}

	// create separate chaining hash table
	public SeparateChainingHashST() {
		this(997);
	}

	// create separate chaining hash table with m lists
	public SeparateChainingHashST(int m) {
		this.size = m;
		st = new Node[m];
	}

	// hash value between 0 and m-1
	private int hash(Key key) {
		return (key.hashCode() & 0x7fffffff) % size;
	}

	// return number of key-value pairs 
	public int size() {
		return numKVPairs;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	// is the key in the symbol table?
	public boolean contains(Key key) {
		return get(key) != null;
	}

	// return value associated with key, null if no such key
	public Value get(Key key) {
		int i = hash(key);
		for (Node x = st[i]; x != null; x = x.next) {
			if (key.equals(x.key))
				return (Value) x.val;
		}
		return null;
	}

	// insert key-value pair into the table
	public void put(Key key, Value val) {
		if (val == null) {
			delete(key);
			return;
		}
		int i = hash(key);
		for (Node x = st[i]; x != null; x = x.next) {
			if (key.equals(x.key)) {
				x.val = val;
				return;
			}
		}
		numKVPairs++;
		st[i] = new Node(key, val, st[i]);
	}

	// delete key (and associated value) 
	public void delete(Key key) {
		throw new UnsupportedOperationException("delete not currently supported");
	}

	// return all keys as an Iterable
	public Iterable<Key> keys() {
		Queue<Key> queue = new Queue<Key>();
		for (int i = 0; i < size; i++) {
			for (Node x = st[i]; x != null; x = x.next) {
				queue.enqueue((Key) x.key);
			}
		}
		return queue;
	}

}
