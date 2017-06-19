package com.yj.navigation.fragment;

/**
 * Created by foxcen on 16/7/29.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yj.navigation.R;
import com.yj.navigation.activity.ShowVideoActivity;
import com.yj.navigation.base.MainApp;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.component.HackyViewPager;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.JobImageJson;
import com.yj.navigation.object.JobJson;
import com.yj.navigation.object.JobListJson;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Constant;
import com.yj.navigation.util.MyStringUtils;
import com.yj.navigation.util.Util;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by foxcen on 16/7/29.
 */
@EFragment(R.layout.viewpager_myvideo)

public class VideoFirstFragment extends Fragment {


    @Pref
    ConfigPref_ configPref;
    @ViewById(R.id.no_data_id)
    RelativeLayout no_data_id;
    int position = 0;
    private List<JobJson> imageInfoListInit;

    TextView img_position, img_content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View chatView = inflater.inflate(R.layout.viewpager_myvideo, container, false);


        init(chatView);
        return chatView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    ViewPager pager = null;

    public void init(View chatView) {
//        initImageList();
        pager = (HackyViewPager) chatView.findViewById(R.id.pager);

        final ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (imageInfoListInit != null) {
                    JobJson imageInfo = imageInfoListInit.get(position);

//                img_content.setText(imageInfo.getUrl());

                    img_position.setText((position + 1) + "/" + imageInfoListInit.size());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        pager.addOnPageChangeListener(listener);

//        position = chatView.getIntent().getIntExtra(Constants.Extra.IMAGE_POSITION, 0);


        img_position = (TextView) chatView.findViewById(R.id.img_position);
        img_content = (TextView) chatView.findViewById(R.id.img_content);


//		}
// img_content.setText(imageInfoListInit.get(position).getContent());
//
//		Log.e("wai当前的position",position+"");
//		img_position.setText((position+1)+ "/" + imageInfoListInit.size());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        init();
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

        initImageList();
    }

    public void initImageList() {

//        MainApp mainApp = (MainApp) getApplicationContext();
//
//        imageInfoListInit = mainApp.designRoomInfosPic;
//        imageInfoListInit = new ArrayList<ImageInfo>();
//
//        for (int i = 0; i < 6; i++) {
//
//            ImageInfo imageInfo = new ImageInfo();
//            imageInfo.setUrl(Constants.IMAGES[i]);
//            imageInfo.setContent(Constants.IMAGES[i]);
//            imageInfoListInit.add(imageInfo);
//        }
        getJobListFromServerForMsg();
    }


    private class ImageAdapter extends PagerAdapter {

//		private static final String[] IMAGE_URLS = Constants.IMAGES;


        private List<JobJson> imageInfoList;

        private LayoutInflater inflater;
        private DisplayImageOptions options;

        ImageAdapter(Context context, List<JobJson> imageInfoList) {
            this.imageInfoList = imageInfoList;
            inflater = LayoutInflater.from(context);

            options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.rounded_gray_wallet_img)
                    .showImageOnFail(R.drawable.rounded_gray_wallet_img)
                    .resetViewBeforeLoading(true)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .considerExifParams(true)
                    .displayer(new FadeInBitmapDisplayer(300))
                    .build();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {

            return imageInfoList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {


            final JobJson imageInfo = imageInfoList.get(position);
            View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);

            RelativeLayout image_frame = (RelativeLayout) imageLayout.findViewById(R.id.image_frame);


            image_frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // getActivity().finish();
                    //finish();
                }
            });
            assert imageLayout != null;

            TextView address_comment = (TextView) imageLayout.findViewById(R.id.address_comment);
            address_comment.setText(imageInfo.province + imageInfo.area);
            TextView time_comment = (TextView) imageLayout.findViewById(R.id.time_comment);
            time_comment.setText(imageInfo.takedt);
            TextView com_comment = (TextView) imageLayout.findViewById(R.id.com_comment);
            com_comment.setText("图片" + imageInfo.images.size() + "张");

            //  ImageView design_room_img_3d = (ImageView) imageLayout.findViewById(R.id.design_room_img_3d);


//			PhotoView photoView
            PhotoView imageView = (PhotoView) imageLayout.findViewById(R.id.image);

            imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

                @Override
                public void onPhotoTap(View arg0, float arg1, float arg2) {
                    MainApp mainApp = (MainApp) getActivity().getApplicationContext();
                    mainApp.jobImageJsonList = imageInfo.images;
                    mainApp.remoteBaseUrl = imageInfo.remoteBaseUrl;
                    mainApp.jobJson = imageInfo;

                    Intent intent = new Intent(getActivity(), ShowVideoActivity.class);
                    getActivity().startActivity(intent);


//                    Util.startActivity(getActivity(), ShowVideoActivity.class);
//                            finish();
                }
            });


//
            final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
            JobImageJson jobImageJson = Util.getFirstJob(imageInfo.images);
            ImageLoader.getInstance().displayImage(imageInfo.remoteBaseUrl + jobImageJson.bigPicUrl, imageView, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
//                    spinner.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    String message = null;
                    switch (failReason.getType()) {
                        case IO_ERROR:
                            message = "Input/Output error";
                            break;
                        case DECODING_ERROR:
                            message = "Image can't be decoded";
                            break;
                        case NETWORK_DENIED:
                            message = "Downloads are denied";
                            break;
                        case OUT_OF_MEMORY:
                            message = "Out Of Memory error";
                            break;
                        case UNKNOWN:
                            message = "Unknown error";
                            break;
                    }
                    Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();

                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    spinner.setVisibility(View.GONE);
                }
            });

            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }


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

                imageInfoListInit = baseJson.data;

                if(imageInfoListInit==null){
                    imageInfoListInit=new ArrayList<JobJson>();
                }

                pager.setAdapter(new ImageAdapter(getActivity(), imageInfoListInit));

                if (imageInfoListInit.size() == 0) {
                    img_position.setVisibility(View.GONE);
//                    img_content.setVisibility(View.GONE);
                    no_data_id.setVisibility(View.VISIBLE);


                } else {
                    no_data_id.setVisibility(View.GONE);
                    img_position.setVisibility(View.VISIBLE);

//                    img_content.setText(imageInfoListInit.get(position).getContent());
                    img_position.setText((position + 1) + "/" + imageInfoListInit.size());


                }
                pager.setCurrentItem(position);

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




