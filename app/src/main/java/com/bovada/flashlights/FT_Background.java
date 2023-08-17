package com.bovada.flashlights;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.bovada.flashlights.data.OnSwipeTouchListener;
import com.bovada.unoprom.R;
import com.walhalla.flashlights.BaseActivity;

public class FT_Background extends BaseActivity
{

	private LinearLayout mainlayout;
	private int color;
	private boolean color_flag=false;
	private final int[] mycolor = new int[]{

			Color.BLUE 	,
			Color.CYAN ,	
			Color.DKGRAY ,	
			Color.GRAY ,	
			Color.GREEN ,	
			Color.LTGRAY, 	
			Color.MAGENTA,	
			Color.RED ,	
			Color.YELLOW	

	};
	private int i=0;
	private TextView txtswap1,txtswap2;
	
	private SharedPreferences preferences;
	private Editor editor;
	private int devicesize_flag;
	private LinearLayout llarrow;
	private ConnectivityManager connMgr_ft_background;
	
	
	InterstitialAd interstitialAd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ft_background);
		/* ... */

	//	RelativeLayout rellaymain = (RelativeLayout)findViewById(R.id.rellaymain);
		
		connMgr_ft_background = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connMgr_ft_background.getActiveNetworkInfo() != null && connMgr_ft_background.getActiveNetworkInfo().isAvailable() && connMgr_ft_background.getActiveNetworkInfo().isConnected()) 
		{
			
//			WebView wbviewnews=(WebView)findViewById(R.id.webView);
//			wbviewnews.getSettings().setJavaScriptEnabled(true);
//			wbviewnews.setVerticalScrollBarEnabled(true);
//			wbviewnews.setHorizontalScrollBarEnabled(true);
//			wbviewnews.loadUrl("file:///android_asset/add.html");
			
			
//			try {
//				AdView adView=(AdView)findViewById(R.id.myAdview);
//				adView.setAdListener(this);
//
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			
//			final Activity act = this;  
//			rellaymain.post(new Runnable() {
//	
//				
//	
//				public void run() { 
//					try {
//						
//						//myController = new AdController(act, "414553826","709210155","709210155","709210155");
//						myController_FT_bg = new AdController(act, "815872234","959132951","116796825","472493710");
//						myController_FT_bg.setAdditionalDockingMargin(5);
//						//myController.loadStartAd("MY_LB_NOTIFICATION_ID", "MY_LB_ICON_ID");
//						myController_FT_bg.loadAd();
//	
//	
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//				} 
//				//});
//			});
		}
		
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		editor = preferences.edit();

//		txtinfoname= findViewById(R.id.txtinfoname);
//		txtinfoname.setText(getResources().getString(R.string.Color_Light));

		llarrow= findViewById(R.id.llarrow);
		devicesize_flag=preferences.getInt("devicesize_flag", 2);


		if(devicesize_flag==4)
		{
			//txtinfoname.setTextSize(getResources().getDimension(R.dimen.textdoubleextralargesize));
			//llarrow.setBackgroundResource(R.drawable.arrow_800x1200);
		}

		txtswap1= findViewById(R.id.txtswap1);
		txtswap1.setText(getResources().getString(R.string.Swipe_to_change));

		txtswap2= findViewById(R.id.txtswap2);
		txtswap2.setText(getResources().getString(R.string.brightness_and_color));

//		imgbtnfunction= findViewById(R.id.imgbtnfunction);
//		imgbtnfunction.setBackgroundResource(R.drawable.bg_color_32x32);
		
