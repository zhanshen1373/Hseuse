package com.hd.hse.ss.adapter;

public class SupervisionItem {
	private String content;
	private String location;
	private String time;
	private String name;

	

	public SupervisionItem() {

	}

	public SupervisionItem(String content,String location,String name,String time) {
		this.content=content;
		this.location=location;
		this.time=time;
		this.name=name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
