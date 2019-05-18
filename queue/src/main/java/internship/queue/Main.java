package internship.queue;

import org.apache.activemq.broker.BrokerService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Main implements BundleActivator {
    public static void main(String... args) throws Exception {
        // start embedded broker
        BrokerService broker = new BrokerService();
        broker.addConnector("tcp://localhost:61617");
        broker.start();

        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        int x = 0;
        while(true) {
            Thread.sleep(2000);
            producer.produceMessage(x);
            x++;
        }
    }

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        main(null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}