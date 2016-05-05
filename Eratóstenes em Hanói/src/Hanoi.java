import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Hanoi {

   static int size;
   static Bag<int[]> solution;

   private static void solveNow(int n, int x, int y, int z, Bag<int[]> r) {
      if (n > 0) {
         solveNow(n - 1, x, z, y, r);
         r.add(new int[] { x, y });
         solveNow(n - 1, z, y, x, r);
      }
   }

   private static Bag<int[]> solve(int n, int x, int y, int z) {
      Bag<int[]> result = new Bag<>();
      solveNow(n, x, y, z, result);
      return result;
   }

   public static ArrayBag<Stack<Integer>> play(int m) {

      ArrayBag<Stack<Integer>> stacks = new ArrayBag<>();
      stacks.add(new Stack<Integer>());
      stacks.add(new Stack<Integer>());
      stacks.add(new Stack<Integer>());

      for (int i = size; i > 0; i--)
         stacks.get(0).push(i);

      int count = 0;
      for (int[] a : solution) {
         if (count == m)
            break;
         int x = stacks.get(a[0]).pop();
         stacks.get(a[1]).push(x);
         count++;
      }

      return stacks;
   }

   public static void test() {
      size = StdIn.readInt();
      solution = solve(size, 0, 1, 2);

      while (!StdIn.isEmpty()) {
         int m = StdIn.readInt();
         ArrayBag<Stack<Integer>> stacks = play(m);

         for (Stack<Integer> stack : stacks) {
            if (stack.isEmpty())
               StdOut.println("*");
            else {
               Iterator<Integer> it = stack.iterator();
               while (it.hasNext()) {
                  StdOut.print(it.next());
                  if (it.hasNext())
                     StdOut.print(" ");
               }
               StdOut.println();
            }
         }
      }
   }

   public static void main(String args[]) {
      test();
   }
}