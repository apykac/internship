package internship.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Main implements BundleActivator {
    public static void main(String... args) throws Exception {

        Consumer consumer = new Consumer();
    }

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        main(null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}