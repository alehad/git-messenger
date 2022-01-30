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

import alehad.messenger.db.MongoDatabaseImpl;
//import alehad.messenger.db.SimpleMessengerDatabaseImpl;
import alehad.messenger.model.IMessageStore;
import alehad.messenger.model.Message;
import alehad.messenger.model.StoredMessage;

@Path("api/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageAPIServer {

	private IMessageStore messageStore = null;
	
	public MessageAPIServer() {
		// no-op default implementation
		// need to clean up later
		//messageStore = SimpleMessengerDatabaseImpl.getInstance();
		messageStore = MongoDatabaseImpl.getInstance();
	}
	
	@GET
	//@Path("/messages")
	public List<StoredMessage> getMessages() {
		return messageStore.getMessages();
	}

	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") int id) {
		return messageStore.getMessage(id);
	}

	@POST
	public Message addMessage(Message message) {
		return messageStore.createMessage(message);
	}

	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") int id, Message message) {
		return messageStore.updateMessage(id, message);
	}

	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") int id) {
		messageStore.deleteMessage(id);
	}
}
