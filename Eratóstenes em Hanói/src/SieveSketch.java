import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import processing.core.PApplet;

/**
 * Draws list of numbers between 1 and the inputed numbers. Starts removing
 * numbers until only prime numbers are showing.
 * 
 * @author cyrillbrito
 */
public class SieveSketch extends PApplet {

   private static final int MINWIDTH = 128; // minimum width of window
   private static final int MINHEIGHT = 128; // max width of window
   private static final int HSIDE = 40; // width of each number
   private static final int VSIDE = 20; // height of each number
   private static final int NPERLINE = 20; // n Numbers per line
   private static final int MARGIN = 10; // margin
   private static final double HERTZ = 20; // frequency
   private static final double INITIALDELAY = 2; // initial delay, in seconds

   private double startTime; // time when the animation started
   private int steps; // number of steps in the animation
   private int blue = color(0, 0, 255); // BLUE;
   private int red = color(255, 0, 0); // RED;

   private static int size;
   private boolean[] current; // current values being shown
   private ArrayBag<Integer> numbers; // the number read from the console
   private Iterator<Integer> it; // iterator for the above

   public void settings() {
      int w = max(MINWIDTH, NPERLINE * HSIDE + 2 * MARGIN);
      int h = max(MINHEIGHT, 25 * VSIDE + 2 * MARGIN);
      size(w, h);
   }

   public void setup() {
      numbers = eratosthenes();
      it = numbers.iterator();
      current = new boolean[size + 1];
      startTime = millis() + INITIALDELAY * 1000;
      steps = 0;
   }

   public void draw() {
      background(200);
      update();
      display();
   }

   private void update() {
      double seconds = (millis() - startTime) / 1000.0;
      int timeUnits = (int) (seconds * HERTZ);
      if (timeUnits > steps) {
         steps++;
         if (it.hasNext()) {
            int m = it.next();
            current[m] = true;
         }
      }
   }

   void drawNumbers(int color) {
      rectMode(CENTER);
      textAlign(CENTER, CENTER);
      fill(color);
      for (int i = 1; i <= size; i++)
         if (!current[i]) {
            float cx = MARGIN + ((i - 1) % NPERLINE) * HSIDE + HSIDE / 2;
            float cy = MARGIN + ((i - 1) / NPERLINE) * VSIDE + VSIDE / 2;
            text(str(i), cx, cy, HSIDE, VSIDE);
         }
   }

   private void display() {
      int c = it.hasNext() ? red : blue; // change to blue when done
      drawNumbers(c);
   }

   // -------------------------------------
   public static ArrayBag<Integer> eratosthenes() {
      size = StdIn.readInt();
      ArrayBag<Integer> numbers = new ArrayBag<>();

      int num = 2;
      do {
         rmMultiple(num, size, numbers);
         num = nextNumber(num, size, numbers);
      } while (num * num <= size);
      return numbers;
   }

   public static void rmMultiple(int num, int x, ArrayBag<Integer> numbers) {
      for (int i = num * num; i <= x; i += num) {
         int a = i;
         if (numbers.find(list -> list == a) == -1)
            numbers.add(i);
      }
   }

   public static int nextNumber(int num, int x, ArrayBag<Integer> numbers) {
      for (int i = num + 1; i <= x; i++) {
         int a = i;
         if (numbers.find(list -> list == a) == -1)
            return i;
      }
      return -1;
   }

   public static void main(java.lang.String[] args) {
      PApplet.main(new String[] { SieveSketch.class.getName() });
   }
}