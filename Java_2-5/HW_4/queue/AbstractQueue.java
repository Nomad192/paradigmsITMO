package queue;

import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
    private int length;

    protected AbstractQueue() {
        length = 0;
    }

    // Model: a[1]...a[n]
    // Invariant: for i=1.. n: a[i] != null
    // let immutable(m, n):  for i=m..n: a'[i]==a[i]

    // Pred: x != null
    // Post: n' = n + 1 && a[n'] == x && immutable(1, n)
    public final void enqueue(final Object x) {
        assert x != null;
        enqueueImp(x, length);
        length++;
    }

    protected abstract void enqueueImp(final Object x, final int length);

    // Pred: true
    // Post: R=n && n' = n && immutable(1, n)
    public final int size() {
        return length;
    }

    // Pred: true
    // Post: R=(n==0) && n' = n && immutable(1, n)
    public final boolean isEmpty() {
        return (length == 0);
    }

    // Pred: n != 0
    // Post: R == a[1] && n' = n && immutable(1, n)
    public final Object element() {
        return elementImp();
    }

    protected abstract Object elementImp();

    // Pred: n != 0
    // Post: R == a[1] && n' = n-1 && for i=2..n: a'[i]==a[i-1]
    public final Object dequeue() {
        if (!this.isEmpty()) {
            length--;
            return dequeueImp();
        }
        return null;
    }

    protected abstract Object dequeueImp();

    // Pred: true
    // Post: n' = 0
    public final void clear() {
        length = 0;
        clearImp();
    }

    protected abstract void clearImp();

    // Pred: predicate != null
    // Post: (s = 0: for i=1..n: if(predicate.test(a[i])): s++: R = s) && n' = n && immutable(1, n)

    public final int count(final Object y) {
        Predicate<Object> predicate = (a) -> a.equals(y);
        return countIf(predicate);
    }
    //protected abstract int countImp(final Object y, final int length);

    public final int countIf(Predicate<Object> predicate) {
        int result = 0;
        for (int i = 0; i < length; i++) {
            final Object buf = this.dequeue();
            if (predicate.test(buf)) {
                result++;
            }
            this.enqueue(buf);
        }
        return result;
    }
    //protected abstract int countIfImp(Predicate <Object> predicate, final int length);
}
