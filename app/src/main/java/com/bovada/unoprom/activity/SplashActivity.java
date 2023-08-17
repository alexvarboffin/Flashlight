package com.bovada.unoprom.activity;

import java.lang.ref.WeakReference;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bovada.unoprom.R;
import com.bovada.unoprom.activity.HomeActivity;
import com.walhalla.ui.DLog;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "@@@";
    private static final String KEY_DISM_COUNT = "key_dism";

    private PulsatorLayout pulsator;

    private View viewProgress;
    /**
     * Number of seconds to count down before showing the app open ad. This simulates the time needed
     * to load the app.
     */


    private long secondsRemaining;
    private int COUNTER_TIME;

    public static class Loading extends AsyncTask<Void, Void, Void> {
        private final WeakReference<Activity> mWeakReference;


        public Loading(Activity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(1100);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Activity activity = mWeakReference.get();
            if (activity != null) {
                activity.startActivity(new Intent(activity.getApplicationContext(), HomeActivity.class));
                activity.overridePendingTransition(R.anim.open_next, R.anim.close_main);
            }

        }
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Thread.setDefaultUncaughtExceptionHandler(CustomExceptionHandler.getInstance(getApplicationContext()));
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.pulsator = findViewById(R.id.pulsator);

        final TextView textView = findViewById(R.id.text_ver);
        textView.setText(""); //DLog.getAppVersion(this)

        COUNTER_TIME = 2;
        //show
        //skip
        //skip


//        viewProgress = findViewById(R.id.view_progress);
//        int viewWidth = viewProgress.getWidth();
//
//        TranslateAnimation move = new TranslateAnimation(-(getScreenWidth() / 2) + viewWidth / 2, (getScreenWidth() / 2) + viewWidth / 2 + viewWidth, 0, 0);
//        move.setDuration(1000);
//        TranslateAnimation move1 = new TranslateAnimation(-viewWidth, 0, 0, 0);
//        move1.setDuration(500);
//        ScaleAnimation laftOut = new ScaleAnimation(0, 1, 1, 1);
//        laftOut.setDuration(500);
//
//        AnimationSet animationSet = new AnimationSet(true);
//        animationSet.addAnimation(move);
//        animationSet.addAnimation(move1);
//        animationSet.addAnimation(laftOut);
//        animationSet.addAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideout));
//        startAnimation(animationSet);

        //@@@ Loading task = new Loading(this);
        //@@@ if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        //@@@     task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //@@@ else
        //@@@     task.execute();

        // Create a timer so the SplashActivity will be displayed for a fixed amount of time.
        createTimer(COUNTER_TIME);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (COUNTER_TIME > 1) {
            this.pulsator.setCount(3);
            this.pulsator.setDuration(2_200);//single pulse duration
            this.pulsator.start();
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (Build.VERSION.SDK_INT >= 19 && hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(5126);
        }
    }

    private void startAnimation(AnimationSet animationSet) {
        viewProgress.startAnimation(animationSet);
        new android.os.Handler().postDelayed(() -> startAnimation(animationSet), 10);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }


    /**
     * Create the countdown timer, which counts down to zero and show the app open ad.
     *
     * @param seconds the number of seconds that the timer counts down from
     */
    private void createTimer(long seconds) {
        //final TextView counterTextView = findViewById(R.id.timer);

        CountDownTimer countDownTimer =
                new CountDownTimer(seconds * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        secondsRemaining = ((millisUntilFinished / 1000) + 1);
                        //counterTextView.setText("" + secondsRemaining);
                    }

                    @Override
                    public void onFinish() {
                        secondsRemaining = 0;
                        //counterTextView.setText("Done.");
                        startMainActivity();
                    }
                };
        countDownTimer.start();
    }


    /**
     * Start the MainActivity.
     */
    private void startMainActivity() {
        this.startActivity(new Intent(this.getApplicationContext(), HomeActivity.class));
        this.overridePendingTransition(R.anim.open_next, R.anim.close_main);
    }
}