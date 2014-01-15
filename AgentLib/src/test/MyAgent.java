package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.github.jamm.MemoryMeter;

public class MyAgent {
	
    private MemoryMeter meter = new MemoryMeter();
    

    public void shouldMeasureMemoryUsage() {
 
        String st1 = "This is the string #1";
        measure(st1);
         
        String st2 = "This is the string #2 and it has more chars.";
        measure(st2);
         
        List aList = new ArrayList(0);
        measure(aList);
         
        aList.add(st1);
        measure(aList);
         
        aList.add(st2);
        measure(aList);
        
        measure(new Author(0,0));
        
        HashMap<Integer,Author> hm = new HashMap<Integer,Author>(15000,1.0f);
        
        measure(hm);
        
        System.gc();
        long heapSize1 = Runtime.getRuntime().freeMemory();
        for(int i=1;i<1000;i++){
        	hm.put(i, new Author(i,i));
        }
        
        
        
        System.gc();
        long heapSize2 =  Runtime.getRuntime().freeMemory();
        System.out.println("Heap size start: "+String.valueOf( heapSize1));
        System.out.println("Heap size final: "+String.valueOf( heapSize2));
        System.out.print("Heap difference: "+String.valueOf( heapSize2-heapSize1));
        
        measure(hm);
        measure(new byte[10000000]);
        measure(1.0);
         
    }
 
    private void measure(Object anObject) {
         
        System.out.println("-----------------------------------");
        System.out.printf("size: %d bytes\n", meter.measure(anObject));
        System.out.printf("retained size: %d bytes\n", meter.measureDeep(anObject));
        System.out.printf("inner object count: %d\n", meter.countChildren(anObject));
    }
    
    public static void main(String arg[]){
    	MyAgent  agent = new MyAgent();
    	agent.shouldMeasureMemoryUsage();
    }

}
