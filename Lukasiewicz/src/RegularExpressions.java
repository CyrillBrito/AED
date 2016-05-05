import java.util.regex.Pattern;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.regex.Matcher;

public final class RegularExpressions
{
  private RegularExpressions() { }

  public static final String REGEX_INTEGER = "\\d+";
  public static final String REGEX_DECIMAL = "\\d+\\.\\d+";
  public static final String REGEX_IDENTIFIER = "[a-zA-Z]\\w+";
  public static final String REGEX_SINGLE_NON_WHITESPACE = "\\S";
  public static final String REGEX_TOKEN2 = REGEX_DECIMAL + "|" + REGEX_INTEGER
      + "|" + REGEX_IDENTIFIER + "|" + REGEX_SINGLE_NON_WHITESPACE;

  public static final String REGEX_TOKEN = "\\d+\\.\\d+|\\d+|[a-zA-Z]\\w+|\\S";

  //The following function is adapted from
  //http://docs.oracle.com/javase/tutorial/essential/regex/test_harness.html
  public static void learnRegularExpressions()
  {
    StdOut.print("Enter your regular expression: ");
    String regex = StdIn.readLine();
    Pattern pattern = Pattern.compile(regex);
    StdOut.print("Enter a string to search: ");
    while (StdIn.hasNextLine())
    {
      String line = StdIn.readLine();
      Matcher matcher = pattern.matcher(line);
      int n = 0; // number of matches
      while (matcher.find())
      {
        String group = matcher.group();
        int start = matcher.start();
        int end = matcher.end();
        int size = end - start;
        StdOut.printf("Group: \"%s\". Start: %d. Length: %d\n", group, start,
            size);
        n++;
      }
      StdOut.printf("Number of matches: %d\n", n);
      StdOut.print("Enter another string to search: ");
    }
  }

  public static ArrayBag<String> groups(String s, String regex)
  {
    ArrayBag<String> result = new ArrayBag<>();
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(s);
    while (matcher.find())
    {
      String z = matcher.group();
      // StdOut.println(z);
      result.add(z);
    }
    return result;
  }

  public static ArrayBag<String> tokens(String s)
  {
    return groups(s, REGEX_TOKEN);
  }

  public static void testTokens()
  {
    while (StdIn.hasNextLine())
    {
      String line = StdIn.readLine();
      ArrayBag<String> tokens = tokens(line);
      StdOut.println(ArrayBag.separate(tokens, " "));
    }
  }
  
  public static void main(String[] args)
  {
    char choice = 'A';
    if (args.length > 0)
      choice = args[0].charAt(0);
    if (choice == 'A')
    {
      StdOut.println("Learn");
      learnRegularExpressions();
    }
    else if (choice == 'B')
    {
      StdOut.println("Test tokens");
      testTokens();
    }
  }

}
