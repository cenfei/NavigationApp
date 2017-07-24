package com.yj.navigation.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yj.navigation.R;
import com.yj.navigation.util.ImageLoaderUtil;
import com.yj.navigation.util.ReadFile;

import org.camera.encode.VideoEncoderFromSurface;

import java.io.IOException;
import java.util.List;

/**
 * Created by Sam on 2015/7/27.
 */
public class AdapterUpVideoItemView extends BaseAdapter {

    private static Context context;
    public List<String> mPersonal;
    private LayoutInflater mInflater;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;


    public AdapterUpVideoItemView(Context context, List<String> personalList) {
        super();
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String personalRanking = mPersonal.get(position);

        ViewHolder viewholder;
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.video_item, null);
            convertView.setTag(viewholder);
            viewholder.video_img_id = (ImageView) convertView.findViewById(R.id.video_img_id);
            viewholder.video_title_id = (TextView) convertView.findViewById(R.id.video_title_id);

        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }


        viewholder.video_title_id.setText(personalRanking);
       String mp4Num=personalRanking.substring(0,personalRanking.indexOf(".mp4"));


//        String mp4Num = personalRanking.substring(0, personalRanking.indexOf(".mp4"));

        //跳转到 show 页面
        String mp4ImgDir = VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE + mp4Num + "/";
        List<String> listImageName = null;
        try {
            listImageName = ReadFile.readfileOnlyFile(mp4ImgDir);
//            handler.sendEmptyMessage(0);
        } catch (IOException e) {
            e.printStackTrace();
        }



if(listImageName==null||listImageName.size()==0) return convertView;

        String imgUrl="file:///mnt"+ VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE+mp4Num+"/"+listImageName.get(0);


//        imgUrl=String.format("file:///mnt"+VideoEncoderFromSurface.DEBUG_FILE_NAME_BASE+"s%/s%.jpg",mp4Num,mp4Num);

        Log.e("video:imgurl",imgUrl);

//        String imgUrl = "file:///mnt/sdcard/Movies/mp4/2/1.jpg";

        imageLoader.displayImage(imgUrl,viewholder.video_img_id,options);
//        viewholder.video_img_id.setImageBitmap(ReadFile.getDiskBitmap(imgUrl));

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ReadFile.getDiskBitmap(imgUrl);
//            }
//        }).start();

        return convertView;
    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            return false;
        }
    });




    private static class ViewHolder {
        ImageView video_img_id;

        TextView video_title_id;

    }
}
