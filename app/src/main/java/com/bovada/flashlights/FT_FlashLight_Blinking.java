package com.bovada.flashlights;

import java.io.IOException;
import java.util.List;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.bovada.unoprom.R;
import com.walhalla.flashlights.BaseActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;


public class FT_FlashLight_Blinking
        extends BaseActivity
        implements SeekBar.OnSeekBarChangeListener, SurfaceHolder.Callback {

    private Camera cam_FlashLight_Blinking;

    private Parameters params;
    private boolean torchflag = true;
    private boolean torchmode = false;

    private SharedPreferences preferences;
    private Editor editor;
    private long interval_value;

    private SeekBar skbarview;
    private Handler m_handler;
    private SurfaceView _surfaceView_FlashLight_Blinking;
    private SurfaceHolder _surfaceHolder_FlashLight_Blinking;
    private int devicesize_flag;
    private ConnectivityManager connMgr_flashlightblinking;
    private Handler handler_wait;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ft_flashlight_blinking);

        //	RelativeLayout rellaymain = (RelativeLayout)findViewById(R.id.rellaymain);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        connMgr_flashlightblinking = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr_flashlightblinking.getActiveNetworkInfo() != null && connMgr_flashlightblinking.getActiveNetworkInfo().isAvailable() && connMgr_flashlightblinking.getActiveNetworkInfo().isConnected()) {

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
//						myController_FT_blink = new AdController(act, "815872234","959132951","116796825","472493710");
//						myController_FT_blink.setAdditionalDockingMargin(5);
//						//myController.loadStartAd("MY_LB_NOTIFICATION_ID", "MY_LB_ICON_ID");
//						myController_FT_blink.loadAd();
//	
//	
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//				} 
//				//});
//			});

        }
        m_handler = new Handler();
        handler_wait = new Handler();
        handler_wait.postDelayed(() -> {
        }, 100); // second

        interval_value = preferences.getLong("interval value", 3000);


//				try {
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}

        try {

            cam_FlashLight_Blinking = Camera.open();

            params = cam_FlashLight_Blinking.getParameters();
        } catch (Exception e) {
            Log.e("CameraFlashActivity", "Camera.open() failed: " + e.getMessage());
        }


        String str_flash = params.getFlashMode();
        Log.e("str_flash", " " + str_flash);

//        txtinfoname = (TextView) findViewById(R.id.txtinfoname);
//        txtinfoname.setText(getResources().getString(R.string.FlashLight_Blinking));

        devicesize_flag = preferences.getInt("devicesize_flag", 2);


        if (devicesize_flag == 4) {
            //txtinfoname.setTextSize(getResources().getDimension(R.dimen.textdoubleextralargesize));
        }

//        imgbtnfunction = findViewById(R.id.imgbtnfunction);
//        imgbtnfunction.setBackgroundResource(R.drawable.flashlight_32x32);

