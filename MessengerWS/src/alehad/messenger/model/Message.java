package alehad.messenger.model;

public class Message {

	private String 	message;
	private String  author;
	
	public Message() {
		//no-op default constructor
	}
	
	public Message(String message) {
		this.message = message;
		this.author  = "c'est moi!";
	}

	public Message(String message, String author) {
		this.message = message;
		this.author  = author;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

}
