/*******************************************************************************
 * Copyright 2014 Sergey Tarasevich
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.yj.navigation.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yj.navigation.R;
import com.yj.navigation.base.MainApp;
import com.yj.navigation.component.FoxProgressbarInterfaceView;
import com.yj.navigation.util.ImageLoaderUtil;
import com.yj.navigation.wheelview.OnWheelClickedListener;
import com.yj.navigation.wheelview.OnWheelScrollListener;
import com.yj.navigation.wheelview.WheelView;
import com.yj.navigation.wheelview.adapters.AbstractWheelTextAdapter;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */

public class ShowVideoUpWebActivity extends Activity {

    FoxProgressbarInterfaceView foxProgressbarInterface;
    private boolean boolFromVideoSecondFragment = false;

    boolean isScroll = false;

    WheelView country;
    ImageView animation_button_id, animation_drawable_id_left;
    Field fieldAnimation = null;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    List<String> jobImageJsonList;
    TextView report_next;
    //    JobJson jobJson;
    int duration = 200;


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {


            switch (msg.what) {
                case 0:
                    Bundle date = msg.getData();
                    foxProgressbarInterface.updateComment(String.valueOf(date.getString("comment")));


                    break;
                case 1:
                    foxProgressbarInterface.stopProgressBar();
                    startAnimationDrawableHandler();

                    break;
                default:

                    break;
            }


            return false;
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.show_view);
        initUi();
    }

    LinearLayout break_rules_button_id;

    public void initUi() {

        RelativeLayout main_title_id = (RelativeLayout) findViewById(R.id.main_title_id);
        //main_title_id.setBackgroundColor(getResources().getColor(R.color.white));

        ImageView left_title_icon = (ImageView) findViewById(R.id.left_title_icon);
        left_title_icon.setVisibility(View.GONE);
        ImageView right_title_icon = (ImageView) findViewById(R.id.right_title_icon);
        right_title_icon.setVisibility(View.GONE);

        LinearLayout left_title_icon_line = (LinearLayout) findViewById(R.id.left_title_icon_line);
        left_title_icon_line.setVisibility(View.VISIBLE);
        left_title_icon_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText("视频");
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

        animation_drawable_id = (ImageView) findViewById(R.id.animation_drawable_id);


        animation_drawable_id_left = (ImageView) findViewById(R.id.animation_drawable_id_left);
        animation_button_id = (ImageView) findViewById(R.id.animation_button_id);

        break_rules_button_id = (LinearLayout) findViewById(R.id.break_rules_button_id);
        report_next = (TextView) findViewById(R.id.report_next);
        break_rules_button_id.setVisibility(View.GONE);

//        break_rules_button_id.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(setChooseId.size()>0) {
//
//                    report_next.setVisibility(View.VISIBLE);
//                }
//                else{
//                    Util.Toast(ShowVideoActivity.this,"请先选择图片",null);
//                }
//            }
//        });

//        break_rules_button_id.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (setChooseId.size() == 0) {
//
//
//                    Util.Toast(ShowVideoUpWebActivity.this, "请先选择图片", null);
//                    return;
//                }
//
//
//                List<JobImageJson> jobImageJsonListChoose = new ArrayList<JobImageJson>();
//                for (int chooseId : setChooseId) {
//
//                    jobImageJsonListChoose.add(jobImageJsonList.get(chooseId));
//
//
//                }
//
//                MainApp mainApp = (MainApp) getApplicationContext();
//
//                mainApp.jobImageJsonList = jobImageJsonListChoose;
//
//                Intent intent = new Intent(ShowVideoUpWebActivity.this, UpdateJobInfoActivity_.class);
//                if (boolFromVideoSecondFragment) {
//
//                    intent.putExtra("FromVideoSecondFragment", true);
//                }
//                startActivity(intent);
//
//                finish();
//            }
//        });
        boolFromVideoSecondFragment=true;

//        boolFromVideoSecondFragment = getIntent().getBooleanExtra("FromVideoSecondFragment", false);


        startAnimationDrawable();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tryRecycleAnimationDrawable(animationDrawable);
        System.gc();
    }

    @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
