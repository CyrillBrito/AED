
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import processing.core.PApplet;

//Binary Search Trees

public abstract class Tree<T extends Comparable<T>> implements Iterable<T> {
   public abstract boolean invariant();

   public abstract T value(); // requires !isEmpty();

   public abstract int size();

   public abstract boolean isEmpty();

   public abstract boolean has(T x);

   public abstract Tree<T> left(); // requires !isEmpty();

   public abstract Tree<T> right(); // requires !isEmpty();

   public abstract Tree<T> put(T x);

   public abstract Tree<T> delete(T x);

   public abstract Tree<T> remove(); // requires !isEmpty();

   public abstract T minimum(); // requires !isEmpty();

   public abstract T maximum(); // requires !isEmpty();

   public abstract T select(int k); // requires 0 <= k < size();

   public abstract int rank(T x);

   public abstract T floor(T x);

   public abstract T ceiling(T x);

   protected abstract Tree<T> first();

   protected abstract Tree<T> last();

   protected abstract Tree<T> removeFirst();

   public abstract String indent(String margin, String tab);

   public abstract String diagram(String margin, String tab);

   public abstract void draw(PApplet p, double x0, double y0, double w, double h, double r);

   public abstract void preorder(Consumer<T> f);

   public abstract void inorder(Consumer<T> f);

   public abstract void postorder(Consumer<T> f);

   private class TreeIterator implements Iterator<T> {
      private Stack<Tree<T>> i;

      public TreeIterator() {
         i = new Stack<>();
         if (!Tree.this.isEmpty())
            i.push(Tree.this);
      }

      public boolean hasNext() {
         return !i.isEmpty();
      }

