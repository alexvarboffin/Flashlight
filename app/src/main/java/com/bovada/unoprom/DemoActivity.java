package com.bovada.unoprom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.bovada.flashlights.FT_Background;
import com.bovada.flashlights.FT_FlashLight_Blinking;
import com.bovada.flashlights.FTPoliceLight;
import com.bovada.flashlights.FT_Screen_Light;
import com.bovada.flashlights.FT_Traffic_Signal;
import com.bovada.flashlights.data.Constants;

import com.walhalla.ui.DLog;

public class DemoActivity extends AppCompatActivity {

    //private View imgbtnsetting;
    private ImageButton imgbtnscreenlgt, imgbtnflashlgtblink, imgbtntraficsignallgt;
    private ImageButton imgbtnpolicelight, imgbtnscreenbackground;
    private boolean hasFlash;

    private int devicesize_flag;

    private ConnectivityManager connMgr_flashlightmain;
    private Handler m_handler_flashlight, m_handler_blinking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        hasFlash = preferences.getBoolean("hasFlash", true);
        Log.e("hasFlash", "" + hasFlash);

        if (hasFlash) {
            setContentView(R.layout.ft_main_activity);

            imgbtnflashlgtblink = findViewById(R.id.imgbtnflashlgtblnk);

//            View imgbtnflashlgt = findViewById(R.id.imgbtnflashlgt);
//            imgbtnflashlgt.setOnClickListener(v -> {
//                // TODO Auto-generated method stub
//
//                m_handler_flashlight = new Handler();
//                m_handler_flashlight.postDelayed(() -> {
//
//                    Intent authIntent = new Intent(this,
//                            FT_Simple_FlashLight.class);
//                    startActivity(authIntent);
//
//
//                }, 10); // second
//
//                //				Intent authIntent = new Intent(this,FT_Simple_FlashLight.class);
//                //				startActivity(authIntent);
//
//            });

            imgbtnflashlgtblink.setOnClickListener(v -> {
                // TODO Auto-generated method stub

                m_handler_blinking = new Handler();
                m_handler_blinking.postDelayed(() -> {

                    Intent authIntent = new Intent(
                            DemoActivity.this, FT_FlashLight_Blinking.class);
                    startActivity(authIntent);
                }, 10); // second

                //				Intent authIntent = new Intent(this,FT_FlashLight_Blinking.class);
                //				startActivity(authIntent);
            });

        } else {
            setContentView(R.layout.ft_main_activity_2);
//            
//            
//            Bovada Games 

//		    interstitial = new InterstitialAd(this);
//		    interstitial.setAdUnitId("ca-app-pub-9366591393970813/4561978780");
//		    interstitial.loadAd(adRequest);
        }

        Constants.startWakeLock(this);

        // RelativeLayout rellaymain = (RelativeLayout)findViewById(R.id.rellaymain);

        connMgr_flashlightmain = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr_flashlightmain.getActiveNetworkInfo() != null && connMgr_flashlightmain.getActiveNetworkInfo().isAvailable() && connMgr_flashlightmain.getActiveNetworkInfo().isConnected()) {
        }

//        TextView txtheadingname = findViewById(R.id.txtheadingname);
//        txtheadingname.setText(getResources().getString(R.string.app_name));


//        imgbtnsetting = findViewById(R.id.imgbtnsetting);
//        imgbtnsetting.setVisibility(ImageButton.GONE);

        //imgbtninfo = findViewById(R.id.imgbtninfo);


        imgbtnscreenlgt = findViewById(R.id.imgbtnscreenlgt);

        imgbtntraficsignallgt = findViewById(R.id.imgbtntraficsignallgt);
        imgbtnpolicelight = findViewById(R.id.imgbtnpolicelight);
        imgbtnscreenbackground = findViewById(R.id.imgbtnscreenbackground);

        devicesize_flag = preferences.getInt("devicesize_flag", 2);

