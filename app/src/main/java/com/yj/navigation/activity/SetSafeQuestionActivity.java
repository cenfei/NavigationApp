package com.yj.navigation.activity;

import android.app.Dialog;
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
import com.yj.navigation.gridpasswordview.GridPasswordView;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.BaseJson;
import com.yj.navigation.object.Question;
import com.yj.navigation.object.QuestionJson;
import com.yj.navigation.object.RegisterJson;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Constant;
import com.yj.navigation.util.FoxShuashuaPayPasswordInterface;
import com.yj.navigation.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhang on 2015/8/7.
 */
@EActivity(R.layout.set_safe_question_view)
public class SetSafeQuestionActivity extends BaseActivity {

    @Pref
    ConfigPref_ configPref;


    @Click(R.id.left_title_line)
    void onLeftTitleLine() {

        finish();

    }

    @Click(R.id.right_title)
    void onRightTitleLine() {

        //跳到 第三步 设置提现密码
        setSafePwd();
//        finish();

    }

    String mobile;

    TextView question_conntent_one_id,question_conntent_two_id,question_conntent_three_id;

    @Click(R.id.logining_btn_rel_id)
    void onLoginingBtnRelId() {


        //没有安全问题的时候----先获取填写的安全问题题目
        String   answer1Qes = question_conntent_one_id.getText().toString();
        if (answer1Qes == null || answer1Qes.equals("")) {
            Util.Toast(SetSafeQuestionActivity.this,  "问题1不能为空",null);
            return;
        }
        String   answer2Qes = question_conntent_two_id.getText().toString();
        if (answer2Qes == null || answer2Qes.equals("")) {
            Util.Toast(SetSafeQuestionActivity.this,  "问题2不能为空",null);
            return;
        }
        String   answer3Qes = question_conntent_three_id.getText().toString();
        if (answer3Qes == null || answer3Qes.equals("")) {
            Util.Toast(SetSafeQuestionActivity.this,  "问题3不能为空",null);
            return;
        }


        if (questionList == null || questionList.size() == 0) {
            for (int j = 0; j < 3; j++) {
                Question question = new Question();

                question.id = 1;
                if (j == 0) {
                    question.question = answer1Qes;
                } else if (j == 1) {
                    question.question = answer2Qes;

                } else {
                    question.question = answer3Qes;

                }
                questionList.add(question);
            }

        }

        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        String answer1 = "";
        String answer2 = "";
        String answer3 = "";
        int i = 1;
        for (Question question : questionList) {
            if (i == 1) {
                answer1 = answer_one_id.getText().toString();
                if (answer1 == null || answer1.equals("")) {
                    Util.Toast(SetSafeQuestionActivity.this, question.question + "不能为空",null);
                    return;
                } else {
                    Map<String, String> map = new HashMap<String, String>();
                    if(fromChangeSafePwdChooseActivityBool){
                        map.put("id", question.id+"");

                    }else {
                        map.put("question", question.question);
                    }
                    map.put("answer", answer1);
                    listMap.add(map);
                }

            }
            if (i == 2) {
                answer2 = answer_two_id.getText().toString();
                if (answer2 == null || answer2.equals("")) {
                    Util.Toast(SetSafeQuestionActivity.this, question.question + "不能为空",null);
                    return;
                } else {
                    Map<String, String> map = new HashMap<String, String>();
                    if(fromChangeSafePwdChooseActivityBool){
                        map.put("id", question.id+"");

                    }else {
                        map.put("question", question.question);
                    }
                    map.put("answer", answer2);
                    listMap.add(map);

                }

            }
            if (i == 3) {
                answer3 = answer_three_id.getText().toString();
                if (answer3 == null || answer3.equals("")) {
                    Util.Toast(SetSafeQuestionActivity.this, question.question + "不能为空",null);
                    return;
                } else {
                    Map<String, String> map = new HashMap<String, String>();
                    if(fromChangeSafePwdChooseActivityBool){
                        map.put("id", question.id+"");

                    }else {
                        map.put("question", question.question);
                    }
                    map.put("answer", answer3);
                    listMap.add(map);

                }

            }

            i++;
        }
        if (fromChangeSafePwdChooseActivityBool) {
            checkQuestionListServerForMsg(listMap);
        } else {

            setQuestionListServerForMsg(listMap);
        }

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
        title.setText("安全问题");
        title.setTextColor(getResources().getColor(R.color.line_hot_all));
        View title_line_id = (View) findViewById(R.id.title_line_id);
        title_line_id.setVisibility(View.GONE);
        TextView right_title = (TextView) findViewById(R.id.right_title);
        right_title.setText("跳过");
        right_title.setVisibility(View.VISIBLE);
        LinearLayout right_title_line = (LinearLayout) findViewById(R.id.right_title_line);

        right_title_line.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });
        question_one_rel_id = (RelativeLayout) findViewById(R.id.question_one_rel_id);
        question_one_rel_id.setVisibility(View.GONE);
        question_two_rel_id = (RelativeLayout) findViewById(R.id.question_two_rel_id);
        question_three_rel_id = (RelativeLayout) findViewById(R.id.question_three_rel_id);
        question_two_rel_id.setVisibility(View.GONE);
        question_three_rel_id.setVisibility(View.GONE);

        question_one_id = (TextView) findViewById(R.id.question_one_id);
        question_two_id = (TextView) findViewById(R.id.question_two_id);

        question_three_id = (TextView) findViewById(R.id.question_three_id);
        question_conntent_one_id = (TextView) findViewById(R.id.question_conntent_one_id);
        question_conntent_two_id = (TextView) findViewById(R.id.question_conntent_two_id);

        question_conntent_three_id = (TextView) findViewById(R.id.question_conntent_three_id);



        answer_one_id = (EditText) findViewById(R.id.answer_one_id);
        answer_two_id = (EditText) findViewById(R.id.answer_two_id);
        answer_three_id = (EditText) findViewById(R.id.answer_three_id);
        TextView left_title = (TextView) findViewById(R.id.left_title);

        fromChangeSafePwdChooseActivityBool = getIntent().getBooleanExtra("FromChangeSafePwdChooseActivity", false);
        if (fromChangeSafePwdChooseActivityBool) {

            left_title.setVisibility(View.VISIBLE);
            left_title.setText("个人中心");

            right_title.setVisibility(View.GONE);
        }else{
            left_title.setVisibility(View.VISIBLE);
            left_title.setText("返回");
        }

