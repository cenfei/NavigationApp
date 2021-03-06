package com.yj.navigation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yj.navigation.R;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.component.FoxToastInterface;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.BaseJson;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Constant;
import com.yj.navigation.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by zhang on 2015/8/7.
 */
@EActivity(R.layout.change_money_pwd_view)
public class ChangeMoneyPwdActivity extends BaseActivity {

    @Pref
    ConfigPref_ configPref;


    @Click(R.id.left_title_line)
    void onLeftTitleLine() {

        finish();

    }


    EditText passwordView, againPasswordView, now_password_info_id;

    @Click(R.id.logining_btn_rel_id)
    void onLoginingBtnRelId() {
        String pwd = passwordView.getText().toString();

        String agaPwd = againPasswordView.getText().toString();
        String nowPwd = now_password_info_id.getText().toString();

        if (nowPwd == null || nowPwd.equals("")) {

            Util.Toast(this, "请输入当前密码",null);

            return;
        }
        if (pwd == null || pwd.equals("")) {

            Util.Toast(this, "请输入密码",null);

            return;
        }
        if (agaPwd == null || agaPwd.equals("")) {

            Util.Toast(this, "请重新输入密码",null);

            return;
        }
        if (pwd != null && !pwd.equals("") && agaPwd != null && !agaPwd.equals("") && !pwd.equals(agaPwd)) {

            Util.Toast(this, "请保持两次密码一致",null);
            return;
        }
        forgetResetPwd(nowPwd, pwd);
        // Util.startActivity(this, IndexActivity_.class);

//        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.design_personal);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        initUi();


    }


    public void initUi() {

        RelativeLayout main_title_id = (RelativeLayout) findViewById(R.id.main_title_id);
        main_title_id.setBackgroundColor(getResources().getColor(R.color.white_alpha80));

        ImageView left_title_icon = (ImageView) findViewById(R.id.left_title_icon);
        left_title_icon.setVisibility(View.VISIBLE);
        ImageView right_title_icon = (ImageView) findViewById(R.id.right_title_icon);
        right_title_icon.setVisibility(View.GONE);
        TextView left_title = (TextView) findViewById(R.id.left_title);
        left_title.setVisibility(View.VISIBLE);
        left_title.setText("个人中心");
        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText("修改密码");
        title.setTextColor(getResources().getColor(R.color.line_hot_all));
        View title_line_id = (View) findViewById(R.id.title_line_id);
        title_line_id.setVisibility(View.GONE);

        passwordView = (EditText) findViewById(R.id.password_info_id);
        againPasswordView = (EditText) findViewById(R.id.again_password_info_id);
        now_password_info_id = (EditText) findViewById(R.id.now_password_info_id);


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
        activityName = ChangeMoneyPwdActivity.class.getName();
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

    public void forgetResetPwd(String nowpwd, String pwd) {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");


        ProtocolUtil.loginpwdUpdateFunction(this, new LoginInfoHandler(), configPref.userToken().get(), nowpwd, pwd);


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


            BaseJson baseJson = new Gson().fromJson(resp, BaseJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {
                Util.Toast(ChangeMoneyPwdActivity.this, "登录密码修改成功", new FoxToastInterface.FoxToastCallback() {
                    @Override
                    public void toastCloseCallbak() {
                        finish();
                    }
                });

//                Util.startActivity(ChangeMoneyPwdActivity.this, IndexActivity_.class);

            }

        }
    }


}
