package com.walhalla.flashlights;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.walhalla.permissions.PermissionResolver;

public abstract class BaseActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_RESULT = 1;


    PermissionResolver resolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolver = new PermissionResolver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        resolver.resolve();
    }

//    private void openCamera() {
//        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
//        try {
//            //Log.v("CAMERA", mCameraId + " " + mCameraDeviceStateCallback);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (ContextCompat.checkSelfPermission(this, aaaa)
//                        == PackageManager.PERMISSION_GRANTED) {
//                    //cameraManager.openCamera(mCameraId, mCameraDeviceStateCallback,mBackgroundHandler);
//                } else {
//                    if (shouldShowRequestPermissionRationale(aaaa)) {
//                        Toast.makeText(this, "No Permission to use the Camera services", Toast.LENGTH_SHORT).show();
//                    }
//                    requestPermissions(new String[]{aaaa}, REQUEST_CAMERA_RESULT);
//                }
//            } else {
//                //cameraManager.openCamera(mCameraId, mCameraDeviceStateCallback, mBackgroundHandler);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
