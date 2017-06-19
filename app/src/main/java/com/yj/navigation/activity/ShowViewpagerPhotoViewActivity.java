/*******************************************************************************
 * Copyright 2014 Sergey Tarasevich
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.yj.navigation.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yj.navigation.R;
import com.yj.navigation.base.ImageInfo;
import com.yj.navigation.base.MainApp;
import com.yj.navigation.component.HackyViewPager;
import com.yj.navigation.network.API;
import com.yj.navigation.util.Constants;


import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */

public class ShowViewpagerPhotoViewActivity extends Activity {

    int position;
    private List<ImageInfo> imageInfoListInit;

    TextView img_position, img_content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_myvideo);
        initImageList();
        ViewPager pager = (HackyViewPager) findViewById(R.id.pager);

        final ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                ImageInfo imageInfo = imageInfoListInit.get(position);

                img_content.setText(imageInfo.getUrl());
                img_position.setText((position + 1) + "/" + imageInfoListInit.size());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        pager.addOnPageChangeListener(listener);

        pager.setAdapter(new ImageAdapter(this, imageInfoListInit));
        position = getIntent().getIntExtra(Constants.Extra.IMAGE_POSITION, 0);


        img_position = (TextView) findViewById(R.id.img_position);
        img_content = (TextView) findViewById(R.id.img_content);
        if (imageInfoListInit.size() == 1) {
            img_position.setVisibility(View.GONE);
            img_content.setVisibility(View.GONE);

        } else {
            img_content.setText(imageInfoListInit.get(position).getContent());
            img_position.setText((position + 1) + "/" + imageInfoListInit.size());


        }
//		}
// img_content.setText(imageInfoListInit.get(position).getContent());
//
//		Log.e("wai当前的position",position+"");
//		img_position.setText((position+1)+ "/" + imageInfoListInit.size());

        pager.setCurrentItem(position);
    }


    public void initImageList() {

//        MainApp mainApp = (MainApp) getApplicationContext();
//
//        imageInfoListInit = mainApp.designRoomInfosPic;
		imageInfoListInit=new ArrayList<ImageInfo>();

		for(int i=0;i<16;i++){

			ImageInfo imageInfo=new ImageInfo();
			imageInfo.setUrl(Constants.IMAGES[i]);
			imageInfo.setContent(Constants.IMAGES[i]);
			imageInfoListInit.add(imageInfo);
		}

    }


    private class ImageAdapter extends PagerAdapter {

//		private static final String[] IMAGE_URLS = Constants.IMAGES;


        private List<ImageInfo> imageInfoList;

        private LayoutInflater inflater;
        private DisplayImageOptions options;

        ImageAdapter(Context context, List<ImageInfo> imageInfoList) {
            this.imageInfoList = imageInfoList;
            inflater = LayoutInflater.from(context);

            options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.rounded_gray_wallet_img)
                    .showImageOnFail(R.drawable.rounded_gray_wallet_img)
                    .resetViewBeforeLoading(true)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .considerExifParams(true)
                    .displayer(new FadeInBitmapDisplayer(300))
                    .build();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {

            return imageInfoList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {


            final ImageInfo imageInfo = imageInfoList.get(position);
            View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);

            FrameLayout image_frame = (FrameLayout) imageLayout.findViewById(R.id.image_frame);



            image_frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            assert imageLayout != null;



            ImageView design_room_img_3d = (ImageView) imageLayout.findViewById(R.id.design_room_img_3d);


//			PhotoView photoView
            PhotoView imageView = (PhotoView) imageLayout.findViewById(R.id.image);

//            if (imageInfo.type != null) {
//                if (imageInfo.type == 1) {
//                    design_room_img_3d.setVisibility(View.GONE);
//                    imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
//
//                        @Override
//                        public void onPhotoTap(View arg0, float arg1, float arg2) {
//                            finish();
//                        }
//                    });
//                } else {
//                    design_room_img_3d.setVisibility(View.VISIBLE);
//                    imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
//
//                        @Override
//                        public void onPhotoTap(View arg0, float arg1, float arg2) {
//                            Intent intent = new Intent(ShowViewpagerPhotoViewActivity.this, WebviewVrActivity.class);
//                            intent.putExtra("url", API.BIG_VR_360_IMG + imageInfo.id);
//                            intent.putExtra("title", imageInfo.text);
//                            startActivity(intent);
//                            finish();
//                        }
//                    });
//
//                }
//            }
            final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

            ImageLoader.getInstance().displayImage(imageInfo.getUrl(), imageView, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
//                    spinner.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    String message = null;
                    switch (failReason.getType()) {
                        case IO_ERROR:
                            message = "Input/Output error";
                            break;
                        case DECODING_ERROR:
                            message = "Image can't be decoded";
                            break;
                        case NETWORK_DENIED:
                            message = "Downloads are denied";
                            break;
                        case OUT_OF_MEMORY:
                            message = "Out Of Memory error";
                            break;
                        case UNKNOWN:
                            message = "Unknown error";
                            break;
                    }
                    Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();

                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    spinner.setVisibility(View.GONE);
                }
            });

            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }
}