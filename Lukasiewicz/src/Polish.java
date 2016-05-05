import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Polish {

   public static boolean polish(String s) {

      Stack<Boolean> stack = new Stack<>();

      for (int i = s.length() - 1; i >= 0; i--) {
         boolean y;
         boolean x;

         switch (s.charAt(i)) {
         case 'A':
            y = stack.pop();
            x = stack.pop();
            stack.push(x || y);
            break;
         case 'K':
            y = stack.pop();
            x = stack.pop();
            stack.push(x && y);
            break;
         case 'I':
            y = stack.pop();
            x = stack.pop();
            stack.push(x || !y);
            break;
         case 'E':
            y = stack.pop();
            x = stack.pop();
            stack.push(x && y || !x && !y);
            break;
         case 'D':
            y = stack.pop();
            x = stack.pop();
            stack.push(!(x && y));
            break;
         case 'N':
            y = stack.pop();
            stack.push(!y);
            break;
         case 'O':
            stack.push(false);
            break;
         default:
            StdOut.print("String errada!");
            break;
         }
      }

      return stack.pop();
   }

   public static void A() {

      while (!StdIn.isEmpty()) {
         String s = StdIn.readString();
         StdOut.println(polish(s));
      }
   }

   public static void main(String args[]) {
      A();
   }
}
