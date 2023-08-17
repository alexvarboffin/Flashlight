package com.bovada.unoprom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import com.walhalla.ui.DLog;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Objects;

public class S {

    //private static final String CERTIFICATE_SHA1 = "A4EC1CD6B4594590FC90546D95C365295BE89E8A";

    private static char[] calcSHA1(byte[] signature) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA1");
        digest.update(signature);
        byte[] signatureHash = digest.digest();
        return bytesToHex(signatureHash);
    }

//    private static String calcSHA1(byte[] signature) throws NoSuchAlgorithmException {
//        MessageDigest digest = MessageDigest.getInstance("SHA1");
//        digest.update(signature);
//        byte[] signatureHash = digest.digest();
//        return bytesToHex(signatureHash);
//    }

//    public static String bytesToHex(byte[] bytes) {
//        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
//        char[] hexChars = new char[bytes.length * 2];
//        int v;
//        for (int j = 0; j < bytes.length; j++) {
//            v = bytes[j] & 0xFF;
//            hexChars[j * 2] = hexArray[v >>> 4];
//            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
//        }
//        return new String(hexChars);
//    }

    public static char[] bytesToHex(byte[] bytes) {
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return hexChars;
    }

    @SuppressLint("PackageManagerGetSignatures")
    public static boolean b(Context context) {
        try {
            PackageManager pm = context.getApplicationContext().getPackageManager();
            String name = context.getApplicationContext().getPackageName();
            Signature[] sa = pm.getPackageInfo(name, PackageManager.GET_SIGNATURES).signatures;
            //Log.w(Constants.TAG, "Signature[] :: " + Arrays.toString(sa));
            for (Signature signature : sa) {
                //Log.w(Constants.TAG, "byte[] :: " + Arrays.toString(bs));
                byte[] bs = signature.toByteArray();
                char[] currentSignature = calcSHA1(bs);
                DLog.d("@@ result string :: " + Arrays.toString(currentSignature));

                //Log.w(Constants.TAG, "new byte[] :: " + Arrays.toString(bs));
                bs = CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(bs)).getEncoded();
                //Log.w(Constants.TAG, "new byte[] encoded :: " + Arrays.toString(bs));
                String s = new String(Base64.encode(MessageDigest.getInstance("MD5").digest(bs), 19));
                DLog.d("@@ result string :: " + s + " " + Arrays.toString(MessageDigest.getInstance("MD5").digest(bs)) + " " + Arrays.toString(bs));
                return Objects.equals(s,  "cJ7equSy4Tf2B+h7NlS2ag");
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
