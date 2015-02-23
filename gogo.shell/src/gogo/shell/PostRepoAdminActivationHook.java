package gogo.shell;

import java.text.MessageFormat;

import org.apache.felix.bundlerepository.RepositoryAdmin;
import org.apache.felix.service.command.CommandProcessor;
import org.apache.felix.service.command.CommandSession;
import org.osgi.service.component.ComponentContext;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

@Component(//
name = "PostRepoAdminActivationHook",//
provide = { PostRepoAdminActivationHook.class }//
)
public class PostRepoAdminActivationHook {
	private CommandProcessor commandsOrNull = null;

	@Reference(service = org.apache.felix.bundlerepository.RepositoryAdmin.class)
	public void setRepositoryAdmin(RepositoryAdmin repoAdmin) {
	}

	@Reference(service = org.apache.felix.service.command.CommandProcessor.class)
	public void setCommandProcessor(CommandProcessor processor) {
		this.commandsOrNull = processor;
	}

	@Activate
	public void activate(ComponentContext context) {
		String userDir = System.getProperty("user.dir");
		String repoAddCommand = MessageFormat.format("repos add file:{0}/obr/index.xml", userDir);

		CommandSession session = commandsOrNull.createSession(System.in, System.out, System.err);
		try {
			session.execute(repoAddCommand);
			
			String repo = repoAddCommand.substring(repoAddCommand.indexOf('/'));
			System.out.println("Registered OBR: " + repo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
