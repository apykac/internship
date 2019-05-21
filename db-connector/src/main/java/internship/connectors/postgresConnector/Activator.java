package internship.connectors.postgresConnector;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private ServiceRegistration serviceRegistration;

	public void start(BundleContext bundleContext) {
		log.info("Starting...");
		serviceRegistration = bundleContext.registerService(IConnector.class.getName(), new Connector(), null);
		log.info("Started.");
	}

	public void stop(BundleContext bundleContext) {
		log.info("Stopping...");
		serviceRegistration.unregister();
		log.info("Stopped.");
	}

}