//        animationDrawable.start();
        super.onResume();
    }

    private static void tryRecycleAnimationDrawable(AnimationDrawable animationDrawable) {
        if (animationDrawable != null) {
            animationDrawable.stop();
            for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
                Drawable frame = animationDrawable.getFrame(i);
                if (frame instanceof BitmapDrawable) {
                    ((BitmapDrawable) frame).getBitmap().recycle();
                }
                frame.setCallback(null);
            }
            animationDrawable.setCallback(null);
        }
    }

    public void startAnimationHandler() {

        animationDrawable.start();

        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          animation_button_id.setImageDrawable(getResources().getDrawable(R.drawable.img_play));

                                      }
                                  }, duration * jobImageJsonList.size()
        );

    }


    public static Bitmap getBitmap(Bitmap bitmap, int screenWidth,
                                   int screenHight) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Log.e("getBitmap", "w:" + w + ",h:" + h);

        Matrix matrix = new Matrix();
        float scale, scale2;
        if (w > h) {
            scale = (float) screenWidth / w;
            scale2 = (float) screenHight / h;
        } else {
            scale = (float) screenWidth / h;
            scale2 = (float) screenHight / w;
        }

//        float scale = (float) screenWidth / w;
//        float scale2 = (float) screenHight / h;
        // scale = scale < scale2 ? scale : scale2;

        Log.e("getBitmap", "scale:" + scale + ",scale2:" + scale2);
        if (scale < 0.75 && scale2 < 0.75) {
            matrix.postScale(0.75f, 0.75f);
        } else {
//            matrix.postScale(1, 1);
            matrix.postScale(3*scale/4, 3*scale2/4);

        }
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        if (bitmap != null && !bitmap.equals(bmp) && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }


        return bmp;// Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }



