package ru.volnenko.se.component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;
import ru.volnenko.se.api.component.IChatProvider;
import ru.volnenko.se.constant.DataConstant;

@Component("KafkaChatProvider")
public class KafkaChatProvider implements IChatProvider {

    private KafkaConsumer<String, Map<String, String>> consumer;
    private KafkaProducer<String, Map<String, String>> producer;
    private static final Duration CONSUME_TIMEOUT = Duration.ofMillis(100);
    private String login;

    @Override
    public void start(String login) {
        this.login = login;
        createConsumer(login);
        startConsumer();

        createProducer();
    }

    private void createProducer() {
        producer = new KafkaProducer<>(getProducerProperties(), new StringSerializer(), new MapSerializer());
    }

    private void createConsumer(String login) {
        consumer = new KafkaConsumer<>(getConsumerProperties(login), new StringDeserializer(), new MapDeserializer());
        consumer.subscribe(Arrays.asList(DataConstant.PVP_TOPIC_NAME_PREFIX + login,
                                         DataConstant.BROADCAST_TOPIC_NAME
        ));
    }

    private String getMessagePrefix(ConsumerRecord<String, Map<String, String>> record) {
        return getMessagePrefix(DataConstant.BROADCAST_TOPIC_NAME.equals(record.topic()));
    }

    private void startConsumer() {
        Thread consumerThread = new Thread(() -> {
            wholeThread:
            while (Objects.nonNull(consumer)) {
                for (ConsumerRecord<String, Map<String, String>> record : consumer.poll(CONSUME_TIMEOUT)) {
                    if (Objects.isNull(consumer)) {
                        break wholeThread;
                    }
                    System.out.println(getMessagePrefix(record) + toString(record));
                }
                if (Objects.nonNull(consumer)) {
                    consumer.commitSync();
                }
            }
        });
        consumerThread.setDaemon(true);
        consumerThread.start();
    }

    private static String toString(ConsumerRecord<String, Map<String, String>> mapMessage) {
        return String.format("%s: %s"
                , mapMessage.value().getOrDefault(DataConstant.SENDER, "")
                , mapMessage.value().getOrDefault(DataConstant.MESSAGE, "")
        );
    }

    private Properties getConsumerProperties(String group) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", group);
        props.put("enable.auto.commit", "false");
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return props;
    }

    private Properties getProducerProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
//        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return props;
    }

    @Override
    public void stop() {
        if (Objects.isNull(consumer)) {
            return;
        }

        KafkaConsumer<String, Map<String, String>> temp = consumer;
        consumer = null;
        temp.close();
        System.out.println("Kafka consumer stopped");
    }

    @Override
    public void sendMessage(String phrase) {
        producer.send(new ProducerRecord<>(DataConstant.BROADCAST_TOPIC_NAME, newMessage(phrase)));
    }

    @Override
    public void sendMessage(String phrase, String recipient) {
        producer.send(new ProducerRecord<>(DataConstant.PVP_TOPIC_NAME_PREFIX + recipient, newMessage(phrase)));
    }

    private Map<String, String> newMessage(String phrase) {
        Map<String, String> message = new HashMap<>();
        message.put(DataConstant.SENDER, login);
        message.put(DataConstant.MESSAGE, phrase);

        return message;
    }

    public static class MapSerializer implements Serializer<Map<String, String>> {

        @Override
        public byte[] serialize(String key, Map<String, String> message) {
            try (ByteArrayOutputStream bytesStream = new ByteArrayOutputStream();
                    ObjectOutputStream objStream = new ObjectOutputStream(bytesStream);) {
                objStream.writeObject(message);
                return bytesStream.toByteArray();
            } catch (IOException e) {
                // no acts expected
            }
            return new byte[0];
        }
    }

    public static class MapDeserializer implements Deserializer<Map<String, String>> {

        @Override
        public Map<String, String> deserialize(String s, byte[] bytes) {
            try (ByteArrayInputStream bytesStream = new ByteArrayInputStream(bytes);
                    ObjectInputStream objStream = new ObjectInputStream(bytesStream);) {
                return (Map<String, String>) objStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                // no acts expected
            }
            return new HashMap<>();
        }
    }
}
