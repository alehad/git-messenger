package alehad.messenger.model;

public class StoredMessage extends Message implements Comparable <StoredMessage> {
    
    private int	msgId;
	
	public StoredMessage() {
		//no-op default constructor
	}

	public StoredMessage(int msgId, Message msg) {
        super(msg.getMessage(), msg.getAuthor());
        this.msgId = msgId;
    }

	public StoredMessage(int msgId, String message, String author) {
        super(message, author);
        this.msgId = msgId;
    }
    
	public int getMessageId() {
		return msgId;
	}
	public void setMessageId(int msgId) {
		this.msgId = msgId;
	}

    @Override
    public int compareTo(StoredMessage o) {
        return this.msgId == o.msgId ? 0 : 1; // trivial implementation
    }    
}
