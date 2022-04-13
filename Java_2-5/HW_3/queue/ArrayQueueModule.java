package queue;

import java.util.LinkedHashMap;

public class ArrayQueueModule {

    // Model: a[1]...a[n]
    // Invariant: for i=1.. n: a[i] != null
    // let immutable(m, n):  for i=m..n: a'[i]==a[i]

    private static int end = 0;
    private static int start = 0;
    private static int real_length = 10;
    private static Object[] elements = new Object[real_length];
    private static final LinkedHashMap<Object, Integer> map = new LinkedHashMap<Object, Integer>();


    private static void ensureCapacity() {
        if (end - start == 0) {
            Object[] new_elements = new Object[real_length * 2];
            java.lang.System.arraycopy(elements, start, new_elements, 0, real_length - start);
            java.lang.System.arraycopy(elements, 0, new_elements, real_length - start, start);
            elements = new_elements;
            start = 0;
            end = real_length;
            real_length *= 2;
        }
    }

    private static int transition(int number) {
        if (number == real_length) {
            return 0;
        }
        return number;
    }

    // Pred: x != null
    // Post: n' = n + 1 && a[n'] == x && immutable(1, n)
    public static void enqueue(final Object x) {
        elements[end++] = x;

        map.merge(x, 1, Integer::sum);

        end = transition(end);
        ensureCapacity();
    }

    // Pred: n != 0
    // Post: R == a[1] && n' = n && immutable(1, n)
    public static Object element() {
        if (start != end)
            return elements[start];
        return null;
    }

    // Pred: n != 0
    // Post: R == a[1] && n' = n-1 && for i=2..n: a'[i]==a[i-1] 
    public static Object dequeue() {
        Object res = null;
        if (start != end) {
            res = elements[start];
            elements[start++] = null;

            if (map.get(res) == 1) {
                map.remove(res, map.get(res));
            } else {
                map.put(res, map.get(res) - 1);
            }

            start = transition(start);
        }
        return res;
    }

    // Pred: true
    // Post: R=n && n' = n && immutable(1, n)
    public static int size() {
        if (end >= start) {
            return end - start;
        } else {
            return real_length - start + end;
        }
    }

    // Pred: true
    // Post: R=(n==0) && n' = n && immutable(1, n)
    public static boolean isEmpty() {
        return start == end;
    }

    // Pred: true
    // Post: n' = 0
    public static void clear() {
        end = 0;
        start = 0;
        real_length = 10;
        elements = new Object[real_length];
        map.clear();
    }

    // Pred: y != null
    // Post: (for i=1..n: if (a[i] == y) s++: R = s) && n' = n && && immutable(1, n)
    public static int count(final Object y) {
        return map.getOrDefault(y, 0);
    }
}