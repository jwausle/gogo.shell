package gogo.shell;

import gogo.shell.fistcommand.FirstCommand;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.service.command.CommandProcessor;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator, BundleListener {

	@Override
	public void start(BundleContext context) throws Exception {		
		Dictionary<String, Object> dict = new Hashtable<>();
		dict.put("osgi.command.scope", "itemisle");
		dict.put("osgi.command.function", new String[] {"closure"});
		context.registerService(FirstCommand.class, new FirstCommand(), dict);
	}

	private BundleContext context;

	@Override
	public void stop(BundleContext context) throws Exception {
		context.addBundleListener(this);
		this.context = context;
		// do nothing
	}

	@Override
	public void bundleChanged(BundleEvent event) {
		Bundle bundle = event.getBundle();
		boolean isThis = bundle.getBundleContext() == context;
		if (!isThis)
			return;

		int state = bundle.getState();
		if (state < Bundle.ACTIVE)
			return;

		// to trigger PostRepoAdminActivationHook
		ServiceReference<?> serviceReference = context.getServiceReference(PostRepoAdminActivationHook.class.getName());
		context.getService(serviceReference);

	}

}
