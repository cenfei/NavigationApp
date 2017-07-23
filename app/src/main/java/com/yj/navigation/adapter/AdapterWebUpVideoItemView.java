package com.yj.navigation.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yj.navigation.R;
import com.yj.navigation.activity.ShowVideoUpWebActivity;
import com.yj.navigation.base.MainApp;
import com.yj.navigation.component.FoxProgressbarInterface;
import com.yj.navigation.network.ProtocolUtil;
import com.yj.navigation.network.RowMessageHandler;
import com.yj.navigation.object.UpWebInfoJson;
import com.yj.navigation.util.Constant;
import com.yj.navigation.util.FileUtil;
import com.yj.navigation.util.GpsLocation;
import com.yj.navigation.util.ImageLoaderUtil;
import com.yj.navigation.util.MyStringUtils;
import com.yj.navigation.util.PutObjectSamples;
import com.yj.navigation.util.ReadFile;
import com.yj.navigation.util.Util;

import org.camera.encode.VideoEncoderFromSurface;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sam on 2015/7/27.
 */
public class AdapterWebUpVideoItemView extends BaseAdapter {

    private static Context context;
    public List<String> mPersonal;
    private LayoutInflater mInflater;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    private String tokenm;

    public AdapterWebUpVideoItemView(Context context, List<String> personalList, String token) {
        super();
        this.tokenm = token;
        this.context = context;
        this.mPersonal = personalList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imageLoader = ImageLoader.getInstance();
        options = ImageLoaderUtil.getAvatarOptionsInstance();

    }

    public void addList(List<String> addList) {
        if (mPersonal == null || mPersonal.size() == 0) {
            mPersonal = addList;
        } else {
            mPersonal.addAll(addList);
        }

    }


    @Override
    public int getCount() {
        return this.mPersonal != null ? this.mPersonal.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return mPersonal.get(i);
    }

//    @Override
//    public String getItem(int position) {
//        return this.mPersonal.get(position);
//    }

    @Override
    public long getItemId(int position) {
        return position;
    }
TextView updateTextview=null;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String personalRanking = mPersonal.get(position);

        final ViewHolder viewholder;
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.video_web_item, null);
            convertView.setTag(viewholder);
            viewholder.video_img_id = (ImageView) convertView.findViewById(R.id.video_img_id);
            viewholder.time_web_text = (TextView) convertView.findViewById(R.id.time_web_text);
            viewholder.update_web_button = (TextView) convertView.findViewById(R.id.update_web_button);
            viewholder.delete_web_button = (TextView) convertView.findViewById(R.id.delete_web_button);


        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }


        viewholder.time_web_text.setText(personalRanking);
        final String mp4Num = personalRanking.substring(0, personalRanking.indexOf(".mp4"));

//        String imgUrl = "file:///mnt" + VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE + mp4Num + "/1.jpg";
        String mp4ImgDir = VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE + mp4Num + "/";
        List<String> listImageName = null;
        try {
            listImageName = ReadFile.readfileOnlyFile(mp4ImgDir);
//            handler.sendEmptyMessage(0);
        } catch (IOException e) {
            e.printStackTrace();
        }





        String imgUrl="file:///mnt"+ VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE+mp4Num+"/"+listImageName.get(0);






//        imgUrl=String.format("file:///mnt"+VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE+"s%/s%.jpg",mp4Num,mp4Num);

        Log.e("video:imgurl", imgUrl);

