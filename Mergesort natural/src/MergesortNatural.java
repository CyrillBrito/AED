import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class MergesortNatural<T extends Comparable<T>> extends Sort<T> {

   public void sort(T[] a) {
      T[] b = Utils.newArrayLike(a, a.length);
      T[] c = Utils.newArrayLike(a, a.length);

      int size = splitRuns(a, b, c);
      while (a.length - size > 0) {
         mergeNatural(size, b, c, a);
         size = splitRuns(a, b, c);
      }
   }

   public int[] sortCounting(T[] a) {
      T[] b = Utils.newArrayLike(a, a.length);
      T[] c = Utils.newArrayLike(a, a.length);

      ArrayBag<Integer> sizes = new ArrayBag<>();
      sizes.add(countSections(a));
      int size = splitRuns(a, b, c);
      while (a.length - size > 0) {
         mergeNatural(size, b, c, a);
         sizes.add(countSections(a));
         size = splitRuns(a, b, c);
      }
      return Utils.intsFromIntegers(sizes.toArray());
   }

   public int countSections(T[] a) {
      int result = 1;
      for (int i = 1; i < a.length; i++)
         if (less(a[i], a[i - 1]))
            result++;
      return result;
   }

   public int splitRuns(T[] a, T[] b, T[] c) {
      int sizeB = 0;
      int sizeC = 0;
      boolean addB = true;

      b[sizeB++] = a[0];
      for (int i = 1; i < a.length; i++) {
         if (less(a[i], a[i - 1]))
            addB = !addB;

         if (addB)
            b[sizeB++] = a[i];
         else
            c[sizeC++] = a[i];
      }

      return sizeB;
   }

   public void mergeNatural(int nb, T[] b, T[] c, T[] a) {
      int pivotB = 0;
      int pivotC = 0;

      if (less(b[0], c[0]) || equal(b[0], c[0]))
         a[0] = b[pivotB++];
      else
         a[0] = c[pivotC++];
      for (int i = 1; i < a.length; i++) {

         if (pivotB >= nb)
            a[i] = c[pivotC++];
         else if (pivotC >= a.length - nb)
            a[i] = b[pivotB++];
         else {
            if (less(b[pivotB], c[pivotC]) || equal(b[pivotB], c[pivotC])) {
               if (greater(b[pivotB], a[i - 1]) || equal(b[pivotB], a[i - 1]))
                  a[i] = b[pivotB++];
               else if (greater(c[pivotC], a[i - 1]) || equal(c[pivotC], a[i - 1]))
                  a[i] = c[pivotC++];
               else
                  a[i] = b[pivotB++];
            } else {
               if (greater(c[pivotC], a[i - 1]) || equal(c[pivotC], a[i - 1]))
                  a[i] = c[pivotC++];
               else if (greater(b[pivotB], a[i - 1]) || equal(b[pivotB], a[i - 1]))
                  a[i] = b[pivotB++];
               else
                  a[i] = c[pivotC++];
            }
         }
      }
   }

   public static Integer[] readIntegers() {
      String s = StdIn.readLine();
      String[] ss = s.split(" ");
      Integer[] result = new Integer[ss.length];
      for (int i = 0; i < ss.length; i++)
         result[i] = Integer.parseInt(ss[i]);
      return result;
   }

   public static void testSplitRunsInt() {
      Integer[] a = integersFromInts(StdIn.readAllInts());
      Integer[] b = new Integer[a.length];
      Integer[] c = new Integer[a.length];
      MergesortNatural<Integer> sort = new MergesortNatural<>();

      int sizeB = sort.splitRuns(a, b, c);

      for (int i = 0; i < sizeB; i++) {
         if (i != 0)
            StdOut.print(' ');
         StdOut.print(b[i]);
      }
      StdOut.println();

      for (int i = 0; i < a.length - sizeB; i++) {
         if (i != 0)
            StdOut.print(' ');
         StdOut.print(c[i]);
      }
      StdOut.println();
   }

   public static void testSplitRunsStr() {
      String[] a = StdIn.readAllStrings();
      String[] b = new String[a.length];
      String[] c = new String[a.length];
      MergesortNatural<String> sort = new MergesortNatural<>();

      int sizeB = sort.splitRuns(a, b, c);

      for (int i = 0; i < sizeB; i++) {
         if (i != 0)
            StdOut.print(' ');
         StdOut.print(b[i]);
      }
      StdOut.println();

      for (int i = 0; i < a.length - sizeB; i++) {
         if (i != 0)
            StdOut.print(' ');
         StdOut.print(c[i]);
      }
      StdOut.println();
   }

   public static void testMergeNaturalInt() {
      Integer[] b = readIntegers();
      Integer[] c = readIntegers();
      Integer[] a = new Integer[b.length + c.length];

      MergesortNatural<Integer> sort = new MergesortNatural<>();
      sort.mergeNatural(b.length, b, c, a);

      for (int i = 0; i < a.length; i++) {
         if (i != 0)
            StdOut.print(' ');
         StdOut.print(a[i]);
      }
      StdOut.println();
   }

   public static void testMergeNaturalStr() {
      String[] b = StdIn.readLine().split(" ");
      String[] c = StdIn.readLine().split(" ");
      String[] a = new String[b.length + c.length];

      MergesortNatural<String> sort = new MergesortNatural<>();
      sort.mergeNatural(b.length, b, c, a);

      for (int i = 0; i < a.length; i++) {
         if (i != 0)
            StdOut.print(' ');
         StdOut.print(a[i]);
      }
      StdOut.println();
   }

   public static void testMergesortInt() {
      Integer[] a = Utils.integersFromInts(StdIn.readAllInts());

      MergesortNatural<Integer> sort = new MergesortNatural<>();
      sort.sort(a);

      for (int i = 0; i < a.length; i++)
         StdOut.print(a[i] + "\n");
   }

   public static void testMergesortStr() {
      String[] a = StdIn.readAllStrings();

      MergesortNatural<String> sort = new MergesortNatural<>();
      sort.sort(a);

      for (int i = 0; i < a.length; i++)
         StdOut.print(a[i] + "\n");
   }

   public static void testMergesortCountInt() {
      Integer[] a = Utils.integersFromInts(StdIn.readAllInts());

      MergesortNatural<Integer> sort = new MergesortNatural<>();
      int[] sizes = sort.sortCounting(a);

      for (int i = 0; i < sizes.length; i++) {
         if (i != 0)
            StdOut.print(' ');
         StdOut.print(sizes[i]);
      }
      StdOut.println();
   }

   public static void testMergesortCountStr() {
      String[] a = StdIn.readAllStrings();

      MergesortNatural<String> sort = new MergesortNatural<>();
      int[] sizes = sort.sortCounting(a);

      for (int i = 0; i < sizes.length; i++) {
         if (i != 0)
            StdOut.print(' ');
         StdOut.print(sizes[i]);
      }
      StdOut.println();
   }

   public static void main(String[] args) {
      if (args[0].equals("1"))
         testMergesortInt();
      else if (args[0].equals("2"))
         testMergesortStr();
      else if (args[0].equals("4"))
         testSplitRunsInt();
      else if (args[0].equals("5"))
         testSplitRunsStr();
      else if (args[0].equals("6"))
         testMergeNaturalInt();
      else if (args[0].equals("7"))
         testMergeNaturalStr();
      else if (args[0].equals("8"))
         testMergesortCountInt();
      else if (args[0].equals("9"))
         testMergesortCountStr();
      else
         StdOut.println("Wrong argument");
   }
}