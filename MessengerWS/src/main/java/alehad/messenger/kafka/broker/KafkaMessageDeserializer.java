package alehad.messenger.kafka.broker;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import alehad.messenger.model.Message;

public class KafkaMessageDeserializer implements Deserializer<Message> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Message deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(new String(data, "UTF-8"), Message.class);
        } catch (Exception e) {
            System.err.println("Unable to deserialize message {} " + data.toString() + e.toString());
            return null;
        }
    }

    @Override
    public void close() {
    }
}
