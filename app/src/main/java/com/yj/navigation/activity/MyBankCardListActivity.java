package com.yj.navigation.activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yj.navigation.R;
import com.yj.navigation.adapter.MyBankcardAllListAdapter;
import com.yj.navigation.base.MainApp;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.BankCardInfoJson;
import com.yj.navigation.object.BankCardListJson;
import com.yj.navigation.object.BaseJson;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Constant;
import com.yj.navigation.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.swu.pulltorefreshswipemenulistview.library.PullToRefreshSwipeMenuListView;
import edu.swu.pulltorefreshswipemenulistview.library.pulltorefresh.interfaces.IXListViewListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.bean.SwipeMenu;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.bean.SwipeMenuItem;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.OnMenuItemClickListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.OnSwipeListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.SwipeMenuCreator;
import edu.swu.pulltorefreshswipemenulistview.library.util.RefreshTime;

/**
 * Created by zhang on 2015/8/7.
 */
@EActivity(R.layout.bankcard_list_view)
public class MyBankCardListActivity extends BaseActivity implements IXListViewListener {

    @Pref
    ConfigPref_ configPref;
    @ViewById(R.id.no_data_id)
    RelativeLayout no_data_id;

    private List<BankCardInfoJson> mAppList;
    private MyBankcardAllListAdapter mAdapter;
    private PullToRefreshSwipeMenuListView mListView;
    private Handler mHandler;


    int limit = 10;

    @Click(R.id.left_title_line)
    void onLeftTitleLine() {

        finish();

    }


