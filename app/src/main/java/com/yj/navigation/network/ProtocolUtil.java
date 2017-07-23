package com.yj.navigation.network;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yj.navigation.base.MainApp;
import com.yj.navigation.util.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xiaoganghu on 15/6/29.
 */
public class ProtocolUtil {
    public static final int VERIFY_CODE_TYPE_REGISTER = 1;
    public static final int VERIFY_CODE_TYPE_BIND_PHONE = 2;
    public static final int VERIFY_CODE_TYPE_ALTER_PASSWD = 3;
    public static final int VERIFY_CODE_TYPE_FORGOT_PASSWD = 4;

    public static boolean isSuccess(JSONObject resp) {
        try {
            if (resp.has(Constant.FIELD_RTN)) {
                return resp.getString(Constant.FIELD_RTN).equals(Constant.RES_SUCCESS);
            } else if (resp.has(Constant.FIELD_RTN2)) {
                return resp.getString(Constant.FIELD_RTN2).equals(Constant.RES_SUCCESS2);
            }
            return false;
        } catch (JSONException e) {
            return false;
        }
    }


    public static void getPhoneMsg(Context context,
                                   Protocol.CallBack callBack,
                                   String mobile
    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("mobile", mobile);

        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));

        new Protocol(context, API.GET_MOBILE_MSG_URL, parmaMap, callBack);
    }


    public static void registerFunction(Context context,
                                     Protocol.CallBack callBack,
                                     String mobile, String verCode
    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("mobile", mobile);

        parmaMap.put("vcode", verCode);



//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.REGISTER_URL, parmaMap, callBack);
    }



    public static void registerCompleteFunction(Context context,
                                        Protocol.CallBack callBack,
                                        String mobile, String key,String password,String nickname,String carno

    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("mobile", mobile);

        parmaMap.put("password", password);
        parmaMap.put("key", key);
        parmaMap.put("nickname", nickname);
        if(carno!=null&&!carno.equals(""))
        parmaMap.put("carno", carno);



//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.REGISTER_COMPLETE_URL, parmaMap, callBack);
    }




    public static void loginByPwdFunction(Context context,
                                                Protocol.CallBack callBack,
                                                String username, String pwd


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("userName", username);

        parmaMap.put("password", pwd);




//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.LOGIN_BY_PWD_URL, parmaMap, callBack);
    }

    public static void loginOutFunction(Context context,
                                          Protocol.CallBack callBack,
                                          String token


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);





//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.LOGIN_OUT_URL, parmaMap, callBack);
    }



    public static void forgetpwdGetCodeFunction(Context context,
                                                  Protocol.CallBack callBack,
                                                  String mobile


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("mobile", mobile);





//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.FORGET_GETCODE_URL, parmaMap, callBack);
    }


    public static void forgetUpdateFunction(Context context,
                                                Protocol.CallBack callBack,
                                                String mobile,String code


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("mobile", mobile);
        parmaMap.put("vcode", code);





//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.FORGET_UPDATE_URL, parmaMap, callBack);
    }

    public static void forgetNowUpdateFunction(Context context,
                                            Protocol.CallBack callBack,
                                            String token,String nowpwd,String pwd


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        parmaMap.put("oldPassword", nowpwd);

        parmaMap.put("newPassword", pwd);




//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.FORGET_UPDATE_URL, parmaMap, callBack);
    }


    public static void loginpwdUpdateFunction(Context context,
                                               Protocol.CallBack callBack,
                                               String token,String nowpwd,String pwd


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        parmaMap.put("oldPassword", nowpwd);

        parmaMap.put("newPassword", pwd);




//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.LOGINPWD_UPDATE_URL, parmaMap, callBack);
    }



    public static void updateSafePwdFunction(Context context,
                                               Protocol.CallBack callBack,
                                               String token,String securitypassword,String bizCode


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        parmaMap.put("securitypassword", securitypassword);

        parmaMap.put("bizCode", bizCode);




//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.UPDATE_PWD_URL, parmaMap, callBack);
    }

    public static void setSafePwdFunction(Context context,
                                             Protocol.CallBack callBack,
                                             String token,String securitypassword


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        parmaMap.put("securityPwd", securitypassword);





//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.SET_PWD_URL, parmaMap, callBack);
    }



    public static void resetSafePwdFunction(Context context,
                                          Protocol.CallBack callBack,
                                          String token,String key,String password


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        parmaMap.put("key", key);
        parmaMap.put("password", password);





//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.RESET_PWD_URL, parmaMap, callBack);
    }



    public static void uploadApplyFunction(Context context,
                                            Protocol.CallBack callBack,
                                          String token, String fileNum,String bizType,String fileFormat,String takeDt


   ,String latitude,String longitude ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);

        parmaMap.put("fileNum", fileNum);
        parmaMap.put("bizType", bizType);
        parmaMap.put("fileFormat", fileFormat);

        parmaMap.put("takeDt", takeDt);

        parmaMap.put("latitude", latitude);

        parmaMap.put("longitude", longitude);



