package com.zes.dailyassistant.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;

import com.zes.dailyassistant.base.SharedMethod;

public class Remind {
	private String mTitle;
	private String mSite;
	private String mNotes;
	private Date mDate;
	private UUID mId;
	private RemindTime mRemindTime;
	private Repetition mRepetition;
	private EnumSet<CustomTime> mCustomTimeSet;

	public static final String DATEFORMAT_PATTERN = "yyyy-MM-dd EE HH:mm:ss";

	public static enum RemindTime {
		TIME_SCHEDULE_OCCURRENCE, // 发生时
		FIVE_MINUTES_AGO, // 五分钟前
		TEN_MINUTES_AGO, // 十分钟前
		FIFTEEN_MINUTES_AGO, // 十五分钟前
		THIRTY_MINUTES_AGO, // 三十分钟前
		AN_HOUR_AGO, // 一小时前
		TWO_HOUR_AGO, // 二小时前
		A_DAY_EARLIER, // 一天前
		TWO_DAYS_EARLIER; // 二天前

		public static RemindTime getRemindTimeByIndex(int ordinal) {
			return SharedMethod.getEnumByIndex(RemindTime.class, ordinal);
		}
	};

	public static enum Repetition {
		NEVER, EVERY_DAY, EVERY_WEEK, EVERY_MONTH, EVERY_YEAR, CUSTOM;

		public static Repetition getRepetitionByIndex(int ordinal) {
			return SharedMethod.getEnumByIndex(Repetition.class, ordinal);
		}
	}

	public static enum CustomTime {
		SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAYS, FRIDAY, SATURDAY;

		public static CustomTime getCustomTimeByIndex(int ordinal) {
			return SharedMethod.getEnumByIndex(CustomTime.class, ordinal);
		}
	}

	private static EnumMap<RemindTime, String> sRemindTimeMap = new EnumMap<Remind.RemindTime, String>(
			RemindTime.class);
	private static EnumMap<Repetition, String> sRepetitionMap = new EnumMap<Remind.Repetition, String>(
			Repetition.class);
	private static EnumMap<CustomTime, String> sCustomTimeMap = new EnumMap<Remind.CustomTime, String>(
			CustomTime.class);

	static {
		sRemindTimeMap.put(RemindTime.TIME_SCHEDULE_OCCURRENCE, "日程发生时");
		sRemindTimeMap.put(RemindTime.FIVE_MINUTES_AGO, "五分钟前");
		sRemindTimeMap.put(RemindTime.TEN_MINUTES_AGO, "十分钟前");
		sRemindTimeMap.put(RemindTime.FIFTEEN_MINUTES_AGO, "十五分钟前");
		sRemindTimeMap.put(RemindTime.THIRTY_MINUTES_AGO, "三十分钟前");
		sRemindTimeMap.put(RemindTime.AN_HOUR_AGO, "一小时前");
		sRemindTimeMap.put(RemindTime.TWO_HOUR_AGO, "二小时前");
		sRemindTimeMap.put(RemindTime.A_DAY_EARLIER, "一天前");
		sRemindTimeMap.put(RemindTime.TWO_DAYS_EARLIER, "二天前");

		sRepetitionMap.put(Repetition.NEVER, "永不");
		sRepetitionMap.put(Repetition.EVERY_DAY, "每天");
		sRepetitionMap.put(Repetition.EVERY_WEEK, "每周");
		sRepetitionMap.put(Repetition.EVERY_MONTH, "每月");
		sRepetitionMap.put(Repetition.EVERY_YEAR, "每年");
		sRepetitionMap.put(Repetition.CUSTOM, "自定");

		sCustomTimeMap.put(CustomTime.SUNDAY, "周日");
		sCustomTimeMap.put(CustomTime.MONDAY, "周一");
		sCustomTimeMap.put(CustomTime.TUESDAY, "周二");
		sCustomTimeMap.put(CustomTime.WEDNESDAY, "周三");
		sCustomTimeMap.put(CustomTime.THURSDAYS, "周四");
		sCustomTimeMap.put(CustomTime.FRIDAY, "周五");
		sCustomTimeMap.put(CustomTime.SATURDAY, "周六");

	}

	public static int getRemindTimesOfSize() {
		return sRemindTimeMap.size();
	}

	public static int getRepetitionsOfSize() {
		return sRepetitionMap.size();
	}

	public static int getCustomTimesOfSize() {
		return sCustomTimeMap.size();
	}

