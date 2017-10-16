package com.zes.dailyassistant.core;

import java.util.UUID;

import com.zes.dailyassistant.R;
import com.zes.dailyassistant.base.GeneralTitleBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class NoteEditorFragment extends Fragment {
	public static final String NOTE_TEXT = "noteText";
	public static final String NOTE_ID = "noteId";
	
	private GeneralTitleBar mTitleBar;
	private EditText mEditText;
	private Note mNote;
	
	private static final String TAG = NoteEditorFragment.class.getName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		UUID noteId = (UUID)getArguments().getSerializable(NOTE_ID);
		mNote = NoteManager.getInstance().getNote(noteId);
		
		setResult();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_note_editor, container, false);
		
		mTitleBar = (GeneralTitleBar)v.findViewById(R.id.note_editor_titleBar);
		mTitleBar.setRightOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setEditable(false);
			}
		});
		mTitleBar.setLeftOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		
		mEditText = (EditText)v.findViewById(R.id.note_editor_editText);
		if(mNote != null){
			mEditText.setText(mNote.getText());
		}
		mEditText.setCursorVisible(false);
		mEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				mNote.save(s.toString());
				setResult();
			}
		});
		mEditText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setEditable(true);
			}
		});
		
		return v;
	}
	
	public static NoteEditorFragment newInstance(UUID noteId){
		Bundle args = new Bundle();
		args.putSerializable(NOTE_ID, noteId);
		NoteEditorFragment fragment = new NoteEditorFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	public void setResult(){
		Intent data = new Intent();
		data.putExtra(NOTE_TEXT, mNote.getText());
		data.putExtra(NOTE_ID, mNote.getId());
		getActivity().setResult(NoteFragment.RESULT_NOTE_EDITOR, data);
	}
	
	public void setEditable(boolean editable){
		InputMethodManager imm =(InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if(editable){
			imm.showSoftInputFromInputMethod(mEditText.getWindowToken(), 0);
			mEditText.setCursorVisible(true);
			mTitleBar.setRightVisibility(View.VISIBLE);
		}else{
			imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
			mEditText.setCursorVisible(false);
			mTitleBar.setRightVisibility(View.INVISIBLE);
		}
	}
}
