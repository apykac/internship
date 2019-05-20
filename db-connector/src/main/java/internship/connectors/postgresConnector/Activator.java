package internship.connectors.postgresConnector;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

    private ServiceRegistration serviceRegistration;

    public void start(BundleContext bundleContext) throws Exception {
        serviceRegistration = bundleContext.registerService(IConnector.class.getName(), new Connector(), null);
    }

    public void stop(BundleContext bundleContext) throws Exception {
        serviceRegistration.unregister();
    }

}
