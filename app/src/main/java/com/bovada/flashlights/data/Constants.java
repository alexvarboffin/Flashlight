package com.bovada.flashlights.data;

import android.app.Activity;
import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;


public class Constants {

//com.soulappsworld.flashlights

    public static int screenwidth;
    public static int screenheight;
    public static WakeLock wakeLock;

    //public static String PRO_link="https://play.google.com/store/apps/details?id=com.king.candycrushsaga";
    public static String aApp_link0 = "https://play.google.com/store/apps/details?id=";

    public static int infocounter = 0;
    private static final String WAKE_LOCK_TAG = "TORCH_WAKE_LOCK";


    public static void startWakeLock(Activity ft_Main_Activity) {
        if (wakeLock == null) {
            Log.d(WAKE_LOCK_TAG, "wakeLock is null, getting a new WakeLock");
            PowerManager pm = (PowerManager) ft_Main_Activity.getSystemService(Context.POWER_SERVICE);
            Log.d(WAKE_LOCK_TAG, "PowerManager acquired");
            wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, WAKE_LOCK_TAG);
            Log.d(WAKE_LOCK_TAG, "WakeLock set");
        }
        wakeLock.acquire();
        Log.d(WAKE_LOCK_TAG, "WakeLock acquired");
    }

    public static void stopWakeLock() {
        if (wakeLock != null) {
            wakeLock.release();
            Log.d(WAKE_LOCK_TAG, "WakeLock released");
        }
    }
}