//        getQuestionListServerForMsg();
    }

    private boolean fromChangeSafePwdChooseActivityBool = false;
    RelativeLayout question_one_rel_id, question_two_rel_id, question_three_rel_id;
    TextView question_one_id, question_two_id, question_three_id;
    EditText answer_one_id, answer_two_id, answer_three_id;

    @AfterViews
    void init() {
        initUi();
    }


    @Override
    protected void initActivityName() {
        activityName = SetSafeQuestionActivity.class.getName();
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


    public void  setSafePwd() {

        Util.Toast(SetSafeQuestionActivity.this, "请设置提现密码",null);
        FoxShuashuaPayPasswordInterface foxShuashuaPayPasswordInterface = new FoxShuashuaPayPasswordInterface();
        foxShuashuaPayPasswordInterface.startProgressBar(this, "", new FoxShuashuaPayPasswordInterface.CallBackPasswordDialog() {
            @Override
            public void handleDialogResultOk(GridPasswordView gpv_normal_paypassword, String pwd, Dialog dialog) {

                //处理网络修改密码
//                changeSafePwd(pwd, dialog);
                updateSafePwd(pwd, dialog);


            }


        });
    }

    Dialog dialogD = null;

    //修改安全密码
    public void updateSafePwd(String pwdsafe, Dialog dialog) {
        dialogD = dialog;
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");

        ProtocolUtil.setSafePwdFunction(this, new UpdateSafePwdHandler(), configPref.userToken().get(), pwdsafe);


    }


    private class UpdateSafePwdHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            updateSafePwdHandler(resp);
        }
    }


    public void updateSafePwdHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {

            BaseJson baseJson = new Gson().fromJson(resp, BaseJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {
                if (dialogD != null) dialogD.dismiss();
                Util.startActivity(SetSafeQuestionActivity.this, IndexActivity_.class);


                Util.Toast(this, "安全密码设置成功", new FoxToastInterface.FoxToastCallback() {
                    @Override
                    public void toastCloseCallbak() {
                        finish();
                    }
                });
            }
        }
    }

    //**********获取筛选的后的list***************/
    FoxProgressbarInterface foxProgressbarInterface;

    //获取安全问题列表
    public void getQuestionListServerForMsg() {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");


        ProtocolUtil.queryQustionFunction(this, new GetQueryQuestionHandler(), configPref.userToken().get());


    }


    private class GetQueryQuestionHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            getQueryQuestionHandler(resp);
        }
    }

    List<Question> questionList = new ArrayList<Question>();

    public void getQueryQuestionHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            QuestionJson baseJson = new Gson().fromJson(resp, QuestionJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {

                questionList = baseJson.data;

                //添加测试数据----
                //********************
                if (questionList == null || questionList.size() == 0) {
                    for (int j = 0; j < 3; j++) {
                        Question question = new Question();

                        question.id = 1;
                        if (j == 0) {
                            question.question = "您的出生地";
                        } else if (j == 1) {
                            question.question = "您最喜欢的人";

                        } else {
                            question.question = "您的初中学校";

                        }
                        questionList.add(question);
                    }

                }

                //********************


                int i = 1;
                for (Question question : questionList) {
                    if (i == 1) {
                        question_one_rel_id.setVisibility(View.VISIBLE);
                        question_one_id.setText("问题一："+question.question);
                    } else if (i == 2) {
                        question_two_rel_id.setVisibility(View.VISIBLE);
                        question_two_id.setText("问题二："+question.question);


                    } else if (i == 3) {

                        question_three_rel_id.setVisibility(View.VISIBLE);
                        question_three_id.setText("问题三："+question.question);


                    }
                    i++;
                }


            }

        }
    }


    //设置安全问题
    public void setQuestionListServerForMsg(List<Map<String, String>> maplist) {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");


        ProtocolUtil.updateQustionFunction(this, new UpdateQuestionHandler(), configPref.userToken().get(), maplist);


    }


    private class UpdateQuestionHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            updateQuestionHandler(resp);
        }
    }


    public void updateQuestionHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            BaseJson baseJson = new Gson().fromJson(resp, BaseJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {

                setSafePwd();


            }

        }
    }


    //设置安全问题
    public void checkQuestionListServerForMsg(List<Map<String, String>> maplist) {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");


        ProtocolUtil.checkQustionFunction(this, new CheckQuestionHandler(), configPref.userToken().get(), maplist);


    }


    private class CheckQuestionHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            checkQuestionHandler(resp);
        }
    }

    private String questionKey = null;

    public void checkQuestionHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            RegisterJson baseJson = new Gson().fromJson(resp, RegisterJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {
                questionKey = baseJson.key;
                resetSafePwdWithDialog();


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
            Util.Toast(SetSafeQuestionActivity.this, "验证码已发送，注意查收",null);


        }
    }

    public void resetSafePwdWithDialog() {
        FoxShuashuaPayPasswordInterface foxShuashuaPayPasswordInterface = new FoxShuashuaPayPasswordInterface();
        foxShuashuaPayPasswordInterface.startProgressBar(this, "", new FoxShuashuaPayPasswordInterface.CallBackPasswordDialog() {
            @Override
            public void handleDialogResultOk(GridPasswordView gpv_normal_paypassword, String pwd, Dialog dialog) {

                //处理网络修改密码
                resetSafePwd(pwd, dialog);
            }


        });

    }

    //修改安全密码
    public void resetSafePwd(String pwdsafe, Dialog dialog) {
        dialogD = dialog;
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");

        ProtocolUtil.resetSafePwdFunction(this, new ResetSafePwdHandler(), configPref.userToken().get(), questionKey, pwdsafe);


    }


    private class ResetSafePwdHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            resetSafePwdHandler(resp);
        }
    }


    public void resetSafePwdHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {

            BaseJson baseJson = new Gson().fromJson(resp, BaseJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {
                if (dialogD != null) dialogD.dismiss();


                Util.Toast(this, "安全密码设置成功", new FoxToastInterface.FoxToastCallback() {
                    @Override
                    public void toastCloseCallbak() {
                        finish();
                    }
                });

            }
        }
    }
}
