package queue;

import java.util.LinkedHashMap;

public class ArrayQueueADT {

    // Model: a[1]...a[n]
    // Invariant: for i=1.. n: a[i] != null
    // let immutable(m, n):  for i=m..n: a'[i]==a[i] 

    private int end;
    private int start;
    private int real_length;
    private Object[] elements;
    private LinkedHashMap<Object, Integer> map;

    public ArrayQueueADT() {
        end = 0;
        start = 0;
        real_length = 10;
        elements = new Object[real_length];
        map = new LinkedHashMap<Object, Integer>();
    }

    ;

    private static void ensureCapacity(final ArrayQueueADT queue) {
        if (queue.end - queue.start == 0) {
            Object[] new_elements = new Object[queue.real_length * 2];
            java.lang.System.arraycopy(queue.elements, queue.start, new_elements, 0, queue.real_length - queue.start);
            java.lang.System.arraycopy(queue.elements, 0, new_elements, queue.real_length - queue.start, queue.start);
            queue.elements = new_elements;
            queue.start = 0;
            queue.end = queue.real_length;
            queue.real_length *= 2;
        }
    }

    private static int transition(final ArrayQueueADT queue, int number) {
        if (number == queue.real_length) {
            return 0;
        }
        return number;
    }

    // Pred: x != null
    // Post: n' = n + 1 && a[n'] == x && immutable(1, n)
    public static void enqueue(final ArrayQueueADT queue, final Object x) {
        queue.elements[queue.end++] = x;

        queue.map.merge(x, 1, Integer::sum);

        queue.end = transition(queue, queue.end);
        ensureCapacity(queue);
    }

    // Pred: n != 0
    // Post: R == a[1] && n' = n && immutable(1, n)
    public static Object element(final ArrayQueueADT queue) {
        if (queue.start != queue.end)
            return queue.elements[queue.start];
        return null;
    }

    // Pred: n != 0
    // Post: R == a[1] && n' = n-1 && for i=2..n: a'[i]==a[i-1]  
    public static Object dequeue(final ArrayQueueADT queue) {
        Object res = null;
        if (queue.start != queue.end) {
            res = queue.elements[queue.start];
            queue.elements[queue.start++] = null;

            if (queue.map.get(res) == 1) {
                queue.map.remove(res, queue.map.get(res));
            } else {
                queue.map.put(res, queue.map.get(res) - 1);
            }

            queue.start = transition(queue, queue.start);
        }
        return res;
    }

    // Pred: true
    // Post: R=n && n' = n && immutable(1, n)
    public static int size(final ArrayQueueADT queue) {
        if (queue.end >= queue.start) {
            return queue.end - queue.start;
        } else {
            return queue.real_length - queue.start + queue.end;
        }
    }

    // Pred: true
    // Post: R=(n==0) && n' = n && immutable(1, n)
    public static boolean isEmpty(final ArrayQueueADT queue) {
        if (queue.start == queue.end)
            return true;
        return false;
    }

    // Pred: true
    // Post: n' = 0
    public static void clear(final ArrayQueueADT queue) {
        queue.end = 0;
        queue.start = 0;
        queue.real_length = 10;
        queue.elements = new Object[queue.real_length];
        queue.map.clear();
    }

    // Pred: y != null
    // Post: (for i=1..n: if (a[i] == y) s++: R = s) && n' = n && && immutable(1, n)
    public static int count(final ArrayQueueADT queue, final Object y) {
        return queue.map.getOrDefault(y, 0);
    }
}