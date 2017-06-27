package com.yj.navigation.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yj.navigation.R;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.component.SZ_PayPopwindow_Avar;
import com.yj.navigation.component.SZ_PayPopwindow_Common;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.BaseJson;
import com.yj.navigation.object.ImageAvarJson;
import com.yj.navigation.object.RegisterCompleteson;
import com.yj.navigation.prefs.ConfigPref_;
import com.yj.navigation.util.Base64Util;
import com.yj.navigation.util.Constant;
import com.yj.navigation.util.FileUtil;
import com.yj.navigation.util.ImageLoaderUtil;
import com.yj.navigation.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.File;

/**
 * Created by zhang on 2015/8/7.
 */
@EActivity(R.layout.mineinfo_view)
public class MineInfoActivity extends BaseActivity {

    @Pref
    ConfigPref_ configPref;


    @ViewById(R.id.image_avar_id)
    ImageView image_avar_id;


    @Click(R.id.left_title_line)
    void onLeftTitleLine() {

        finish();

    }

    public void updateUi(){
        String headImg=configPref.userHeadImg().get();
        if(headImg!=null&&!headImg.equals("")){
            imageLoader.displayImage(headImg, mine_avar_id, options);
        }

    }
    @Click(R.id.change_mine_info_line)
    void onChangeMineInfoId() {


        new SZ_PayPopwindow_Avar().showPopwindow(this, image_avar_id, new SZ_PayPopwindow_Avar.CallBackPayWindow() {
            @Override
            public void handleCallBackPayWindowFromCamara() {

                Intent intentFromCapture = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                // 判断存储卡是否可以用，可用进行存储
                if (FileUtil.checkSDCard()) {
                    intentFromCapture.putExtra(
                            MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(Environment
                                    .getExternalStorageDirectory(),
                                    IMAGE_FILE_NAME)));
                }
                startActivityForResult(intentFromCapture,
                        CAMERA_REQUEST_CODE);

            }

            @Override
            public void handleCallBackPayWindowFromLib() {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    startActivityForResult(intent, SELECT_PIC_KITKAT);
                } else {
                    startActivityForResult(intent, IMAGE_REQUEST_CODE);
                }
            }
        });

    }

    @Click(R.id.change_pwd_id)
    void onChangePwdId() {
        Intent intent = new Intent(MineInfoActivity.this, ChangeMoneyPwdActivity_.class);
//        intent.putExtra("FromMineInfoActivity", true);

        startActivity(intent);
//        finish();

    }

    @Click(R.id.change_info_id)
    void onChangeInfoId() {
        Util.startActivity(this, ChangeCompleteInfoActivity_.class);

//        finish();

    }

    @Click(R.id.change_money_pwd_id)
    void onChangeMoneyPwdId() {

        new SZ_PayPopwindow_Common().showPopwindow(this, image_avar_id,"修改提现密码","回答密保，修改提现密码", new SZ_PayPopwindow_Common.CallBackPayWindow() {
            @Override
            public void handleCallBackPayWindowFromCamara() {
//需要新的页面 类似  改密码
                Intent intent = new Intent(MineInfoActivity.this, SafeQuestionActivity_.class);
                intent.putExtra("FromChangeSafePwdChooseActivity", true);

                startActivity(intent);

            }

            @Override
            public void handleCallBackPayWindowFromLib() {


            }
        });


//        Util.startActivity(this, ChangeSafePwdChooseActivity_.class);

//        finish();

//        FoxShuashuaPayPasswordInterface foxShuashuaPayPasswordInterface = new FoxShuashuaPayPasswordInterface();
//        foxShuashuaPayPasswordInterface.startProgressBar(this, "", new FoxShuashuaPayPasswordInterface.CallBackPasswordDialog() {
//            @Override
//            public void handleDialogResultOk(GridPasswordView gpv_normal_paypassword, String pwd, Dialog dialog) {
//
//                //处理网络修改密码
//                changeSafePwd(pwd, dialog);
//            }
//
//
//        });

    }


    String mobile;

    @Click(R.id.logining_btn_rel_id)
    void onLoginingBtnRelId() {

//退出登录
        loginout();

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
    ImageView  mine_avar_id;
    public void initUi() {
        imageLoader = ImageLoader.getInstance();
        options = ImageLoaderUtil.getAvatarOptionsInstance();

        RelativeLayout main_title_id = (RelativeLayout) findViewById(R.id.main_title_id);
        main_title_id.setBackgroundColor(getResources().getColor(R.color.white_alpha80));

        ImageView left_title_icon = (ImageView) findViewById(R.id.left_title_icon);
        left_title_icon.setVisibility(View.VISIBLE);

        TextView left_title = (TextView) findViewById(R.id.left_title);
        left_title.setVisibility(View.VISIBLE);
        left_title.setText("返回");

        ImageView right_title_icon = (ImageView) findViewById(R.id.right_title_icon);
        right_title_icon.setVisibility(View.GONE);

        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText("个人中心");
        title.setTextColor(getResources().getColor(R.color.line_hot_all));
        View title_line_id = (View) findViewById(R.id.title_line_id);
        title_line_id.setVisibility(View.GONE);
        mine_avar_id = (ImageView) findViewById(R.id.image_avar_id);


        LinearLayout right_title_line = (LinearLayout) findViewById(R.id.right_title_line);

        right_title_line.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });


        TextView version_id = (TextView) findViewById(R.id.version_id);

        version_id.setText(""+Util.getAppVersionName(this));


    }

    @AfterViews
    void init() {
        initUi();
    }


    @Override
    protected void initActivityName() {
        activityName = MineInfoActivity.class.getName();
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
    }


    //**********获取筛选的后的list***************/
    FoxProgressbarInterface foxProgressbarInterface;

    public void uploadAvar(String file, String fileSuffix) {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");


        ProtocolUtil.uploadAvarFunction(this, new UploadAvarHandler(), configPref.userToken().get(), file, fileSuffix, "1");


    }


    private class UploadAvarHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            uploadAvarHandler(resp);
        }
    }


    public void uploadAvarHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            ImageAvarJson baseJson = new Gson().fromJson(resp, ImageAvarJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {
                Log.d("path:", baseJson.data.path);

                Log.d("url:", baseJson.data.url);
                //保存token
                configPref.userHeadImg().put(baseJson.data.url);//是url还是path
                completeRegisterServerForMsg(baseJson.data.url);


//                Util.startActivity(MineInfoActivity.this, RegActivity_.class);
//                finish();
            }

        }
    }



    public void completeRegisterServerForMsg( String headUrl) {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");

        String clientVersion = Util.getAppVersionName(this);

        ProtocolUtil.updateUserInfoFunction(this, new UpdateUserInfoHandler(), configPref.userToken().get(), headUrl, configPref.userName().get(),null);


    }


    private class UpdateUserInfoHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            UpdateUserInfoHandler(resp);
        }
    }


    public void UpdateUserInfoHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            RegisterCompleteson baseJson = new Gson().fromJson(resp, RegisterCompleteson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {

                image_avar_id.setImageBitmap(tempCropBitmap);
                //保存token
                configPref.userHeadImg().put(baseJson.headImg);
                configPref.userId().put(baseJson.userId);
                configPref.userName().put(baseJson.userName);
//                configPref.userToken().put(baseJson.accessToken);

//                Util.startActivity(this, SafeQuestionActivity_.class);

//                Util.startActivity(CompleteInfoActivity.this, IndexActivity_.class);
//                finish();
            }

        }
    }


    //注销
    public void loginout() {
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");


        ProtocolUtil.loginOutFunction(this, new LoginOutHandler(), configPref.userToken().get());


    }


    private class LoginOutHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            loginOutHandler(resp);
        }
    }


    public void loginOutHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            BaseJson baseJson = new Gson().fromJson(resp, BaseJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {

                //保存token
//                configPref.userName().put("");

                configPref.userToken().put("");
                Util.startActivity(MineInfoActivity.this, RegActivity_.class);
                finish();
            }

        }
    }

    Dialog dialogD = null;

    //修改安全密码
    public void changeSafePwd(String pwdsafe, Dialog dialog) {
        dialogD = dialog;
        foxProgressbarInterface = new FoxProgressbarInterface();
        foxProgressbarInterface.startProgressBar(this, "加载中...");

        ProtocolUtil.updateSafePwdFunction(this, new UpdateSafePwdHandler(), configPref.userToken().get(), pwdsafe, "1");


    }


    private class UpdateSafePwdHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            updateSafePwdHandler(resp);
        }
    }


    public void updateSafePwdHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {

            BaseJson baseJson = new Gson().fromJson(resp, BaseJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {
                if (dialogD != null) dialogD.dismiss();


                Util.Toast(this, "安全密码设置成功");
            }
        }
    }

    //********************************图片选择*********************************************
    Bitmap tempCropBitmap;
    boolean changeImgAvar = true;

    @OnActivityResult(RESULT_REQUEST_CODE)
    void onActivityResultCropAvatar(int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) return;
        if (data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                tempCropBitmap = extras.getParcelable("data");
                if (tempCropBitmap != null) {
                    changeImgAvar = false;


                    byte[] avarByte = Util.getBitmapByte(tempCropBitmap);
                    String avarBase64 = Base64Util.encode(avarByte);
                    uploadAvar(avarBase64, "jpg");

//                    ProtocolUtil.updateAvar(this, new AsyncHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//
//                        }
//
//                        @Override
//                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//
//                        }
//                    },configPref.userToken().get(),Util.getBitmapByte(tempCropBitmap));

                    //上传头像

                }
            }
        }
    }


    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int SELECT_PIC_KITKAT = 3;
    private static final int RESULT_REQUEST_CODE = 4;

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final String IMAGE_FILE_NAME = "tempSSAvatar.jpg";


    @OnActivityResult(SELECT_PIC_KITKAT)
    void onActivityResultSelectPicKitkat(int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) return;
        startPhotoZoom(data.getData());
    }

    @OnActivityResult(IMAGE_REQUEST_CODE)
    void onActivityResultSelectPic(int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) return;
        startPhotoZoom(data.getData());
    }

    @OnActivityResult(CAMERA_REQUEST_CODE)
    void onActivityResultCameraPic(int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) return;
        if (FileUtil.checkSDCard()) {
            File tempFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
            startPhotoZoom(Uri.fromFile(tempFile));
        } else {
            Util.showToast(this, "没有sdcard");
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
            return;
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String url = getPath(this, uri);
            intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
        } else {
            intent.setDataAndType(uri, "image/*");
        }

        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    //原本uri返回的是file:  android4.4返回的是content:///...
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