//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.UPLOAD_APPLY_URL, parmaMap, callBack);
    }


    public static void resetSafePwdWithOldpwdFunction(Context context,
                                            Protocol.CallBack callBack,
                                            String token,String oldPassword,String newPassword


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        parmaMap.put("oldPassword", oldPassword);
        parmaMap.put("newPassword", newPassword);





//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.RESET_PWD_WITHOLDER_URL, parmaMap, callBack);
    }


    public static void forgetResetFunction(Context context,
                                            Protocol.CallBack callBack,
                                            String mobile,String key,String newPassword


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("mobile", mobile);
        parmaMap.put("key", key);

        parmaMap.put("newPassword", newPassword);




//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.FORGET_RESET_URL, parmaMap, callBack);
    }



    public static void queryQustionFunction(Context context,
                                           Protocol.CallBack callBack,
                                           String token


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);



//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.QUERY_QUESTION_URL, parmaMap,  callBack);


    }
    public static void updateQustionFunction(Context context,
                                            Protocol.CallBack callBack,
                                            String token,List<Map<String,String>> listmap


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);

        parmaMap.put("MAP", listmap);


//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.UPDATE_QUESTION_URL, parmaMap, callBack);
    }



    public static void checkQustionFunction(Context context,
                                             Protocol.CallBack callBack,
                                             String token,List<Map<String,String>> listmap


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);

        parmaMap.put("MAP", listmap);


//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.CHECK_QUESTION_URL, parmaMap, callBack);
    }


    /**
     *  设备绑定
     * @param context
     * @param callBack
     * @param token
     */
    public static void bindDeviceFunction(Context context,
                                            Protocol.CallBack callBack,
                                            String token,String bindMethod,String devNo,String deviceToken


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);

        parmaMap.put("bindMethod", bindMethod);
        if(devNo!=null&&!devNo.equals(""))
        parmaMap.put("devNo", devNo);
        parmaMap.put("deviceToken", deviceToken);


//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.BIND_DEVICE_URL, parmaMap, callBack);
    }


    /**
     *  设备解绑
     * @param context
     * @param callBack
     * @param token
     * @param listmap
     */
    public static void unbindDeviceFunction(Context context,
                                          Protocol.CallBack callBack,
                                          String token,Integer bindId


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);

        parmaMap.put("bindId", bindId);



//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.UNBIND_DEVICE_URL, parmaMap, callBack);
    }

    /**
     *  设备列表
     * @param context
     * @param callBack
     * @param token
     * @param listmap
     */
    public static void deviceListFunction(Context context,
                                            Protocol.CallBack callBack,
                                            String token


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);




//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.DEVICE_LIST_URL, parmaMap, callBack);
    }






    /**
     *  绑定银行卡
     * @param context
     * @param callBack
     * @param token
     * @param listmap
     */
    public static void bindCardFunction(Context context,
                                          Protocol.CallBack callBack,
                                          String token,String bankcode,String bankname,
                                        String bankcardno,String realname,String cardno


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);


        parmaMap.put("bankcode", bankcode);
        parmaMap.put("bankname", bankname);
        parmaMap.put("bankcardno", bankcardno);
        parmaMap.put("realname", realname);
        parmaMap.put("cardno", cardno);


