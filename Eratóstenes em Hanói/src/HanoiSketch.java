import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import processing.core.PApplet;

/**
 * Animation of the game Hanoi being solved. The input is the number of pieces.
 * 
 * @author cyrillbrito
 */
public class HanoiSketch extends PApplet {

   private static final int WIDTH = 1000; // width of window
   private static final int HEIGHT = 600; // height of window
   private static final double HERTZ = 10; // frequency
   private static final double INITIALDELAY = 2; // initial delay, in seconds
   private static final int MARGIN = 30;
   private static final int STICK_W = 20;

   private double startTime; // time when the animation started
   private int steps; // number of steps in the animation
   private int baseColor = color(182, 155, 76);
   private int pieceColor = color(100, 221, 100);

   private ArrayBag<Integer>[] stacks;
   private int size; // number of pieces
   private Iterator<int[]> it;
   private int STICK_H;
   private int[] STICK_X;
   private int STICK_Y;

   private int PIECE_W;
   private static final int PIECE_H = -50;

   public void settings() {
      size(WIDTH, HEIGHT);
   }

   @SuppressWarnings("unchecked")
   public void setup() {
      it = hanoi();

      stacks = new ArrayBag[3];
      stacks[0] = new ArrayBag<>();
      stacks[1] = new ArrayBag<>();
      stacks[2] = new ArrayBag<>();
      for (int i = size; i > 0; i--)
         stacks[0].add(i);

      int usableSpace = WIDTH - (MARGIN * 4);
      int start = MARGIN * 2;
      STICK_X = new int[3];
      STICK_X[0] = usableSpace / 6 + start;
      STICK_X[1] = usableSpace / 6 * 3 + start;
      STICK_X[2] = usableSpace / 6 * 5 + start;

      STICK_Y = HEIGHT - MARGIN - 2 * STICK_W;
      STICK_H = -(HEIGHT - STICK_W * 2 - MARGIN * 2);

      PIECE_W = usableSpace / 3 / size;

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
            int[] move = it.next();
            StdOut.println(move[0] + " -> " + move[1]);
            int movedItem = stacks[move[0]].get(stacks[move[0]].size() - 1);
            stacks[move[0]] = stacks[move[0]].filter(p -> p != movedItem);
            stacks[move[1]].add(movedItem);
         }
      }
   }

   private void display() {
      fill(baseColor);
      noStroke();
      rect(MARGIN, HEIGHT - MARGIN, WIDTH - 2 * MARGIN, -STICK_W * 2);

      rect(STICK_X[0] - STICK_W / 2, STICK_Y, STICK_W, STICK_H);
      rect(STICK_X[1] - STICK_W / 2, STICK_Y, STICK_W, STICK_H);
      rect(STICK_X[2] - STICK_W / 2, STICK_Y, STICK_W, STICK_H);

      fill(pieceColor);
      stroke(0);
      for (int i = 0; i < 3; i++) {
         int nPieces = 0;
         for (int n : stacks[i])
            rect(STICK_X[i] - PIECE_W / 2 * n, STICK_Y + PIECE_H * nPieces++, PIECE_W * n, PIECE_H);
      }
   }

   // ---------------------------------------------------------------------------------

   private static void solveNow(int n, int x, int y, int z, Bag<int[]> r) {
      if (n > 0) {
         solveNow(n - 1, x, z, y, r);
         r.add(new int[] { x, y });
         solveNow(n - 1, z, y, x, r);
      }
   }

   private static Bag<int[]> solve(int n, int x, int y, int z) {
      Bag<int[]> result = new Bag<>();
      solveNow(n, x, y, z, result);
      return result;
   }

   private Iterator<int[]> hanoi() {
      size = StdIn.readInt();
      Bag<int[]> solution = solve(size, 0, 1, 2);
      return solution.iterator();
   }

   public static void main(String args[]) {
      PApplet.main(new String[] { HanoiSketch.class.getName() });
   }
}