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
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.RegisterCompleteson;
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
@EActivity(R.layout.completeinfo_view)
public class CompleteInfoActivity extends BaseActivity {

    @Pref
    ConfigPref_ configPref;


    @Click(R.id.left_title_line)
    void onLeftTitleLine() {

        finish();

    }


    @Click(R.id.safe_question_rel)
    void onSafeQuestionRel() {
        Util.startActivity(this, SafeQuestionActivity_.class);

//        finish();

    }

    @Click(R.id.address_rel)
    void onAddressRel() {

        Util.startActivity(this, SelectCityActivity_.class);
//        finish();

    }


    @Click(R.id.logining_btn_rel_id)
    void onLoginingBtnRelId() {

        String pwd = passwordView.getText().toString();

        String agaPwd = againPasswordView.getText().toString();
        String name = nameView.getText().toString();
        String cardno = cardnoView.getText().toString();


        if (pwd == null || pwd.equals("")) {

            Util.Toast(this, "请输入密码");

            return;
        }
        if (agaPwd == null || agaPwd.equals("")) {

            Util.Toast(this, "请重新输入密码");

            return;
        }
        if (pwd != null && !pwd.equals("") && agaPwd != null && !agaPwd.equals("") && !pwd.equals(agaPwd)) {

            Util.Toast(this, "请保持两次密码一致");
            return;
        }
        if (name == null || name.equals("")) {
            Util.Toast(this, "请输入姓名");
            return;
        }



        completeRegisterServerForMsg(name,pwd,cardno);


//        Util.startActivity(this, IndexActivity_.class);
//
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
        left_title.setText("返回");
        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText("完善信息");
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
        passwordView = (EditText) findViewById(R.id.password_info_id);
        againPasswordView = (EditText) findViewById(R.id.again_password_info_id);

        nameView = (EditText) findViewById(R.id.name_info_id);
        cardnoView = (EditText) findViewById(R.id.cardno_info_id);


    }


    EditText passwordView, againPasswordView, nameView, cardnoView;

    @AfterViews
    void init() {
        initUi();
    }


    @Override
    protected void initActivityName() {
        activityName = CompleteInfoActivity.class.getName();
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

    public void completeRegisterServerForMsg( String name,String pwd,String cardno) {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");

        String clientVersion = Util.getAppVersionName(this);

        ProtocolUtil.registerCompleteFunction(this, new RegisterComInfoHandler(), configPref.userMobile().get(), configPref.userKey().get(), pwd, name, cardno);


    }


    private class RegisterComInfoHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            registerComInfoHandler(resp);
        }
    }


    public void registerComInfoHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            RegisterCompleteson baseJson = new Gson().fromJson(resp, RegisterCompleteson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {

                //保存token
                configPref.userHeadImg().put(baseJson.headImg);
                configPref.userId().put(baseJson.userId);
                configPref.userName().put(baseJson.userName);
                configPref.userToken().put(baseJson.accessToken);
               configPref.userNickname().put(baseJson.nickname);
                Util.startActivity(this, SafeQuestionActivity_.class);

//                Util.startActivity(CompleteInfoActivity.this, IndexActivity_.class);
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
            Util.Toast(CompleteInfoActivity.this, "验证码已发送，注意查收");


        }
    }


//    //listener  editview
//    class EditChangedListener implements TextWatcher {
//
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
////                Log.i(TAG, "输入文本之前的状态");
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//        }
//
//    }
}




