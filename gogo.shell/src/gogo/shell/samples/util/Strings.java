package gogo.shell.samples.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Strings {

	public static String join(Collection<String> list) {
		return Strings.join(list.toArray(new String[list.size()]));
	}

	public static String join(List<String> list, String delimitter) {
		return Strings.join(list.toArray(new String[list.size()]), delimitter);
	}

	public static CharSequence join(Map<String, ? extends Object> map) {
		Set<String> keySet = map.keySet();
		StringBuffer buffer = new StringBuffer();

		for (String entry : keySet) {
			buffer.append(Strings.paddingRight("", 5) + String.format("%s -> %s\n", entry, map.get(entry)));
		}
		return buffer.toString();
	}

	public static String join(String[] help) {
		String delimitter = "\n";
		return join(help, delimitter);
	}

	public static String join(String[] help, String delimitter) {
		if (help.length == 0)
			return "";
		StringBuffer buffer = new StringBuffer();
		for (String string : help) {
			if (string == null) {
				buffer.append("'null'").append(delimitter);
			} else if (string.endsWith(delimitter)) {
				buffer.append(string);
			} else {
				buffer.append(string).append(delimitter);
			}
		}
		return buffer.substring(0, buffer.length() - delimitter.length());
	}

	public static String joinToString(List<? extends Object> objects, String delimitter) {
		List<String> list = new LinkedList<>();
		for (Object object : objects) {
			if (object == null) {
				list.add(null);
				continue;
			}
			list.add(object.toString());
		}
		return join(list, delimitter);
	}

	public static String paddingLeft(String level, int paddingSize) {
		if (paddingSize <= 0)
			return "";
		return String.format("%1$" + paddingSize + "s", level);
	}

	public static String paddingRight(String level, int paddingSize) {
		if (paddingSize <= 0)
			return "";
		return String.format("%1$-" + paddingSize + "s", level);
	}

	public static List<String> paddingRight(String string, int i, Collection<String> original) {
		LinkedList<String> strings = new LinkedList<>();
		for (String string2 : original) {
			String line = Strings.paddingRight(string, i) + string2;
			strings.add(line);
		}
		return strings;
	}

	public static String repeat(String string, int level) {
		String returnString = "";
		for (int i = 0; i < level; i++) {
			returnString = returnString + string;
		}
		return returnString;
	}

	/**
	 * Convert '[a 2 ... any-string]' to 'collectionOf(a,2,...,any-string)'
	 * 
	 * @param value
	 * @param value2
	 * @return collection parsed from space seperated ([] if null).
	 */
	public static Collection<String> toCollection(String seperator, String value) {
		if (value == null)
			return Collections.emptyList();

		String listPattern = "\\[(.*)\\]";
		String source = value.matches(listPattern) ? value.replaceAll(listPattern, "$1") : value;

		String[] splittedSource = source.split(seperator);
		return Arrays.asList(splittedSource);
	}

	public static List<Integer> toIntegers(List<String> repo) {
		LinkedList<Integer> list = new LinkedList<>();
		for (String string : repo) {
			try {
				Integer valueOf = Integer.valueOf(string);
				list.add(valueOf);
			} catch (Exception e) {
				// ignore any exception.
				continue;
			}
		}
		return list;
	}

	public static <T> List<String> toString(Iterable<T> collection) {
		List<String> innerList = new LinkedList<>();
		for (T object : collection) {
			if (object == null) {
				innerList.add(null);
				continue;
			}
			innerList.add(object.toString());
		}
		return innerList;
	}

	public static <T> Iterable<Iterable<String>> toStrings(Iterable<? extends Iterable<T>> from) {
		List<Iterable<String>> list = new LinkedList<>();
		for (Iterable<? extends T> collection : from) {
			List<String> innerList = toString(collection);

			list.add(innerList);
		}
		return list;
	}

	public static boolean matches(String actionRegex, List<String> actions) {
		for (String string : actions) {
			if (string.matches(actionRegex))
				return true;
		}
		return false;
	}

	public static String paddingRightAndJoin(String padding, int paddingCount, Collection<String> strings) {
		if (strings.isEmpty())
			return "";
		
		String head = "";//paddingRight(padding, paddingCount);
		List<String> tail = paddingRight(" ", 5, strings);
		String string = head + join(tail);
		return string;
	}

}
