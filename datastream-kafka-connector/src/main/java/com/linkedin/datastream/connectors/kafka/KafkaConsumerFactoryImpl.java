package com.linkedin.datastream.connectors.kafka;

import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;


public class KafkaConsumerFactoryImpl implements KafkaConsumerFactory<byte[], byte[]> {
  @Override
  public Consumer<byte[], byte[]> createConsumer(Properties properties) {
    properties.put("key.deserializer", ByteArrayDeserializer.class.getCanonicalName());
    properties.put("value.deserializer", ByteArrayDeserializer.class.getCanonicalName());
    return new KafkaConsumer<>(properties);
  }
}