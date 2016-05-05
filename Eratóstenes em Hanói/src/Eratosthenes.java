import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Eratosthenes {

   // ----- CLASSIC -----
   public static ArrayBag<Integer> sieveClassic(int x) {
      boolean[] numbers = new boolean[x];
      int num = 2;

      do {
         rmMultiple(num, x, numbers);
         num = nextNumber(num, x, numbers);
      } while (num * num <= x);

      ArrayBag<Integer> primeNumbers = new ArrayBag<>();
      for (int i = 2; i < x; i++)
         if (!numbers[i])
            primeNumbers.add(i);

      return primeNumbers;
   }

   public static int nextNumber(int num, int x, boolean[] numbers) {
      for (int i = num + 1; i < x; i++)
         if (!numbers[i])
            return i;
      return -1;
   }

   public static void rmMultiple(int num, int x, boolean[] numbers) {
      for (int i = num * num; i < x; i += num)
         numbers[i] = true;
   }

   public static void testClassic() {
      int x = StdIn.readInt();

      ArrayBag<Integer> primeNumbers = sieveClassic(x);
      StdOut.println(primeNumbers.size);

      while (!StdIn.isEmpty()) {
         int n = StdIn.readInt();
         if (n > primeNumbers.size)
            StdOut.println("-1");
         else
            StdOut.println(primeNumbers.get(n - 1));
      }
   }

   // ----- MODERN -----
   public static ArrayBag<Integer> sieveModern(int x) {
      ArrayBag<Integer> numbers = new ArrayBag<>();
      for (int i = 2; i <= x; i++) // fill the ArrayBag
         numbers.add(i);

      int num = 2;
      do {

         int num2 = num;
         numbers = numbers.filter(list -> list % num2 != 0 || list == num2);
         num = numbers.get(numbers.find(list -> list == num2) + 1);
      } while (num * num <= x);

      return numbers;
   }

   public static void testModern() {
      int x = StdIn.readInt();

      ArrayBag<Integer> primeNumbers = sieveModern(x);
      StdOut.println(primeNumbers.size);

      while (!StdIn.isEmpty()) {
         int n = StdIn.readInt();
         if (n > primeNumbers.size)
            StdOut.println("-1");
         else
            StdOut.println(primeNumbers.get(n - 1));
      }
   }

   public static void main(String args[]) {
      // testClassic();
      testModern();
   }
}