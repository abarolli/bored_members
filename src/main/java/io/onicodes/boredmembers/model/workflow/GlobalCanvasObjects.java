package io.onicodes.boredmembers.model.workflow;

import java.util.Map;

public class GlobalCanvasObjects {
	
	private TextBox[] textBoxes;
	private Map<String, Avatar> avatars;
	
	public GlobalCanvasObjects() {}

	public GlobalCanvasObjects(TextBox[] textBoxes, Map<String, Avatar> avatars) {
		this.textBoxes = textBoxes;
		this.avatars = avatars;
	}

	public TextBox[] getTextBoxes() {
		return textBoxes;
	}

	public void setTextBoxes(TextBox[] textBoxes) {
		this.textBoxes= textBoxes;
	}

	public Map<String, Avatar> getAvatars() {
		return avatars;
	}

	public void setAvatars(Map<String, Avatar> avatars) {
		this.avatars = avatars;
	}

}