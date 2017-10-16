package com.zes.dailyassistant.base;

import java.util.ArrayList;
import java.util.Map;

import com.zes.dailyassistant.R;
import com.zes.dailyassistant.tools.ILog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.ListFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class BulkEditListFragment extends ListFragment implements OnClickListener{
	private static final String TAG = BulkEditListFragment.class.getName();
	
	private boolean isEditing = false;
	private boolean isSelectedAll = false;

	@SuppressLint("UseSparseArrays")
	private CheckedMap mCheckedMap = new CheckedMap();
	
	private FragmentTabHost mTabHost;
	private DailyassistantViewPager mPager;
	private RelativeLayout mBottomMenu;
	private Button mBottomDeleteButton;
	private GeneralTitleBar mTitleBar;

	private final String INSTANCE_EDIT = "isEditing";
	private final String INSTANCE_SELECTEDALL = "isSelecttedAll";
	private final String INSTANCE_ITEMCHECKEDS = "itemCheckeds";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState != null){
			isEditing = savedInstanceState.getBoolean(INSTANCE_EDIT);
			isSelectedAll = savedInstanceState.getBoolean(INSTANCE_SELECTEDALL);
			Bundle itemCheckedsBundle = savedInstanceState.getBundle(INSTANCE_ITEMCHECKEDS);
			for(String key : itemCheckedsBundle.keySet()){
				mCheckedMap.put(Integer.parseInt(key), itemCheckedsBundle.getBoolean(key));
			}
		}else{
			initCheckeds();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v = inflater.inflate(R.layout.fragment_bulk_edit, container, false);

		mTitleBar = (GeneralTitleBar) v.findViewById(R.id.bulk_edit_generalTitleBar);
		mTitleBar.setRightOnClickListener(this);
		mTitleBar.setLeftOnClickListener(this);
		
		mTabHost = (FragmentTabHost)getActivity().findViewById(R.id.main_activity_tab_host);
		mPager = (DailyassistantViewPager)getActivity().findViewById(R.id.main_activity_pager);
		mBottomMenu = (RelativeLayout)getActivity().findViewById(R.id.main_activity_bottomMenu);
		mBottomDeleteButton = (Button)getActivity().findViewById(R.id.main_activity_bottomMenu_item_delete);
		mBottomDeleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				removeItemOfBulkEditt();
				bulkEdit();
			}
		});
		
		setListAdapter(new BulkEditAdapter());
		
		return v;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
		outState.putBoolean(INSTANCE_EDIT, isEditing);
		outState.putBoolean(INSTANCE_SELECTEDALL, isSelectedAll);
		Bundle itemCheckedsBundle = new Bundle();
		for(Map.Entry<Integer, Boolean> entry : mCheckedMap.entrySet()){
			itemCheckedsBundle.putBoolean(String.valueOf(entry.getKey()), entry.getValue());
		}
		if(!itemCheckedsBundle.isEmpty()){
			outState.putBundle(INSTANCE_ITEMCHECKEDS, itemCheckedsBundle);
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		if(!isEditing){
			onListItemClick(position);
		}else{
			CheckBox checkBox = getCheckBoxOfListViewItem(position);
			if(checkBox.isChecked()){
				setChecked(position, false);
			}else{
				setChecked(position, true);
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.general_title_bar_leftButton || v.getId() == R.id.general_title_bar_leftTextView){ // 编辑或取消
			bulkEdit();
		}
		
		if(v.getId() == R.id.general_title_bar_rightButton || v.getId() == R.id.general_title_bar_rightTextView){ // 添加或全选
			if(!isEditing()){
				createNewItem();
			}else{
				selectAll();
			}
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(isEditing){
				bulkEdit();
				return true;
			}
		}
		return false;
	}
	
	private void initCheckeds(){
		mCheckedMap.clear();
		for(int i = 0; i < getAdapterCount(); i++){
			mCheckedMap.put(i, false);
		}
	}
	
	private void setChecked(int index, boolean checked){
		CheckBox checkBox = getCheckBoxOfListViewItem(index);
		checkBox.setChecked(checked);
		mCheckedMap.put(index, checked);
		
		if(getCheckedCount() == 0){
			mBottomDeleteButton.setEnabled(false);
		}else{
			mBottomDeleteButton.setEnabled(true);
		}
	}
	
	/**
	 * 获取已经选中的CheckBox数量
	 * @return
	 */
	private int getCheckedCount(){
		int count = 0;
		for(int i = 0; i < mCheckedMap.size(); i++){
			if(mCheckedMap.get(i)){
				count++;
			}
		}
		return count;
	}
	
	/**
	 * 批量编辑ListView
	 * @param editState true: 进入批量编辑状态，否则退出编辑状态
	 */
	protected void bulkEdit(){
		if(getListView() != null){
			if(!isEditing){
				isEditing = true;
				initCheckeds();
				
				startEditOfBase();
			}else{
				endEditOfBase();
				isEditing = false;
			}
		}
	}
	
	private void startEditOfBase() {
		if(mTabHost != null){
			mTabHost.setVisibility(View.GONE);
		}
		if(mPager != null){
			mPager.allowScroll(false);
		}
		setVisibilityOfCheckBoxes(View.VISIBLE);
		mBottomMenu.setVisibility(View.VISIBLE);
		
		startEditOfTitleBar();
	}
	
	protected void startEditOfTitleBar(){
		mTitleBar.setLeftText(R.string.note_fragment_cancel);
		mTitleBar.setTitle(R.string.note_fragment_select);
		mTitleBar.setRightText(R.string.note_fragment_selectAll);
	}
	
	private void endEditOfBase(){
		if(mTabHost != null){
			mTabHost.setVisibility(View.VISIBLE);
		}
		if(mPager != null){
			mPager.allowScroll(true);
		}
		setVisibilityOfCheckBoxes(View.GONE);
		mBottomMenu.setVisibility(View.GONE);
		mBottomDeleteButton.setEnabled(false);
		
		endEditOfTitleBar();
		
		if(getAdapterCount() <= 0){ // 如果没有便签，则隐藏左上角编辑
			mTitleBar.setLeftVisibility(View.INVISIBLE);
		}
	}
	
	protected void endEditOfTitleBar(){
		mTitleBar.setLeftText(R.string.note_fragment_edit);
		mTitleBar.setTitle(R.string.note_fragment_note);
		mTitleBar.setRightImage(R.drawable.pointer_crosshair);
	}
	
	private void setVisibilityOfCheckBoxes(int visibility){
		for(int i = 0; i < getListView().getChildCount(); i++){
			CheckBox checkBox = getCheckBoxOfListViewItem(i);
			checkBox.setVisibility(visibility);
		}
	}
	
	private CheckBox getCheckBoxOfListViewItem(int index){
		return (CheckBox)((View)getListView().getChildAt(index)).findViewById(R.id.list_view_item_checkBox);
	}
	
	private void removeItemOfBulkEditt(){
		ArrayList<Integer> checkedItems = new ArrayList<Integer>();
		ILog.d(TAG, "count=" + getAdapterCount());
		for(int i = 0; i < mCheckedMap.size(); i++){
//			CheckBox checkBox = getCheckBoxOfListViewItem(i);
//			if(checkBox.isChecked()){
//				checkedItems.add(i);
//			}
			if(mCheckedMap.get(i)) {
				checkedItems.add(i);
			}
		}
		if(!checkedItems.isEmpty()){
			for(int i = 0; i < checkedItems.size(); i++){
				removeItem(checkedItems.get(i));
			}
		}
		((BulkEditAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
//	protected void addItem(){
//		mCheckedMap.put(mCheckedMap.size(), false);
//	}
//	
//	protected void removeItem(int index){
//		mCheckedMap.remove(index);
//	}
	
	protected void selectAll(){
		if(isEditing){
			if(isSelectedAll || getCheckedCount() == getListView().getChildCount()){
				isSelectedAll = false;
			}else{
				isSelectedAll = true;
			}
			
			for(int i = 0; i < getListView().getChildCount(); i++){
				setChecked(i, isSelectedAll);
			}
		}
	}
	
	protected boolean isEditing(){
		return isEditing;
	}
	
	protected void notifyDataSetChanged() {
		((BulkEditAdapter)getListAdapter()).notifyDataSetChanged();
	}

	protected abstract int getAdapterCount();

	protected abstract Object getAdapterItem(int position);

	protected abstract String getAdapterTextOfLargeTextView(int position);

	protected abstract String getAdapterTextOfSmallTextView(int position);

	protected abstract void onListItemClick(int position);
	
	protected abstract void createNewItem();
	
	protected abstract void removeItem(int position);
	
	private class ViewHolder {
		public TextView largeTextView;
		public TextView smallTextView;
		public CheckBox checkBox;
	}

	protected class BulkEditAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return getAdapterCount();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return getAdapterItem(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder;

			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.view_note_fragment_list_view_item, parent, false);
				viewHolder = new ViewHolder();
				viewHolder.largeTextView = (TextView) convertView.findViewById(R.id.list_view_item_title);
				viewHolder.smallTextView = (TextView) convertView.findViewById(R.id.list_view_item_date);
				viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.list_view_item_checkBox);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.largeTextView.setText(getAdapterTextOfLargeTextView(position));
			viewHolder.smallTextView.setText(getAdapterTextOfSmallTextView(position));

			if (isEditing) {
				startEditOfBase();
				viewHolder.checkBox.setVisibility(View.VISIBLE);
				viewHolder.checkBox.setChecked(mCheckedMap.get(position));
			}
			
			if(getAdapterCount() > 0 && mTitleBar.getLeftVisibility() != View.VISIBLE){ // 判断是否显示左上角编辑
				mTitleBar.setLeftVisibility(View.VISIBLE);
			}
			
			return convertView;
		}
	}
}
