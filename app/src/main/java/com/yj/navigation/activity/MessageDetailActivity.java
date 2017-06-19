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
import com.yj.navigation.adapter.AdapterMessageDetailView;
import com.yj.navigation.adapter.AdapterMessageListView;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.JobJson;
import com.yj.navigation.object.MessageDetailJson;
import com.yj.navigation.object.MessageItemJson;
import com.yj.navigation.object.MessageJson;
import com.yj.navigation.object.MessageListJson;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Constant;

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
@EActivity(R.layout.message_detail_view)
public class MessageDetailActivity extends BaseActivity {

    @Pref
    ConfigPref_ configPref;

    @ViewById(R.id.no_data_id)
    RelativeLayout no_data_id;

    private PullToRefreshListView pullToRefreshListView;

    private AdapterMessageDetailView adapterHomeDesignListView;

    private List<MessageItemJson> designRoomInfos;

    private RelativeLayout design_choose_line;

    int y = 0;
    int limit = 10;

    String msgtype=null;
    String msgid=null;
    @Click(R.id.left_title_line)
    void onLeftTitleLine() {

        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.design_personal);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        initUi();


    }



    public void initUi() {
        msgtype=getIntent().getStringExtra("type");
        msgid=getIntent().getStringExtra("id");

        RelativeLayout main_title_id = (RelativeLayout) findViewById(R.id.main_title_id);
//        main_title_id.setBackgroundColor(getResources().getColor(R.color.white));

        ImageView left_title_icon = (ImageView) findViewById(R.id.left_title_icon);
        left_title_icon.setVisibility(View.VISIBLE);
        ImageView right_title_icon = (ImageView) findViewById(R.id.right_title_icon);
        right_title_icon.setVisibility(View.GONE);

        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText("详情");
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

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listview_design);

        initdata();
    }



    @AfterViews
    void init() {
        initUi();
    }


    @Override
    protected void initActivityName() {
        activityName = MessageDetailActivity.class.getName();
    }

    @Override
    protected void setActivityBg() {
//        if (BgTransitionUtil.bgDrawable != null) {
//            mainPage.setBackgroundDrawable(BgTransitionUtil.bgDrawable);
//        }
    }




    int pageNum = 1;

    int i = 0;

    /**
     * 测试数据
     */
    public void getDataFromServer() {

//

        getMessageListFromServerForMsg();


    }


    void initdata() {
        designRoomInfos = new ArrayList<MessageItemJson>();

        adapterHomeDesignListView = new AdapterMessageDetailView(this, designRoomInfos);

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
                        MessageDetailActivity.this,
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

//                Intent intent = new Intent(MessageDetailActivity.this, MyWorkDetailActivity_.class);
//                JobJson jobJson = (JobJson) adapterHomeDesignListView.getItem(position);
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

    public void getMessageListFromServerForMsg() {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");

        String rows="20";
        String page="1";
        String type0=msgtype;

        ProtocolUtil.messageDetailFunction(this, new MessageDetailHandler(), configPref.userToken().get(), page,rows,type0,null);//devno 空表示所有


    }


    private class MessageDetailHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            messageDetailHandler(resp);
        }
    }


    public void messageDetailHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            MessageDetailJson baseJson = new Gson().fromJson(resp, MessageDetailJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {

                //测试数据
//                baseJson.data = (ArrayList<MessageItemJson>) setTestData();


                if (baseJson.rows != null && baseJson.rows.size() > 0) {//列表

                    if (pageNum == 1) {
                        designRoomInfos.clear();
                        designRoomInfos.addAll(baseJson.rows);
                    } else {
                        designRoomInfos.addAll(baseJson.rows);
                    }

                }


                if (designRoomInfos.size() <= 0 && pageNum == 1) {
                    pullToRefreshListView.setVisibility(View.GONE);
                    no_data_id.setVisibility(View.VISIBLE);
                } else {
                    pullToRefreshListView.setVisibility(View.VISIBLE);
                    no_data_id.setVisibility(View.GONE);
                    adapterHomeDesignListView.notifyDataSetChanged();
                    // Call onRefreshComplete when the list has been refreshed.
                    pullToRefreshListView.onRefreshComplete();
                    pullToRefreshListView.getRefreshableView().setSelection(y);


                }
            }

        }
    }


    public List<MessageItemJson> setTestData() {
        List<MessageItemJson> jobJsons = new ArrayList<MessageItemJson>();
        for (int i = 0; i < 10; i++) {
            MessageItemJson jobJson = new MessageItemJson();
            jobJson.id = i+"";
            jobJson.content = "订单将于【2017-01-06 12:00:0"+i+"】分关闭，请及时处理。支付失败、支付困难，请拨打帮助电话400-011-9980.";
            jobJson.createTime = "2017-01-0"+i;
            jobJson.title = "您有一个订单没有关闭，请及时关闭";


            jobJsons.add(jobJson);

        }

        return jobJsons;
    }

}




