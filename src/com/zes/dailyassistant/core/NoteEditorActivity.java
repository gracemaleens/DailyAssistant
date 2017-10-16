package com.zes.dailyassistant.core;

import java.util.UUID;

import android.support.v4.app.Fragment;

public class NoteEditorActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		UUID noteId = (UUID)getIntent().getSerializableExtra(NoteEditorFragment.NOTE_ID);
		return NoteEditorFragment.newInstance(noteId);
	}

}
