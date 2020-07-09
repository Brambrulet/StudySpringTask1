package ru.volnenko.se.component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Component;
import ru.volnenko.se.api.component.IChatProvider;
import ru.volnenko.se.constant.DataConstant;

@Component("JmsChatProvider")
public class JmsChatProvider implements IChatProvider {

    private ActiveMQConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Map<String, MessageProducer> producers = new TreeMap<>();
    private MessageConsumer bcConsumer;
    private MessageConsumer pvpConsumer;
    private ConcurrentMap<String, LocalDateTime> receivedMessageIds = new ConcurrentHashMap<>();
    private String login;

    private static String toString(MapMessage mapMessage) throws JMSException {
        return String.format("%s: %s"
                , mapMessage.getString(DataConstant.SENDER)
                , mapMessage.getString(DataConstant.MESSAGE)
        );
    }

    private void onMessage(Message message, boolean bc) {
        ignoringExceptions(() -> {
            message.acknowledge();
            final String messageStr = toString((MapMessage)message);
            receivedMessageIds.computeIfAbsent(message.getJMSMessageID(), key -> {
                System.out.println(getMessagePrefix(bc) + messageStr);
                return LocalDateTime.now();
            });

            supportMessageIds();
        });
    }

    private void supportMessageIds() {
        if (receivedMessageIds.size() < 1000) {
            return;
        }

        final LocalDateTime fiveMinutesBefore = LocalDateTime.now().minusMinutes(5);
        receivedMessageIds.forEach((id, received) -> {
            if (received.isBefore(fiveMinutesBefore)) {
                receivedMessageIds.remove(id);
            }
        });
    }

    private MessageProducer getProducer(String topicName) throws JMSException {
        MessageProducer producer = producers.get(topicName);

        return producer != null
                ? producer
                : session.createProducer(session.createTopic(topicName));
    }

    private MessageProducer getBcProducer() throws JMSException {
        return getProducer(DataConstant.BROADCAST_TOPIC_NAME);
    }

    private MessageProducer getPvpProducer(String recipient) throws JMSException {
        return getProducer(DataConstant.PVP_TOPIC_NAME_PREFIX + recipient);
    }

    private Message newMessage(String phrase) throws JMSException {
        MapMessage message = session.createMapMessage();
        message.setString(DataConstant.SENDER, login);
        message.setString(DataConstant.MESSAGE, phrase);

        return message;
    }

    private void start() {
        if (connectionFactory != null) {
            return;
        }

        stopOnException(() -> {
            connectionFactory = new ActiveMQConnectionFactory();
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            bcConsumer = session.createConsumer(session.createTopic(DataConstant.BROADCAST_TOPIC_NAME));
            bcConsumer.setMessageListener(message -> onMessage(message, true));
            pvpConsumer = session.createConsumer(session.createTopic(DataConstant.PVP_TOPIC_NAME_PREFIX + login));
            pvpConsumer.setMessageListener(message -> onMessage(message, false));

            connection.start();
        });
    }

    @Override
    public void stop() {
        if (connectionFactory == null) {
            return;
        }
        ignoringExceptions(session::close);
        ignoringExceptions(connection::close);
        connectionFactory = null;
        connection = null;
        session = null;
        bcConsumer = null;
        pvpConsumer = null;

        producers.clear();
        receivedMessageIds.clear();
    }

    @Override
    public void sendMessage(String phrase) {
        stopOnException(() -> getBcProducer().send(newMessage(phrase)));
    }

    @Override
    public void sendMessage(String phrase, String recipient) {
        stopOnException(() -> getPvpProducer(recipient).send(newMessage(phrase)));
    }

    @Override
    public void start(String login) {
        this.login = login;
        start();
    }

    private void stopOnException(ThrowableAction action) {
        try {
            action.execute();
        } catch (Throwable throwable) {
            stop();
        }
    }
}
