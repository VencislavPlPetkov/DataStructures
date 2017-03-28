package linear.Arrays;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *  The ResizingArrayStack class represents a last-in-first-out (LIFO) stack
 *  of generic items.
 * 
 *  This implementation uses a resizing array, which doubles the underlying array
 *  when it is full and halves the underlying array when it is one-quarter full.
 *  The push and pop operations take constant amortised time.
 *  The size, peek, and is-empty operations takes
 *  constant time in the worst case. 
 *  
 */
public class ResizingArrayStack<Item> implements Iterable<Item> {
    private Item[] arr;        
    private int numberOfElements;    

    
    public ResizingArrayStack() {
        arr = (Item[]) new Object[2];
        numberOfElements = 0;
    }

    
    public boolean isEmpty() {
        return numberOfElements == 0;
    }

    
    public int size() {
        return numberOfElements;
    }


    // resize the underlying array holding the elements
    private void resize(int capacity) {
        
    	assert capacity >= numberOfElements;

        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < numberOfElements; i++) {
            temp[i] = arr[i];
        }
        arr = temp;

       // alternative implementation
       // a = java.util.Arrays.copyOf(a, capacity);
    }



    public void push(Item item) {
    	
    	// double size of array if necessary
        if (numberOfElements == arr.length){
        	resize(2*arr.length); 
        }
        arr[numberOfElements++] = item;     // add item
    }

   
    public Item pop() {
        if (isEmpty()){ 
        	throw new NoSuchElementException("Stack underflow");
        }
        
        Item item = arr[numberOfElements-1];
        arr[numberOfElements-1] = null;     // to avoid loitering
        numberOfElements--;
        // shrink size of array if necessary
        if (numberOfElements > 0 && numberOfElements == arr.length/4) resize(arr.length/2);
        return item;
    }


    public Item peek() {
        if (isEmpty()){
        	throw new NoSuchElementException("Stack underflow");
        }
        return arr[numberOfElements-1];
    }
    
    @Override
    public String toString() {
		
    	StringBuilder s = new StringBuilder();
		for (Item item : this)
			s.append(item + " ");
		return s.toString();
    	
	}
    
    /**
     * Returns an iterator to this stack that iterates through the items in LIFO order.
     */
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<Item> {
        private int i;

        public ReverseArrayIterator() {
            i = numberOfElements-1;
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return arr[i--];
        }
    }


    public static void main(String[] args) {
        
    	ResizingArrayStack<String> stack = new ResizingArrayStack<String>();
        
        Scanner sc = new Scanner(System.in);

        
        while (sc.hasNextLine()) {
        	String item = sc.nextLine();

			if (item.equals("quit")) {
				break;
			}
            
            
            if (!item.equals("pop")){ 
            
            	stack.push(item);
            }
            else if (!stack.isEmpty()){ 
            	
            	System.out.println(stack.pop() + " ");
            
            }
        }
        
        System.out.println("(" + stack.size() + " items left on stack)");

		System.out.println("Stack Contains: " + stack.toString());
    }
}
