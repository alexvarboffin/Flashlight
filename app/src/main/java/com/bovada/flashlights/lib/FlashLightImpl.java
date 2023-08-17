package com.bovada.flashlights.lib;

import android.app.Activity;
import android.content.pm.PackageManager;

public class FlashLightImpl implements FlashLight {

    private final Activity a;

    public FlashLightImpl(Activity activity) {
        this.a = activity;
    }

    @Override
    public boolean isFlashAvailable() {
        return a.getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
}
