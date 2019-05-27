package internship.dao.addressDAO;

import internship.connectors.postgresConnector.IConnector;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class Activator implements BundleActivator, ServiceTrackerCustomizer {

    private ServiceRegistration serviceRegistration;
    private ServiceTracker addressDBConnectorTracker;
    private BundleContext context;
    private final AddressDatabaseDAO databaseDAO = new AddressDatabaseDAO();

    @Override
    public void start(BundleContext bundleContext) {
        this.context = bundleContext;
        serviceRegistration = context.registerService(AddressDAO.class.getName(), databaseDAO, null);
        addressDBConnectorTracker = new ServiceTracker(context, IConnector.class.getName(), this);
        addressDBConnectorTracker.open();
    }

    @Override
    public void stop(BundleContext bundleContext) {
        serviceRegistration.unregister();
        addressDBConnectorTracker.close();
    }

    @Override
    public Object addingService(ServiceReference serviceReference) {
        final Object trackedService = context.getService(serviceReference);
        if (trackedService instanceof IConnector) {
            databaseDAO.setConnector((IConnector) trackedService);
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
