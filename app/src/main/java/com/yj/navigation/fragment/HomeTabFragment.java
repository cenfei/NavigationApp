//package com.yj.navigation.fragment;
//
//import android.content.res.Resources;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.util.DisplayMetrics;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.TranslateAnimation;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.yj.yjdesign.R;
//import com.yj.yjdesign.adapter.FragmentAdapter;
//
//import org.androidannotations.annotations.EFragment;
//
//import java.util.ArrayList;
//
//@EFragment(R.layout.home_design_fragment)
//public class HomeTabFragment extends Fragment {
//
//    Resources resources;
//    private ViewPager mPager;
//    private ArrayList<Fragment> fragmentsList;
//    private ImageView ivBottomLine;
//    private TextView tvTabNew, tvTabHot;
//
//    private int currIndex = 0;
//    private int bottomLineWidth;
//    private int offset = 0;
//    private int position_one;
//    public final static int num = 2 ;
//    Fragment home1;
//    Fragment home2;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // TODO Auto-generated method stub
//        View view = inflater.inflate(R.layout.home_design_fragment, null);
//        resources = getResources();
//
//        InitTextView(view);
//        InitWidth(view);
//        InitViewPager(view);
//        TranslateAnimation animation = new TranslateAnimation(position_one, offset, 0, 0);
//        tvTabHot.setTextColor(resources.getColor(R.color.line_hot_all));
//        animation.setFillAfter(true);
//        animation.setDuration(300);
//        ivBottomLine.startAnimation(animation);
//        return view;
//    }
//
//    private void InitTextView(View parentView) {
//        tvTabNew = (TextView) parentView.findViewById(R.id.tv_tab_1);
//        tvTabHot = (TextView) parentView.findViewById(R.id.tv_tab_2);
//
//        tvTabNew.setOnClickListener(new MyOnClickListener(0));
//        tvTabHot.setOnClickListener(new MyOnClickListener(1));
//    }
//
//    private void InitViewPager(View parentView) {
//        mPager = (ViewPager) parentView.findViewById(R.id.vPager);
//        fragmentsList = new ArrayList<Fragment>();
//
//        home1 = new HotFragment_();
//        home2 = new AllFragment_();
//
//        fragmentsList.add(home1);
//        fragmentsList.add(home2);
//
//        mPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragmentsList));
//        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
//        mPager.setCurrentItem(0);
//
//    }
//
//    private void InitWidth(View parentView) {
//        ivBottomLine = (ImageView) parentView.findViewById(R.id.iv_bottom_line);
//        bottomLineWidth = ivBottomLine.getLayoutParams().width;
//        DisplayMetrics dm = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int screenW = dm.widthPixels;
////        int  tvTabNewWid=tvTabNew.getLayoutParams().width;;
////Log.e("aaaaa",screenW+".."+bottomLineWidth+".."+tvTabNewWid);
////        offset = (int) ( screenW/2-bottomLineWidth/2-(35*3)-bottomLineWidth/2) ;
////        int avg = (int) (screenW -2*offset);
////        position_one = avg + offset;
//        offset = (int) (((screenW-(2*100*bottomLineWidth/48)) / num - bottomLineWidth) / 2)+100*bottomLineWidth/48;
//        int avg = (int) ((screenW-(2*100*bottomLineWidth/48)) / num);
//        position_one = avg + offset;
//
//
//    }
//
//    public class MyOnClickListener implements View.OnClickListener {
//        private int index = 0;
//
//        public MyOnClickListener(int i) {
//            index = i;
//        }
//
//        @Override
//        public void onClick(View v) {
//            mPager.setCurrentItem(index);
//        }
//    };
//
//    public class MyOnPageChangeListener implements OnPageChangeListener {
//
//        @Override
//        public void onPageSelected(int arg0) {
//            Animation animation = null;
//            switch (arg0) {
//                case 0:
//                    if (currIndex == 1) {
//                        animation = new TranslateAnimation(position_one, offset, 0, 0);
//                        tvTabHot.setTextColor(resources.getColor(R.color.line_hot_all));
//                    }
//                    tvTabNew.setTextColor(resources.getColor(R.color.line_hot_all));
//                    break;
//                case 1:
//                    if (currIndex == 0) {
//                        animation = new TranslateAnimation(offset, position_one, 0, 0);
//                        tvTabNew.setTextColor(resources.getColor(R.color.line_hot_all));
//                    }
//                    tvTabHot.setTextColor(resources.getColor(R.color.line_hot_all));
//                    break;
//            }
//            currIndex = arg0;
//            animation.setFillAfter(true);
//            animation.setDuration(300);
//            ivBottomLine.startAnimation(animation);
//        }
//
//        @Override
//        public void onPageScrolled(int arg0, float arg1, int arg2) {
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int arg0) {
//        }
//    }
//
//}
