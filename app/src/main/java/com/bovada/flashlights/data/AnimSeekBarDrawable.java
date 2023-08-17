package com.bovada.flashlights.data;

import com.bovada.flashlights.FT_FlashLight_Blinking;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.StateSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;


public class AnimSeekBarDrawable extends Drawable implements Runnable {
	static final int[] STATE_FOCUSED = {android.R.attr.state_focused};
	static final int[] STATE_PRESSED = {android.R.attr.state_pressed};

	private static final long DELAY = 30;
	//private static final String TAG = "AnimSeekBarDrawable";
	private String mText;
	private float mTextWidth;
	private Drawable mProgress;
	private Paint mPaint;
	private Paint mOutlinePaint;
	private float mTextXScale;
	private int mDelta,mText_intdata;
	private ScrollAnimation mAnimation;
	private Activity  activity;
	private SharedPreferences preferences;
	private Editor editor;
	private long interval_value;
	public AnimSeekBarDrawable(Resources res, boolean labelOnRight) {
		mProgress = res.getDrawable(android.R.drawable.progress_horizontal);
		mText = "";
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setTypeface(Typeface.DEFAULT_BOLD);
		mPaint.setTextSize(16);
		mOutlinePaint = new Paint(mPaint);
		mOutlinePaint.setStyle(Style.STROKE);
		mOutlinePaint.setStrokeWidth(4);
		mOutlinePaint.setMaskFilter(new BlurMaskFilter(1, Blur.NORMAL));
		mTextXScale = labelOnRight? 1 : 0;
		mAnimation = new ScrollAnimation();
	}

	public AnimSeekBarDrawable(Resources res, boolean labelOnRight,
			FT_FlashLight_Blinking ft_FlashLight_Blinking) 
	{

		mProgress = res.getDrawable(android.R.drawable.progress_horizontal);
		mText = "";
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setTypeface(Typeface.DEFAULT_BOLD);
		mPaint.setTextSize(16);
		mOutlinePaint = new Paint(mPaint);
		mOutlinePaint.setStyle(Style.STROKE);
		mOutlinePaint.setStrokeWidth(4);
		mOutlinePaint.setMaskFilter(new BlurMaskFilter(1, Blur.NORMAL));
		mTextXScale = labelOnRight? 1 : 0;
		mAnimation = new ScrollAnimation();
		this.activity=ft_FlashLight_Blinking;
		preferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
		editor = preferences.edit();

	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		mProgress.setBounds(bounds);
	}

	@Override
	protected boolean onStateChange(int[] state) {
		boolean active = StateSet.stateSetMatches(STATE_FOCUSED, state) | StateSet.stateSetMatches(STATE_PRESSED, state);
		mOutlinePaint.setColor(active? 0xffffffff : 0xffbbbbbb);
		mPaint.setColor(active? 0xff000000 : 0xff606060);
		invalidateSelf();
		return false;
	}

	@Override
	public boolean isStateful() {
		return true;
	}

	@Override
	protected boolean onLevelChange(int level) {
		mText = (level / 100) + " %";
		mText_intdata = (level / 100) ;
		
		if(mText_intdata<=10)
		{
			editor.putLong("interval value", 3500);
			editor.commit();
			Log.e("1", 1+"");
		}
		else if(mText_intdata<=20)
		{
			editor.putLong("interval value", 3000);
			editor.commit();
			Log.e("10", 10+"");
		}
		else if(mText_intdata<=30)
		{
			editor.putLong("interval value", 2500);
			editor.commit();
			Log.e("20", 20+"");
		}
		else if(mText_intdata<=40)
		{
			editor.putLong("interval value", 2000);
			editor.commit();
			Log.e("30", 30+"");
		}
		else if(mText_intdata<=50)
		{
			editor.putLong("interval value", 1500);
			editor.commit();
			Log.e("40", 50+"");
		}
		else if(mText_intdata<=60)
		{
			editor.putLong("interval value", 1000);
			editor.commit();
			Log.e("50", 50+"");
		}
		else if(mText_intdata<=70)
		{
			editor.putLong("interval value", 2000);
			editor.commit();
		}
		else if(mText_intdata<=80)
		{
			editor.putLong("interval value", 500);
			editor.commit();
		}
		else if(mText_intdata<=90)
		{
			editor.putLong("interval value", 100);
			editor.commit();
		}
		else if(mText_intdata<=100)
		{
			editor.putLong("interval value", 0);
			editor.commit();
		}
		interval_value=	preferences.getLong("interval value", 3500);
		
		Log.e("AnimSeekBarDrawable interval_value", interval_value+"");
		mTextWidth = mOutlinePaint.measureText(mText);

		if (level < 4000 && mDelta <= 0) {
			mDelta = 1;
			// move to the right
			startScrolling(1);
		} else
			if (level > 6000 && mDelta >= 0) {
				mDelta = -1;
				// move to the left
				startScrolling(0);
			}

		return mProgress.setLevel(level);
	}

	private void startScrolling(int to) {
		mAnimation.startScrolling(mTextXScale, to);
		scheduleSelf(this, SystemClock.uptimeMillis() + DELAY);
	}

	@Override
	public void draw(Canvas canvas) {
		mProgress.draw(canvas);

		if (mAnimation.hasStarted() && !mAnimation.hasEnded()) {
			// pending animation
			mAnimation.getTransformation(AnimationUtils.currentAnimationTimeMillis(), null);
			mTextXScale = mAnimation.getCurrent();
		}

		Rect bounds = getBounds();
		float x = 6 + mTextXScale * (bounds.width() - mTextWidth - 6 - 6);
		float y = (bounds.height() + mPaint.getTextSize()) / 2;
		//canvas.drawText(mText, x, y, mOutlinePaint);
		//canvas.drawText(mText, x, y, mPaint);
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

	@Override
	public void setAlpha(int alpha) {
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
	}

	public void run() {
		mAnimation.getTransformation(AnimationUtils.currentAnimationTimeMillis(), null);
		// close interpolation of mTextX
		mTextXScale = mAnimation.getCurrent();
		if (!mAnimation.hasEnded()) {
			scheduleSelf(this, SystemClock.uptimeMillis() + DELAY);
		}
		invalidateSelf();
	}

	static class ScrollAnimation extends Animation {
		private static final long DURATION = 750;
		private float mFrom;
		private float mTo;
		private float mCurrent;

		public ScrollAnimation() {
			setDuration(DURATION);
			setInterpolator(new DecelerateInterpolator());
		}

		public void startScrolling(float from, float to) {
			mFrom = from;
			mTo = to;
			startNow();
		}

		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t) {
			mCurrent = mFrom + (mTo - mFrom) * interpolatedTime;
		}

		public float getCurrent() {
			return mCurrent;
		}
	}
}