      public T next() {
         Tree<T> t = i.pop();
         T result = t.value();
         if (!t.left().isEmpty()) {
            i.push(new Cons<T>(result, new Empty<T>(), t.right()));
            i.push(t.left());
            result = next();
         } else if (!t.right().isEmpty())
            i.push(t.right());
         return result;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   public Iterator<T> iterator() {
      return new TreeIterator();
   }

   // Exercises

   public abstract T fold(BinaryOperator<T> f, T zero);

   public abstract boolean isLeaf();

   public abstract int countLeaves();

   public abstract Tree<T> trimLeaves();

   public abstract Tree<T> cut(int n);

   public static final String REGEX_TOKEN = "\\-?\\d+|\\(|\\)";

   protected static String newLine = System.getProperty("line.separator");
   public final boolean SHOW_FRAMES = false;
   public final int COLOR_FRAMES = Colors.GREEN;
   public final int COLOR_VALUES = Colors.BLACK;
   public final int COLOR_TEXT = Colors.YELLOW;
   public final int COLOR_LINES = Colors.DARKRED;

   public static ArrayBag<String> groups(String s, String regex) {
      ArrayBag<String> result = new ArrayBag<>();
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(s);
      while (matcher.find()) {
         String z = matcher.group();
         result.add(z);
      }
      return result;
   }

   public static Tree<Integer> fromString(String s) {
      ArrayBag<String> tokens = groups(s, REGEX_TOKEN);
      Queue<String> queue = new Queue<>();
      tokens.visit(x -> queue.enqueue(x));
      return fromQueue(queue);
   }

   public static Tree<Integer> fromQueue(Queue<String> q) {
      Tree<Integer> result = new Empty<Integer>();
      assert "(".equals(q.front());
      q.dequeue();
      if (!")".equals(q.front())) {
         int x = Integer.parseInt(q.dequeue());
         Tree<Integer> left = fromQueue(q);
         Tree<Integer> right = fromQueue(q);
         result = new Cons<Integer>(x, left, right);
      }
      assert ")".equals(q.front());
      q.dequeue();
      return result;
   }

   private final Consumer<T> write = x -> StdOut.print(" " + x);

   public void stats() {
      StdOut.println(size());
      StdOut.println(countLeaves());
      inorder(write);
      StdOut.println();
      preorder(write);
      StdOut.println();
      postorder(write);
      StdOut.println();
      Tree<T> t1 = trimLeaves();
      Tree<T> t2 = cut(2);
      StdOut.println(t1);
      StdOut.println(t2);
   }

   public static Tree<Integer> naturals(int x, int n) {
      return n == 0 ? new Empty<>() : new Cons<>(x, naturals(2 * x, n - 1), naturals(2 * x + 1, n - 1));
   }

   // --------------------
   // Functions to be programmed by you in derived classes

   public abstract boolean isomorphs(Tree<T> u);

   public abstract int height();

   public abstract int unbalance();

   // -------------------

   public static void testTree() {
      Tree<Integer> empty = new Empty<Integer>();
      StdOut.println(empty.toString());
      Tree<Integer> t1 = new Cons<Integer>(6, empty, empty);
      System.out.println(t1);
      StdOut.printf("%d %b %b\n", t1.size(), t1.has(6), t1.has(25));
      Tree<Integer> t2 = new Cons<Integer>(4, empty, empty);
      StdOut.println(t2);
      StdOut.printf("%d %b %b\n", t2.size(), t2.has(4), t2.has(6));
      Tree<Integer> t3 = new Cons<Integer>(15, t1, t2);
      StdOut.println(t3);
      StdOut.printf("%d %b %b\n", t3.size(), t3.has(4), t3.has(15));
   }

   public static void testReadIndent() {
      while (StdIn.hasNextLine()) {
         String line = StdIn.readLine();
         Tree<Integer> t = Tree.fromString(line);
         StdOut.println(t.indent("", "  "));
      }
   }

   public static void testRead() {
      Tree<Integer> t1 = Tree.fromString("(5()())");
      StdOut.println(t1);
      Tree<Integer> t2 = Tree.fromString("(15(6()())(4()(5()())))");
      StdOut.println(t2);
      Tree<Integer> t3 = Tree.fromString("(5(-7(10(666()())())(-100()()))(6(17()())(88()())))");
      StdOut.println(t3);
      while (StdIn.hasNextLine()) {
         String line = StdIn.readLine();
         Tree<Integer> t = Tree.fromString(line);
         StdOut.println(t);
      }
   }

   public static void testPut() {
      Tree<Integer> t = new Empty<Integer>();
      while (!StdIn.isEmpty()) {
         int x = StdIn.readInt();
         t = t.put(x);
         StdOut.println(t);
      }
   }

   public static void testFold() {
      Tree<Integer> t = new Empty<Integer>();
      while (!StdIn.isEmpty()) {
         int x = StdIn.readInt();
         t = t.put(x);
         StdOut.println(t);
         int sum = t.fold((a, b) -> a + b, 0);
         StdOut.println(sum);
         int min = t.fold((a, b) -> Math.min(a, b), Integer.MAX_VALUE);
         StdOut.println(min);
      }
   }

   public static void testDiagram() {
      Tree<Integer> t = new Empty<Integer>();
      while (!StdIn.isEmpty()) {
         int x = StdIn.readInt();
         t = t.put(x);
      }
      StdOut.println(t);
      StdOut.println(t.diagram("", "   "));
   }

   public static void testNaturals() {
      int x = StdIn.readInt();
      int n = StdIn.readInt();
      Tree<Integer> t = Tree.naturals(x, n);
      StdOut.println(t);
      StdOut.println(t.diagram("", "   "));
   }

   public static void testIsomorphs() {
      String[] lines = StdIn.readAllLines();
      ArrayBag<Tree<Integer>> t = new ArrayBag<Tree<Integer>>();
      for (String s : lines)
         t.add(fromString(s));
      int n = t.size();
      StdOut.println(n);
      for (int i = 0; i < n; i++)
         for (int j = i + 1; j < n; j++)
            if (t.at(i).isomorphs(t.at(j)))
               StdOut.println(i + " " + j + t.at(i) + t.at(j));
   }

   public static void testHeight() {
      while (StdIn.hasNextLine()) {
         Tree<Integer> t = Tree.fromString(StdIn.readLine());
         int z = t.height();
         StdOut.println(z);
      }
   }

   public static void testUnbalance() {
      while (StdIn.hasNextLine()) {
         Tree<Integer> t = Tree.fromString(StdIn.readLine());
         int z = t.unbalance();
         StdOut.println(z);
      }
   }

   public static void main(String[] args) {
      char choice = 'B';
      if (args.length > 0)
         choice = args[0].charAt(0);
      if (choice == 'A')
         testIsomorphs();
      else if (choice == 'B')
         testHeight();
      else if (choice == 'C')
         testUnbalance();
      else
         System.out.printf("Illegal choice: %s\n", args[0]);
   }

}

// ---------------------

class Empty<T extends Comparable<T>> extends Tree<T> {
   public boolean invariant() {
      return true;
   }

   public T value() {
      throw new NoSuchElementException();
   }

   public int size() {
      return 0;
   }

   public boolean isEmpty() {
      return true;
   }

   public boolean has(T x) {
      return false;
   }

   public String toString() {
      return "()";
   }

   public Tree<T> left() {
      throw new NoSuchElementException();
   }

   public Tree<T> right() {
      throw new NoSuchElementException();
   }

