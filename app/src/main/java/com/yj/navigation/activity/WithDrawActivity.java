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
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.BankCardInfoJson;
import com.yj.navigation.object.BankCardListJson;
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
@EActivity(R.layout.withdraw_view)
public class WithDrawActivity extends BaseActivity {

    @Pref
    ConfigPref_ configPref;


    @ViewById(R.id.change_money_pwd_id)
    TextView change_money_pwd_id;

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
                    change_money_pwd_id.setText("请选择银行卡");


                }else{
                    hadBindCard=false;
                    change_money_pwd_id.setText("请添加银行卡");

                }



            }

        }
    }




}