	public static String getRemindTimesOfItem(RemindTime remindTime) {
		return sRemindTimeMap.get(remindTime);
	}

	public static String getRemindTimesOfItem(int index) {
		return sRemindTimeMap.get(RemindTime.getRemindTimeByIndex(index));
	}

	public static String getRepetitionsOfItem(Repetition repetition) {
		return sRepetitionMap.get(repetition);
	}

	public static String getRepetitionsOfItem(int index) {
		return sRepetitionMap.get(Repetition.getRepetitionByIndex(index));
	}

	public static String getCustomTimesOfItem(CustomTime customTime) {
		return sCustomTimeMap.get(customTime);
	}

	public static String getCustomTimesOfItem(int index) {
		return sCustomTimeMap.get(CustomTime.getCustomTimeByIndex(index));
	}

	public Remind() {
		// TODO Auto-generated constructor stub
		mDate = new Date();
		mId = UUID.randomUUID();
		mRemindTime = RemindTime.TEN_MINUTES_AGO;
		mRepetition = Repetition.NEVER;
		mCustomTimeSet = EnumSet.noneOf(CustomTime.class);
	}

	public Remind(String title, Date date) {
		mTitle = title;
		mDate = date;
		mId = UUID.randomUUID();
		mRemindTime = RemindTime.TEN_MINUTES_AGO;
		mRepetition = Repetition.NEVER;
		mCustomTimeSet = EnumSet.noneOf(CustomTime.class);
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String text) {
		mTitle = text;
	}

	public String getSite() {
		return mSite;
	}

	public void setSite(String site) {
		mSite = site;
	}

	public String getNotes() {
		return mNotes;
	}

	public void setNotes(String notes) {
		mNotes = notes;
	}

	public Date getDate() {
		return mDate;
	}
	
	public String getSimpleDateFormat() {
		return new SimpleDateFormat(DATEFORMAT_PATTERN).format(mDate);
	}

	public void setDate(Date date) {
		mDate = date;
	}
	
	public void setDate(String date) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT_PATTERN);
			mDate = dateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public UUID getId() {
		return mId;
	}
	
	public void setId(UUID id) {
		mId = id;
	}
	
	public void setId(String id) {
		mId = UUID.fromString(id);
	}

	public RemindTime getRemindTime() {
		return mRemindTime;
	}
	
	public int getOrdinalOfRemindTime() {
		return mRemindTime.ordinal();
	}

	public void setRemindTime(RemindTime remindTime) {
		mRemindTime = remindTime;
	}
	
	public void setRemindTime(int ordinal) {
		mRemindTime = RemindTime.getRemindTimeByIndex(ordinal);
	}

	public Repetition getRepetition() {
		return mRepetition;
	}
	
	public int getOrdinalOfRepetition() {
		return mRepetition.ordinal();
	}

	public void setRepetition(Repetition repetition) {
		mRepetition = repetition;
	}
	
	public void setRepetition(int ordinal) {
		mRepetition = Repetition.getRepetitionByIndex(ordinal);
	}

	public EnumSet<CustomTime> getCustomTimeSet() {
		return mCustomTimeSet;
	}
	
	public String getCustomTimeSetOfJSON() {
		JSONArray jsonArray = new JSONArray();
		for(CustomTime ct : mCustomTimeSet) {
			jsonArray.put(ct.ordinal());
		}
		return jsonArray.toString();
	}

	public void setCustomTimeSet(ArrayList<Integer> customTimes) {
		if (mRepetition != Repetition.CUSTOM) {
			mRepetition = Repetition.CUSTOM;
		}
		if(!mCustomTimeSet.isEmpty()) {
			mCustomTimeSet.clear();
		}
		for (Integer customTime : customTimes) {
			mCustomTimeSet.add(CustomTime.getCustomTimeByIndex(customTime));
		}
	}
	
	public void setCustomTimeSetOfJSON(String json) {
		try {
			ArrayList<Integer> customTimeArray = new ArrayList<Integer>();
			JSONArray jsonArray = new JSONArray(json);
			for(int i = 0; i < jsonArray.length(); i++) {
				customTimeArray.add(jsonArray.getInt(i));
			}
			if(!customTimeArray.isEmpty()) {
				setCustomTimeSet(customTimeArray);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getRemindTimeOfString() {
		return sRemindTimeMap.get(mRemindTime);
	}

	public String getRepetitionOfString() {
		return sRepetitionMap.get(mRepetition);
	}
}
