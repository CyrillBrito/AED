import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Newton {

   private static double mySqrt(double z) {
      return z >= 1 ? newton(z) : z * newton(1 / z);
   }

   private static double newton(double z) {
      double result = z;

      do {
         result = nextGuess(result, z);
      } while (0.0000000001 < result - nextGuess(result, z));

      return result;
   }

   private static double nextGuess(double x, double z) {
      double y = x * x - z;
      double slope = 2 * x;
      return x - y / slope;
   }

   private static void test() {
      while (!StdIn.isEmpty()) {
         double num = StdIn.readDouble();
         StdOut.printf("%.8f %.8f\n", num, mySqrt(num));
      }
   }

   public static void main(String args[]) {
      test();
   }
}