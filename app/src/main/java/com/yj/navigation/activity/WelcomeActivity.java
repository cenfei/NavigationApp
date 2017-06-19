package com.yj.navigation.activity;

import android.os.Bundle;

import com.umeng.analytics.MobclickAgent;
import com.yj.navigation.R;
import com.yj.navigation.base.BaseActivity;
import com.yj.navigation.util.FoxHandler;
import com.yj.navigation.util.ImageLoaderUtil;

import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;



/**
 * Created by zhang on 2015/8/11.
 */
@EActivity(R.layout.activity_welcome)
public class WelcomeActivity extends BaseActivity {

//    private AdvertiseInfo mAdvertiseInfo = null;
//    private String adLogo;

    private boolean isStartActivity = false;

    @Pref
    ConfigPref_ configPref;


//
//    @Pref
//    UserPref_ userPref;

    @AfterViews
    void init() {
        ImageLoaderUtil.initAdLoader(getApplicationContext());
//        getLocation();
        new FoxHandler(this).postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity();
            }
        }, 2000);

//        String channel = "";
//        try {
//            channel = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA).metaData.getString("UMENG_CHANNEL");
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        Log.w("welcomeactivity", "channel=" + channel);
    }

//    private void getLocation() {
//        ProtocolUtil.getAdvertiseDataCity(WelcomeActivity.this, new AdvertiseMsgHandler(), userPref.accessToken().get(), "startup",
//                "", "");
//    }

    private synchronized void startActivity() {
        if (isStartActivity) return;
        isStartActivity = true;
        if (configPref.showWelcome4().get()) {
            configPref.showWelcome4().put(false);
//            Util.startActivityNewTask(this, LoadingActivity_.class);
            Util.startActivityNewTask(this, RegActivity_.class);//服务器判断是否有账户

        } else {

//            Util.startActivityNewTask(this, RegActivity_.class);//服务器判断是否有账户

            if (configPref.userToken().get()==null||configPref.userToken().get().equals("")) {

                Util.startActivityNewTask(this, RegActivity_.class);//服务器判断是否有账户
            }else{
                Util.startActivityNewTask(this, IndexActivity_.class);//服务器判断是否有账户

            }



//            Util.startActivityNewTask(this, UserMainActivity_.class);
//            if (mAdvertiseInfo != null) {
//                AdvertiseActivity_.intent(this).advertiseInfo(mAdvertiseInfo).start();
//                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
//            } else
//                Util.startActivityNewTask(this, UserMainActivity_.class);
        }

//        it.putExtra("mobileNo", "18300000091");	//手机号码
//        it.putExtra("channel", "WUxwzI");	//渠道短链接
//        startActivity(it);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobclickAgent.openActivityDurationTrack(false);
        //******************友盟测试*****************************
//        MobclickAgent.setDebugMode(true);
//        Log.i("UMENG_INFO", "device=" + getDeviceInfo(this));
        //******************友盟测试*****************************
        try {
            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) getSystemService(WIFI_SERVICE);

//            WxUtil.wxMac = wifi.getConnectionInfo().getMacAddress().replace(":", "");
        } catch (Exception e) {
        }
//        PushManager.getInstance().initialize(this.getApplicationContext());
//        Intent intent = getIntent();
//        TencentUtils.QQ_OPENID = intent.getStringExtra("openid");
//        TencentUtils.QQ_TOKEN = intent.getStringExtra("accesstoken");
    }

    @Override
    protected void initActivityName() {
        activityName = WelcomeActivity.class.getName();
    }

    @Override
    protected void setActivityBg() {

    }

//    public static String getDeviceInfo(Context context) {
//        try {
//            org.json.JSONObject json = new org.json.JSONObject();
//            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
//                    .getSystemService(Context.TELEPHONY_SERVICE);
//
//            String device_id = tm.getDeviceId();
//
//            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//
//            String mac = wifi.getConnectionInfo().getMacAddress();
//            json.put("mac", mac);
//
//            if (TextUtils.isEmpty(device_id)) {
//                device_id = mac;
//            }
//
//            if (TextUtils.isEmpty(device_id)) {
//                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//            }
//
//            json.put("device_id", device_id);
//
//            return json.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private class AdvertiseMsgHandler extends RowMessageHandler {
//        @Override
//        protected void handleResp(String resp) {
//            handleAdvertiseResp(resp);
//        }
//    }
//
//    public void handleAdvertiseResp(String resp) {
//        if (resp != null && resp.length() > 0) {
//            final AdvertiseInfo adInfo = new Gson().fromJson(resp, AdvertiseInfo.class);
//            if (adInfo != null) {
//                if (adInfo.images != null && adInfo.images.size() > 0) {
//                    Advertise ad = adInfo.images.get(0);
//
////                    Advertise ad1 = adInfo.images.get(1);
////                    if (ad1 != null && ad1.imgUrl != null && !ad1.imgUrl.isEmpty()) {
////                        adLogo = ad1.imgUrl;
////                    }
//
//                    if (ad != null && ad.imgUrl != null && !ad.imgUrl.isEmpty()) {
//                        ImageLoader.getInstance().loadImage(ad.imgUrl, new SimpleImageLoadingListener() {
//                            @Override
//                            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                                startActivity();
//                            }
//
//                            @Override
//                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                                mAdvertiseInfo = adInfo;
//                                AdvertiseActivity.preloadedAdbmp = loadedImage;
//                                startActivity();
//                            }
//                        });
//                    }
//
//                }
//            }
//        }
//    }
}
