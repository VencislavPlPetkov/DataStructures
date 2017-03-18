package stackOfStrings;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The LinkedStack class represents a last-in-first-out (LIFO) stack of generic
 * items. This implementation uses a singly-linked list.
 */

public class LinkedStack<Item> implements Iterable<Item> {
	
	private int stackSize;
	private Node first;

	private class Node {
		private Item item;
		private Node next;
	}

	public LinkedStack() {
		first = null;
		stackSize = 0;
		assert check();
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return stackSize;
	}

	public void push(Item item) {

		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = oldfirst;
		stackSize++;
		assert check();

	}

	/**
	 * Removes and returns the item most recently added to this stack.
	 */
	public Item pop() {

		if (isEmpty()) {
			throw new NoSuchElementException("Stack underflow");
		}

		Item item = first.item; // save item to return
		first = first.next; // delete first node
		stackSize--;
		assert check();
		return item; // return the saved item
	}

	/**
	 * Returns (but does not remove) the item most recently added to this stack.
	 */
	public Item peek() {

		if (isEmpty()) {
			throw new NoSuchElementException("Stack underflow");
		}

		return first.item;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Item item : this)
			s.append(item + " ");
		return s.toString();
	}

	/**
	 * Returns an iterator to this stack that iterates through the items in LIFO
	 * order.
	 */

	public Iterator<Item> iterator() {
		return new ListIterator();
	}

	// an iterator, doesn't implement remove() since it's optional
	private class ListIterator implements Iterator<Item> {
		private Node current = first;

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {

			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			Item item = current.item;
			current = current.next;
			return item;
		}
	}

	// check internal invariants
	private boolean check() {

		// check a few properties of instance variable 'first'
		if (stackSize < 0) {
			return false;
		}
		if (stackSize == 0) {
			if (first != null)
				return false;
		} else if (stackSize == 1) {
			if (first == null)
				return false;
			if (first.next != null)
				return false;
		} else {
			if (first == null)
				return false;
			if (first.next == null)
				return false;
		}

		// check internal consistency of instance variable n
		int numberOfNodes = 0;
		for (Node x = first; x != null && numberOfNodes <= stackSize; x = x.next) {
			numberOfNodes++;
		}
		if (numberOfNodes != stackSize)
			return false;

		return true;
	}

	public static void main(String[] args) {
		LinkedStack<String> stack = new LinkedStack<String>();
		Scanner sc = new Scanner(System.in);

		while (sc.hasNextLine()) {
			String item = sc.nextLine();

			if (item.equals("quit")) {
				break;
			}
			if (!item.equals("pop")) {

				stack.push(item);

			} else if (!stack.isEmpty()) {

				System.out.println(stack.pop() + " ");

			}
		}

		System.out.println("(" + stack.size() + " items left on stack)");

		System.out.println("Stack Contains: " + stack.toString());
	}
}
