package com.yj.navigation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yj.navigation.R;
import com.yj.navigation.base.MainApp;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.BankCardInfoJson;
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
@EActivity(R.layout.bindcard_view)
public class BindCardActivity extends BaseActivity {

    @Pref
    ConfigPref_ configPref;


    @Click(R.id.left_title_line)
    void onLeftTitleLine() {

        finish();

    }


    @ViewById(R.id.choose_cardname_id)
    TextView choose_cardname_id;
    @ViewById(R.id.cardno_id)
    EditText cardno_id;

    @ViewById(R.id.truename_info_id)
    EditText truename_info_id;
    @ViewById(R.id.idcard_info_id)
    EditText idcard_info_id;

    String bankcode=null;

    @Click(R.id.logining_btn_rel_id)
    void onLoginingBtnRelId() {


        String mobile = cardno_id.getText().toString();

        if (mobile == null || mobile.equals("")) {

            Util.Toast(BindCardActivity.this, "请输入卡号");
            return;
        }

        String truename_info_id_V = truename_info_id.getText().toString();

        if (truename_info_id_V == null || truename_info_id_V.equals("")) {

            Util.Toast(BindCardActivity.this, "请输入真实姓名");
            return;
        }
        String idcard_info_id_v = idcard_info_id.getText().toString();

        if (idcard_info_id_v == null || idcard_info_id_v.equals("")) {

            Util.Toast(BindCardActivity.this, "请输入身份证号");
            return;
        }

        String choose_cardname_id_V = choose_cardname_id.getText().toString();

        if (choose_cardname_id_V == null &&choose_cardname_id_V.contains("选择")) {

            Util.Toast(BindCardActivity.this, "请选择银行");
            return;
        }


        bindCard(choose_cardname_id_V,bankcode,truename_info_id_V,mobile,idcard_info_id_v);

    }


    @Click(R.id.choose_cardname_id)
    void onchoose_cardname_id() {
        Util.startActivity(BindCardActivity.this, BankListActivity_.class);
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

        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText("添加银行卡");
        TextView left_title = (TextView) findViewById(R.id.left_title);
        left_title.setVisibility(View.VISIBLE);
        left_title.setText("选择银行卡");
//        title.setTextColor(getResources().getColor(R.color.line_hot_all));
        View title_line_id = (View) findViewById(R.id.title_line_id);
        title_line_id.setVisibility(View.GONE);


        LinearLayout right_title_line = (LinearLayout) findViewById(R.id.right_title_line);

        right_title_line.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });
        FromWithDraw=getIntent().getBooleanExtra("FromWithDraw",false);
    }

    boolean FromWithDraw=false;
    @AfterViews
    void init() {
        initUi();
    }


    @Override
    protected void initActivityName() {
        activityName = BindCardActivity.class.getName();
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
        MainApp mainApp=(MainApp) getApplicationContext();
if(mainApp.choosebankInfoJson!=null){

    choose_cardname_id.setText(mainApp.choosebankInfoJson.bankname);
    bankcode=mainApp.choosebankInfoJson.bankcode;
}


    }


    //**********获取筛选的后的list***************/
    FoxProgressbarInterface foxProgressbarInterface;

    public void loginFromServerForMsg(String mobile, String code) {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");

        String clientVersion = Util.getAppVersionName(this);

//        ProtocolUtil.loginFunction(this, new LoginInfoHandler(), mobile, code, "android", clientVersion);


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

BankCardInfoJson bankCardInfoJson=null;
    public void bindCard(String bankname,String bankcode,String truename,String cardno,String idcard) {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");

        bankCardInfoJson=new BankCardInfoJson();
        bankCardInfoJson.bankcardno=cardno;
        bankCardInfoJson.bankname=bankname;
        bankCardInfoJson.bankcode=bankcode;
        bankCardInfoJson.realname=truename;
        bankCardInfoJson.cardno=idcard;

        ProtocolUtil.bindCardFunction(this, new GetPhoneMsginfoHandler(), configPref.userToken().get(), bankcode
                , bankname, cardno, truename,idcard);


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
            Util.Toast(BindCardActivity.this, "绑定成功");
            if(FromWithDraw){
                MainApp mainApp=(MainApp) getApplicationContext();
                mainApp.chooseBankCardInfoJson=bankCardInfoJson;
            }

finish();

        }
    }

}
