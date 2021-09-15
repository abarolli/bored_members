package io.onicodes.boredmembers.model.workflow;

public class Avatar {
	
	private int[] coords;
	
	public Avatar() {}

	public Avatar(int[] coords) {
		this.coords = coords;
	}

	public int[] getCoords() {
		return coords;
	}

	public void setCoords(int[] coords) {
		this.coords = coords;
	}

}
