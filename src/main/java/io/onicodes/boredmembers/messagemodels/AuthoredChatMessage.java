package io.onicodes.boredmembers.messagemodels;

public class AuthoredChatMessage extends ChatMessage {
	
	private String author;

	public AuthoredChatMessage(String author, String message, Integer id) {
		super(id, message);
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
