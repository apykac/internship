package internship.validators.userValidator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

    private ServiceRegistration serviceRegistration;

    public void start(BundleContext bundleContext) {
        serviceRegistration = bundleContext.registerService(IUserValidator.class.getName(), new UserValidator(), null);
    }

    public void stop(BundleContext bundleContext) {
        serviceRegistration.unregister();
    }

}
