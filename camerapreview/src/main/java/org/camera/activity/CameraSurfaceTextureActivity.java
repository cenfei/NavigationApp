package org.camera.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.camerapreview.R;

import org.camera.camera.CameraWrapper;
import org.camera.camera.CameraWrapper.CamOpenOverCallback;
import org.camera.encode.VideoEncoderFromSurface;
import org.camera.preview.CameraTexturePreview;

import java.io.File;

@SuppressLint("NewApi")
public class CameraSurfaceTextureActivity extends Activity implements CamOpenOverCallback{
	private static final String TAG = "CameraPreviewActivity1";
	private CameraTexturePreview mCameraTexturePreview;
	private float mPreviewRate = -1f;


	public static  String mp4FileName="";

	boolean boolfirst=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		if(boolfirst){
			boolfirst=false;

		}else{
			finish();
			return;
		}

		super.onCreate(savedInstanceState);


		setContentView(R.layout.activity_camera_preview);
//		if(!TextUtils.isEmpty(mp4FileName)) {
//
//			finish();
//			return;
//
//		}
		mp4FileName=getIntent().getStringExtra("mp4FileNameNumber");

		if(TextUtils.isEmpty(mp4FileName)) {
		finish();
				return;
		}
		File dirFileF = new File("/sdcard/Movies");
		if (!dirFileF.exists()) {
			dirFileF.mkdir();
		}

		File dirFile = new File(VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File dirFileIMG = new File(VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE+mp4FileName+"/");
		if (!dirFileIMG.exists()) {
			dirFileIMG.mkdir();
		}
//		mp4FileName=null;


		initUI();
		initViewParams();


		VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE_NAME=System.currentTimeMillis()+".jpg";
			 comment_time_id = (TextView) findViewById(R.id.comment_time_id);


	final 	RelativeLayout	 camera_content_id = (RelativeLayout) findViewById(R.id.camera_content_id);

	final	ImageView camera_textureview_btn = (ImageView) findViewById(R.id.camera_textureview_btn);
		camera_textureview_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				camera_textureview_btn.setVisibility(View.GONE);
				mHandler.post(runnable);
				CameraWrapper.getInstance().startVideo();
				camera_content_id.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {

						Toast.makeText(CameraSurfaceTextureActivity.this,"保存成功",Toast.LENGTH_LONG);
						CameraWrapper.getInstance().stopVideo();
					}
				});
			}
		});

		findViewById(R.id.left_title_icon3).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mHandler.removeCallbacks(runnable);
				CameraWrapper.getInstance().stopVideo();

				finish();
			}
		});




//		Intent it = new Intent(this, MyService.class);
//		startService(it);
	}
	TextView	 comment_time_id;


	boolean goontime=true;
	protected Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//要做的事情
			if (goontime) {


				Message msg = new Message();
				msg.what = 1;  //消息(一个整型值)
				mHandler.sendMessage(msg);
				mHandler.postDelayed(this, 1000);

			}
		}
	};

	//在主线程里面处理消息并更新UI界面
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 1:
					long sysTime = System.currentTimeMillis();
					CharSequence sysTimeStr = DateFormat.format("yyyy-MM-dd HH:mm:ss", sysTime);
						 comment_time_id.setText(sysTimeStr); //更新时间
					  break;
			default:
				break;

			}
		}
	};
	@Override
	protected void onResume() {
		super.onResume();

		if(TextUtils.isEmpty(mp4FileName)) return;
//		CameraWrapper.getInstance().doOpenCamera(CameraSurfaceTextureActivity.this);
		Thread openThread = new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				CameraWrapper.getInstance().doOpenCamera(CameraSurfaceTextureActivity.this);
			}
		};
		openThread.start();

//		Intent it = new Intent(this, MyService.class);
//		bindService(it, mServiceConn, BIND_AUTO_CREATE);

	}

	@Override
	protected void onPause() {
		super.onPause();
		if(TextUtils.isEmpty(mp4FileName)) return;
		Thread openThread = new Thread() {
			@Override
			public void run() {
				CameraWrapper.getInstance().doStopCamera();
			}
		};
		openThread.start();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
//		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		unbindService(mServiceConn);


	}

	@Override
    protected void onStart() {  
		Log.i(TAG, "onStart");
        super.onStart();  
        

    } 
	
	private void initUI() {
		mCameraTexturePreview = (CameraTexturePreview) findViewById(R.id.camera_textureview);
	}
	
	private void initViewParams() { 
		LayoutParams params = mCameraTexturePreview.getLayoutParams();
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();  
        int screenWidth = displayMetrics.widthPixels;  
        int screenHeight = displayMetrics.heightPixels; 
        params.width = screenWidth;  
        params.height = screenHeight;   
        this.mPreviewRate = (float)screenHeight / (float)screenWidth; 
        mCameraTexturePreview.setLayoutParams(params);
	}

//	MyService mService;
	@Override
	public void cameraHasOpened() {
		if(TextUtils.isEmpty(mp4FileName)) return;
		SurfaceTexture surface = this.mCameraTexturePreview.getSurfaceTexture();
		CameraWrapper.getInstance().doStartPreview(surface, mPreviewRate,this);
	}
//	public ServiceConnection mServiceConn = new ServiceConnection(){
//		@Override
//		public void onServiceConnected(ComponentName name, IBinder service) {
//			mService = ((MyService.ServiceBinder)service).getService();
//			Log.d(TAG, "onServiceConnected: mService = " + mService);
//
////			if(mService != null){
////				mService.notifyToActivity(false, true);
////			}
//		}
//
//		@Override
//		public void onServiceDisconnected(ComponentName name) {
//			mService = null;
//		}
//	};
}
