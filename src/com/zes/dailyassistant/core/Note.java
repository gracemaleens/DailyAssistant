package com.zes.dailyassistant.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Note {
	private UUID mId;
	private Date mDate;
	private String mTitle;
	private String mText;
	
	public Note(){
		init();
	}
	
	public Note(String text){
		init();
		save(text);
	}
	
	public Note(String title, String text){
		init();
		save(title, text);
	}
	
	public void init(){
		mId = UUID.randomUUID();
		mDate = new Date();
	}
	
	public void save(String text){
		mTitle = text.length() > 18 ? text.substring(0, 18) + "..." : text;
		mText = text;
	}
	
	public void save(String title, String text){
		mTitle = title;
		mText = text;
		mDate = new Date();
	}
	
	public String getDateFormat(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 HH:mm:ss", Locale.CHINA);
		if(mDate != null){
			return dateFormat.format(mDate);
		}
		return null;
	}
	
	public UUID getId() {
		return mId;
	}

	public String getTitle() {
		return mTitle;
	}

	public String getText() {
		return mText;
	}

	public void setId(UUID id) {
		mId = id;
	}
	
	public void setId(String id){
		mId = UUID.fromString(id);
	}

	public void setDate(Date date) {
		mDate = date;
	}
	
	public void setDate(String date){
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 HH:mm:ss", Locale.CHINA);
			mDate = dateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public void setText(String text) {
		mText = text;
	}
}
