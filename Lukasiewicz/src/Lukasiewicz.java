import java.util.function.Predicate;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Lukasiewicz {

   private static boolean isNumeric(String s) {
      return Character.isDigit(s.charAt(0));
   }

   public static void operationRPN(Stack<Double> stack, String s) {
      double x;
      double y;

      switch (s) {
      case "+":
         y = stack.pop();
         x = stack.pop();
         stack.push(x + y);
         break;
      case "-":
         y = stack.pop();
         x = stack.pop();
         stack.push(x - y);
         break;
      case "*":
         y = stack.pop();
         x = stack.pop();
         stack.push(x * y);
         break;
      case "/":
         y = stack.pop();
         x = stack.pop();
         stack.push(x / y);
         break;
      case "sqrt":
         y = stack.pop();
         stack.push(Math.sqrt(y));
         break;
      default:
         StdOut.print("String errada!");
         break;
      }
   }

   public static double evaluationRPN(String[] ss) {

      Stack<Double> stack = new Stack<>();

      for (String s : ss) {
         if (isNumeric(s))
            stack.push(Double.parseDouble(s));
         else
            operationRPN(stack, s);
      }

      return stack.pop();
   }

   public static ArrayBag<String> infixFullParenToRPN(String[] ss) {
      ArrayBag<String> result = new ArrayBag<>();
      Stack<String> operators = new Stack<>();

      for (String s : ss) {
         if (isNumeric(s))
            result.add(s);
         else if (s.equals(")"))
            result.add(operators.pop());
         else if (s.equals("("))
            ;
         else
            operators.push(s);
      }

      return result;
   }

   public static int operatorPrecedence(String operator) {
      if (operator.equals("+") || operator.equals("-"))
         return 1;
      else if (operator.equals("*") || operator.equals("/"))
         return 2;
      else if (operator.equals("sqrt"))
         return 3;
      else if (operator.equals("("))
         return -1;
      else {
         System.out.println("[operatorPrecedence] Wrong eperator: " + operator);
         return -1;
      }
   }

   public static ArrayBag<String> infixToRPN(String[] ss) {
      ArrayBag<String> result = new ArrayBag<>();
      Stack<String> operators = new Stack<>();

      for (String s : ss) {
         if (isNumeric(s))
            result.add(s);
         else if (s.equals("("))
            operators.push(s);
         else if (s.equals(")")) {
            while (!operators.top().equals("("))
               result.add(operators.pop());
            operators.pop();
         } else {
            if (operators.size() < 1)
               operators.push(s);
            else if (operatorPrecedence(operators.top()) < operatorPrecedence(s))
               operators.push(s);
            else {
               while (!operators.isEmpty() && operatorPrecedence(operators.top()) >= operatorPrecedence(s))
                  result.add(operators.pop());
               operators.push(s);
            }
         }
      }

      for (String op : operators)
         result.add(op);

      return result;
   }

   public static String parenthesize(String[] ss) {
      Stack<String> stack = new Stack<>();
      for (String s : ss)
         stack.push(s);

      return parenthesizeOperand(stack);
   }

   public static String parenthesizeOperand(Stack<String> stack) {

      if (isNumeric(stack.top()))
         return stack.pop();
      else if (stack.top().equals("sqrt")) {
         stack.pop();
         String operand = parenthesizeOperand(stack);
         if (isNumeric(operand))
            operand = ' ' + operand;
         return "(sqrt" + operand + ')';
      } else {
         String operator = stack.pop();
         String operand2 = parenthesizeOperand(stack);
         String operand1 = parenthesizeOperand(stack);
         return '(' + operand1 + operator + operand2 + ')';
      }
   }

   public static String parenthesizeMinimally(String[] ss) {
      Stack<String> stackSS = new Stack<>();
      for (String s : ss)
         stackSS.push(s);

      return minimallyOperand(stackSS, "(", false);
   }

   public static String minimallyOperand(Stack<String> ss, String operator, boolean rightSide) {
      String s = ss.pop();

      if (isNumeric(s))
         return s;

      if (s.equals("sqrt")) {
         String operand = minimallyOperand(ss, s, false);
         if (operand.charAt(0) == 's')
            operand = '(' + operand + ')';
         else if (isNumeric(operand))
            operand = " " + operand;

         return s + operand;
      }

      String operand1 = minimallyOperand(ss, s, true);
      String operand2 = minimallyOperand(ss, s, false);
      String operand = operand2 + s + operand1;
      if (operatorPrecedence(s) == operatorPrecedence(operator) && rightSide && (operator.equals("-") || operator.equals("/")))
         operand = '(' + operand + ')';
      else if (operatorPrecedence(s) < operatorPrecedence(operator))
         operand = '(' + operand + ')';

      return operand;
   }

   public static void B() {

      while (!StdIn.isEmpty()) {
         String str = StdIn.readLine();
         String[] ss = str.split(" ");
         StdOut.println(evaluationRPN(ss));
      }
   }

   public static void C() {
      while (!StdIn.isEmpty()) {
         String str = StdIn.readLine();
         String[] ss = StringUtils.tokens(str).toArray();
         ArrayBag<String> result = infixFullParenToRPN(ss);
         for (int i = 0; i < result.size - 1; i++)
            StdOut.print(result.get(i) + ' ');
         StdOut.println(result.get(result.size - 1));
      }
   }

   public static void D() {
      while (!StdIn.isEmpty()) {
         String str = StdIn.readLine();
         String[] ss = StringUtils.tokens(str).toArray();
         ArrayBag<String> result = infixToRPN(ss);
         for (int i = 0; i < result.size - 1; i++)
            StdOut.print(result.get(i) + ' ');
         StdOut.println(result.get(result.size - 1));
      }
   }

   public static void E() {
      while (!StdIn.isEmpty()) {
         String str = StdIn.readLine();
         String[] ss = str.split(" ");

         StdOut.println(parenthesize(ss));
      }
   }

   public static void F() {
      while (!StdIn.isEmpty()) {
         String str = StdIn.readLine();
         String[] ss = StringUtils.tokens(str).toArray();
         StdOut.println(parenthesizeMinimally(ss));
      }
   }

   public static void main(String args[]) {
      D();
   }
}

