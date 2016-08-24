import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class SortDoubleRatio
{
  private static double tMin = 0.5; // minimum duration of a test in the double ratio experiment

  public static double timing(Sort<Integer> s, Integer[] a)
  {
    Stopwatch timer = new Stopwatch();
    s.sort(a);
    double result = timer.elapsedTime();
    assert s.isSorted(a);  // just in case something went wrong...
    return result;
  }
  
  public static double[] doubleRatio(Sort<Integer> s, int size, int max, int times)
  {
    double[] result = new double[times];
    for (int i = 0; i < times; i++, size *= 2, max *= 2)
    {
      double t = 0.0;
      int n = 0;
      while (t < tMin)
      {
        Integer[] a = Sort.integersFromInts(RandomArrays.uniform(size, max));
        t += timing(s, a);
        n++;
      }
      result[i] = t / n;
    }      
    return result;
  }
  
  public static double[] doubleRatio(Sort<Integer> s, int size, int max, int times, IntsSupplier u)
  {
    double[] result = new double[times];
    for (int i = 0; i < times; i++, size *= 2, max *= 2)
    {
      double t = 0.0;
      int n = 0;
      while (t < tMin)
      {
        Integer[] a = Sort.integersFromInts(u.supply(size, max));
        t += timing(s, a);
        n++;
      }
      result[i] = t / n;
    }      
    return result;
  }
  
  private static void printTable(int size, double[] t)
  {
    double t0 = 0.0;
    for (int i = 0; i < t.length; i++, size *= 2)
    {
      StdOut.printf("%d %.4f", size, t[i]);
      if (t0 > 0.0) // for i == 0 and also in case t[i] == 0
        StdOut.printf(" %.2f", t[i] / t0);
      StdOut.println();
      t0 = t[i];
    }
  }

  public static void test(Sort<Integer> s, int size, int max, int times)
  {
    double z[] = doubleRatio(s, size, max, times);
    printTable(size, z);
  }
  
  public static void test(Sort<Integer> s, int size, int max, int times, IntsSupplier u)
  {
    double z[] = doubleRatio(s, size, max, times, u);
    printTable(size, z);
  }
}