   public Tree<T> put(T x) {
      return new Cons<>(x, this, this);
   }

   public Tree<T> delete(T x) {
      return this;
   }

   public Tree<T> remove() {
      throw new NoSuchElementException();
   }

   public String indent(String margin, String tab) {
      return margin + "-" + newLine;
   }

   public String diagram(String margin, String tab) {
      return margin + "-" + newLine;
   }

   public void draw(PApplet p, double x0, double y0, double w, double h, double r) {
      // nothing to do
   }

   public T fold(BinaryOperator<T> f, T zero) {
      return zero;
   }

   public boolean isLeaf() {
      throw new UnsupportedOperationException();
   }

   public int countLeaves() {
      return 0;
   }

   public void preorder(Consumer<T> c) {
      // Nothing to do
   }

   public void inorder(Consumer<T> c) {
      // Nothing to do
   }

   public void postorder(Consumer<T> c) {
      // Nothing to do
   }

   public Tree<T> trimLeaves() {
      return this;
   }

   public Tree<T> cut(int n) {
      return this;
   }

   public T minimum() {
      throw new NoSuchElementException();
   }

   public T maximum() {
      throw new NoSuchElementException();
   }

   @Override
   public T select(int k) {
      throw new NoSuchElementException();
   }

   public int rank(T x) {
      return 0;
   }

   public T floor(T x) {
      throw new NoSuchElementException();
   }

   public T ceiling(T x) {
      throw new NoSuchElementException();
   }

   protected Tree<T> first() {
      throw new NoSuchElementException();
   }

   protected Tree<T> last() {
      throw new NoSuchElementException();
   }

   protected Tree<T> removeFirst() {
      throw new NoSuchElementException();
   }

   public boolean isomorphs(Tree<T> u) {
      return u.isEmpty();
   }

   public int height() {
      return 0;
   }

   public int unbalance() {
      return 0;
   }

}

// ------------------------

class Cons<T extends Comparable<T>> extends Tree<T> {
   private final T value;
   private final Tree<T> left;
   private final Tree<T> right;
   private final int size;

   public Cons(T value, Tree<T> left, Tree<T> right) {
      assert (left.isEmpty() || value.compareTo(left.value()) > 0)
            && (right.isEmpty() || value.compareTo(right.value()) < 0);
      this.value = value;
      this.left = left;
      this.right = right;
      this.size = 1 + left.size() + right.size();
      assert invariant();
   }

   public boolean invariant() {
      return (left.isEmpty() || value.compareTo(left.value()) > 0)
            && (right.isEmpty() || value.compareTo(right.value()) < 0) && size == 1 + left.size() + right.size()
            && left.invariant() && right.invariant();
   }

   public T value() {
      return value;
   }

   public int size() {
      return size;
   }

   public Tree<T> left() {
      return left;
   }

   public Tree<T> right() {
      return right;
   }

   public boolean isEmpty() {
      return false;
   }

   public boolean has(T x) {
      boolean result = true;
      int cmp = x.compareTo(value);
      if (cmp < 0)
         result = left.has(x);
      else if (cmp > 0)
         result = right.has(x);
      return result;
   }

   public Tree<T> put(T x) {
      Tree<T> result = this;
      int cmp = x.compareTo(value);
      if (cmp < 0)
         result = new Cons<T>(value, left.put(x), right);
      else if (cmp > 0)
         result = new Cons<T>(value, left, right.put(x));
      return result;
   }

   public Tree<T> first() {
      Tree<T> result = this;
      if (!left.isEmpty())
         result = left.first();
      return result;
   }

   public Tree<T> last() {
      Tree<T> result = this;
      if (!right.isEmpty())
         result = right.last();
      return result;
   }

   public T minimum() {
      return first().value();
   }

   public T maximum() {
      return last().value();
   }

   public T select(int k) {
      T result = value;
      int t = left.size();
      if (k < t)
         result = left.select(k);
      else if (k > t)
         result = right.select(k - t - 1);
      return result;
   }

   public int rank(T x) {
      int result;
      int cmp = x.compareTo(value);
      if (cmp < 0)
         result = left.rank(x);
      else if (cmp > 0)
         result = 1 + left.size() + right.rank(x);
      else
         result = left.size();
      return result;
   }

   public T floor(T x) {
      T result = value;
      int cmp = x.compareTo(value);
      if (cmp < 0)
         result = left.floor(x);
      else if (cmp > 0) {
         T t = right.floor(x);
         if (t != null)
            result = t;
      }
      return result;
   }