//        String imgUrl = "file:///mnt/sdcard/Movies/mp4/2/1.jpg";

        imageLoader.displayImage(imgUrl, viewholder.video_img_id, options);
        viewholder.video_img_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mp4Num = personalRanking.substring(0, personalRanking.indexOf(".mp4"));

                //跳转到 show 页面
                String mp4ImgDir = VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE + mp4Num + "/";
                List<String> listImageName = null;
                try {
                    listImageName = ReadFile.readfileOnlyFile(mp4ImgDir);
//            handler.sendEmptyMessage(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MainApp mainApp = (MainApp) context.getApplicationContext();
                List<String> listImage = new ArrayList<String>();

                for (String imgname : listImageName) {
                    String imgUrl = "file:///mnt" + VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE + mp4Num + "/" + imgname;
                    listImage.add(imgUrl);
                }

                mainApp.upImageJsonList = listImage;


                Intent intent = new Intent(context, ShowVideoUpWebActivity.class);

                context.startActivity(intent);
            }
        });
        viewholder.delete_web_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//删除当前上传图片---以及mp4
                delFileMp4AndImg(personalRanking);

            }
        });

        viewholder.update_web_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上传图片参数
                updateTextview=viewholder.update_web_button;
                selectMp4 = personalRanking;
                getJobListFromServerForMsg();

            }
        });

        return convertView;
    }

    public void delFileMp4AndImg(String mp4filename){
        String mp4Num = mp4filename.substring(0, mp4filename.indexOf(".mp4"));

        File mpFile = new File(VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE + mp4filename);
        if (mpFile.exists())         //如果文件本身就是目录 ，就要删除目录
            mpFile.delete();
        File mpImgFile = new File(VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE + mp4Num);
        FileUtil.deleteAll(mpImgFile);

    }
Integer lastprocess=0;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
switch (message.what){

    case 1:
        String process=message.getData().getString("process");
        Integer nowProcess=Integer.valueOf(process);

        if(nowProcess>lastprocess){

            updateTextview.setText("正上传"+process+"%");
            lastprocess=nowProcess;
        }



        break;
    case 2:
        String processvalue=message.getData().getString("processvalue");
        Util.Toast(context,processvalue,null);
        updateTextview.setText(processvalue);
        mPersonal.remove(selectMp4);
        notifyDataSetChanged();
        break;


}


            return false;
        }
    });


    private String selectMp4 = null;


    private static class ViewHolder {
        ImageView video_img_id;

        TextView time_web_text;
        TextView update_web_button;
        TextView delete_web_button;
    }


    //**********获取筛选的后的list***************/
    FoxProgressbarInterface foxProgressbarInterface;
    List<String> listImageName = null;
    String mp4ImgDir = null;

    public void getJobListFromServerForMsg() {

        String mp4Num = selectMp4.substring(0, selectMp4.indexOf(".mp4"));

        //跳转到 show 页面
        mp4ImgDir = VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE + mp4Num + "/";
        try {
            listImageName = ReadFile.readfileOnlyFile(mp4ImgDir);
//            handler.sendEmptyMessage(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (listImageName != null && listImageName.size() > 0) {
            foxProgressbarInterface = new FoxProgressbarInterface();
            foxProgressbarInterface.startProgressBar(context, "加载中...");
            String filenum = listImageName.size() + "";
            String biztype = "1";
            String fileFormat = "jpg";
            String date = MyStringUtils.getNowTimeFormat2(new Date());
            GpsLocation.initLocation(context);
//获取经纬度
            Log.d("经度：",GpsLocation.longitude+"");
            Log.d("纬度：",GpsLocation.latitude+"");



            ProtocolUtil.uploadApplyFunction(context, new MyJobListHandler(), tokenm, filenum, biztype, fileFormat, date
            ,GpsLocation.latitude+"",GpsLocation.longitude+"");
        }

    }


    private class MyJobListHandler extends RowMessageHandler {
        @Override
        protected void handleResp(String resp) {
            myJobListHandler(resp);
        }
    }

    UpWebInfoJson baseJson;

    public void myJobListHandler(String resp) {
        foxProgressbarInterface.stopProgressBar();
        if (resp != null && !resp.equals("")) {


            baseJson = new Gson().fromJson(resp, UpWebInfoJson.class);
            if (baseJson.retCode.equals(Constant.RES_SUCCESS)) {
                //aliyun sdk

                endpoint = baseJson.endpoint;
                accessKeyId = baseJson.accessKeyId;
                accessKeySecret = baseJson.accessKeySecret;
                testBucket = baseJson.bucketName;
                final String uploadObject = baseJson.fileDir;
                accessToken = baseJson.securityToken;
                initOss();

new Thread(new Runnable() {
    @Override
    public void run() {
        for (String imgname : listImageName) {

            uploadAliyun(imgname, mp4ImgDir + imgname, uploadObject);
//                            break;//fox测试
        }
    }
}).start();


            }

        }
    }

    private OSS oss;

    // 运行sample前需要配置以下字段为有效的值
    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static String accessKeyId = "STS.Mbw95rG1RyAiCV3haghQeVmaR";
    private static String accessKeySecret = "7uyBDBuNvqtrLNTMUBpsGaujDitFCVfEq4QZhQAFYyAv";
    private static String uploadFilePath = "<upload_file_path>";

    private static String testBucket = "tjh-test";
