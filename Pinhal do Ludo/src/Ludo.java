import java.util.HashMap;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Ludo {

   public static HashMap<Integer, Integer> ludo(ArrayBag<Segment> segments, int width, int height) {

      QuickUnionWeighted QUW = new QuickUnionWeighted(width * height);
      for (Segment segment : segments) {
         int index1 = Point.to1D(segment.p1, width);
         int index2 = Point.to1D(segment.p2, width);

         if (!QUW.connected(index1, index2))
            QUW.union(index1, index2);
      }

      // sizes <parent, size>
      HashMap<Integer, Integer> sizes = new HashMap<>();
      for (Segment segment : segments) {
         int parent = QUW.find(Point.to1D(segment.p1, width));
         if (!sizes.containsKey(parent))
            sizes.put(parent, QUW.size(parent));
      }

      // sizesFinal <size, count>
      HashMap<Integer, Integer> sizesFinal = new HashMap<>();
      for (int size : sizes.values()) {
         if (!sizesFinal.containsKey(size))
            sizesFinal.put(size, 1);
         else
            sizesFinal.put(size, sizesFinal.get(size) + 1);
      }

      return sizesFinal;
   }

   public static void test() {
      int width = StdIn.readInt();
      int height = StdIn.readInt();

      ArrayBag<Segment> segments = new ArrayBag<>();
      while (!StdIn.isEmpty()) {
         Point p1 = new Point(StdIn.readInt(), StdIn.readInt());
         Point p2 = new Point(StdIn.readInt(), StdIn.readInt());

         segments.add(new Segment(p1, p2));
      }

      HashMap<Integer, Integer> sizes = ludo(segments, width, height);
      for (int i = 2; i < width * height; i++)
         if (sizes.containsKey(i))
            StdOut.println(i - 1 + " " + sizes.get(i));
   }

   public static void main(String args[]) {
      test();
   }
}

class Point {
   public int x;
   public int y;

   public Point(int x, int y) {
      this.x = x;
      this.y = y;
   }

   public static Point to2D(int oneDimention, int width) {
      int x = oneDimention % width;
      int y = oneDimention / width;
      return new Point(x, y);
   }

   public static int to1D(Point p, int width) {
      return p.x + width * p.y;
   }
}

class Segment {
   public Point p1;
   public Point p2;

   public Segment(Point p1, Point p2) {
      this.p1 = p1;
      this.p2 = p2;
   }
}