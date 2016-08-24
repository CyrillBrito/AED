
public final class Utils
{
  private Utils() { }
  
  public static <T> int find(T[] a, T x)
  {
    for (int i = 0; i < a.length; i++)
      if (x.equals(a[i]))
        return i;
    return -1;
  }
  
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
    // I acknowledge this could be more efficient, but it is simple and good
    // enough for small tasks.
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
  
  public static String stringOf(int[] a, String separator)
  {
    // I acknowledge this could be more efficient, but it is simple and good
    // enough for small tasks.
    String result = "";
    int n = a.length;
    if (n > 0)
    {
      result += Integer.toString(a[0]);
      for (int i = 1; i < n; i++)
        result += separator + a[i];
    }
    return result;
  }
      
  public static <T> T[] slice(T[] a, int start, int n)
  {
    T[] result = newArrayLike(a, n);
    for (int i = 0; i < n; i++)
      result[i] = a[start+i];
    return result;
  }

  public static <T> void copyFrom(T[] a, T[] b)
  {
    assert a.length == b.length;
    for (int i = 0; i < a.length; i++)
      a[i] = b[i];
  }
  
  public static <T> void copyFrom(T[] a, T[] b, int x, int n)
  {
    int j = x;
    for (int i = 0; i < n; i++)
      a[i] = b[j++];
  }
  
  public static <T> T[] inverse(T[] a)
  {
    int n = a.length;
    T[] result = newArrayLike(a, n);
    for (int i = 0; i < n; i++)
      result[i] = a[n-1-i];
    return result;
  }
  
  public static <T> T[] concatenate(T[] a, T[] b)
  {
    T[] result = newArrayLike(a, a.length + b.length);
    for (int i = 0; i < a.length; i++)
      result[i] = a[i];
    for (int i = 0; i < b.length; i++)
      result[a.length + i] = b[i];
    return result;
  }
  
  public static <T> T[] concatenate(T[] a, T[] b, T[] c)
  {
    T[] result = newArrayLike(a, a.length + b.length + c.length);
    for (int i = 0; i < a.length; i++)
      result[i] = a[i];
    for (int i = 0; i < b.length; i++)
      result[a.length + i] = b[i];
    for (int i = 0; i < c.length; i++)
      result[a.length + b.length + i] = c[i];
    return result;
  }
    
  @SuppressWarnings("unchecked")
  public static <T> T[] newArrayLike(T[] a, int n)
  {
    T[] result = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), n);
    return result;
  }

  @SuppressWarnings("unchecked")
  public static <T> T[] newArrayWith(T a, int n)
  {
    T[] result = (T[]) java.lang.reflect.Array.newInstance(a.getClass(), n);
    return result;
  }
  
  @SuppressWarnings("unchecked")
  public static <T> T[] newArrayOf(Class<T> c, int n)
  {
    T[] result = (T[]) java.lang.reflect.Array.newInstance(c, n);
    return result;
  }
  

}
