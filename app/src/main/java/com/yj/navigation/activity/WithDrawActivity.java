package com.yj.navigation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yj.navigation.R;
import com.yj.navigation.base.MainApp;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.component.FoxToastInterface;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.BankCardInfoJson;
import com.yj.navigation.object.BankCardListJson;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.BankCardCheck;
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
@EActivity(R.layout.withdraw_view)
public class WithDrawActivity extends BaseActivity {

    @Pref
    ConfigPref_ configPref;


    @ViewById(R.id.change_money_pwd_id)
    TextView change_money_pwd_id;
//    @ViewById(R.id.cash_money_id)
    TextView cash_money_id;


    @ViewById(R.id.name_info_id)
    TextView name_info_id;



    @Click(R.id.left_title_line)
    void onLeftTitleLine() {

        finish();

    }


    @Click(R.id.change_money_pwd_id)
    void onchange_money_pwd_id() {

        if(hadBindCard){
            Util.startActivity(this, MyBankCardListActivity_.class);

        }else {
            Intent intent = new Intent(this,BindCardActivity_.class);
            intent.putExtra("FromWithDraw",true);
            startActivity(intent);

        }

    }


    @Click(R.id.logining_btn_rel_id)
    void onLoginingBtnRelId() {


        String name_info_id_V = name_info_id.getText().toString();


        String cash_money_id_v = cash_money_id.getText().toString();


        if (name_info_id_V == null || name_info_id_V.equals("")) {

            Util.Toast(WithDrawActivity.this, "请输入金额",null);
            return;
        }else{
            Integer moneynum=Integer.valueOf(name_info_id_V);
            Integer havemoney=Integer.valueOf(cash_money_id_v);


            if(havemoney==0){
                Util.Toast(WithDrawActivity.this, "积分为0,无法提现",null);
                return;
            }
            if(moneynum==0||moneynum>havemoney){
                Util.Toast(WithDrawActivity.this, "提现金额不正确",null);
                return;
            }



    }

    //验证卡号

        String change_money_pwd_id_v = change_money_pwd_id.getText().toString();

        if (change_money_pwd_id_v == null || change_money_pwd_id_v.equals("")) {

            Util.Toast(WithDrawActivity.this, "请输入卡号",null);
            return;
        }else if(!BankCardCheck.luhmCheck(change_money_pwd_id_v).equals("true")){

            Util.Toast(WithDrawActivity.this, "银行卡不符合规则",null);
            return;


        }

//提现操作----fox
        applyCash(bankCardInfoJsonC.id, Integer.valueOf(name_info_id_V));
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
        main_title_id.setBackgroundColor(getResources().getColor(R.color.white_alpha80));

        ImageView left_title_icon = (ImageView) findViewById(R.id.left_title_icon);
        left_title_icon.setVisibility(View.VISIBLE);
        ImageView right_title_icon = (ImageView) findViewById(R.id.right_title_icon);
        right_title_icon.setVisibility(View.GONE);

        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.GONE);
        title.setText("提现");
        TextView left_title = (TextView) findViewById(R.id.left_title);
        left_title.setVisibility(View.VISIBLE);
        left_title.setText("返回");
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

        String useScore=getIntent().getStringExtra("useScore");
         cash_money_id = (TextView) findViewById(R.id.cash_money_id);

        cash_money_id.setText(useScore);


        uploadAvar();
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
BankCardInfoJson bankCardInfoJsonC=null;
    @Override
    protected void onResume() {
        super.onResume();

        MainApp mainApp=(MainApp) getApplicationContext();
if(mainApp.chooseBankCardInfoJson!=null){
    bankCardInfoJsonC=mainApp.chooseBankCardInfoJson;
    change_money_pwd_id.setText(bankCardInfoJsonC.bankcardno);
}
     //   uploadAvar();

    }


    //**********获取筛选的后的list***************/
    FoxProgressbarInterface foxProgressbarInterface;

    public void uploadAvar() {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");


        ProtocolUtil.myCardListFunction(this, new UploadAvarHandler(), configPref.userToken().get());


    }


    private class UploadAvarHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            uploadAvarHandler(resp);
        }
    }

boolean hadBindCard=false;
    public void uploadAvarHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            BankCardListJson baseJson = new Gson().fromJson(resp, BankCardListJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {



                if (baseJson.data != null && baseJson.data.size() > 0) {//列表

                    hadBindCard=true;

                    MainApp mainApp=(MainApp) getApplicationContext();
                    if(mainApp.chooseBankCardInfoJson==null) {
                        change_money_pwd_id.setText("请选择银行卡");
                    }

                }else{
                    hadBindCard=false;
                    change_money_pwd_id.setText("请添加银行卡");

                }



            }

        }
    }

    public void applyCash(Integer cardid,Integer cashnum) {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");


        ProtocolUtil.applycashFunction(this, new ApplyCashHandler(), configPref.userToken().get(),cardid,cashnum);


    }


    private class ApplyCashHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            applyCashHandler(resp);
        }
    }

    public void applyCashHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            Util.Toast(WithDrawActivity.this, "提现成功", new FoxToastInterface.FoxToastCallback() {
                @Override
                public void toastCloseCallbak() {
                    finish();
                }
            });




//            BankCardListJson baseJson = new Gson().fromJson(resp, BankCardListJson.class);
//            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {
//
//
//
//
//
//
//            }

        }
    }


}