//    private static  String uploadObject = "A17071015072621710000/";
//    private static  String downloadObject = "sampleObject";

    private static String accessToken = "";

    private final String DIR_NAME = "oss";
    private final String FILE_NAME = "caifang.m4a";

    public void initOss() {

        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(accessKeyId, accessKeySecret, accessToken);

//        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);

//        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(450 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(450 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(context.getApplicationContext(), endpoint, credentialProvider, conf);
    }


    int countSuccess=0;

    public void uploadAliyun(String filename, String filepath, String uploadObject0) {


        String uploadFilePath = filepath;
//        uploadFilePath="/sdcard/Movies/mp4/1/"+filename;
        String uploadObject = uploadObject0 + filename;
        try {
            Log.i("MainActivity : ", "uploadFilePath : " + uploadFilePath);
            File uploadFile = new File(uploadFilePath);
            InputStream input = new FileInputStream(uploadFile);
            long fileLength = uploadFile.length();
            Log.i("MainActivity : ", "fileLength : " + fileLength);
        } catch (Exception e) {
            e.printStackTrace();
        }
//                new PutObjectSamples(oss, testBucket, uploadObject, uploadFilePath).asyncPutObjectFromLocalFile();
        new PutObjectSamples(oss, testBucket, uploadObject, uploadFilePath).asyncPutObjectWithServerCallback(baseJson, filename,
                new OSSProgressCallback<PutObjectRequest>() {
                    @Override
                    public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                        Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);

long process=(100*currentSize*(countSuccess+1))/(totalSize*listImageName.size());

                        Message message = new Message();
                        message.what = 1;
                        Bundle bundle=new Bundle();
                        bundle.putString("process",process+"");

                        message.setData(bundle);



                        handler.sendMessage(message);
//                        updateTextview.setText("正上传"+process);
                    }
                }, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                        Log.d("PutObject", "UploadSuccess");

                        // 只有设置了servercallback，这个值才有数据
                        String serverCallbackReturnJson = result.getServerCallbackReturnBody();

                        if(serverCallbackReturnJson.contains("OK")){
                            countSuccess++;

                            Log.i("countSuccess","countsuccess"+countSuccess+"size:"+listImageName.size()+",mp4:"+selectMp4);
                            if(countSuccess>=listImageName.size()){
                                //上传成功
                                delFileMp4AndImg(selectMp4);
//                                updateTextview.setText("上传成功");
//                                Util.Toast(context,"上传成功",null);
                                Message message = new Message();
                                message.what = 2;
                                Bundle bundle=new Bundle();
                                bundle.putString("processvalue","上传成功");

                                message.setData(bundle);



                                handler.sendMessage(message);
                            }

                        }

                        Log.d("servercallback", serverCallbackReturnJson);
                    }

                    @Override
                    public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                        // 请求异常
                        if (clientException != null) {
                            // 本地异常如网络异常等
                            clientException.printStackTrace();
                        }
                        if (serviceException != null) {
                            // 服务异常
                            Log.e("ErrorCode", serviceException.getErrorCode());
                            Log.e("RequestId", serviceException.getRequestId());
                            Log.e("HostId", serviceException.getHostId());
                            Log.e("RawMessage", serviceException.getRawMessage());
                        }
                    }
                }

        );

    }

}
