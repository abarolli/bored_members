package io.onicodes.boredmembers.model.message;

public class AuthoredChatMessage extends ChatMessage {
	
	private Integer id;
	private String author;

	public AuthoredChatMessage(String author, String message, Integer id) {
		super(message);
		this.id = id;
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