//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.BIND_CARD_URL, parmaMap, callBack);
    }


    public static void unbindCardFunction(Context context,
                                        Protocol.CallBack callBack,
                                        String token,Integer id


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);

        parmaMap.put("cardId", id);



//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.UNBIND_CARD_URL, parmaMap, callBack);
    }


    /**
     *  绑定银行卡
     * @param context
     * @param callBack
     * @param token
     * @param listmap
     */
    public static void unBindCardFunction(Context context,
                                        Protocol.CallBack callBack,
                                        String token,String cardId


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);


        parmaMap.put("cardId", cardId);



//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.UNBIND_DEVICE_URL, parmaMap, callBack);
    }



    /**
     *  银行卡列表
     * @param context
     * @param callBack
     * @param token
     * @param listmap
     */
    public static void myCardListFunction(Context context,
                                          Protocol.CallBack callBack,
                                          String token


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);





//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.MY_CARDLIST_URL, parmaMap, callBack);
    }
    /**
     *  银行卡列表
     * @param context
     * @param callBack
     * @param token
     * @param listmap
     */
    public static void applycashFunction(Context context,
                                          Protocol.CallBack callBack,
                                          String token,Integer id ,Integer applyScore


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);

        parmaMap.put("cardId", id);

        parmaMap.put("applyScore", applyScore);



//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.MY_APPLYCASH_URL, parmaMap, callBack);
    }



    /**
     *  银行列表
     * @param context
     * @param callBack
     * @param token
     * @param listmap
     */
    public static void mybankListFunction(Context context,
                                          Protocol.CallBack callBack,
                                          String token


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);





//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.MY_BANKLIST_URL, parmaMap, callBack);
    }


    /**
     *  账户信息
     * @param context
     * @param callBack
     * @param token
     * @param listmap
     */
    public static void  queryAccountInfoFunction(Context context,
                                          Protocol.CallBack callBack,
                                          String token) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);





//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.QUERY_ACCOUNT_URL, parmaMap, callBack);
    }

    /**
     *  账户信息列表
     *
     * @param context
     * @param callBack
     * @param token
     * @param listmap
     */
    public static void  queryAccountDetailListFunction(Context context,
                                                 Protocol.CallBack callBack,
                                                 String token,String  bizType,String begDt,String endDt) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        if(bizType!=null) {
            parmaMap.put("bizType", bizType);
        }
        parmaMap.put("begDt", begDt);
        parmaMap.put("endDt", endDt);





//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.MY_ACCOUNT_DETAIL_LIST_URL, parmaMap, callBack);
    }




    /**
     *  提现申请
     *
     * @param context
     * @param callBack
     * @param token
     * @param listmap
     */
    public static void  applyCashFunction(Context context,
                                                       Protocol.CallBack callBack,
                                                       String token,String  cardId,String applyScore) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        parmaMap.put("cardId", cardId);//int?
        parmaMap.put("applyScore", applyScore);//int?
//        parmaMap.put("endDt", endDt);





//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.MY_ACCOUNT_DETAIL_LIST_URL, parmaMap, callBack);
    }



    /**
     *  提现申请
     *
     * @param context
     * @param callBack
     * @param token
     * @param listmap
     */
    public static void  initOverviewCashFunction(Context context,
                                          Protocol.CallBack callBack,
                                          String token,String  cardId,String applyScore) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        parmaMap.put("cardId", cardId);//int?
        parmaMap.put("applyScore", applyScore);//int?
//        parmaMap.put("endDt", endDt);





//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.MY_ACCOUNT_DETAIL_LIST_URL, parmaMap, callBack);
    }




    /**
     *  上传头像
     *
     * @param context
     * @param callBack
     * @param token
     * @param listmap
     */
    public static void  uploadAvarFunction(Context context,
                                          Protocol.CallBack callBack,
                                          String token,String  file,String fileSuffix,String useWay) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        parmaMap.put("file", file);//int?
        parmaMap.put("fileSuffix", fileSuffix);//int?
        parmaMap.put("useWay", useWay);//int?