//        imgbtnback = findViewById(R.id.imgbtnback);
//        imgbtnback.setOnClickListener(v -> {
//            // TODO Auto-generated method stub
//
//
//            onBackPressed();
//        });


        //setup((SeekBar) findViewById(R.id.onSeekBar));


        skbarview = findViewById(R.id.skbarview);
        skbarview.setOnSeekBarChangeListener(this);

        _surfaceView_FlashLight_Blinking = findViewById(R.id.blinkingsurfaceView);
        _surfaceHolder_FlashLight_Blinking = _surfaceView_FlashLight_Blinking.getHolder();
        _surfaceHolder_FlashLight_Blinking.addCallback(this);
        _surfaceHolder_FlashLight_Blinking.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        interval_value = preferences.getLong("interval value", 3000);
        m_handler.post(m_statusChecker);

    }

    Runnable m_statusChecker = new Runnable() {
        @Override
        public void run() {
            //	    	 MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.randomsound8);
            //				mp.start();
            interval_value = preferences.getLong("interval value", 3000);


            Log.e("interval_value", interval_value + "");

            if (cam_FlashLight_Blinking != null) {

                if (torchflag) {

                    try {

                        params = cam_FlashLight_Blinking.getParameters();

                        List<String> flashmodes = params.getSupportedFlashModes();
                        if (flashmodes == null || flashmodes.size() == 0) {

                        } else {
                            for (int i = 0; i < flashmodes.size(); i++) {
                                Log.e("" + i, flashmodes.get(i));

                                if (flashmodes.get(i).equalsIgnoreCase(Parameters.FLASH_MODE_TORCH)) {
                                    torchmode = true;
                                    Log.e("torchmode" + i, torchmode + "");
                                }
                            }
                        }

                    } catch (Exception e) {
                        // TODO: handle exception
                    }


                    if (torchmode) {
                        params.setFlashMode(Parameters.FLASH_MODE_TORCH);
                    } else {
                        params.setFlashMode(Parameters.FLASH_MODE_ON);
                    }


                    if (m_handler != null) {


                        if (cam_FlashLight_Blinking != null) {
                            try {
                                cam_FlashLight_Blinking.setParameters(params);
                            } catch (Exception r) {
                                //DLog.handleException(r);
                            }
                            cam_FlashLight_Blinking.startPreview();
                        }
                    }
                    torchflag = false;
                } else {
                    params.setFlashMode(Parameters.FLASH_MODE_OFF);

                    if (m_handler != null) {

                        if (cam_FlashLight_Blinking != null) {
                            cam_FlashLight_Blinking.setParameters(params);

                            cam_FlashLight_Blinking.startPreview();

                        }
                    }
                    torchflag = true;
                }

                try {

                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

            m_handler.postDelayed(m_statusChecker, interval_value);
        }
    };


    void startRepeatingTask() {
        m_statusChecker.run();
    }

    void stopRepeatingTask() {
        m_handler.removeCallbacks(m_statusChecker);
    }

    private void ledon() {

        //		try {
        //			Thread.sleep(100);
        //		} catch (InterruptedException e) {
        //			// TODO Auto-generated catch block
        //			e.printStackTrace();
        //		}


        //		List<String> flashmodes = params.getSupportedFlashModes();
        //
        //		for (int i = 0; i < flashmodes.size(); i++)
        //		{
        //			Log.e("flashmodes", flashmodes.get(i));
        //
        //		}
        //		String supportedFlashModes = params.getFlashMode();
        //		Log.e("supportedFlashModes", supportedFlashModes);

        if (cam_FlashLight_Blinking != null) {

            for (int i = 0; i < 100; i++) {
                if (torchflag) {
                    params.setFlashMode(Parameters.FLASH_MODE_TORCH);

                    torchflag = false;
                } else {
                    params.setFlashMode(Parameters.FLASH_MODE_OFF);

                    torchflag = true;
                }

                //				try {
                //
                //					Thread.sleep(10);
                //				} catch (InterruptedException e) {
                //					e.printStackTrace();
                //				}

                if (cam_FlashLight_Blinking != null) {
                    cam_FlashLight_Blinking.setParameters(params);

                    cam_FlashLight_Blinking.startPreview();


                }

            }


            interval_value = preferences.getLong("interval value", 3000);

            Log.e("interval_value", interval_value + "");
            //			try {
            //				Thread.sleep(interval_value);
            //			} catch (InterruptedException e) {
            //				// TODO Auto-generated catch block
            //				e.printStackTrace();
            //			}

        }


        //		cam_FlashLight_Blinking.stopPreview();
        //		cam_FlashLight_Blinking.release();
    }

    private void ledoff() {
        if (cam_FlashLight_Blinking != null) {
            cam_FlashLight_Blinking.stopPreview();
            cam_FlashLight_Blinking.release();
            cam_FlashLight_Blinking = null;

        }

    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        if (m_handler != null) {
            m_handler.removeCallbacks(m_statusChecker);
        }

        finish();


    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        ledoff();
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        ///mProgressText.setText(progress + " " + "seekbar_from_touch" + "=" + fromTouch);

        if (progress <= 10) {
            editor.putLong("interval value", 3000);
            editor.commit();

        } else if (progress <= 20) {
            editor.putLong("interval value", 2500);
            editor.commit();
        } else if (progress <= 30) {
            editor.putLong("interval value", 2000);
            editor.commit();
        } else if (progress <= 40) {
            editor.putLong("interval value", 1500);
            editor.commit();
        } else if (progress <= 50) {
            editor.putLong("interval value", 1000);
            editor.commit();
        } else if (progress <= 60) {
            editor.putLong("interval value", 500);
            editor.commit();
        } else if (progress <= 70) {
            editor.putLong("interval value", 300);
            editor.commit();
        } else if (progress <= 80) {
            editor.putLong("interval value", 100);
            editor.commit();
        } else if (progress <= 90) {
            editor.putLong("interval value", 50);
            editor.commit();
        } else if (progress <= 100) {
            editor.putLong("interval value", 10);
            editor.commit();
        }

    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        //mTrackingText.setText("seekbar_tracking_on");
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        //mTrackingText.setText("seekbar_tracking_off");


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            cam_FlashLight_Blinking.setPreviewDisplay(holder);
        } catch (IOException e) {
            Log.e("", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }

}
