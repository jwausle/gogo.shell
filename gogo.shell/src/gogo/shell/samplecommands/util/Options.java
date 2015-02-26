package gogo.shell.samplecommands.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Options {

	private final List<String> argList = new LinkedList<String>();
	
	private final List<String> inputLines = new LinkedList<String>();
	public Options(String[] args) {
		this(args,Collections.<String>emptyList());
	}
	public Options(String[] args, List<String> readLines) {
		argList.addAll(Arrays.asList(args));
		inputLines.addAll(readLines);
	}

	public Option exist(String... options) {
		int index = getIndexOrMinus1(options);
		if (index == -1)
			return null;

		String arg = argList.get(index);
		if (arg.contains("=")) {
			return new Option.SingleOption(this, arg);
		}

		return new Option.TwoArgOption(this, argList, index);
	}

	public Option existFlag(String...options) {
		int index = getIndexOrMinus1(options);
		if (index == -1)
			return null;
		String arg = argList.get(index);
		if (arg.contains("=")) {
			return new Option.SingleOption(this, arg);
		}

		return new Option.SingleOption(this, arg);
	}

	public List<String> getArgs() {
		return Collections.unmodifiableList(argList);
	}

	public List<String> getInputLines() {
		return new LinkedList<>(this.inputLines);
	}

	public boolean isEmpty() {
		return argList.isEmpty();
	}

	public boolean needHelp(String...helpOptions) {
		if (exist(helpOptions) != null) {
			return true;
		}
		return false;
	}
	public boolean needHelp_emptyArgs(String...helpOptions) {
		if (exist(helpOptions) != null) {
			return true;
		} else if (inputLines.isEmpty() && this.argList.size()== 0) {
			return true;
		}
		return false;
	}
	public Options remove(Option option) {
		List<String> indexToRemoves = option.getOriginalArgs();
		for (String i : indexToRemoves) {
			if (!argList.contains(i)) {
				continue;
			}
			argList.remove(i);
		}
		return this;
	}
	public Options remove(String prefix) {
		if(argList.contains(prefix)){
			argList.remove(prefix);
		}
		return this;
	}
	@Override
	public String toString() {
		return argList.toString();
	}
	private int getIndexOrMinus1(String... options) {
		int index = -1;
		for (String arg : argList) {
			if (arg == null)
				continue;

			for (String opt : options) {
				if (opt == null)
					continue;
				if (arg.startsWith("--") && arg.startsWith(opt)) {
					index = argList.indexOf(arg);
					continue;
				}
				if (arg.equals(opt)) {
					index = argList.indexOf(arg);
					continue;
				}
			}
		}
		return index;
	}


}