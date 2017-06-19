package com.yj.navigation.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yj.navigation.R;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.component.SZ_PayPopwindow_Avar;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.BaseJson;
import com.yj.navigation.object.ImageAvarJson;
import com.yj.navigation.object.RegisterCompleteson;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Base64Util;
import com.yj.navigation.util.Constant;
import com.yj.navigation.util.FileUtil;
import com.yj.navigation.util.ImageLoaderUtil;
import com.yj.navigation.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.File;

/**
 * Created by zhang on 2015/8/7.
 */
@EActivity(R.layout.withdraw_view)
public class WithDrawActivity extends BaseActivity {

    @Pref
    ConfigPref_ configPref;



    @Click(R.id.left_title_line)
    void onLeftTitleLine() {

        finish();

    }


    @Click(R.id.change_money_pwd_id)
    void onchange_money_pwd_id() {
        Util.startActivity(this, BindCardActivity_.class);

        finish();

    }


    @Click(R.id.logining_btn_rel_id)
    void onLoginingBtnRelId() {

//退出登录
        finish();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.design_personal);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        initUi();


    }


    ImageView  mine_avar_id;
    public void initUi() {

        RelativeLayout main_title_id = (RelativeLayout) findViewById(R.id.main_title_id);
        main_title_id.setBackgroundColor(getResources().getColor(R.color.white));

        ImageView left_title_icon = (ImageView) findViewById(R.id.left_title_icon);
        left_title_icon.setVisibility(View.VISIBLE);
        ImageView right_title_icon = (ImageView) findViewById(R.id.right_title_icon);
        right_title_icon.setVisibility(View.GONE);

        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText("提现");
        title.setTextColor(getResources().getColor(R.color.line_hot_all));
        View title_line_id = (View) findViewById(R.id.title_line_id);
        title_line_id.setVisibility(View.GONE);


        LinearLayout right_title_line = (LinearLayout) findViewById(R.id.right_title_line);

        right_title_line.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    @AfterViews
    void init() {
        initUi();
    }


    @Override
    protected void initActivityName() {
        activityName = WithDrawActivity.class.getName();
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

    public void uploadAvar(String file, String fileSuffix) {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");


        ProtocolUtil.uploadAvarFunction(this, new UploadAvarHandler(), configPref.userToken().get(), file, fileSuffix, "1");


    }


    private class UploadAvarHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            uploadAvarHandler(resp);
        }
    }


    public void uploadAvarHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            ImageAvarJson baseJson = new Gson().fromJson(resp, ImageAvarJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {
                Log.d("path:", baseJson.data.path);

                Log.d("url:", baseJson.data.url);
                //保存token
                configPref.userHeadImg().put(baseJson.data.url);//是url还是path


//                Util.startActivity(MineInfoActivity.this, RegActivity_.class);
//                finish();
            }

        }
    }




}
