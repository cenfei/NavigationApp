//package com.yj.navigation.activity;
//
//import android.graphics.drawable.AnimationDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.yj.navigation.R;
//import com.yj.navigation.component.FoxProgressbarInterface;
//import com.yj.navigation.network.ProtocolUtil;
//import com.yj.navigation.network.RowMessageHandler;
//import com.yj.navigation.prefs.ConfigPref_;
//import com.yj.navigation.util.Util;
//
//import org.androidannotations.annotations.AfterViews;
//import org.androidannotations.annotations.Click;
//import org.androidannotations.annotations.EActivity;
//import org.androidannotations.annotations.ViewById;
//import org.androidannotations.annotations.sharedpreferences.Pref;
//
///**
// * Created by zhang on 2015/8/7.
// */
//@EActivity(R.layout.animation_drawable_main)
//public class AnimationDrawableActivity extends BaseActivity {
//
//    @Pref
//    ConfigPref_ configPref;
//
//    ImageView animation_drawable_id;
//
//    AnimationDrawable animationDrawable;
//    @Click(R.id.animation_drawable_id)
//    void onClickAnimationDrawableId() {
//
//        if(animationDrawable.isRunning()){
//            animationDrawable.stop();
//        }else {
//            animationDrawable.start();
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
////        setContentView(R.layout.design_personal);
////        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//
////        initUi();
//
//
//    }
//
//    public void startAnimationDrawable() {
//        //创建帧动画
//         animationDrawable = new AnimationDrawable();
//        //添加帧
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.kebi1), 300);
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.kebi2), 300);
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.kebi3), 300);
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.kebi5), 300);
//
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.kebi6), 300);
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.kebi7), 300);
//
//        //设置动画是否只播放一次， 默认是false
//        animationDrawable.setOneShot(true);
//        //根据索引获取到那一帧的时长
//        int duration = animationDrawable.getDuration(2);
//        //根据索引获取到那一帧的图片
//        Drawable drawable = animationDrawable.getFrame(0);
//        //判断是否是在播放动画
//        boolean isRunning = animationDrawable.isRunning();
//        //获取这个动画是否只播放一次
//        boolean isOneShot = animationDrawable.isOneShot();
//        //获取到这个动画一共播放多少帧
//        int framesCount = animationDrawable.getNumberOfFrames();
//        //把这个动画设置为background，兼容更多版本写下面那句
//        animation_drawable_id.setBackground(animationDrawable);
////        animation_drawable_id.setBackgroundDrawable(animationDrawable);
////        //开始播放动画
////        animationDrawable.start();
////        //停止播放动画
////        animationDrawable.stop();
//    }
//    public void initUi() {
//
//        RelativeLayout main_title_id = (RelativeLayout) findViewById(R.id.main_title_id);
//        main_title_id.setBackgroundColor(getResources().getColor(R.color.white));
//
//        ImageView left_title_icon = (ImageView) findViewById(R.id.left_title_icon);
//        left_title_icon.setVisibility(View.VISIBLE);
//        ImageView right_title_icon = (ImageView) findViewById(R.id.right_title_icon);
//        right_title_icon.setVisibility(View.GONE);
//
//        TextView title = (TextView) findViewById(R.id.title);
//        title.setVisibility(View.GONE);
//        title.setTextColor(getResources().getColor(R.color.line_hot_all));
//        View title_line_id = (View) findViewById(R.id.title_line_id);
//        title_line_id.setVisibility(View.GONE);
//
//        animation_drawable_id=(ImageView) findViewById(R.id.animation_drawable_id);
//        startAnimationDrawable();
//
//
//    }
//
//    @AfterViews
//    void init() {
//        initUi();
//    }
//
//
//    @Override
//    protected void initActivityName() {
//        activityName = AnimationDrawableActivity.class.getName();
//    }
//
//    @Override
//    protected void setActivityBg() {
////        if (BgTransitionUtil.bgDrawable != null) {
////            mainPage.setBackgroundDrawable(BgTransitionUtil.bgDrawable);
////        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//    }
//
//
//
//}
