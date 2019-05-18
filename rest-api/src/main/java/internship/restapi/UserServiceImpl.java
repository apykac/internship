package internship.restapi;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class UserServiceImpl {

    private Connection connection;
    private Logger log = LoggerFactory.getLogger(this.getClass());
    int num = 1;

    public UserServiceImpl() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61617");

        connection = connectionFactory.createConnection();
        connection.start();
    }


    @GET
    @Path("/user")
    public String getUser() {
        System.out.println("get user");
        log.info("Get user invoked.");
        produceMessage(num);
        num++;
        return "Hello!";
    }

    public void produceMessage(int x) {
        try {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination
//            Destination destination = session.createQueue("Testqueue");
            Destination destination = session.createTopic("Testtopic");

            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a messages
            String text = "Hello world " + x + "! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
            TextMessage message = session.createTextMessage(text);

            // Tell the producer to send the message
            System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());

            producer.send(message);
            session.close();
        }
        catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

}
