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
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yj.navigation.R;
import com.yj.navigation.activity.ShowVideoActivity;
import com.yj.navigation.adapter.NewAdapterUpVideoListView;
import com.yj.navigation.base.MainApp;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
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
@EFragment(R.layout.new_upvideo_list_view)

public class NewUploadVideoFragment extends Fragment {


    @Pref
    ConfigPref_ configPref;


    @ViewById(R.id.no_data_id)
    RelativeLayout no_data_id;

    //    @ViewById(R.id.hot_network_no_id)
    LinearLayout hotNetworkNoId;

    private PullToRefreshListView pullToRefreshListView;

    private NewAdapterUpVideoListView adapterHomeDesignListView;

    int position = 0;
//    private List<JobJson> imageInfoListInit;

    TextView img_position, img_content;
    private RelativeLayout design_choose_line;

    int y = 0;
    int limit = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View chatView = inflater.inflate(R.layout.new_upvideo_list_view, container, false);


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

//    int pageNum = 1;

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

        adapterHomeDesignListView = new NewAdapterUpVideoListView(getActivity(), designRoomInfos);

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
                final JobJson imageInfo = designRoomInfos.get(position-1);

                MainApp mainApp = (MainApp) getActivity().getApplicationContext();
                mainApp.jobImageJsonList = imageInfo.images;
                mainApp.remoteBaseUrl = imageInfo.remoteBaseUrl;
                mainApp.jobJson = imageInfo;

                Intent intent = new Intent(getActivity(), ShowVideoActivity.class);
                getActivity().startActivity(intent);
//                Intent intent = new Intent(getActivity(), MyWorkDetailActivity_.class);
//                JobJson jobJson = (JobJson) adapterHomeDesignListView.getItem(position-1);
//                intent.putExtra("SN", jobJson.sn);
//                startActivity(intent);//                Intent intent = new Intent(getActivity(), HomeAllDetailActivity_.class);
//				intent.putExtra(Constant.CASE_HOME_ID,adapterHomeDesignListView.getItem(position-1).id);
//                intent.putExtra(Constant.CASE_HOME_NAME,adapterHomeDesignListView.getItem(position-1).name);
//
//                startActivity(intent);

            }
        });

        pageNum = 1;

        getDataFromServer();

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

//        pageNum = 1;
//
//        getDataFromServer();
    }

    //**********获取筛选的后的list***************/
    FoxProgressbarInterface foxProgressbarInterface;
    private List<JobJson> designRoomInfos;
    int pageNum = 1;

    public void getJobListFromServerForMsg() {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(getActivity(), "加载中...");
        String beginD = "20161201";
        String endD = MyStringUtils.getNowTimeFormata(new Date());
        String params = "0";//0全部 1 审核 2通过
        Integer rows = 20;
        ProtocolUtil.myVideoListFunction(getActivity(), new MyJobListHandler(), configPref.userToken().get(), beginD
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

//                imageInfoListInit = baseJson.data;

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




