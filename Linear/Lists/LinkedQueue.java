package linear.Lists;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *  The LinkedQueue class represents a first-in-first-out (FIFO)
 *  queue of generic items.
 *  This implementation uses a singly-linked list.
 *  The enqueue, dequeue, peek, size, and is-empty
 *  operations all take constant time in the worst case.
 */
public class LinkedQueue<Item> implements Iterable<Item> {
    private int numberOfElements;      
    private Node first;    
    private Node last;     

   
    private class Node {
        private Item item;
        private Node next;
    }

    
    public LinkedQueue() {
        first = null;
        last  = null;
        numberOfElements = 0;
        assert check();
    }

        public boolean isEmpty() {
        return first == null;
    }

    
    public int size() {
        return numberOfElements;     
    }

    /**
     * Returns the item least recently added to this queue.
     */
    public Item peek() {
        if (isEmpty()){
        	throw new NoSuchElementException("Queue underflow");
        }
        return first.item;
    }

    /**
     * Adds the item to this queue.
     */
    public void enqueue(Item item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()){
        	
        	first = last;
        }
        else{
        	oldlast.next = last;
        }
        numberOfElements++;
        assert check();
    }

    /**
     * Removes and returns the item on this queue that was least recently added.
     */
    public Item dequeue() {
        if (isEmpty()){
        	
        	throw new NoSuchElementException("Queue underflow");
        }
        Item item = first.item;
        first = first.next;
        numberOfElements--;
        if (isEmpty()){
        	
        	last = null;   // to avoid loitering
        }
        assert check();
        return item;
    }

   
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    } 

    // check internal invariants
    private boolean check() {
        if (numberOfElements < 0) {
            return false;
        }
        else if (numberOfElements == 0) {
            if (first != null) return false;
            if (last  != null) return false;
        }
        else if (numberOfElements == 1) {
            if (first == null || last == null) return false;
            if (first != last)                 return false;
            if (first.next != null)            return false;
        }
        else {
            if (first == null || last == null) return false;
            if (first == last)      return false;
            if (first.next == null) return false;
            if (last.next  != null) return false;

            // check internal consistency of instance variable n
            int numberOfNodes = 0;
            for (Node x = first; x != null && numberOfNodes <= numberOfElements; x = x.next) {
                numberOfNodes++;
            }
            if (numberOfNodes != numberOfElements) return false;

            // check internal consistency of instance variable last
            Node lastNode = first;
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }
            if (last != lastNode) return false;
        }

        return true;
    } 
 

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     */
    public Iterator<Item> iterator()  {
        return new ListIterator();  
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext()  { 
        	
        	return current != null;
        	
        }
        public void remove() {
        	throw new UnsupportedOperationException();  
        	}

        public Item next() {
            if (!hasNext()){
            	throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }


    
    public static void main(String[] args) {
        LinkedQueue<String> queue = new LinkedQueue<String>();
        
		Scanner sc = new Scanner(System.in);

        
        while (sc.hasNextLine())
        {
            String item = sc.nextLine();
            
            if (item.equals("quit")) {
				break;
			}
            
            if (!item.equals("dequeue")){
                queue.enqueue(item);
            }
            else if (!queue.isEmpty()){
                System.out.println(queue.dequeue() + " ");
            }
        }

        System.out.println("(" + queue.size() + " left on queue)");
        
		System.out.println("Queue Contains: " + queue.toString());

    }
}