   public T ceiling(T x) {
      T result = value;
      int cmp = x.compareTo(value);
      if (cmp > 0)
         result = right.ceiling(x);
      else if (cmp < 0) {
         T t = left.ceiling(x);
         if (t != null)
            result = t;
      }
      return result;
   }

   public String toString() {
      return "(" + value + left.toString() + right.toString() + ")";
   }

   public String indent(String margin, String tab) {
      String result = "";
      result += margin + value + newLine;
      result += left.indent(margin + tab, tab);
      result += right.indent(margin + tab, tab);
      return result;
   }

   public String diagram(String margin, String tab) {
      String result = "";
      result += right.diagram(margin + tab, tab);
      result += margin + value + newLine;
      result += left.diagram(margin + tab, tab);
      return result;
   }

   public void draw(PApplet p, double x0, double y0, double w, double h, double r) {
      double x = x0 + left.size() * w;
      double y = y0;
      double xc = x + w / 2;
      double yc = y + r;

      if (SHOW_FRAMES) {
         p.noFill();
         p.stroke(COLOR_FRAMES);
         p.rect((float) x, (float) y, (float) w, (float) h);
      }
      p.stroke(COLOR_LINES);
      p.fill(COLOR_LINES);
      if (left.isEmpty())
         p.line((float) xc, (float) yc, (float) x, (float) (yc + h / 2));
      else
         p.line((float) xc, (float) yc, (float) (xc - w * (1 + left.right().size())), (float) (y + h));

      if (right.isEmpty())
         p.line((float) xc, (float) yc, (float) (x + w), (float) (yc + h / 2));
      else
         p.line((float) xc, (float) yc, (float) (xc + w * (1 + right.left().size())), (float) (y + h));

      p.fill(COLOR_VALUES);
      p.stroke(COLOR_VALUES);
      p.ellipseMode(PApplet.CENTER);
      p.ellipse((float) xc, (float) yc, 2 * (float) r, 2 * (float) r);
      p.textAlign(PApplet.CENTER, PApplet.CENTER);
      p.fill(COLOR_TEXT);
      p.text(value.toString(), (float) xc, (float) yc);

      left.draw(p, x0, y0 + h, w, h, r);
      right.draw(p, x + w, y0 + h, w, h, r);

   }

   public T fold(BinaryOperator<T> f, T zero) {
      return f.apply(f.apply(value, left.fold(f, zero)), right.fold(f, zero));
   }

   public boolean isLeaf() {
      return left.isEmpty() && right.isEmpty();
   }

   public int countLeaves() {
      return isLeaf() ? 1 : left.countLeaves() + right.countLeaves();
   }

   public void preorder(Consumer<T> c) {
      c.accept(value);
      left.preorder(c);
      right.preorder(c);
   }

   public void inorder(Consumer<T> c) {
      left.inorder(c);
      c.accept(value);
      right.inorder(c);
   }

   public void postorder(Consumer<T> c) {
      left.postorder(c);
      right.postorder(c);
      c.accept(value);
   }

   public Tree<T> trimLeaves() {
      return isLeaf() ? new Empty<>() : new Cons<T>(value, left.trimLeaves(), right.trimLeaves());
   }

   public Tree<T> cut(int n) {
      assert n >= 0;
      return n == 0 ? new Empty<>() : new Cons<T>(value, left.cut(n - 1), right.cut(n - 1));
   }

   protected Tree<T> removeFirst() {
      Tree<T> result = right; // if left is empty, result is right
      if (!left.isEmpty())
         result = new Cons<T>(value, left.removeFirst(), right);
      return result;
   }

   public Tree<T> remove() {
      Tree<T> result;
      if (right.isEmpty())
         result = left;
      else if (left.isEmpty())
         result = right;
      else
         result = new Cons<T>(right.first().value(), left, right.removeFirst());
      return result;
   }

   public Tree<T> delete(T x) {
      Tree<T> result;
      int cmp = x.compareTo(value);
      if (cmp < 0)
         result = new Cons<T>(value, left.delete(x), right);
      else if (cmp > 0)
         result = new Cons<T>(value, left, right.delete(x));
      else
         result = remove();
      return result;
   }

   public boolean isomorphs(Tree<T> u) {
      return !u.isEmpty() && left.isomorphs(u.left()) && right.isomorphs(u.right());
   }

   public int height() {
      return 1 + Math.max(left.height(), right.height());
   }

   public int unbalance() {
      int maxUnbalance = Math.max(left.unbalance(), right.unbalance());
      int thisUnbalance = Math.abs(left.height() - right.height());
      return Math.max(maxUnbalance, thisUnbalance);
   }
}