//    String remoteBaseUrl;
    LinearLayout country_line;
    Integer scrollFirst = 0;

    public void startAnimationDrawable() {

        MainApp mainApp = (MainApp) getApplicationContext();
//        mainApp.jobImageJsonList=imageInfo.images;
//        remoteBaseUrl = mainApp.remoteBaseUrl;

        jobImageJsonList = mainApp.upImageJsonList;

//        JobJson jobJson = mainApp.jobJson;
//        if (boolFromVideoSecondFragment) {
//            if (jobJson != null && "1".equals(jobJson.state)) {
//
//                break_rules_button_id.setVisibility(View.VISIBLE);
//            } else {
//                break_rules_button_id.setVisibility(View.GONE);
//
//            }
//        } else {
////            if(jobJson!=null&&"2".equals(jobJson.state)){
//            break_rules_button_id.setVisibility(View.VISIBLE);
//
////            }else{
////                break_rules_button_id.setVisibility(View.GONE);
////
////            }
//
//        }

        imageLoader = ImageLoader.getInstance();
        options = ImageLoaderUtil.getBannerOptionsInstance();


        country = (WheelView) findViewById(R.id.country);

        country_line = (LinearLayout) findViewById(R.id.country_line);

        country.setVisibleItems(1);
        //   country.setVisibility(View.GONE);
        final CountryAdapter countryAdapter = new CountryAdapter(ShowVideoUpWebActivity.this);
        country.setViewAdapter(countryAdapter);
        country.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
//                scrolling = true;
            }

            public void onScrollingFinished(WheelView wheel) {
//                scrolling = false;
                isScroll = true;
                scrollFirst = wheel.getCurrentItem();
                Log.i("onScrollingFinished", wheel.getCurrentItem() + "");
//                updateCities(city, cities, country.getCurrentItem());
                imageLoader.displayImage( jobImageJsonList.get(country.getCurrentItem()), animation_drawable_id_left, options);
//                animation_drawable_id_left.setImageDrawable(getResources().getDrawable(flags[country.getCurrentItem()]));
            }
        });

        country.addClickingListener(new OnWheelClickedListener() {
            @Override
            public void onItemClicked(WheelView wheel, int itemIndex) {
                if (setChooseId.contains(itemIndex)) {
//                    Log.e("remove", (itemIndex) + "");

                    setChooseId.remove(itemIndex);
                } else {
//                    Log.e("add", (itemIndex) + "");

                    setChooseId.add(itemIndex);
                }

                if (setChooseId.size() > 0) {
                    report_next.setBackgroundDrawable(ContextCompat.getDrawable(ShowVideoUpWebActivity.this, R.drawable.rounded_apply_use));

                } else {
                    report_next.setBackgroundDrawable(ContextCompat.getDrawable(ShowVideoUpWebActivity.this, R.drawable.rounded_apply));

                }

                country.invalidateWheel(true);


            }
        });


        country.setVisibility(View.GONE);
        country_line.setVisibility(View.GONE);


        country.setCurrentItem(1);

        foxProgressbarInterface = new FoxProgressbarInterfaceView();
        foxProgressbarInterface.startProgressBar(this, "正在生成视频...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("run:", "run:");
                //创建帧动画
                animationDrawable = new AnimationDrawable();
                int i = 0;
                for (String jobImageJson : jobImageJsonList) {

                    Bitmap bitmap = imageLoader.loadImageSync(jobImageJson, options);

                    DisplayMetrics metric = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(metric);
                    int width = metric.widthPixels;     // 屏幕宽度（像素）
                    int height = metric.heightPixels;   // 屏幕高度（像素）

                    Log.e("DisplayMetrics", "width:" + width + ",height:" + height);
                    Bitmap smallBitmap = getBitmap(bitmap, width, height);


//                    imageLoader.loadImageSync(remoteBaseUrl + jobImageJson.minPicUrl, options);

//                    bitmap=Util.compressImage(bitmap);
                    Drawable drawable = new BitmapDrawable(smallBitmap);
                    animationDrawable.addFrame(drawable, duration);
                    Log.d("run:", "run:" + i++);
                    Message msg = new Message();
                    Bundle date = new Bundle();// 存放数据
                    int count = jobImageJsonList.size();
                    date.putString("comment", "剩余" + (count - i) + "张加载");
                    msg.setData(date);
                    msg.what = 0;
                    Log.d("ThreadId", "POST:"
                            + String.valueOf(Thread.currentThread().getId()));
//                    myHandler.sendMessage(msg);
                    handler.sendMessage(msg);


//                    bitmap.recycle();

                }
                handler.sendEmptyMessage(1);
            }
        }).start();
    }

    public Set<Integer> setChooseId = new HashSet<Integer>();

    public void startAnimationDrawableHandler() {


        //添加帧
//        animationDrawable.addFrame(getResources().getDrawable(flags[0]), 1000);
//        animationDrawable.addFrame(getResources().getDrawable(flags[1]), 1000);
//        animationDrawable.addFrame(getResources().getDrawable(flags[2]), 1000);
//        animationDrawable.addFrame(getResources().getDrawable(flags[3]), 1000);
//
//        animationDrawable.addFrame(getResources().getDrawable(flags[4]), 1000);
//        animationDrawable.addFrame(getResources().getDrawable(flags[5]), 1000);

        //设置动画是否只播放一次， 默认是false
        animationDrawable.setOneShot(true);
        //根据索引获取到那一帧的时长
//        int duration = animationDrawable.getDuration(2);
//        //根据索引获取到那一帧的图片
//        Drawable drawable = animationDrawable.getFrame(0);
//        //判断是否是在播放动画
//        boolean isRunning = animationDrawable.isRunning();
//        //获取这个动画是否只播放一次
//        boolean isOneShot = animationDrawable.isOneShot();
//        //获取到这个动画一共播放多少帧
//        int framesCount = animationDrawable.getNumberOfFrames();
        //把这个动画设置为background，兼容更多版本写下面那句
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            animation_drawable_id.setBackground(animationDrawable);
        } else {
            animation_drawable_id.setBackgroundDrawable(animationDrawable);
        }


        animation_drawable_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCountry=true;
                country_line.setVisibility(View.VISIBLE);
                country.setVisibility(View.VISIBLE);
                animation_drawable_id_left.setVisibility(View.VISIBLE);

                Drawable drawable = animationDrawable.getCurrent();
                animation_drawable_id_left.setImageDrawable(drawable);
                country.setCurrentItem(getCurrentAnimation(drawable));

                animationDrawable.stop();

            }
        });

        animation_drawable_id_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation_drawable_id.setVisibility(View.VISIBLE);

                animation_drawable_id_left.setVisibility(View.GONE);
                country.setVisibility(View.GONE);
                country_line.setVisibility(View.GONE);


            }
        });


        animation_button_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (animationDrawable.isRunning()) {
                    country.setVisibility(View.GONE);
                    country_line.setVisibility(View.GONE);
                    animation_drawable_id.setVisibility(View.VISIBLE);
                    animation_drawable_id_left.setVisibility(View.GONE);

//                    animation_drawable_id_left.setImageDrawable(getResources().getDrawable(flags[0]));
//                    country.setCurrentItem(1);
                    animation_button_id.setImageDrawable(getResources().getDrawable(R.drawable.img_play));
                    animationDrawable.stop();

                } else {
                    country.setVisibility(View.GONE);
                    country_line.setVisibility(View.GONE);
                    animation_drawable_id.setVisibility(View.VISIBLE);

                    animation_drawable_id_left.setVisibility(View.GONE);
                    animation_button_id.setImageDrawable(getResources().getDrawable(R.drawable.img_pause));

                    startAnimationHandler();
//                    animationDrawable.start();
                }
            }
        });
    }

    ImageView animation_drawable_id;

    AnimationDrawable animationDrawable;


    public int getCurrentAnimation(Drawable drawable) {
        int returnCode = 0;
        for (int i = 0; i < jobImageJsonList.size(); i++) {

            if (drawable.equals(animationDrawable.getFrame(i))) {
                returnCode = i;
                return returnCode;
            }
        }

        return returnCode;

    }
