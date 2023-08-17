package com.bovada.unoprom.fragment;

import java.io.IOException;
import java.util.List;

import com.bovada.flashlights.lib.F_Type;
import com.bovada.flashlights.lib.GameMainView;
import com.bovada.unoprom.R;
import com.walhalla.ui.BuildConfig;
import com.walhalla.ui.DLog;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import org.apache.cordova.fragment.BaseFragment;

public class FTSimpleFlashLight
        extends Fragment
        implements SurfaceHolder.Callback {

    private static final String KEY_TYPE = "key.type_obj";
    private int _t = F_Type.TYPE1;
    private GameMainView mCallback;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            _t = getArguments().getInt(KEY_TYPE);
        }
    }

    public static Fragment newInstance(int s1) {
        FTSimpleFlashLight fr = new FTSimpleFlashLight();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, s1);
        fr.setArguments(bundle);
        return fr;
    }


    //private TextView txtinfoname;

    private ImageButton imgbtnsimpleflashlgt;
    private Camera camera;

    private SharedPreferences preferences;
    private Editor editor;
    private boolean simple_flashlight;
    private SurfaceView _surfaceView_Simple_FlashLight;


    private SurfaceHolder _surfaceHolder;
    private int devicesize_flag;
    private boolean torchmode = false;
    private ConnectivityManager connectivityManager;
    private Handler handler_wait;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ft_simple_flashlight, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //	RelativeLayout rellaymain = (RelativeLayout)findViewById(R.id.rellaymain);
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected()) {

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
            //						myController_FT_simple = new AdController(act, "815872234","959132951","116796825","472493710");
            //						myController_FT_simple.setAdditionalDockingMargin(5);
            //						//myController.loadStartAd("MY_LB_NOTIFICATION_ID", "MY_LB_ICON_ID");
            //						myController_FT_simple.loadAd();
            //
            //
            //					} catch (Exception e) {
            //						// TODO: handle exception
            //					}
            //				}
            //				//});
            //			});
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = preferences.edit();

        devicesize_flag = preferences.getInt("devicesize_flag", 2);

        handler_wait = new Handler();
        handler_wait.postDelayed(() -> {

        }, 200); // second


        //set title
//        txtinfoname = view.findViewById(R.id.txtinfoname);
//        txtinfoname.setText(getResources().getString(R.string.Torch));
//        if (devicesize_flag == 4) {
//            txtinfoname.setTextSize(getResources().getDimension(R.dimen.textdoubleextralargesize));
//        }

        View rootView = view.findViewById(R.id.rootView);
        imgbtnsimpleflashlgt = view.findViewById(R.id.imgbtnsimpleflashlgt);
        if (_t == F_Type.TYPE2) {
            imgbtnsimpleflashlgt.setBackgroundResource(R.drawable.lamp_off);
            rootView.setBackgroundColor(Color.BLACK);
        } else if (_t == F_Type.TYPE3) {
            imgbtnsimpleflashlgt.setBackgroundResource(R.drawable.button_pressed);
            rootView.setBackgroundResource(R.drawable.whoopee_cushion_bg);
        }

        //imgbtnback = view.findViewById(R.id.imgbtnback);

        //imgbtnfunction = view.findViewById(R.id.imgbtnfunction);
        //imgbtnfunction.setBackgroundResource(R.drawable.torch_icon_32x32);

        _surfaceView_Simple_FlashLight = view.findViewById(R.id.surfaceView);
        makeSurfaceHolder();
    }

    private void makeSurfaceHolder() {
        _surfaceHolder = _surfaceView_Simple_FlashLight.getHolder();
        _surfaceHolder.addCallback(FTSimpleFlashLight.this);
        _surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    @Override
    public void onResume() {
        super.onResume();

        devicesize_flag = preferences.getInt("devicesize_flag", 2);

//        imgbtnback.setOnClickListener((OnClickListener) v -> {
//            if (simple_flashlight) {
//                ledoff();
//            }
//            //finish();
//        });


        imgbtnsimpleflashlgt.setOnClickListener(v -> {

            if (mCallback != null) {
                if (!mCallback.isResolved()) {
                    mCallback.resolveCamera();
                } else {

                    if (BuildConfig.DEBUG) {
                        if (camera != null) {
                            Parameters param = camera.getParameters();
                            DLog.d(param.getFlashMode() + "<MODE> " + Parameters.FLASH_MODE_OFF.equals(param.getFlashMode()));
                            DLog.d(param.getFlashMode() + "<MODE> " + Parameters.FLASH_MODE_TORCH.equals(param.getFlashMode()));
                        }
                    }

                    if (simple_flashlight) {
                        clickHandler(simple_flashlight);
                        turnOffFlashLight();
                    } else {


                        if (camera == null) {
                            handler_wait = new Handler();
                            handler_wait.postDelayed(() -> {
                                try {
                                    camera = Camera.open();
                                    camera.startPreview();
                                } catch (RuntimeException e) {
                                    DLog.d("@@@ Camera.open() failed: " + e.getMessage());
                                }
                                ledOnRequest();
                            }, 300); // second

                        } else {
                            ledOnRequest();
                        }

                    }
                }
            }
        });
    }

    private void ledOnRequest() {
        //if (camera == null) {
        makeSurfaceHolder();
        initializeCamera();
        //}
        handler_wait = new Handler();
        handler_wait.postDelayed(() -> {
            DLog.d("@@ isOk? " + (camera != null));
            clickHandler(simple_flashlight);
            ledon0();
        }, 300); // second
    }

    private void clickHandler(boolean simple_flashlight) {
        if (F_Type.TYPE2 == _t) {
            if (simple_flashlight) {
                imgbtnsimpleflashlgt.setBackgroundResource(R.drawable.lamp_off);
            } else {
                imgbtnsimpleflashlgt.setBackgroundResource(R.drawable.lamp_on);
            }
        } else if (F_Type.TYPE3 == _t) {
            if (simple_flashlight) {
                imgbtnsimpleflashlgt.setBackgroundResource(R.drawable.button_pressed);
            } else {
                imgbtnsimpleflashlgt.setBackgroundResource(R.drawable.button_default);
            }
        } else {
            if (simple_flashlight) {
                imgbtnsimpleflashlgt.setBackgroundResource(R.drawable.lgt_start);
            } else {
                imgbtnsimpleflashlgt.setBackgroundResource(R.drawable.lgt_stop);
            }
        }
    }


    private void ledon0() {


        if (camera != null) {

            try {

                Parameters parameters = camera.getParameters();
                List<String> flashmodes = parameters.getSupportedFlashModes();
//                DLog.d("@@@@->" + simple_flashlight + " "
//                        + ((cam_Simple_FlashLight == null))
//                +(flashmodes == null));

                if (flashmodes == null || flashmodes.size() == 0) {
                    simple_flashlight = true;
                } else {
                    for (int i = 0; i < flashmodes.size(); i++) {
                        Log.e("torchmode list" + i, flashmodes.get(i));

                        if (flashmodes.get(i).equalsIgnoreCase(Parameters.FLASH_MODE_TORCH)) {
                            torchmode = true;
                            Log.e("torchmode" + i, torchmode + "");
                        }
                    }
                }


                if (torchmode) {
                    parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
                } else {
                    parameters.setFlashMode(Parameters.FLASH_MODE_ON);
                }

                parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);


                DLog.d("@ 0 " + torchmode);
                camera.setParameters(parameters);
//                try {
//                    camera.setParameters(parameters);
//                } catch (Exception e) {
//                    Parameters param = camera.getParameters();
//                    param.setFlashMode(Parameters.FLASH_MODE_TORCH);
//                    camera.setParameters(param);
//                }
                simple_flashlight = true;
                DLog.d("@ 1");
            } catch (Exception e) {
                DLog.handleException(e);
            }
        }
    }

    private void turnOffFlashLight() {
        try {
            Parameters params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
        } catch (Exception e) {
            // TODO: handle exception
        }
        simple_flashlight = false;
    }


    //@Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //super.onBackPressed();

        if (simple_flashlight) {
            turnOffFlashLight();
        }
    }

    @Override
    public void onDestroy() {
        if (simple_flashlight) {
            turnOffFlashLight();
        }
        super.onDestroy();

        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w,
                               int h) {
//        Camera.Parameters parameters = camera.getParameters();
//        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
//        Camera.Size previewSize =previewSizes.get(0);
//        parameters.setPreviewSize(previewSize.width, previewSize.height);
//        try {
//            camera.setParameters(parameters);
//            camera.startPreview();
//            DLog.d("<0>" + previewSize.width + ":" + previewSize.height);
//        }catch (Exception e){
//            DLog.handleException(e);
//        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initializeCamera();
    }

    private void initializeCamera() {
        try {
            try {
                camera = Camera.open();
                camera.startPreview();
            } catch (RuntimeException e) {
                DLog.handleException(e);
            }
            if (camera != null) { //null if not permission
                camera.setPreviewDisplay(_surfaceHolder);
            }
        } catch (IOException e) {
            camera.release();
            camera = null;
            DLog.handleException(e);
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        // Surface will be destroyed when we return, so stop the preview.
        // Because the CameraDevice object is not a shared resource, it's very
        // important to release it when the activity is paused.
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GameMainView) {
            this.mCallback = (GameMainView) context;
        } else {
            throw new RuntimeException(context + " must implement Callback");
        }
    }
}
