package com.yj.navigation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yj.navigation.R;
import com.yj.navigation.base.MainApp;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.gallery.GalleryImageAdapter;
import com.yj.navigation.gallery.GalleryView;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.BaseJson;
import com.yj.navigation.object.JobImageJson;
import com.yj.navigation.object.JobJson;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Constant;
import com.yj.navigation.util.Util;
import com.yj.navigation.wheelviewdialog.DialogWheelArrayInfo;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

/**
 * Created by zhang on 2015/8/7.
 */
@EActivity(R.layout.updatejobinfoview)
public class UpdateJobInfoActivity extends BaseActivity {

    @Pref
    ConfigPref_ configPref;
    @ViewById(R.id.password_info_id)
    TextView password_info_id;

    @Click(R.id.left_title_line)
    void onLeftTitleLine() {

        finish();

    }
    String chooseDistrict="1";
String[] districtsArray={"1","2","3"};
    @Click(R.id.password_info_id)
    void onAreaLineId() {

        final DialogWheelArrayInfo wheelDialog = new DialogWheelArrayInfo(UpdateJobInfoActivity.this, R.style.mypopwindow_anim_style, "所在地", chooseDistrict, districtsArray,
                new DialogWheelArrayInfo.IWheelCallBack() {
                    @Override
                    public void getWheelCallBack(String chooseValue) {
                        chooseDistrict = chooseValue;
//
                        password_info_id.setText(chooseValue);
                        Log.e("chooseValue", chooseValue);
//                        Util.Toast(DesignPersonalActivity.this, "" + chooseValue);
                    }
                });
        wheelDialog.showDialog();

    }


    @Click(R.id.logining_btn_rel_id)
    void onLoginingBtnRelId() {

        MainApp mainApp = (MainApp) getApplication();

        jobJson = mainApp.jobJson;
        String sn = jobJson.sn;
        String cardNo = cardno_info_id.getText().toString();
        String content = password_info_id.getText().toString();
        if (sn == null || sn.equals("")) {

            Util.Toast(this, "序列号为空");

            return;
        }

        if (cardNo == null || cardNo.equals("")) {

            Util.Toast(this, "请输入车牌号");

            return;
        }
        if (content == null || content.equals("")) {

            Util.Toast(this, "请输入违章内容");

            return;
        }
        List<JobImageJson> jobImageJsonListApp = mainApp.jobImageJsonList;
        StringBuffer sb = new StringBuffer();
        for (JobImageJson jobImageJson : jobImageJsonListApp) {
            sb.append(jobImageJson.bigPicUrl);
            sb.append(",");
        }


        updateJobInfoServerForMsg(sn, cardNo, content, sb.toString());


//        Util.startActivity(this, IndexActivity_.class);
//
//        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.design_personal);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        initUi();


    }

    boolean boolFromVideoSecondFragment = false;

    public void initUi() {
        boolFromVideoSecondFragment = getIntent().getBooleanExtra("FromVideoSecondFragment", false);

        RelativeLayout main_title_id = (RelativeLayout) findViewById(R.id.main_title_id);
        main_title_id.setBackgroundColor(getResources().getColor(R.color.white_alpha80));

        ImageView left_title_icon = (ImageView) findViewById(R.id.left_title_icon);
        left_title_icon.setVisibility(View.VISIBLE);
        ImageView right_title_icon = (ImageView) findViewById(R.id.right_title_icon);
        right_title_icon.setVisibility(View.GONE);
        TextView left_title = (TextView) findViewById(R.id.left_title);
        left_title.setVisibility(View.VISIBLE);
        left_title.setText("我的上传视频");
        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText("完善信息");
//        title.setTextColor(getResources().getColor(R.color.white));
        View title_line_id = (View) findViewById(R.id.title_line_id);
        title_line_id.setVisibility(View.GONE);


        LinearLayout right_title_line = (LinearLayout) findViewById(R.id.right_title_line);

        right_title_line.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView address_comment_id = (TextView) findViewById(R.id.address_comment_id);
        TextView time_comment_id = (TextView) findViewById(R.id.time_comment_id);

        cardno_info_id = (EditText) findViewById(R.id.cardno_info_id);
//        password_info_id = (EditText) findViewById(R.id.password_info_id);

        MainApp mainApp = (MainApp) getApplication();

        jobJson = mainApp.jobJson;

        if (jobJson.takedt != null && !jobJson.takedt.equals("")) {
            time_comment_id.setText(jobJson.takedt);
        }
        if (jobJson.address != null && !jobJson.address.equals("")) {
            address_comment_id.setText(jobJson.address);
        }


        GalleryView galleryView = (GalleryView) findViewById(R.id.mygallery);
        TextView img_count_id = (TextView) findViewById(R.id.img_count_id);

//        mainApp.jobImageJsonList=imageInfo.images;

        List<JobImageJson> jobImageJsonListApp = mainApp.jobImageJsonList;

        GalleryImageAdapter galleryImageAdapter = new GalleryImageAdapter(this, img_count_id);
        galleryImageAdapter.jobImageJsonList = jobImageJsonListApp;
        img_count_id.setText("1/" + jobImageJsonListApp.size());

//        ImageAdapter imageAdapter=new ImageAdapter(this);
//        imageAdapter.createReflectedImages();
        galleryView.setAdapter(galleryImageAdapter);

        galleryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(UpdateJobInfoActivity.this, ShowVideoActivity.class);
                intent.putExtra("FromVideoSecondFragment", true);
                startActivity(intent);
                finish();
            }
        });
    }

    private JobJson jobJson = null;
    private EditText cardno_info_id;

    @AfterViews
    void init() {
        initUi();
    }


    @Override
    protected void initActivityName() {
        activityName = UpdateJobInfoActivity.class.getName();
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

    }


    //**********获取筛选的后的list***************/
    FoxProgressbarInterface foxProgressbarInterface;

    public void updateJobInfoServerForMsg(String sn, String cardno, String remarkcontent, String piclist) {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");

        if (boolFromVideoSecondFragment) {
            ProtocolUtil.updateJobInfoFunction(this, new UpdateJobInfoHandler(), configPref.userToken().get(), sn, cardno, remarkcontent, null, piclist);

        } else {
            ProtocolUtil.reportJobInfoFunction(this, new UpdateJobInfoHandler(), configPref.userToken().get(), sn, cardno, remarkcontent, null, piclist);
        }

    }


    private class UpdateJobInfoHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            updateJobInfoHandler(resp);
        }
    }


    public void updateJobInfoHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            BaseJson baseJson = new Gson().fromJson(resp, BaseJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {

                Util.Toast(this, "信息完善成功");

//                Util.startActivity(CompleteInfoActivity.this, IndexActivity_.class);
                finish();
            }

        }
    }


}




