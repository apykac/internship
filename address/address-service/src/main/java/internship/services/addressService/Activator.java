package internship.services.addressService;

import internship.dao.addressDAO.AddressDAO;
import internship.services.addressSort.IAddressSort;
import internship.validators.addressValidator.IAddressValidator;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class Activator implements BundleActivator, ServiceTrackerCustomizer {

    private BundleContext context;
    private ServiceTracker validationTracker;
    private ServiceTracker daoTracker;
    private ServiceTracker sortTracker;
    private Server server = null;
    private final AddressServiceImpl addressService = new AddressServiceImpl();

    @Override
    public void start(BundleContext bundleContext) {
        this.context = bundleContext;
        validationTracker = new ServiceTracker(context, IAddressValidator.class.getName(), this);
        daoTracker = new ServiceTracker(context, AddressDAO.class.getName(), this);
        sortTracker = new ServiceTracker(context, IAddressSort.class.getName(), this);
        validationTracker.open();
        daoTracker.open();
        sortTracker.open();

        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
        sf.setResourceClasses(addressService.getClass());
        sf.setResourceProvider(addressService.getClass(), new SingletonResourceProvider(addressService));
        sf.setAddress("/addressservice");
        server = sf.create();
    }

    @Override
    public void stop(BundleContext bundleContext) {
        if (server != null) {
            server.stop();
            server.destroy();
            server = null;
        }
        sortTracker.close();
        validationTracker.close();
        daoTracker.close();
        this.context = null;
    }

    @Override
    public Object addingService(ServiceReference serviceReference) {
        final Object trackedService = context.getService(serviceReference);

        if (trackedService instanceof IAddressValidator) {
            addressService.setUserValidator((IAddressValidator) trackedService);
        }
        if (trackedService instanceof AddressDAO) {
            addressService.setAddressDAO((AddressDAO) trackedService);
        }

        if(trackedService instanceof IAddressSort){
            addressService.setAddressSort((IAddressSort) trackedService);
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
