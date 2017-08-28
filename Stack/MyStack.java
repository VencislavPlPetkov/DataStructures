package Task7;

import java.util.Scanner;

/*
 *  Constant time of push, pop and max. Uses more memory to store items as objects instead of primitives.
 */

public class MyStack {
	
	class Item{
		int data;
		int max;
	}
	
	
    private Item[] arr;        
    private int numberOfElements;  
    public int curMax;

    
    public MyStack() {
        arr = new Item[20];
        numberOfElements = 0;
        curMax = Integer.MIN_VALUE;
    }

    
    public boolean isEmpty() {
        return numberOfElements == 0;
    }



    private void resize(int capacity) {

        Item[] temp = new Item[capacity];
        for (int i = 0; i < numberOfElements; i++) {
            temp[i] = arr[i];
        }
        arr = temp;

       // a = java.util.Arrays.copyOf(a, capacity);
    }



    public void push(int num) {
    	
        if (numberOfElements == arr.length){
        	resize(2*arr.length); 
        }
        
        Item item = new Item();
        
        item.data = num;
        item.max = curMax;
        
        if(num > curMax) {
        	curMax = num;
        }
        
        arr[numberOfElements++] = item;     
        
        
    }

   
    public int pop() {
        if (isEmpty()){ 
        	System.out.println("Can't pop because stack is empty");
        }
        
        Item item = arr[numberOfElements-1];
        
        if(item.data == curMax) {
        	curMax = item.max;
        }
        arr[numberOfElements-1] = null; 
        numberOfElements--;
        return item.data;
    }


   
    
    public int max() {
        if (isEmpty()){
        	System.out.println("Stack is empty");
        }
        return curMax;
    }
    
    @Override
    public String toString() {
		
    	String s = "";
		for (int i = 0; i <= numberOfElements; i++) {
			if(arr[i] != null) {
				s += arr[i].data + " ";
			} else 
				break;
		}
		
		
		
		
		String [] sArr = s.split(" ");
		String res = "";
		
		for(int i = sArr.length - 1; i >= 0; i--) {
			res += sArr[i] + " ";
		}
		
		//String reversedS = new StringBuilder(s).reverse().toString();
		
		return res;
    	
	}
    
   

    public static void main(String[] args) {
        
    	MyStack stack = new MyStack();
        
    	StringBuilder sb = new StringBuilder(); 
    	
        Scanner sc = new Scanner(System.in);

        
        while (sc.hasNextLine()) {
        	String line = sc.nextLine();
        	
        	String [] lineArr = line.split(" ");
        	String command = lineArr[0];
        	

			if (command.equals("END")) {
				
				break;
			}
            
            
            if (command.equals("POP")){ 
            	
            	sb.append(stack.pop() + "\n");
            	//System.out.println(stack.pop());
      
            }
            
            if (command.equals("MAX")){ 
            	sb.append(stack.max() + "\n");
            	//System.out.println(stack.max());
            	
            }
            
            if (command.equals("PUSH")){ 
                
            	int num = Integer.parseInt(lineArr[1]);
            	stack.push(num);
            }
            
            
        }
        
        String output = sb.toString();
        System.out.print(output);
        String curStack = stack.toString();
		System.out.println(curStack);
        

    }
}
