package com.yj.navigation.activity;

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
import com.yj.navigation.adapter.AdapterBankItemView;
import com.yj.navigation.base.MainApp;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.BankInfoJson;
import com.yj.navigation.object.BankListJson;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Constant;
import com.yj.navigation.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 2015/8/7.
 */
@EActivity(R.layout.bank_list_view)
public class BankListActivity extends BaseActivity   {


    @Pref
    ConfigPref_ configPref;


    //    @ViewById(R.id.hot_network_no_id)
    LinearLayout hotNetworkNoId;


    @ViewById(R.id.no_data_id)
    RelativeLayout no_data_id;


    private PullToRefreshListView pullToRefreshListView;

    private AdapterBankItemView adapterHomeDesignListView;

    private List<BankInfoJson> designRoomInfos;

    private RelativeLayout design_choose_line;

    int y = 0;
    int limit = 10;




    int pageNum = 1;

    int i = 0;


    @Click(R.id.left_title_line)
    void onLeftTitleLine() {

        finish();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



//        init();
    }

    @Override
    protected void initActivityName() {

    }

    @Override
    protected void setActivityBg() {

    }


    /**
     * 测试数据
     */
    public void getDataFromServer() {

//

        getJobListFromServerForMsg();


    }
    @AfterViews
    void init() {
        initUi();
    }

    void initUi() {

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listview_design);

        RelativeLayout main_title_id = (RelativeLayout) findViewById(R.id.main_title_id);
        main_title_id.setBackgroundColor(getResources().getColor(R.color.white_alpha80));

        ImageView left_title_icon = (ImageView) findViewById(R.id.left_title_icon);
        left_title_icon.setVisibility(View.VISIBLE);
        TextView left_title = (TextView) findViewById(R.id.left_title);
        left_title.setVisibility(View.VISIBLE);
        left_title.setText("添加银行卡");
        ImageView right_title_icon = (ImageView) findViewById(R.id.right_title_icon3);
        right_title_icon.setVisibility(View.GONE);
        Util.setBackgroundOfVersion(right_title_icon, getResources().getDrawable(R.drawable.new_add_code));

        TextView right_title = (TextView) findViewById(R.id.right_title);

        right_title.setVisibility(View.GONE);
//        right_title.setText("筛选");
//        right_title.setTextColor(getResources().getColor(R.color.white));


        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText("请选择银行");
//        title.setTextColor(getResources().getColor(R.color.white));
        View title_line_id = (View) findViewById(R.id.title_line_id);
        title_line_id.setVisibility(View.GONE);


        LinearLayout right_title_line = (LinearLayout) findViewById(R.id.right_title_line);

        right_title_line.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });



        designRoomInfos = new ArrayList<BankInfoJson>();

        adapterHomeDesignListView = new AdapterBankItemView(this, designRoomInfos);

        pullToRefreshListView.setAdapter(adapterHomeDesignListView);

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);// 设置底部下拉刷新模式
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
                        BankListActivity.this,
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

                //选中的银行卡 还code

                BankInfoJson bankInfoJson=designRoomInfos.get(position-1);

                MainApp mainApp=(MainApp) getApplicationContext();
                mainApp.choosebankInfoJson=bankInfoJson;

finish();
//                Util.startActivity(getActivity(), MyWorkDetailActivity_.class);

//                Intent intent = new Intent(BankListActivity.this, MyWorkDetailActivity_.class);
//                JobJson jobJson = (JobJson) adapterHomeDesignListView.getItem(position-1);
//                intent.putExtra("SN", jobJson.sn);
//                startActivity(intent);
//                Intent intent = new Intent(getActivity(), HomeAllDetailActivity_.class);
//				intent.putExtra(Constant.CASE_HOME_ID,adapterHomeDesignListView.getItem(position-1).id);
//                intent.putExtra(Constant.CASE_HOME_NAME,adapterHomeDesignListView.getItem(position-1).name);
//
//                startActivity(intent);

            }
        });

    }


    @Override
    public void onDestroy() {
        //退出activity前关闭菜单

        super.onDestroy();

    }

    //**********网络***************/


    //**********获取筛选的参数***************/


    @Override
    public void onResume() {
        super.onResume();

        pageNum = 1;

        getDataFromServer();
    }


    //**********获取筛选的后的list***************/
    FoxProgressbarInterface foxProgressbarInterface;

    public void getJobListFromServerForMsg() {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(BankListActivity.this, "加载中...");
//        String beginD = "20161201";
//        String endD = MyStringUtils.getNowTimeFormata(new Date());
//        String params = "0";//0全部 1 审核 2通过
//        Integer rows = 20;
        ProtocolUtil.mybankListFunction(BankListActivity.this, new MyJobListHandler(), configPref.userToken().get());


    }


    private class MyJobListHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            myJobListHandler(resp);
        }
    }


    public void myJobListHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            BankListJson baseJson = new Gson().fromJson(resp, BankListJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {

                //测试数据
//                baseJson.data = (ArrayList<JobJson>) setTestData();


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
