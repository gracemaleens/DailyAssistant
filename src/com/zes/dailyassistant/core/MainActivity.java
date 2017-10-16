package com.zes.dailyassistant.core;

import java.util.ArrayList;

import com.zes.dailyassistant.R;
import com.zes.dailyassistant.tools.ILog;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	private ViewPager mPager;
	private FragmentTabHost mTabHost;
	private IPagerAdapter mAdapter;
	
	private String[] mTabTitles = {"便签", "日程提醒", "云空间", "我"};
	private Class<?>[] mPagerFragments = {NoteFragment.class, RemindFragment.class, CloudSpaceFragment.class, AccountFragment.class};
	
	@TargetApi(21)
	@Override
	protected void onCreate(Bundle saveInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(saveInstanceState);
		setContentView(R.layout.activity_main);
		
		ILog.isDebug(true);
		
		initData();
		
		mAdapter = new IPagerAdapter(getSupportFragmentManager());
		
		mPager = (ViewPager)findViewById(R.id.main_activity_pager);
		mPager.setAdapter(mAdapter);
		mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				mTabHost.setCurrentTab(position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mTabHost = (FragmentTabHost)findViewById(R.id.main_activity_tab_host);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.main_activity_pager);
		for(int i = 0; i < mTabTitles.length; i++){
			mTabHost.addTab(mTabHost.newTabSpec(mTabTitles[i]).setIndicator(createTabView(mTabTitles[i])), mPagerFragments[i], null);
//			mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selected_button_background);
		}
		mTabHost.getTabWidget().setDividerDrawable(android.R.color.black);
		mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				int position = mTabHost.getCurrentTab();
				mPager.setCurrentItem(position);
			}
		});
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Fragment fragment = mAdapter.getItem(mTabHost.getCurrentTab());
		if(fragment instanceof NoteFragment){
			if(((NoteFragment)fragment).onKeyDown(keyCode, event)){
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 异步加载（按始化）数据
	 */
	private void initData(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				NoteManager.getInstance().load(MainActivity.this);
				RemindManager.getInstance().load(MainActivity.this);
			}
		}).start();
	}
	
	private View createTabView(String title){
		View v = getLayoutInflater().inflate(R.layout.view_tab, null);
		TextView textView = (TextView)v.findViewById(R.id.view_tab_title);
		textView.setText(title);
		
		return v;
	}
	
	private class IPagerAdapter extends FragmentPagerAdapter{
		private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();

		public IPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
			mFragments.add(new NoteFragment());
			mFragments.add(new RemindFragment());
			mFragments.add(new CloudSpaceFragment());
			mFragments.add(new AccountFragment());
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return mFragments.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mFragments.size();
		}
		
	}
}
