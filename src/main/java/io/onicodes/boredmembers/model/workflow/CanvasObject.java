package io.onicodes.boredmembers.model.workflow;

public class CanvasObject {
	
	private Integer id;
	private TextboxAttributes attributes;
	
	public CanvasObject() {}
	
	public CanvasObject(Integer id, TextboxAttributes attributes) {
		this.id = id;
		this.attributes = attributes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TextboxAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(TextboxAttributes attributes) {
		this.attributes = attributes;
	}
	
}
