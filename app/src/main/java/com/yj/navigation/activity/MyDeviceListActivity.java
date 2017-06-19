package com.yj.navigation.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qr_codescan.MipcaActivityCapture;
import com.google.gson.Gson;
import com.yj.navigation.R;
import com.yj.navigation.adapter.MyDeviceFragmentPageAdapter;
import com.yj.navigation.adapter.MyFragmentPageAdapter;
import com.yj.navigation.base.BaseFragmentActivity;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.BaseJson;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Constant;
import com.yj.navigation.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

/**
 * Created by zhang on 2015/8/7.
 */
@EActivity(R.layout.mydevice_list_view)
public class MyDeviceListActivity extends BaseFragmentActivity {

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
    @Click(R.id.left_title_line)
    void onLeftTitleLine() {

        finish();

    }

    @Click(R.id.work_all_title_id)
    void onworkalltitleid() {

        Util.setBackgroundOfVersion(workAllTitleId, getResources().getDrawable(R.drawable.rounded_work_left_choosewallet));
        workDoingTitleId.setBackgroundColor(getResources().getColor(R.color.work_title2_unchoose_color));
//        Util.setBackgroundOfVersion(workOkTitleId, getResources().getDrawable(R.drawable.rounded_work_right_wallet));

        mPager.setCurrentItem(0);
    }

    @Click(R.id.work_doing_title_id)
    void onwork_doing_title_id() {
        Util.setBackgroundOfVersion(workAllTitleId, getResources().getDrawable(R.drawable.rounded_work_left_wallet));


        workDoingTitleId.setBackgroundColor(getResources().getColor(R.color.work_title2_choose_color));
//        Util.setBackgroundOfVersion(workOkTitleId, getResources().getDrawable(R.drawable.rounded_work_right_wallet));

        mPager.setCurrentItem(1);

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
        MyDeviceFragmentPageAdapter myFragmentPageAdapter=new MyDeviceFragmentPageAdapter(fm);

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
        main_title_id.setBackgroundColor(getResources().getColor(R.color.work_title_color));

        ImageView left_title_icon = (ImageView) findViewById(R.id.left_title_icon);
        left_title_icon.setVisibility(View.VISIBLE);
        ImageView right_title_icon = (ImageView) findViewById(R.id.right_title_icon);
        right_title_icon.setVisibility(View.GONE);

        TextView right_title = (TextView) findViewById(R.id.right_title);

        right_title.setVisibility(View.VISIBLE);
        right_title.setText("添加");
        right_title.setTextColor(getResources().getColor(R.color.white));


        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText("设备");
        title.setTextColor(getResources().getColor(R.color.white));
        View title_line_id = (View) findViewById(R.id.title_line_id);
        title_line_id.setVisibility(View.GONE);


        LinearLayout right_title_line = (LinearLayout) findViewById(R.id.right_title_line);

        right_title_line.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                Util.startActivity(MyDeviceListActivity.this,BindCardActivity_.class);

                Intent intent = new Intent();
                intent.setClass(MyDeviceListActivity.this, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });

        InitViewPager();
    }
    private final static int SCANNIN_GREQUEST_CODE = 1;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    Log.d("capture result:", bundle.getString("result"));

                    String deviceNo = "";
                    String deviceToken = "123456";

                    //假设返回的结果-----
                    bindDevice(null,deviceToken);

//                    Util.startActivity(IndexActivity.this, MyDeviceListActivity_.class);


//                    mTextView.setText(bundle.getString("result"));
//                    mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
                }
                break;
        }
    }
    @AfterViews
    void init() {
        initUi();
    }


    protected void initActivityName() {
        activityName = MyDeviceListActivity.class.getName();
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

    public void bindDevice(String deviceNo, String deviceToken) {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");


        //扫描绑定 0 设备号绑定1
        ProtocolUtil.bindDeviceFunction(this, new BindDeviceHandler(), configPref.userToken().get(), "0", deviceNo, deviceToken);


    }


    private class BindDeviceHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            bindDeviceHandler(resp);
        }
    }

    FoxProgressbarInterface foxProgressbarInterface;

    public void bindDeviceHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            BaseJson baseJson = new Gson().fromJson(resp, BaseJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {

                //保存token

//                Util.startActivity(MyDeviceListActivity.this, MyDeviceListActivity_.class);

            }

        }
    }

}
