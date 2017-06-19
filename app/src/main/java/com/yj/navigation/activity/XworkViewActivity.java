//package com.yj.yjdesign.activity;
//
//import android.os.Bundle;
//import android.webkit.WebSettings;
//
//import org.xwalk.core.XWalkActivity;
//import org.xwalk.core.XWalkNavigationHistory;
//import org.xwalk.core.XWalkResourceClient;
//import org.xwalk.core.XWalkSettings;
//import org.xwalk.core.XWalkUIClient;
//import org.xwalk.core.XWalkView;
//import org.xwalk.core.internal.XWalkViewBridge;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//
///**
// * Created by foxcen on 16/8/26.
// */
//    public class XworkViewActivity extends XWalkActivity {
//
//        private XWalkView xWalkView;
//    private String url, title = null;
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//
//            if (getIntent() != null) {
//                title = getIntent().getStringExtra("title");
//                url = getIntent().getStringExtra("url");
////                downLoad = getIntent().getStringExtra("download");
//
////                accessToken = getIntent().getStringExtra("accessToken");
//
//            }
//
//            xWalkView = new XWalkView(this, this);
//            //类似于webview.setWebViewClient(new WebViewClient());
//            //XWalkView已经没有setWebChromeClient()方法，但是在XWalkResourceClient中多了个onProgressChanged回调方法。
//            xWalkView .setResourceClient(new XWalkResourceClient(xWalkView ));
//            //设置Ui回调
//            xWalkView.setUIClient(new XWalkUIClient(xWalkView));
//            setContentView(xWalkView);
//        }
//
//        //返回上个页面
//        private void back(){
//            if(xWalkView.getNavigationHistory().canGoBack())
//                xWalkView.getNavigationHistory().navigate(XWalkNavigationHistory.Direction.BACKWARD, 1);
//        }
//
//        //抽象方法，当crosswalk初始化完成时才会执行该方法，允许XWalkView在此之前初始化，但是不允许它在此之前加载网页。
//        //之前的版本load方法可以在crosswalk初始化之前调用，现在不行了
//        @Override
//        protected void onXWalkReady() {
//            // webview加载网页是loadUrl("http://csdn.net");
//            xWalkView.load(url, null);
//        }
//
//    //设置加载缓存的方式，和WebView的设置方式其实差不多
//    public void setCacheMode(XWalkView xw){
//        try {
//            Method _getBridge = XWalkView.class.getDeclaredMethod("getBridge");
//            _getBridge.setAccessible(true);
//            XWalkViewBridge xWalkViewBridge = null;
//            xWalkViewBridge = (XWalkViewBridge)_getBridge.invoke(xw);
////            XWalkSettings xWalkSettings = xWalkViewBridge.getSettings();
////            if(NetWorkHelp.isWifiConnected(this)){
////                xWalkSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
////            }else{
////                xWalkSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
////            }
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
