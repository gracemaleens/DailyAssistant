package com.zes.dailyassistant.core;

import java.util.UUID;

import com.zes.dailyassistant.base.BulkEditListFragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class NoteFragment extends BulkEditListFragment implements OnClickListener {
	private static final String TAG = NoteFragment.class.getName();

	public static final int RESULT_NOTE_EDITOR = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onCreateView");
		View v = super.onCreateView(inflater, container, savedInstanceState);

		return v;
	}

	// @Override
	// public void onPause() {
	// // TODO Auto-generated method stub
	// Log.d(TAG, "onPause");
	// super.onPause();
	// NoteManager.getInstance().save(getContext());
	// }

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onActivityResult");
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_NOTE_EDITOR) {
			try {
				String text = data.getStringExtra(NoteEditorFragment.NOTE_TEXT);
				UUID noteId = (UUID) data.getSerializableExtra(NoteEditorFragment.NOTE_ID);
				if (TextUtils.isEmpty(text)) {
					removeItem(NoteManager.getInstance().getIndex(noteId));
				}
			} catch (NullPointerException e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				((BulkEditAdapter) getListAdapter()).notifyDataSetChanged(); // 不管有没有更新数据，都刷新一次界面，防止第一次添加的数据没有及时刷新
			}
		}
	}

	private void startNoteEditorActivity(Note note) {
		if (note == null) {
			note = new Note();
		}
		addItem(note);
		Intent intent = new Intent(getContext(), NoteEditorActivity.class);
		intent.putExtra(NoteEditorFragment.NOTE_ID, note.getId());
		startActivityForResult(intent, RESULT_NOTE_EDITOR);
	}

	private void addItem(Note note) {
		NoteManager.getInstance().addNote(getContext(), note);

		// super.addItem();
	}

	@Override
	protected void removeItem(int index) {
		// super.removeItem(index);
		NoteManager.getInstance().removeNote(getContext(), index);
	}

	@Override
	protected int getAdapterCount() {
		// TODO Auto-generated method stub
		return NoteManager.getInstance().getNotes().size();
	}

	@Override
	protected Object getAdapterItem(int position) {
		// TODO Auto-generated method stub
		return NoteManager.getInstance().getNotes().get(position);
	}

	@Override
	protected String getAdapterTextOfLargeTextView(int position) {
		// TODO Auto-generated method stub
		return NoteManager.getInstance().getNotes().get(position).getTitle();
	}

	@Override
	protected String getAdapterTextOfSmallTextView(int position) {
		// TODO Auto-generated method stub
		return NoteManager.getInstance().getNotes().get(position).getDateFormat();
	}

	@Override
	protected void onListItemClick(int position) {
		// TODO Auto-generated method stub
		startNoteEditorActivity(NoteManager.getInstance().getNotes().get(position));
	}

	@Override
	protected void createNewItem() {
		// TODO Auto-generated method stub
		startNoteEditorActivity(null);
	}

}
