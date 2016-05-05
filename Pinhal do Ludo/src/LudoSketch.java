import java.awt.Color;
import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import processing.core.PApplet;

/**
 * Draws a tree. The input is: x1 y1 x2 y2. The input represents two points that
 * make a branch of the tree.
 * 
 * @author cyrillbrito
 */
public class LudoSketch extends PApplet {

   private static final int WIDTH = 800; // width of window
   private static final int HEIGHT = 600; // height of window
   private static final int HERTZ = 3; // frequency
   private static final int INITIALDELAY = 5; // initial delay, in seconds
   private static final int MARGIN = 20;

   private double startTime; // time when the animation started
   private int steps; // number of steps in the animation

   private QuickUnionWeighted QUW;
   private ArrayBag<Segment> segments;
   private Iterator<Segment> it;
   private int width;
   private int height;

   public void settings() {
      size(WIDTH, HEIGHT);
   }

   public void setup() {

      segments = new ArrayBag<>();
      ludo();
      StdOut.println("Starting");
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
         if (it.hasNext())
            segments.add(it.next());
      }
   }

   private void display() {
      for (Segment segment : segments) {
         strokeWeight(4);
         stroke(Color.DARK_GRAY.hashCode());
         if (!it.hasNext()) {
            int size = QUW.size(Point.to1D(segment.p1, width)) - 1;
            if (size == 1)
               stroke(Color.BLUE.hashCode());
            else if (size == 2)
               stroke(Color.RED.hashCode());
            else if (size == 3)
               stroke(Color.GREEN.hashCode());
            else if (size == 4)
               stroke(Color.MAGENTA.hashCode());
            else if (size == 5)
               stroke(Color.ORANGE.hashCode());
            else if (size == 6)
               stroke(Color.YELLOW.hashCode());
         }
         double widthUnit = (WIDTH - 2 * MARGIN) / width;
         double heightUnit = (HEIGHT - 2 * MARGIN) / height;

         int x1 = (int) (MARGIN + segment.p1.x * widthUnit);
         int y1 = (int) (MARGIN + segment.p1.y * heightUnit);
         int x2 = (int) (MARGIN + segment.p2.x * widthUnit);
         int y2 = (int) (MARGIN + segment.p2.y * heightUnit);
         line(x1, y1, x2, y2);
      }
   }

   private void ludo() {
      width = StdIn.readInt();
      height = StdIn.readInt();

      ArrayBag<Segment> segments = new ArrayBag<>();
      while (!StdIn.isEmpty()) {
         Point p1 = new Point(StdIn.readInt(), StdIn.readInt());
         Point p2 = new Point(StdIn.readInt(), StdIn.readInt());

         segments.add(new Segment(p1, p2));
      }

      QUW = new QuickUnionWeighted(width * height);
      ArrayBag<Segment> segmentsFinal = new ArrayBag<>();
      for (Segment segment : segments) {
         int index1 = Point.to1D(segment.p1, width);
         int index2 = Point.to1D(segment.p2, width);

         if (!QUW.connected(index1, index2)) {
            QUW.union(index1, index2);
            segmentsFinal.add(segment);
         }
      }

      it = segmentsFinal.iterator();
   }

   public static Point addPoint(Point p, HashTable<Point, Integer> points) {
      for (Point point : points)
         if (point.x == p.x && point.y == p.y)
            return point;

      points.put(p, points.size());
      return p;
   }

   public static void main(String[] args) {
      PApplet.main(new String[] { LudoSketch.class.getName() });
   }
}