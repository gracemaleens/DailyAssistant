package com.zes.dailyassistant.base;

import com.zes.dailyassistant.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GeneralTitleBar extends LinearLayout {
	private RelativeLayout mRootLayout;
	private TextView mTitleTextView;
	private Button mLeftButton;
	private TextView mLeftTextView;
	private Button mRightButton;
	private TextView mRightTextView;
	private View mBottomLine;

	private View.OnClickListener mLeftOnClickListener, mRightOnClickListener;

	private static final String TAG = GeneralTitleBar.class.getName();

	public GeneralTitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		View v = LayoutInflater.from(context).inflate(R.layout.view_general_title_bar, this);
		mRootLayout = (RelativeLayout) v.findViewById(R.id.general_title_bar_rootLayout);
		mTitleTextView = (TextView) v.findViewById(R.id.general_title_bar_title);
		mLeftButton = (Button) v.findViewById(R.id.general_title_bar_leftButton);
		mLeftTextView = (TextView) v.findViewById(R.id.general_title_bar_leftTextView);
		mRightButton = (Button) v.findViewById(R.id.general_title_bar_rightButton);
		mRightTextView = (TextView) v.findViewById(R.id.general_title_bar_rightTextView);
		mBottomLine = (View)v.findViewById(R.id.general_title_bar_bottomLine);

		initView(context, attrs);
		setListener();
	}

	private void initView(Context context, AttributeSet attrs) {
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GeneralTitleBar);

		setBackground(mRootLayout, typedArray, R.styleable.GeneralTitleBar_background);

		// Title
		setTextParams(typedArray, R.styleable.GeneralTitleBar_title, R.styleable.GeneralTitleBar_titleColor,
				R.styleable.GeneralTitleBar_titleSize, new SetTextParamsCallback() {

					@Override
					public void setTextSize(int size) {
						// TODO Auto-generated method stub
						setTitleSize(TypedValue.COMPLEX_UNIT_PX, size);
					}

					@Override
					public void setTextColor(int color) {
						// TODO Auto-generated method stub
						setTitleColor(color);
					}

					@Override
					public void setText(String text) {
						// TODO Auto-generated method stub
						setTitle(text);
					}
				});
		if (typedArray.getBoolean(R.styleable.GeneralTitleBar_title_visibility, true)) {
			setTitleVisibility(View.VISIBLE);
		} else {
			setTitleVisibility(View.INVISIBLE);
		}

		// Left
		setTextParams(typedArray, R.styleable.GeneralTitleBar_leftText, R.styleable.GeneralTitleBar_leftTextColor,
				R.styleable.GeneralTitleBar_leftTextSize, new SetTextParamsCallback() {

					@Override
					public void setTextSize(int size) {
						// TODO Auto-generated method stub
						setLeftTextSize(TypedValue.COMPLEX_UNIT_PX, size);
					}

					@Override
					public void setTextColor(int color) {
						// TODO Auto-generated method stub
						setLeftTextColor(color);
					}

					@Override
					public void setText(String text) {
						// TODO Auto-generated method stub
						setLeftText(text);
					}
				});
		int drawableLeftId = typedArray.getResourceId(R.styleable.GeneralTitleBar_leftDrawableLeft, 0);
		if (drawableLeftId > 0) {
			setLeftDrawableLeft(typedArray.getResources().getDrawable(drawableLeftId));
		}
		if (typedArray.getBoolean(R.styleable.GeneralTitleBar_leftVisibility, true)) {
			setLeftVisibility(View.VISIBLE);
		} else {
			setLeftVisibility(View.INVISIBLE);
		}

		//Right
		setTextParams(typedArray, R.styleable.GeneralTitleBar_rightText, R.styleable.GeneralTitleBar_rightTextColor,
				R.styleable.GeneralTitleBar_rightTextSize, new SetTextParamsCallback() {

					@Override
					public void setTextSize(int size) {
						// TODO Auto-generated method stub
						setRightTextSize(TypedValue.COMPLEX_UNIT_PX, size);
					}

					@Override
					public void setTextColor(int color) {
						// TODO Auto-generated method stub
						setRightTextColor(color);
					}

					@Override
					public void setText(String text) {
						// TODO Auto-generated method stub
						setRightText(text);
					}
				});
		int imageId = typedArray.getResourceId(R.styleable.GeneralTitleBar_rightImage, 0);
		if(imageId > 0){
			setRightImage(imageId);
		}
		if (typedArray.getBoolean(R.styleable.GeneralTitleBar_rightVisibility, true)) {
			setRightVisibility(View.VISIBLE);
		} else {
			setRightVisibility(View.INVISIBLE);
		}
		
		int bottomLinecolorId = typedArray.getResourceId(R.styleable.GeneralTitleBar_bottomLineColor, 0);
		if(bottomLinecolorId > 0){
			setBottomLineColorResource(bottomLinecolorId);
		}else{
			int color = typedArray.getColor(R.styleable.GeneralTitleBar_bottomLineColor, -1);
			if(color != -1){
				setBottomLineColor(color);
			}
		}

		typedArray.recycle();
	}

	private void setListener() {
		if (mLeftButton != null) {
			mLeftButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (mLeftOnClickListener != null) {
						mLeftOnClickListener.onClick(v);
					}
				}
			});
		}
		if(mLeftTextView != null){
			mLeftTextView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (mLeftOnClickListener != null) {
						mLeftOnClickListener.onClick(v);
					}
				}
			});
		}
		if (mRightButton != null) {
			mRightButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (mRightOnClickListener != null) {
						mRightOnClickListener.onClick(v);
					}
				}
			});
		}
		if(mRightTextView != null){
			mRightTextView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (mRightOnClickListener != null) {
						mRightOnClickListener.onClick(v);
					}
				}
			});
		}
	}

	public void setLeftOnClickListener(View.OnClickListener l) {
		mLeftOnClickListener = l;
	}

	public void setRightOnClickListener(View.OnClickListener l) {
		mRightOnClickListener = l;
	}

