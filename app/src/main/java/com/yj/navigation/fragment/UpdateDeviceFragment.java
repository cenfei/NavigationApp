package com.yj.navigation.fragment;

/**
 * Created by foxcen on 16/7/29.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yj.navigation.R;
import com.yj.navigation.activity.MyDeviceListActivity;
import com.yj.navigation.adapter.MyDeviceAllListAdapter;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.BaseJson;
import com.yj.navigation.object.DeviceJson;
import com.yj.navigation.object.DeviceListJson;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Constant;
import com.yj.navigation.util.Util;

import org.androidannotations.annotations.EFragment;
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
 * Created by foxcen on 16/7/29.
 */
@EFragment(R.layout.device_list_all_view)

public class UpdateDeviceFragment extends Fragment implements IXListViewListener {


    @Pref
    ConfigPref_ configPref;
    @ViewById(R.id.no_data_id)
    RelativeLayout no_data_id;
    private List<DeviceJson> mAppList;
    private MyDeviceAllListAdapter mAdapter;
    private PullToRefreshSwipeMenuListView mListView;
    private Handler mHandler;


    int limit = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View chatView = inflater.inflate(R.layout.device_list_all_view, container, false);

        // getDataFromServer();
        init(chatView);
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
    int positionIndex = 0;

    /**
     * 测试数据
     */
    public void getDataFromServer() {
        getDeviceListFromServerForMsg();


    }

    MyDeviceListActivity myDeviceListActivity;



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myDeviceListActivity = (MyDeviceListActivity) activity;
    }

    void init(View chatView) {


        mListView = (PullToRefreshSwipeMenuListView) chatView.findViewById(R.id.listView);

//        mListView.setViewPager(myDeviceListActivity.mPager);

        mAppList=new ArrayList<DeviceJson>();
        mAdapter = new MyDeviceAllListAdapter(getActivity(), mAppList,configPref.userToken().get());
        mListView.setAdapter(mAdapter);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
        mHandler = new Handler();

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                Log.d("onScrollStateChanged","onScrollStateChanged");
                getDataFromServer();

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                Log.d("onScroll","onScroll");

            }
        });

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getActivity());
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
                DeviceJson item = (DeviceJson) mAppList.get(position);
                switch (index) {

                    case 0:
                        // delete
                        // delete(item);
                        //解绑操作

                        //当前deviceId有问题
                        positionIndex = position;

                        unBindDeviceFromServerForMsg(item.bindId);

                        break;
                }
            }
        });

        // set SwipeListener
        mListView.setOnSwipeListener(new OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                Log.d("onSwipeStart","onSwipeStart");
            }

            @Override
            public void onSwipeEnd(int position) {
                Log.d("onSwipeEnd","onSwipeEnd");
            }
        });

        // other setting
        // listView.setCloseInterpolator(new BounceInterpolator());

        // test item long click
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), position + " long click", Toast.LENGTH_SHORT).show();
                return false;
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


    private void onLoad() {
        mListView.setRefreshTime(RefreshTime.getRefreshTime(getActivity()));
        mListView.stopRefresh();

        mListView.stopLoadMore();

    }

    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
                RefreshTime.setRefreshTime(getActivity().getApplicationContext(), df.format(new Date()));
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
        foxProgressbarInterface.startProgressBar(getActivity(), "加载中...");


        ProtocolUtil.deviceListFunction(getActivity(), new DeviceListHandler(), configPref.userToken().get());


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


            DeviceListJson baseJson = new Gson().fromJson(resp, DeviceListJson.class);
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
        foxProgressbarInterface.startProgressBar(getActivity(), "加载中...");


        ProtocolUtil.unbindDeviceFunction(getActivity(), new UnBindDeviceHandler(), configPref.userToken().get(), deviceId);


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
                Util.Toast(getActivity(), "解绑成功",null);
                mAppList.remove(positionIndex);
                mAdapter.notifyDataSetChanged();
                //保存token
//                configPref.userToken().put(baseJson.token);
//                configPref.userMobile().put(mobile);
//                Util.startActivity(LoginActivity.this, MineActivity_.class);
//                finish();
            }

        }
    }

}




