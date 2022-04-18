package alehad.messenger.db;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import alehad.messenger.model.IMessageStore;
import alehad.messenger.model.Message;
import alehad.messenger.model.StoredMessage;

public class SimpleMessengerDatabaseImpl implements IMessageStore {

	private List<Message> messages = new ArrayList<Message>();
	
	private static SimpleMessengerDatabaseImpl instance;
	
	public static SimpleMessengerDatabaseImpl getInstance() {
		if (instance == null) {
			instance = new SimpleMessengerDatabaseImpl();
		}
		return instance;
	}

	private SimpleMessengerDatabaseImpl() {
		// initiate message list with couple of messages
		messages.add(new StoredMessage(1, "bonjour a tous!", "moi"));
		messages.add(new StoredMessage(2, "bonjour a vous!", "toi"));
	}

	@Override
	public List<Message> getMessages() {
		return messages;
	}

	@Override
	public Message getMessage(int id) {
		Message message = null;
		ListIterator<Message> iterator = instance.messages.listIterator();
		int storedMsgId = 1; // naive implementation to support Message refactoring
		while (iterator.hasNext()) {
			message = iterator.next();
			if (storedMsgId++ == id) {
				break;
			}
		}
		//TODO: fix implementation, currently it will return last element if id not found
		return message;
	}

	@Override
	public Message createMessage(Message msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message updateMessage(int id, Message msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteMessage(int id) {
		// TODO Auto-generated method stub
		
	}
}
