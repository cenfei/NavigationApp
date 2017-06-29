package com.yj.navigation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yj.navigation.R;
import com.yj.navigation.adapter.AdapterWorkDetailListView;
import com.yj.navigation.base.MainApp;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.JobDetailJson;
import com.yj.navigation.object.JobImageJson;
import com.yj.navigation.object.JobJson;
import com.yj.navigation.object.OpesJson;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Constant;
import com.yj.navigation.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 2015/8/7.
 */
@EActivity(R.layout.mywork_detail_view)
public class MyWorkDetailActivity extends BaseActivity {

    @Pref
    ConfigPref_ configPref;
    private PullToRefreshListView pullToRefreshListView;

    private AdapterWorkDetailListView adapterHomeDesignListView;

    private List<OpesJson> designRoomInfos;


    int y = 0;
    int limit = 10;

    @Click(R.id.left_title_line)
    void onLeftTitleLine() {

        finish();

    }
    @Click(R.id.apply_report_id)
    void onapply_report_id() {
        Intent intent = new Intent(this, ShowVideoActivity.class);
        intent.putExtra("FromVideoSecondFragment", true);
        startActivity(intent);

    }


    @Click(R.id.logining_btn_rel_id)
    void onLoginingBtnRelId() {


        // Util.startActivity(this, IndexActivity_.class);

        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.design_personal);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        initUi();


    }

TextView apply_report_id;
    public void initUi() {

        RelativeLayout main_title_id = (RelativeLayout) findViewById(R.id.main_title_id);
        main_title_id.setBackgroundColor(getResources().getColor(R.color.white_alpha80));

        ImageView left_title_icon = (ImageView) findViewById(R.id.left_title_icon);
        left_title_icon.setVisibility(View.VISIBLE);
        TextView left_title = (TextView) findViewById(R.id.left_title);
        left_title.setVisibility(View.VISIBLE);
        left_title.setText("工单");
        ImageView right_title_icon = (ImageView) findViewById(R.id.right_title_icon);
        right_title_icon.setVisibility(View.GONE);

        TextView right_title = (TextView) findViewById(R.id.right_title);

        right_title.setVisibility(View.GONE);
//        right_title.setTextColor(getResources().getColor(R.color.white));


        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        String  snno=getIntent().getStringExtra("SN");
        title.setText(snno);
//        title.setTextColor(getResources().getColor(R.color.white));
        View title_line_id = (View) findViewById(R.id.title_line_id);
        title_line_id.setVisibility(View.GONE);


        LinearLayout right_title_line = (LinearLayout) findViewById(R.id.right_title_line);

        right_title_line.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });


         apply_report_id = (TextView) findViewById(R.id.apply_report_id);

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listview_design);

        sn = getIntent().getStringExtra("SN");
        initData();
    }

    private String sn = null;

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
        int pageNum = 1;

        getDataFromServer();

    }


    int pageNum = 1;

    int i = 0;

    /**
     * 测试数据
     */
    public void getDataFromServer() {

//        for(int i=0;i<4;i++){
//
//            designRoomInfos.add("武定路附近");
//        }

        getJobDetailForMsg(sn);

    }


    void initData() {
        designRoomInfos = new ArrayList<OpesJson>();

        adapterHomeDesignListView = new AdapterWorkDetailListView(this, designRoomInfos,configPref.userHeadImg().get());

        pullToRefreshListView.setAdapter(adapterHomeDesignListView);

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);// 设置底部下拉刷新模式
        pullToRefreshListView.getLoadingLayoutProxy(true, false)
                .setLastUpdatedLabel("下拉刷新");
        pullToRefreshListView.getLoadingLayoutProxy(true, false)
                .setPullLabel("");
        pullToRefreshListView.getLoadingLayoutProxy(true, false)
                .setRefreshingLabel("正在刷新");
        pullToRefreshListView.getLoadingLayoutProxy(true, false)
                .setReleaseLabel("放开以刷新");

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(
                        MyWorkDetailActivity.this,
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);

                y = 0;
                getDataFromServer();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (adapterHomeDesignListView.mPersonal.size() < limit) {
                    pageNum = 1;
                } else {
                    pageNum++;

                }
                if (!refreshView.isHeaderShown()) {
                    y = adapterHomeDesignListView.mPersonal.size();
                } else {
                    // 得到上一次滚动条的位置，让加载后的页面停在上一次的位置，便于用户操作
                    // y = adapter.list.size();

                }
                getDataFromServer();
            }
        });

        // 点击详单
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Intent intent = new Intent(getActivity(), HomeAllDetailActivity_.class);
//				intent.putExtra(Constant.CASE_HOME_ID,adapterHomeDesignListView.getItem(position-1).id);
//                intent.putExtra(Constant.CASE_HOME_NAME,adapterHomeDesignListView.getItem(position-1).name);
//
//                startActivity(intent);

            }
        });

    }


    //**********获取筛选的后的list***************/
    FoxProgressbarInterface foxProgressbarInterface;

    public void getJobDetailForMsg(String sn) {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");


        ProtocolUtil.myJobDetailFunction(this, new MyJobDetailHandler(), configPref.userToken().get(), sn);


    }


    private class MyJobDetailHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            myJobDetailHandler(resp);
        }
    }

