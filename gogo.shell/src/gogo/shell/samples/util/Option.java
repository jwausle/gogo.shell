package gogo.shell.samples.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Option {
	public static class SingleOption extends Option {

		public SingleOption(Options options, String string) {
			super.addOriginals(string);

			int lastIndexOf = string.lastIndexOf("=");
			if (lastIndexOf != -1) {
				super.key = string.substring(0, lastIndexOf);
				super.value = string.substring(lastIndexOf + 1);
			} else {
				super.key = string;
				super.value = "";
			}

			super.optionsRef = options;

		}

	}
	public static class TwoArgOption extends Option {

		public TwoArgOption(Options options, List<String> argList, int index) {
			super.optionsRef = options;

			String key = argList.get(index);
			super.key = key;
			super.addOriginals(key);

			try {
				String firstValue = argList.get(index + 1);
				if (firstValue.startsWith("-")) {
					return;
				}
				super.addOriginals(firstValue);
				super.value = firstValue;
			} catch (Exception e) {
				super.value = Boolean.TRUE.toString();
			}
		}

	}

	private String key;

	private String value;

	private List<String> originals = new LinkedList<String>();

	private Options optionsRef;

	public List<String> getOriginalArgs() {
		return this.originals;
	}

	public String getValue() {
		this.optionsRef.remove(this);
		return value;
	}

	public Option putValueIfNotNull(Map<String, String> config, String key) {
		if (getValue() == null)
			return this;

		config.put(key, getValue());
		return this;
	}

	@Override
	public String toString() {
		return key + "=" + value;
	}

	protected Option addOriginals(String... originals) {
		this.originals.addAll(Arrays.asList(originals));
		return this;
	}

}