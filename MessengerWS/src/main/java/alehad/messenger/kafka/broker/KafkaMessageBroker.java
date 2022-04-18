package alehad.messenger.kafka.broker;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import alehad.messenger.db.MongoDatabaseImpl;
import alehad.messenger.model.Message;

public class KafkaMessageBroker {
	
	public static class Topics {
		public final static String GetAllMessages = "alehad.messenger.topic.getall";
		public final static String GetOneMessage  = "alehad.messenger.topic.getone";
		public final static String AddOneMessage  = "alehad.messenger.topic.addone";
		public final static String UpdateMessage  = "alehad.messenger.topic.update";
		public final static String DeleteMessage  = "alehad.messenger.topic.delete";
	}
	
	public static final int ALL_MESSAGES = 0;
	
	private static String BootstrapServer = "localhost:29092";
	private static String Group = "alehad-group";

	public KafkaMessageBroker() {
		// no-op
	}
	
	public void publish(String topic, String key, Message value) {
		
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaMessageBroker.BootstrapServer);
        props.put("kafka.topic.name", topic);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", KafkaMessageSerializer.class.getName());
        
        KafkaProducer<String, Message> producer = new KafkaProducer<>(props);

        try {
            ProducerRecord<String, Message> msg = new ProducerRecord<>(topic, key, value);
            producer.send(msg);
            System.out.println(new Date() + " publishing record: key = " + msg.key() + " message = " + msg.value().getMessage());
        } catch (Exception e) {
            System.err.println("Failed to send message by the producer " + e.toString());
        } finally {
        	producer.close();
        }
	}
	
	public List<Message> processRequest() {
		
        Properties props = new Properties();
        
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaMessageBroker.BootstrapServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaMessageDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaMessageBroker.Group);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		List<Message> result = new ArrayList<Message>();

		final Consumer<String, Message> consumer = new KafkaConsumer<String, Message>(props);
        consumer.subscribe(Arrays.asList(Topics.GetAllMessages, Topics.GetOneMessage, Topics.AddOneMessage, Topics.UpdateMessage, Topics.DeleteMessage));

        int total_count = 0;

        try {
          while (true) {
        	System.out.println(new Date() + " total processed request count = " + Integer.toString(total_count));  
            //ConsumerRecords<String, Message> records = consumer.poll(1000);
            ConsumerRecords<String, Message> records = consumer.poll(Duration.ofSeconds(1L));
            for (ConsumerRecord<String, Message> record : records) {
				String topic = record.topic();
				String key = record.key();
                String value = record.value().getMessage();
				total_count += 1;
                System.out.printf("Consumed record on topic %s: key = %s and value = %s, and updated total count to %d%n", topic, key, value, total_count);
				switch (topic) {
		        	case Topics.GetAllMessages:
		        	{
		        		result = MongoDatabaseImpl.getInstance().getMessages();
		        		break;
		        	}
		        	case Topics.GetOneMessage:
		        	{
		        		Message msg = MongoDatabaseImpl.getInstance().getMessage(Integer.valueOf(key));
		        		result.add(msg);
		        		break;
		        	}
		        	case Topics.AddOneMessage:
		        	{
		        		Message msg = MongoDatabaseImpl.getInstance().createMessage(record.value());
		        		result.add(msg);
		        		break;
		        	}
		        	case Topics.UpdateMessage:
		        	{
		        		Message msg = MongoDatabaseImpl.getInstance().updateMessage(Integer.valueOf(key), record.value());
		        		result.add(msg);
		        		break;
		        	}
		        	case Topics.DeleteMessage:
		        	{
		        		MongoDatabaseImpl.getInstance().deleteMessage(Integer.valueOf(key));
		        		break;
		        	}
		        	default:
		        	{
		        		System.err.println("Unknown topic to process: " + topic);
		        	}
				}
            }
            if (total_count > 0) {
            	break;
            }
          }
        } catch (Exception e) {
            System.err.println("Failed to process message by the consumer " + e.toString());
        } finally {
          consumer.close();
        }
		
        return result;
	}
}