//        View btn_Buy_PRO = findViewById(R.id.btn_Buy_PRO);
//        btn_Buy_PRO.setOnClickListener(v -> {
//            // TODO Auto-generated method stub
//
//            if (connMgr_flashlightmain.getActiveNetworkInfo() != null && connMgr_flashlightmain.getActiveNetworkInfo().isAvailable() && connMgr_flashlightmain.getActiveNetworkInfo().isConnected()) {
//
//                Intent authIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.PRO_link));
//                startActivity(authIntent);
//            } else {
//                Toast.makeText(FT_Test_Activity.this, R.string.No_Internet_Connection, Toast.LENGTH_SHORT).show();
//            }
//        });

        if (devicesize_flag == 4) {
            //fl_header_layout.setBackgroundResource(R.drawable.header_back_800x100);
            //txtheadingname.setTextSize(getResources().getDimension(R.dimen.textdoubleextralargesize));
            //			imgbtnflashlgt.setImageResource(R.drawable.icon_torch_150x150);
            //			imgbtnscreenlgt.setImageResource(R.drawable.icon_screen_lgt_150x150);
            //			imgbtnflashlgtblink.setImageResource(R.drawable.icon_flashlight_150x150);
            //			imgbtntraficsignallgt.setImageResource(R.drawable.icon_trafic_150x150);
            //			imgbtnpolicelight.setImageResource(R.drawable.icon_police_light_150x150);
            //			imgbtnscreenbackground.setImageResource(R.drawable.icon_bg_color_150x150);
        }


        //		imgbtnsetting.setOnClickListener(new OnClickListener() {
        //
        //			@Override
        //			public void onClick(View v) {
        //				// TODO Auto-generated method stub
        //
        //				Intent authIntent = new Intent(this,FT_Setting.class);
        //				startActivity(authIntent);
        //
        //			}
        //		});

//        imgbtninfo.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Intent authIntent = new Intent(FT_Test_Activity.this, AboutUS.class);
//                startActivity(authIntent);
//            }
//        });

        imgbtnscreenlgt.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            Intent authIntent = new Intent(DemoActivity.this, FT_Screen_Light.class);
            startActivity(authIntent);
            //interstitial.show();
        });

        imgbtntraficsignallgt.setOnClickListener(v -> {
            // TODO Auto-generated method stub

            Intent authIntent = new Intent(this, FT_Traffic_Signal.class);
            startActivity(authIntent);
            //interstitial.show();
        });

        imgbtnpolicelight.setOnClickListener(v -> {
            // TODO Auto-generated method stub

            Intent authIntent = new Intent(DemoActivity.this, FTPoliceLight.class);
            startActivity(authIntent);

            //interstitial.show();
        });
        imgbtnscreenbackground.setOnClickListener(v -> {
            // TODO Auto-generated method stub

            Intent authIntent = new Intent(this, FT_Background.class);
            startActivity(authIntent);
        });

        if (connMgr_flashlightmain.getActiveNetworkInfo() != null && connMgr_flashlightmain.getActiveNetworkInfo().isAvailable() && connMgr_flashlightmain.getActiveNetworkInfo().isConnected()) {
            //			try {
            //				
            //				AdView adView=(AdView)findViewById(R.id.myAdview);
            //				adView.setAdListener(this);
            //
            //			} catch (Exception e) {
            //				// TODO: handle exception
            //			}
        }

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        if (connMgr_flashlightmain.getActiveNetworkInfo() != null && connMgr_flashlightmain.getActiveNetworkInfo().isAvailable() && connMgr_flashlightmain.getActiveNetworkInfo().isConnected()) {
            DLog.d("@@@" + Constants.infocounter + "");

        }
    }

    @Override
    public void onBackPressed() {
        if (connMgr_flashlightmain.getActiveNetworkInfo() != null && connMgr_flashlightmain.getActiveNetworkInfo().isAvailable() && connMgr_flashlightmain.getActiveNetworkInfo().isConnected()) {
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        Constants.stopWakeLock();
        System.gc();
        Runtime.getRuntime().gc();
        super.onDestroy();
        try {
            unbindDrawables(findViewById(R.id.llmainactivity));
        } catch (Exception e) {
            Log.e("@@", e.toString());
        }

    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}
