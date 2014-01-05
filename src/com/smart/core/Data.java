package com.smart.core;
import java.util.List;
import java.util.Date;

public class Data {
	
	private int totalProperty;
	// 数据集合
	private List root;
	
	private Date start;
	private Date end;
	
	// 进行分组的列名
	private String groupColumn;
	private String sortCol;
	private String SortDir;
	
	
	public String getSortCol() {
		return sortCol;
	}
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
	public String getSortDir() {
		return SortDir;
	}
	public void setSortDir(String sortDir) {
		SortDir = sortDir;
	}
	public String getGroupColumn() {
		return groupColumn;
	}
	public void setGroupColumn(String groupColumn) {
		this.groupColumn = groupColumn;
	}
	
	public int getTotalProperty() {
		return totalProperty;
	}
	public void setTotalProperty(int totalProperty) {
		this.totalProperty = totalProperty;
	}
	public List getRoot() {
		return root;
	}
	public void setRoot(List root) {
		this.root = root;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
}
