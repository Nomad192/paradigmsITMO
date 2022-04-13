package queue;

public class MyLinkedQueueTest {

    public static void main(String[] args) {

        LinkedQueue myQueue1 = new LinkedQueue();

        //System.out.println("myQueue1" + myQueue1.element() + " size=" + myQueue1.size());
        myQueue1.enqueue("1");
        System.out.println("myQueue1 " + myQueue1.element() + " size=" + myQueue1.size());
        myQueue1.enqueue("2");
        System.out.println("myQueue1 " + myQueue1.element() + " size=" + myQueue1.size());
        myQueue1.printQ();
        System.out.println("myQueue1 " + myQueue1.dequeue() + " size=" + myQueue1.size());
        //System.out.println("myQueue1" + myQueue1.element() + " size=" + myQueue1.size());
        //System.out.println("myQueue1" + "e=" + myQueue1.element() + " size=" + myQueue1.size()); 
        myQueue1.printQ();
        for (int i = 0; i < 9; i++)
        {
            myQueue1.enqueue(i);
            System.out.println("myQueue1 " + "en=" + myQueue1.element() + " size=" + myQueue1.size()); 
            myQueue1.printQ();
        }        
        for (int i = 0; i < 9; i++)
        {
            System.out.println("myQueue1 " + "deq=" + myQueue1.dequeue() + " size=" + myQueue1.size());

        }
        System.out.println("myQueue1 " + "e=" + myQueue1.element() + " size=" + myQueue1.size()); 
        myQueue1.enqueue(10);
        System.out.println("myQueue1 " + "en=" + myQueue1.element() + " size=" + myQueue1.size()); 
        myQueue1.enqueue(11);
        System.out.println("myQueue1 " + "en=" + myQueue1.element() + " size=" + myQueue1.size()); 
        myQueue1.enqueue(12);
        System.out.println("myQueue1 " + "en=" + myQueue1.element() + " size=" + myQueue1.size()); 

    }
}