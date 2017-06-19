package com.yj.navigation.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yj.navigation.R;
import com.yj.navigation.adapter.MyFragmentPageAdapter;
import com.yj.navigation.base.BaseFragmentActivity;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.fragment.AllFragment;

import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 2015/8/7.
 */
@EActivity(R.layout.mywork_main_view)
public class MyWorkActivity extends BaseFragmentActivity {

    @Pref
    ConfigPref_ configPref;

    Resources resources;
    private ViewPager mPager;
    private List<Fragment> fragmentsList;
    private ImageView ivBottomLine;
    private TextView tvTabNew, tvTabHot;

    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    private int position_one;
    public final static int num = 3;
    Fragment home1;
    Fragment home2;
    Fragment home3;

    private String activityName = null;
    @ViewById(R.id.work_all_title_id)
    LinearLayout workAllTitleId;
    @ViewById(R.id.work_doing_title_id)
    LinearLayout workDoingTitleId;
    @ViewById(R.id.work_ok_title_id)
    LinearLayout workOkTitleId;

    @Click(R.id.work_all_title_id)
    void onworkalltitleid() {

        Util.setBackgroundOfVersion(workAllTitleId, getResources().getDrawable(R.drawable.rounded_work_left_choosewallet));
        workDoingTitleId.setBackgroundColor(getResources().getColor(R.color.work_title2_unchoose_color));
        Util.setBackgroundOfVersion(workOkTitleId, getResources().getDrawable(R.drawable.rounded_work_right_wallet));

        mPager.setCurrentItem(0);
    }

    @Click(R.id.work_doing_title_id)
    void onwork_doing_title_id() {
        Util.setBackgroundOfVersion(workAllTitleId, getResources().getDrawable(R.drawable.rounded_work_left_wallet));


        workDoingTitleId.setBackgroundColor(getResources().getColor(R.color.work_title2_choose_color));
        Util.setBackgroundOfVersion(workOkTitleId, getResources().getDrawable(R.drawable.rounded_work_right_wallet));

        mPager.setCurrentItem(1);

    }

    @Click(R.id.work_ok_title_id)
    void onwork_ok_title_id() {
        Util.setBackgroundOfVersion(workAllTitleId, getResources().getDrawable(R.drawable.rounded_work_left_wallet));


        workDoingTitleId.setBackgroundColor(getResources().getColor(R.color.work_title2_unchoose_color));

        Util.setBackgroundOfVersion(workOkTitleId, getResources().getDrawable(R.drawable.rounded_work_right_choose_wallet));

        mPager.setCurrentItem(2);

    }

    @Click(R.id.left_title_line)
    void onLeftTitleLine() {

        finish();

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
        MyFragmentPageAdapter myFragmentPageAdapter=new MyFragmentPageAdapter(fm);

        mPager.setAdapter(myFragmentPageAdapter);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mPager.setCurrentItem(0);

    }

//    public class MyOnClickListener implements View.OnClickListener {
//        private int index = 0;
//
//        public MyOnClickListener(int i) {
//            index = i;
//        }
//
//        @Override
//        public void onClick(View v) {
//            mPager.setCurrentItem(index);
//        }
//    }

    ;

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    onworkalltitleid();
//                    if (currIndex == 1) {
//                        animation = new TranslateAnimation(position_one, offset, 0, 0);
//                        tvTabHot.setTextColor(resources.getColor(R.color.line_hot_all));
//                    }
//                    tvTabNew.setTextColor(resources.getColor(R.color.line_hot_all));
                    break;
                case 1:
                    onwork_doing_title_id();
//                    if (currIndex == 0) {
//                        animation = new TranslateAnimation(offset, position_one, 0, 0);
//                        tvTabNew.setTextColor(resources.getColor(R.color.line_hot_all));
//                    }
//                    tvTabHot.setTextColor(resources.getColor(R.color.line_hot_all));
                    break;
                case 2:
                    onwork_ok_title_id();

                    break;
            }
//            currIndex = arg0;
//            animation.setFillAfter(true);
//            animation.setDuration(300);
//            ivBottomLine.startAnimation(animation);
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
        main_title_id.setBackgroundColor(getResources().getColor(R.color.work_title_color));

        ImageView left_title_icon = (ImageView) findViewById(R.id.left_title_icon);
        left_title_icon.setVisibility(View.VISIBLE);
        ImageView right_title_icon = (ImageView) findViewById(R.id.right_title_icon);
        right_title_icon.setVisibility(View.GONE);

        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText("工单");
        title.setTextColor(getResources().getColor(R.color.white));
        View title_line_id = (View) findViewById(R.id.title_line_id);
        title_line_id.setVisibility(View.GONE);


        LinearLayout right_title_line = (LinearLayout) findViewById(R.id.right_title_line);

        right_title_line.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        InitViewPager();
    }


    @AfterViews
    void init() {
        initUi();
    }


    protected void initActivityName() {
        activityName = MyWorkActivity.class.getName();
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
