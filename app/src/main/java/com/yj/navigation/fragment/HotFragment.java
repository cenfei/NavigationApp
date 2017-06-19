//package com.yj.navigation.fragment;
//
///**
// * Created by foxcen on 16/7/29.
// */
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.text.format.DateUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//
//import com.google.gson.Gson;
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.yj.yjdesign.R;
//import com.yj.yjdesign.activity.HomeAllDetailActivity_;
//import com.yj.yjdesign.activity.WebviewVrActivity;
//import com.yj.yjdesign.adapter.AdapterHomeDesignListView;
//import com.yj.yjdesign.component.FoxProgressbarInterface;
//import com.yj.yjdesign.component.ImageCycleView;
//import com.yj.yjdesign.network.ProtocolUtil;
//import com.yj.yjdesign.network.RowMessageHandler;
//import com.yj.yjdesign.object.CaseRome;
//import com.yj.yjdesign.object.EventRome;
//import com.yj.yjdesign.object.HotListJson;
//import com.yj.yjdesign.prefs.ConfigPref_;
//import com.yj.yjdesign.util.Constant;
//import com.yj.yjdesign.util.ImageLoaderUtil;
//
//import org.androidannotations.annotations.AfterViews;
//import org.androidannotations.annotations.EFragment;
//import org.androidannotations.annotations.sharedpreferences.Pref;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@EFragment(R.layout.activity_tab_hot)
//
//public class HotFragment extends Fragment {
//    @Pref
//    ConfigPref_ configPref;
//
//
//    //    @ViewById(R.id.hot_network_no_id)
//    LinearLayout hotNetworkNoId;
//
//    private PullToRefreshListView pullToRefreshListView;
//
//    private AdapterHomeDesignListView adapterHomeDesignListView;
//
//    private List<CaseRome> designRoomInfos;
//
//    private ImageCycleView slideshowView;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        View chatView = inflater.inflate(R.layout.activity_tab_hot, container, false);
//
//
//        pullToRefreshListView = (PullToRefreshListView) chatView.findViewById(R.id.listview_design);
//        View listViewHeader = inflater.inflate(R.layout.hot_slide_show_view, null);
//
//
//        slideshowView = (ImageCycleView) listViewHeader.findViewById(R.id.slideshowView);
//
//        pullToRefreshListView.getRefreshableView().addHeaderView(listViewHeader);
//        hotNetworkNoId = (LinearLayout) chatView.findViewById(R.id.hot_network_no_id);
//
//        init();
//        return chatView;
//    }
//
//    @AfterViews
//    void onInit() {
//
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    int pageNum = 1;
//    int limit = 20;
//
//    int i = 0;
//
//
//    int y = 0;
//
//    void init() {
//        designRoomInfos = new ArrayList<CaseRome>();
//
//        adapterHomeDesignListView = new AdapterHomeDesignListView(getActivity(), designRoomInfos);
//
//        pullToRefreshListView.setAdapter(adapterHomeDesignListView);
//
//
////        getDataFromServer();
//        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//
//
//                pageNum = 1;
//
//                String label = DateUtils.formatDateTime(
//                        getActivity(),
//                        System.currentTimeMillis(),
//                        DateUtils.FORMAT_SHOW_TIME
//                                | DateUtils.FORMAT_SHOW_DATE
//                                | DateUtils.FORMAT_ABBREV_ALL);
//
//                refreshView.getLoadingLayoutProxy()
//                        .setLastUpdatedLabel(label);
//                y = 0;
//
//                getDataFromServer();
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                if (adapterHomeDesignListView.mPersonal.size() < limit) {
//                    pageNum = 1;
//                } else {
//                    pageNum++;
//
//                }
//
//                if (!refreshView.isHeaderShown()) {
//                    y = adapterHomeDesignListView.mPersonal.size();
//                } else {
//                    // 得到上一次滚动条的位置，让加载后的页面停在上一次的位置，便于用户操作
//                    // y = adapter.list.size();
//
//                }
//                getDataFromServer();
//            }
//        });
//
//        // 点击详单
//        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//
//                Intent intent = new Intent(getActivity(), HomeAllDetailActivity_.class);
//                intent.putExtra(Constant.CASE_HOME_ID, adapterHomeDesignListView.getItem(position - 2).id);
//                intent.putExtra(Constant.CASE_HOME_NAME, adapterHomeDesignListView.getItem(position - 2).name);
//
////				intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImagePagerFragment.INDEX);
////                intent.putExtra(Constants.Extra.IMAGE_POSITION, position);
//                startActivity(intent);
//
//
//            }
//        });
//
//    }
//
//    FoxProgressbarInterface foxProgressbarInterface=null;
//
//    public void getDataFromServer() {
//        foxProgressbarInterface=new FoxProgressbarInterface();
//        foxProgressbarInterface.startProgressBar(getActivity(), "加载中...");
//
//        ProtocolUtil.hotList(getActivity(), new HotListHomeinfoHandler(), null, limit, (pageNum - 1) * limit);
//
//    }
//
//
//    private class HotListHomeinfoHandler extends RowMessageHandler {
//        @Override
//        protected void handleResp(String resp) {
//
//            handleHotListHomeinfoResp(resp);
//        }
//    }
//
//
//    public void handleHotListHomeinfoResp(String resp) {
//        foxProgressbarInterface.stopProgressBar();
//
//        if (resp == null || resp.equals("")) {
//            //网络不行的时候 需要从缓存中取值
//
//            resp = configPref.designHotListJson().get();
//
//        }
//
//        if (resp == null || resp.equals("")) {
//            //第一次进来没有初始值
//
//            hotNetworkNoId.setVisibility(View.VISIBLE);
//        }
//
//
//        if (resp != null && !resp.equals("")) {
//
//            hotNetworkNoId.setVisibility(View.GONE);
//
//            HotListJson hotListJson = new Gson().fromJson(resp, HotListJson.class);
//
//            configPref.designHotListJson().put(resp);
//
//
//            if (hotListJson.retCode.equals(Constant.RES_SUCCESS)) {
//
////                && hotListJson.cases != null
////                && !hotListJson.events.isEmpty() && hotListJson.cases.size() > 0) {
//                if (pageNum == 1) {
//                    if (hotListJson.events != null && hotListJson.events.size() > 0) {//banner列表
//
//                        hotListJson.events.addAll(hotListJson.events);
////                        slideshowView.initDataToPlay(hotListJson.events);
//
//                        slideshowView.setImageResources(hotListJson.events, new ImageCycleView.ImageCycleViewListener() {
//                            @Override
//                            public void displayImage(String imageURL, ImageView imageView) {
//                                ImageLoader.getInstance().displayImage(imageURL, imageView, ImageLoaderUtil.getServerrOptionsInstance());// 使用ImageLoader对图片进行加装！
//
//                            }
//
//                            @Override
//                            public void onImageClick(EventRome info, int postion, View imageView) {
//
//                                Intent intent = new Intent(getActivity(), WebviewVrActivity.class);
//                                intent.putExtra("url", info.url);
//                                intent.putExtra("title", "营匠推荐");
//                                //intent.putExtra("boolshare", "1");
//
//                                getActivity().startActivity(intent);
//                            }
//                        });
//                        slideshowView.startImageCycle();
//
//                    } else {//使用缓存 的list
//
//
//                    }
//                }
//
//
//                if (hotListJson.cases != null && hotListJson.cases.size() > 0) {//列表
//
//                    if (pageNum == 1) {
//                        designRoomInfos.clear();
//                        designRoomInfos.addAll(hotListJson.cases);
//                    } else {
//                        designRoomInfos.addAll(hotListJson.cases);
//                    }
//
//                } else {//使用缓存 的list
//
//
//                }
//
//
//                if (designRoomInfos.size() <= 0 && pageNum == 1) {
//                    pullToRefreshListView.setVisibility(View.GONE);
//                } else {
//
//                    adapterHomeDesignListView.notifyDataSetChanged();
//                    // Call onRefreshComplete when the list has been refreshed.
//                    pullToRefreshListView.onRefreshComplete();
//                    pullToRefreshListView.getRefreshableView().setSelection(y);
//
//
//                }
//
//            }
//        }
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        pageNum = 1;
//        getDataFromServer();
//
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        slideshowView.pushImageCycle();
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        slideshowView.pushImageCycle();
//
//    }
//}