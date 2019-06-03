package internship.services.userFilter;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    private ServiceRegistration sr;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("bundle UserFilter started");
        sr = bundleContext.registerService(IUserFilter.class.getName(), new UserFilter(), null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        sr.unregister();
    }
}
