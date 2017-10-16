package com.zes.dailyassistant.core;

import java.util.UUID;

import android.support.v4.app.Fragment;

public class RemindEditorActivity extends SingleFragmentActivity{

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		UUID id = (UUID)(getIntent().getSerializableExtra(RemindEditorFragment.KEY_ID));
		return RemindEditorFragment.newInstance(id);
	}

}
