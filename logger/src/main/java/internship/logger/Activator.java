package internship.logger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Dictionary;

public class Activator implements BundleActivator {
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Hello!");
		updateConfiguration(bundleContext);
		Logger log = LoggerFactory.getLogger(this.getClass());
		for (int i = 0; i < 10; i++) {
			log.error("numer " + i);
			log.info("info");
		}
	}

	@Override
	public void stop(BundleContext bundleContext) {
		Logger log = LoggerFactory.getLogger(this.getClass());
		log.error(this.getClass().getName());
		log.info("stopping");
		log.debug("stop debug");
	}

	/**
	 * Updates Pax Logging configuration to a specifid conversion pattern.
	 *
	 * @param bundleContext bundle context
	 * @throws IOException - Re-thrown
	 */
	private void updateConfiguration(BundleContext bundleContext)
			throws IOException {
		final ConfigurationAdmin configAdmin = getConfigurationAdmin(bundleContext);
		final Configuration configuration = configAdmin.getConfiguration("org.ops4j.pax.logging", null);

		final Dictionary<String, Object> log4jProps = configuration.getProperties();

		//log4j.logger.internship=DEBUG, sift
		log4jProps.put("log4j.logger.internship", "DEBUG, sift");

		configuration.update(log4jProps);
	}

	/**
	 * Gets Configuration Admin service from service registry.
	 *
	 * @param bundleContext bundle context
	 * @return configuration admin service
	 * @throws IllegalStateException - If no Configuration Admin service is available
	 */
	private ConfigurationAdmin getConfigurationAdmin(final BundleContext bundleContext) {
		final ServiceReference ref = bundleContext.getServiceReference(ConfigurationAdmin.class.getName());
		if (ref == null) {
			throw new IllegalStateException("Cannot find a configuration admin service");
		}
		return (ConfigurationAdmin) bundleContext.getService(ref);
	}
}
