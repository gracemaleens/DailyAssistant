package com.zes.dailyassistant.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zes.dailyassistant.base.ISerializer;
import com.zes.dailyassistant.core.Remind.CustomTime;
import com.zes.dailyassistant.core.Remind.RemindTime;
import com.zes.dailyassistant.core.Remind.Repetition;

import android.content.Context;

public class RemindManager {
	private static RemindManager sInstance;
	
	private ArrayList<Remind> mReminds = new ArrayList<Remind>();
	
	private static final String JSON_FILENAME = "remind.json";
	private static final String JSON_KEY_TITLE = "remind_title";
	private static final String JSON_KEY_SITE = "remind_site";
	private static final String JSON_KEY_NOTES = "remind_notes";
	private static final String JSON_KEY_DATE = "remind_date";
	private static final String JSON_KEY_ID = "remind_id";
	private static final String JSON_KEY_REMIND_TIME = "remind_remind_time";
	private static final String JSON_KEY_REPETITION = "remind_repetition";
	private static final String JSON_KEY_CUSTOM_TIME_SET = "remind_custom_time_set";
	
	public static RemindManager getInstance(){
		if(sInstance == null){
			sInstance = new RemindManager();
		}
		return sInstance;
	}
	
	public ArrayList<Remind> getReminds(){
		return mReminds;
	}
	
	public void addRemind(Context context, Remind remind){
		mReminds.add(remind);
		save(context);
	}
	
	public void removeRemind(Context context, int index){
		mReminds.remove(index);
		save(context);
	}
	
	public boolean contain(Remind remind) {
		for(Remind r : mReminds) {
			if(r.getId().equals(remind.getId())) {
				return true;
			}
		}
		return false;
	}
	
	public Remind getRemind(UUID id){
		for(Remind r : mReminds){
			if(r.getId().equals(id)){
				return r;
			}
		}
		return null;
	}
	
	public Remind getRemind(int index) {
		return mReminds.get(index);
	}
	
	public void save(Context context) {
		try {
			JSONArray jsonArray = new JSONArray();
			for(Remind r : mReminds) {
				JSONObject jsonObject = new JSONObject();
					jsonObject.put(JSON_KEY_TITLE, r.getTitle());
					jsonObject.put(JSON_KEY_SITE, r.getSite());
					jsonObject.put(JSON_KEY_NOTES, r.getNotes());
					jsonObject.put(JSON_KEY_ID, r.getId());
					jsonObject.put(JSON_KEY_DATE, r.getSimpleDateFormat());
					jsonObject.put(JSON_KEY_REMIND_TIME, r.getRemindTime().ordinal());
					jsonObject.put(JSON_KEY_REPETITION, r.getRepetition().ordinal());
					jsonObject.put(JSON_KEY_CUSTOM_TIME_SET, r.getCustomTimeSetOfJSON());
					jsonArray.put(jsonObject);
			}
			ISerializer.saveDataOfLocal(context, JSON_FILENAME, jsonArray.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void load(Context context) {
		try {
			JSONArray jsonArray = new JSONArray(ISerializer.loadDataOfLocal(context, JSON_FILENAME));
			if(jsonArray != null) {
				if(!mReminds.isEmpty()) {
					mReminds.clear();
				}
				
				for(int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.optJSONObject(i);
					Remind r = new Remind();
					r.setTitle(jsonObject.optString(JSON_KEY_TITLE));
					r.setSite(jsonObject.optString(JSON_KEY_SITE));
					r.setNotes(jsonObject.optString(JSON_KEY_NOTES));
					r.setId(jsonObject.optString(JSON_KEY_ID));
					r.setDate(jsonObject.optString(JSON_KEY_DATE));
					r.setRemindTime(jsonObject.optInt(JSON_KEY_REMIND_TIME));
					r.setRepetition(jsonObject.optInt(JSON_KEY_REPETITION));
					r.setCustomTimeSetOfJSON(jsonObject.optString(JSON_KEY_CUSTOM_TIME_SET));
					mReminds.add(r);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
