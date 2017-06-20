package com.yj.navigation.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yj.navigation.R;
import com.yj.navigation.adapter.NewMyVideoFragmentPageAdapter;
import com.yj.navigation.base.BaseFragmentActivity;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by zhang on 2015/8/7.
 */
@EActivity(R.layout.new_myvideo_list_view)
public class NewMyVideoListActivity extends BaseFragmentActivity {

    @Pref
    ConfigPref_ configPref;

    public ViewPager mPager;


    private String activityName = null;
    @ViewById(R.id.work_all_title_id)
    LinearLayout workAllTitleId;
    @ViewById(R.id.work_doing_title_id)
    LinearLayout workDoingTitleId;

    @ViewById(R.id.video_list_title2_id)
    TextView video_list_title2_id;
    @ViewById(R.id.video_list_title1_id)
    TextView video_list_title1_id;

    @Click(R.id.left_title_line)
    void onLeftTitleLine() {

        finish();

    }

    @Click(R.id.work_all_title_id)
    void onworkalltitleid() {

        Util.setBackgroundOfVersion(workAllTitleId, getResources().getDrawable(R.drawable.rounded_work_left_choosewallet));

        Util.setBackgroundOfVersion(workDoingTitleId, getResources().getDrawable(R.drawable.rounded_work_right_wallet));
        video_list_title1_id.setTextColor(getResources().getColor(R.color.white));

        video_list_title2_id.setTextColor(getResources().getColor(R.color.new_blue));

//        workDoingTitleId.setBackgroundColor(getResources().getColor(R.color.work_title2_unchoose_color));
//        Util.setBackgroundOfVersion(workOkTitleId, getResources().getDrawable(R.drawable.rounded_work_right_wallet));

        mPager.setCurrentItem(0);
    }

    @Click(R.id.work_doing_title_id)
    void onwork_doing_title_id() {
        Util.setBackgroundOfVersion(workAllTitleId, getResources().getDrawable(R.drawable.rounded_work_left_wallet));
        Util.setBackgroundOfVersion(workDoingTitleId, getResources().getDrawable(R.drawable.rounded_work_right_choose_wallet));
        video_list_title2_id.setTextColor(getResources().getColor(R.color.white));

        video_list_title1_id.setTextColor(getResources().getColor(R.color.new_blue));

//        workDoingTitleId.setBackgroundColor(getResources().getColor(R.color.work_title2_choose_color));
//        Util.setBackgroundOfVersion(workOkTitleId, getResources().getDrawable(R.drawable.rounded_work_right_wallet));

        mPager.setCurrentItem(1);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    private void InitViewPager() {
        mPager = (ViewPager) findViewById(R.id.vPager);


        FragmentManager fm = getSupportFragmentManager();
        NewMyVideoFragmentPageAdapter myFragmentPageAdapter=new NewMyVideoFragmentPageAdapter(fm);

        mPager.setAdapter(myFragmentPageAdapter);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mPager.setCurrentItem(0);

    }


    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    onworkalltitleid();
                    break;
                case 1:
                    onwork_doing_title_id();

                    break;

            }


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
        main_title_id.setBackgroundColor(getResources().getColor(R.color.white_alpha80));

        ImageView left_title_icon = (ImageView) findViewById(R.id.left_title_icon);
        left_title_icon.setVisibility(View.VISIBLE);
        TextView left_title = (TextView) findViewById(R.id.left_title);
        left_title.setVisibility(View.VISIBLE);
        left_title.setText("返回");
        ImageView right_title_icon = (ImageView) findViewById(R.id.right_title_icon);
        right_title_icon.setVisibility(View.GONE);

        TextView right_title = (TextView) findViewById(R.id.right_title);

        right_title.setVisibility(View.VISIBLE);
        right_title.setText("筛选");
//        right_title.setTextColor(getResources().getColor(R.color.white));


        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText("我的上传视频");
//        title.setTextColor(getResources().getColor(R.color.white));
        View title_line_id = (View) findViewById(R.id.title_line_id);
        title_line_id.setVisibility(View.GONE);


        LinearLayout right_title_line = (LinearLayout) findViewById(R.id.right_title_line);

        right_title_line.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });

        InitViewPager();
    }

    @AfterViews
    void init() {
        initUi();
    }


    protected void initActivityName() {
        activityName = NewMyVideoListActivity.class.getName();
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


}
