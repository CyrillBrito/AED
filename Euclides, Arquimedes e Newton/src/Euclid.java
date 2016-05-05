import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Euclid {

   private static int euclid(int x, int y) {
      while (y != 0) {
         int r = x % y;
         x = y;
         y = r;
      }
      return x;
   }

   private static void test() {
      while (!StdIn.isEmpty()) {
         int num1 = StdIn.readInt();
         int num2 = StdIn.readInt();
         StdOut.println(euclid(num1, num2));
      }
   }

   public static void main(String args[]) {
      test();
   }
}
