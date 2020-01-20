package com.in3.dto;

import java.io.Serializable;
/**
 * @author Ammar
 * @since 21/1/2020
 * @version 1.0 .
 */
public class SportingNewsDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String link;
	private String title;
	private String date;
	private String source;
	private String id;
	
	
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	
	

}
