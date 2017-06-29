package com.yj.navigation.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.yj.navigation.object.RegisterJson;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Constant;
import com.yj.navigation.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by zhang on 2015/8/7.
 */
@EActivity(R.layout.register_view)
public class RegisterActivity extends BaseActivity {

    @Pref
    ConfigPref_ configPref;

    @ViewById(R.id.mobile_info_id)
    EditText mobileInfoId;

    @ViewById(R.id.get_code_id)
    TextView getCodeId;


    @ViewById(R.id.code_msg_id)
    EditText codeMsgId;


    @Click(R.id.left_title_line)
    void onLeftTitleLine() {

        finish();

    }

    String mobile;

    @Click(R.id.logining_btn_rel_id)
    void onLoginingBtnRelId() {

        mobile = mobileInfoId.getText().toString();

        String code = codeMsgId.getText().toString();


        if (mobile != null && !mobile.equals("") && code != null && !code.equals("")) {

//            Util.startActivity(this,CompleteInfoActivity_.class);
//            finish();
            registerPhoneAndCode(mobile, code);

        } else {

            Util.Toast(RegisterActivity.this, "手机号或者验证码不能为空",null);

        }


    }

    @Click(R.id.get_code_id)
    void onGetCodeId() {
        String mobile = mobileInfoId.getText().toString();

        if (mobile != null && !mobile.equals("")) {
            writehandler.post(runnable);
            //定时器
            registerFromServerForMsg(mobile);


        } else {
            Util.Toast(RegisterActivity.this, "请先填写手机号码",null);

        }


    }


    final Handler writehandler = new Handler();

    int process = 1;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情
            if (process < 60) {
                getCodeId.setBackgroundColor(getResources().getColor(R.color.work_detail_btn));

                getCodeId.setText((60 - process) + "s");
                getCodeId.setClickable(false);
                writehandler.postDelayed(this, 1000);
                process = process + 1;
            } else {
                getCodeId.setBackgroundColor(getResources().getColor(R.color.work_detail_btn_use));

                getCodeId.setText("重新获取");
                getCodeId.setClickable(true);

                writehandler.removeCallbacks(runnable);
            }
        }
    };

    @Click(R.id.can_not_get_code_id)
    void onCanNotGetCodeId() {

        Util.Toast(RegisterActivity.this, "无法获取验证码",null);

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
        title.setText("注册");
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


//        TextView mine_name_id = (TextView) findViewById(R.id.mine_name_id);
//        mine_name_id.setText(configPref.userName().get()+"");

    }

    @AfterViews
    void init() {
        initUi();
    }


    @Override
    protected void initActivityName() {
        activityName = RegisterActivity.class.getName();
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

    public void registerFromServerForMsg(String mobile) {
        foxProgressbarInterface=new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this,"加载中...");


        ProtocolUtil.getPhoneMsg(this, new GetPhoneMsgHandler(), mobile);


    }


    private class GetPhoneMsgHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            getPhoneMsgHandler(resp);
        }
    }


    public void getPhoneMsgHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        Log.d("getPhoneMsgHandler",resp);
        if (resp != null && !resp.equals("")) {
            Util.Toast(RegisterActivity.this, "验证码已发送，注意查收",null);



        }
    }


    public void registerPhoneAndCode(String mobile,String code) {
        foxProgressbarInterface=new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this,"加载中...");

        ProtocolUtil.registerFunction(this, new RegisterMsginfoHandler(), mobile,code);


    }


    private class RegisterMsginfoHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            registerMsginfoHandler(resp);
        }
    }


    public void registerMsginfoHandler(String resp) {
foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            RegisterJson baseJson = new Gson().fromJson(resp, RegisterJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {

                configPref.userMobile().put(mobile);
                //保存key
                configPref.userKey().put(baseJson.key);
                Util.startActivity(RegisterActivity.this, CompleteInfoActivity_.class);
                finish();
            }
        }
    }

}
