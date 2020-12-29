package com.vczyh;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.internals.FutureRecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Future;

public class Producer {
    public static final String TOPIC = "topic-demo";

    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.0.44.9:9092");
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(prop);

        while (true) {
            String msg = "Hello," + new Random().nextInt(100);
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, msg);
            kafkaProducer.send(record, (RecordMetadata metadata, Exception exception) -> {
                if (exception != null) {
                    System.out.printf("Failed to send message，err: %s\n", exception);
                } else {
                    System.out.println("Succeed to send message");
                }
            });
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("Failed to sleep");
                e.printStackTrace();
                kafkaProducer.close();
            }
        }
    }
}