//		imgbtnback= findViewById(R.id.imgbtnback);
//		imgbtnback.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//
//				onBackPressed();
//			}
//		});

		//		 float hsbVals[] = Color.RGBtoHSB( originalColour.getRed(),
		//                 originalColour.getGreen(),
		//                 originalColour.getBlue(), null );
		//Color highlight = Color.getHSBColor( hsbVals[0], hsbVals[1], 0.5f * ( 1f + hsbVals[2] ));
		//Color shadow = Color.getHSBColor( hsbVals[0], hsbVals[1], 0.5f * hsbVals[2] );


		mainlayout= findViewById(R.id.llbackgroundmainlayout);
		mainlayout.setBackgroundColor(mycolor[i]);
		mainlayout.setOnTouchListener(new OnSwipeTouchListener() {



			public void onSwipeTop() {
				//Toast.makeText(Main.this, "top", Toast.LENGTH_SHORT).show();

				Log.e("i value ", i+"");
				color_flag=true;
				Log.e("top", "top");
				float[] hsv = new float[3];

				if(i==2 || i==3 || i==5)
				{
					Color.colorToHSV(mycolor[i], hsv);
					hsv[2]=(1f *  hsv[2]);
					//	hsv[2] *= 0.8f; // value component
					color = Color.HSVToColor(hsv);
					Log.e("lgt color", "lgt color");
					mainlayout.setBackgroundColor(color);
				}
				else
				{
					Color.colorToHSV(mycolor[i], hsv);
					hsv[2]=3f * ( 1f + hsv[2]);
					//	hsv[2] *= 0.8f; // value component
					color = Color.HSVToColor(hsv);
					Log.e("dark color", "dark color");
					mainlayout.setBackgroundColor(color);
				}
				
			}
			public void onSwipeRight() {
				//Toast.makeText(Main.this, "right", Toast.LENGTH_SHORT).show();
				Log.e("right", "right");

				i++;
				if(i>8)
				{
					i=0;

				}
				float[] hsv = new float[3];
				if(color_flag)
				{


					Color.colorToHSV(mycolor[i], hsv);
					hsv[2]=3f * ( 1f + hsv[2]);
					//	hsv[2] *= 0.8f; // value component
					color = Color.HSVToColor(hsv);


				}
				else
				{


					Color.colorToHSV(mycolor[i], hsv);
					hsv[2]=0.5f * hsv[2];
					//	hsv[2] *= 0.8f; // value component
					color = Color.HSVToColor(hsv);


				}
				mainlayout.setBackgroundColor(color);
				//				float[] hsv = new float[3];
				//
				//				Color.colorToHSV(mycolor[i], hsv);
				//				hsv[2]=1f * hsv[2];
				//				//	hsv[2] *= 0.8f; // value component
				//				color = Color.HSVToColor(hsv);
				//				mainlayout.setBackgroundColor(color);
			}
			public void onSwipeLeft() {
				//Toast.makeText(Main.this, "left", Toast.LENGTH_SHORT).show();
				Log.e("left", "left");
				i--;
				if(i<0)
				{
					i=8;

				}
				float[] hsv = new float[3];
				if(color_flag)
				{


					Color.colorToHSV(mycolor[i], hsv);
					hsv[2]=3f * ( 1f + hsv[2]);
					//	hsv[2] *= 0.8f; // value component
					color = Color.HSVToColor(hsv);


				}
				else
				{


					Color.colorToHSV(mycolor[i], hsv);
					hsv[2]=0.5f * hsv[2];
					//	hsv[2] *= 0.8f; // value component
					color = Color.HSVToColor(hsv);


				}
				mainlayout.setBackgroundColor(color);
				//				float[] hsv = new float[3];
				//
				//				Color.colorToHSV(mycolor[i], hsv);
				//				hsv[2]=1f * hsv[2];
				//				//	hsv[2] *= 0.8f; // value component
				//				color = Color.HSVToColor(hsv);
				//				mainlayout.setBackgroundColor(color);
			}
			public void onSwipeBottom() {
				//Toast.makeText(Main.this, "bottom", Toast.LENGTH_SHORT).show();

				color_flag=false;

				Log.e("bottom", "bottom");
				float[] hsv = new float[3];

				Color.colorToHSV(mycolor[i], hsv);
				hsv[2]=0.5f * hsv[2];
				//	hsv[2] *= 0.8f; // value component
				color = Color.HSVToColor(hsv);

				mainlayout.setBackgroundColor(color);
			}

		});


	}

	
	@Override
	protected void onDestroy() 
	{

		System.gc();
		Runtime.getRuntime().gc();
		super.onDestroy();
		try
		{
			unbindDrawables(findViewById(R.id.llbackgroundmainlayout)); 
		}
		catch (Exception e) 
		{
			Log.e("@@@", e.toString());
		}

	}

	private void unbindDrawables(View view) 
	{
		if (view.getBackground() != null) 
		{
			view.getBackground().setCallback(null);
		}
		if (view instanceof ViewGroup) 
		{
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) 
			{
				unbindDrawables(((ViewGroup) view).getChildAt(i));
			}
			((ViewGroup) view).removeAllViews();
		}
	}
	
}