private String  jobImageUrl=null;
    public void myJobDetailHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            JobDetailJson baseJson = new Gson().fromJson(resp, JobDetailJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {


                List<JobImageJson> jobImageJsonList=baseJson.images;
                JobImageJson jobImageJsonOk=null;
                if(jobImageJsonList!=null) {
                    for (JobImageJson jobImageJson : jobImageJsonList) {
                        jobImageJsonOk = jobImageJson;
                        break;

                    }

                    MainApp mainApp = (MainApp) getApplicationContext();
                    mainApp.jobImageJsonList = baseJson.images;
                    mainApp.remoteBaseUrl = baseJson.remoteBaseUrl;

                    JobJson jobJson=new JobJson();
                    jobJson.sn=baseJson.sn;
                    jobJson.address=baseJson.address;
                    jobJson.takedt=baseJson.takedt;
                    jobJson.state=baseJson.state;
                    mainApp.jobJson=jobJson;




                    jobImageUrl = baseJson.remoteBaseUrl + jobImageJsonOk.bigPicUrl;
                    adapterHomeDesignListView.setJobImageUrl(jobImageUrl);






                }

                //status 2  4  6的时候需要审核  其他不用  7的时候消失
                if(!TextUtils.isEmpty(baseJson.state)){
                    Integer stateint=Integer.valueOf(baseJson.state);
                    if(stateint==2||stateint==4||stateint==6){
                        apply_report_id.setClickable(true);
                        apply_report_id.setBackgroundResource(R.drawable.rounded_apply_use);
                    }
                    else if(stateint==7){
                        apply_report_id.setVisibility(View.GONE);
                    }
                    else{
                        apply_report_id.setClickable(false);
                        apply_report_id.setBackgroundResource(R.drawable.rounded_apply);


                    }



                }
                if (baseJson.opes != null && baseJson.opes.size() > 0) {//列表

                    if (pageNum == 1) {
                        designRoomInfos.clear();

                        designRoomInfos.add(new OpesJson());


                        designRoomInfos.addAll(baseJson.opes);
                    } else {
                        designRoomInfos.addAll(baseJson.opes);
                    }

                }


                if (designRoomInfos.size() <= 0 && pageNum == 1) {
                    pullToRefreshListView.setVisibility(View.GONE);
                } else {

                    adapterHomeDesignListView.notifyDataSetChanged();
                    // Call onRefreshComplete when the list has been refreshed.
                    pullToRefreshListView.onRefreshComplete();
                    pullToRefreshListView.getRefreshableView().setSelection(y);


                }

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
            Util.Toast(MyWorkDetailActivity.this, "验证码已发送，注意查收",null);


        }
    }

}