    @AfterViews
    void init() {
        initUi();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.design_personal);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        initUi();


    }


    @Override
    protected void initActivityName() {

    }

    @Override
    protected void setActivityBg() {

    }

    int pageNum = 1;

    int i = 0;
    int positionIndex = 0;

    /**
     * 测试数据
     */
    public void getDataFromServer() {
        getDeviceListFromServerForMsg();


    }

    MyBankCardListActivity myDeviceListActivity;


    void initUi() {

        RelativeLayout main_title_id = (RelativeLayout) findViewById(R.id.main_title_id);
        main_title_id.setBackgroundColor(getResources().getColor(R.color.white_alpha80));

        ImageView left_title_icon = (ImageView) findViewById(R.id.left_title_icon);
        left_title_icon.setVisibility(View.VISIBLE);
        TextView left_title = (TextView) findViewById(R.id.left_title);
        left_title.setVisibility(View.VISIBLE);
        left_title.setText("返回");
        ImageView right_title_icon = (ImageView) findViewById(R.id.right_title_icon3);
        right_title_icon.setVisibility(View.GONE);
        Util.setBackgroundOfVersion(right_title_icon, getResources().getDrawable(R.drawable.new_add_code));

        TextView right_title = (TextView) findViewById(R.id.right_title);

        right_title.setVisibility(View.VISIBLE);
        right_title.setText("添加");
//        right_title.setTextColor(getResources().getColor(R.color.white));


        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText("选择银行卡");
//        title.setTextColor(getResources().getColor(R.color.white));
        View title_line_id = (View) findViewById(R.id.title_line_id);
        title_line_id.setVisibility(View.GONE);


        LinearLayout right_title_line = (LinearLayout) findViewById(R.id.right_title_line);

        right_title_line.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Util.startActivity(MyBankCardListActivity.this, BindCardActivity_.class);

            }
        });


        mListView = (PullToRefreshSwipeMenuListView) findViewById(R.id.listView);


        mAppList = new ArrayList<BankCardInfoJson>();
        mAdapter = new MyBankcardAllListAdapter(this, mAppList, configPref.userToken().get());
        mListView.setAdapter(mAdapter);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
        mHandler = new Handler();

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                Log.d("onScrollStateChanged", "onScrollStateChanged");

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                Log.d("onScroll", "onScroll");

            }
        });

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(MyBankCardListActivity.this);
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("解绑");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.RED);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
//                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
//                // set item background
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
//                // set item width
//                deleteItem.setWidth(dp2px(90));
//                // set a icon
//                deleteItem.setIcon(R.drawable.ic_delete);
//                // add to menu
//                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mListView.setMenuCreator(creator);

        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                BankCardInfoJson item = (BankCardInfoJson) mAppList.get(position-1);
                switch (index) {

                    case 0:
                        // delete
                        // delete(item);
                        //解绑操作

                        //当前deviceId有问题
                        positionIndex = position-1;

                        unBindDeviceFromServerForMsg(item.id);

                        break;
                }
            }
        });

        // set SwipeListener
        mListView.setOnSwipeListener(new OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                Log.d("onSwipeStart", "onSwipeStart");
            }

            @Override
            public void onSwipeEnd(int position) {
                Log.d("onSwipeEnd", "onSwipeEnd");
            }
        });

        // other setting
        // listView.setCloseInterpolator(new BounceInterpolator());

        // test item long click
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MyBankCardListActivity.this, position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                BankCardInfoJson bankCardInfoJson = mAppList.get(i - 1);

                MainApp mainApp = (MainApp) getApplicationContext();
                mainApp.chooseBankCardInfoJson = bankCardInfoJson;
                finish();

            }
        });


    }


    public void bindDevice(String deviceNo, String deviceToken) {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");


        //扫描绑定 0 设备号绑定1
        ProtocolUtil.bindDeviceFunction(this, new BindDeviceHandler(), configPref.userToken().get(), "0", deviceNo, deviceToken);


    }


    private class BindDeviceHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            bindDeviceHandler(resp);
        }
    }


    public void bindDeviceHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            BaseJson baseJson = new Gson().fromJson(resp, BaseJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {

                //保存token

//                Util.startActivity(MyDeviceListActivity.this, MyDeviceListActivity_.class);

            }

        }
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


    private void onLoad() {
        mListView.setRefreshTime(RefreshTime.getRefreshTime(MyBankCardListActivity.this));
        mListView.stopRefresh();

        mListView.stopLoadMore();

    }

    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
                RefreshTime.setRefreshTime(getApplicationContext(), df.format(new Date()));
                onLoad();
            }
        }, 2000);
    }

    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoad();
            }
        }, 2000);
    }

    private void delete(ApplicationInfo item) {
        // delete app
        try {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.fromParts("package", item.packageName, null));
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    private void open(String item) {
        // open app
//        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
//        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//        resolveIntent.setPackage(item.packageName);
//        List<ResolveInfo> resolveInfoList = getActivity().getPackageManager().queryIntentActivities(resolveIntent, 0);
//        if (resolveInfoList != null && resolveInfoList.size() > 0) {
//            ResolveInfo resolveInfo = resolveInfoList.get(0);
//            String activityPackageName = resolveInfo.activityInfo.packageName;
//            String className = resolveInfo.activityInfo.name;
//
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_LAUNCHER);
//            ComponentName componentName = new ComponentName(activityPackageName, className);
//
//            intent.setComponent(componentName);
//            startActivity(intent);
//        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    //***********************接口***************************


    //**********获取筛选的后的list***************/
    FoxProgressbarInterface foxProgressbarInterface;

    public void getDeviceListFromServerForMsg() {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(MyBankCardListActivity.this, "加载中...");


        ProtocolUtil.myCardListFunction(MyBankCardListActivity.this, new DeviceListHandler(), configPref.userToken().get());


    }


    private class DeviceListHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            deviceListHandler(resp);
        }
    }


    public void deviceListHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            BankCardListJson baseJson = new Gson().fromJson(resp, BankCardListJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {


                if (baseJson.data != null && baseJson.data.size() > 0) {//列表

                    if (pageNum == 1) {
                        mAppList.clear();
                        mAppList.addAll(baseJson.data);
                    } else {
                        mAppList.addAll(baseJson.data);
                    }

                }


                if (mAppList.size() <= 0 && pageNum == 1) {
                    no_data_id.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                } else {
                    no_data_id.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);

                    mAdapter.notifyDataSetChanged();
                    // Call onRefreshComplete when the list has been refreshed.
//                    mListView.onRefreshComplete();
//                    mListView.getRefreshableView().setSelection(y);


                }
            }

        }
    }


    public void unBindDeviceFromServerForMsg(Integer deviceId) {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(MyBankCardListActivity.this, "加载中...");


        ProtocolUtil.unbindCardFunction(this, new
                UnBindDeviceHandler(), configPref.userToken().get(), deviceId);

    }


    private class UnBindDeviceHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            unBindDeviceHandler(resp);
        }
    }


    public void unBindDeviceHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            BaseJson baseJson = new Gson().fromJson(resp, BaseJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {
                Util.Toast(MyBankCardListActivity.this, "解绑成功");
                getDeviceListFromServerForMsg();
//                mAppList.remove(positionIndex);
//                mAdapter.notifyDataSetChanged();
                //保存token

//                configPref.userToken().put(baseJson.token);
//                configPref.userMobile().put(mobile);
//                Util.startActivity(LoginActivity.this, MineActivity_.class);
//                finish();
            }

        }
    }

}
