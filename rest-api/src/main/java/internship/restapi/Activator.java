package internship.restapi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.spring.ActiveMQConnectionFactory;

public class Activator implements BundleActivator {
    public void start(BundleContext bundleContext) throws Exception {

    }

    public void stop(BundleContext bundleContext) throws Exception {

    }

}
