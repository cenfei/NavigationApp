package com.yj.navigation.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yj.navigation.R;
import com.yj.navigation.adapter.MyFragmentPageAdapter;
import com.yj.navigation.adapter.MyVideoFragmentPageAdapter;
import com.yj.navigation.base.BaseFragmentActivity;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

/**
 * Created by zhang on 2015/8/7.
 */
@EActivity(R.layout.my_video_list_view)
public class MyVideoListActivity extends BaseFragmentActivity {

    @Pref
    ConfigPref_ configPref;

    Resources resources;
    public ViewPager mPager;
    private List<Fragment> fragmentsList;
    private ImageView ivBottomLine;
    private TextView tvTabNew, tvTabHot;

    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    private int position_one;
    public final static int num = 2;
    Fragment home1;
    Fragment home2;
    Fragment home3;

    @Click(R.id.left_title_line)
    void onLeftTitleLine() {

        finish();

    }
    private void InitTextView() {
        tvTabNew = (TextView) findViewById(R.id.tv_tab_1);
        tvTabHot = (TextView) findViewById(R.id.tv_tab_2);

        tvTabNew.setOnClickListener(new MyOnClickListener(0));
        tvTabHot.setOnClickListener(new MyOnClickListener(1));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.design_personal);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        initUi();


    }

    private void InitViewPager() {
        mPager = (ViewPager) findViewById(R.id.vPager);
//        fragmentsList = new ArrayList<Fragment>();
//
//        home1 = new AllFragment_();
//        home2 = new WorkDoingFragment_();
//        home3 = new WorkOkFragment_();
//
//        fragmentsList.add(home1);
//        fragmentsList.add(home2);
//        fragmentsList.add(home3);

        FragmentManager fm = getSupportFragmentManager();
        MyVideoFragmentPageAdapter myFragmentPageAdapter = new MyVideoFragmentPageAdapter(fm);

        mPager.setAdapter(myFragmentPageAdapter);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
//        mPager.setCurrentItem(1);

    }


    private void InitWidth() {
        ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);
        bottomLineWidth = ivBottomLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
//        int  tvTabNewWid=tvTabNew.getLayoutParams().width;;
//Log.e("aaaaa",screenW+".."+bottomLineWidth+".."+tvTabNewWid);
//        offset = (int) ( screenW/2-bottomLineWidth/2-(35*3)-bottomLineWidth/2) ;
//        int avg = (int) (screenW -2*offset);
//        position_one = avg + offset;
        offset = (int) (((screenW - (2 * 100 * bottomLineWidth / 48)) / num - bottomLineWidth) / 2) + 100 * bottomLineWidth / 48;
        int avg = (int) ((screenW - (2 * 100 * bottomLineWidth / 48)) / num);
        position_one = avg + offset;


    }

    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    }


    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, offset, 0, 0);
                        tvTabHot.setTextColor(resources.getColor(R.color.myvideo_color));
                        tvTabHot.setTextSize(TypedValue.COMPLEX_UNIT_SP,19);
                    }
                    tvTabNew.setTextColor(resources.getColor(R.color.white));
                    tvTabNew.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);

                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_one, 0, 0);
                        tvTabNew.setTextColor(resources.getColor(R.color.myvideo_color));
                        tvTabNew.setTextSize(TypedValue.COMPLEX_UNIT_SP,19);

                    }
                    tvTabHot.setTextColor(resources.getColor(R.color.white));
                    tvTabHot.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);

                    break;

            }
            currIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }


    public void initUi() {

        RelativeLayout main_title_id = (RelativeLayout) findViewById(R.id.main_title_id);
//        main_title_id.setBackgroundColor(getResources().getColor(R.color.work_title_color));

        ImageView left_title_icon = (ImageView) findViewById(R.id.left_title_icon);
        left_title_icon.setVisibility(View.VISIBLE);
        ImageView right_title_icon = (ImageView) findViewById(R.id.right_title_icon);
        right_title_icon.setVisibility(View.GONE);

        TextView right_title = (TextView) findViewById(R.id.right_title);

        right_title.setVisibility(View.GONE);
        right_title.setText("");
        right_title.setTextColor(getResources().getColor(R.color.white));
//
//
//        TextView title = (TextView) findViewById(R.id.title);
//        title.setVisibility(View.VISIBLE);
//        title.setText("设备");
//        title.setTextColor(getResources().getColor(R.color.white));
//        View title_line_id = (View) findViewById(R.id.title_line_id);
//        title_line_id.setVisibility(View.GONE);
//
//
//        LinearLayout right_title_line = (LinearLayout) findViewById(R.id.right_title_line);
//
//        right_title_line.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });


        resources = getResources();

        InitTextView();
        InitWidth();
        InitViewPager();
        TranslateAnimation animation = new TranslateAnimation(position_one, offset, 0, 0);
        tvTabHot.setTextColor(resources.getColor(R.color.myvideo_color));
        tvTabNew.setTextColor(resources.getColor(R.color.white));

        animation.setFillAfter(true);
        animation.setDuration(300);
        ivBottomLine.startAnimation(animation);


        InitViewPager();
    }


    @AfterViews
    void init() {
        initUi();
    }

    private String activityName;

    protected void initActivityName() {
        activityName = MyVideoListActivity.class.getName();
    }

    @Override
    protected void setActivityBg() {
//        if (BgTransitionUtil.bgDrawable != null) {
//            mainPage.setBackgroundDrawable(BgTransitionUtil.bgDrawable);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    //**********获取筛选的后的list***************/
    FoxProgressbarInterface foxProgressbarInterface;

    public void loginFromServerForMsg(String mobile, String code) {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");

        String clientVersion = Util.getAppVersionName(this);

        ProtocolUtil.loginFunction(this, new LoginInfoHandler(), mobile, code, "android", clientVersion);


    }


    private class LoginInfoHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            loginInfoHandler(resp);
        }
    }


    public void loginInfoHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


//            BaseJson baseJson = new Gson().fromJson(resp, BaseJson.class);
//            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {
//
//                //保存token
//                configPref.userToken().put(baseJson.token);
//                configPref.userMobile().put(mobile);
////                Util.startActivity(LoginActivity.this, MineActivity_.class);
//                finish();
//            }

        }
    }


}
