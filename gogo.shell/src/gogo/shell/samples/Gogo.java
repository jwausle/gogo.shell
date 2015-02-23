package gogo.shell.samples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.felix.service.command.CommandSession;

public class Gogo {

	public static String getProperty(CommandSession session, String key){
		return getProperty(session, key, null);
	}

	public static String getProperty(CommandSession session, String key, String defaultValue) {
		Properties properties = new Properties();
		properties.putAll(System.getenv());
		properties.putAll(System.getProperties());
		
		String toolbasePropertyOrNull = session.get(key) == null ? properties.getProperty(key) == null ? defaultValue : properties.getProperty(key) : session.get(key).toString();
		return toolbasePropertyOrNull;
	}

	public static List<String> readLines() throws IOException {
		BufferedReader rdr = new BufferedReader(new InputStreamReader(System.in));
	
		List<String> lines = new LinkedList<String>();
		
		String lastLine = null;
		while (true) {
			String line = rdr.readLine();
			if (line == null) {
				if(lastLine != null && lastLine.isEmpty()){
					lines.remove(lines.size()-1);
				}
				break;
			}
			lines.add(line);
			lastLine = line;
		}
		return lines;
	}
	
	public static List<String> readLines(CommandSession in) throws IOException {
		if (in == null) {
			return Collections.emptyList();
		}
		
		return readLines();
	}

	public static List<String> readLines_onlyFromPipe() throws IOException {
		if(Thread.currentThread().getName().contains("pipe")){
			return readLines();
		}
		return Collections.emptyList();
	}

}
