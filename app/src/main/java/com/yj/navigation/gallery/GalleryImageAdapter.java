package com.yj.navigation.gallery;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yj.navigation.R;
import com.yj.navigation.base.MainApp;
import com.yj.navigation.object.JobImageJson;
import com.yj.navigation.util.ImageLoaderUtil;

import java.util.List;

public class GalleryImageAdapter extends BaseAdapter
{

	private Context mContext;
	public    List<JobImageJson> jobImageJsonList;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
private TextView img_count_id;
	public GalleryImageAdapter(Context c,TextView img_count_id)
	{
		this.mContext = c;
this.img_count_id=img_count_id;
		imageLoader = ImageLoader.getInstance();
		options = ImageLoaderUtil.getAvatarOptionsInstance();
	}

	public Integer[] imgs = { R.drawable.car1, R.drawable.car1, R.drawable.car1,
			R.drawable.car1, R.drawable.car1, R.drawable.car1,
			R.drawable.car1, R.drawable.car1, R.drawable.car1,
			R.drawable.car1, R.drawable.car1, R.drawable.car1,
			R.drawable.car1, R.drawable.car1, R.drawable.car1,
			R.drawable.car1, R.drawable.car1, R.drawable.car1};

	@Override
	public int getCount() {
		return jobImageJsonList.size();
	}

	@Override
	public Object getItem(int position) {
		return jobImageJsonList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final int Height = 330;
		final int Width = 330;

		img_count_id.setText(""+(position+1)+"/"+jobImageJsonList.size());

		ImageView imageView = new ImageView(mContext);
//		imageView.setBackground(mContext.getDrawable(R.drawable.rounded_image_gallery));
		imageView.setBackgroundDrawable(ContextCompat.getDrawable(mContext,R.drawable.rounded_image_gallery));

		// 设置倒影图片
//		imageView.setImageBitmap(miniBitmap);
		imageView.setLayoutParams(new GalleryView.LayoutParams((int)(Width),
				(int)(Height)));
		imageView.setScaleType(ScaleType.FIT_XY);

		MainApp mainApp = (MainApp) mContext.getApplicationContext();
		String remoteBaseUrl = mainApp.remoteBaseUrl;

		imageLoader.displayImage(remoteBaseUrl+jobImageJsonList.get(position).bigPicUrl,imageView,options);


//		imageView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				Intent intent = new Intent(mContext, ShowVideoActivity.class);
//				intent.putExtra("FromVideoSecondFragment", true);
//				mContext.startActivity(intent);
//
//			}
//		});


		return imageView;		// 显示倒影图片（当前获取焦点）
	}

	public float getScale(boolean focused, int offset) {
		return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
	}

}
