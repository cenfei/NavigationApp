package org.camera.camera;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import org.camera.activity.CameraSurfaceTextureActivity;
import org.camera.encode.VideoEncoderFromBuffer;
import org.camera.encode.VideoEncoderFromSurface;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

@SuppressLint("NewApi")
public class CameraWrapper {
	private static final String TAG = "CameraWrapper";
	private Camera mCamera;
	private Camera.Parameters mCameraParamters;
	private static CameraWrapper mCameraWrapper;
	private boolean mIsPreviewing = false;
	private float mPreviewRate = -1.0f;
	public static int IMAGE_HEIGHT = 1080;
	public static int IMAGE_WIDTH = 1920;
	private CameraPreviewCallback mCameraPreviewCallback;
	private byte[] mImageCallbackBuffer = new byte[CameraWrapper.IMAGE_WIDTH
			* CameraWrapper.IMAGE_HEIGHT * 3 / 2];

	public interface CamOpenOverCallback {
		public void cameraHasOpened();
	}

	private CameraWrapper() {
	}

	public static synchronized CameraWrapper getInstance() {
		if (mCameraWrapper == null) {
			mCameraWrapper = new CameraWrapper();
		}
		return mCameraWrapper;
	}

	public void doOpenCamera(CamOpenOverCallback callback) {
		Log.i(TAG, "Camera open....");


		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int numCameras = Camera.getNumberOfCameras();
		Camera.CameraInfo info = new Camera.CameraInfo();
		for (int i = 0; i < numCameras; i++) {
			Camera.getCameraInfo(i, info);
			if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
				mCamera = Camera.open(i);
				break;
			}
		}
		if (mCamera == null) {
			Log.d(TAG, "No front-facing camera found; opening default");
			mCamera = Camera.open();    // opens first back-facing camera
		}
		if (mCamera == null) {
			throw new RuntimeException("Unable to open camera");
		}
		Log.i(TAG, "Camera open over....");
		callback.cameraHasOpened();
	}

	public void doStartPreview(SurfaceHolder holder, float previewRate) {
		Log.i(TAG, "doStartPreview...");
		if (mIsPreviewing) {
			this.mCamera.stopPreview();
			return;
		}

		try {
			this.mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		initCamera();
	}

	public void doStartPreview(SurfaceTexture surface, float previewRate) {
		Log.i(TAG, "doStartPreview()");
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (mIsPreviewing) {
			this.mCamera.stopPreview();
			return;
		}

		try {
			this.mCamera.setPreviewTexture(surface);
		} catch (IOException e) {
			e.printStackTrace();
		}
		initCamera();
	}

	public void doStopCamera() {
		Log.i(TAG, "doStopCamera");
		if (this.mCamera != null) {
			mCameraPreviewCallback.close();
			this.mCamera.setPreviewCallback(null);
			this.mCamera.stopPreview();
			this.mIsPreviewing = false;
			this.mPreviewRate = -1f;
			this.mCamera.release();
			this.mCamera = null;
		}
	}

	private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
		final double ASPECT_TOLERANCE = 0.05;
		double targetRatio = (double) w / h;
		if (sizes == null)
			return null;
		Camera.Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;
		int targetHeight = h;
		// Try to find an size match aspect ratio and size
		for (Camera.Size size : sizes) {
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
				continue;
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}
		// Cannot find the one match the aspect ratio, ignore the requirement
		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Camera.Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}

	private void initCamera() {
		if (this.mCamera != null) {
			this.mCameraParamters = this.mCamera.getParameters();
			this.mCameraParamters.setPreviewFormat(ImageFormat.NV21);
			this.mCameraParamters.setFlashMode("off");
			this.mCameraParamters.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
			this.mCameraParamters.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
			List<Camera.Size> sizes = mCameraParamters.getSupportedPreviewSizes();

			Camera.Size prosize = getOptimalPreviewSize(sizes, IMAGE_WIDTH, IMAGE_HEIGHT);
//			WindowManager windowManager = getWindowManager();
//			Display display = windowManager.getDefaultDisplay();
//			int screenWidth = screenWidth = display.getWidth();
//			int screenHeight = screenHeight = display.getHeight();
			IMAGE_WIDTH = prosize.width;
			IMAGE_HEIGHT = prosize.height;
			Log.e("prosize:", "w:" + IMAGE_WIDTH + ",H:" + IMAGE_HEIGHT);

			this.mCameraParamters.setPreviewSize(IMAGE_WIDTH, IMAGE_HEIGHT);

//			this.mCamera.setDisplayOrientation(90);
			mCameraPreviewCallback = new CameraPreviewCallback();
			mCamera.addCallbackBuffer(mImageCallbackBuffer);
			mCamera.setPreviewCallbackWithBuffer(mCameraPreviewCallback);
			List<String> focusModes = this.mCameraParamters.getSupportedFocusModes();
			if (focusModes.contains("continuous-video")) {
				this.mCameraParamters
						.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
			}
			this.mCamera.setParameters(this.mCameraParamters);


			this.mCamera.startPreview();
			this.mCamera.cancelAutoFocus();
			this.mIsPreviewing = true;
		}
	}

	public void startVideo() {

		//mCameraPreviewCallback.start();
		startVideo = true;
	}

	public void stopVideo() {
		startVideo = false;
		mCameraPreviewCallback.close();
	}


	boolean startVideo = false;

	int countImage=0;


	class CameraPreviewCallback implements Camera.PreviewCallback {
		private static final String TAG = "CameraPreviewCallback";
		private VideoEncoderFromBuffer videoEncoder = null;

		private CameraPreviewCallback() {
			start();
			countImage=0;
		}

		void close() {
			videoEncoder.close();
		}

		void start() {
			//startVideo=true;
			videoEncoder = new VideoEncoderFromBuffer(CameraWrapper.IMAGE_WIDTH,
					CameraWrapper.IMAGE_HEIGHT);


		}

		@Override
		public void onPreviewFrame(byte[] data, Camera camera) {
			Log.i(TAG, "onPreviewFrame");
			if (startVideo) {
				long startTime = System.currentTimeMillis();
				videoEncoder.encodeFrame(data/*, encodeData*/);
				long endTime = System.currentTimeMillis();
				Log.i(TAG, Integer.toString((int) (endTime - startTime)) + "ms");

if(countImage<30) {
	Camera.Size size = camera.getParameters().getPreviewSize();
	if (mDetectThread == null) {
		mDetectThread = new DetectThread();
		mDetectThread.start();

	}

	mDetectThread.addDetect(data, size.width, size.height);
	 countImage++;
}
			}
			camera.addCallbackBuffer(data);




		}
	}
DetectThread mDetectThread;

	public Bitmap runInPreviewFrame(byte[] data, int w,int h ) {
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
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
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
		private int width;
		private int height;

		public void stopRun() {
			addDetect(new byte[]{0}, -1, -1);
		}
		public void addDetect(byte[] data, int width, int height) {
			if (mPreviewQueue.size() == 1) {
				mPreviewQueue.clear();
			}
			mPreviewQueue.add(data);
			this.width = width;
			this.height = height;
		}
		@Override
		public void run() {
			try {
				while (true) {
					byte[] data = mPreviewQueue.take();// block here, if no data
					// in the queue.
					if (data.length == 1) {// quit the thread, if we got special
						// byte array put by stopRun().
						return;
					}

			Bitmap bitmap=		runInPreviewFrame(data,width,height);
				//	VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE_NAME=System.currentTimeMillis()+".jpg";

		String filename=countImage+".jpg";
					saveFile(bitmap,filename,VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE+ CameraSurfaceTextureActivity.mp4FileName
							);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}