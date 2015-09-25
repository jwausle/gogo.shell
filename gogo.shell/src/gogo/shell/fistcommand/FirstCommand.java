package gogo.shell.fistcommand;

import java.util.Arrays;

import org.apache.felix.service.command.CommandProcessor;
import org.apache.felix.service.command.CommandSession;

import aQute.bnd.annotation.component.Component;

@Component(//
properties = {//
CommandProcessor.COMMAND_SCOPE + ":String=itemis",//
		CommandProcessor.COMMAND_FUNCTION + ":String=macro",//
},//
provide = FirstCommand.class//
)
public class FirstCommand {

	// public void macro(Long arg, Long... args) {
	// System.out.println("macro#long: Bla " + arg + ", "
	// + Arrays.toString(args) + " blub");
	// }
	// public void macro(Long... arg) {
	// System.out.println("macro#long: Bla " + Arrays.toString(arg) + " blub");
	// }

	public void macro(String... arg) {
		System.out.println("macro#string: Bla " + Arrays.toString(arg)
				+ " blub");
	}

	public void macro(CommandSession session, Object... arg) {
		System.out.println("macro#object Bla " + Arrays.toString(arg) + " blub");
	}

	public void closure(CommandSession session, String arg) {
		System.out.println("closure# Blub " + arg + " bla");
	}

	public void nocmd() {
		System.out.println("nocmd reflected.");
	}

}
