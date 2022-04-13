package queue;

public class LinkedQueue extends AbstractQueue {
    private Node start;
    private Node end;

    private static class Node {
        private final Object value;
        private Node next;

        public Node(final Object value, final Node prev) {
            assert value != null;
            this.value = value;
            if (prev != null)
                prev.next = this;
        }
    }

    public LinkedQueue() {
        start = null;
        end = null;
    }

    @Override
    protected void enqueueImp(final Object x, final int length) {
        end = new Node(x, end);
        if (start == null) {
            start = end;
        }
    }

    @Override
    protected Object elementImp() {
        return start.value;
    }

    @Override
    protected Object dequeueImp() {
        Object res = start.value;
        start = start.next;
        return res;
    }

    @Override
    protected void clearImp() {
        start = null;
        end = null;
    }

    /*@Override
    protected int countIfImp(Predicate <Object> predicate, final int length)
    {
        int result = 0;
        Node a = start;
        while(a != null)
        {
            if (predicate.test(a.value))
                result++;
            a = a.next;
        }
        return result;  
    }*/
}