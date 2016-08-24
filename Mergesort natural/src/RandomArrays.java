import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomArrays
{
  // creates array with n uniformly generated
  // random numbers in [0..max[
  public static int[] uniform(int n, int max)
  {
    int[] result = new int[n];
    for (int i = 0; i < n; i++)
      result[i] = StdRandom.uniform(max);
    return result;
  }
  
  public static void plus1(int [] a)
  {
    for (int i = 0; i < a.length; i++)
      a[i]++;
  }
  
  // creates array with n uniformly generated
  // random numbers in [1..max]
  public static int[] uniform1(int n, int max)
  {
    int[] result = uniform(n, max);
    plus1(result);
    return result;
  }
  
  // creates array with n uniformly generated
  // unique random numbers in [0..max[  
  public static int[] uniformUnique(int n, int max)
  {
    assert n <= max;
    int[] result = new int[n];
    int[] candidates = new int[max];
    for (int i = 0; i < max; i++)
      candidates[i] = i;
    int maxRandom = max;
    for (int i = 0; i < n; i++)
    {
      int r = StdRandom.uniform(maxRandom);
      result[i] = candidates[r];
      candidates[r] = candidates[maxRandom - 1];
      maxRandom--;
    }
    return result;
  }
  
  // creates array with n uniformly generated
  // unique random numbers in [0..max[  
  public static int[] uniform1Unique(int n, int max)
  {
    assert n <= max;
    int[] result = uniformUnique(n, max);
    plus1(result);
    return result;
  }
  
  // creates sorted array with n uniformly generated
  // unique random numbers in [0..max[  
  public static int[] uniformUniqueSorted(int n, int max)
  {
    assert n <= max;
    int[] result = uniformUnique(n, max);
    Arrays.sort(result);
    return result;
  }
  
  public static int[] uniform1UniqueSorted(int n, int max)
  {
    assert n <= max;
    int[] result = uniformUniqueSorted(n, max);
    plus1(result);
    return result;
  }
  
  private static void show(int a[])
  {
    for (int x : a)
      StdOut.print(" " + x);
    StdOut.println();
  }
  
  public static void testRandomArrays(SupplierRandomArray r, int n, int max)
  {
    int[] a = r.supply(n, max);
    show(a);
  }

  public static void main(String[] args)
  {
    char choice = 'A';
    int x = 10;
    int y = 20;
    if (args.length > 0)
      choice = args[0].charAt(0);
    if (args.length > 1)
      x = Integer.parseInt(args[1]);
    if (args.length > 2)
      y = Integer.parseInt(args[2]);
    if (choice == 'A')
      testRandomArrays((s, m) -> uniform(s, m), x, y);
    else if (choice == 'B')
      testRandomArrays((s, m) -> uniformUnique(s, m), x, y);
    else
      StdOut.println("Illegal option: " + choice);
  }
}

@FunctionalInterface
interface SupplierRandomArray {
    public int [] supply(int size, int max);
}

