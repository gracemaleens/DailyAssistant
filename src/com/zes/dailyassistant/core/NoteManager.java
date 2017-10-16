package com.zes.dailyassistant.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zes.dailyassistant.base.ISerializer;

import android.content.Context;

public class NoteManager {
	private static NoteManager sInstance;
	private ArrayList<Note> mNotes;
	
	private static final String JSON_FILENAME = "notes.json";
	private static final String JSON_KEY_DATE = "noteDate";
	private static final String JSON_KEY_TITLE = "noteTitle";
	private static final String JSON_KEY_TEXT = "noteText";
	private static final String JSON_KEY_ID = "noteId";
	
	private static final String TAG = NoteManager.class.getName();
	
	private NoteManager(){
		mNotes = new ArrayList<Note>();
//		load();
	}
	
	public static synchronized NoteManager getInstance(){
		if(sInstance == null){
			sInstance = new NoteManager();
		}
		return sInstance;
	}
	
	public ArrayList<Note> getNotes(){
		return mNotes;
	}
	
	public Note getNote(UUID noteId){
		if(mNotes != null){
			for(int i = 0; i < mNotes.size(); i++){
				if(mNotes.get(i).getId().equals(noteId)){
					return mNotes.get(i);
				}
			}
		}
		return null;
	}
	
	public int getIndex(Note note){
		return getIndex(note.getId());
	}
	
	public int getIndex(UUID id){
		for(int i = 0; i < mNotes.size(); i++){
			if(mNotes.get(i).getId().equals(id)){
				return i;
			}
		}
		return -1;
	}
	
	public boolean contain(Note note){
		if(note != null){
			return contain(note.getId());
		}
		return false;
	}
	
	public boolean contain(UUID id){
		for(int i = 0; i < mNotes.size(); i++){
			if(mNotes.get(i).getId().equals(id)){
				return true;
			}
		}
		return false;
	}
	
	public void addNote(Context context, Note note) {
		if(!contain(note)) {
			mNotes.add(note);
			save(context);
		}
	}
	
	public void removeNote(Context context, int index){
		mNotes.remove(index);
		save(context);
	}
	
	public void removeNote(Context context, UUID nodeId){
		for(int i = 0; i < mNotes.size(); i++){
			if (mNotes.get(i).getId().equals(nodeId)) {
				mNotes.remove(i);
				save(context);
			}
		}
	}
	
	public void save(Context context){
		try {
			JSONArray jsonArray = new JSONArray();
			for(Note note : mNotes){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put(JSON_KEY_ID, note.getId());
				jsonObject.put(JSON_KEY_DATE, note.getDateFormat());
				jsonObject.put(JSON_KEY_TITLE, note.getTitle());
				jsonObject.put(JSON_KEY_TEXT, note.getText());
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
	
	public void load(Context context){
		try {
			String data = ISerializer.loadDataOfLocal(context, JSON_FILENAME);
			JSONArray jsonArray = new JSONArray(data);
			if(!mNotes.isEmpty()){
				mNotes.clear();
			}
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject jsonObject = jsonArray.optJSONObject(i);
				Note note = new Note();
				note.setId(jsonObject.optString(JSON_KEY_ID));
				note.setDate(jsonObject.optString(JSON_KEY_DATE));
				note.setTitle(jsonObject.optString(JSON_KEY_TITLE));
				note.setText(jsonObject.optString(JSON_KEY_TEXT));
				mNotes.add(note);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
