package linear.Arrays;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *  The ResizingArrayQueue class represents a first-in-first-out (FIFO)
 *  queue of generic items.
 *  This implementation uses a resizing array, which double the underlying array
 *  when it is full and halves the underlying array when it is one-quarter full.
 *  The enqueue and dequeue operations take constant amortized time.
 *  The size, peek, and is-empty operations take
 *  constant time in the worst case. 
 */
public class ResizingArrayQueue<Item> implements Iterable<Item> {
    private Item[] queue;    
    private int numberOfElements;    
    private int first;      
    private int last;       


    public ResizingArrayQueue() {
        queue = (Item[]) new Object[2];
        numberOfElements = 0;
        first = 0;
        last = 0;
    }

    public boolean isEmpty() {
        return numberOfElements == 0;
    }

    
    public int size() {
        return numberOfElements;
    }

    // resize the underlying array
    private void resize(int capacity) {
        assert capacity >= numberOfElements;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < numberOfElements; i++) {
            temp[i] = queue[(first + i) % queue.length];
        }
        queue = temp;
        first = 0;
        last  = numberOfElements;
    }

    /**
     * Adds the item to this queue.
     */
    public void enqueue(Item item) {
        // double size of array if necessary and recopy to front of array
        if (numberOfElements == queue.length) {
        	
        	resize(2*queue.length);   // double size of array if necessary
        }
        queue[last++] = item;                        // add item
        if (last == queue.length) last = 0;          // wrap-around
        numberOfElements++;
    }

    /**
     * Removes and returns the item on this queue that was least recently added.
     */
    public Item dequeue() {
        if (isEmpty()) {
        	throw new NoSuchElementException("Queue underflow");
        }
        Item item = queue[first];
        queue[first] = null;                            // to avoid loitering
        numberOfElements--;
        first++;
        if (first == queue.length) {
        	first = 0;           // wrap-around
        }
        // shrink size of array if necessary
        if (numberOfElements > 0 && numberOfElements == queue.length/4) {
        	resize(queue.length/2); 
        }
        return item;
    }

    /**
     * Returns the item least recently added to this queue.
     */
    public Item peek() {
        if (isEmpty()) {
        	throw new NoSuchElementException("Queue underflow");
        }
        return queue[first];
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    } 
    
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        public boolean hasNext()  { 
        	return i < numberOfElements;
        	}
        public void remove() {
        	throw new UnsupportedOperationException();  
        	}

        public Item next() {
            if (!hasNext()) {
            	throw new NoSuchElementException();
            }
            Item item = queue[(i + first) % queue.length];
            i++;
            return item;
        }
    }

   
    public static void main(String[] args) {
        ResizingArrayQueue<String> queue = new ResizingArrayQueue<String>();
        
		Scanner sc = new Scanner(System.in);

        
        while (sc.hasNextLine()) {
            String item = sc.nextLine();
            
            if (item.equals("quit")) {
				break;
			}
            
            if (!item.equals("dequeue")) {
            	queue.enqueue(item);
            }
            else if (!queue.isEmpty()) {
            	System.out.println(queue.dequeue() + " ");
            }
        }
        
        System.out.println("(" + queue.size() + " left on queue)");
        
		System.out.println("Queue Contains: " + queue.toString());
    }

}

