package com.bovada.flashlights;

import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.bovada.unoprom.R;
import com.walhalla.flashlights.BaseActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FT_Traffic_Signal extends BaseActivity
{


	private Timer timer;
	private TimerTask timerTask;
	private LinearLayout lltraficlgt;
	private final int[] mysignal = new int[]{
			R.drawable.trafic_red,
			R.drawable.trafic_yellow,
			R.drawable.trafic_green
	};
	private int i=0;
	private ImageView imgbtntraficlgt;
	private TextView txtinfoname;

	private int devicesize_flag;
	private SharedPreferences preferences;
	private Editor editor;
	private ConnectivityManager connMgr_trafficsignal;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ft_traffic_signal);

	//	RelativeLayout rellaymain = (RelativeLayout)findViewById(R.id.rellaymain);
		
		connMgr_trafficsignal = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connMgr_trafficsignal.getActiveNetworkInfo() != null && connMgr_trafficsignal.getActiveNetworkInfo().isAvailable() && connMgr_trafficsignal.getActiveNetworkInfo().isConnected()) 
		{
			
//			WebView wbviewnews=(WebView)findViewById(R.id.webView);
//			wbviewnews.getSettings().setJavaScriptEnabled(true);
//			wbviewnews.setVerticalScrollBarEnabled(true);
//			wbviewnews.setHorizontalScrollBarEnabled(true);
//			wbviewnews.loadUrl("file:///android_asset/add.html");
			
//			try {
//				
//				AdView adView=(AdView)findViewById(R.id.myAdview);
//				adView.setAdListener(this);
//
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
			
//			final Activity act = this;  
//			rellaymain.post(new Runnable() {
//	
//				
//	
//				public void run() { 
//					try {
//						
//						//myController = new AdController(act, "414553826","709210155","709210155","709210155");
//						myController_FT_traffic = new AdController(act, "815872234","959132951","116796825","472493710");
//						myController_FT_traffic.setAdditionalDockingMargin(5);
//						//myController.loadStartAd("MY_LB_NOTIFICATION_ID", "MY_LB_ICON_ID");
//						myController_FT_traffic.loadAd();
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

		lltraficlgt= findViewById(R.id.lltraficlgt);
		imgbtntraficlgt= findViewById(R.id.imgbtntraficlgt);

//		txtinfoname= findViewById(R.id.txtinfoname);
//		txtinfoname.setText(getResources().getString(R.string.Traffic_Light));
		devicesize_flag=preferences.getInt("devicesize_flag", 2);


		if(devicesize_flag==4)
		{
			//txtinfoname.setTextSize(getResources().getDimension(R.dimen.textdoubleextralargesize));
		}

//		imgbtnfunction=(ImageButton)findViewById(R.id.imgbtnfunction);
//		imgbtnfunction.setBackgroundResource(R.drawable.traffic_icon_32x32);

		
//		imgbtnback= findViewById(R.id.imgbtnback);
//		imgbtnback.setOnClickListener(v -> {
//			// TODO Auto-generated method stub
//
//
//			onBackPressed();
//		});

//		try {
//
//			Thread.sleep(1);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		if(timer!=null)
			timer.cancel();

		timerTask=new TimerTask() 
		{	


			@Override
			public void run() 
			{

				runOnUiThread(new Runnable() {
					public void run() {


						if(i>2)
						{
							i=0;

						}

						imgbtntraficlgt.setImageResource(mysignal[i]);
						i++;
					}
				});

			}
		};
		timer=new Timer();
		timer.schedule(timerTask,500,500);

	}





	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		if(timer!=null)
			timer.cancel();

		finish();


	}

	@Override
	protected void onDestroy() 
	{

		System.gc();
		Runtime.getRuntime().gc();
		super.onDestroy();
		try
		{
			unbindDrawables(findViewById(R.id.lltraficlgt)); 
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