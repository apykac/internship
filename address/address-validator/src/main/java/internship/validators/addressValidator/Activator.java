package internship.validators.addressValidator;

import internship.dao.addressDAO.AddressDAO;
import internship.dao.userDAO.UserDAO;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class Activator implements BundleActivator, ServiceTrackerCustomizer {

    private ServiceRegistration serviceRegistration;
    private ServiceTracker userDaoTracker;
    private ServiceTracker addressDaoTracker;
    private BundleContext context;
    private final AddressValidator addressValidator = new AddressValidator();

    @Override
    public void start(BundleContext bundleContext) {
        this.context = bundleContext;
        serviceRegistration = context.registerService(IAddressValidator.class.getName(), addressValidator, null);
        userDaoTracker = new ServiceTracker(context, UserDAO.class.getName(), this);
        addressDaoTracker = new ServiceTracker(context, AddressDAO.class.getName(), this);
        userDaoTracker.open();
        addressDaoTracker.open();
    }

    @Override
    public void stop(BundleContext bundleContext) {
        serviceRegistration.unregister();
        userDaoTracker.close();
        addressDaoTracker.close();
    }

    @Override
    public Object addingService(ServiceReference serviceReference) {
        final Object trackedService = context.getService(serviceReference);
        if (trackedService instanceof UserDAO) {
            addressValidator.setUserDao((UserDAO) trackedService);
        }
        if (trackedService instanceof AddressDAO) {
            addressValidator.setAddressDao((AddressDAO) trackedService);
        }

        return trackedService;
    }

    @Override
    public void modifiedService(ServiceReference serviceReference, Object o) {

    }

    @Override
    public void removedService(ServiceReference serviceReference, Object o) {

    }
}
