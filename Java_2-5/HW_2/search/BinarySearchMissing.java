package search;

import java.util.ArrayList;

public class BinarySearchMissing {

    public static void main(String[] args) {

        Integer x = Integer.parseInt(args[0]);
        ArrayList<Integer> numbers = new ArrayList<Integer>();


        if (args.length == 1) {
            System.out.print(-1);
        } else {
            numbers.add(Integer.MIN_VALUE);

            for (int i = 1; i < args.length; i++) {
                numbers.add(Integer.parseInt(args[i]));
            }

            //numbers.add(Integer.MAX_VALUE);

            int ite = iterativeBinarySearch(numbers, x, 1, numbers.size() - 1) - 1;
            int rec = recursiveBinarySearch(numbers, x, 1, numbers.size() - 1) - 1;

            if (ite == rec) {
                System.out.print(ite);
            } else {
                System.out.print("Error: Iterative=" + ite + " and Recursive=" + rec);
            }
        }
    }


    //Pre: a[i] <= a[i+1], i=left...rigth-1; lelft <= rigth; left >= 0; right <= a.length - 1
    //Post: R=i, a[i] = x and a[j] < x, j=left...i; or if (x does not belong to a), R=i, i = -right or a[-i] > x and a[-i-1] < x 
    private static Integer iterativeBinarySearch(ArrayList<Integer> a, Integer x, Integer l, Integer r) {
        //true
        while (l <= r) {
            // l' <= r'
            Integer m = (l + r) / 2;
            // m = (l' + r') / 2
            if (a.get(m) < x) {
                // a[(l' + r') / 2] < x
                l = m + 1;
                // l'' = (l' + r') / 2 + 1 
                // r'' = r'
            } else if (a.get(m - 1) < x) {
                // a[(l' + r') / 2] >= x && a[(l' + r') / 2 - 1] < x
                if (a.get(m).equals(x)) {
                    // a[(l' + r') / 2] = x
                    return m;
                } else {
                    // a[(l' + r') / 2] > x && a[(l' + r') / 2 - 1] < x
                    return -(m - 1);
                }
            } else {
                // a[(l' + r') / 2] >= x  && a[(l' + r') / 2 - 1] >= x
                r = m - 1;
                // r'' = (l' + r') / 2 - 1 
                // l'' = l'
            }
            // (r''-l'')/(r'-l') = 1/2
        }
        // l' > r' 
        // => r' = l or l' = r + 1
        // => a[i] <= x, i = l...r or a[i] > x, i = l+1...r
        // => a[l'] <= x, l' = l or a[j] > x, j=l...l'
        return -r;
    }

    //Pre: a[i] <= a[i+1], i=left...rigth-1; lelft <= rigth; left >= 0; right <= a.length - 1
    //Post: R=i, a[i] = x and a[j] < x, j=left...i; or if (x does not belong to a), R=i, i = -right or a[-i] > x and a[-i-1] < x
    private static Integer recursiveBinarySearch(ArrayList<Integer> a, Integer x, Integer l, Integer r) {
        if (l > r) {
            // l' > r'
            // => r' = l or l' = r + 1
            // => a[i] <= x, i = l...r or a[i] > x, i = l+1...r
            // => a[l'] <= x, l' = l or a[j] > x, j=l...l'
            return -r;
        }
        // l' <= r'
        Integer m = (l + r) / 2;
        // m = (l' + r') / 2
        if (a.get(m) < x) {
            // a[(l' + r') / 2] < x
            l = m + 1;
            // l'' = (l' + r') / 2 + 1 
            // r'' = r'
        } else if (a.get(m - 1) < x) {
            // a[(l' + r') / 2] <= x && a[(l' + r') / 2 - 1] < x
            if (a.get(m).equals(x)) {
                // a[(l' + r') / 2] = x
                return m;
            } else {
                // a[(l' + r') / 2] > x && a[(l' + r') / 2 - 1] < x
                return -(m - 1);
            }

        } else {
            // a[(l' + r') / 2] >= x  && a[(l' + r') / 2 - 1] >= x
            r = m - 1;
            // r'' = (l' + r') / 2 - 1 
            // l'' = l'
        }
        // (r''-l'')/(r'-l') = 1/2
        return recursiveBinarySearch(a, x, l, r);
    }
}