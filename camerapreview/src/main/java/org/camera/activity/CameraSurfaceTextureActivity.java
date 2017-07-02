package org.camera.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import com.example.camerapreview.R;

import org.camera.camera.CameraWrapper;
import org.camera.camera.CameraWrapper.CamOpenOverCallback;
import org.camera.encode.VideoEncoderFromSurface;
import org.camera.preview.CameraTexturePreview;

import java.io.File;

@SuppressLint("NewApi")
public class CameraSurfaceTextureActivity extends Activity implements CamOpenOverCallback{
	private static final String TAG = "CameraPreviewActivity";
	private CameraTexturePreview mCameraTexturePreview;
	private float mPreviewRate = -1f;


	public static  String mp4FileName="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_preview);

		mp4FileName=getIntent().getStringExtra("mp4FileNameNumber");

		File dirFile = new File(VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File dirFileIMG = new File(VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE+mp4FileName+"/");
		if (!dirFileIMG.exists()) {
			dirFileIMG.mkdir();
		}


		initUI();
		initViewParams();


		VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE_NAME=System.currentTimeMillis()+".jpg";


		Button camera_textureview_btn = (Button) findViewById(R.id.camera_textureview_btn);
		camera_textureview_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				CameraWrapper.getInstance().startVideo();
			}
		});


		Button	camera_textureview_stop_btn = (Button) findViewById(R.id.camera_textureview_stop_btn);
		camera_textureview_stop_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				CameraWrapper.getInstance().stopVideo();
			}
		});
	}


	@Override
	protected void onResume() {
		super.onResume();


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

	}

	@Override
	protected void onPause() {
		super.onPause();
//		Thread openThread = new Thread() {
//			@Override
//			public void run() {
//				CameraWrapper.getInstance().doStopCamera();
//			}
//		};
//		openThread.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();


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

	@Override
	public void cameraHasOpened() {
		SurfaceTexture surface = this.mCameraTexturePreview.getSurfaceTexture();
		CameraWrapper.getInstance().doStartPreview(surface, mPreviewRate);
	}
}
