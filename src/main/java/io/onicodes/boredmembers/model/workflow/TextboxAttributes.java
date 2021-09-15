package io.onicodes.boredmembers.model.workflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TextboxAttributes {
	private Double height;
	private Double width;
	private Double left;
	private Double top;
	private String text;
	private Integer fontSize;
	private String backgroundColor;
	
	public TextboxAttributes() {}

	public TextboxAttributes(
			Double height, Double width, 
			Double left, Double top, 
			String text, Integer fontSize, String backgroundColor) {
//		this.type = type;
		this.height = height;
		this.width = width;
		this.left = left;
		this.top = top;
		this.text = text;
		this.fontSize = fontSize;
		this.backgroundColor = backgroundColor;
	}

//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getLeft() {
		return left;
	}

	public void setLeft(Double left) {
		this.left = left;
	}

	public Double getTop() {
		return top;
	}

	public void setTop(Double top) {
		this.top = top;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getFontSize() {
		return fontSize;
	}

	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
}
