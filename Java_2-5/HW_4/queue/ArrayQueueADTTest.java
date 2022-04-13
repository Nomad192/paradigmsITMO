package queue;

import static queue.ArrayQueueADT.*;

public class ArrayQueueADTTest {

    public static void main(String[] args) {

        ArrayQueueADT myQueue = new ArrayQueueADT();

        System.out.println(element(myQueue) + " size=" + size(myQueue));
        enqueue(myQueue, "1");
        System.out.println(element(myQueue) + " size=" + size(myQueue));
        enqueue(myQueue, "2");
        System.out.println(element(myQueue) + " size=" + size(myQueue));
        System.out.println(dequeue(myQueue) + " size=" + size(myQueue));
        System.out.println(element(myQueue) + " size=" + size(myQueue));

        System.out.println("e=" + element(myQueue) + " size=" + size(myQueue)); 
        for (int i = 0; i < 9; i++)
        {
            myQueue.enqueue(myQueue, i);
            System.out.println("en=" + element(myQueue) + " size=" + size(myQueue)); 
        }        
        for (int i = 0; i < 8; i++)
        {
            System.out.println("deq=" + dequeue(myQueue) + " size=" + size(myQueue));
        }
        System.out.println("e=" + element(myQueue) + " size=" + size(myQueue)); 
        myQueue.enqueue(myQueue, 10);
        System.out.println("en=" + element(myQueue) + " size=" + size(myQueue)); 
        myQueue.enqueue(myQueue, 11);
        System.out.println("en=" + element(myQueue) + " size=" + size(myQueue)); 
        myQueue.enqueue(myQueue, 12);
        System.out.println("en=" + element(myQueue) + " size=" + size(myQueue)); 
    }
}