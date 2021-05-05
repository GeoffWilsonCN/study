package com.f.bean;

public class Commodity {
	private int id;
	private int class_id;
	private String title;
	private float price;
	private String place;
	private String alcohol;
	private String content;
	private String als;
	private String ale;
	private float prs;
	private float pre;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getAlcohol() {
		return alcohol;
	}
	public void setAlcohol(String alcohol) {
		this.alcohol = alcohol;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getAls() {
		return als;
	}
	public void setAls(String als) {
		this.als = als;
	}
	public String getAle() {
		return ale;
	}
	public void setAle(String ale) {
		this.ale = ale;
	}
	public float getPrs() {
		return prs;
	}
	public void setPrs(float prs) {
		this.prs = prs;
	}
	public float getPre() {
		return pre;
	}
	public void setPre(float pre) {
		this.pre = pre;
	}
	@Override
	public String toString() {
		return "Commodity [id=" + id + ", class_id=" + class_id + ", title=" + title + ", price=" + price + ", place="
				+ place + ", alcohol=" + alcohol + ", content=" + content + ", als=" + als + ", ale=" + ale + ", prs="
				+ prs + ", pre=" + pre + "]";
	}
	
	
}