//        parmaMap.put("endDt", endDt);





//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.UPLOAD_AVAR_URL, parmaMap, callBack);
    }



    public static void updateUserInfoFunction(Context context,
                                          Protocol.CallBack callBack,
                                          String token,String headimg,String nickname,String cardno


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        if(headimg!=null&&!headimg.equals("")){
        parmaMap.put("headimg", headimg);}
        if(nickname!=null&&!nickname.equals("")) {

            parmaMap.put("nickname", nickname);

        } if(cardno!=null&&!cardno.equals("")) {

            parmaMap.put("carno", cardno);

        }


//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.UPDATE_USERINFO_URL, parmaMap, callBack);
    }



    public static void myJobListFunction(Context context,
                                              Protocol.CallBack callBack,
                                              String token,String bedDt,String endDt,String param,String devNo
                                         ,Integer page,Integer rows


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        parmaMap.put("begDt", bedDt);
        parmaMap.put("endDt", endDt);
        parmaMap.put("param", param);
        if(devNo!=null&&!devNo.equals("")) {
            parmaMap.put("devNo", devNo);
        }

        parmaMap.put("page", page);
        parmaMap.put("rows", rows);
//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.MY_JOB_LIST_URL, parmaMap, callBack);
    }



    public static void myVideoListFunction(Context context,
                                         Protocol.CallBack callBack,
                                         String token,String bedDt,String endDt,String param,String devNo
            ,Integer page,Integer rows


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        parmaMap.put("begDt", bedDt);
        parmaMap.put("endDt", endDt);
        parmaMap.put("param", param);
        if(devNo!=null&&!devNo.equals("")) {
            parmaMap.put("devNo", devNo);
        }

        parmaMap.put("page", page);
        parmaMap.put("rows", rows);
//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.MY_Video_LIST_URL, parmaMap, callBack);
    }

    public static void myAccidentListFunction(Context context,
                                           Protocol.CallBack callBack,
                                           String token,String bedDt,String endDt,String param,String devNo
            ,Integer page,Integer rows


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        parmaMap.put("begDt", bedDt);
        parmaMap.put("endDt", endDt);
        parmaMap.put("param", param);
        if(devNo!=null&&!devNo.equals("")) {
            parmaMap.put("devNo", devNo);
        }

        parmaMap.put("page", page);
        parmaMap.put("rows", rows);
//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.MY_ACCIDENT_LIST_URL, parmaMap, callBack);
    }




    public static void myJobDetailFunction(Context context,
                                         Protocol.CallBack callBack,
                                         String token,String sn


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        parmaMap.put("sn", sn);



//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.MY_JOB_DETAIL_URL, parmaMap, callBack);
    }



    public static void myMainInfoFunction(Context context,
                                           Protocol.CallBack callBack,
                                           String token


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);



