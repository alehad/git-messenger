package alehad.messenger.ws.server;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import alehad.messenger.kafka.broker.KafkaMessageBroker;
import alehad.messenger.model.Message;

@Path("api/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageAPIServer {

	KafkaMessageBroker kafkaBroker;

	public MessageAPIServer() {
		kafkaBroker = new KafkaMessageBroker();
	}
	
	@GET
	//@Path("/messages")
	public List<Message> getMessages() {
		kafkaBroker.publish(KafkaMessageBroker.Topics.GetAllMessages, Integer.toString(KafkaMessageBroker.ALL_MESSAGES), new Message());
		return kafkaBroker.processRequest();
	}

	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") int id) {
		kafkaBroker.publish(KafkaMessageBroker.Topics.GetOneMessage, Integer.toString(id), new Message());
		List<Message> result = kafkaBroker.processRequest();
		return result.size() > 0 ? result.get(0) : null; 
	}

	@POST
	public Message addMessage(Message message) {
		kafkaBroker.publish(KafkaMessageBroker.Topics.AddOneMessage, "", message);
		List<Message> result = kafkaBroker.processRequest();
		return result.size() > 0 ? result.get(0) : null; 
	}

	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") int id, Message message) {
		kafkaBroker.publish(KafkaMessageBroker.Topics.UpdateMessage, Integer.toString(id), message);
		List<Message> result = kafkaBroker.processRequest();
		return result.size() > 0 ? result.get(0) : null; 
	}

	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") int id) {
		kafkaBroker.publish(KafkaMessageBroker.Topics.DeleteMessage, Integer.toString(id), new Message());
		kafkaBroker.processRequest();
	}
}
