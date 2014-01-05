package com.smart.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TreeNode implements Serializable {
	private boolean leaf;
	private boolean checked;
	private String text;
	
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
