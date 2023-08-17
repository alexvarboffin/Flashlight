package com.bovada.flashlights.lib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.bovada.unoprom.R;
import com.walhalla.ui.DLog;
import com.walhalla.ui.Module_U;

public class Navigation {

    private final AppCompatActivity activity;

    public Navigation(AppCompatActivity activity) {
        this.activity = activity;
    }


    public void replaceFragment(Fragment fragment) {
        try {
            //Clear back stack
            //final int count = getSupportFragmentManager().getBackStackEntryCount();
            FragmentManager fm = activity.getSupportFragmentManager();
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // update the main content by replacing fragments
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.container, fragment, fragment.getClass().getSimpleName());
//                    fragmentTransaction.commitAllowingStateLoss();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.container, fragment);
//                    fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } catch (Exception e) {
            //java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        }
    }

//    public void replaceFragment(Fragment fragment) {
//        FragmentManager fm = activity.getSupportFragmentManager();
//        try {
//            String fragmentTag = fragment.getClass().getName();
//            boolean fragmentPopped = fm
//                    .popBackStackImmediate(fragmentTag, 0); //popBackStackImmediate - some times crashed
//            if (!fragmentPopped && fm.findFragmentByTag(fragmentTag) == null) {
//
//                FragmentTransaction ftx = fm.beginTransaction();
//                ftx.addToBackStack(fragment.getClass().getSimpleName());
////                ftx.setCustomAnimations(R.anim.slide_in_right,
////                        R.anim.slide_out_left, R.anim.slide_in_left,
////                        R.anim.slide_out_right);
//                ftx.replace(R.id.container, fragment);
//                ftx.commit();
//            }
//        } catch (IllegalStateException e) {
//            DLog.handleException(e);
//        }
//    }

//set fragment with backstack
//    public void get(Fragment fragment) {
//            activity.getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, fragment)
//                    .commit();
//    }

    public void get(Fragment fragment) {
        try {
            activity.getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void homeScreen() {
//        //Fragment fragment = TabHolderFragment.newInstance("", "");
//        Fragment fragment = TabHolder2Fragment.newInstance("", "");
//
//        //Fragment fragment = BlockListFragment.newInstance("");
//        activity.getSupportFragmentManager()
//
//                .beginTransaction()
//                //.addToBackStack(null)
//                .add(R.id.container, fragment, fragment.getClass().getSimpleName())
//                .commit();
////        replaceFragment(fragment);
//    }
}
