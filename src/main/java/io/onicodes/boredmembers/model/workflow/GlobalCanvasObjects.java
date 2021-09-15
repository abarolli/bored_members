package io.onicodes.boredmembers.model.workflow;

import java.util.List;
import java.util.Map;


public class GlobalCanvasObjects {
	
	private String canvasState;
	
	public GlobalCanvasObjects() {}

	public GlobalCanvasObjects(String canvasState) {
		this.canvasState = canvasState;
	}

	public String getCanvasState() {
		return canvasState;
	}

	public void setCanvasState(String canvasState) {
		this.canvasState = canvasState;
	}
	
}