package com.yj.navigation.fragment;

/**
 * Created by foxcen on 16/7/29.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yj.navigation.R;
import com.yj.navigation.activity.MyWorkDetailActivity_;
import com.yj.navigation.adapter.AdapterWorkAllListView;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.JobImageJson;
import com.yj.navigation.object.JobJson;
import com.yj.navigation.object.JobListJson;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Constant;
import com.yj.navigation.util.MyStringUtils;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by foxcen on 16/7/29.
 */
@EFragment(R.layout.work_all_view)

public class AllFragment extends Fragment {


    @Pref
    ConfigPref_ configPref;


    //    @ViewById(R.id.hot_network_no_id)
    LinearLayout hotNetworkNoId;


    @ViewById(R.id.no_data_id)
    RelativeLayout no_data_id;


    private PullToRefreshListView pullToRefreshListView;

    private AdapterWorkAllListView adapterHomeDesignListView;

    private List<JobJson> designRoomInfos;

    private RelativeLayout design_choose_line;

    int y = 0;
    int limit = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View chatView = inflater.inflate(R.layout.work_all_view, container, false);


        pullToRefreshListView = (PullToRefreshListView) chatView.findViewById(R.id.listview_design);

//        mDropDownMenu = (DropDownMenu) chatView.findViewById(R.id.dropDownMenu);
//        hotNetworkNoId = (LinearLayout) chatView.findViewById(R.id.hot_network_no_id);

//        initView(inflater);
        init();
//        getDataFromServerForFilter();
        return chatView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        init();
    }

    int pageNum = 1;

    int i = 0;

    /**
     * 测试数据
     */
    public void getDataFromServer() {

//

        getJobListFromServerForMsg();


    }


    void init() {
        designRoomInfos = new ArrayList<JobJson>();

        adapterHomeDesignListView = new AdapterWorkAllListView(getActivity(), designRoomInfos);

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
                        getActivity(),
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

//                Util.startActivity(getActivity(), MyWorkDetailActivity_.class);

                Intent intent = new Intent(getActivity(), MyWorkDetailActivity_.class);
                JobJson jobJson = (JobJson) adapterHomeDesignListView.getItem(position-1);
                intent.putExtra("SN", jobJson.sn);
                startActivity(intent);
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
        foxProgressbarInterface.startProgressBar(getActivity(), "加载中...");
        String beginD = "20161201";
        String endD = MyStringUtils.getNowTimeFormata(new Date());
        String params = "0";//0全部 1 审核 2通过
        Integer rows = 20;
        ProtocolUtil.myJobListFunction(getActivity(), new MyJobListHandler(), configPref.userToken().get(), beginD
                , endD, params, null, pageNum, rows);//devno 空表示所有


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


            JobListJson baseJson = new Gson().fromJson(resp, JobListJson.class);
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

    public List<JobJson> setTestData() {
        List<JobJson> jobJsons = new ArrayList<JobJson>();
        for (int i = 0; i < 10; i++) {
            JobJson jobJson = new JobJson();
            jobJson.address = "金桥附近" + i;
            jobJson.province = "上海";
            jobJson.city = "上海";
            jobJson.area = "浦东新区";
            jobJson.devno = "123456" + i;
            jobJson.takedt = "2016-12-01 12:23:3" + i;
            jobJson.state = "一审";
            jobJson.state = "一审";

            jobJson.remoteBaseUrl = "http://img00.hc360.com/security/";

            List<JobImageJson> imageAvarJsons = new ArrayList<JobImageJson>();
            for (int j = 0; j < 2; j++) {
                JobImageJson jobImageJson = new JobImageJson();
                jobImageJson.bigPicUrl = "201304/201304030954539941.jpg";
                jobImageJson.minPicUrl = "201304/201304030954539941.jpg";
                imageAvarJsons.add(jobImageJson);
            }
            jobJson.images = (ArrayList<JobImageJson>) imageAvarJsons;

            jobJsons.add(jobJson);

        }

        return jobJsons;
    }
}




