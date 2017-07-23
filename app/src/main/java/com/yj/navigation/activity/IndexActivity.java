package com.yj.navigation.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.makeramen.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yj.navigation.R;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.MainInfoJson;
import com.yj.navigation.object.VersionJson;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Constant;
import com.yj.navigation.util.DialogUtil;
import com.yj.navigation.util.ImageLoaderUtil;
import com.yj.navigation.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 2015/8/7.
 */
@EActivity(R.layout.new_index_view)
public class IndexActivity extends BaseActivity {

    @Pref
    ConfigPref_ configPref;


    @Click(R.id.left_title_line)
    void onleft_title_line() {


        Util.Toast(IndexActivity.this, "敬请期待",null);



    }
    @Click(R.id.img_info_rel)
    void onimg_info_rel() {


        Util.startActivity(this, MineInfoActivity_.class);



    }

    @Click(R.id.index_video_info_line)
    void onindex_video_info_line() {//我的视频


        Util.startActivity(IndexActivity.this, NewMyVideoListActivity_.class);



    }
    @Click(R.id.device_info_line)
    void ondevice_info_line() {//我的设备


        Util.startActivity(IndexActivity.this, MyDeviceListActivity_.class);



    }
    @Click(R.id.myorder_info_line)
    void onmyorder_info_line() {//我的工单


        Util.startActivity(IndexActivity.this, MyWorkActivity_.class);



    }
    @Click(R.id.wz_info_line)
    void onwz_info_line() {//我的违章
        Util.startActivity(IndexActivity.this, MyUpVideoListActivity_.class);


//        Util.startActivity(IndexActivity.this, MyWorkActivity_.class);



    }
    @Click(R.id.account_info_line)
    void onaccount_info_line() {//我的账户


        Util.startActivity(IndexActivity.this, MyAccountActivity_.class);



    }
    @Click(R.id.mine_info_line)
    void onmine_info_line() {//个人中心


        Util.startActivity(IndexActivity.this, MineInfoActivity_.class);



    }



    @Click(R.id.right_title_line)
    void onright_title_line() {

        Util.startActivity(this,MessageInfoActivity_.class);




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.design_personal);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        initUi();


    }

    private ImageLoader imageLoader;
    private DisplayImageOptions options;


    public void initUi() {
        imageLoader = ImageLoader.getInstance();
        options = ImageLoaderUtil.getAvatarOptionsInstance();

        RelativeLayout main_title_id = (RelativeLayout) findViewById(R.id.main_title_id);
        //main_title_id.setBackgroundColor(getResources().getColor(R.color.white));

        ImageView left_title_icon = (ImageView) findViewById(R.id.left_title_icon);
        left_title_icon.setVisibility(View.GONE);
//        left_title_icon.setImageDrawable(getResources().getDrawable(R.drawable.menu_icon));
        ImageView right_title_icon = (ImageView) findViewById(R.id.right_title_icon);
        right_title_icon.setVisibility(View.VISIBLE);
        right_title_icon.setImageDrawable(getResources().getDrawable(R.drawable.new_index_msg));

        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.GONE);
        title.setTextColor(getResources().getColor(R.color.line_hot_all));
        View title_line_id = (View) findViewById(R.id.title_line_id);
        title_line_id.setVisibility(View.GONE);





        mine_avar_id = (RoundedImageView) findViewById(R.id.mine_avar_id);

        mine_name_id = (TextView) findViewById(R.id.mine_name_id);

//        mine_city_id = (TextView) findViewById(R.id.mine_city_id);


        money_mount_id = (TextView) findViewById(R.id.money_mount_id);

        video_mount_id = (TextView) findViewById(R.id.video_mount_id);

        job_mount_id = (TextView) findViewById(R.id.job_mount_id);

