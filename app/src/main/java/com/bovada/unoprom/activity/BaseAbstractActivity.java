package com.bovada.unoprom.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.os.PowerManager;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.browser.customtabs.CustomTabsService;
import androidx.fragment.app.Fragment;

import com.bovada.flashlights.lib.F_Type;
import com.bovada.flashlights.lib.FlashLight;
import com.bovada.flashlights.lib.FlashLightImpl;
import com.bovada.flashlights.lib.Navigation;
import com.bovada.flashlights.lib.RemoteRepository;
import com.bovada.unoprom.R;
import com.bovada.unoprom.fragment.FTSimpleFlashLight;
import com.walhalla.ui.DLog;

import org.apache.cordova.GConfig;
import org.apache.cordova.ScreenType;
import org.apache.cordova.domen.Dataset;
import org.apache.cordova.fragment.WebFragment;
import org.apache.cordova.repository.AbstractDatasetRepository;
import org.apache.mvp.MainView;

import java.util.ArrayList;
import java.util.List;

import org.apache.cordova.enumer.UrlSaver;

public abstract class BaseAbstractActivity
        extends MyCompatMvpActivity
        implements MainView, org.apache.cordova.fragment.WebFragment.Lecallback {


    private static final int PURCHASE_REQUEST_CODE = 1001;
    //private CustomTabsIntent customTabsIntent;

    protected Navigation navigation;
    protected FlashLight flashLight;

    private static final boolean unlocked = true; //Unlocked Offer www btn
    private AlertDialog dialog;

//    public void trackLevel(View ignored) {
//        MyTracker.trackLevelEvent();
//        Toast.makeText(this, "Tracking level", Toast.LENGTH_SHORT).show();
//    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleDepplink(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (PURCHASE_REQUEST_CODE == requestCode) {
            //MyTracker.onActivityResult(resultCode, data);
        }
    }


    @SuppressLint({"WakelockTimeout", "MissingPermission"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setSupportActionBar(findViewById(R.id.toolbar));

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view ->
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).
//                        setAction("Action", null).
//                        show());

        if (findViewById(R.id.container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            //addFragment(R.id.container, CategoryListFragment.newInstance());
        }
//        loan = new ViewModelProvider(this).get(LoanViewModel.class);
//        loan.getLoan().observe(this, new Observer<Loan>() {
//            @Override
//            public void onChanged(Loan s) {
//                DLog.d("###-->" + loan.toString());
//            }
//        });

//        loadUrl(this.launchUrl);


        // Obtain the FirebaseAnalytics instance.
        //FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        navigation = new Navigation(this);
        flashLight = new FlashLightImpl(this);
        DLog.d("@@ AVAILABLE-> @@" + flashLight.isFlashAvailable());

        //Home Screen
        //addFragment(R.id.container, CategoryListFragment.newInstance(0, null));
        navigation.get(FTSimpleFlashLight.newInstance(F_Type.TYPE1));
        //addFragment(R.id.container, SimpleListFragment.newInstance());

//        throw new RuntimeException("wtf");
//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//        filter.addAction(MyFirebaseJobService.ACTION_SHOW_MESSAGE);
//        registerReceiver(br, filter);

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "myapp:htracker");
        wakeLock.acquire();

        //MyTracker.getTrackerConfig().setTrackingEnvironmentEnabled(true);
        handleDepplink(getIntent());
        //customTabsIntent = customWeb();
    }


    public void homeScreen() {
        navigation.replaceFragment(FTSimpleFlashLight.newInstance(F_Type.TYPE1));
    }

    //Fabric.with(this, new Crashlytics()); //crashReport
    //setContentView(R.layout.activity_main);


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        switch (item.getItemId()) {
//            case R.id.action_refresh:
//
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    private JobBroadcastReceiver br = new JobBroadcastReceiver();
//
//    private class JobBroadcastReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            StringBuilder sb = new StringBuilder();
//            sb.append("Action: " + intent.getAction() + "\n");
//            sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
//            String log = sb.toString();
//            Log.d(TAG, log);
//            Toast.makeText(context, log, Toast.LENGTH_LONG).show();
//
//        }
//    }


    @Override
    public Dataset data() {
//        Dataset dataset = new Dataset();
//        dataset.screenType = ScreenType.WEB_VIEW;
//        dataset.url = "https://google.com";
//        return dataset;// Payload
        return null;
    }

    /**
     * активирует Web
     */
    @Override
    public void makeScreen(Dataset screen) {

//        if (1 == 1) {
//            replaceFragment(WebFragment.newInstance(
//                    "https://kzmoney.web.app/cookies.html", aaa));
//            return;
//        }
//        screen.screenType = ScreenType.WEB_VIEW;
//        screen.url = "https://google.com";
        DLog.d("dsds" + screen.toString());

        if (screen.screenType == ScreenType.WEB_VIEW) {
            //Toast.makeText(this, "" + screen, Toast.LENGTH_LONG).show();
            clazz1.setVisibility(View.GONE);
            main.setVisibility(View.VISIBLE);
            //mWebView.setVisibility((web) ? View.VISIBLE : View.GONE);
            pb.setVisibility(View.GONE);
            externalSetter(screen);
            if (enableHomeWebView()) {
                replaceFragment(WebFragment.newInstance(screen.url, aaa));
            }

        } else {
            super.makeScreen(screen);
            externalSetter(screen);
        }
    }


    protected void externalSetter(Dataset screen) {
        //unlocked = true; //screen.isEnabled(); NOT USE
        webview_external = screen.webview_external;
        DLog.d("@EX@" + webview_external);
    }

    protected abstract boolean enableHomeWebView();


    @Override
    public Integer orientation404() {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    @Override
    public Integer orientationWeb() {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }


    @Override
    public boolean webTitle() {
        return false;
    }

    @Override
    public boolean handleDeepLink() {
        return false;
    }

    @Override
    public void PEREHOD_S_DEEPLINKOM() {

    }

    @Override
    public AbstractDatasetRepository makeTracker() {
        Handler handler = new Handler();
        return new RemoteRepository(handler, this);
    }

    @Override
    protected GConfig GCfc() {
        return new GConfig(
                true,
                true,
                UrlSaver.OH_NONE,
                false, false
        );
    }


//    @Override
//    public void m404(int errorCode) {
//    }


    @Override
    public void mAcceptPressed(String url) {

    }


    public boolean isWebViewExternal() {
        DLog.d("@@@@@@@@" + webview_external);
        return webview_external;
    }

    protected static boolean webview_external;

    @Override
    protected void onResume() {
        super.onResume();

        //AboutBox.privacy_dialog_request(this);
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String muted = sharedPreferences.getString(KEY_MUTED, null);
//        if (muted == null) {
//            if (BuildConfig.DEBUG) {
//                //Toast.makeText(this, "Application first launch", Toast.LENGTH_SHORT).show();
//            }
//            //DLog.d(new GsonBuilder().create().toJson(new SystemEnvironment()));
//            Thread t = new Thread(() -> {
//                try {
//                    String am = new WebView(this).getSettings().getUserAgentString();
//                    SystemEnvironment me = new SystemEnvironment(am);
//                    DatabaseReference reference = FirebaseDatabase.getInstance()
//                            .getReference("users").child(me.GUID);
//                    reference.setValue(me);
//
//                } catch (Exception e) {
//                    DLog.handleException(e);
//                }
//            });
//            t.start();
//        }
    }


//    private void privacy_dialog_request(Context context) {
////        dialog = new AlertDialog.Builder(context)
////                .setTitle(R.string.app_name)
////                .setMessage(spannable)
////                .setIcon(R.mipmap.ic_launcher)
////                .setCancelable(false)
////                .setNegativeButton("No",
////                        (dialog, id) -> {
////                            dialog.cancel();
////                            finish();
////                        })
////                .setPositiveButton(R.string.action_continue,
////                        (dialog, id) -> dialog.cancel())
////                .create();
////
////        TextView aa = ((TextView) dialog.findViewById(android.R.id.message));
////        if (aa != null) {
////            aa.setMovementMethod(LinkMovementMethod.getInstance());
////            aa.setHighlightColor(Color.BLACK);// background color when pressed
////            aa.setLinkTextColor(Color.RED);
////        }
////        if (dialog != null && !dialog.isShowing()) {
////            dialog.show();
////        }
//    }


    public void replaceFragment(Fragment fragment) {
        navigation.replaceFragment(fragment);
    }

    @Override
    public void setActionBarTitle(String title) {

    }


    public boolean isUnlocked() {
        DLog.d("hidden functionality unlocked? -->" + unlocked + " " + webview_external);
        return unlocked;
    }


    private void handleDepplink(Intent intent) {
        //Toast.makeText(this, "Handling deeplink", Toast.LENGTH_SHORT).show();
//        String deeplink = MyTracker.handleDeeplink(intent);
//        if (deeplink != null && !deeplink.isEmpty()) {
//            return;
//        }
        //Toast.makeText(this, "Deeplink tracked: " + deeplink, Toast.LENGTH_SHORT).show();
    }

//    public void trackLogin(View ignored) {
//        // you can add custom params you want to track to all events
//        // can be omitted or null
//        Map<String, String> eventCustomParams = new HashMap<>();
//        eventCustomParams.put("someParamKey", "someParamValue");
//
//        MyTracker.trackLoginEvent("custom_user_id",
//                "vk_connect_id", eventCustomParams);
//        Toast.makeText(this, "Tracking login", Toast.LENGTH_SHORT).show();
//    }
//
//    public void trackInvite(View ignored) {
//        MyTracker.trackInviteEvent();
//        Toast.makeText(this, "Tracking invite", Toast.LENGTH_SHORT).show();
//    }

//    public void trackRegistration(View ignored) {
//        MyTracker.trackRegistrationEvent("custom_user_id", "vk_connect_id");
//        Toast.makeText(this, "Tracking registration", Toast.LENGTH_SHORT).show();
//    }


    public void trackPurchase(View ignored) {
        // Create buy bundle
        // Bundle buyIntentBundle = callMethodToReceiveBundle();
        // Extracting pending intent from bundle
        // PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
        // Starting intent sender
        // startIntentSenderForResult(pendingIntent.getIntentSender(),
        //							  PURCHASE_REQUEST_CODE,
        //							  new Intent(),
        //							  0,
        //							  0,
        //							  0);
    }

    //"https://kzmoney.web.app/cookies.html"

//    @Override
//    public void openOfferRequest(Category ka) {
//        openBrowser(ka);
//    }

//    public void openBrowser(Category category) {
//        if (isWebViewExternal()) {
//            String url = category.url;
//            if (!url.startsWith("http://") && !url.startsWith("https://")) {
//                url = "http://" + url;
//            }
//            ArrayList<ResolveInfo> list = getCustomTabsPackages(this);
//            for (ResolveInfo info : list) {
//                //24 ResolveInfo{5f59c04 com.android.chrome/com.google.android.apps.chrome.Main m=0x208000}
//                //   ResolveInfo{a8a75dc com.android.chrome/com.google.android.apps.chrome.IntentDispatcher m=0x208000}
//                //DLog.d("@" + info.toString());
//            }
//            if (list.size() > 0) {
//                customTabsIntent.launchUrl(this, Uri.parse(url));
//            } else {
//                Helpers.openExternalBrowser(this, category);
//            }
//        } else {
//            replaceFragment(WebFragment.newInstance(category.url, aaa));
//        }
//    }


//    private CustomTabsIntent customWeb() {
//        //Configure the color of the address bar
//        CustomTabColorSchemeParams defaultColors = new CustomTabColorSchemeParams.Builder()
//                .setToolbarColor(getResources().getColor(R.color.colorPrimaryDark))
//                //.setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark))
//                .setNavigationBarDividerColor(Color.BLACK)
//                .setSecondaryToolbarColor(Color.BLACK)
//                .build();
//
//        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//        builder.setDefaultColorSchemeParams(defaultColors);
//
//        //Configure a custom action button
//        //24dp
//        //@PandingIntent pandingIntent = new PendingI
//        //@builder.setActionButton(icon, description, pendingIntent, tint);
//
//        //Configure a custom menu
//        //builder.addMenuItem(menuItemTitle, menuItemPendingIntent);
//
//        Intent intent = new Intent(this, CustomTabsBroadcastReceiver.class);
//        intent.setAction(ACTION_COPY_URL);
//        String label = "Copy link";
//
//
//        //Android 12
//        final int flag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE : PendingIntent.FLAG_UPDATE_CURRENT;
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, flag);
//
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////            //PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
////            pendingIntent = PendingIntent.getBroadcast(
////                    this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
////        } else {
////            //PendingIntent.FLAG_UPDATE_CURRENT
////            pendingIntent = PendingIntent.getBroadcast(
////                    this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
////        }
//        builder.addMenuItem(label, pendingIntent)
//                .build();
////        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
////        Bundle bundle = new Bundle();
////        bundle.putString("goz_ad_click", "1");
////        mFirebaseAnalytics.logEvent("in_offer_open_click", bundle);
//        return builder.build();
//    }

    public static ArrayList<ResolveInfo> getCustomTabsPackages(Context context) {
        PackageManager pm = context.getPackageManager();
        // Get default VIEW intent handler.
        Intent activityIntent = new Intent()
                .setAction(Intent.ACTION_VIEW)
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .setData(Uri.fromParts("http", "", null));

        // Get all apps that can handle VIEW intents.
        List<ResolveInfo> resolvedActivityList = pm.queryIntentActivities(activityIntent, 0);
        ArrayList<ResolveInfo> packagesSupportingCustomTabs = new ArrayList<>();
        for (ResolveInfo info : resolvedActivityList) {
            Intent serviceIntent = new Intent();
            serviceIntent.setAction(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION);
            serviceIntent.setPackage(info.activityInfo.packageName);
            // Check if this package also resolves the Custom Tabs service.
            if (pm.resolveService(serviceIntent, 0) != null) {
                packagesSupportingCustomTabs.add(info);
            }
        }
        return packagesSupportingCustomTabs;
    }


    public void showLoading() {
    }


    public void hideLoading() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

}
