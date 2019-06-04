package internship.services.addressSort;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    ServiceRegistration sr;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        sr = bundleContext.registerService(IAddressSort.class.getName(), new AddressSort(), null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        sr.unregister();
    }
}
