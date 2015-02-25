package gogo.shell.fistcommand;

import org.apache.felix.service.command.CommandSession;

import aQute.bnd.annotation.component.Component;

@Component(//
properties = {//
"osgi.command.scope:String=itemis",//
		"osgi.command.function:String=macro",//
},//
provide = FirstCommand.class//
)
public class FirstCommand {
	// public void macro(Object arg) {
	// System.out.println("macro#obj: Bla " + arg + " blub");
	// }
	// public void macro(Long arg){
	// System.out.println("macro#long: Bla " + arg + " blub");
	// }
	//
	// public void macro(String arg){
	// System.out.println("macro#string: Bla " + arg + " blub");
	// }
	//
	//
	// public void macro(CommandSession session, String arg){
	// System.out.println("macro#session Bla " + arg + " blub");
	// }
	//
	public void closure(CommandSession session, String arg) {
		System.out.println("closure# Blub " + arg + " bla");
	}

}
