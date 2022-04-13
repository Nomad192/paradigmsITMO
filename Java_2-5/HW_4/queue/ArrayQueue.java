package queue;

public class ArrayQueue extends AbstractQueue {
    private int start;
    private int real_length;
    private Object[] elements;

    public ArrayQueue() {
        start = 0;
        real_length = 10;
        elements = new Object[real_length];
    }

    private void ensureCapacity(final int length) {
        if (length == real_length) {
            Object[] new_elements = new Object[real_length * 2];
            java.lang.System.arraycopy(elements, start, new_elements, 0, real_length - start);
            java.lang.System.arraycopy(elements, 0, new_elements, real_length - start, start);
            elements = new_elements;
            start = 0;
            real_length *= 2;
        }
    }

    private int transition(int number) {
        if (number == real_length) {
            return 0;
        }
        return number;
    }

    @Override
    protected void enqueueImp(final Object x, final int length) {
        elements[(start + length) % real_length] = x;
        ensureCapacity(length + 1);
    }

    @Override
    protected Object elementImp() {
        return elements[start];
    }

    @Override
    protected Object dequeueImp() {
        Object res = elements[start];
        elements[start++] = null;
        start = transition(start);
        return res;
    }

    @Override
    protected void clearImp() {
        start = 0;
        real_length = 10;
        elements = new Object[real_length];
    }

    /*@Override
    protected int countImp(final Object y, final int length)
    {
        int result = 0;
        int i = 0;
        int end = (start + length)%real_length;
        for(i = start; i < end || i < real_length; i++)
        {
            if (elements[i] == y)
                result++; 
        }
        if(end < start)        
            for(i = 0; i < end; i++)    
            {
                if (elements[i] == y)
                    result++;        
            }

        return result;  
    }*/

    /*@Override
    protected int countIfImp(Predicate <Object> predicate, final int length)
    {
        int result = 0;
        int i = 0;
        int end = (start + length)%real_length;
        for(i = start; i < end || i < real_length; i++)
        {
            if (predicate.test(elements[i]))
                result++; 
        }
        if(end < start)        
            for(i = 0; i < end; i++)    
            {
                if (predicate.test(elements[i]))
                    result++;        
            }

        return result;  
    }*/
}