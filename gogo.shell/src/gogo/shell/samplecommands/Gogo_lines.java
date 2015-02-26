package gogo.shell.samplecommands;

import gogo.shell.samplecommands.util.Options;
import gogo.shell.samplecommands.util.Strings;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.felix.service.command.CommandProcessor;
import org.apache.felix.service.command.CommandSession;

import aQute.bnd.annotation.component.Component;

@Component(//
properties = {//
/* scope */CommandProcessor.COMMAND_SCOPE + ":String=jwausle",//
		CommandProcessor.COMMAND_FUNCTION + ":String=sort",//
		CommandProcessor.COMMAND_FUNCTION + ":String=sortToString",//
		CommandProcessor.COMMAND_FUNCTION + ":String=head",//
		CommandProcessor.COMMAND_FUNCTION + ":String=join",//
		CommandProcessor.COMMAND_FUNCTION + ":String=unique",//
		CommandProcessor.COMMAND_FUNCTION + ":String=distinct",//
		CommandProcessor.COMMAND_FUNCTION + ":String=duplicates",//
},//
provide = Gogo_lines.class//
)
public class Gogo_lines {

	public Set<String> distinct(CommandSession session, String... args) throws IOException {
		return unique(session, args);
	}

	public Set<String> duplicates(CommandSession session, String... args) throws IOException {
		final Options options = new Options(args);

		if (options.needHelp("-h", "--help", "/?")) {
			String help = //
			/* .. */"difference [options] ([lines] ++ < [session.inputLines])\n" + //
					"\n" + //
					"Description:\n" + //
					"- diff the input lines\n" + //
					"- opposite of 'unique'\n" + //
					"\n" + //
					"Options:\n" + //
					"-h/--help               : show this help\n" + //
					"\n" + //
					"Example: \n" + //
					"'unique line1 'line2 with space' < line3FromSession' \n" + //
					"'echo line3_fromSession | unique line1 'line2 with spaces' 'line1''\n" + //
					"";
			System.out.println(help);
			return Collections.emptySet();
		}

		LinkedList<String> lines = new LinkedList<String>();
		{
			List<String> args2 = options.getArgs();
			Collection<? extends String> list = Gogo_string.toList(args2);
			lines.addAll(list);
		}
		if (Thread.currentThread().getName().contains("pipe")) {
			lines.addAll(Gogo.readLines(session));
		}

		Set<String> lineSet = new LinkedHashSet<String>();
		for (String line : lines) {
			int first = lines.indexOf(line);
			int last = lines.lastIndexOf(line);
			if (first != last) {
				lineSet.add(line);
			}
		}
		return lineSet;
	}

	public List<String> head(CommandSession session, String... args) throws IOException {
		List<String> argList = new LinkedList<>(Arrays.asList(args));

		if (argList.contains("-h") || argList.contains("--help")) {
			System.out.println("head <options>");
			System.out.println();
			System.out.println("Description:");
			System.out.println("- Write head of list/lines/... to stdout");
			System.out.println();
			System.out.println("Options:");
			System.out.println("  -h --help		: show this help.");

			return Collections.emptyList();
		}

		List<String> lines = Gogo.readLines(session);

		if (lines.isEmpty())
			return Collections.emptyList();

		return Collections.singletonList(lines.get(0));
	}

	public String join(CommandSession session, List<String> strings) {
		return "";
	}

