package com.yj.navigation.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.yj.navigation.adapter.AdapterMineAccountListView;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.AccountDetailJson;
import com.yj.navigation.object.AccountInFOJson;
import com.yj.navigation.object.AccountListJson;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Constant;
import com.yj.navigation.util.MyStringUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhang on 2015/8/7.
 */
@EActivity(R.layout.my_count_view)
public class MyAccountActivity extends BaseActivity {

    @Pref
    ConfigPref_ configPref;

    @ViewById(R.id.no_data_id)
    RelativeLayout no_data_id;
    private PullToRefreshListView pullToRefreshListView;

    private AdapterMineAccountListView adapterHomeDesignListView;

    private List<AccountDetailJson> designRoomInfos;

    TextView mine_integral;
    int y = 0;
    int limit =10;

    @Click(R.id.left_title_line)
    void onLeftTitleLine() {

        finish();

    }



    @Click(R.id.logining_btn_rel_id)
    void onLoginingBtnRelId() {
        Intent intent = new Intent(this, WithDrawActivity_.class);
        intent.putExtra("useScore",baseJson.useScore);
        startActivity(intent);

//         Util.startActivity(this, WithDrawActivity_.class);

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
       // main_title_id.setBackgroundColor(getResources().getColor(R.color.white));

        ImageView left_title_icon = (ImageView) findViewById(R.id.left_title_icon);
        left_title_icon.setVisibility(View.GONE);

        ImageView left_title_icon2 = (ImageView) findViewById(R.id.left_title_icon2);
        left_title_icon2.setVisibility(View.VISIBLE);




        ImageView right_title_icon = (ImageView) findViewById(R.id.right_title_icon);
        right_title_icon.setVisibility(View.GONE);

        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.GONE);
       // title.setText("详情");
        title.setTextColor(getResources().getColor(R.color.white));
        View title_line_id = (View) findViewById(R.id.title_line_id);
        title_line_id.setVisibility(View.GONE);


        LinearLayout right_title_line = (LinearLayout) findViewById(R.id.right_title_line);

        right_title_line.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });
         mine_integral = (TextView) findViewById(R.id.mine_integral);


        pullToRefreshListView=(PullToRefreshListView)     findViewById(R.id.listview_design);

        initData();
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
        pageNum = 1;
        initDataFromServerForMsg();
    }


    int pageNum = 1;

    int i = 0;

    /**
     * 测试数据
     */
    public void getDataFromServer(){

//        for(int i=0;i<4;i++){
//
//            designRoomInfos.add("武定路附近");
//        }
        QueryAccountDetailListFromServerForMsg();


    }



    void initData() {
        designRoomInfos = new ArrayList<AccountDetailJson>();

        adapterHomeDesignListView = new AdapterMineAccountListView(this, designRoomInfos);

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
                        MyAccountActivity.this,
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);

                y=0;
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

    public void initDataFromServerForMsg() {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");


        ProtocolUtil.queryAccountInfoFunction(this, new QueryAccountInfoHandler(), configPref.userToken().get());


    }


    private class QueryAccountInfoHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            queryAccountInfoHandler(resp);
        }
    }


    public void queryAccountInfoHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


             baseJson = new Gson().fromJson(resp, AccountInFOJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {
                mine_integral.setText(((baseJson.totalScore==null||"".equals(baseJson.totalScore))?"¥0.00":"¥"+MyStringUtils.getDoubleDecimal(Double.valueOf(baseJson.totalScore),"0.00"))+"");

                getDataFromServer();
            }

        }
    }
    AccountInFOJson baseJson=null;

    public void QueryAccountDetailListFromServerForMsg() {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");


        String nowDate="20161202";
        String endDate = MyStringUtils.getNowTimeFormata(new Date());

        ProtocolUtil.queryAccountDetailListFunction(this, new QueryAccountDetailListFunctionHandler(), configPref.userToken().get(),null,nowDate,endDate);


    }


    private class QueryAccountDetailListFunctionHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            queryAccountDetailListFunctionHandler(resp);
        }
    }


    public void queryAccountDetailListFunctionHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {
//            Util.Toast(MyAccountActivity.this, "验证码已发送，注意查收");
            AccountListJson baseJson = new Gson().fromJson(resp, AccountListJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {

                if (baseJson.data != null && baseJson.data.size() > 0) {//列表

                    if (pageNum == 1) {
                        designRoomInfos.clear();
                        designRoomInfos.addAll(baseJson.data);
                    } else {
                        designRoomInfos.addAll(baseJson.data);
                    }

                }


                if (designRoomInfos.size() <= 0 && pageNum == 1) {
                    no_data_id.setVisibility(View.VISIBLE);
                    pullToRefreshListView.setVisibility(View.GONE);
                } else {
                    no_data_id.setVisibility(View.GONE);
                    pullToRefreshListView.setVisibility(View.VISIBLE);


                    adapterHomeDesignListView.notifyDataSetChanged();
                    // Call onRefreshComplete when the list has been refreshed.
                    pullToRefreshListView.onRefreshComplete();
                    pullToRefreshListView.getRefreshableView().setSelection(y);


                }



            }

        }
    }

}
