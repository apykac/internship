package internship.queue;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    public Consumer() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        connectionFactory.setUserName("karaf");
        connectionFactory.setPassword("karaf");

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        MessageConsumer consumer = session.createConsumer(session.createQueue("Testqueue"));
        MessageConsumer consumer = session.createConsumer(session.createTopic("Testtopic"));
        consumer.setMessageListener(new HelloMessageListener());

    }

    private class HelloMessageListener implements MessageListener {

        @Override
        public void onMessage(Message message) {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println("Consumer " + Thread.currentThread().getName() + " received message: " + textMessage.getText());
                log.info("Got message. Msg="+textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }
}