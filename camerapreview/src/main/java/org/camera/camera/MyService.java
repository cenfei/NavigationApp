package org.camera.camera;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import org.camera.activity.CameraSurfaceTextureActivity;
import org.camera.encode.VideoEncoderFromSurface;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import static org.camera.preview.CameraSurfacePreview.TAG;

public class MyService extends Service {
public static byte[] dataAll=null;

    public MyService() {
    }
//    public class ServiceBinder extends Binder {
//        public MyService getService(){
//            return MyService.this;
//        }
//    }
//
//    ServiceBinder mBinder;

    @Override
    public void onCreate() {
        super.onCreate();
//        mBinder=new ServiceBinder();


    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        byte[] bytedata = dataAll;

       int w = intent.getIntExtra("width",0);
        int h = intent.getIntExtra("height",0);
        int countImage = intent.getIntExtra("countImage",0);
if(bytedata!=null) {
      addTaskInQueue(bytedata,w,h,countImage);
}
    return super.onStartCommand(intent, flags, startId);

    }
    int  countImage=0;
    public void addTaskInQueue(byte[] data,int w,int h,int count){
        if (mDetectThread == null) {
            mDetectThread = new DetectThread();
            mDetectThread.start();
            countImage=0;
        }
            countImage=count;
            mDetectThread.addDetect(data, w, h,count);




//        if(mDownloadQueue != null){
//            mDownloadQueue.add(ti);
//            Log.d(TAG, "addTaskInQueue id = " + ti.getTaskId());
//        }
//
//        if(isRunning == false && mDownloadQueue.size() > 0){
//            startDownload();
//        }
    }

    public void stopTaskInQueue(){
        if (mDetectThread != null) {
            mDetectThread.stopRun();
        }
    }

    DetectThread mDetectThread;

    public Bitmap runInPreviewFrame(byte[] data, int w, int h ) {
        //处理data
//		Camera.Size previewSize = camera.getParameters().getPreviewSize();//获取尺寸,格式转换的时候要用到
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;

        YuvImage yuvimage = new YuvImage(
                data,
                ImageFormat.NV21,
                w,
                h,
                null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        yuvimage.compressToJpeg(new Rect(0, 0, w, h), 100, baos);// 80--JPG图片的质量[0-100],100最高
        byte[] rawImage = baos.toByteArray();
        //将rawImage转换成bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length, options);

        return bitmap;
    }


    public Bitmap runInPreviewFrameSmall(byte[] data, int w, int h ) {
        //处理data
//		Camera.Size previewSize = camera.getParameters().getPreviewSize();//获取尺寸,格式转换的时候要用到
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;

        YuvImage yuvimage = new YuvImage(
                data,
                ImageFormat.NV21,
                w,
                h,
                null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        yuvimage.compressToJpeg(new Rect(0, 0, w, h), 100, baos);// 80--JPG图片的质量[0-100],100最高
        byte[] rawImage = baos.toByteArray();
        //将rawImage转换成bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize=8;
        Bitmap bitmap = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length, options);

        return bitmap;
    }


    public void saveFile(Bitmap bm, String fileName, String path) throws IOException {
//		String path = getSDPath() +"/revoeye/";
        BufferedOutputStream bos=null;
        try {

            File dirFile = new File(path);
            if (!dirFile.exists()) {
                dirFile.mkdir();
            }
            File myCaptureFile = new File(path +"/"+ fileName);
            Log.i("后台下载 成功：",path +"/"+ fileName);
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        }catch (Exception e){
            e.printStackTrace();

        }
        finally {


            if (bm != null) {
                bm.recycle();
            }
        }
    }
    public static Bitmap getBitmap(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();
        float scale, scale2;

        matrix.postScale(0.25f, 0.25f);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
//        if (bitmap != null && !bitmap.equals(bmp) && !bitmap.isRecycled()) {
//            bitmap.recycle();
//            bitmap = null;
//        }


        return bmp;// Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }

    /*
     * @CN:功能：将每一次预览的data 存入ArrayBlockingQueue
     * 队列中，然后依次进行ismatch的验证，如果匹配就会就会进行进一步的识别
     *
     * @EN:Function: each preview of the data into the ArrayBlockingQueue queue,
     * followed by ismatch verification, if the match will be further identified
     */
    private class DetectThread extends Thread {
        private ArrayBlockingQueue<byte[]> mPreviewQueue = new ArrayBlockingQueue<byte[]>(
                1);
        private ArrayBlockingQueue<Integer> mPreviewQueueInt = new ArrayBlockingQueue<Integer>(
                1);
//        private ArrayBlockingQueue<ByteDataInfo> mPreviewQueueMap = new ArrayBlockingQueue<ByteDataInfo>(
//                1);
        private int width;
        private int height;

        public void stopRun() {
            addDetect(new byte[]{0}, -1, -1,-1);
        }
        public void addDetect(byte[] data, int width, int height,int num) {
            if (mPreviewQueue.size() == 1) {
                mPreviewQueue.clear();
                mPreviewQueueInt.clear();
            }
            mPreviewQueue.add(data);
            mPreviewQueueInt.add(num);
//            if (mPreviewQueueMap.size() == 1) {
//                mPreviewQueueMap.clear();
//            }
//
//ByteDataInfo byteDataInfo=new ByteDataInfo();
//            byteDataInfo.data=data;
//            byteDataInfo.dataNum=num;
//            mPreviewQueueMap.add(byteDataInfo);

            Log.i("mPreviewQueueMap size：",mPreviewQueue.size()+"");

            this.width = width;
            this.height = height;
        }

        int counti=0;

        @Override
        public void run() {
            try {
                while (true) {
                    byte[] data = mPreviewQueue.take();// block here, if no data


                Integer numInt=    mPreviewQueueInt.take();
                    // in the queue.
                    if (data.length == 1) {// quit the thread, if we got special

                        return;
                    }

                    Bitmap bitmap=		runInPreviewFrame(data,width,height);

                    Bitmap smallbitmap=		getBitmap(bitmap);
                    //	VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE_NAME=System.currentTimeMillis()+".jpg";

                    String filename=numInt+".jpg";
                    saveFile(bitmap,filename, VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE+ CameraSurfaceTextureActivity.mp4FileName
                    );


//
//
                    saveFile(smallbitmap,"mini"+filename, VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE+ CameraSurfaceTextureActivity.mp4FileName
                    );

                    counti++;
//                    if(counti==30){
//                        MyService.dataAll=null;
//                        stopRun();
//                        Intent intent = new Intent("org.camera.camera.MyService.Service");
//
//                        stopService(intent);
//
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
