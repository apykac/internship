package internship.services.userService;

import internship.dao.userDAO.UserDAO;
import internship.validators.userValidator.IUserValidator;
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
	private Server server = null;
	private final UserServiceImpl userService = new UserServiceImpl();

	@Override
	public void start(BundleContext bundleContext) {
		this.context = bundleContext;

		validationTracker = new ServiceTracker(context, IUserValidator.class.getName(), this);
		daoTracker = new ServiceTracker(context, UserDAO.class.getName(), this);
		validationTracker.open();
		daoTracker.open();

		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setResourceClasses(userService.getClass());
		sf.setResourceProvider(userService.getClass(), new SingletonResourceProvider(userService));
		sf.setAddress("/userservice");
		server = sf.create();
	}

	@Override
	public void stop(BundleContext bundleContext) {
		if (server != null) {
			server.stop();
			server.destroy();
			server = null;
		}
		validationTracker.close();
		daoTracker.close();
		this.context = null;
	}

	@Override
	public Object addingService(ServiceReference serviceReference) {
		final Object trackedService = context.getService(serviceReference);

		if (trackedService instanceof IUserValidator) {
			userService.setUserValidator((IUserValidator) trackedService);
		}
		if (trackedService instanceof UserDAO) {
			userService.setUserDAO((UserDAO) trackedService);
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
