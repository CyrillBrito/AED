import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Archimedes {

   private static void archimedes(double epsilon) {
      int N = 6;
      double length = 1;

      double oldPi;
      double pi = semiperimeters(N, length);
      StdOut.printf("%d %.12f\n", N, pi);

      do {
         oldPi = pi;
         N *= 2;
         length = nextSide(length);
         pi = semiperimeters(N, length);
         StdOut.printf("%d %.12f\n", N, pi);
      } while (epsilon < pi - oldPi);
   }

   private static double nextSide(double length) {
      double ac = length / 2;
      double oc = Math.sqrt(1 - Math.pow(ac, 2));
      double cd = 1 - oc;
      double ad = Math.hypot(ac, cd);
      return ad;
   }

   private static double semiperimeters(int N, double length) {
      return N * length / 2;
   }

   private static void test() {
      while (!StdIn.isEmpty()) {
         double epsilon = StdIn.readDouble();
         archimedes(epsilon);
      }
   }

   public static void main(String args[]) {
      test();
   }
}