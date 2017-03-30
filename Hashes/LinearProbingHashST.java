package hashes;

import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import queue.Queue;

/**
 * Collision resolution by linear probing (open addressing)
 */
public class LinearProbingHashST<Key, Value> {
	private static final int INIT_CAPACITY = 4;

	private int numberOfPairs; // number of key-value pairs
	private int sizeLPTable; // size of linear probing table
	private Key[] keys; // the keys
	private Value[] vals; // the values

	public LinearProbingHashST() {
		this(INIT_CAPACITY);
	}

	/**
	 * Initializes an empty symbol table with the specified initial capacity.
	 */
	public LinearProbingHashST(int capacity) {
		sizeLPTable = capacity;
		numberOfPairs = 0;
		keys = (Key[]) new Object[sizeLPTable];
		vals = (Value[]) new Object[sizeLPTable];
	}

	/**
	 * Returns the number of key-value pairs
	 */
	public int size() {
		return numberOfPairs;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns true if this symbol table contains the specified key.
	 */
	public boolean contains(Key key) {
		if (key == null)
			throw new IllegalArgumentException("argument to contains() is null");
		return get(key) != null;
	}

	// hash function for keys - returns value between 0 and M-1
	private int hash(Key key) {
		return (key.hashCode() & 0x7fffffff) % sizeLPTable;
	}

	// resizes the hash table to the given capacity by re-hashing all of the
	// keys
	private void resize(int capacity) {
		LinearProbingHashST<Key, Value> temp = new LinearProbingHashST<Key, Value>(capacity);
		for (int i = 0; i < sizeLPTable; i++) {
			if (keys[i] != null) {
				temp.put(keys[i], vals[i]);
			}
		}
		keys = temp.keys;
		vals = temp.vals;
		sizeLPTable = temp.sizeLPTable;
	}

	/**
	 * Inserts the specified key-value pair into the symbol table, overwriting
	 * the old value with the new value if the symbol table already contains the
	 * specified key. Deletes the specified key (and its associated value) from
	 * this symbol table if the specified value is null.
	 *
	 * @param key
	 *            the key
	 * @param val
	 *            the value
	 * @throws IllegalArgumentException
	 *             if key is null
	 */
	public void put(Key key, Value val) {
		if (key == null)
			throw new IllegalArgumentException("first argument to put() is null");

		if (val == null) {
			delete(key);
			return;
		}

		// double table size if 50% full
		if (numberOfPairs >= sizeLPTable / 2)
			resize(2 * sizeLPTable);

		int i;
		for (i = hash(key); keys[i] != null; i = (i + 1) % sizeLPTable) {
			if (keys[i].equals(key)) {
				vals[i] = val;
				return;
			}
		}
		keys[i] = key;
		vals[i] = val;
		numberOfPairs++;
	}

	/**
	 * Returns the value associated with the specified key.
	 */
	public Value get(Key key) {
		if (key == null)
			throw new IllegalArgumentException("argument to get() is null");
		for (int i = hash(key); keys[i] != null; i = (i + 1) % sizeLPTable)
			if (keys[i].equals(key))
				return vals[i];
		return null;
	}

	/**
	 * Removes the specified key and its associated value from this symbol table
	 * (if the key is in this symbol table).
	 */
	public void delete(Key key) {
		if (key == null)
			throw new IllegalArgumentException("argument to delete() is null");
		if (!contains(key))
			return;

		// find position i of key
		int i = hash(key);
		while (!key.equals(keys[i])) {
			i = (i + 1) % sizeLPTable;
		}

		// delete key and associated value
		keys[i] = null;
		vals[i] = null;

		// rehash all keys in same cluster
		i = (i + 1) % sizeLPTable;
		while (keys[i] != null) {
			// delete keys[i] an vals[i] and reinsert
			Key keyToRehash = keys[i];
			Value valToRehash = vals[i];
			keys[i] = null;
			vals[i] = null;
			numberOfPairs--;
			put(keyToRehash, valToRehash);
			i = (i + 1) % sizeLPTable;
		}

		numberOfPairs--;

		// halves size of array if it's 12.5% full or less
		if (numberOfPairs > 0 && numberOfPairs <= sizeLPTable / 8)
			resize(sizeLPTable / 2);

		assert check();
	}

	/**
	 * Returns all keys in this symbol table as an Iterable.
	 */
	public Iterable<Key> keys() {
		Queue<Key> queue = new Queue<Key>();
		for (int i = 0; i < sizeLPTable; i++)
			if (keys[i] != null)
				queue.enqueue(keys[i]);
		return queue;
	}

	// integrity check - don't check after each put() because
	// integrity not maintained during a delete()
	private boolean check() {

		// check that hash table is at most 50% full
		if (sizeLPTable < 2 * numberOfPairs) {
			System.err.println("Hash table size m = " + sizeLPTable + "; array size n = " + numberOfPairs);
			return false;
		}

		// check that each key in table can be found by get()
		for (int i = 0; i < sizeLPTable; i++) {
			if (keys[i] == null)
				continue;
			else if (get(keys[i]) != vals[i]) {
				System.err.println("get[" + keys[i] + "] = " + get(keys[i]) + "; vals[i] = " + vals[i]);
				return false;
			}
		}
		return true;
	}

}