//	public void setBackground(Drawable background) {
//		mRootLayout.setBackground(background);
//	}
//
//	public void setBackgroundResource(int id) {
//		mRootLayout.setBackgroundResource(id);
//	}
//
//	public void setBackgroundColor(int color) {
//		mRootLayout.setBackgroundColor(color);
//	}
	
	@SuppressLint("NewApi")
	public void setRightImage(Drawable background){
		mRightTextView.setVisibility(View.INVISIBLE);
		mRightButton.setBackground(background);
	}
	
	public void setRightImage(int id){
		mRightTextView.setVisibility(View.INVISIBLE);
		mRightButton.setBackgroundResource(id);
		mRightButton.setVisibility(View.VISIBLE);
	}

	public void setTitle(String text) {
		mTitleTextView.setText(text);
	}
	
	public void setTitle(@IdRes int id){
		mTitleTextView.setText(id);
	}

	public void setLeftText(String text) {
		mLeftButton.setVisibility(View.INVISIBLE);
		mLeftTextView.setVisibility(View.VISIBLE);
		mLeftTextView.setText(text);
	}
	
	public void setLeftText(@IdRes int id) {
		mLeftButton.setVisibility(View.INVISIBLE);
		mLeftTextView.setVisibility(View.VISIBLE);
		mLeftTextView.setText(id);
	}

	public void setRightText(String text) {
		mRightButton.setVisibility(View.INVISIBLE);
		mRightTextView.setVisibility(View.VISIBLE);
		mRightTextView.setText(text);
	}
	
	public void setRightText(@IdRes int id) {
		mRightButton.setVisibility(View.INVISIBLE);
		mRightTextView.setVisibility(View.VISIBLE);
		mRightTextView.setText(id);
	}

	public void setTextColor(int color) {
		setTitleColor(color);
		setLeftTextColor(color);
		setRightTextColor(color);
	}

	public void setTitleColor(int color) {
		mTitleTextView.setTextColor(color);
	}

	public void setLeftTextColor(int color) {
		mLeftTextView.setTextColor(color);
	}

	public void setRightTextColor(int color) {
		mRightTextView.setTextColor(color);
	}

	public void setTextSize(int size) {
		setTitleSize(size);
		setLeftTextSize(size);
		setRightTextSize(size);
	}

	public void setTitleSize(int size) {
		mTitleTextView.setTextSize(size);
	}

	public void setTitleSize(int unit, int size) {
		mTitleTextView.setTextSize(unit, size);
	}

	public void setLeftTextSize(int size) {
		mLeftTextView.setTextSize(size);
	}

	public void setLeftTextSize(int unit, int size) {
		mLeftTextView.setTextSize(unit, size);
	}

	public void setRightTextSize(int size) {
		mRightTextView.setTextSize(size);
	}

	public void setRightTextSize(int unit, int size) {
		mRightTextView.setTextSize(unit, size);
	}

	public void setTitleVisibility(int visibility) {
		mTitleTextView.setVisibility(visibility);
	}

	public void setLeftVisibility(int visibility) {
		if(TextUtils.isEmpty(mLeftTextView.getText())){
			mLeftButton.setVisibility(visibility);
		}else{
			mLeftTextView.setVisibility(visibility);
		}
	}
	
	public int getLeftVisibility(){
		if(TextUtils.isEmpty(mLeftTextView.getText())){
			return mLeftButton.getVisibility();
		}else{
			return mLeftTextView.getVisibility();
		}
	}

	public void setRightVisibility(int visibility) {
		if(TextUtils.isEmpty(mRightTextView.getText())){
			mRightButton.setVisibility(visibility);
		}else{
			mRightTextView.setVisibility(visibility);
		}
	}

	public void setLeftDrawableLeft(Drawable drawableLeft) {
		if (drawableLeft != null) {
			mLeftTextView.setVisibility(View.INVISIBLE);
		}
		mLeftButton.setCompoundDrawables(drawableLeft, null, null, null);
	}
	
	public void setBottomLineColor(@ColorInt int color){
		mBottomLine.setBackgroundColor(color);
	}
	
	public void setBottomLineColorResource(@ColorRes int colorId){
		mBottomLine.setBackgroundResource(colorId);
	}

	private void setBackground(View v, TypedArray typedArray, int index) {
		int backgroundId = typedArray.getResourceId(index, 0);
		if (backgroundId > 0) {
			v.setBackgroundResource(backgroundId);
		} else {
			int backgroundColor = typedArray.getColor(index, -1);
			if (backgroundColor != -1) {
				v.setBackgroundColor(backgroundColor);
			}
		}
	}

	private void setTextParams(TypedArray typedArray, int textIndex, int colorIndex, int sizeIndex,
			SetTextParamsCallback setTextParamsCallback) {
		String text;
		int color, size;

		text = getText(typedArray, textIndex);

		// 获得该组件文本颜色
		color = getTextColor(typedArray, colorIndex, -1);
		// 如果获得该组件文本颜色失败，则获得全局文本颜色
		if (color == -1) {
			color = getTextColor(typedArray, R.styleable.GeneralTitleBar_textColor, -1);
		}

		// 获得该组件文本大小
		size = getTextSize(typedArray, sizeIndex, -1);

		// 如果获得该组件文本大小失败，则获得全局文本大小
		if (size == -1) {
			size = getTextSize(typedArray, R.styleable.GeneralTitleBar_textSize, -1);
		}

		if (!TextUtils.isEmpty(text)) {
			setTextParamsCallback.setText(text);
		}
		if (color != -1) {
			setTextParamsCallback.setTextColor(color);
		}
		if (size != -1) {
			setTextParamsCallback.setTextSize(size);
		}

	}

	private String getText(TypedArray typedArray, int index) {
		int id = typedArray.getResourceId(index, 0);
		return id > 0 ? typedArray.getResources().getString(id) : typedArray.getString(index);
	}

	private int getTextColor(TypedArray typedArray, int index, int defValue) {
		int id = typedArray.getResourceId(index, 0);
		return id > 0 ? typedArray.getResources().getColor(id) : typedArray.getColor(index, defValue);
	}

	private int getTextSize(TypedArray typedArray, int index, int defValue) {
		int id = typedArray.getResourceId(index, 0);
		return id > 0 ? typedArray.getResources().getDimensionPixelSize(id)
				: typedArray.getDimensionPixelSize(index, defValue);
	}

	private interface SetTextParamsCallback {
		public void setText(String text);

		public void setTextColor(int color);

		public void setTextSize(int size);
	}

}