	public String join(CommandSession session, String... args) throws IOException {
		List<String> argList = new LinkedList<>(Arrays.asList(args));

		if (argList.contains("-h") || argList.contains("--help")) {
			System.out.println("join <options>");
			System.out.println();
			System.out.println("Description:");
			System.out.println("- Write joined concatenation to stdout");
			System.out.println("- Concat by delimitter ");
			System.out.println();
			System.out.println("Options:");
			System.out.println("  -d --delimitter <char>	: set the delimitter (default='\\\\n')");
			System.out.println("  -h --help					: show this help");

			return "";
		}

		String delimitter = "\n";
		if (argList.contains("-d") || argList.contains("--delimitter")) {
			int indexOf = argList.indexOf("-d");
			if (indexOf != -1) {
				delimitter = argList.get(indexOf + 1);
				argList.remove(indexOf);
				argList.remove(indexOf);
			}
			indexOf = argList.indexOf("--delimitter");
			if (indexOf != -1) {
				delimitter = argList.get(indexOf + 1);
				argList.remove(indexOf);
				argList.remove(indexOf);
			}
		}

		List<String> lines = new LinkedList<>();
		if (!argList.isEmpty()) {
			lines.addAll(Gogo_string.toList(argList));
		}

		if (Thread.currentThread().getName().contains("pipe")) {
			lines.addAll(Gogo.readLines(session));
		}

		if (lines.isEmpty()) {
			return "";
		}
		if (lines.size() == 1) {
			return lines.get(0);
		}

		StringBuffer joinBuffer = new StringBuffer();
		for (Iterator<String> linesIterator = lines.iterator(); linesIterator.hasNext();) {
			String line = (String) linesIterator.next();
			joinBuffer.append(line);

			boolean hasNext = linesIterator.hasNext();
			if (hasNext)
				joinBuffer.append(delimitter);
		}

		String join = joinBuffer.toString();
		return join;
	}

	public List<String> sort(CommandSession session, String... args) throws IOException {
		List<String> argList = new LinkedList<>(Arrays.asList(args));

		if (argList.contains("-h") || argList.contains("--help")) {
			System.out.println("sort <options>");
			System.out.println();
			System.out.println("Description:");
			System.out.println("- Write sorted concatenation to stdout");
			System.out.println("- Sorting depends on process-encoding");
			System.out.println();
			System.out.println("Options:");
			System.out.println("  -r --reverse		: reverse the order");
			System.out.println("  -h --help			: show this help");

			return Collections.emptyList();
		}

		boolean isReverse = false;
		if (argList.contains("-r") || argList.contains("--reverse")) {
			isReverse = true;
			argList.remove("-r");
			argList.remove("--reverse");
		}

		List<String> lines = new LinkedList<>();
		if (!argList.isEmpty()) {
			lines.addAll(Gogo_string.toList(argList));
		}

		if (Thread.currentThread().getName().contains("pipe")) {
			lines.addAll(Gogo.readLines(session));
		}

		Collections.sort(lines);
		if (isReverse) {
			Collections.reverse(lines);
		}

		return lines;
	}

	public String sortToString(CommandSession session, String... args) throws IOException {
		return Strings.join(sort(session, args));
	}

	public Set<String> unique(CommandSession session, String... args) throws IOException {
		final Options options = new Options(args);

		if (options.needHelp("-h", "--help", "/?")) {
			String help = //
			/* .. */"unique/distince [options] ([lines] ++ < [session.inputLines])\n" + //
					"\n" + //
					"Description:\n" + //
					"- distict the input lines\n" + //
					"\n" + //
					"Options:\n" + //
					"-h/--help               : show this help\n" + //
					"\n" + //
					"Example: \n" + //
					"'unique line1 'line2 with space' < line3FromSession' \n" + //
					"'echo line3_fromSession | unique line1 'line2 with spaces' 'line1''\n" + //
					"";
			System.out.println(help);
			return Collections.emptySet();
		}

		LinkedList<String> lines = new LinkedList<String>();
		{
			List<String> args2 = options.getArgs();
			Collection<? extends String> list = Gogo_string.toList(args2);
			lines.addAll(list);
		}
		if (Thread.currentThread().getName().contains("pipe")) {
			lines.addAll(Gogo.readLines(session));
		}

		Set<String> lineSet = new LinkedHashSet<String>();
		for (String line : lines) {
			lineSet.add(line);
		}
		return lineSet;
	}
}
