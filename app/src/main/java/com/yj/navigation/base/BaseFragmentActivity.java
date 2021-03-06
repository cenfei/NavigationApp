package com.yj.navigation.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yj.navigation.R;
import com.yj.navigation.util.MyPreferences;

public abstract class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract void setActivityBg();

    protected void onResume() {
        super.onResume();

        setActivityBg();
//        MobclickAgent.onResume(this);
    }

    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    private int guideResourceId=0;//引导页图片资源id
    @Override
    protected void onStart() {
        super.onStart();
        addGuideImage();//添加引导页
    }

    /**
     * 添加引导图片
     */
    public void addGuideImage() {
        View view = getWindow().getDecorView().findViewById(R.id.my_content_view);//查找通过setContentView上的根布局
        if(view==null)return;
        if(MyPreferences.activityIsGuided(this, this.getClass().getName())){
            //引导过了
            return;
        }
        ViewParent viewParent = view.getParent();
        if(viewParent instanceof FrameLayout){
            final FrameLayout frameLayout = (FrameLayout)viewParent;
            if(guideResourceId!=0){//设置了引导图片
                final ImageView guideImage = new ImageView(this);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
                guideImage.setLayoutParams(params);
                guideImage.setScaleType(ImageView.ScaleType.FIT_XY);
                guideImage.setImageResource(guideResourceId);
                guideImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        frameLayout.removeView(guideImage);
                        MyPreferences.setIsGuided(getApplicationContext(), BaseFragmentActivity.this.getClass().getName());//设为已引导
                    }
                });
                frameLayout.addView(guideImage);//添加引导图片

            }
        }
    }

    /**子类在onCreate中调用，设置引导图片的资源id
     *并在布局xml的根元素上设置android:id="@id/my_content_view"
     * @param resId
     */
    protected void setGuideResId(int resId){
        this.guideResourceId=resId;
    }


}
