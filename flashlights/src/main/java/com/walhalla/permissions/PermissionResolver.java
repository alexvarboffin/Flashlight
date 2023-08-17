package com.walhalla.permissions;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.walhalla.flashlights.R;
import com.walhalla.ui.DLog;

import java.util.LinkedList;
import java.util.List;

public class PermissionResolver {

    private static final int CAMERA_PERMISSION = 1001;

    public static final String[] camera_permission = new String[]{
            android.Manifest.permission.CAMERA
    };

    private static final String KEY_PERMISSION_DIALOG = "key_permission_dlg";

    private final Activity a;

    private AlertDialog alertDialog;

    public PermissionResolver(Activity activity) {
        this.a = activity;
    }


    public boolean isResolved() {
        List<String> unmet_permissions = not_granted_permission();
        return unmet_permissions.isEmpty();
    }

    public void resolve() {
        List<String> unmet_permissions = not_granted_permission();
        if (!unmet_permissions.isEmpty()) {

            SharedPreferences aa = PreferenceManager.getDefaultSharedPreferences(a);
            boolean isSet = aa.getBoolean(KEY_PERMISSION_DIALOG, false);
            if (!isSet) {
                showHomePermissionDialog(a, (dialogInterface, i) ->
                {
                    aa.edit().putBoolean(KEY_PERMISSION_DIALOG, true).apply();
                    make(unmet_permissions);
                });
            } else {
                make(unmet_permissions);
            }
        }
//         else {
////            Intent intent = new Intent(this, ClassUsingCamera);
////            startActivity(intent);
//        }
    }

    private void make(List<String> unmet_permissions) {
        String[] arr = new String[unmet_permissions.size()];
        unmet_permissions.toArray(arr);
        ActivityCompat.requestPermissions(a, arr, CAMERA_PERMISSION);
    }

    public void showHomePermissionDialog(Activity activity, DialogInterface.OnClickListener aa) {
        alertDialog = new AlertDialog.Builder(activity)
                .setTitle(R.string.alert_home_title)
                .setMessage(R.string.alert_home_body)
                .setPositiveButton(R.string.alert_home_btn, aa)
                .create();
        alertDialog.show();
    }

    private List<String> not_granted_permission() {
        List<String> unmet_permissions = new LinkedList<String>();
        for (String perm : camera_permission) {
            if (ContextCompat.checkSelfPermission(a, perm) != PackageManager.PERMISSION_GRANTED) {
                unmet_permissions.add(perm);
            }
        }
        return unmet_permissions;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == CAMERA_PERMISSION) {
            boolean granted = true;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }
            if (granted) return;
            if (ActivityCompat.shouldShowRequestPermissionRationale(a, android.Manifest.permission.CAMERA)) {
                //Show permission explanation dialog...
                showPermissionDialog();
            } else {
                //Never ask again selected, or device policy prohibits the app from having that permission.
                //So, disable that feature, or fall back to another situation...
            }
        }
    }

    private void showPermissionDialog() {
        DLog.d("@@@");
    }

    public void destroy() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
}
