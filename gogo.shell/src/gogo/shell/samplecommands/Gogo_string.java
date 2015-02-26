package gogo.shell.samplecommands;

import gogo.shell.samplecommands.util.Option;
import gogo.shell.samplecommands.util.Options;
import gogo.shell.samplecommands.util.Strings;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.felix.service.command.CommandProcessor;
import org.apache.felix.service.command.CommandSession;

import aQute.bnd.annotation.component.Component;

@Component(//
properties = {//
/* scope */CommandProcessor.COMMAND_SCOPE + ":String=jwausle",//
		CommandProcessor.COMMAND_FUNCTION + ":String=sed",//
		CommandProcessor.COMMAND_FUNCTION + ":String=tr",//
		CommandProcessor.COMMAND_FUNCTION + ":String=concat",//
},//
provide = Gogo_string.class//
)
public class Gogo_string {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Collection<? extends String> toList(List<String> args) {
		LinkedList linkedList = new LinkedList();
		for (String string : args) {
			linkedList.addAll(toList(string));
		}
		return linkedList;
	}

	public static List<String> toList(String string) {
		if (!string.matches("\\[.*\\]")) {
			return Collections.singletonList(string);
		}
		char[] charArray = string.toCharArray();
		int lastIndex = charArray.length - 1;

		LinkedList<String> linkedList = new LinkedList<>();
		StringBuffer buffer = new StringBuffer();
		char cBefore = 0;
		for (int i = 0; i <= lastIndex; i++) {
			char c = charArray[i];

			if (i == 0 && c == '[') {
				continue;
			}
			if (i == lastIndex && c == ']') {
				linkedList.add(buffer.toString());
				continue;
			}

			if (cBefore == ',' && c == ' ') {
				linkedList.add(buffer.substring(0, buffer.length() - 1));
				buffer = new StringBuffer();
				cBefore = 0;
				continue;
			}

			buffer.append(c);
			cBefore = c;
		}

		return linkedList;
	}

	public List<String> concat(CommandSession session, String... args) throws IOException {
		final Options options = new Options(args, Gogo.readLines(session));

		if (options.needHelp_emptyArgs("-h", "--help")) {
			String[] help = {//
			"concat [options] head-string[tail-strings]]",//
					"",//
					"Description:",//
					"- concat each tail-string with head-string",//
					"  - head as prefix (-p/--prefix)",//
					"  - head as suffix (-s/--suffix)",//
					"- default is suffix and prefix together.",//
					"",//
					"Options:",//
					"-h/--help          : show this help",//
					"-p/--prefix str    : to prefix each string",//
					"-s/--suffix str    : to suffix each string",//
					"",//
					"Example: ",//
					"> concat * string",//
					"*string*",//
					"> concat --suffix * string1 string2",//
					"string1*",//
					"string2*",//
					"",//
			};
			session.getConsole().println(Strings.join(help));
			return Collections.emptyList();
		}

		Option opt = null;

		String prefix;
		if ((opt = options.exist("-p", "--prefix")) != null) {
			prefix = opt.getValue();
			opt = null;
		} else {
			prefix = "";
		}

		String suffix;
		if ((opt = options.exist("-s", "--suffix")) != null) {
			suffix = opt.getValue();
			opt = null;
		} else {
			suffix = "";
		}

		if (prefix.isEmpty() && suffix.isEmpty()) {
			prefix = options.getArgs().get(0);
			suffix = options.getArgs().get(0);
			options.remove(prefix);
		}

		LinkedList<String> outputs = new LinkedList<>();
		
		List<String> inputs = new LinkedList<>();
		if (Thread.currentThread().getName().contains("pipe")) {
			inputs.addAll(Gogo.readLines(session));
		}
		if (inputs.isEmpty()) {
			inputs.addAll(options.getArgs());
		}

		for (String input : inputs) {
			String concat = prefix + input + suffix;
			outputs.add(concat);
		}

		return outputs;
	}

	public List<String> sed(CommandSession session, String... args) throws IOException {
		// session.getConsole().println("sed ... " + Arrays.toString(args));
		List<String> argList = new LinkedList<>(Arrays.asList(args));

		if (argList.contains("-h") || argList.contains("--help") || argList.isEmpty()) {
			System.out.println("sed 's;regex;replacment;g' or 's/regex/replacment/g' ");
			System.out.println();
			System.out.println("Description:");
			System.out.println("- Translate each str1 to str2 to stdout");
			System.out.println("- Another syntax for 'tr str1 str2'");
			System.out.println();
			System.out.println("Options:");
			System.out.println("  -h --help		: show this help.");
			System.out.println();
			System.out.println("Samples:");
			System.out.println("g! bundles | sed 's/.*\\|(.*) .*/$1/g'");
			System.out.println("g! each ( bundles | sed 's/.*\\|(.*) .*/$1/g' ) { ( tr 'org.apache.' '' $it ) }");

			return Collections.emptyList();
		}

		String pattern = argList.get(0);
		List<String> lines = argList.size() == 1 ? Gogo.readLines(session) : Collections.singletonList(argList.get(1));

		List<String> split = pattern.matches("'?s/.*/.*/g'?") ?
		/**/Arrays.asList(pattern.split("/")) :
		/**/Arrays.asList(pattern.split(";"));

		if (split.size() != 4) {
			return Collections.singletonList("Wrong sed pattern in arg-list + " + pattern
					+ ". 's;str1;str2;g' XOR 's/str1/str2/g' expected.");
		}

		String regex = split.get(1);

		String replacement = split.get(2);

		List<String> sedLines = new LinkedList<String>();
		for (String line : lines) {
			String sedLine = line.replaceAll(regex, replacement);
			sedLines.add(sedLine);
		}
		return sedLines;
	}

	public List<String> tr(CommandSession session, String... args) throws IOException {
		// session.getConsole().println("tr ... " + Arrays.toString(args));

		List<String> argList = new LinkedList<>(Arrays.asList(args));

		if (argList.contains("-h") || argList.contains("--help") || argList.isEmpty()) {
			System.out.println("tr str1 [str2]");
			System.out.println();
			System.out.println("Description:");
			System.out.println("- Translate each str1 to str2 to stdout");
			System.out.println("- If str2='' than delete str1.");
			System.out.println();
			System.out.println("Options:");
			System.out.println("  -h --help		: show this help.");
			System.out.println();
			System.out.println("Samples:");
			System.out.println("g! echo ..foo.. | tr 'foo' 'bar' ");

			return Collections.emptyList();
		}

		String str1 = argList.get(0);

		String str2 = "";
		if (argList.size() > 1) {
			str2 = argList.get(1);
		}

		List<String> rest = new LinkedList<>();
		if (argList.size() > 2) {
			Object[] copyOfRange = Arrays.copyOfRange(argList.toArray(), 2, argList.size());

			List<Object> asList = Arrays.asList(copyOfRange);
			for (Object object : asList) {
				if (object == null)
					continue;
				rest.add(object.toString());
			}
		}

		List<String> lines = !rest.isEmpty() ? rest : Gogo.readLines(session);
		List<String> trLines = new LinkedList<String>();
		for (String line : lines) {
			String trLine = line.replace(str1, str2);
			trLines.add(trLine);
		}
		return trLines;
	}
}
