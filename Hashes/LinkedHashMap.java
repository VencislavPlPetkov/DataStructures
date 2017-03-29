package hashes;
/**
 * Collision resolution by chaining (closed addressing)
 * 
 * Each slot of the array contains a link to a singly-linked list containing
 * key-value pairs with the same hash. New key-value pairs are added to the end
 * of the list. Lookup algorithm searches through the list to find matching key.
 * Initially table slots contain nulls. List is being created, when value with
 * the certain hash is added for the first time.
 * 
 * Assuming, that hash function distributes hash codes uniformly and table
 * allows dynamic resizing, amortized complexity of insertion, removal and
 * lookup operations is constant. Actual time, taken by those operations
 * depends on table's load factor.
 *
 */
public class LinkedHashMap {
	private class LinkedHashEntry {
		private int key;
		private int value;
		private LinkedHashEntry next;

		LinkedHashEntry(int key, int value) {
			this.key = key;
			this.value = value;
			this.next = null;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public int getKey() {
			return key;
		}

		public LinkedHashEntry getNext() {
			return next;
		}

		public void setNext(LinkedHashEntry next) {
			this.next = next;
		}
	}

	private final static int TABLE_SIZE = 128;

	LinkedHashEntry[] table;

	LinkedHashMap() {
		table = new LinkedHashEntry[TABLE_SIZE];
		for (int i = 0; i < TABLE_SIZE; i++)
			table[i] = null;
	}

	public int get(int key) {
		int hash = (key % TABLE_SIZE);
		if (table[hash] == null)
			return -1;
		else {
			LinkedHashEntry entry = table[hash];
			while (entry != null && entry.getKey() != key)
				entry = entry.getNext();
			if (entry == null)
				return -1;
			else
				return entry.getValue();
		}
	}

	public void put(int key, int value) {
		int hash = (key % TABLE_SIZE);
		if (table[hash] == null)
			table[hash] = new LinkedHashEntry(key, value);
		else {
			LinkedHashEntry entry = table[hash];
			while (entry.getNext() != null && entry.getKey() != key)
				entry = entry.getNext();
			if (entry.getKey() == key)
				entry.setValue(value);
			else
				entry.setNext(new LinkedHashEntry(key, value));
		}
	}

	public void remove(int key) {
		int hash = (key % TABLE_SIZE);
		if (table[hash] != null) {
			LinkedHashEntry prevEntry = null;
			LinkedHashEntry entry = table[hash];
			while (entry.getNext() != null && entry.getKey() != key) {
				prevEntry = entry;
				entry = entry.getNext();
			}
			if (entry.getKey() == key) {
				if (prevEntry == null)
					table[hash] = entry.getNext();
				else
					prevEntry.setNext(entry.getNext());
			}
		}
	}
}
