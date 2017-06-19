package com.yj.navigation.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.WebView;

import com.yj.navigation.object.JobImageJson;
import com.yj.navigation.object.JobJson;
import com.yj.navigation.object.JobListJson;
import com.yj.navigation.util.ImageLoaderUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by xiaoganghu on 15/6/26.
 */
public class MainApp extends Application {

    private static String TAG = "MainApp";
    public static WebView webView;
    public static boolean WX_SHOP = false;

    public static String adUrl = "";

    public Map<String, String> parmaMap;
    public    List<JobImageJson> jobImageJsonList;
    public    String  remoteBaseUrl;
public JobJson jobJson;
    public Map<String, String> getAppInfo() {

        if (parmaMap == null) {
            parmaMap = new HashMap<String, String>();
            String appname = getApplicationInfo().loadLabel(getPackageManager()).toString();
            parmaMap.put("appName", appname);

            parmaMap.put("platform", "app");
            String  versionName = null;
            try {
                versionName = getPackageManager().getPackageInfo(getPackageName(), 1).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            parmaMap.put("version", versionName);
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = tm.getDeviceId();
            parmaMap.put("deviceNo", deviceId);
        } else {
            if (parmaMap.get("appName") == null) {
                String appname = getApplicationInfo().loadLabel(getPackageManager()).toString();
                parmaMap.put("appName", appname);
            }
            if (parmaMap.get("platform") == null) {
                parmaMap.put("platform", "app");
            }
            if (parmaMap.get("version") == null) {
                String  versionName = null;
                try {
                    versionName = getPackageManager().getPackageInfo(getPackageName(), 1).versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                parmaMap.put("version", versionName);
            }
            if (parmaMap.get("deviceNo") == null) {
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String deviceId = tm.getDeviceId();
                parmaMap.put("deviceNo", deviceId);
            }


        }

        return parmaMap;

    }


    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "MainApp start");
        // strictModeBuild();
        initImageLoader(getApplicationContext());
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
//        QbSdk.allowThirdPartyAppDownload(true);
//        QbSdk.initX5Environment(this, QbSdk.WebviewInitType.FIRSTUSE_AND_PRELOAD, null);
    }


    public void strictModeBuild() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        builder.detectAll();
        builder.penaltyLog();
        StrictMode.VmPolicy vmp = builder.build();
        StrictMode.setVmPolicy(vmp);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();

        Log.d(TAG, "MainApp terminate");
    }

    public static void initImageLoader(Context context) {
//        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));

        ImageLoaderUtil.initAdLoader(context);

    }
}
