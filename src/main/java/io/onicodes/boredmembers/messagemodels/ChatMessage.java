package io.onicodes.boredmembers.messagemodels;

public class ChatMessage {
	
	private Integer id;
	private String content;
	
	public ChatMessage() {}
	
	public ChatMessage(Integer id, String content) {
		this.id = id;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	};

}
