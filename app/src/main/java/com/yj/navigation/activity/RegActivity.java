package com.yj.navigation.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.makeramen.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yj.navigation.R;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.RegisterCompleteson;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Constant;
import com.yj.navigation.util.ImageLoaderUtil;
import com.yj.navigation.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by zhang on 2015/8/7.
 */
@EActivity(R.layout.new_reg_view)
public class RegActivity extends BaseActivity {

    @Pref
    ConfigPref_ configPref;
    EditText username_id;

    EditText pwdId;

    @Click(R.id.logining_btn_rel_id)
    void onLoginingBtnRelId() {


        String mobile = username_id.getText().toString();

        if (mobile == null || mobile.equals("")) {

            mobile = configPref.userName().get();
        }

        String pwd = pwdId.getText().toString();


        if (mobile != null && !mobile.equals("") && pwd != null && !pwd.equals("")) {

//            Util.startActivity(this,CompleteInfoActivity_.class);
//            finish();
            loginFromServerForMsg(mobile, pwd);

        } else {

            Util.Toast(RegActivity.this, "请输入密码");

        }


    }

    @Click(R.id.register_id)
    void onRegisterId() {

        Util.startActivity(this, RegisterActivity_.class);
//        finish();
    }

    @Click(R.id.forget_id)
    void onForgetId() {
//忘记密码


        Util.startActivity(this, ForgetPwdActivity_.class);
//        finish();
    }

    @Click(R.id.changeuser_id)
    void onchangeuser_id() {
        if (needChangeUser) {

            username_rel_id.setVisibility(View.VISIBLE);
            mine_name_info_id.setVisibility(View.GONE);
            needChangeUser = false;
            mine_defalut_avar_id.setVisibility(View.VISIBLE);
            mine_avar_id.setVisibility(View.GONE);
        }


    }


    TextView mine_name_info_id;
    RoundedImageView mine_avar_id;
    ImageView mine_defalut_avar_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.design_personal);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        initUi();


    }

    private boolean needChangeUser = false;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    RelativeLayout username_rel_id;
    public void initUi() {
        imageLoader = ImageLoader.getInstance();
        options = ImageLoaderUtil.getAvatarOptionsInstance();


        mine_name_info_id = (TextView) findViewById(R.id.mine_name_info_id);

        username_id = (EditText) findViewById(R.id.username_id);
        username_rel_id = (RelativeLayout) findViewById(R.id.username_rel_id);



        pwdId = (EditText) findViewById(R.id.pwd_id);
        String username = configPref.userName().get();
        String headImg = configPref.userHeadImg().get();
        mine_avar_id = (RoundedImageView) findViewById(R.id.mine_avar_id);

        mine_defalut_avar_id = (ImageView) findViewById(R.id.mine_defalut_avar_id);

        if (headImg != null && !headImg.equals("")) {
            mine_avar_id.setVisibility(View.VISIBLE);
            mine_defalut_avar_id.setVisibility(View.GONE);
            imageLoader.displayImage(headImg, mine_avar_id, options);
        } else {
            mine_defalut_avar_id.setVisibility(View.VISIBLE);
            mine_avar_id.setVisibility(View.GONE);

        }

        if (username != null && !username.equals("")) {
            String nickName = configPref.userNickname().get();
            mine_name_info_id.setVisibility(View.VISIBLE);

            mine_name_info_id.setText(nickName + "");
            username_rel_id.setVisibility(View.INVISIBLE);
            needChangeUser = true;
//            head_ifno_line.setVisibility(View.VISIBLE);
        } else {
            username_rel_id.setVisibility(View.VISIBLE);
            mine_name_info_id.setVisibility(View.GONE);
            needChangeUser = false;
//            head_ifno_line.setVisibility(View.GONE);
        }


//        right_title_line.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                username_id.setVisibility(View.VISIBLE);
//                mine_name_info_id.setVisibility(View.GONE);
//                mine_avar_id.setImageDrawable(getResources().getDrawable(R.drawable.avar_mine_init));
//
//            }
//        });
    }

    @AfterViews
    void init() {
        initUi();
    }


    @Override
    protected void initActivityName() {
        activityName = RegActivity.class.getName();
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


        ProtocolUtil.loginByPwdFunction(this, new LoginInfoHandler(), mobile, code);


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


            RegisterCompleteson baseJson = new Gson().fromJson(resp, RegisterCompleteson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {

                //保存token
                if (baseJson.headImg == null || baseJson.headImg.equals("")) {
                    Log.d("userHeadImg", "");
                    configPref.userHeadImg().put("");

                } else {
                    Log.d("userHeadImg", baseJson.headImg);
                    configPref.userHeadImg().put(baseJson.headImg);
                }
                configPref.userId().put(baseJson.userId);
                configPref.userName().put(baseJson.userName);
                configPref.userToken().put(baseJson.accessToken);
                configPref.userNickname().put(baseJson.nickname);
                configPref.userMobile().put(baseJson.mobile);

                Util.startActivity(RegActivity.this, IndexActivity_.class);
                finish();
            }

        }
    }


    public void getDataFromServerForMsg(String mobile) {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");

        ProtocolUtil.getPhoneMsg(this, new GetPhoneMsginfoHandler(), mobile);


    }


    private class GetPhoneMsginfoHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            getPhoneMsginfoHandler(resp);
        }
    }


    public void getPhoneMsginfoHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {
            Util.Toast(RegActivity.this, "验证码已发送，注意查收");


        }
    }

}