class StringUtils {

   private static final char SPACE = ' ';
   private static final char PERIOD = '.';
   private static final char RPAREN = ')';
   private static final char LPAREN = '(';

   public static int countWhile(String s, Predicate<Character> p) {
      int n = s.length();
      int result = 0;
      while (result < n && p.test(s.charAt(result)))
         result++;
      return result;
   }

   public static String take(String s, int n) {
      assert n > 0;
      if (n > s.length())
         n = s.length();
      return s.substring(0, n);
   }

   public static String drop(String s, int n) {
      assert n > 0;
      if (n > s.length())
         n = s.length();
      return s.substring(n, s.length());
   }

   public static String takeWhile(String s, Predicate<Character> p) {
      return take(s, countWhile(s, p));
   }

   public static String dropWhile(String s, Predicate<Character> p) {
      return drop(s, countWhile(s, p));
   }

   public static ArrayBag<String> tokens(String s) {
      ArrayBag<String> result = new ArrayBag<>();
      while (!s.isEmpty()) {
         char c = s.charAt(0);
         if (c == SPACE)
            s = StringUtils.drop(s, 1);
         else if (c == LPAREN || c == RPAREN) {
            result.add(StringUtils.take(s, 1));
            s = StringUtils.drop(s, 1);
         } else if (Character.isLetterOrDigit(c)) {
            result.add(StringUtils.takeWhile(s, x -> Character.isLetterOrDigit(x) || x == PERIOD));
            s = StringUtils.drop(s, result.at(-1).length());
         } else {
            result.add(StringUtils.take(s, 1));
            s = StringUtils.drop(s, 1);
         }
      }
      return result;
   }
}