//        GridView gridview = (GridView) findViewById(R.id.gridview);
//        gridview.setAdapter(new MyGridAdapter(this));
//        gridview.setOnItemClickListener(new ItemClickListener());
//        permissionAll();
        if (Build.VERSION.SDK_INT >= 23) {

            permissionAll();
        }

    }

    public void updateUi() {
        String headImg = configPref.userHeadImg().get();
        if (headImg != null && !headImg.equals("")) {
            Log.d("index ","headImg"+headImg);

            imageLoader.displayImage(headImg, mine_avar_id, options);
        }else{

            mine_avar_id.setImageDrawable(getResources().getDrawable(R.drawable.avar_mine_init));

        }
        String nickname = configPref.userNickname().get();
        if (nickname != null && !nickname.equals("")) {
            mine_name_id.setText(nickname);
        }

//        mine_city_id.setText("上海");
    }


    RoundedImageView mine_avar_id;
    TextView money_mount_id, video_mount_id, job_mount_id, mine_name_id;





    @AfterViews
    void init() {
        initUi();
    }


    @Override
    protected void initActivityName() {
        activityName = IndexActivity.class.getName();
    }

    @Override
    protected void setActivityBg() {
//        if (BgTransitionUtil.bgDrawable != null) {
//            mainPage.setBackgroundDrawable(BgTransitionUtil.bgDrawable);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUi();
        initDataFromServerForMsg();
    }


    //**********获取筛选的后的list***************/
    FoxProgressbarInterface foxProgressbarInterface;


    public void initDataFromServerForMsg() {
//        foxProgressbarInterface = new FoxProgressbarInterface();
//        foxProgressbarInterface.startProgressBar(this, "加载中...");

        ProtocolUtil.myMainInfoFunction(this, new MyMainInfoFunctionHandler(), configPref.userToken().get());


    }


    private class MyMainInfoFunctionHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            myMainInfoFunctionHandler(resp);
        }
    }


    public void myMainInfoFunctionHandler(String resp) {
//        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {
//            Util.Toast(IndexActivity.this, "验证码已发送，注意查收");

            MainInfoJson baseJson = new Gson().fromJson(resp, MainInfoJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {
//设置当前的数值

                money_mount_id.setText("￥" + baseJson.useScore);

                video_mount_id.setText(baseJson.vedioNum);
                job_mount_id.setText(baseJson.jobNum);
                updateVersionServerForMsg();
            }


        }
    }


    public void updateVersionServerForMsg() {
//        foxProgressbarInterface = new FoxProgressbarInterface();
//        foxProgressbarInterface.startProgressBar(this, "加载中...");
        String appversion = Util.getAppVersionName(this);

        ProtocolUtil.updateVersionAppFunction(this, new UpdateVersionAppFunctionHandler(), configPref.userToken().get(), "1", appversion);


    }


    private class UpdateVersionAppFunctionHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            updateVersionAppFunctionHandler(resp);
        }
    }


    public void updateVersionAppFunctionHandler(String resp) {
      //  foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {
//            Util.Toast(IndexActivity.this, "验证码已发送，注意查收");

            VersionJson baseJson = new Gson().fromJson(resp, VersionJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {
//设置当前的数值
                //判断是否要更新
                String isRequireUpdate = baseJson.isRequireUpdate;//是否强制更新 1
                String isUpdate = baseJson.isUpdate;//是否更新 1
                boolean boolRequireUpdate = false;
                boolean boolUpdate = false;
                if (isRequireUpdate != null && !isRequireUpdate.equals("")) {
                    boolRequireUpdate = isRequireUpdate.equals("1") ? true : false;
                    boolUpdate = true;
                }
                if (isUpdate != null && !isUpdate.equals("")) {
                    if (isUpdate.equals("0")) {
                        boolUpdate = false;
                    }

                }
                if (boolUpdate) {
                    String content = baseJson.remark;
                    content = content == null ? "" : content;
                    final String url = baseJson.url;

                    DialogUtil
                            .alertDialogDelete(
                                    this,
                                    "提示",
                                    "更新",
                                    "取消",
                                    content,
                                    "",
                                    new DialogUtil.DialogUtilCallBack() {

                                        @Override
                                        public void okFuncBack() {
                                            // TODO Auto-generated method stub
                                            Intent intent = new Intent(IndexActivity.this, WebViewActivity_.class);
                                            intent.putExtra("url", url);
                                            intent.putExtra("title","版本更新");
                                            startActivity(intent);


                                        }

                                        @Override
                                        public void cancleFuncBack() {
                                            // TODO Auto-generated method stub
                                            // nothing to do
                                        }
                                    }, !boolRequireUpdate);

                }
            }


        }
    }


    int  MY_PERMISSIONS_REQUEST_ACCESS_ALL=5005;
    public void permissionAll() {

        List<String> plist=new ArrayList<String>();
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)) {
            plist.add(Manifest.permission.CAMERA);
        }
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            plist.add(Manifest.permission.CAMERA);
        }
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            plist.add(Manifest.permission.CAMERA);
        }

//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//            plist.add(Manifest.permission.RECORD_AUDIO);
//        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            plist.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            plist.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            plist.add(Manifest.permission.READ_CONTACTS);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) {
            plist.add(Manifest.permission.WAKE_LOCK);
        }
        if(plist.size()>0) {
            String[] toBeStored = plist.toArray(new String[plist.size()]);

//请求权限
            ActivityCompat.requestPermissions(this, toBeStored,
                    MY_PERMISSIONS_REQUEST_ACCESS_ALL);
//判断是否需要 向用户解释，为什么要申请该权限
        }else{
            postStart();

        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_ALL) {
            postStart();
        }


    }

    public void postStart(){

    }
}
