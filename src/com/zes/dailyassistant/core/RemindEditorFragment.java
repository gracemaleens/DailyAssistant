package com.zes.dailyassistant.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.zes.dailyassistant.R;
import com.zes.dailyassistant.base.GeneralTitleBar;
import com.zes.dailyassistant.core.Remind.RemindTime;
import com.zes.dailyassistant.core.Remind.Repetition;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RemindEditorFragment extends Fragment implements OnClickListener{
	private GeneralTitleBar mTitleBar;
	protected EditText mTitleEditText;
	private EditText mSiteEditText;
	private TextView mDateTimeTextView;
	private Button mRemindTimeButton;
	private Button mRepetitionButton;
	private EditText mNotesEditText;
	
	private Remind mRemind;
	
	public static final int REQUEST_CODE_DATE_TIME = 1;
	public static final int REQUEST_CODE_REMIND_TIME = 2;
	public static final int REQUEST_CODE_REPETITIONS = 3;
	public static final int REQUEST_CODE_CUSTOM_TIMES = 4;
	public static final String KEY_ID = "com.zes.dailyassistant.core.RemindEditorFragment.id";
	
	public static RemindEditorFragment newInstance(UUID id){
		Bundle args = new Bundle();
		args.putSerializable(KEY_ID, id);
		RemindEditorFragment fragment = new RemindEditorFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		UUID id = (UUID)(getArguments().getSerializable(KEY_ID));
		if(id != null){
			mRemind = RemindManager.getInstance().getRemind(id);
		}
		if(mRemind == null){
			mRemind = new Remind();
		}
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_remind_editor, container, false);
		
		mTitleBar = (GeneralTitleBar)v.findViewById(R.id.remind_editor_generalTitleBar);
		mTitleBar.setLeftOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		mTitleBar.setRightOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(TextUtils.isEmpty(mRemind.getTitle())){
					createRemindFaild("标题不能为空，请重新输入！");
					return;
				}
				Calendar remindCalendar = Calendar.getInstance();
				remindCalendar.setTime(mRemind.getDate());
				if(Calendar.getInstance().after(remindCalendar)){
					createRemindFaild("时间设置错误，请重试！");
					return;
				}
				RemindManager.getInstance().addRemind(getContext(), mRemind);
				setResult();
				getActivity().finish();
			}
		});
		
		mTitleEditText = (EditText)v.findViewById(R.id.remind_editor_title_editText);
		mTitleEditText.setText(mRemind.getTitle());
		mTitleEditText.addTextChangedListener(new RemindEditorTextWatcher(mTitleEditText));
		
		mSiteEditText = (EditText)v.findViewById(R.id.remind_editor_site_editText);
		mSiteEditText.setText(mRemind.getSite());
		mSiteEditText.addTextChangedListener(new RemindEditorTextWatcher(mSiteEditText));
		
		mDateTimeTextView = (TextView)v.findViewById(R.id.remind_editor_fragment_dateTimeTextView);
		mDateTimeTextView.setText(mRemind.getSimpleDateFormat());
		mDateTimeTextView.setOnClickListener(this);
		
		mRemindTimeButton = (Button)v.findViewById(R.id.remind_editor_fragment_remindContent_button);
		mRemindTimeButton.setText(mRemind.getRemindTimeOfString());
		mRemindTimeButton.setOnClickListener(this);
		
		mRepetitionButton = (Button)v.findViewById(R.id.remind_editor_fragment_repetitionContent_Button);
		mRepetitionButton.setText(mRemind.getRepetitionOfString());
		mRepetitionButton.setOnClickListener(this);
		
		mNotesEditText = (EditText)v.findViewById(R.id.remind_editor_fragment_notes_editText);
		mNotesEditText.setText(mRemind.getNotes());
		mNotesEditText.addTextChangedListener(new RemindEditorTextWatcher(mNotesEditText));
		
		return v;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.remind_editor_fragment_dateTimeTextView:
			DateTimePickerDialogFragment dateTimePickerDialog = new DateTimePickerDialogFragment();
			dateTimePickerDialog.setTargetFragment(RemindEditorFragment.this, REQUEST_CODE_DATE_TIME);
			dateTimePickerDialog.show(getFragmentManager(), DateTimePickerDialogFragment.TAG);
			break;
		case R.id.remind_editor_fragment_remindContent_button:
			RemindTimeDialogFragment remindTimeDialogFragment = new RemindTimeDialogFragment();
			remindTimeDialogFragment.setTargetFragment(RemindEditorFragment.this, REQUEST_CODE_REMIND_TIME);
			remindTimeDialogFragment.show(getFragmentManager(), RemindTimeDialogFragment.TAG);
			break;
		case R.id.remind_editor_fragment_repetitionContent_Button:
			RepetitionsDialogFragment repetitionDialogFragment = new RepetitionsDialogFragment();
			repetitionDialogFragment.setTargetFragment(RemindEditorFragment.this, REQUEST_CODE_REPETITIONS);
			repetitionDialogFragment.show(getFragmentManager(), RepetitionsDialogFragment.TAG);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case REQUEST_CODE_DATE_TIME:
			Date date = (Date)data.getSerializableExtra(DateTimePickerDialogFragment.EXTRA_DATETIME);
			if(date != null){
				mRemind.setDate(date);
			}
			mDateTimeTextView.setText(mRemind.getSimpleDateFormat());
			break;
		case REQUEST_CODE_REMIND_TIME:
			int position = data.getIntExtra(RemindTimeDialogFragment.EXTRA_ITEM_POSITION, -1);
			if(position != -1){
				mRemind.setRemindTime(RemindTime.getRemindTimeByIndex(position));
			}
			mRemindTimeButton.setText(Remind.getRemindTimesOfItem(position));
			break;
		case REQUEST_CODE_REPETITIONS:
			int position2 = data.getIntExtra(RemindTimeDialogFragment.EXTRA_ITEM_POSITION, -1);
			if(Repetition.getRepetitionByIndex(position2) == Repetition.CUSTOM) {
				CustomTimesDialogFragment customTimesDialogFragment = CustomTimesDialogFragment.newInstance(mRemind.getCustomTimeSet());
				customTimesDialogFragment.setTargetFragment(RemindEditorFragment.this, REQUEST_CODE_CUSTOM_TIMES);
				customTimesDialogFragment.show(getFragmentManager(), CustomTimesDialogFragment.TAG);
				return;
			}
			if(position2 != -1){
				mRemind.setRepetition(Repetition.getRepetitionByIndex(position2));
			}
			mRepetitionButton.setText(Remind.getRepetitionsOfItem(position2));
			break;
		case REQUEST_CODE_CUSTOM_TIMES:
			@SuppressWarnings("unchecked") ArrayList<Integer> checkedArray = (ArrayList<Integer>)data.getSerializableExtra(CustomTimesDialogFragment.EXTRA_CHECKED);
			if(checkedArray != null) {
				mRemind.setCustomTimeSet(checkedArray);
				mRepetitionButton.setText(Remind.getRepetitionsOfItem(Repetition.CUSTOM));
			}
		default:
			break;
		}
	}
	
	public void createRemindFaild(String message){
		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), android.R.style.Theme_Holo_Light));
		builder.setTitle("提示：");
		builder.setMessage(message);
		builder.setPositiveButton("确定", null);
		builder.create();
		builder.show();
	}
	
	private void setResult() {
		getActivity().setResult(Activity.RESULT_OK);
	}
	
	private class RemindEditorTextWatcher implements TextWatcher{
		private View mView;
		
		public RemindEditorTextWatcher(View v) {
			mView = v;
		}
		

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if(mView != null) {
				switch(mView.getId()) {
				case R.id.remind_editor_title_editText:
					mRemind.setTitle(s.toString());
					break;
				case R.id.remind_editor_site_editText:
					mRemind.setSite(s.toString());
					break;
				case R.id.remind_editor_fragment_notes_editText:
					mRemind.setNotes(s.toString());
					break;
				}
			}
		}
		

	}
}
