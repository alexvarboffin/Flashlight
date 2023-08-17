package com.bovada.unoprom.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.ViewModel;

import com.bovada.flashlights.lib.GameMainView;
import com.walhalla.permissions.PermissionResolver;

import com.bovada.unoprom.R;
import com.bovada.unoprom.S;
import com.google.android.material.tabs.TabLayout;
import com.walhalla.ui.Module_U;

import org.apache.cordova.ScreenType;
import org.apache.cordova.domen.Dataset;
import org.apache.cordova.fragment.BaseFragment;
import org.apache.mvp.presenter.DeviceCheck;

public class HomeActivity
        extends BaseAbstractActivity
        implements BaseFragment.Callback
        , GameMainView
{

    @Override
    protected boolean enableHomeWebView() {
        return true;
    }


    final int ERR_INTERNET_DISCONNECTED = -2;



    private PermissionResolver resolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolver = new PermissionResolver(this);


        S.b(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //getSupportActionBar().setSubtitle("#$Demo Version");
    }

    @Override
    public boolean isResolved() {
        return resolver.isResolved();
    }

    @Override
    protected void onDestroy() {
        resolver.destroy();
        super.onDestroy();
    }

    @Override
    public void resolveCamera() {
        resolver.resolve();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        resolver.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Nullable
    @Override
    public TabLayout getTabs() {
        return null;
    }

    @Override
    public boolean showRefresh() {
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        Menu rr = menu;
//        rr.add("Demo 1")
//                .setIcon(R.drawable.ic_android_black_24dp).setOnMenuItemClickListener(v -> {
//                    startActivity(new Intent(this, FT_Test_Activity.class));
//                    return true;
//                });
//        rr.add("Demo 2").setOnMenuItemClickListener(v -> {
//            navigation.get(FT_Simple_FlashLight.newInstance(F_Type.TYPE2));
//            return true;
//        }).setIcon(R.drawable.ic_android_black_24dp);
//        rr.add("Demo 3").setOnMenuItemClickListener(v -> {
//            navigation.get(FT_Simple_FlashLight.newInstance(F_Type.TYPE3));
//            return true;
//        }).setIcon(R.drawable.ic_android_black_24dp);

//        rr.add("Crash").setOnMenuItemClickListener(v -> {
//            throw new RuntimeException("Test Crash"); // Force a crash
//        }).setIcon(R.drawable.ic_android_black_24dp);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }

        if (itemId == R.id.action_about) {
            Module_U.aboutDialog(this);
            return true;
        } else if (itemId == R.id.action_privacy_policy) {
            Module_U.openBrowser(this, getString(R.string.url_privacy_policy));
            return true;
//            case R.id.action_rate_app:
//                Module_U.rateUs(this);
//                return true;
        } else if (itemId == R.id.action_share_app) {
            Module_U.shareThisApp(this);
            return true;
        }
//        else if (itemId == R.id.action_privacy_policy_credit) {
//            //AboutBox.showPolicy(this);
//            return true;
//        }
//        else if (itemId == R.id.action_discover_more_app) {
//            Module_U.moreApp(activity);
//            return true;
//
////            case R.id.action_exit:
////                this.finish();
////                return true;
//        }
//        else if (itemId == R.id.action_feedback) {
//            Module_U.feedback(activity);
//            return true;
//        }
//        else if (itemId == R.id.action_terms) {
//            startActivity(new Intent(this, TermsActivity.class));
//            return true;
//
////            case R.id.action_more_app_01:
////                Module_U.moreApp(this, "com.walhalla.ttloader");
////                return true;
//
////            case R.id.action_more_app_02:
////                Module_U.moreApp(this, "com.walhalla.vibro");
////                return true;
//        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void dappend(String s) {

    }

    @Override
    public void makeScreen(Dataset screen) {

    }

    @Override
    public boolean rotated() {
        return false;
    }

    @Override
    public void hiDeRefreshLayout() {

    }

    @Override
    public DeviceCheck checkDevice() {
        return null;
    }

    @Override
    public void webClientError(int errorCode, String description, String failingUrl) {
        if (toolbar != null) {
            toolbar.setSubtitle("");
        }
        if (errorCode == WebViewClient.ERROR_TIMEOUT
                || ERR_INTERNET_DISCONNECTED == errorCode) {
            makeScreen(new Dataset(ScreenType.GAME_VIEW, null));
        }
    }

    @Override
    public void onClick(ViewModel navItem) {

    }

    @Override
    public void showToolbar(boolean visible) {

    }
}



