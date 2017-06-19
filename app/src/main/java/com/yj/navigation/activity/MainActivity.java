package com.yj.navigation.activity;

import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yj.navigation.R;
import com.yj.navigation.base.BaseFragmentActivity;
import com.yj.navigation.util.ImageLoaderUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;


//import java.util.Timer;
//import java.util.TimerTask;

//import java.util.Timer;
//import java.util.TimerTask;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseFragmentActivity {

    static String TAG = MainActivity.class.getName();

    @Extra
    int flag = 0;

    @ViewById(R.id.container)
    FrameLayout container;

    @ViewById(R.id.my_content_view)
    View myContentView;

    Fragment fragmentHome;
    Fragment fragmentDesign;
    Fragment fragmentShop;
    Fragment fragmentMine;


    private Fragment currentFragment = null;

    @ViewById(R.id.tab_run)
    View tabRun;
    @ViewById(R.id.tab_run_img)
    ImageView tabRunImg;

    @ViewById(R.id.tab_pay)
    View tabPay;
    @ViewById(R.id.tab_pay_img)
    ImageView tabPayImg;

    @ViewById(R.id.tab_discover)
    View tabDiscover;
    @ViewById(R.id.tab_discover_img)
    ImageView tabDiscoverImg;

    @ViewById(R.id.tab_mine)
    View tabMine;
    @ViewById(R.id.tab_mine_img)
    ImageView tabMineImg;



//    @Pref
//    UserPref_ userPref;
//    @Pref
//ConfigPref_ configPref;
//    @Pref
//    DeviceBindPref_ deviceBindPref;
//    @Pref
//    IntroPref_ introPref;
//
//    @Pref
//    SyncPref_ syncPref;
//    @Pref
//    DiscoverActivitiesPref_ discoverActivitiesPref;
//
//    SSDialogSure dialogNotification;

    public static boolean isSelectPayTab = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @AfterViews
    void onInit() {
//        ImageLoaderUtil.initDefaulLoader(getApplicationContext());
//
//        fragmentHome = HomeTabFragment_.builder().build();
//        fragmentDesign = DesignTabFragment_.builder().build();
//        fragmentShop = ShopTabFragment_.builder().build();
//        fragmentMine = MineTabFragment_.builder().build();
//
//        //预先add fragments,防止anr时切换tab导致重复add exception
//
//        changePage(fragmentMine);
//        changePage(fragmentDesign);
//        changePage(fragmentShop);
//        changePage(fragmentHome);

//        if (flag == 0) {
//            selectRun();
////            showIntroDialog();
//        } else if (flag == 1) {
//            selectPay();
//        }



    }

//    @Click(R.id.tab_run)
//    void onTabRun() {
//        selectRun();
//    }
//
//    @Click(R.id.tab_pay)
//    void onTabPay() {
//        selectPay();
//    }
//
//    @Click(R.id.tab_discover)
//    void onTabDiscover() {
//        selectDiscover();
//    }
//
//    @Click(R.id.tab_mine)
//    void onTabMine() {
//        selectMine();
//    }
//
//    final int TAB_TRANS_TIME = 200;
//
//    private void resetTab(View tab) {
//        if (tab != null) {
//            TransitionDrawable transitionDrawable = (TransitionDrawable) tab.getBackground();
//            transitionDrawable.reverseTransition(TAB_TRANS_TIME);
//        }
//    }
//
//    private void selectTab(View tab) {
//        currentTab = tab;
//        TransitionDrawable transitionDrawable = (TransitionDrawable) tab.getBackground();
//        transitionDrawable.startTransition(TAB_TRANS_TIME);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
//    }
//
//    private View currentTab = null;
//
//    private void selectRun() {
//        quitFullScreen();
////        setGuideResId(R.drawable.design_show_loading);
//
//        resetImg();
//        resetTab(currentTab);
//        selectTab(tabRun);
//        changePage(fragmentHome);
//
//        tabRunImg.setImageResource(R.drawable.tab_home_s);
//
//    }
//
//    private void selectPay() {
//
//        setFullScreen();
//        setGuideResId(R.drawable.design_show_loading);
//
//        resetImg();
//        resetTab(currentTab);
//        selectTab(tabPay);
//        changePage(fragmentDesign);
//        tabPayImg.setImageResource(R.drawable.tab_design_s);
//
//    }
//
//    private void selectDiscover() {
//        quitFullScreen();
////        setGuideResId(R.drawable.design_show_loading);
//
//        resetImg();
//        resetTab(currentTab);
//        selectTab(tabDiscover);
//        changePage(fragmentShop);
//        tabDiscoverImg.setImageResource(R.drawable.tab_shop_s);
//
//    }
//
//    private void selectMine() {
//        quitFullScreen();
////        setGuideResId(R.drawable.design_show_loading);
//        resetImg();
//        resetTab(currentTab);
//        selectTab(tabMine);
//        changePage(fragmentMine);
//        tabMineImg.setImageResource(R.drawable.tab_my_s);
//
//    }
//
//    private void changePage(Fragment fragment) {
//
//        if (currentFragment != fragment) {
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//            if (currentFragment != null) {
//                transaction.hide(currentFragment);
//            }
//
//            if (fragment.isAdded()) {
//                transaction.show(fragment);
//            } else {
//                transaction.add(R.id.container, fragment);
//            }
//            transaction.commit();
//            currentFragment = fragment;
//        }
//    }
//
//    static int count = 0;


    @Override
    protected void onDestroy() {
        super.onDestroy();

//        Util.eventUnregister(this);
//        unregisterReceiver(timeTickReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        if(currentTab==tabPay){
//            setFullScreen();
//        }else{
//            quitFullScreen();
//        }



    }

    @Override
    protected void setActivityBg() {
//        if (BgTransitionUtil.bgDrawable != null) {
//            mainPage.setBackground(BgTransitionUtil.bgDrawable);
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "return from setttings... in UserMain");

    }

//


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    private void resetImg() {
//        tabDiscoverImg.setImageResource(R.drawable.tab_shop);
//        tabMineImg.setImageResource(R.drawable.tab_my);
//        tabPayImg.setImageResource(R.drawable.tab_design);
//        tabRunImg.setImageResource(R.drawable.tab_home);
//    }
//
//
//    private void setFullScreen() {
////        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去标题栏
//        if (Build.VERSION.SDK_INT >= 19){
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE  //去掉好像无影响
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //去掉好像无影响
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//            decorView.setSystemUiVisibility(option);
//        }else {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
//    }
////退出全屏函数：
//
//    private void quitFullScreen() {
////        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去标题栏
//        if (Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_VISIBLE;
//            decorView.setSystemUiVisibility(option);
//
//        } else {
//
//
//            final WindowManager.LayoutParams attrs = getWindow().getAttributes();
//            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getWindow().setAttributes(attrs);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
//    }
}
