import java.text.Collator;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Class Concordance represents objects that implement concordances. A
 * concordance is a table that associates a word to all small pieces of text
 * where that word has been identified.
 */
public class Concordance implements Iterable<String> {

	public final static Locale myLocale = new Locale("pt", "PT");
	public final static Collator ptCollator = Collator.getInstance(myLocale);

	private HashTable<String, List<String>> ht;

	/**
	 * Initializes a concordance object.
	 */
	public Concordance() {
		ht = new HashTable<>();
	}

	public Iterator<String> iterator() {
		return ht.iterator();
	}

	public boolean has(String key) {
		return ht.has(key);
	}

	public boolean isEmpty() {
		return ht.isEmpty();
	}

	public int size() {
		return ht.size();
	}

	/**
	 * Adds a word and the corresponding text where the word has been
	 * identified.
	 * 
	 * @param key
	 *            the word.
	 * @param line
	 *            the text where the word has been identified.
	 */
	public void put(String key, String line) {
		key = key.toLowerCase();

		if (!ht.has(key))
			ht.put(key, new Empty<String>().cons(line));
		else if (!ht.get(key).has(line))
			ht.put(key, ht.get(key).cons(line));
	}

	/**
	 * Adds each word in the given string together with the given string. Words
	 * are substrings of the given string delimited by spaces.
	 * 
	 * @param line
	 *            the given string, which contains several words.
	 */
	public void putLine(String line) {
		String[] ss = line.split(" ");

		for (String word : ss)
			put(word, line);
	}

	public void putLineB(String line) {
		String[] ss = line.split("[^\\p{L}\\-]+");

		for (String word : ss)
			put(word, line);
	}

	/**
	 * Returns the list of strings associated with the given string,
	 * representing all texts where the given string has been identified. Words
	 * are delimited in each line be spaces.
	 * 
	 * @param key
	 *            the given string.
	 * @returns the list of strings associated with the given string, as
	 *          explained.
	 */
	public List<String> get(String key) {
		key = key.toLowerCase();
		return ht.get(key);
	}

	/**
	 * Display the table on the console, using a simple format
	 */
	public void show() {
		for (String s : ht) {
			StdOut.println(s);
			for (String t : get(s))
				StdOut.println("...." + t);
		}
	}

	/**
	 * Display the table in the same format as function show but with the keys
	 * sorted and the lines for each key also sorted. Note: use Arrays.sort().
	 */
	public void showSorted() {
		String[] keys = sort(ht.iterator(), ht.size());

		for (String s : keys) {
			StdOut.println(s);

			String[] values = sort(get(s).iterator(), get(s).size());
			for (String t : values)
				StdOut.println("...." + t);
		}
	}

	public void showSortedB(String key) {
		if (!ht.has(key))
			return;

		String[] values = sortB(get(key).iterator(), get(key).size());
		for (String t : values)
			StdOut.println("...." + t);
	}

	public String[] sort(Iterator<String> it, int size) {
		String[] array = new String[size];
		for (int i = 0; i < array.length; i++)
			array[i] = it.next();
		Arrays.sort(array);
		return array;
	}

	public String[] sortB(Iterator<String> it, int size) {
		String[] array = new String[size];
		for (int i = 0; i < array.length; i++)
			array[i] = it.next();
		Arrays.sort(array, ptCollator);
		return array;
	}

	public static void taskA() {
		Concordance c = new Concordance();
		while (StdIn.hasNextLine()) {
			String line = StdIn.readLine();
			c.putLine(line);
		}
		c.showSorted();
	}

	public static void taskB(String filename) {
		Concordance c = new Concordance();

		In f = new In(filename);
		String[] lines = f.readAllLines();

		for (String line : lines)
			c.putLineB(line);

		while (StdIn.hasNextLine()) {
			String key = StdIn.readLine();
			c.showSortedB(key);
		}
	}

	// First impressions...
	public static void testA() {
		Concordance c = new Concordance();
		while (StdIn.hasNextLine()) {
			String line = StdIn.readLine();
			c.putLine(line);
		}
		c.show();
	}

	public static void main(String[] args) {
		char choice = 'A';
		if (args.length > 0)
			choice = args[0].charAt(0);
		if (choice == 'A')
			// Run from console, at directory work, with:
			// java -ea -cp ../../*:../bin:. Concordance A
			taskA();
		else if (choice == 'B') {
			// Run from console, at directory work, with:
			// java -ea -cp ../../*:../bin:. Concordance B estrofe_1_1.txt
			// java -ea -cp ../../*:../bin:. Concordance B lusiadas_canto_1.txt
			// java -ea -cp ../../*:../bin:. Concordance B lusiadas_tudo.txt
			assert args.length > 1;
			taskB(args[1]);
		} else if (choice == 'C')
			// Run from console, at directory work, with:
			// java -ea -cp ../../*:../bin:. Concordance C
			testA();
		else
			StdOut.println("Illegal option: " + choice);
	}

}