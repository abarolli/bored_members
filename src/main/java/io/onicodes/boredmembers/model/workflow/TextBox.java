package io.onicodes.boredmembers.model.workflow;

public class TextBox {
	
	private int x;
	private int y;
	private int width;
	private int height;
	private String strokeColor;
	private String content;
	
	public TextBox() {}
	
	public TextBox(int x, int y, int width, int height, String strokeColor, String content) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.strokeColor = strokeColor;
		this.content = content;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
