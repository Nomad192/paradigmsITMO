package queue;

public class ArrayQueueTest {

    public static void main(String[] args) {

        ArrayQueueModule myQueue = new ArrayQueueModule();

        System.out.println(myQueue.element() + " size=" + myQueue.size());
        myQueue.enqueue("1");
        System.out.println(myQueue.element() + " size=" + myQueue.size());
        myQueue.enqueue("2");
        System.out.println(myQueue.element() + " size=" + myQueue.size());
        System.out.println(myQueue.dequeue() + " size=" + myQueue.size());
        System.out.println(myQueue.element() + " size=" + myQueue.size());

        System.out.println("e=" + myQueue.element() + " size=" + myQueue.size()); 
        for (int i = 0; i < 9; i++)
        {
            myQueue.enqueue(i);
            System.out.println("en=" + myQueue.element() + " size=" + myQueue.size()); 
        }        
        for (int i = 0; i < 8; i++)
        {
            System.out.println("deq=" + myQueue.dequeue() + " size=" + myQueue.size());
        }
        System.out.println("e=" + myQueue.element() + " size=" + myQueue.size()); 
        myQueue.enqueue(10);
        System.out.println("en=" + myQueue.element() + " size=" + myQueue.size()); 
        myQueue.enqueue(11);
        System.out.println("en=" + myQueue.element() + " size=" + myQueue.size()); 
        myQueue.enqueue(12);
        System.out.println("en=" + myQueue.element() + " size=" + myQueue.size()); 

    }
}