boolean clickCountry=false;

    //***************************************



    /**
     * Adapter for countries
     */
    private class CountryAdapter extends AbstractWheelTextAdapter {
        // Countries names


        /**
         * Constructor
         */
        protected CountryAdapter(Context context) {
            super(context, R.layout.country_layout, NO_RESOURCE);

            // setItemTextResource(R.id.country_name);
        }

        @Override
        public View getItem(final int index, View cachedView, ViewGroup parent) {
            //Log.e("invalid", (index)+"");

            View view = super.getItem(index, cachedView, parent);


            RelativeLayout flag_rel = (RelativeLayout) view.findViewById(R.id.flag_rel);
            if (setChooseId.contains(index)) {
                flag_rel.setBackground(getResources().getDrawable(R.drawable.rounded_image_gallery));
            } else {
                flag_rel.setBackground(getResources().getDrawable(R.color.black));

            }


            ImageView img = (ImageView) view.findViewById(R.id.flag);
            Bitmap       bitmap=   imageLoader.loadImageSync( jobImageJsonList.get(index),options);
            Bitmap smallBitmap = getBitmap(bitmap, 120, 70);
            bitmap.recycle();
            bitmap=null;
            Drawable drawable = new BitmapDrawable(smallBitmap);
//view.setTag(index,img);
//
//
//
            img.setImageDrawable(drawable);
//            imageLoader.displayImage(jobJson.remoteBaseUrl + jobImageJsonList.get(index).minPicUrl, img, options);
//            img.setImageResource(flags[index]);


            return view;
        }


        @Override
        public int getItemsCount() {
            return jobImageJsonList.size();
        }

//        protected int getItemTextId(int index) {
//            return jobImageJsonList[index];
//        }


        @Override
        protected CharSequence getItemText(int index) {
            return jobImageJsonList.get(index) + "";
        }
    }
}