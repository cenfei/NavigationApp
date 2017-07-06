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

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yj.navigation.R;
import com.yj.navigation.adapter.AdapterWebUpVideoItemView;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.ReadFile;
import com.yj.navigation.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.camera.encode.VideoEncoderFromSurface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 2015/8/7.
 */
@EActivity(R.layout.myupwebvideo_list_view)
public class MyUpWebVideoListActivity extends BaseActivity {


    @Pref
    ConfigPref_ configPref;


    //    @ViewById(R.id.hot_network_no_id)
    LinearLayout hotNetworkNoId;


    @ViewById(R.id.no_data_id)
    RelativeLayout no_data_id;


    private PullToRefreshListView pullToRefreshListView;

    private AdapterWebUpVideoItemView adapterHomeDesignListView;

    private List<String> designRoomInfos;

    private RelativeLayout design_choose_line;

    int y = 0;
    int limit = 10;


    int pageNum = 1;

    int i = 0;

//    @Click(R.id.logining_btn_rel_id)
//    void onlogining_btn_rel_id() {
//
//        Util.startActivity(this, CameraSurfaceTextureActivity.class);
//        Intent intent = new Intent(this, CameraSurfaceTextureActivity.class);
//        intent.putExtra("mp4FileNameNumber", "" + (designRoomInfos.size() + 1));
//        startActivity(intent);
//    }


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

//        getJobListFromServerForMsg();


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
        left_title.setText("行车记录视频");
        ImageView right_title_icon = (ImageView) findViewById(R.id.right_title_icon3);
        right_title_icon.setVisibility(View.GONE);
        Util.setBackgroundOfVersion(right_title_icon, getResources().getDrawable(R.drawable.new_add_code));

        TextView right_title = (TextView) findViewById(R.id.right_title);

        right_title.setVisibility(View.GONE);
//        right_title.setText("筛选");
//        right_title.setTextColor(getResources().getColor(R.color.white));


        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText("上传列表");
//        title.setTextColor(getResources().getColor(R.color.white));
        View title_line_id = (View) findViewById(R.id.title_line_id);
        title_line_id.setVisibility(View.GONE);


        LinearLayout right_title_line = (LinearLayout) findViewById(R.id.right_title_line);

        right_title_line.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });


        designRoomInfos = new ArrayList<String>();

//
        String mp4Dir = VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE;
        try {
            designRoomInfos = ReadFile.readfileOnlyFile(mp4Dir);
//            handler.sendEmptyMessage(0);
        } catch (IOException e) {
            e.printStackTrace();
        }


        adapterHomeDesignListView = new AdapterWebUpVideoItemView(this, designRoomInfos,configPref.userToken().get());

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
                        MyUpWebVideoListActivity.this,
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
                String bankInfoJson = designRoomInfos.get(position - 1);//mp4的名字
//                String mp4Num = bankInfoJson.substring(0, bankInfoJson.indexOf(".mp4"));
//
//                //跳转到 show 页面
//                String mp4ImgDir = VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE + mp4Num+"/";
//                List<String> listImageName = null;
//                try {
//                    listImageName = ReadFile.readfileOnlyFile(mp4ImgDir);
////            handler.sendEmptyMessage(0);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                MainApp mainApp = (MainApp) getApplicationContext();
//                List<String> listImage = new ArrayList<String>();
//
//                for (String imgname : listImageName) {
//                    String imgUrl = "file:///mnt" + VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE + mp4Num+"/" + imgname;
//                    listImage.add(imgUrl);
//                }
//
//                mainApp.upImageJsonList = listImage;
//
//
//                Intent intent = new Intent(MyUpWebVideoListActivity.this, ShowVideoUpWebActivity.class);
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


        String mp4Dir = VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE;
        try {
            designRoomInfos = ReadFile.readfileOnlyFile(mp4Dir);
//            handler.sendEmptyMessage(0);
        } catch (IOException e) {
            e.printStackTrace();
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



