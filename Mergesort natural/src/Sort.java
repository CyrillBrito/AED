import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author Pedro Guerreiro
 * Algoritmos e Estruturas de Dados
 * Universidade do Algarve
 * 2014
 */

public abstract class Sort<T extends Comparable<T>>
{
  public abstract void sort(T[] a);

  public boolean less(T x, T y)
  {
    return x.compareTo(y) < 0;
  }

  public boolean greater(T x, T y)
  {
    return x.compareTo(y) > 0;
  }

  public boolean equal(T x, T y)
  {
    return x.compareTo(y) == 0;
  }
  
  public T min(T x, T y)
  {
    return less(x, y) ? x : y;
  }

  public T max(T x, T y)
  {
    return less(y, x) ? x : y;
  }

  public void exchange(T[] a, int x, int y)
  {
    T m = a[x];
    a[x] = a[y];
    a[y] = m;
  }

  public boolean isSorted(T[] a)
  {
    for (int i = 1; i < a.length; i++)
      if (less(a[i], a[i - 1]))
        return false;
    return true;
  }
  
// General functions for testing
  
  public static Integer[] integersFromInts(int[] a)
  {
    Integer[] result = new Integer[a.length];
    for (int i = 0; i < a.length; i++)
      result[i] = a[i];
    return result;
  }
  
  public static int[] intsFromIntegers(Integer[] a)
  {
    int[] result = new int[a.length];
    for (int i = 0; i < a.length; i++)
      result[i] = a[i];
    return result;
  }

  public static <T> String stringOf(T[] a, String separator)
  {
    String result = "";
    int n = a.length;
    if (n > 0)
    {
      result += a[0].toString();
      for (int i = 1; i < n; i++)
        result += separator + a[i];
    }
    return result;
  }

//General test functions

  public static void testIntegers(Sort<Integer> s)
  {
    Integer[] a = integersFromInts(StdIn.readAllInts());
    s.sort(a);
    StdOut.println(stringOf(a, "\n"));
  }

  public static void testStrings(Sort<String> s)
  {
    String[] a = StdIn.readAllStrings();
    s.sort(a);
    StdOut.println(stringOf(a, "\n"));
  }

  public static void testDoubleRatio(Sort<Integer> s, int size, int max, int times)
  {
    SortDoubleRatio.test(s, size, max, times);
  }

  public static void testDoubleRatio(Sort<Integer> s, int size, int max, int times, IntsSupplier u)
  {
    SortDoubleRatio.test(s, size, max, times, u);
  }

}

@FunctionalInterface
interface IntsSupplier
{
  public int[] supply(int n, int max);
}
