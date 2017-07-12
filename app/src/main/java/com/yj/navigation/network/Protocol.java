package com.yj.navigation.network;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yj.navigation.activity.RegActivity_;
import com.yj.navigation.util.Util;

import java.util.Map;

@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class Protocol {
    Context context;
    CallBack call;
    int callerId;
    Main mMainAsync;
    String url;
    int TOSTETIME = 3000;

    public Protocol(Context context, String url, Map<String, Object> paramMap,
                    CallBack call) {
        this.url = url;
        this.context = context;
        this.call = call;
        if (context == null)
            makeAsyncRequest(url, paramMap);
        else {
            if (Util.netState(context)) {
                makeAsyncRequest(url, paramMap);
            } else {
                Util.Toast(context, "网络连接不可用，请稍后重试",null);
                call.getMessage("", url);
            }
        }

    }

    private void makeAsyncRequest(String url, Map<String, Object> paramMap) {

        if (Util.netState(context)) {
            ProtocolManager.getProtocolManager().addToQueue(this, call);
            mMainAsync = new Main(paramMap);
            mMainAsync.execute(url);
        } else {
            Util.Toast(context, "网络连接不可用，请稍后重试",null);
            call.getMessage("", url);
        }
    }

    public class Main extends AsyncTask<String, Void, String> {
        String return_code;
        private Map<String, Object> paramMap;
        Map<String, Integer> param;

        public Main() {
        }

        public Main(Map<String, Object> params) {
            this.paramMap = params;
        }

        // public Main(Map<String, String> params,Map<String, Integer> param) {
        // this.paramMap = params;
        // this.param=param;
        // }

        public void toBaiduLogin(String resultNumCode) {
            // 判断是否登录

//            if (resultNumCode != null && "6000037".equals(resultNumCode)) {// 跳转到重新登录页面
//                Intent intent = new Intent(context, LoginActivity.class);
//                context.startActivity(intent);
//                return;
//            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null && result.length() > 0) {
                try {
                    String returnMsg = null;

                    Map<String, Object> orderMap = new Gson().fromJson(result,
                            new TypeToken<Map<String, Object>>() {
                            }.getType());
                    // 将 infomation 转成需要的 order信息

                    return_code = (String) orderMap.get("retCode");
                    returnMsg = (String) orderMap.get("retMsg");


                    if (return_code.equals("0")) {
                        call.getMessage(result, url);
                    } else if (return_code.equals("e000")) {
                        Util.Toast(context, returnMsg,null);
//                        call.getMessage(result, url);
                    } else if (return_code.equals("10")) {
                        Log.i("return_code", "参数错误");
                        Util.Toast(context, returnMsg,null);

                        call.getMessage(result, url);

                    } else if (return_code.equals("11")) {
                        Log.i("return_code", "参数为空");
                        call.getMessage(result, url);


                    } else if (return_code.equals("12")) {
                        Log.i("return_code", "验证码错误");
                        Util.Toast(context, returnMsg,null);

                        call.getMessage(result, url);

                    } else if (return_code.equals("13")) {
                        Log.i("return_code", "数据库错误");
                        call.getMessage(result, url);

                    } else if (return_code.equals("14")) {
                        Log.i("return_code", "系统错误");
                        call.getMessage(result, url);

                    } else if (return_code.equals("-3")) {
                        Log.i("return_code", "用户未登录");
                        //跳转登录页面
                        Util.Toast(context, returnMsg,null);

                        Intent intent = new Intent(context, RegActivity_.class);
                        context.startActivity(intent);

                    } else if (return_code.equals("-4")) {
                        Log.i("return_code", "没有访问权限");
                        Util.Toast(context, returnMsg,null);

                        call.getMessage(result, url);

                    } else if (return_code.equals("-1")) {
                        Log.i("return_code", "系统请求失败");
                        call.getMessage(result, url);

                    } else if (return_code.equals("-2")) {
                        Log.i("return_code", "业务请求失败");
                        call.getMessage(result, url);

                    } else {
                        Log.i("return_code", "未知错误码" + return_code);
                        Util.Toast(context, returnMsg,null);
                        call.getMessage(result, url);

                    }

                } catch (Exception e) {
                    System.out.println("Exception : " + e);
                    Log.e("Exception", "Exception", e);
                }
            } else {
                // WalletUtil.makeText(context, "网络连接超时!",
                // Toast.LENGTH_SHORT).show();.
                Util.Toast(context, "网络连接超时!",null);
                // call.getMessage(null, url);
                call.getMessage(null, url);// 网络请求失败-客户端进行处理
            }
            ProtocolManager.getProtocolManager().removeFromQueue(Protocol.this,
                    call);
        }

        @Override
        protected String doInBackground(String... params) {
            String result;

            if (paramMap != null && paramMap.get(API.GET_URL) != null) {
//                result = Caller.dPosta(params[0],  paramMap);
//
                result = Caller.dGet(params[0], null,null);

            } else {
                result = Caller.sendPost(params[0], paramMap);
            }
            return result;
        }
    }

    public interface CallBack {
        void getMessage(String infomation, String url);
    }
}
