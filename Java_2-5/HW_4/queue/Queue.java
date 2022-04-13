package queue;

import java.util.function.Predicate;

public interface Queue {
    public void enqueue(final Object x);

    public Object element();

    public Object dequeue();

    public int size();

    public boolean isEmpty();

    public void clear();

    public int countIf(Predicate<Object> predicate);
}
