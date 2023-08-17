package com.bovada.unoprom.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.ExtendedWebView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bovada.unoprom.R;
import com.google.android.material.snackbar.Snackbar;
import com.walhalla.ui.DLog;

import org.apache.P;
import org.apache.Utils;
import org.apache.cordova.ChromeView;

import org.apache.cordova.GConfig;
import org.apache.cordova.ScreenType;
import org.apache.cordova.domen.BodyClass;
import org.apache.cordova.domen.Dataset;

import org.apache.cordova.v70.app.MyJavascriptInterface;
import org.apache.mvp.presenter.MainPresenter;
import org.apache.mvp.MainView;
import org.jetbrains.annotations.NotNull;


public abstract class MyCompatMvpActivity extends AppCompatActivity
        implements ChromeView,
        MainView,
        SwipeRefreshLayout.OnRefreshListener {


    private boolean doubleBackToExitPressedOnce;
    protected final GConfig aaa;

    //Views

    public SwipeRefreshLayout swipeRefreshLayout;
    protected ExtendedWebView __mView;
    protected RelativeLayout main;
    protected FrameLayout clazz1;
    protected ProgressBar pb;

    protected Toolbar toolbar;
    private boolean rotated0 = false;

    private MainPresenter presenter;


    public MyCompatMvpActivity() {
        aaa = GCfc();
    }

    protected abstract GConfig GCfc();

    protected ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;

    @Override
    public void hiDeRefreshLayout() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    public void wrapContentRequest() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume(getIntent());
    }

    @Override
    public void eventRequest(BodyClass body) {
        presenter.event(body);
    }

    /**
     * If true активирует Web
     */
    public void makeScreen(Dataset screen) {

        DLog.d("[screen]: " + screen.screenType + "\t" + screen.getUrl());

        if (screen.screenType != ScreenType.WEB_VIEW) {
            if (orientation404() != null && this.getRequestedOrientation() != orientation404()) {
                this.setRequestedOrientation(orientation404());
            }
        } else {
            if (orientationWeb() != null && this.getRequestedOrientation() != orientationWeb()) {
                this.setRequestedOrientation(orientationWeb());
            }
            launch(screen.getUrl());
        }
        Utils.hideKeyboard(this);
        boolean web = screen.screenType == ScreenType.WEB_VIEW;
        clazz1.setVisibility((web) ? View.VISIBLE : View.GONE);
        main.setVisibility((web) ? View.GONE : View.VISIBLE);
        //mWebView.setVisibility((web) ? View.VISIBLE : View.GONE);
        pb.setVisibility((web) && aaa.PROGRESSBAR_ENABLED ? View.VISIBLE : View.GONE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler = new Handler(Looper.getMainLooper());

        presenter = new MainPresenter(this, this, aaa, handler, makeTracker());
        //DLog.d("ON_CREATE: " + this.getClass().getSimpleName());
        setContentView(R.layout.activity_main);
        clazz1 = findViewById(R.id.browser);
        main = findViewById(R.id.main);
        pb = findViewById(R.id.progressBar1);

        if (aaa.TOOLBAR_ENABLED) {
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
//            toolbar.setSubtitle(Util.getAppVersion(this));
            toolbar.setVisibility(View.VISIBLE);
        }

        if (!rotated()) {
            presenter.init0(this);
        }
        //Generate Dynamic Gui
        onViewCreated(clazz1, this);

//        Uri targetUrl =
//                AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
//        if (targetUrl != null) {
//            Log.i(TAG, "App Link Target URL: " + targetUrl.toString());
//        }

//                if (rawUrl != null) {
//
//                    HttpPostRequest getRequest = new HttpPostRequest();
//                    try {
//                        String device_id = Util.phoneId(BaseActivity.this.getApplicationContext());
//
//                        //Replace deep to tracker
//                        URI uri = new URI(getString(R.string.app_url));
//
//                        String tracker =
//                                rawUrl.replace(getString(R.string.app_scheme), uri.getScheme())
//                                        .replace(getString(R.string.app_host), uri.getHost())
//                                        .replace("%26", "&")
//                                        .replace("%3D", "=")
//                                        + "&id=" + device_id;
//
////                        if (BuildConfig.DEBUG) {
////                            Log.i(TAG, "URL: " + rawUrl);
////                            Log.i(TAG, "TR: " + uri.getScheme() + "|" + uri.getHost());
////                            Log.i(TAG, "--->: " + tracker);
////
////                            //Only for Google Chrome test
////                            //
////
////                            Log.i(TAG, "CHROME: "
////                                    + ("intent://"
////                                    + getString(R.string.app_host)
////                                    + "/#Intent;scheme="
////                                    + getString(R.string.app_scheme) + ";package=" + getPackageName())
////                                    //        .replace(";",";")
////                                    + ";end"
////                            );
////                        }
//
//                        LocalStorage storage = LocalStorage.getInstance(BaseActivity.this);
//                        JSONObject parent = new JSONObject();
//                        try {
//                            parent.put("dl", rawUrl);
//                            parent.put("ref", storage.referer());
//                            getRequest.execute(parent).get();
//                        } catch (JSONException e) {
//                            DLog.handleException(e);
//                        }
//                    } catch (ExecutionException e) {
//                        DLog.handleException(e);
//                    } catch (InterruptedException e) {
//                        DLog.handleException(e);
//                    } catch (URISyntaxException e) {
//                        DLog.handleException(e);
//                    }
//                }

//        String device_id = Util.phoneId(MainActivity.getAppContext().getApplicationContext());
//        launch(webview_url + "?id=" + device_id);

//###        if (savedInstanceState != null) {
//###            return;
//###        }

//        if (checkUpdate()) {
//            if (toolbar != null) {
//                toolbar.post(() -> Module_U.checkUpdate(this));
//            }
//        }
    }


    @Override
    protected void onStop() {
        swipeRefreshLayout.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }


    protected void onViewCreated(ViewGroup view, Context context) {
        //mWebView = privacy.findViewById(R.id.web_view);
        //swipeRefreshLayout = privacy.findViewById(R.id.refresh);
        swipeRefreshLayout = new SwipeRefreshLayout(context);
        __mView = new ExtendedWebView(new ContextThemeWrapper(context, R.style.AppTheme));
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        swipeRefreshLayout.setLayoutParams(lp);
        __mView.setLayoutParams(lp);
        view.addView(swipeRefreshLayout);
        swipeRefreshLayout.addView(__mView);
        swipeRefreshLayout.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener =
                () -> {
                    if (__mView.getScrollY() == 0)
                        swipeRefreshLayout.setEnabled(true);
                    else
                        swipeRefreshLayout.setEnabled(false);
                });

        swipeRefreshLayout.setOnRefreshListener(this);

        //mWebView.setVisibility(View.INVISIBLE);
        //webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);

//        JavaScriptInterface jsInterface = new JavaScriptInterface(this);
//        __mView.addJavascriptInterface(jsInterface, "JSInterface");

        Utils.a123(this, __mView);
        __mView.addJavascriptInterface(new MyJavascriptInterface(MyCompatMvpActivity.this, __mView), "Client");
    }

    public void onPageStarted() {
//@@@
        if (aaa.PROGRESSBAR_ENABLED) {
            pb.setVisibility(View.VISIBLE);
        }
    }


    public void onPageFinished(WebView view, String url) {
        String title = view.getTitle();
        if (webTitle() && !TextUtils.isEmpty(title) && toolbar != null) {
            if (title.startsWith(view.getUrl())) {
                toolbar.setSubtitle(title);
            }
        }
        pb.setVisibility(View.GONE);

//        ShowOrHideWebViewInitialUse = "hide";
//        privacy.setVisibility(View.VISIBLE);

        __mView.setVisibility(View.VISIBLE);
        main = findViewById(R.id.main);
        if (main != null) {
            main.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {

//        if (fullLayout.isDrawerOpen(GravityCompat.START)) {
//            fullLayout.closeDrawer(GravityCompat.START);
//        } else {
        if (__mView.canGoBack()) {
            __mView.goBack();
        } else {


            //Log.d(TAG, "onBackPressed: " + getSupportFragmentManager().getBackStackEntryCount());


            //Pressed back => return to home screen
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeButtonEnabled(count > 0);
            }
            if (count > 0) {
                getSupportFragmentManager()
                        .popBackStack(getSupportFragmentManager()
                                        .getBackStackEntryAt(0).getId(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } else {//count == 0

//                Dialog
//                new AlertDialog.Builder(this)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .setTitle("Leaving this App?")
//                        .setMessage("Are you sure you want to close this application?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
//                            }
//
//                        })
//                        .setNegativeButton("No", null)
//                        .show();
                //super.onBackPressed();


                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    try {
                        this.finish();
                    } catch (Exception ignored) {
                    }
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, R.string.press_again_to_exit, Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 800);

            }


            /*
            //Next/Prev Navigation
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Leaving this App?")
                        .setMessage("Are you sure you want to close this application?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
            else
            {
            super.onBackPressed();
            }
            */

        }
    }

    public void launch(String url) {
        DLog.d("00000000000000000000000000000000000000000");
        __mView.post(() -> __mView.loadUrl(url));
    }



    @Override
    public void dappend(String var0) {
        DLog.d("@@@" + var0 + "\n");
    }

    protected void actionRefresh() {
        String url = __mView.getUrl();
        if (url != null) {
            __mView.reload();
            //getContent(url);
            Snackbar.make(getWindow().getDecorView(), /*url*/"Data updated.", Snackbar.LENGTH_SHORT)
                    .setAction(android.R.string.ok, null).show();
        }
    }

    @Override
    public void onRefresh() {
        __mView.reload();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public boolean rotated() {
        return rotated0;
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        outState.putBoolean(P.KEY_ROTATED, rotated0);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        rotated0 = savedInstanceState.getBoolean(P.KEY_ROTATED, false);
    }

}