//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.ACCT_QUERY_INFO, parmaMap, callBack);
    }




    public static void updateVersionAppFunction(Context context,
                                          Protocol.CallBack callBack,
                                          String token,String osType,String softVersion


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        parmaMap.put("osType", osType);
        parmaMap.put("softVersion", softVersion);



//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.UPDATE_VERSION_INFO, parmaMap, callBack);
    }




    public static void reportJobInfoFunction(Context context,
                                                Protocol.CallBack callBack,
                                                String token,String sn,String carNo,String remark,String violateType,String checkPics


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        parmaMap.put("sn", sn);
        parmaMap.put("carNo", carNo);
        parmaMap.put("remark", remark);
        if(violateType!=null&&!violateType.equals("")) {

            parmaMap.put("violateType", violateType);
        }
        if(checkPics!=null&&!checkPics.equals("")) {

            parmaMap.put("checkPics", checkPics);
        }

//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.UPDATE_AGIAN_JOB_INFO, parmaMap, callBack);
    }

    public static void updateJobInfoFunction(Context context,
                                             Protocol.CallBack callBack,
                                             String token,String sn,String carNo,String remark,String violateType,String checkPics


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        parmaMap.put("sn", sn);
        parmaMap.put("carNo", carNo);
        parmaMap.put("remark", remark);
        if(violateType!=null&&!violateType.equals("")) {

            parmaMap.put("violateType", violateType);
        }
        if(checkPics!=null&&!checkPics.equals("")) {

            parmaMap.put("checkPics", checkPics);
        }

//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.UPDATE_JOB_INFO, parmaMap, callBack);
    }
    public static void getViolatetypeFunction(Context context,
                                             Protocol.CallBack callBack,
                                             String token


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put(API.GET_URL,API.GET_URL);



        new Protocol(context, API.UPDATE_violation_INFO, parmaMap, callBack);
    }



    public static void messageListFunction(Context context,
                                             Protocol.CallBack callBack,
                                             String token,String sign


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        if(sign!=null&&!sign.equals("")) {
            parmaMap.put("sign", sign);
        }


//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.MESSAGE_LIST, parmaMap, callBack);
    }



    public static void updateMessageReadFunction(Context context,
                                           Protocol.CallBack callBack,
                                           String token,String sign,String id


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        if(sign!=null&&!sign.equals("")) {
            parmaMap.put("sign", sign);
        }
        if(id!=null&&!id.equals("")) {
            parmaMap.put("id", id);
        }


//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.MESSAGE_Update_Read, parmaMap, callBack);
    }


    public static void messageDetailFunction(Context context,
                                           Protocol.CallBack callBack,
                                           String token,String page,String rows,String type,String sign


    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("token", token);
        if(sign!=null&&!sign.equals("")) {
            parmaMap.put("sign", sign);
        }
        if(page!=null&&!page.equals("")) {
            parmaMap.put("page", page);
        } if(rows!=null&&!rows.equals("")) {
            parmaMap.put("rows", rows);
        }
        if(type!=null&&!type.equals("")) {
            parmaMap.put("type", type);
        }

//公共参数
        MainApp mainApp = (MainApp) context.getApplicationContext();
        Map<String, String> mapCommon = mainApp.getAppInfo();
        String appname = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        Log.d("appname:", appname);
        parmaMap.put("appName", mapCommon.get("appName"));

        parmaMap.put("platform", mapCommon.get("platform"));
        parmaMap.put("version", mapCommon.get("version"));

        parmaMap.put("deviceNo", mapCommon.get("deviceNo"));


        new Protocol(context, API.MESSAGE_Detail, parmaMap, callBack);
    }

    public static void orderDesignFromBitmap(Context context,
                                             AsyncHttpResponseHandler callBack,
                                             String token, String district, String style, String layout
            , Double size, String resiN, String contacts, String mobile, byte[] imgByteArray
    ) {


        RequestParams parmaMap = new RequestParams();
        if (imgByteArray != null)

            parmaMap.put("pic", new ByteArrayInputStream(imgByteArray), "image/jpeg");


        if (token != null && !token.equals(""))
            parmaMap.put("token", token);
        if (district != null)
            parmaMap.put("district", district);
        if (style != null)
            parmaMap.put("style", style);

        if (layout != null)
            parmaMap.put("layout", layout);
        if (size != null)
            parmaMap.put("size", new BigDecimal(size));
        if (resiN != null)
            parmaMap.put("resiN", resiN);

        if (contacts != null)
            parmaMap.put("contacts", contacts);

        if (mobile != null)
            parmaMap.put("mobile", mobile);


        AsyncHttpUtil.post(API.ORDRE_DESIGN_FROM_LIB, parmaMap, callBack);

//            new Protocol(context, API.ORDRE_DESIGN_FROM_LIB, parmaMap, callBack);
    }
    public static void loginFunction(Context context,
                                     Protocol.CallBack callBack,
                                     String mobile, String code, String cliId, String cliVer
    ) {
        Map<String, Object> parmaMap = new HashMap<String, Object>();
        parmaMap.put("mobile", mobile);

        parmaMap.put("code", code);
        parmaMap.put("cliId", cliId);
        parmaMap.put("cliVer", cliVer);


        new Protocol(context, API.REGISTER_URL, parmaMap, callBack);
    }


}
