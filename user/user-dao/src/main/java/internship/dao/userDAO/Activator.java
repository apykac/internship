package internship.dao.userDAO;

import internship.connectors.postgresConnector.IConnector;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class Activator implements BundleActivator, ServiceTrackerCustomizer {

    private ServiceRegistration serviceRegistration;
    private ServiceTracker userDBConnectorTracker;
    private BundleContext context;
    private final UserDatabaseDAO databaseDAO = new UserDatabaseDAO();

    public void start(BundleContext bundleContext) throws Exception {
        this.context = bundleContext;
        serviceRegistration = context.registerService(UserDAO.class.getName(), databaseDAO, null);
        userDBConnectorTracker = new ServiceTracker(context, IConnector.class.getName(), this);
        userDBConnectorTracker.open();
    }

    public void stop(BundleContext bundleContext) throws Exception {
        serviceRegistration.unregister();
        userDBConnectorTracker.close();
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
    public void modifiedService(ServiceReference serviceReference, Object service) {
    }

    @Override
    public void removedService(ServiceReference serviceReference, Object service) {
    }
}
